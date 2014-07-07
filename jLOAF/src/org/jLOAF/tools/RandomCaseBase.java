/** RandomCaseBase.java in the package org.JIFSA.tools of the JIFSA project.
    Originally created 16-Feb-08

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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jLOAF.casebase.Case;
import org.jLOAF.casebase.CaseBase;

/** Randomly selects Cases from a lager CaseBase and creates a smaller
 * CaseBase with those selected Cases.
 * 
 * @author Michael W. Floyd
 * @since 0.5
 *
 */
public class RandomCaseBase {

	/** Given a CaseBase, random Cases will be selected from that CaseBase to create
	 * a new CaseBase. *NOTE* Cases are selected with replacement
	 * 
	 * @param cb the CaseBase
	 * @param num the number of Cases to select
	 * @return The new CaseBase
	 * 
	 * @author Michael W. Floyd
	 * @since 0.5
	 */
	public static CaseBase random(CaseBase cb, int num){
		//check the input
		if(cb == null){
			throw new IllegalArgumentException("Null parameter given to RandomCaseBase.random");
		}
		if(num <= 0 || num > cb.getSize()){
			throw new IllegalArgumentException("Invalid number of Cases specified.");
		}
		
		//create our empty CaseBase to add to
		CaseBase newCB = new CaseBase();
		
		List<Case> allCases = (List<Case>) cb.getCases();
		
		Random rand = new Random();
		for(int ii=0;ii<num;ii++){
			int randomInt = rand.nextInt(allCases.size());
			newCB.add(allCases.get(randomInt));
		}
		
		
		return newCB;
	}
	
	/** Given a CaseBase, random Cases will be selected from that CaseBase to create
	 * a new CaseBase. *NOTE* Cases are selected without replacement
	 * 
	 * @param cb The CaseBase
	 * @param num The number of Cases to be select
	 * @return The new CaseBase
	 * 
	 * @author Caleb Chan
	 */	
	public static CaseBase randomWithoutReplacement(CaseBase cb, int num){
		//check the input
		if(cb == null){
			throw new IllegalArgumentException("Null parameter given to RandomCaseBase.random");
		}
		if(num <= 0 || num > cb.getSize()){
			throw new IllegalArgumentException("Invalid number of Cases specified.");
		}
		
		//create our empty CaseBase to add to
		CaseBase newCB = new CaseBase();
		ArrayList<Case> allCases = new ArrayList<Case>(cb.getCases());
		Random rand = new Random();
		for(int ii=0;ii<num;ii++){
			int randomInt = rand.nextInt(allCases.size());
			newCB.add(allCases.remove(randomInt));
		}
		
		return newCB;
	}
}
