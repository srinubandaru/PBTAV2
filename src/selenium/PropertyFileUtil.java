
package selenium;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileUtil 
{
	public static String getValueForKey(String key) throws Exception
	{
		
		Properties configProporties=new Properties();
		
		configProporties.load(new FileInputStream(new File("./PropertiesFile/Environment.properties")));
		
		int randomNum =  (int)(Math.random() * 100000); 
		
		String duplicateData = String.valueOf(randomNum);
		
		insetValueForKey("PayeeID", duplicateData);
		
		
		return configProporties.getProperty(key);
		
	}
	
	
	public static void insetValueForKey(String newKey,String valueText) throws Exception
	{
		
		Properties insertProp=new Properties();
		
		
		insertProp.load(new FileInputStream(new File("./PropertiesFile/Environment.properties")));
		insertProp.setProperty(newKey,valueText);
		
		//System.out.println(newKey+"  "+valueText  +"  in Property class");
		
		insertProp.store(new FileOutputStream("./PropertiesFile/Environment.properties"), "Inserting Card Holder UserID");
		
		
	}
}



