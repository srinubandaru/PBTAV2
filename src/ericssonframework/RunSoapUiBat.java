package ericssonframework;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ericssonframework.GenerateResults;

public class RunSoapUiBat {
	
	public static void executeBat() throws IOException, InterruptedException
	{
		
		     BufferedReader in = null;
		     String line;
		
		     try
		     {
		       Process proc2 = new ProcessBuilder("cmd.exe", "/C", "start ..\\..\\ReadyAPIRunner.bat").redirectErrorStream(true).start(); 
		      //Process proc2 = new ProcessBuilder("cmd.exe", "/C", "start    D:\\Ericsson\\Documents\\testxml\\SubscribeToService\\runSubcribeToService.bat").redirectErrorStream(true).start();
		       Thread.sleep(10000); //Waiting for global-groovy.log to be created by StartSoapUI.bat
		       FileInputStream f = new FileInputStream("..\\..\\Reports\\Results\\Logs\\SoapLogFile.txt");
		       in = new BufferedReader(new InputStreamReader(f));
		       while(((line=in.readLine())!=null)||((line=in.readLine())!=""))
		       {
		        if(line!=null)
		        if(line.contains("Execution done...."))
		        {
		          System.out.println("soap bat execution over...");
		          break;
		        }
		       }
		       proc2.destroy();
		     }
		     
		     catch(Exception e)
		     {
		    	 System.out.println(e.getMessage());
		     }

		
		
	}

   public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		executeBat();
	
		
		
	}

}
