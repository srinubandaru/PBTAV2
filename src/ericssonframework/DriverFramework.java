package ericssonframework;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import javax.mail.MessagingException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import selenium.AppTest;
import selenium.DriverScript2;
import selenium.ExcelFileUtil;

public class DriverFramework {

	public static void runPbta() throws Exception {
		
		
		

		// getting execution start time
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH.mm.ss");
		Date date = new Date();
		String startdateTime = dateFormat.format(date);
		System.out.println(startdateTime);

		// Creating folder to store execution results with default name
		File theDir = new File("..//..//Reports//Results//Logs");
		
	   // Creating Folder for store Screenshot
		
		File screenDir = new File("..//..//Reports//Results//Screenshots");
		
		if (!screenDir.exists()) {
			try {
				screenDir.mkdirs();
				System.out.println("Screenshots DIR Created");
				
			} catch (SecurityException se) {
				
			}
			
			
		}
		
		
     File RFValidation = new File("..//..//Reports//Results//RFV");
		
		if (!RFValidation.exists()) {
			try {
				RFValidation.mkdirs();
				System.out.println("RFV DIR Created");
				
			} catch (SecurityException se) {
				
			}
			
			
		}else{
			
			try {
				RFValidation.delete();
				System.out.println("RFV DIR deleted");
				
				RFValidation.mkdirs();
				System.out.println("RFV Dir created after Delete");
				
				 
				
			} catch (SecurityException se) {
				
			}
			
			
			
		}
		
		// Creating RFV Excel  file in RFV folder
		try {
			
			ExcelFileUtil RomoteExcel=new ExcelFileUtil("..\\..\\TestInputs\\RemotefilevalidationTemplate.xls","..\\..\\Reports\\Results\\RFV\\RemotefileValidationResult.xls");
			
			RomoteExcel.setData("Sheet1", 0, 8, " ");
			
			System.out.println("RFV Excel File Created");
			
		} catch (Exception e) {
			System.out.println("RFV Excel File Error : "+e);
		}
		

		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;

			try {
				theDir.mkdirs();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
		
		System.out.println("DIR created");
			}
		}
		String filePathString = "..//..//Reports//Results";
		File folder = new File(filePathString);
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile()) {

				// split filename from it's extension
				String[] filename = file.getName().split("\\.(?=[^\\.]+$)");

				// Matching defined filename
				if ((filename[1].equalsIgnoreCase("html"))
						&& ((filename[1].equalsIgnoreCase("xls")) || (filename[1].equalsIgnoreCase("xlsx"))))

					// Match occures.Apply anyany condition what you need
					System.out.println("File exist: " + filename[0] + "." + filename[1]+"\n\n");

				file.delete();
			}
			System.out.println("Unwanted files deleted....");
		}
		
		

		try {
			RunSoapUiBat.executeBat();
			Thread.sleep(10000);

		} catch (Exception e) {
			System.out.println("Exeception in RunSoapUiBat.executeBat()  Execution \n" + e);
		}  
		
		
		 try {
				
				SSHManager.flatfileReader();
				Thread.sleep(10000);

			} catch (Exception e) {
				System.out.println("Exeception in Remote flat file validation  \n" + e);
			}
		
		try {
			
			//AppTest.kickStart();
			DriverScript2 b=new DriverScript2();
			b.startTest();
			
			Thread.sleep(10000);

		} catch (Exception e) {
			System.out.println("Exeception in Browser based use cases  Execution  \n" + e);
		}
		
      

		try {
			GenerateResults.htmlData();
			Thread.sleep(3000);
			

		} catch (Exception e) {
			System.out.println("Exeception in GenerateResults.htmlData()  Execution \n" + e);
		}

		try {
			EmailReport.postMail();
			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println("Exeception in EmailReport Class  Execution \n\n  " + e);
		}
		
		
		//Report open in default browser
		
		try
		{
			  File folder2  = new File("..//..//Reports//Results");
			  //File folder  = new File(filePathString);
			   File[] listOfFiles2 = folder2.listFiles();
			   for (File file : listOfFiles2)
			   {
				   if (file.isFile())
			        {
					   File temp=new File(file.getCanonicalPath());
					   
			           if(file.getName().contains(".html"))
			           {
			        	   System.out.println("Opening HTML file with File name :"+file.getName());
			        	   Desktop.getDesktop().browse(temp.toURI());
			        	   System.out.println("Opened HTML file with File name :"+file.getName());
			        	   Thread.sleep(10000);
			           }
			        }
				   
			   }
			
		}
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		

		// Renaming the folder with execution start time

		String a = "..//..//Reports//ExecutionReports_" + startdateTime;
		System.out.println(a);
		File dir = new File("..//..//Reports//Results");
		File newName = null;
		
        try {
        	 newName = new File(a);
        	 System.out.println("File Created");
			
		} catch (Exception e) {
			System.out.println("Exception in Renaming : "+e);
			
		}
        
       // File newName = new File(a);
		
		
		try {
			if (dir.isDirectory()) {
				boolean isFileRenamed = dir.renameTo(newName);
				 System.out.println("File Created2");
				
				if (isFileRenamed)
					System.out.println("File has been renamed");
				else
					System.out.println("Error renaming the file");
			}
		} catch (Exception e) {

			e.printStackTrace();
			//System.out.println("Exception in Renaming : "+e);
		}

	}

	public static void main(String[] args) throws Exception {

		runPbta();

	}

}
