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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import selenium.ExcelFileUtil;

//import test.DriverFuncAutomation;

//import test.TCPieChart;

public class GenerateResults {
	
	//static int tcnt=0;
	static int i;
	static String fileName = null; //HTML file name
	FileOutputStream fileOut = null; 
	static Writer writer = null;
	static Calendar calendar = Calendar.getInstance();
	public static String testResultPath=null; //Test Results path
	public static int pcnt, fcnt,scnt,tecnt,tcnt,nacnt; //variables which define execution status
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
	

	
	public static void htmlData() throws Exception
	{
		
		String HTMLBodyTitles="<table border=1 cellpadding=3 bgcolor=#C6DEFF width=100%> <tr> <th width=07%>Test Case ID</th> <th width=08%>Test Case Title</th> <th width=05%><b>Status</b></th><th width=05%><b>WSResult</b></th><th width=12%>Start Time</th> <th width=08%>Execution Time</th><th width=20%>Error Reason</th><th width=05%>WSCheck Result</th><th width=10%>WSCheck Error Reason</th><th width=05%>DB Result</th><th width=05%>RFV Result</th><th width=10%>RFV ErrorReason</th></tr>";
		String resultColor="";
		String WSresultColor="";
		String DBresultColor="";
		String tResultColor="";
		String rfvColor="";
		
		
		
		String TCHTMLBody = "";
		TCHTMLBody = TCHTMLBody + HTMLBodyTitles;
		
		
		
		  try {
			  writeToUcaseExcel();
 	    	
			} catch (Exception e) {
				System.out.println("#### Error In file writing "+e);
			}
		
		
    String path="..\\..\\Reports\\Results";
    
    
    
    File dir = new File(path);
    
    String resultPath=dir.getCanonicalPath();
    
   
    
    File[] files = dir.listFiles();
    System.out.println(files.length);
    
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
    	 		if (s.getRow(i)!=null&&s.getRow(i).getCell(0)!=null) {
    	 			tcnt = tcnt+1;
				}
    	     
    	    }
    	 	System.out.println(tcnt);
    	 	for(i=1;i<=rowcount1;i++)
    	 	 {
    	 	 	  Row row = s.getRow(i);
    	 	 	  if (row != null) 
    	 	 	  {
    	 	 		  
    	 	 	    Cell cell = s.getRow(i).getCell(2);
    	 	 	    if (cell==null) {
						break;
					}
    	 	 	    String result=cell.getStringCellValue();
    	 	 	    
    	 	 	    System.out.println(result);
    	 	 	    
    	 	 	   /* String[] multiRes=result.split(",");
     	 	 	 	if (multiRes.length>1) {
     	 	 		 	nacnt=nacnt+1;
     	            }*/
     	 	 	 
    	 	 	  if ((result!=null)&&result.substring(result.length()-1).equalsIgnoreCase(",")) {
    	    			
    	 	 		result=result.substring(0, result.length()-1);
   	    			}
    	 	 	  
    	 	 	
    	 	 	    
    	 	 	    
    	 	 	    cell = s.getRow(i).getCell(0);
    	 	 	    String TCaseID=cell.getStringCellValue();
    	 	 	    System.out.println(TCaseID);
    	 	 	    
    	 	 	   
    	 	 	    
    	 	 	     cell = s.getRow(i).getCell(3);
    	 	 	    
    	 	 	    String startTime=cell.getStringCellValue();
    	 	 	    System.out.println(startTime);
    	 	 	    
    	 	 	 
    	 	 	    
    	 	 	      cell = s.getRow(i).getCell(4);
    	 	 	    
    	 	 	    String executionTime=cell.getStringCellValue();
    	 	 	    String et[]=executionTime.split(" ");
    	 	 	    
    	 	 	    
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
    	 	 	    
    	 	 	   
    	 	 	  
    	 	 	  cell = s.getRow(i).getCell(9);
    	            
    	            String rfv=cell.getStringCellValue();
    	            
    	          
  	              if ((rfv!=null)&&(rfv.length()>=1)) {
  	            	  
  	            	  if (rfv.substring(0,rfv.length()-1).equalsIgnoreCase(",")) {
  	            		rfv=rfv.substring(0, rfv.length()-1);;
						
					}
  	    			
  	            	
  	    			}
  	              
                  cell = s.getRow(i).getCell(10);
  	            
  	            String rfvError=cell.getStringCellValue();
  	            
  	          
	              if ((rfvError!=null)&&(rfvError.length()>=1)) {
	            	  if (rfvError.substring(0,rfvError.length()-1).equalsIgnoreCase(",")) {
	            		  rfvError=rfvError.substring(0, rfvError.length()-1);
						
					}
	    			
	            	  
	    			}
    	 	 	    
	              
	              //Web Service Result color 
	              
	              if(result.toLowerCase().contains("pass")){
	      	 	 	     resultColor="#046607";   //#228B22
	      	 	 	     
	      	 	 	     
	      	 	 	    }
	      	 	 	    else if(result.toLowerCase().contains("fail")) {
	      	 	 	     resultColor="#910202";   //Red
	      	 	 	    
	      	 	 	    }
	      	 	 	    else {
	      	 	 	   // result="#0a0a96";   //#0000FF
	      	 	 	       resultColor="#000000";
	      	 	 	    }
	              
	              if(result.trim().toLowerCase().contains("pass")&&result.trim().toLowerCase().contains("fail")){
	            	  resultColor="#0a0a96" ;  //Blue
    	 	 	     
    	 	 	     
    	 	 	    }
    	 	 	
    	 	 	     
    	 	 	   //  WebService Check Status Color
    	 	 	     
    	 	 	   if(WS_Check_Result.toLowerCase().contains("pass")){
      	 	 	     WSresultColor="#046607";   //#228B22
      	 	 	     
      	 	 	     
      	 	 	    }
      	 	 	    else if(WS_Check_Result.toLowerCase().contains("fail")||WS_Check_Result.toLowerCase().contains("error")) {
      	 	 	     WSresultColor="#910202";   //Red
      	 	 	    
      	 	 	    }
      	 	 	    else {
      	 	 	    WSresultColor="#0a0a96";   //#0000FF
      	 	 	   // WSresultColor="#000000";
      	 	 	    }
    	 	 	   
    	 	 	 //  DB Result Status Color
  	 	 	     
    	 	 	   if(DB_Result.toLowerCase().contains("pass")){
      	 	 	     DBresultColor="#046607";   //#228B22
      	 	 	     
      	 	 	     
      	 	 	    }
      	 	 	    else if(DB_Result.toLowerCase().contains("fail")||DB_Result.toLowerCase().contains("error")) {
      	 	 	     DBresultColor="#910202";   //Red
      	 	 	    
      	 	 	    }
      	 	 	    else {
      	 	 	    DBresultColor="#0a0a96";   //#0000FF
      	 	 	     
      	 	 	    }
    	 	 	   
    	 	 	   
    	 	 	 if(rfv.trim().toLowerCase().contains("pass")){
    	 	 		rfvColor="#046607";   //#228B22
      	 	 	     
      	 	 	     
      	 	 	    }
    	 	 	      else if(rfv.trim().toLowerCase().contains("fail")) {
      	 	 	       rfvColor="#910202";   //Red
      	 	 	    //rfvColor="#0a0a96";     //Blue
      	 	 	    
      	 	 	    
      	 	 	    }
      	 	 	    else {
      	 	 	    rfvColor="#0a0a96";   //#0000FF
      	 	 	    	
      	 	 	   // rfvColor="#910202";  //Red
      	 	 	     
      	 	 	    }
    	 	 	     if(rfv.trim().toLowerCase().contains("pass")&&rfv.trim().toLowerCase().contains("fail")){
    	 	 	    	rfvColor="#0a0a96" ;  //Blue
      	 	 	     
      	 	 	     
      	 	 	    }
    	 	 	   
    	 	 	   
    	 	 	     
    	 	 	   // Calling Over All Result Method
    	 	 	     
    	 	 	    String tResult=TotalResult(result,WS_Check_Result,DB_Result,rfv);
    	 	 	    
    	 	 	    System.out.println("******** ToTal Result : "+tResult);
    	 	 	    
    	 	 	  if(tResult.trim().toLowerCase().equalsIgnoreCase("pass")){
    	 	 		tResultColor="#046607";   //#228B22
     	 	 	     pcnt=pcnt+1;
     	 	 	     
     	 	 	    }
     	 	 	    else if(tResult.trim().toLowerCase().equalsIgnoreCase("fail")) {
     	 	 	    	tResultColor="#910202";   //Red
     	 	 	     fcnt=fcnt+1;
     	 	 	    }
     	 	 	    else if(tResult.trim().toLowerCase().contains(",")){
     	 	 	    	tResultColor="#000000";  //black
     	 	 	    }
     	 	 	    else {
     	 	 	    	tResultColor="#0a0a96";   //#0000FF
     	 	 	     scnt=scnt+1;
     	 	 	    }
    	 	 	     
    	 	 	     
    	 	 	     
    	 	 	    System.out.println(pcnt);
    	 	 	    
    	 	 	   // if(!result.equals("Failed"))
    	 	 	  // {
    	 	 	        
    	 	 	     TCHTMLBody=TCHTMLBody + "<tr> <td align=center >"+ TCaseID +"</td> <td>"+ fname[0] +" </td> <b><td align=center style=color:"+tResultColor+">" + tResult + "</td></b><b><td align=center style=color:"+resultColor+">" + result + "</td></b> <td align=center>" + startTime + "</td></b> <td align=center>" + executionTime + "</td><td align=center style=color:" + resultColor + ">" + Errorlog +"</td><td align=center style=color:" + WSresultColor + ">"+ WS_Check_Result +"</td><td align=center style=color:" + WSresultColor + ">"+ WS_Check_ErrorReason +"</td><td align=center style=color:" + DBresultColor + ">"+ DB_Result +"</td><td align=center style=color:" + rfvColor + ">"+ rfv +"</td><td align=center style=color:" + rfvColor + ">"+ rfvError +"</td></tr>";
    	 	 	     
    	 	 	     
    	 	 	  //   TCHTMLBody=TCHTMLBody + "<tr> <td align=center >"+ TCaseID +"</td> <td>"+ fname[0] +" </td> <b><td align=center style=color:"+resultColor+">" + result + "</td></b> <td align=center>" + startTime + "</td></b> <td align=center>" + executionTime + "</td><td align=center style=color:" + resultColor + ">" + Errorlog +"</td><td align=center style=color:" + WSresultColor + ">"+ WS_Check_Result +"</td><td align=center style=color:" + WSresultColor + ">"+ WS_Check_ErrorReason +"</td><td align=center style=color:" + DBresultColor + ">"+ DB_Result +"</td></tr>";
    	 	 	  //  } 
    	 	 	    
    	 	 	   
    	 	 	  }
    	 	 	      
    	 	 	 }
    	 	 		 
    	 	    }
    	 	    }
    	 	     

    	 		 tecnt=pcnt+fcnt+scnt+nacnt;
    	 		 
    	 		 System.out.println("Total Executed: "+ tecnt);
    	 		 System.out.println("Passed: "+ pcnt);
    	 		 System.out.println("Failed: "+ fcnt);
    	 		 System.out.println("Skipped: "+ scnt);
    	 		 System.out.println("multicommandoutput: "+ nacnt);
    	 		 
    	 		 TCPieChart tcp = new TCPieChart("TC Pie chart");
    	 		 
    	 		String HTMLHead="<HTML><HEAD><TITLE>Ericsson Automation Test Report as on " + calendar.getTime() + "</TITLE></HEAD><BODY> <Heading><p align=center><font face=TrebuchetMS size=4><B>Ericsson Automation Detailed Test Report as on "+ calendar.getTime() +  " </B></p></font></Heading><table WIDTH=100%><tr><td><img WIDTH=120 HEIGHT=65 src=file:///C:/Ericsson-PBTA_Base_Project_Latest/PBTA2.0/Framework-Src/Templates/EricssonImage.jpg alt=Ericsson></td><td><img align=RIGHT WIDTH=140 HEIGHT=65 src=file:///C:/Ericsson-PBTA_Base_Project_Latest/PBTA2.0/Framework-Src/Templates/STC_Logo.jpg alt=STC></td></table><P>";
    	 		 //String HTMLHead="<HTML><HEAD><TITLE>Ericsson Automation Test Report as on " + calendar.getTime() + "</TITLE></HEAD><BODY> <Heading><p align=center><font face=TrebuchetMS size=4><B>Ericsson Automation Detailed Test Report as on "+ calendar.getTime() +  " </B></p></font></Heading><table WIDTH=100%><tr><td><img WIDTH=120 HEIGHT=65 src=http://logosdatabase.com/logoimages/78615497.jpg alt=Ericsson></td><td><img align=RIGHT WIDTH=140 HEIGHT=65 src=file:///C:/Ericsson-PBTA_Base_Project_Latest/PBTA2.0/Framework-Src/Templates/STC_Logo.jpg alt=STC></td></table><P>";
//    	 		 String HTMLExeclog="<p><b>To see complete execution Log from "+ ipaddress + " machine: </b><a href=file:///" +resdir + "> <b>Click here</b> </a><br></p><p><b>To see complete execution Log from other than "+ ipaddress + " machine: </b><!--[if !IE]> --><a href=file:\\\\\\\\\\" + ExecResFolder + "> <![endif]--><!--[if !FF]><a href=file:\\\\" + ExecResFolder + "> <![endif]--><b>Click here</b> </a><br><b>Note: </b> The above Link will only open if you are in the same network</p><p><b> Log Details:</b></p><p><b>Machine ip :" + ipaddress + "</b></p><p><b>Log Path : " + resdir + "</b></p>";
    	 		 
    	 		InetAddress inetAddress = InetAddress.getLocalHost();
    	 		String IPaddress=inetAddress.getHostAddress();
    	 		
    	 		
    	 		 
    	 		 
    	 		String HTMLExeclog="<p><b>To see complete execution Log from "+ IPaddress + " machine: </b><a href=file:///" +resultPath+ "> <b>Click here</b> </a><br></p><p><b>To see complete execution Log from other than "+ IPaddress + " machine: </b><!--[if !IE]> --><a href=file:\\\\\\\\\\" + IPaddress+"\\Ericsson\\PBTA2.0\\Reports"+ "> <![endif]--><!--[if !FF]><a href=file:\\\\" + IPaddress+"\\Ericsson\\PBTA2.0\\Reports"+ "> <![endif]--><b>Click here</b> </a><br><b>Note: </b> The above Link will only open if you are in the same network</p><p><b> Log Details:</b></p><p><b>Machine ip :" + IPaddress + "</b></p><p><b>Log Path : " + resultPath + "</b></p>";
    	 		 String HTMLFoot="</BODY></HTML>";
    	 		 String TSuiteHead="";
    	 		 String TFHTMLBody = "";
    	 		 
    	 		 String TestDetails = "<h2>Test Execution Summary Report</h2> <table border=3 cellpadding=5 bgcolor=#C6DEFF ><tr> <th align=left >Total Cases:</th> <td align=center width =80 style=color:#000000><b>"+ tcnt + "</b></td></tr> <tr> <th align=left>Total Executed:</th> <td align=center width =80 style=color:#000000><b>" + tecnt + "</b></td></tr><tr> <th align=left >Skipped Cases:</th> <td align=center width =80 style=color:#000000><b>"+ scnt + "</b></td></tr> <tr> <th align=left>Passed:</th> <td align=center width =80 style=color:#387C44><b>"+ pcnt + "</b></td></tr><tr><th align=left>Failed:</th><td align=center width =80 style=color:red><b>"+ fcnt +"</b></td></tr><tr><th align=left>TotalExecutionTime:</th><td align=center width =80 style=color:#000000><b>"+ tet +"sec"+"</b></td></tr></table>";
    	 		 
    	 		 //String TestDetails = "<h2>Test Execution Summary Report</h2> <table border=3 cellpadding=5 bgcolor=#C6DEFF ><tr> <th align=left >Total Cases:</th> <td align=center width =80 style=color:#000000><b>"+ tcnt + "</b></td></tr> <tr> <th align=left>Total Executed:</th> <td align=center width =80 style=color:#000000><b>" + tecnt + "</b></td></tr><tr> <th align=left >Skipped Cases:</th> <td align=center width =80 style=color:#000000><b>"+ scnt + "</b></td></tr> <tr> <th align=left >NA Cases:</th> <td align=center width =80 style=color:#000000><b>"+ nacnt + "</b></td></tr> <tr> <th align=left>Passed:</th> <td align=center width =80 style=color:#387C44><b>"+ pcnt + "</b></td></tr><tr><th align=left>Failed:</th><td align=center width =80 style=color:red><b>"+ fcnt +"</b></td></tr><tr><th align=left>TotalExecutionTime:</th><td align=center width =80 style=color:#000000><b>"+ tet +"sec"+"</b></td></tr></table>";
    	 		 
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
    	 		    //finalstr = finalstr + "myChart.setXMLData(\"<chart showLegend='1' caption='Test Case Execution Status' useRoundEdges='1' bgColor='FFFFFF,FFFFFF' showBorder='0' baseFont='Calibri' baseFontSize ='18'><set label='Passed' value='"+pcnt+"' color='#2EFE2E'/><set label='Failed' value='"+fcnt+"' color='#FF0000'/><set label='Skipped' value='"+scnt+"' color='#0000FF'/><set label='NA' value='"+nacnt+"' color='#FFFF1E'/><styles> <definition><style name='CaptionFont' type='font' size='25'/></definition><application><apply toObject='Caption' styles='CaptionFont'/></application></styles></chart>\");myChart.render(\"chartdiv1\");</script>";
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
    	 		           System.out.println(lname[0]);
    	 		             
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
    	 		 		
    	 		static String readFileAsString(String filePath) throws java.io.IOException{

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
   public static void main(String[] args) throws Exception {
		
		
		htmlData();
		//System.out.println("executed GenerateResultsCopy");
	}
   
   
   public static String TotalResult(String wsResult,String wsCheck,String dbResult,String RFV) {
	   
	   String finalResult="";
	   
	   try {
		
	
	  // Multimap<String, String> multimap = ArrayListMultimap.create();
		   
		   ArrayList<String> TR= new ArrayList<String>();
		   
		   String[] ws=wsResult.split(",");
	   
		   ArrayList<String> wsR= new ArrayList<String>();
	   
	   if (wsResult.toLowerCase().contains("fail")) {
		   
		   wsR.add("Fail");
		
	}else if(wsResult.toLowerCase().contains("fail")&&wsResult.toLowerCase().contains("pass")){
		
		wsR.add("fail");
		
	}else{
		
		wsR.add("pass");
		
	}
	   
	   if (wsR.contains("fail")) {
			  
			  TR.add("Fail");
			  
			
		}  else{
			
			TR.add("Pass");
			
		}
	   
	   
	   String[] wsc=wsCheck.split(",");
	   
	   ArrayList<String> mWSC= new ArrayList<String>();
	   
	  
	   
	   for (int i = 0; i < wsc.length; i++) {
		   
		   if (wsc[i].toLowerCase().equalsIgnoreCase("pass")) {
			   
			   mWSC.add("pass");
			
		}else if (wsc[i].contains("Need to Provide Proper Data in WebService Sheet")) {
			
			mWSC.add("fail");
			
		}
		   
        else if (wsc[i].toLowerCase().equalsIgnoreCase("error")) {
			
			mWSC.add("fail");
			
		}
		   
      else if (wsc[i].toLowerCase().equalsIgnoreCase("NA")||wsc[i].toLowerCase().equalsIgnoreCase("N/A")) {
			
			mWSC.add("pass");
			
		}
		
	}
	   
	  if (mWSC.contains("fail")) {
		  
		  TR.add("Fail");
		  
		
	}  else{
		
		TR.add("Pass");
		
	}
	  
	  
	  
	  String[] db=dbResult.split(",");
	   
	   ArrayList<String> DBR= new ArrayList<String>();
	   
	  
	   
	   for (int i = 0; i < db.length; i++) {
		   
		   if (db[i].trim().toLowerCase().contains("pass")) {
			   
			   DBR.add("pass");
			
		}else if (db[i].contains("Need to provide SQlQueryFlag Yes/No")||db[i].contains("Usecase DB data in DataBase sheet")) {
			
			DBR.add("fail");
			
		}
		   
       else if (db[i].trim().toLowerCase().contains("error")) {
			
    	   DBR.add("fail");
			
		}
		   
     else if (db[i].trim().toLowerCase().equalsIgnoreCase("NA")||db[i].toLowerCase().equalsIgnoreCase("N/A")) {
			
    	 DBR.add("pass");
			
		}
		
	}
	  
	   if (DBR.contains("fail")) {
			  
			  TR.add("Fail");
			  
			
		}  else{
			
			TR.add("Pass");
			
		}
	   
	   
	   
    String[] rf=RFV.split(",");
	   
	   ArrayList<String> RFR= new ArrayList<String>();
	   
	  
	   
	   /*for (int i = 0; i < rf.length; i++) {
		   
		   if (rf[i].toLowerCase().contains("pass")) {
			   
			   RFR.add("pass");
			
		}else if (rf[i].trim().toLowerCase().contains("pass")&&rf[i].trim().toLowerCase().contains("fail")) {
			
			RFR.add("fail");
			
		}
		   
		else if (rf[i].trim().toLowerCase().equalsIgnoreCase("fail")) {
			
    	   RFR.add("fail");
			
		}
		   
		else if (rf[i].trim().toLowerCase().equalsIgnoreCase("NA")||rf[i].trim().toLowerCase().equalsIgnoreCase("N/A")||rf[i].trim().toLowerCase().equalsIgnoreCase("")) {
			
    	 RFR.add("pass");
			
		}
		
		
	}*/
	   if(RFV.contains("Fail")|| RFV.contains("fail"))
	   {
		   RFR.add("fail");
	   }else if (RFV.trim().toLowerCase().equalsIgnoreCase("NA")||RFV.trim().toLowerCase().equalsIgnoreCase("N/A")||RFV.trim().toLowerCase().equalsIgnoreCase(""))
	   {
		   RFR.add("pass");
	   }else
	   {
		   RFR.add("pass");
	   }
	  
	   if (RFR.contains("fail")) {
			  
			  TR.add("Fail");
			  
			
		}  else{
			
			TR.add("Pass");
			
		}
		  
	   
	   
	   
		  
		  
	  
	   if (TR.contains("Fail")) {
		   
		   finalResult="Fail";
		
	}else{
		
		finalResult="Pass";
		
		
	}
	   
	  // return finalResult;
	   
	   
	   
	   } catch (Exception e) {
			
		   System.out.println("ERROR IN OverAll Result : "+e);
		}
	return finalResult;
	   
	 
	   
	
	
}
   
  // TO WRITE REMOTE VALIDATION RESULT TO USE CASE SHEET 
   
   public static void writeToUcaseExcel() throws Exception {
	   
	   Multimap<String, String> multimapResult = ArrayListMultimap.create();
	   Multimap<String, String> multimapError = ArrayListMultimap.create();
	   
	   String path="..\\..\\Reports\\Results";
	    File dir = new File(path);
	    File[] files = dir.listFiles();
	    System.out.println("## Total Files : "+files.length);
	   
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
	    	     System.out.println("File Name : "+fname[0]);
	    	     
	    	     String[] um=fname[0].split("_");
	    	     
	    	     
	    	     
	    	     
	    	        ExcelFileUtil remoteExcel=new ExcelFileUtil("..\\..\\Reports\\Results\\RFV\\RemotefileValidationResult.xls","");
	    		   
	    		   ExcelFileUtil UsecaseExcel=new ExcelFileUtil("..\\..\\Reports\\Results\\"+fname[0]+".xls","..\\..\\Reports\\Results\\"+fname[0]+".xls");
	    		   
	    		   for (int i = 1; i <=UsecaseExcel.rowCount("Sheet1"); i++) {
	    			   
	    			   if (UsecaseExcel.getRow("Sheet1", i)!=null) {
	    				   
	    				   if (UsecaseExcel.getcell("Sheet1", i, 0)!=null) {
	    					   
	    					   UsecaseExcel.setData("Sheet1", i, 9, "NA");
	    					   UsecaseExcel.setData("Sheet1", i, 10, "NA");
							
						}
	    				   
	    				   
						
					}
	    			   
	    			  
					
				}
	    		   
	    		   int repeat=0;
	    		   ArrayList<String> remoteR= new ArrayList<String>();
				   ArrayList<String> remoteE= new ArrayList<String>();
	    		   
	    		   for (int i = 1; i <=remoteExcel.rowCount("Sheet1"); i++) {
	    			   
	    			  
	    			  if (remoteExcel.getRow("Sheet1", i)!=null) {
	    				  
	    				  if (remoteExcel.getData("Sheet1", i, 0)==null&&remoteExcel.getData("Sheet1", i, 1)==null&&remoteExcel.getData("Sheet1", i, 2)==null) {
							
	    					  break;
						}
	    				  
	    				   String remoteUsecase=remoteExcel.getData("Sheet1", i, 0).trim();
		    			   String remoteModule=remoteExcel.getData("Sheet1", i, 1).trim();
		    			   String remoteTestcase=remoteExcel.getData("Sheet1", i, 2).trim();
		    			   String remoteResult=remoteExcel.getData("Sheet1", i, 3).trim();
		    			   String remoteError=remoteExcel.getData("Sheet1", i, 6).trim();
		    			   
		    			  
		    			   
		    			   for (int j = 1; j <=UsecaseExcel.rowCount("Sheet1"); j++) {
		    				   
		    				   if (UsecaseExcel.getRow("Sheet1", j)!=null) {
		    					   
		    					   
		    					   if (UsecaseExcel.getData("Sheet1", j, 0)==null) {
			    					   
				    					  break;
			    					   
			    				   }
			    					   
			    						
					    				   String testCase=UsecaseExcel.getData("Sheet1", j, 0).trim();
					    				   
					    				   
					    				
					    				   if (remoteTestcase.equalsIgnoreCase(testCase)) {
					    					   
					    					   repeat=++repeat;
					    					   
					    					  // remoteR.add(remoteResult);
					    					  // remoteE.add(remoteError);
					    					   
					    					  // System.out.println("Test Case Repetation : "+repeat);
											
										}
					    				   
					    				   
					    				   if (um[0].trim().equalsIgnoreCase(remoteUsecase)&&um[1].trim().equalsIgnoreCase(remoteModule)&&testCase.equalsIgnoreCase(remoteTestcase)&&(remoteUsecase!=null)&&(remoteModule!=null)&&(remoteTestcase!=null)&&(remoteTestcase.equalsIgnoreCase(testCase))) {
					    					   
					    					   
					    					  // multimap.put(remoteTestcase, remoteResult);
					    					   
					    					 //  remoteR.add(remoteResult);
					    					 //  remoteE.add(remoteError);
					    					   
					    					   multimapResult.put(remoteTestcase, remoteResult+"|");
					    					   
					    					   multimapError.put(remoteTestcase, remoteError+"|");
					    					   
					    					  // System.out.println("#########"+remoteR.toString());
					    					   
					    					  // remoteR.add(remoteResult);
					    					  // remoteE.add(remoteError);
					    					   
					    					  // UsecaseExcel.setData("Sheet1", j, 9, remoteResult);
					    					  // UsecaseExcel.setData("Sheet1", j, 10, remoteError);
					    					   
					    					//   if (repeat>1) {
					    						   
					    						   
						    					   
						    					 //  UsecaseExcel.setData("Sheet1", j, 9, remoteR.toString());
						    					  // UsecaseExcel.setData("Sheet1", j, 10, remoteE.toString());
												
											//}
					    					   
					    					
					    				}
								
							}
		    				   
		    				   
								
							
		    				  
		    				   
		    				   
		    			
						
					}
	    			   
	    			 
		    			   System.out.println("Test Case Repetation : "+repeat);
	    				   
	    				
	    			}
	    			  
	    			  
	    			  
	    			  /*for (int k = 1; k <=UsecaseExcel.rowCount("Sheet1"); k++) {
		    			   
		    			   if (UsecaseExcel.getRow("Sheet1", i)!=null) {
		    				   
		    				   if (UsecaseExcel.getcell("Sheet1", k, 0)==null) {
		    					   UsecaseExcel.setData("Sheet1", k, 9, remoteR.toString());
				    			   UsecaseExcel.setData("Sheet1", k, 10, remoteE.toString());
								
							}
		    				   
		    				   
							
						}
		    			    
		    			   
						
					}*/
	    			  
	    			  
	    				
	    			}
	    	     
	    		   
	    		   for (int i = 1; i <=UsecaseExcel.rowCount("Sheet1"); i++) {
	    			   
	    			   if (UsecaseExcel.getRow("Sheet1", i)!=null) {
	    				   if (UsecaseExcel.getcell("Sheet1", i, 0)!=null) {
	    					   
	    					String tc=UsecaseExcel.getData("Sheet1", i, 0);
	    					
	    					try {
	    						
	    						String rr=multimapResult.get(tc).toString();
	    						rr=rr.replaceAll("\\[", "");
	    						rr=rr.replaceAll("\\]", "");
	    						rr=rr.substring(0, rr.length()-1);
	    						rr=rr.replaceAll("\\|,", " \\|");
	    						if (rr.equalsIgnoreCase("")) {
	    							UsecaseExcel.setData("Sheet1", i, 9, "NA");
									
								}else{
	    						
	    						UsecaseExcel.setData("Sheet1", i, 9, rr);
								}
							} catch (Exception e) {
							      System.out.println("##############   Sheet result Final : "+e);
							}
	    					
	    					try {
	    						String re=multimapError.get(tc).toString();
	    						re=re.replaceAll("\\[", "");
	    						re=re.replaceAll("\\]", "");
	    						re=re.substring(0, re.length()-1);
	    						re=re.replaceAll("\\|,", " \\|");
	    						if (re.equalsIgnoreCase("")) {
	    							
	    							UsecaseExcel.setData("Sheet1", i, 10, "NA");
								}else{
	    						
	    						UsecaseExcel.setData("Sheet1", i, 10, re);
								}
								
							} catch (Exception e) {
							      System.out.println("##############   Sheet Error Final: "+e);
							}
	    					   
			    			   
							
							
						}
	    				   
	    				   
					}
	    			   
	    			   
					
				} 
	    	     
	    		  // System.out.println("Test Case Repetation : "+repeat);
	    	     
	    	}
	    	
	    }
	   
	
		   
		   
			
		}
		   
		
	}



