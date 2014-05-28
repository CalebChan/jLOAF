/** CaseBaseMerger.java in the package org.JIFSA.tools of the JIFSA project.
    Originally created 16-Mar-08

    Copyright (C) 2007 - 2008 Michael W. Floyd

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
package org.jLOAF.tools;

import java.util.Collection;
import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;

/** Takes a collection of CaseBases and merges them into a single CaseBase
 * 
 * @author Michael W. Floyd
 * @since 0.4
 *
 */
public class CaseBaseMerger {

	/** A collection of CaseBases will be merged into a single CaseBase that
	 * contains every Case in each of the CaseBases.
	 * 
	 * @param collect The Collection of CaseBases
	 * @return The new CaseBase
	 */
	public static CaseBase merge(Collection<CaseBase> collect){
		//check the input
		if(collect == null){
			throw new IllegalArgumentException("Null parameter given to CaseBaseMerge.merge()");
		}
		
		//create our empty CaseBase to add to
		CaseBase merged = new CaseBase();
		
		//now go through each of the other CaseBases
		for(CaseBase cb : collect){
			Collection<Case> cases = cb.getCases();
			for(Case curCase : cases){
				merged.add(curCase);
			}
		}
		
		return merged;
	}
}
