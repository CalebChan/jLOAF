/** LastActionEstimate.java in the package org.JIFSA.reasoning.actionselection.actionestimation of the JIFSA project.
    Originally created 23-Nov-07

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

package org.jLOAF.performance.actionestimation;

import java.util.List;

import org.jLOAF.action.Action;

/** Assumes the agent mean to perform the last action they performed
 * (possibly due to processing delay) and returns that action.
 * 
 * @author Michael W. Floyd
 * @since 0.3
 */
public class LastActionEstimate implements ActionEstimation {

	/** Returns the last AgentAction in the list. If there is no
	 * associated action, null is returned.
	 * 
	 * @see org.JIFSA.reasoning.actionselection.actionestimation.ActionEstimation
	 * 
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public Action estimateAction(List<Action> possibilities) {
		//check parameters
		if(possibilities == null){
			throw new IllegalArgumentException("Null list given to LastActionEstimate.");
		}
		
		//if there are no associated actions, return null
		if(possibilities.size() == 0){
			return null;
		}
		
		//return the last item
		return possibilities.get(possibilities.size() -1);
	}

}
