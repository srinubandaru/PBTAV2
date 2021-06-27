package ericssonframework;

import java.io.*;
import java.text.*;
import java.util.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LastModifiedFile  {
	
	static File lastestModifiedFile;
		//LastModifiedFileComparator
		
		public static File  getLatestFilefromDir(String dirPath){
		    File dir = new File(dirPath);
		    System.out.println(dirPath);
		    File[] files = dir.listFiles();
		   // System.out.println(files.length);
		    for(int i=0; i<files.length; i++)
		    	
		    {
		    	//System.out.println(files.length);
		    	System.out.println(files[i].getName());
		    }
		    if (files == null || files.length == 0) {
		        System.out.println("No files are available"); 
		    }

		    lastestModifiedFile = files[0];
		    System.out.println(files[0].getName());
		    //System.out.println(lastModifiedFile.getName()+lastModifiedFile.lastModified());
		    for (int i = 1; i < files.length; i++) {
		      if (lastestModifiedFile.lastModified() < files[i].lastModified()) {
		    	  lastestModifiedFile = files[i];
		           System.out.println(lastestModifiedFile.getName());
		           
		       }
		    	
		    }
		    System.out.println(lastestModifiedFile);
		    return lastestModifiedFile;
		}
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			getLatestFilefromDir("C:\\Users\\srinu\\Downloads");
			File fileName=lastestModifiedFile;
			
			String a=fileName.toString();
			System.out.println(a);
			
		}
		
}
	

/*
	public static void main(String[] args)
    {
	File file = new File("D:\\Ericsson\\Documents\\test xml\\CalculatorService\\Results");

	System.out.println("Before Format : " + file.lastModified());

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	System.out.println("After Format : " + sdf.format(file.lastModified()));
    }
*/

