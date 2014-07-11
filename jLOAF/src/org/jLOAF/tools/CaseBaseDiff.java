/** CaseBaseDiff.java in the package org.JIFSA.tools of the JIFSA project.
    Originally created 21-Oct-08

    Copyright (C) 2008    Edgar Acosta

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

import java.io.*;

import org.jLOAF.casebase.CaseBase;

/**
 * A command line tool for finding the differences between 2 casebases.
 * 
 * @author Edgar Acosta
 * @since 0.5
 * 
 */
public class CaseBaseDiff {
	@SuppressWarnings("unused")
	private CaseBase CBone; //TODO: REMOVE / FIX
	@SuppressWarnings("unused")
	private CaseBase CBtwo; //TODO: REMOVE / FIX

	/**
	 * Main procedure
	 * 
	 * @author Edgar Acosta
	 * 
	 */
	public static void main(String a[]) throws IOException {
		String firstCBfile = "";
		String secondCBfile = "";
		try {
			if (a.length < 2)
				throw new Exception();
			else {
				firstCBfile = a[0];
				secondCBfile = a[1];
			}
		} catch (Exception e) {
			System.err.println("");
			System.err.println("USAGE: java CaseBaseDiff file1 file2");
			System.err.println("");
			System.err.println("    Files must contain a serialized CaseBase,");
			System.err.println("   usually files with extension .cb");
			return;

		}
		System.out.println("Loading Case Bases ...");
		CaseBase fcb = CaseBaseIO.loadCaseBase(firstCBfile);
		CaseBase scb = CaseBaseIO.loadCaseBase(secondCBfile);
		CaseBaseDiff cbd = new CaseBaseDiff(fcb, scb);
		System.out.println("Comparing Case Bases ...");
		cbd.compareCBs();
	}

	/**
	 * Class constructor
	 * 
	 * @param firstCB
	 *            the first CaseBase received
	 * @param secondCB
	 *            the second CaseBase
	 * 
	 * @author Edgar Acosta
	 * 
	 */
	public CaseBaseDiff(CaseBase firstCB, CaseBase secondCB) {
		CBone = firstCB;
		CBtwo = secondCB;
	}

	/**
	 * comparator method. Prints out the differences.
	 * 
	 * @author Edgar Acosta
	 * 
	 */
	public void compareCBs() {
		//TODO: Fix
		//CBone.Diff(CBtwo);
	}
}
