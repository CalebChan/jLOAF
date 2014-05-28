/** MeanExecutionTime.java in the package org.JIFSA.performance of the JIFSA project.
    Originally created 24-Nov-07

    Copyright (C) 2007  Michael W. Floyd

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
import org.jLOAF.casebase.Case;
import org.jLOAF.inputs.Input;


/** Used to calculate the mean time it took the Agent to select
 * an AgentAction to perform (in milliseconds) after getting an AgentInputs. This method
 * only accepts Agents (not PerformanceWrappers) because it needs to
 * be directly wrapped around the Agent (so other PerformanceWrappers
 * do not slow down the Agent)
 * 
 * @author Michael W. Floyd
 * @since 0.3
 */
public class MeanExecutionTime implements PerformanceWrapper {

	//agent that is being monitored
	private Agent m_agent;
	//the total time the agent has taken for all inputs
	private long m_totalTime;
	//the total number of inputs the agent has seen
	private int m_numInputs;
	
	/** Creates a MeanExecutionTime object that
	 * monitors the mean execution time of the supplied
	 * agent.
	 * 
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public MeanExecutionTime(Agent a){
		//check params
		if(a == null){
			throw new IllegalArgumentException("Null Agent given to MeanExecutionTime.");
		}
		
		this.m_agent = a;
		this.m_totalTime = 0;
		this.m_numInputs = 0;
	}
	
	/** Stores the time it takes the Agent to select an
	 * AgentAction to perform.
	 * 
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public Action senseEnvironment(Case c) {
		//check the parameters
		if(c == null){
			throw new IllegalArgumentException("Null Case given to senseEnvironment");
		}
		Input av = c.getInput();
		
		long startTime = System.currentTimeMillis();
		Action action = this.m_agent.senseEnvironment(av);
		long endTime = System.currentTimeMillis();
		
		this.m_totalTime += endTime - startTime;
		this.m_numInputs ++;
		
		return action;
	}
	
	/** Returns the mean execution time of the Agent being
	 * monitored (in milliseconds).
	 * 
	 * @return mean execution time
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public float getMeanExecutionTime(){
		//make sure we don't divide by zero
		if(this.m_numInputs == 0){
			return 0.0f;
		}
		return ((float)this.m_totalTime)/this.m_numInputs;
	}

}
