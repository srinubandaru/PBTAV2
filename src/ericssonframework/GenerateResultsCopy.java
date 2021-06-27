package ericssonframework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

//import test.DriverFuncAutomation;

//import test.TCPieChart;

public class GenerateResultsCopy {

	//static int tcnt=0;
	static int i;
	static String fileName = null; //HTML file name
	FileOutputStream fileOut = null; 
	static Writer writer = null;
	static Calendar calendar = Calendar.getInstance();
	public static String testResultPath=null; //Test Results path
	public static int pcnt, fcnt,scnt,tecnt,tcnt; //variables which define execution status
	public static final String dateTime = "MMddyy_HHmmss";
	static SimpleDateFormat dateFormat = new SimpleDateFormat(dateTime);
	static Properties props = new Properties(); //Properties object
	private static String UserName = null; //DB username
	private static String Password = null; //DB password
	private static String ConnectionStr = null; //DB connection string
	private static String ExecResFolder; //Execution results folder
	static int TFPassedcnt =0; //Test suites passed count
	static int TFFailedcnt =0, NumSuites=0; //Test suites failed count, number of test suites
	static String TestSuites = null, resultdir=null; //Test suite names, Result directory
	public static String emailSource = null; //String to save Email body source
	public static String filename;
	public static int j=1;

	static DecimalFormat df = new DecimalFormat("#.##");
	public static double tet=0.000;

	/*public static void GenerateReport() throws IOException
	{

	//resultdir = resdir;
		try{

				String htmltext = htmlData();
				fileName = "HTMLReport"+".html";
				testResultPath =  "D:\\Ericsson\\Documents\\test xml\\CalculatorService\\HTMLReports\\"+ fileName;  
				File file = new  File(testResultPath);
				writer = new  BufferedWriter(new  FileWriter(file));
				writer.write(htmltext);
				writer.close();
			}catch(Exception e)
		{ e.printStackTrace();
		}

	}*/

	public static void htmlData() throws IOException
	{
		
		try
		{

		String HTMLBodyTitles="<table border=1 cellpadding=3 bgcolor=#C6DEFF width=97%> <tr> <th width=07%>Test Case ID</th> <th width=25%>Test Case Title</th> <th width=05%><b>Status</b></th><th width=12%>Start Time</th> <th width=08%>Execution Time</th><th width=20%>Error Reason</th><th width=05%>WSCheck Result</th><th width=10%>WSCheck Error Reason</th><th width=05%>DB Result</th></tr>";
		String resultColor="";
		String WSresultColor="";
		String DBresultColor="";
		String TCHTMLBody = "";
		TCHTMLBody = TCHTMLBody + HTMLBodyTitles;


		String path="..\\..\\Reports\\Results";
		File dir = new File(path);
		File[] files = dir.listFiles();
		System.out.println(files.length);
		/*File lastModified = Arrays.stream(files).filter(File::isDirectory).max(Comparator.comparing(File::lastModified)).orElse(null);
    String lastModifiedFolder=lastModified.toString();
    System.out.println(lastModified);
    File Subdir = new File(lastModifiedFolder);*/
		for(int filecount=0;filecount<files.length;filecount++)
		{
			if(files[filecount].isDirectory())
			{
				System.out.println("Directory ignored....");
			}
			else
			{
				System.out.println(files[filecount].getPath());
				System.out.println(files[filecount].getName());
				String filepath=files[filecount].getPath();
				filename=files[filecount].getName();
				String[] fname=filename.split(".xls");
				System.out.println(fname[0]);
				File f= new File(filepath);
				System.out.println(filepath);
				FileInputStream fis=new FileInputStream(f);
				HSSFWorkbook w=new HSSFWorkbook(fis);
				HSSFSheet s=w.getSheetAt(0);
				int rowcount1=s.getLastRowNum();
				System.out.println(rowcount1);
				for( i=1;i<=rowcount1;i++)
				{
					tcnt = tcnt+1;
				}
				System.out.println(tcnt);
				for(i=1;i<=rowcount1;i++)
				{
					Row row = s.getRow(i);
					if (row != null) 
					{
						Cell cell = s.getRow(i).getCell(2);
						String result=cell.getStringCellValue();
						System.out.println(result);

						cell = s.getRow(i).getCell(0);
						String TCaseID=cell.getStringCellValue();
						System.out.println(TCaseID);

						cell = s.getRow(i).getCell(3);
						String startTime=cell.getStringCellValue();
						System.out.println(startTime);

						cell = s.getRow(i).getCell(4);
						String executionTime=cell.getStringCellValue();
						String et[]=executionTime.split(" ");
						//System.out.println(et[0]);
						// System.out.println(et[1]);
						// System.out.println(executionTime);

						String TotExecnTime=et[0];
						double texe=Double.parseDouble(TotExecnTime);
						tet=texe+tet;
						String seconds=et[1];
						tet=Double.parseDouble(df.format(tet));

						cell = s.getRow(i).getCell(6);
						String DB_Result=cell.getStringCellValue();
						System.out.println(DB_Result);

						cell = s.getRow(i).getCell(7);
						String WS_Check_Result=cell.getStringCellValue();
						
						if ((WS_Check_Result!=null)&&WS_Check_Result.substring(WS_Check_Result.length()-1).equalsIgnoreCase(",")) {
		  	    			
		  	    			 WS_Check_Result=WS_Check_Result.substring(0, WS_Check_Result.length()-1);
		  	    			}
						cell = s.getRow(i).getCell(8);
						String WS_Check_ErrorReason=cell.getStringCellValue();
						
						if ((WS_Check_ErrorReason!=null)&&WS_Check_ErrorReason.substring(WS_Check_ErrorReason.length()-1).equalsIgnoreCase(",")) {
			    			
			            	  WS_Check_ErrorReason=WS_Check_ErrorReason.substring(0, WS_Check_ErrorReason.length()-1);
			    			}

						cell = s.getRow(i).getCell(5);
						String Errorlog=cell.getStringCellValue();
						System.out.println(Errorlog);
						 if ((Errorlog!=null)&&Errorlog.substring(Errorlog.length()-1).equalsIgnoreCase(",")) {
			  	    			
			    	 	 		Errorlog=Errorlog.substring(0, Errorlog.length()-1);
				    			}



						 // Use Case Status Color
						 
						if(result.equalsIgnoreCase("pass")){
							resultColor="#046607";   //#228B22
							pcnt=pcnt+1;

						}
						else if(result.equalsIgnoreCase("fail")) {
							resultColor="#910202";   //Red
							fcnt=fcnt+1;
						}
						else {
							resultColor="#0a0a96";   //#0000FF
							scnt=scnt+1;
						}
						
						 //  WebService Status Color
	    	 	 	     
		    	 	 	   if(WS_Check_Result.toLowerCase().contains("pass")){
		      	 	 	     WSresultColor="#046607";   //#228B22
		      	 	 	     
		      	 	 	     
		      	 	 	    }
		      	 	 	    else if(WS_Check_Result.toLowerCase().contains("fail")||WS_Check_Result.toLowerCase().contains("error")) {
		      	 	 	     WSresultColor="#910202";   //Red
		      	 	 	    
		      	 	 	    }
		      	 	 	    else {
		      	 	 	    //WSresultColor="#0a0a96";   //#0000FF
		      	 	 	    WSresultColor="#000000";
		      	 	 	     
		      	 	 	    }
		    	 	 	   
		    	 	 	 //  DB Result Status Color
		  	 	 	     
		    	 	 	   if(DB_Result.toLowerCase().contains("pass")){
		      	 	 	     DBresultColor="#046607";   //#228B22
		      	 	 	     
		      	 	 	     
		      	 	 	    }
		      	 	 	    else if(DB_Result.toLowerCase().contains("fail")) {
		      	 	 	     DBresultColor="#910202";   //Red
		      	 	 	    
		      	 	 	    }
		      	 	 	    else {
		      	 	 	       //DBresultColor="#0a0a96";   //#0000FF
		      	 	 	    
		      	 	 	         DBresultColor="#000000";
		      	 	 	     
		      	 	 	    }
		    	 	 	     
		    	 	 	 
						
						
						System.out.println(pcnt);
						if(!result.equals("Failed"))
						{

							TCHTMLBody=TCHTMLBody + "<tr> <td align=center >"+ TCaseID +"</td> <td>"+ fname[0] +" </td> <b><td align=center style=color:"+resultColor+">" + result + "</td></b> <td align=center>" + startTime + "</td></b> <td align=center>" + executionTime + "</td><td align=center style=color:" + resultColor + ">" + Errorlog +"</td><td align=center style=color:" + WSresultColor + ">"+ WS_Check_Result +"</td><td align=center style=color:" + WSresultColor + ">"+ WS_Check_ErrorReason +"</td><td align=center style=color:" + DBresultColor + ">"+ DB_Result +"</td></tr>";
						} 
						else
						{
							// depends on browser automation testcases
						}

					}

				}

			}
		}


		tecnt=pcnt+fcnt+scnt;

		System.out.println("Total Executed: "+ tecnt);
		System.out.println("Passed: "+ pcnt);
		System.out.println("Failed: "+ fcnt);
		System.out.println("Skipped: "+ scnt);
		
		InetAddress inetAddress = InetAddress.getLocalHost();
 		String IPaddress=inetAddress.getHostAddress();

		TCPieChart tcp = new TCPieChart("TC Pie chart");

		String HTMLHead="<HTML><HEAD><TITLE>Ericsson Automation Test Report as on " + calendar.getTime() + "</TITLE></HEAD><BODY> <Heading><p align=center><font face=TrebuchetMS size=4><B>Ericsson Automation Detailed Test Report as on "+ calendar.getTime() +  " </B></p></font></Heading><table WIDTH=100%><tr><td><img WIDTH=120 HEIGHT=65 src=http://logosdatabase.com/logoimages/78615497.jpg alt=Ericsson></td><td><img align=RIGHT WIDTH=140 HEIGHT=65 src=http://www.logostage.com/logos/STC.jpg alt=STC></td></table><P>";
		//String HTMLExeclog="<p><b>To see complete execution Log from "+ ipaddress + " machine: </b><a href=file:///" +resdir + "> <b>Click here</b> </a><br></p><p><b>To see complete execution Log from other than "+ ipaddress + " machine: </b><!--[if !IE]> --><a href=file:\\\\\\\\\\" + ExecResFolder + "> <![endif]--><!--[if !FF]><a href=file:\\\\" + ExecResFolder + "> <![endif]--><b>Click here</b> </a><br><b>Note: </b> The above Link will only open if you are in the same network</p><p><b> Log Details:</b></p><p><b>Machine ip :" + ipaddress + "</b></p><p><b>Log Path : " + resdir + "</b></p>";
		String HTMLExeclog="<p><b>To see complete execution Log from "+ IPaddress + " machine: </b><a href=file:///" +"C:\\Ericsson-PBTA_Base_Project\\PBTA2.0\\Reports"+ "> <b>Click here</b> </a><br></p><p><b>To see complete execution Log from other than "+ IPaddress + " machine: </b><!--[if !IE]> --><a href=file:\\\\\\\\\\" + IPaddress+"\\Ericsson-PBTA_Base_Project\\PBTA2.0\\Reports"+ "> <![endif]--><!--[if !FF]><a href=file:\\\\" + IPaddress+"\\Ericsson-PBTA_Base_Project\\PBTA2.0\\Reports"+ "> <![endif]--><b>Click here</b> </a><br><b>Note: </b> The above Link will only open if you are in the same network</p><p><b> Log Details:</b></p><p><b>Machine ip :" + IPaddress + "</b></p><p><b>Log Path : " + "C:\\Ericsson-PBTA_Base_Project\\PBTA2.0\\Reports" + "</b></p>";
		String HTMLFoot="</BODY></HTML>";
		String TSuiteHead="";
		String TFHTMLBody = "";
		

		String TestDetails = "<h2>Test Execution Summary Report</h2> <table border=3 cellpadding=5 bgcolor=#C6DEFF ><tr> <th align=left >Total Cases:</th> <td align=center width =80 style=color:#000000><b>"+ tcnt + "</b></td></tr> <tr> <th align=left>Total Executed:</th> <td align=center width =80 style=color:#000000><b>" + tecnt + "</b></td></tr><tr> <th align=left >Skipped Cases:</th> <td align=center width =80 style=color:#000000><b>"+ scnt + "</b></td></tr><tr> <th align=left>Passed:</th> <td align=center width =80 style=color:#387C44><b>"+ pcnt + "</b></td></tr><tr><th align=left>Failed:</th><td align=center width =80 style=color:red><b>"+ fcnt +"</b></td></tr><tr><th align=left>TotalExecutionTime:</th><td align=center width =80 style=color:#000000><b>"+ tet +"sec"+"</b></td></tr></table>";

		String finalstr = "<script language=\"JavaScript\">";
		String t =readFileAsString("..\\Templates\\FusionCharts.js");
		String u =readFileAsString("..\\Templates\\jquery.min.js");
		String v =readFileAsString("..\\Templates\\FusionCharts.HC.js");
		String x =readFileAsString("..\\Templates\\FusionCharts.HC.Charts.js");

		finalstr = finalstr.concat(t);
		finalstr = finalstr.concat(u);
		finalstr = finalstr.concat(v);
		finalstr = finalstr.concat(x);
		finalstr = finalstr + "</script><body bgcolor=\"#ffffff\">";

		String y = "<div id=\"chartdiv1\" align=\"center\">Chart will be displayed here</div><script type=\"text/javascript\">FusionCharts.setCurrentRenderer('javascript');";		
		finalstr = finalstr.concat(y);
		finalstr = finalstr + "var myChart = new FusionCharts( \"Pie3D\", \"myChartId\", \"500\", \"400\");";
		finalstr = finalstr + "myChart.setXMLData(\"<chart showLegend='1' caption='Test Case Execution Status' useRoundEdges='1' bgColor='FFFFFF,FFFFFF' showBorder='0' baseFont='Calibri' baseFontSize ='18'><set label='Passed' value='"+pcnt+"' color='#2EFE2E'/><set label='Failed' value='"+fcnt+"' color='#FF0000'/><set label='Skipped' value='"+scnt+"' color='#0000FF'/><styles> <definition><style name='CaptionFont' type='font' size='25'/></definition><application><apply toObject='Caption' styles='CaptionFont'/></application></styles></chart>\");myChart.render(\"chartdiv1\");</script>";


		String text = "";

		text=HTMLHead + TestDetails + finalstr +"<br><h3>Test Summary </h3>" + TCHTMLBody + "</table>" + HTMLExeclog + HTMLFoot;
		emailSource = HTMLHead + TestDetails + "<table WIDTH=100% ><tr align=center><td><img WIDTH=500 HEIGHT=250 src=cid:TestCaseGraph></td></tr></table><br><h3>Test Summary </h3>" + TCHTMLBody + "</table>" + HTMLExeclog + HTMLFoot;
		//return text;

		try
		{
			Date date = new Date();  
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy HH.mm.ss");  
			String Htmldate = formatter.format(date);  

			String htmltext = text;
			String[] fname=path.split("\\\\");
			int a=fname.length-1;
			System.out.println(fname[a]);
			String[] lname=fname[a].split("\\.");
			System.out.println("LName: "+lname[0]);

			fileName = "HTMLReport_"+Htmldate+".html";
			System.out.println(fileName);
			testResultPath =  "..\\..\\Reports\\Results\\"+" "+ fileName;  
			File htmlfile = new  File(testResultPath);
			writer = new  BufferedWriter(new  FileWriter(htmlfile));
			writer.write(htmltext);
			writer.close();

		}
		catch(Exception e)
		{ 
			e.printStackTrace();
		}
		}
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

	}

	static String readFileAsString(String filePath) throws java.io.IOException
	{
		
		try
		{

		StringBuffer fileData = new StringBuffer(1000);

		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[1024];

		int numRead=0;

		while((numRead=reader.read(buf)) != -1){

			String readData = String.valueOf(buf, 0, numRead);

			fileData.append(readData);

			buf = new char[1024];

		}
		reader.close();
		return fileData.toString();
		}
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}

	
}
