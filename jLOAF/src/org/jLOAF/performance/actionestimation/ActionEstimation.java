/** ActionEstimation.java in the package org.JIFSA.reasoning.actionselection.actionestimation of the JIFSA project.
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


/** When a Case has multiple associated AgentActions and a single
 * one of them is required, it is necessary to estimate what action
 * the agent actually meant to perform.
 * 
 * @author Michael W. Floyd
 * @since 0.3
 */
public interface ActionEstimation {

	/** Returns the AgentAction that it thinks the Agent actually
	 * meant to perform.
	 * 
	 * @param possibilities The multiple actions the agent performed
	 * @return The estimation of the action it meant to perform
	 */
	public Action estimateAction(List<Action> possibilities);
}
