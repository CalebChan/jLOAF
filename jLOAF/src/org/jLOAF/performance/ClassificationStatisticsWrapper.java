/** ClassificationStatisticsWrapper.java in the package org.JIFSA.performance of the JIFSA project.
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

import org.jLOAF.Agent;
import org.jLOAF.action.Action;
import org.jLOAF.performance.actionestimation.ActionEstimation;


/** Used to gather statistics based on how the known AgentAction (from
 * the Case) relates to the AgentAction produced by the Agent.
 * 
 * @author Michael W. Floyd
 * @since 0.3
 */
public class ClassificationStatisticsWrapper extends StatisticsWrapper {

	/** Wraps around a PerformanceWrapper
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public ClassificationStatisticsWrapper(PerformanceWrapper internal, ActionEstimation ae){
		super(internal,ae);
	}
	
	/** Wraps around an Agent
	 * 
	 * @param agent The Agent to wrap around
	 * @param ae The action estimation algorithm to use
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public ClassificationStatisticsWrapper(Agent agent, ActionEstimation ae){
		super(agent,ae);
	}
	
	
	/** Records the expected (from the Case) and actual (from the Agent) actions that
	 * were performed. No differentiation is made between actions with different parameters.
	 * 
	 * @param fromCase The action in the Case
	 * @param fromAgent The action the Agent performed
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 *
	 */
	@Override
	protected void actionPair(Action fromCase, Action fromAgent){
		String fromCaseName = null;
		String fromAgentName = null;
		if(fromCase != null){
			fromCaseName = fromCase.getName();
		}else{
			fromCaseName = StatisticsWrapper.NOACTION;
		}
		
		if(fromAgent != null){
			fromAgentName = fromAgent.getName();
		}else{
			fromAgentName = StatisticsWrapper.NOACTION;
		}
		
		addPair(fromCaseName, fromAgentName);
	}

}
