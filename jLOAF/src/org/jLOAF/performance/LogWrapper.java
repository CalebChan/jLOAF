/** LogWrapper.java in the package org.JIFSA.performance of the JIFSA project.
    Originally created 09-Aug-08

    Copyright (C) 2008  Michael W. Floyd

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
import java.util.List;

import org.jLOAF.Agent;
import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;
import org.jLOAF.inputs.Input;
import org.jLOAF.performance.datatypes.CaseActionPair;

/** Used as a wrapper class for an Agent or other PerformanceWrapper class
 * and logs the input case and the action the agent performed. Over the 
 * duration of operation, this class will log all of these pairs. 
 * 
 * @author Michael W. Floyd
 * @since 0.4
 */
public class LogWrapper implements PerformanceWrapper {

	/**Used to hold the Agent that is wrapped, null if an Agent is not wrapped */
	protected Agent m_agent;
	/**Used to hold the PerformanceWrapper that is wrapped, null if one is not wrapped */
	protected PerformanceWrapper m_internalwrapper;
	/**Holds the pairs that have already been encountered */
	private List<CaseActionPair> m_pairlist;
	
	/** This constructor is used if the LogWrapper is being wrapped directly 
	 * around an Agent.
	 * 
	 * @param agent The Agent to wrap around
	 *
	 * @author Michael W. Floyd
	 * @since 0.4
	 */
	public LogWrapper(Agent a){
		if(a == null){
			throw new IllegalArgumentException("Null Agent given to LogWrapper.");
		}
		
		//we got an Agent, so set that and set the wrapper null
		this.m_agent = a;
		this.m_internalwrapper = null;
		
		//create the empty list
		this.m_pairlist = new ArrayList<CaseActionPair>();
	}
	
	/** This constructor is used if the LogWrapper is being wrapped around 
	 * another PerformanceWrapper.
	 * 
	 * @param pw The PerformanceWrapper to wrap around
	 *
	 * @author Michael W. Floyd
	 * @since 0.4
	 */
	public LogWrapper(PerformanceWrapper pw){
		if(pw == null){
			throw new IllegalArgumentException("Null PerformanceWrapper given to LogWrapper.");
		}
		
		this.m_agent = null;
		this.m_internalwrapper = pw;
		
		//create the empty list
		this.m_pairlist = new ArrayList<CaseActionPair>();
	}
	
	/** This method will pass the inputed Case to the object that
	 * this wrapper is wrapped around and will store the input and
	 * output pairings from the wrapped object.
	 * 
	 * @param c The case representing what the agent currently sees
	 * @return The action performed by the agent
	 *
	 * @author Michael W. Floyd
	 * @since 0.4
	 */
	public Action senseEnvironment(Case c) {
		if(c == null){
			throw new IllegalArgumentException("Null Case given to LogWrapper.senseEnvironment");
		}
		
		//pass the Case along to what is wrapped around
		Action act = null;
		if(this.m_internalwrapper != null){
			act = this.m_internalwrapper.senseEnvironment(c);
		}else if(this.m_agent != null){
			Input ainput = c.getInput();
			act = this.m_agent.senseEnvironment(ainput);
		}else{
			throw new IllegalStateException("Neither a PerformanceWrapper nor Agent was set.");
		}
		
		//now we store the CaseActionPair
		CaseActionPair cap = new CaseActionPair(c,act);
		this.m_pairlist.add(cap);
		
		return act;
	}

	/** Returns the pairs that have already been logged by the class.
	 * 
	 * @return the list of logged pairs
	 * 
	 * @author Michael W. Floyd
	 * @since 0.4
	 */
	public List<CaseActionPair> getPairs(){
		return this.m_pairlist;
	}
}
