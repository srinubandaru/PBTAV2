
package selenium;

import org.testng.annotations.Test;



public class AppTest
{	
	
	public static void main(String[] args) throws Exception {
		
		kickStart();
		
		
		
	}

	
	public static void kickStart()
	{		
		DriverScript t = new DriverScript();
		try 
		{
			t.startTest();
			
			
		} 
		catch (Exception e)
		{			
			e.printStackTrace();
			
			System.out.println("ERROR  : "+e);
		}
		
		
	}	
}
