/** CaseActionPair.java in the package org.JIFSA.performance.datatypes of the JIFSA project.
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
package org.jLOAF.performance.datatypes;

import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;


/** Used to store a pair composed of a Case and an AgentAction.
 * 
 * @author Michael W. Floyd
 * @since 0.4
 */
public class CaseActionPair {

	/**The Case in the pairing */
	private Case m_case;
	/**The AgentAction in the pairing */
	private Action m_action;
	
	/** Constructs a pairing of a Case and an AgentAction
	 * from the ones supplied as parameters.
	 * 
	 * @param c The Case in the pairing
	 * @param act The AgentAction in the pairing
	 * 
	 * @author Michael W. Floyd
	 * @since 0.4
	 */
	public CaseActionPair(Case c, Action act){
		if(c == null){
			throw new IllegalArgumentException("Null Case given to CaseActionPair.");
		}
		if(act == null){
			throw new IllegalArgumentException("Null AgentAction given to CaseActionPair.");
		}
		
		this.m_case = c;
		this.m_action = act;
	}
	
	/** Returns the Case portion of the pairing.
	 * 
	 * @return the Case portion of the pairing
	 * 
	 * @author Michael W. Floyd
	 * @since 0.4
	 */
	public Case getCase(){
		return this.m_case;
	}
	
	/** Returns the AgentAction portion of the pairing.
	 * 
	 * @return the AgentAction portion of the pairing
	 * 
	 * @author Michael W. Floyd
	 * @since 0.4
	 */
	public Action getAction(){
		return this.m_action;
	}
}
