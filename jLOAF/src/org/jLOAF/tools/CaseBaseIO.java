/** CaseBaseIO.java in the package org.JIFSA.tools of the JIFSA project.
    Originally created 18-Jun-07

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

package org.jLOAF.tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jLOAF.casebase.CaseBase;


/** This class is used to handle the loading of a CaseBase from a file as well
 * as saving a CaseBase to a file.
 * 
 * @author Michael W. Floyd
 * @since 0.2
 *
 */
public class CaseBaseIO {

	/** Used to read a CaseBase from a file.
	 * 
	 * @param filename The name of the file to read from.
	 * @return The CaseBase
	 *
	 * @author Michael W. Floyd
	 * @since 0.2
	 */
	public static CaseBase loadCaseBase(String filename){
		//test the parameters
		if(filename == null){
			throw new IllegalArgumentException("A null value was given for the file name");
		}
		
		try{
			//open the file streams
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fileIn);
			
			Object o = ois.readObject();
			
			ois.close();
			//make sure we read a CaseBase
			if( !(o instanceof CaseBase)){
				return null;
			}
			return (CaseBase)o;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Filename : " + filename);
			//if there was a file problem we return null
			return null;
		}
		

	}
	
	/** Used to save a CaseBase to a file.
	 * 
	 * @param casebase CaseBase that is being writen to the file
	 * @param filename The name of the file to write to
	 * @throws IOException
	 *
	 * @author Michael W. Floyd
	 * @since 0.2
	 */
	public static void saveCaseBase(CaseBase casebase, String filename) throws IOException{
		//test the parameters
		if(filename == null || casebase == null){
			throw new IllegalArgumentException("A null value was given for the file name");
		}
		
		//create the output streams
		FileOutputStream fileOut = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream(fileOut);
	    
		//write the case base
		oos.writeObject(casebase);
		
		//close the output streams
		oos.close();
		fileOut.close();
	}
}
