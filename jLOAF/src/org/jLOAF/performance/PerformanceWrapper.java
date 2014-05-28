/** PerformanceWrapper.java in the package org.JIFSA.performance of the JIFSA project.
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

import org.jLOAF.action.Action;
import org.jLOAF.casebase.Case;

/** Used as a wrapper class for an Agent or other PerformanceWrapper class
 * for performance metric collection.
 * 
 * @author Michael W. Floyd
 * @since 0.3
 */
public interface PerformanceWrapper {

	/** Used to get between the caller of the function and
	 * the Agent object (or other PerformanceWrapper). This
	 * can be used to calculate metrics based on the Cases
	 * given to the Agent and the AgentActions given in return.
	 * 
	 * @param c The case representing what the agent currently sees
	 * @return The action performed by the agent
	 *
	 * @author Michael W. Floyd
	 * @since 0.3
	 */
	public Action senseEnvironment(Case c);
}
