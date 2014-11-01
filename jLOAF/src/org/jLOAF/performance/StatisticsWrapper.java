/** StatisticsWrapper.java in the package org.JIFSA.performance of the JIFSA project.
    Originally created 24-Nov-07

    Copyright (C) 2007 Michael W. Floyd

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

 * 
 */
package org.jLOAF.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jLOAF.Agent;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.performance.actionestimation.ActionEstimation;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

/** Used to gather statistics based on how the known AgentAction (from
 * the Case) relates to the AgentAction produced by the Agent.
 * 
 * @author Michael W. Floyd
 * @since 0.3
 */
public abstract class StatisticsWrapper implements PerformanceWrapper {

	public static final String NOACTION = "NO_ACTION";
	
	//the items that are wrapped around
	protected PerformanceWrapper m_internalWrapper;
	protected Agent m_agent;
	
	//the action estimation algorithm to use
	protected ActionEstimation m_actionEstimation;
	
	//a confusion matrix. The external HashMap maps the rows (the expected actions)
	//to a hashmap containing the values for actual actions.
	protected Map<String,Map<String,Integer>> m_confusion;
	
	/** This constructor is used if the StatisticsWrapper is
	 * being wrapped around another PerformanceWrapper. Since
	 * each Case can have several AgentActions, we use a supplied
	 * ActionEstimation algorithm to select which on the actions
	 * we think the Case actually represents.
	 * 
	 * @param internal The PerformanceWrapper to wrap around
	 * @param ae The action estimation algorithm to use
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public StatisticsWrapper(PerformanceWrapper internal, ActionEstimation ae){
		if(ae == null){
			throw new IllegalArgumentException("Null ActionEstimation given to StatisticsWrapper");
		}
		if(internal == null){
			throw new IllegalArgumentException("Null PerformanceWrapper given to StatisticsWrapper");
		}

		this.m_internalWrapper = internal;
		this.m_agent = null;
		this.m_actionEstimation = ae;
		
		this.m_confusion = new HashMap<String,Map<String,Integer>>();
	}
	
	/** This constructor is used if the StatisticsWrapper is
	 * being wrapped directly around an Agent. Since
	 * each Case can have several AgentActions, we use a supplied
	 * ActionEstimation algorithm to select which on the actions
	 * we think the Case actually represents.
	 * 
	 * @param agent The Agent to wrap around
	 * @param ae The action estimation algorithm to use
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public StatisticsWrapper(Agent agent, ActionEstimation ae){
		if(ae == null){
			throw new IllegalArgumentException("Null ActionEstimation given to StatisticsWrapper");
		}
		if(agent == null){
			throw new IllegalArgumentException("Null Agent given to StatisticsWrapper");
		}

		this.m_internalWrapper = null;
		this.m_agent = agent;
		this.m_actionEstimation = ae;
		
		this.m_confusion = new HashMap<String,Map<String,Integer>>();
	}
	
	/** Passes a Case (or AgentInputs) to the wrapped object and records the 
	 * expected and actual actions that were performed.
	 * 
	 * @Override
	 * 
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public Action senseEnvironment(Case c){
		if(c == null){
			throw new IllegalArgumentException("Null case given to StatisticsWrapper");
		}
		
		//get the data from the case
		List<Action> actions  = new ArrayList<Action>();
		actions.add(c.getAction());
		Action fromCase = this.m_actionEstimation.estimateAction(actions);
		
		//figure out which is wrapped, the agent or the performance wrapper
		if(this.m_internalWrapper != null){
			Action fromAgent = this.m_internalWrapper.senseEnvironment(c);
			actionPair(fromCase,fromAgent);
			return fromAgent;
		}else if(this.m_agent != null){
			Action fromAgent = this.m_agent.senseEnvironment(c.getInput());
			actionPair(fromCase,fromAgent);
			return fromAgent;
		}else{
			throw new IllegalStateException("Neither a PerformanceWrapper nor Agent was set.");
		}		
	}
	
	/** Used to get the current classification accuracy of the Agent
	 * 
	 * @return The classification accuracy
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 *
	 */
	public float getClassificationAccuracy(){
		int correct = 0;
		int total = 0;
		
		Set<String> allRows = this.m_confusion.keySet();
		
		//go through each of the rows in the matrix
		for(String currentRow: allRows){
			Map<String,Integer> thisRow = this.m_confusion.get(currentRow);
			//now we get all the non-zero cells for this row
			Set<String> nonzeroCols = thisRow.keySet();
			for(String col: nonzeroCols){
				Integer cellValue = thisRow.get(col);
				//if the label is the same it represents a correct match
				if(currentRow.equals(col)){
					correct += cellValue.intValue();
				}
				total += cellValue.intValue();
			}
		}
		
		//make sure we don't divide by zero
		if(total == 0){
			return 0.0f;
		}
		return (float)correct/(float)total;
	}
	
	/** Used to get the current recall of the Agent for a specific type of
	 * action.
	 * 
	 * @param action The action to get the recall for
	 * @return The recall
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public float getPrecision(String action){
		if(action == null){
			throw new IllegalArgumentException("Null String given to getPrecision()");
		}
		
		int correct = 0;
		int total = 0;
		
		//go through each row
		Set<String> allRows = this.m_confusion.keySet();
		for(String currentRow: allRows){
			Map<String,Integer> rowMap = this.m_confusion.get(currentRow);
			Integer cellVal = rowMap.get(action);
			
			//see if there was an entry in this col for that action
			if(cellVal != null){
				//see if it was a correct match
				if(currentRow.equals(action)){
					correct += cellVal.intValue();
				}
				total += cellVal.intValue();
			}
		}
		
		if(total == 0){
			return 0.0f;
		}
		
		return (float)correct/(float)total;
	}
	
	/** Used to get the current recall of the Agent for a specific type of
	 * action.
	 * 
	 * @param action The action to get the recall for
	 * @return The recall
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public float getRecall(String action){
		if(action == null){
			throw new IllegalArgumentException("Null String given to getRecall()");
		}
		
		Map<String,Integer> actionRow = this.m_confusion.get(action);
		
		//we never had an expected action of this type
		if(actionRow == null){
			return 0.0f;
		}
		
		int correct = 0;
		int total = 0;
		
		//get all the actions the agent performed when it should have been the expected action
		Set<String> colEntries = actionRow.keySet();
		for(String currentCol: colEntries){
			Integer cellVal = actionRow.get(currentCol);
			//if they are the same, it means a correct match
			if(action.equals(currentCol)){
				correct += cellVal.intValue();
			}
			total += cellVal.intValue();
			
		}
		
		return (float)correct/(float)total;
	}

	/** Used to get the current f1-measure of the Agent for a specific type of
	 * action.
	 *  
	 * @param action The action to get the f1 for
	 * @return The f1 value
	 * 
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public float getF1(String action){
		if(action == null){
			throw new IllegalArgumentException("Null value given to getF1");
		}
		
		float prec = getPrecision(action);
		float recall = getRecall(action);
		float numer = 2*prec*recall;
		float denom = prec + recall;
		
		if(denom == 0.0f){
			return 0.0f;
		}
		return numer/denom;
	}
	
	/** Used to get the current global f1-measure of the Agent.
	 *  
	 * @return The global f1 value
	 * 
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public float getGlobalF1(){
		int num = 0;
		float sumF1 = 0;
		
		//make a list of all actions in the table
		Set<String> allRow = this.m_confusion.keySet();
		List<String> completeList = new ArrayList<String>(allRow);
		
		//update the complete list with ones that were never matched properly
		//this is necessary since the metric is an average of all actions (even zeros)
		for(String currentRow: allRow){
			Map<String,Integer> cols = this.m_confusion.get(currentRow);
			Set<String> colNames = cols.keySet();
			for(String col: colNames){
				if(!completeList.contains(col)){
					completeList.add(col);
				}
			}
		}
		
		//now we sum the f-measures for each action type
		for(String currentAction: completeList){
			sumF1 += getF1(currentAction);
			num++;
		}

		//make sure we don't divide by zero
		if(num == 0){
			return 0.0f;
		}
		return sumF1/num;
	}
	
	/** Returns a list of all the types of actions that
	 * were expected (meaning they were the action of the
	 * Case).
	 * 
	 * @return A list of expected actions
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 *
	 */
	public List<String> getAllExpectedActions(){
		return new ArrayList<String>(this.m_confusion.keySet());
	}
	
	/** Returns all of the statistics as a StatisticsBundle.
	 * 
	 * @return a StatisticsBundle
	 * 
	 * @author Michael W. Floyd
	 * @since 0.5
	 */
	public StatisticsBundle getStatisticsBundle(){
		List<String> expected = this.getAllExpectedActions();
		
		//f1global, accuracy + precision/recall/f1 for all actions
		int numStats = 2 + expected.size()*3;
		
		float[] stats = new float[numStats];
		String[] labels = new String[numStats];
		
		stats[0] = this.getGlobalF1();
		labels[0] = "Global F1";
		stats[1] = this.getClassificationAccuracy();
		labels[1] = "Classification Accuracy";
		
		int index = 2;
		for(String nextAct: expected){
			stats[index] = this.getF1(nextAct);
			labels[index] = "F1 " + nextAct;
			index++;
			stats[index] = this.getPrecision(nextAct);
			labels[index] = "Precision " + nextAct;
			index++;
			stats[index] = this.getRecall(nextAct);
			labels[index] = "Recall " + nextAct;
			index++;
		}
		
		StatisticsBundle bndl = new StatisticsBundle(stats,labels);
		return bndl;
	}
	
	/** Records the expected (from the Case) and actual (from the Agent) actions that
	 * were performed.
	 * 
	 * @param fromCase The action in the Case
	 * @param fromAgent The action the Agent performed
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 *
	 */
	protected abstract void actionPair(Action fromCase, Action fromAgent);
	
	
	/** Adds values to the confusion matrix.
	 * 
	 * @param fromCaseName The row in the matrix
	 * @param fromAgentName The column in the matrix
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 *
	 */
	protected void addPair(String fromCaseName, String fromAgentName){
		//get the confusion row related to the expected (fromCase) action
		Map<String,Integer> confusionRow = this.m_confusion.get(fromCaseName);
		
		//see if the row is null..meaning this is the first case we expected to be that action
		if(confusionRow == null){
			confusionRow = new HashMap<String,Integer>();
		}
		
		//now we get the value in the specific cell of the matrix
		Integer cellValue = confusionRow.get(fromAgentName);
		
		//now we see if it is null (meaning a zero) or a non-zero value
		if(cellValue == null){
			cellValue = new Integer(1);
		}else{
			cellValue = new Integer(cellValue.intValue() + 1);
		}
		
		//now we put the updated values back in the matrix
		confusionRow.put(fromAgentName, cellValue);
		this.m_confusion.put(fromCaseName, confusionRow);
	}

	
	public Map<String, Map<String, Integer>> getConfusionMatrix(){
		return java.util.Collections.unmodifiableMap(this.m_confusion);
	}
}
