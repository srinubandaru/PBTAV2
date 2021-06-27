package ericssonframework;

 

import com.jcraft.jsch.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

  public class SSHManager
       {
	    private static final Logger LOGGER =
	    	    Logger.getLogger(SSHManager.class.getName());
	    	    private JSch jschSSHChannel;
	    	    private String strUserName;
	    	    private String strConnectionIP;
	    	    private int intConnectionPort;
	    	    private String strPassword;
	    	    private Session sesConnection;
	    	    private int intTimeOut;
	    	    static FileInputStream inputStream;
	    	    static HSSFWorkbook workbook;
	    	    static HSSFSheet TCMsheet;
	    	    static FileReader filereader;
	    	    static BufferedReader bufferedreader;
	    	    static String[] headerData;
	    	    static String[] reqCommands;
	    	    static String[] parametersTovalidate;
	    	    static String flatFileheaderData;
	    	    static String flatfileValuedata;
	    	    static String[] flatfileData;
	    	    static String reqcommand;
	    	    static String valueToValidate;
	    	    static Calendar calendar1 = Calendar.getInstance();
	    	    static long startTime; 
	    	    
	    	    
	    	    public static String buildCommand(String[] headerData, String reqCommand, String[] flatfileData )
	    	    {
	    	        String finalCommand="";
	    	        for(int hd=0; hd<headerData.length; hd++)
	    	        {
	    	            if(reqCommand.contains(headerData[hd]))
	    	            {
	    	            	System.out.println("Build command method started....");
	    	                reqCommand=reqCommand.replace("H"+":"+headerData[hd], flatfileData[hd]);
	    	                
	    	            }
	    	        }
	    	        finalCommand=reqCommand;
	    	        return finalCommand;
	    	    }
	    	    
	    	    public static String setParameterValue(String[] headerData, String reqCommand, String[] flatfileData )
	    	    {
	    	        String value="";
	    	        for(int hd=0; hd<headerData.length; hd++)
	    	        {
	    	            if(reqCommand.contains(headerData[hd]))
	    	            {
	    	                reqCommand=reqCommand.replace("h"+":"+headerData[hd], flatfileData[hd]);
	    	                
	    	            }
	    	        }
	    	        value=reqCommand;
	    	        return value;
	    	    }
	    	    
	    		public static ArrayList<String> parametersdata(String[] headerData, String reqCommand, String[] flatfileData )
	    	    {
	    	        ArrayList<String> datavalues = new ArrayList<>();
	    	        //System.out.println("datavalues list intially:  "+datavalues.size());
	    	        for(int hd=0; hd<headerData.length; hd++)
	    	        {
	    	            if(reqCommand.contains(headerData[hd]))
	    	            {
	    	                datavalues.add(flatfileData[hd]);
	    	                
	    	            }
	    	        }
	    	        //finalCommand=reqCommand;
	    	        //System.out.println("datavalues are: "+datavalues);
	    	        //System.out.println(datavalues.toString());
	    	        return datavalues;
	    	        
	    	    }
	    	    
	    	    
	    	    public static String commandValues(String[] headerData, String reqCommand, String[] flatfileData )
	    	    {
	    	        String parameterToValidate="";
	    	        //System.out.println(reqCommand);
	    	        String[] parametersList=reqCommand.split("\"");
	    	        /*for(String a: parametersList)
	    	        {
	    	            System.out.println(a);
	    	        }*/
	    	        for(int pl=0;pl<parametersList.length;pl++)
	    	        {
	    	            for(int hd=0; hd<headerData.length; hd++)
	    	            {
	    	                if(parametersList[pl].contains(headerData[hd]))
	    	                {
	    	                    //finalCommand=reqCommand=reqCommand.replace("h:"+headerData[hd], flatfileData[hd]);
	    	                    parameterToValidate=parameterToValidate+flatfileData[hd]+" ";
	    	                }
	    	            }
	    	        }
	    	        return parameterToValidate;
	    	    }
	    	    
	    	    public static void flatfileReader() throws IOException
	    	    {
	    	        String errorText="no such file or directory";
	    	        try
	    	        {
	    	            inputStream=new FileInputStream("..//..//TestInputs//MasterSheet_New.xls");
	    	            workbook=new HSSFWorkbook(inputStream);
	    	            TCMsheet=workbook.getSheet("TestCaseMaster");
	    	            int TCMrowCount=TCMsheet.getLastRowNum();
	    	            
	    	            FileInputStream refOutputsheet=new FileInputStream("..//..//TestInputs//RemotefilevalidationTemplate.xls");
	    	            HSSFWorkbook refworkbook=new HSSFWorkbook(refOutputsheet);
	    	            HSSFSheet outputSht=refworkbook.getSheet("Sheet1");
	    	            Row outputSheetRow = null;
	    	            int rowcnt=1;
	    	            for(int i=1; i<=TCMrowCount; i++)
	    	            {
	    	                Row TCMrow=TCMsheet.getRow(i);
	    	                if(TCMrow!=null)
	    		                {
	    	                	String TCMUsecaseName = null, TCMModuleName = null, TCMExecutionFlag = null, TCMFlatfileFlag = null;
	    		                	if(TCMrow.getCell(0)!=null)
	    		                	{
	    		                		TCMUsecaseName=TCMrow.getCell(0).getStringCellValue().trim();   
	    		                	}
	    		                	if(TCMrow.getCell(1)!=null)
	    		                	{
	    		                		TCMModuleName=TCMrow.getCell(1).getStringCellValue().trim(); 
	    		                	}
	    		                	if(TCMrow.getCell(4)!=null)
	    		                	{
	    		                		TCMExecutionFlag=TCMrow.getCell(4).getStringCellValue().trim();   
	    		                	}
	    		                	if(TCMrow.getCell(5)!=null)
	    		                	{
	    		                		TCMFlatfileFlag=TCMrow.getCell(5).getStringCellValue().trim();
	    		                	}
	    			                             		    
	    		                try
	    		                {
	    		                    if(TCMExecutionFlag.equalsIgnoreCase("yes") && TCMFlatfileFlag.equalsIgnoreCase("Yes") && TCMExecutionFlag!=null && TCMFlatfileFlag!=null)
	    		                    {
	    		                        //RemoteFileValidation---> redirecting to remote file validation sheet
	    		                        HSSFSheet RFVsheet=workbook.getSheet("RemoteFileValidation");
	    		                        int RFVrowCount= RFVsheet.getLastRowNum();	                        
	    		                        startTime = calendar1.getTimeInMillis();
	    		                        for(int j=1; j<=RFVrowCount; j++)
	    		                        {
	    		                            Row RFVrow=RFVsheet.getRow(j);
	    		                            String RFVUsecaseName=RFVrow.getCell(0).getStringCellValue().trim();
	    		                            String RFVModuleName=RFVrow.getCell(1).getStringCellValue().trim();
	    		                            if(RFVUsecaseName.equalsIgnoreCase(TCMUsecaseName) && RFVModuleName.equalsIgnoreCase(TCMModuleName)
	    		                            		&& RFVUsecaseName!=null && RFVModuleName!=null && RFVUsecaseName!="" && RFVModuleName!="")
	    		                            {
	    		                            	String FlatfilePath=TCMrow.getCell(11).getStringCellValue().trim();
	    		                            	System.out.println(FlatfilePath);
	    		                            	filereader=new FileReader(FlatfilePath);
	    		                                bufferedreader=new BufferedReader(filereader);
	    		                                flatFileheaderData=bufferedreader.readLine();
	    		                                flatFileheaderData=flatFileheaderData.toLowerCase();
	    		    
	    		    
	    		                                String connectionIP = RFVrow.getCell(2).getStringCellValue();
	    		                                String[] multiServers=connectionIP.split("\\|");
	    		                                String userName= RFVrow.getCell(3).getStringCellValue();
	    		                                String password = RFVrow.getCell(4).getStringCellValue();
	    		                                String filePath = RFVrow.getCell(5).getStringCellValue();
	    		                                String cmdValidationFlag = RFVrow.getCell(6).getStringCellValue();
	    		                                
	    		                                
	    		                                if(cmdValidationFlag.equalsIgnoreCase("Yes"))
	    		                                {
	    		                                	flatfileValuedata=bufferedreader.readLine();
	    		                                    while(flatfileValuedata!=null)
	    		                                    {
	    		                                    	//ArrayList<String> commandValues = new ArrayList<String>();
	    		    	                                //ArrayList<String> commandResult = new ArrayList<String>();
	    		    	                                ArrayList<String> ErrorReasons = new ArrayList<String>();
	    		    	                                ArrayList<String> paramsFromCommand = new ArrayList<String>();
	    		    	                                //ArrayList<String> serverCommandResult=new ArrayList<String>();
	    		                                    	outputSheetRow=outputSht.createRow(rowcnt);
	    		                                    	outputSheetRow.createCell(0).setCellValue(RFVUsecaseName);
	    		    	                                outputSheetRow.createCell(1).setCellValue(RFVModuleName);
	    		    	                                outputSheetRow.createCell(7).setCellValue(" ");
	    		    	                                outputSheetRow.createCell(8).setCellValue(" ");
	    		    	                                outputSheetRow.createCell(9).setCellValue(" ");
	    		                                        flatfileData=flatfileValuedata.split("\\|");
	    		                                        outputSheetRow.createCell(2).setCellValue(flatfileData[0]);
	    		                                        String CommandStr=RFVrow.getCell(8).getStringCellValue();
	    		                                        reqCommands=CommandStr.split("\\|\\|\\|");
	    		                                        System.out.println("No of commands to execute :"+reqCommands.length);
	    		                                        headerData=flatFileheaderData.split("\\|");
	    		                                        ArrayList<String> serverCommandResult=new ArrayList<String>();
	    		                                        for(int cmndlen=0; cmndlen<reqCommands.length; cmndlen++)
	    		                                        {
	    		                                            reqcommand=reqCommands[cmndlen];
	    		                                            System.out.println("Command to execute: "+reqcommand);
	    		                        
	    		                                            //if(reqcommand.contains("h:")||reqcommand.contains("H:"))
	    		                                            //{
	    		                                                String command=buildCommand(headerData,reqcommand,flatfileData);
	    		                                                System.out.println(command);
	    		                                                List<String> dataTotest=parametersdata(headerData,reqcommand,flatfileData);
	    		                                                System.out.println(dataTotest);
	    		                                                ArrayList<String> commandValues = new ArrayList<String>();
	    		                                                ArrayList<String> commandResult = new ArrayList<String>();
	    		                                                for(int serverCount=0; serverCount<multiServers.length; serverCount++)
	    		                                                {
	    		                                                    String commandParameters=commandValues(headerData,reqcommand,flatfileData);
	    		                                                    System.out.println(commandParameters);
	    		                                                    SSHManager instance= new SSHManager(userName, password, multiServers[serverCount], "");
	    		                                                    String errorMessage = instance.connect();
	    		                                                   
	    		                                                    //String errorMessage =null;
	    		                                                    if(errorMessage != null)
	    		                                                    {
	    		                                                        System.out.println("Check 2 "+errorMessage);
	    		                                                        String serverError=errorMessage;
	    		                                                        //fail();
	    		                                                        //outputSheetRow.createCell(3).setCellValue("Fail");
	    		                	        							//outputSheetRow.createCell(6).setCellValue(multiServers[serverCount]+" "+serverError);
	    		                	        							commandValues.add("Fail");
	    	                                                			commandResult.add(multiServers[serverCount]+": "+serverError);
	    		                                                        
	    		                                                    }
	    		                                                    else
	    		                                                    {
	    			                                                    String expResult = "FILE_NAME\n";
	    			                                                    String finalCommand="cd /"+filePath+"; "+command;
	    			                                                    String result = instance.sendCommand(finalCommand);
	    			                                                    //String result = "966556151261test66xyz";
	    			                                                    result=result.replaceAll("\n", "");
	    			                                                    //adding command to the result sheet
	    			                                                    System.out.println("Output of the given command is:" + result);
	    			                                                    System.out.println("Length of the result: "+result.length());
	    			                                                    
	    			                                                    
	    			                                                    if(!(result.toLowerCase().contains(errorText)) && result.length()!=0)
	    			                                                    {
	    			                                                    	if(command.contains("|") && dataTotest.size()>0)
	    			                                                    	{
	    			                                                    		ArrayList<String> internalResult=new ArrayList<String>();	                                                    		                        		
	    			                                                    		for(int len=0; len<dataTotest.size(); len++)
	    			                                                    		{
	    			                                                    			if(result.contains(dataTotest.get(len)))
	    			                                                    			{	                                                    				
	    			                                                    				internalResult.add("Pass");
	    			                                                    			}else{
	    			                                                    				internalResult.add("Fail");
	    			                                                    				commandResult.add("Required value "+ dataTotest.get(len) + " not found...");
	    			                                                    			}
	    			                                                    		}
	    			                                                    		if(internalResult.contains("Fail"))
	    			                                                    		{
	    			                                                    			commandValues.add("Fail");
	    			                                                    			
	    			                                                    		}else{
	    			                                                    			commandValues.add("Pass");
	    			                                                    			commandResult.add(result);
	    			                                                    		}
	    			                                                    		
	    			                                                    	}else if(command.contains(";") && dataTotest.size()>0)
	    			                                                    	{
	    			                                                    		ArrayList<String> internalResult=new ArrayList<String>();	                                                    		                        		
	    			                                                    		for(int len=0; len<dataTotest.size(); len++)
	    			                                                    		{
	    			                                                    			if(result.contains(dataTotest.get(len)))
	    			                                                    			{	                                                    				
	    			                                                    				internalResult.add("Pass");
	    			                                                    			}else{
	    			                                                    				internalResult.add("Fail");
	    			                                                    				//commandResult.add("Required value "+ dataTotest.get(len) + " not found...");
	    			                                                    			}
	    			                                                    		}
	    			                                                    		if(internalResult.contains("Pass"))
	    			                                                    		{
	    			                                                    			commandValues.add("Pass");
	    			                                                    			commandResult.add(result);
	    			                                                    		}else{
	    			                                                    			commandValues.add("Fail");
	    			                                                    			commandResult.add("Required values "+dataTotest+" not found....");
	    			                                                    		}
	    			                                                    	}else if(dataTotest.size()>0 && (!command.contains(";")|| !command.contains("|")))
	    			                                                    	{
	    			                                                    		ArrayList<String> internalResult=new ArrayList<String>();
	    			                                                    		ArrayList<String> ResultError=new ArrayList<String>();
	    			                                                    		for(int len=0; len<dataTotest.size(); len++)
	    			                                                    		{
	    			                                                    			if(result.contains(dataTotest.get(len)))
	    			                                                    			{	                                                    				
	    			                                                    				internalResult.add("Pass");
	    			                                                    			}else{
	    			                                                    				internalResult.add("Fail");
	    			                                                    				ResultError.add(dataTotest.get(len) );
	    			                                                    			}
	    			                                                    		}
	    			                                                    		if(internalResult.contains("Pass"))
	    			                                                    		{
	    			                                                    			commandValues.add("Pass");
	    			                                                    			commandResult.add(result);
	    			                                                    		}else{
	    			                                                    			commandValues.add("Fail");
	    			                                                    			commandResult.add("Required values "+ResultError+" not found....");
	    			                                                    		}
	    			                                                    	}
	    			                                                    	
	    			                                                    	else{
	    			                                                    		
	    			                                                    		commandValues.add("Pass");
	    		                                                    			commandResult.add(result);
	    			                                                    	}
	    			                                                        
	    			                                                    }
	    			                                                    else if(result.toLowerCase().contains("command not found"))
	    			                                                    {
	    			                                                        commandValues.add("Fail");
	    			                                                        //commandResult.add(command+" command execution failed....");
	    			                                                        commandResult.add(result);
	    			                                                        
	    			                                                    }
	    			                                                    else
	    			                                                    {
	    			                                                        commandValues.add("Fail");
	    			                                                        //commandResult.add(command+" command execution failed....");
	    			                                                        commandResult.add(/*command+*/" *****Found result as empty*****");
	    			                                                        
	    			                                                    }
	    			                                                    // close only after all commands are sent
	    			                                                    instance.close();
	    		                                                    }
	    		                                                }
	    		                                                paramsFromCommand.add(command);
	    		                                            System.out.println("Command Values List: "+commandValues);
	    		                        
	    		                                            //consolidating servers and command results
	    		                                            if(commandValues.contains("Pass"))
	    		                                            {
	    		                                                serverCommandResult.add("Pass");
	    		                                            }
	    		                                            else
	    		                                            {
	    		                                                serverCommandResult.add("Fail");
	    		                                            }
	    		                                            for(int err=0; err<commandResult.size(); err++)
	    		                                            {
	    		                                            	ErrorReasons.add(commandResult.get(err));
	    		                                            }
	    		                                            
	    		                                            ErrorReasons.add("|||");
	    		                                        }
	    		                                        //break;
	    		                                        flatfileValuedata=bufferedreader.readLine();
	    		                                        StringBuilder CmndRsltStr = new StringBuilder();
	    			        							for (String cmd_Rslts : ErrorReasons)
	    			        							{
	    			        								CmndRsltStr.append(cmd_Rslts);
	    			        								CmndRsltStr.append(", ");
	    			        								}
	    			        							String final_cmd_Rslts=CmndRsltStr.toString();
	    			        							final_cmd_Rslts=final_cmd_Rslts.replace(", |||, ", "|||");
	    			        							final_cmd_Rslts=final_cmd_Rslts.substring(0, final_cmd_Rslts.length()-3);
	    			        							System.out.println("Final Result :"+final_cmd_Rslts);
	    			        							
	    			        							StringBuilder CmndstrBldr = new StringBuilder();
	    			        							for (String cmd_vldtnResult : serverCommandResult)
	    			        							{
	    			        								CmndstrBldr.append(cmd_vldtnResult);
	    			        								CmndstrBldr.append(",");
	    			        								}
	    			        							String serverCommandResultstatus=CmndstrBldr.toString();
	    			        							serverCommandResultstatus=serverCommandResultstatus.substring(0, serverCommandResultstatus.length()-1);
	    			        							
	    			        							outputSheetRow.createCell(3).setCellValue(serverCommandResultstatus);
	    			        							outputSheetRow.createCell(6).setCellValue(final_cmd_Rslts);
	    			        							//outputSheetRow.createCell(6).setCellValue(ErrorReasons.toString());
	    			        							System.out.println("######  "+serverCommandResultstatus);
	    			        							
	    			        							
	    			        							
	    			        							Calendar calendar2 = Calendar.getInstance();
	    			        						    long endTime = calendar2.getTimeInMillis();
	    			        						     
	    			        						    long diff = endTime - startTime;
	    			        						    long TimeDiff = diff / 1000;
	    			        						    System.out.println("time difference: "+diff);
	    			        						    outputSheetRow.createCell(5).setCellValue(TimeDiff+" Sec");
	    			        						     	
	    			        							Date currentDate=new Date(startTime);
	    			        							DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    			        							outputSheetRow.createCell(4).setCellValue(df.format(currentDate));
	    			        						     	
	    			        							FileOutputStream fout=new FileOutputStream("..\\..\\Reports\\Results\\RFV\\RemotefileValidationResult.xls");
	    			        							
	    			        							refworkbook.write(fout);
	    			        							fout.close();
	    			        							System.out.println("check1");
	    			        							rowcnt=rowcnt+1;
	    		                                    }	                                    
	    		                                }
	    		                                else if(cmdValidationFlag.equalsIgnoreCase("No"))
	    		                                {
	    		                                	//Code for command validation flag is no

	    		                                	flatfileValuedata=bufferedreader.readLine();
	    		                                    while(flatfileValuedata!=null)
	    		                                    {
	    		                                    	//ArrayList<String> commandValues = new ArrayList<String>();
	    		    	                                //ArrayList<String> commandResult = new ArrayList<String>();
	    		    	                                ArrayList<String> ErrorReasons = new ArrayList<String>();
	    		    	                                ArrayList<String> validationValues = new ArrayList<String>();
	    		    	                                ArrayList<String> paramsFromCommand = new ArrayList<String>();
	    		    	                                ArrayList<String> serverCommandResult=new ArrayList<String>();
	    		    	                                outputSheetRow=outputSht.createRow(rowcnt);
	    		                                    	outputSheetRow.createCell(0).setCellValue(RFVUsecaseName);
	    		    	                                outputSheetRow.createCell(1).setCellValue(RFVModuleName);
	    		    	                                outputSheetRow.createCell(7).setCellValue(" ");
	    		    	                                outputSheetRow.createCell(8).setCellValue(" ");
	    		    	                                outputSheetRow.createCell(9).setCellValue(" ");
	    		                                        flatfileData=flatfileValuedata.split("\\|");
	    		                                        outputSheetRow.createCell(2).setCellValue(flatfileData[0]);
	    		                                        flatfileData=flatfileValuedata.split("\\|");
	    		                                        outputSheetRow.createCell(2).setCellValue(flatfileData[0]);
	    		                                        String parameters=RFVrow.getCell(7).getStringCellValue();
	    		                                        String fileName=RFVrow.getCell(9).getStringCellValue();
	    		                                        parametersTovalidate=parameters.split("\\|");
	    		                                        System.out.println("No of parameters to validate from file :"+parametersTovalidate.length);
	    		                                        headerData=flatFileheaderData.split("\\|");
	    		                                        for(int parameterslen=0; parameterslen<parametersTovalidate.length; parameterslen++)
	    		                                        {
	    		                                        	valueToValidate=parametersTovalidate[parameterslen];
	    		                                        	String valueafterReplacement=setParameterValue(headerData,valueToValidate,flatfileData);
	    		                                        	System.out.println("valueafterReplacement : "+valueafterReplacement);
	    		                        
	    		                                            //if(reqcommand.contains("h:")||reqcommand.contains("H:"))
	    		                                            //{
	    		                                                String command="cd /"+filePath+"; grep "+"\""+valueafterReplacement+"\" "+fileName;
	    		                                                System.out.println(command);
	    		                                                List<String> dataTotest=parametersdata(headerData,valueToValidate,flatfileData);
	    		                                                System.out.println("dataTotest:"+dataTotest);
	    		                                                ArrayList<String> commandValues = new ArrayList<String>();
	    		                                                ArrayList<String> commandResult = new ArrayList<String>();
	    		                                                for(int serverCount=0; serverCount<multiServers.length; serverCount++)
	    		                                                {
	    		                                                    //String commandParameters=commandValues(headerData,valueToValidate,flatfileData);
	    		                                                    //System.out.println("commandParameters: "+commandParameters);
	    		                                                	SSHManager instance= new SSHManager(userName, password, multiServers[serverCount], "");
	    		                                                    String errorMessage = instance.connect();
	    		                                                    //String errorMessage = null;
	    		                                                    if(errorMessage != null)
	    		                                                    {
	    		                                                        System.out.println("Check 2 "+errorMessage);
	    		                                                        String serverError=errorMessage;
	    		                                                        //fail();
	    		                                                        outputSheetRow.createCell(3).setCellValue("Fail");
	    		                	        							outputSheetRow.createCell(6).setCellValue(multiServers[serverCount]+" "+serverError);
	    		                	        							commandValues.add("Fail");
	    	                                                			commandResult.add(multiServers[serverCount]+" "+serverError);
	    		                                                        
	    		                                                    }
	    		                                                    else
	    		                                                    {
	    			                                                    String expResult = "FILE_NAME\n";
	    			                                                    String finalCommand=command;
	    			                                                    String result = instance.sendCommand(finalCommand);
	    			                                                    //String result = "966556151261test66xyz";
	    			                                                    result=result.replaceAll("\n", "");
	    			                                                    //adding command to the result sheet
	    			                                                    System.out.println("Output of the given command is:" + result);
	    			                                                    System.out.println("Length of the result: "+result.length());
	    			                                                    
	    			                                                    
	    			                                                    if(!(result.toLowerCase().contains(errorText)) && result.length()!=0)
	    			                                                    {
	    			                                                    	
	    			                                                    	if(dataTotest.size()>0)
	    			                                                    	{
	    				                                                    	ArrayList<String> internalResult=new ArrayList<String>();	                                                    		                        		
	    			                                                    		for(int len=0; len<dataTotest.size(); len++)
	    			                                                    		{
	    			                                                    			if(result.contains(dataTotest.get(len)))
	    			                                                    			{	                                                    				
	    			                                                    				internalResult.add("Pass");
	    			                                                    			}else{
	    			                                                    				internalResult.add("Fail");
	    			                                                    				commandResult.add("Required value "+ dataTotest.get(len) + " not found...");
	    			                                                    			}
	    			                                                    		}
	    			                                                    		if(internalResult.contains("Fail"))
	    			                                                    		{
	    			                                                    			commandValues.add("Fail");
	    			                                                    			
	    			                                                    		}else{
	    			                                                    			commandValues.add("Pass");
	    			                                                    			commandResult.add(result);
	    			                                                    		}
	    			                                                    	}
	    			                                                    	else if(dataTotest.size()==0)
	    				                                                    {
	    				                                                    	commandValues.add("Fail");
	    				                                                        commandResult.add("Please provide valid header value in the master sheet....");
	    				                                                    }
	    			                                                    	
	    			                                                    }
	    			                                                    else
	    			                                                    {
	    			                                                        commandValues.add("Fail");
	    			                                                        commandResult.add(command+" command execution failed....");
	    			                                                        
	    			                                                    }
	    			                                                    // close only after all commands are sent
	    			                                                    instance.close();
	    		                                                    }
	    		                                                }
	    		                                                paramsFromCommand.add(command);
	    		                                            
	    		                        
	    		                                            //consolidating servers and command results
	    		                                            if(commandValues.contains("Fail"))
	    		                                            {
	    		                                                serverCommandResult.add("Fail");
	    		                                            }
	    		                                            else
	    		                                            {
	    		                                                serverCommandResult.add("Pass");
	    		                                            }
	    		                                            for(int err=0; err<commandResult.size(); err++)
	    		                                            {
	    		                                            	ErrorReasons.add(commandResult.get(err));
	    		                                            }
	    		                                            
	    		                                            ErrorReasons.add("|||");
	    		                        
	    		                                        }
	    		                                        //break;
	    		                                        flatfileValuedata=bufferedreader.readLine();
	    		                                        
	    		                                        StringBuilder CmndRsltStr = new StringBuilder();
	    			        							for (String cmd_Rslts : ErrorReasons)
	    			        							{
	    			        								CmndRsltStr.append(cmd_Rslts);
	    			        								CmndRsltStr.append(", ");
	    			        								}
	    			        							String final_cmd_Rslts=CmndRsltStr.toString();
	    			        							final_cmd_Rslts=final_cmd_Rslts.replace(", |||, ", "|||");
	    			        							final_cmd_Rslts=final_cmd_Rslts.substring(0, final_cmd_Rslts.length()-3);
	    			        							System.out.println("Final Result :"+final_cmd_Rslts);
	    			        							
	    			        							StringBuilder CmndstrBldr = new StringBuilder();
	    			        							for (String cmd_vldtnResult : serverCommandResult)
	    			        							{
	    			        								CmndstrBldr.append(cmd_vldtnResult);
	    			        								CmndstrBldr.append(",");
	    			        								}
	    			        							String serverCommandResultstatus=CmndstrBldr.toString();
	    			        							serverCommandResultstatus=serverCommandResultstatus.substring(0, serverCommandResultstatus.length()-1);
	    			        							
	    			        							outputSheetRow.createCell(3).setCellValue(serverCommandResultstatus);
	    			        							outputSheetRow.createCell(6).setCellValue(final_cmd_Rslts);
	    			        							System.out.println("######  "+serverCommandResultstatus);
	    			        							
	    			        							
	    			        							Calendar calendar2 = Calendar.getInstance();
	    			        						    long endTime = calendar2.getTimeInMillis();
	    			        						     
	    			        						    long diff = endTime - startTime;
	    			        						    long TimeDiff = diff / 1000;
	    			        						    System.out.println("time difference: "+diff);
	    			        						    outputSheetRow.createCell(5).setCellValue(TimeDiff+" Sec");
	    			        						     	
	    			        							Date currentDate=new Date(startTime);
	    			        							DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    			        							outputSheetRow.createCell(4).setCellValue(df.format(currentDate));
	    			        						     	
	    			        							FileOutputStream fout=new FileOutputStream("..\\..\\Reports\\Results\\RFV\\RemotefileValidationResult.xls");
	    			        							
	    			        							refworkbook.write(fout);
	    			        							fout.close();
	    			        							System.out.println("check1");
	    			        							rowcnt=rowcnt+1;
	    		                                    }
	    		                                }
	    		                               
	    		                            }
	    		                        }
	    		                    }
	    		                    
	    		                    //code for remote file validation flag is "NO"
	    		                    else if(TCMExecutionFlag.equalsIgnoreCase("yes") && TCMFlatfileFlag.equalsIgnoreCase("no") && TCMExecutionFlag!=null && TCMFlatfileFlag!=null)
	    		                    {
	    		                        
	    		                    	String FlatfilePath=TCMrow.getCell(11).getStringCellValue().trim();
	    	                        	System.out.println(FlatfilePath);
	    	                        	filereader=new FileReader(FlatfilePath);
	    	                            bufferedreader=new BufferedReader(filereader);
	    	                            flatFileheaderData=bufferedreader.readLine();
	    	                            flatfileValuedata=bufferedreader.readLine();
	    	                            flatFileheaderData=flatFileheaderData.toLowerCase();
	    	                            startTime = calendar1.getTimeInMillis();
	    	                            while(flatfileValuedata!=null)
	    	                            {
	    	                            	outputSheetRow=outputSht.createRow(rowcnt);
	    	                            	flatfileData=flatfileValuedata.split("\\|");
	    	                            	outputSheetRow.createCell(0).setCellValue(TCMUsecaseName);
	    	                                outputSheetRow.createCell(1).setCellValue(TCMModuleName);
	    	                                outputSheetRow.createCell(2).setCellValue(flatfileData[0]);
	    	                                outputSheetRow.createCell(3).setCellValue("NA");
	    	    							outputSheetRow.createCell(6).setCellValue("Remote file validation is not required....");
	    	                                flatfileValuedata=bufferedreader.readLine();
	    	                                Calendar calendar2 = Calendar.getInstance();
	    	                    		    long endTime = calendar2.getTimeInMillis();
	    	                    		     
	    	                    		    long diff = endTime - startTime;
	    	                    		    long TimeDiff = diff / 1000;
	    	                    		    System.out.println("time difference: "+diff);
	    	                    		    outputSheetRow.createCell(5).setCellValue(TimeDiff+" Sec");
	    	                    		     	
	    	                    			Date currentDate=new Date(startTime);
	    	                    			DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    	                    			outputSheetRow.createCell(4).setCellValue(df.format(currentDate));
	    	                    		     	
	    	                    			FileOutputStream fout=new FileOutputStream("..\\..\\Reports\\Results\\RFV\\RemotefileValidationResult.xls");
	    	                    			
	    	                    			refworkbook.write(fout);
	    	                    			fout.close();
	    	                    			System.out.println("check1");
	    	                    			rowcnt=rowcnt+1;
	    	                            }
	    		                    }
	    		                }
	    		                catch(Exception e)
	    		                {
	    		                    System.out.println(e.getMessage());
	    		                }
	    		            }
	    	            }
	    	            
	    	        }
	    	        catch(Exception e)
	    	        {
	    	            String errorMessage=e.getMessage();
	    	            System.out.println("errorMessage: "+errorMessage);
	    	        }
	    	                        
	    	    }
	    	                        
	    	    public static void main(String[] args) throws IOException
	    	    {
	    	        System.out.println("Remote file validation class execution started....");
	    	        flatfileReader();
	    	    }
	    	                        
	    	    private void doCommonConstructorActions(String userName,String password, String connectionIP, String knownHostsFileName)
	    	    {
	    	        jschSSHChannel = new JSch();
	    	        try
	    	        {
	    	            jschSSHChannel.setKnownHosts(knownHostsFileName);
	    	        }
	    	        catch(Exception jschX)
	    	        {
	    	            logError(jschX.getMessage());
	    	        }
	    	        strUserName = userName;
	    	        strPassword = password;
	    	        strConnectionIP = connectionIP;
	    	    }
	    	    public SSHManager(String userName, String password,String connectionIP, String knownHostsFileName)
	    	    {
	    	        doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
	    	        intConnectionPort = 22;
	    	        intTimeOut = 60000;
	    	    }
	    	    public SSHManager(String userName, String password, String connectionIP,String knownHostsFileName, int connectionPort)
	    	    {
	    	        doCommonConstructorActions(userName, password, connectionIP,knownHostsFileName);
	    	        intConnectionPort = connectionPort;
	    	        intTimeOut = 60000;
	    	    }
	    	    public SSHManager(String userName, String password, String connectionIP,String knownHostsFileName, int connectionPort, int timeOutMilliseconds)
	    	    {
	    	        doCommonConstructorActions(userName, password, connectionIP,knownHostsFileName);
	    	        intConnectionPort = connectionPort;
	    	        intTimeOut = timeOutMilliseconds;
	    	    }
	    	    public String connect()
	    	    {
	    	        String errorMessage = null;
	    	        try
	    	        {
	    	            sesConnection = jschSSHChannel.getSession(strUserName,strConnectionIP, intConnectionPort);
	    	            sesConnection.setPassword(strPassword);
	    	            java.util.Properties config = new java.util.Properties();
	    	            config.put("StrictHostKeyChecking", "no");
	    	            sesConnection.setConfig(config);
	    	            // UNCOMMENT THIS FOR TESTING PURPOSES, BUT DO NOT USE IN PRODUCTION
	    	            // sesConnection.setConfig("StrictHostKeyChecking", "no");
	    	            sesConnection.connect(intTimeOut);
	    	            //sesConnection.connect();
	    	            //sesConnection.c
	    	        }
	    	        catch(JSchException jschX)
	    	        {
	    	            errorMessage = jschX.getMessage();
	    	        }
	    	        return errorMessage;
	    	    }
	    	    private String logError(String errorMessage)
	    	    {
	    	        if(errorMessage != null)
	    	        {
	    	            LOGGER.log(Level.SEVERE, "{0}:{1} - {2}",
	    	                    new Object[]{strConnectionIP, intConnectionPort, errorMessage});
	    	        }
	    	        return errorMessage;
	    	    }
	    	    private String logWarning(String warnMessage)
	    	    {
	    	        if(warnMessage != null)
	    	        {
	    	            LOGGER.log(Level.WARNING, "{0}:{1} - {2}",
	    	                    new Object[]{strConnectionIP, intConnectionPort, warnMessage});
	    	        }
	    	        return warnMessage;
	    	    }
	    	    public String sendCommand(String command)
	    	    {
	    	        StringBuilder outputBuffer = new StringBuilder();
	    	        try
	    	        {
	    	            Channel channel = sesConnection.openChannel("exec");
	    	            ((ChannelExec)channel).setCommand(command);
	    	            InputStream commandOutput = channel.getInputStream();
	    	            channel.connect();
	    	            int readByte = commandOutput.read();
	    	            while(readByte != 0xffffffff)
	    	            {
	    	                outputBuffer.append((char)readByte);
	    	                readByte = commandOutput.read();
	    	            }
	    	            channel.disconnect();
	    	        }
	    	        catch(IOException ioX)
	    	        {
	    	            logWarning(ioX.getMessage());
	    	            return null;
	    	        }
	    	        catch(JSchException jschX)
	    	        {
	    	            logWarning(jschX.getMessage());
	    	            return null;
	    	        }
	    	        return outputBuffer.toString();
	    	    }
	    	    public void close()
	    	    {
	    	        sesConnection.disconnect();
	    	    }
	    	 }