package selenium;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;



public class DriverScript 
{
	public static void main(String[] args) {
		
		
		
		
	}
	
	public static WebDriver driver;
	
	public void startTest() throws Exception
	{
		ExcelFileUtil Mexcel=new ExcelFileUtil("..\\..\\TestInputs\\MasterSheet_New.xls","");
		
		//ExcelFileUtil excel = new ExcelFileUtil("..\\..\\TestInputs\\BrowserCasesTemplate.xls","..\\..\\TestInputs\\BrowserCasesTemplate_Result.xls");
		
		ExcelFileUtil excel = new ExcelFileUtil("..\\..\\TestInputs\\BrowserCasesTemplate.xls","..\\..\\Reports\\Results\\BrowserBasedUseCases.xls");
		
		int m=1;
		
		
		for (int mc = 1; mc <=Mexcel.rowCount("TestCaseMaster"); mc++) {
			String us=Mexcel.getData("TestCaseMaster", mc, 2);
			String Eu=Mexcel.getData("TestCaseMaster", mc, 4);
			String Uc=Mexcel.getData("TestCaseMaster", mc, 0);
			
			for (int l = 1; l <=excel.rowCount("Sheet1"); l++) {
				
				if (Mexcel.getData("TestCaseMaster", mc, 2).equalsIgnoreCase("WAP")&&Mexcel.getData("TestCaseMaster", mc, 4).equalsIgnoreCase("No")&&excel.getData("Sheet1", l, 0).equalsIgnoreCase(Uc)) 
				{
					
					excel.setData("Sheet1", l, 2, "Not Executed");
					
				}
			
			
			}
			
			if (Mexcel.getData("TestCaseMaster", mc, 2).equalsIgnoreCase("WAP")&&Mexcel.getData("TestCaseMaster", mc, 4).equalsIgnoreCase("Yes")) {
				
				String Musecase=Mexcel.getData("TestCaseMaster", mc, 0);
				
				
				for (int k = 1; k <=excel.rowCount("Sheet1"); k++) {
					
					if (Mexcel.getData("TestCaseMaster", mc, 4).equalsIgnoreCase("Yes")&&excel.getData("Sheet1", k, 0).equalsIgnoreCase(Musecase)) 
					{
						m=k;
						
						break;
					}
					
					
				}
				   
				
				
				for (int i = m; i <=excel.rowCount("Sheet1"); i++) 
				{	
					String ModuleStatus = null;
					
					
					
					
					if (Mexcel.getData("TestCaseMaster", mc, 4).equalsIgnoreCase("Yes")&&excel.getData("Sheet1", i, 0).equalsIgnoreCase(Musecase)) 
					{
						
						Calendar calendar1 = Calendar.getInstance();
					     long startTime = calendar1.getTimeInMillis(); 
						
						
						// Define Module Name
						String TCModule=excel.getData("Sheet1", i, 0);	
						
						
						int rowcount = excel.rowCount(TCModule);
						
						for(int j=1;j<=rowcount;j++)
						{
							String Description = excel.getData(TCModule, j, 0);
							String Object_Type = excel.getData(TCModule, j, 1);
							String Locator_Type = excel.getData(TCModule, j, 2);
							String Locator_Value = excel.getData(TCModule, j, 3);
							String Test_Data = excel.getData(TCModule, j, 4);
							
						 try 
						 {						
										
							if(Object_Type.equalsIgnoreCase("Browser"))
							{
								driver=FunctionLibrary.startBrowser(driver,Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("openApp"))
							{
								FunctionLibrary.openApplication(driver,Test_Data);
								
							}
							
							
							if(Object_Type.equalsIgnoreCase("typeText"))
							{
								FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							
							
							if(Object_Type.equalsIgnoreCase("typeAction_key"))
							{
								FunctionLibrary.typeAction_key(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("selectAction"))
							{
								FunctionLibrary.selectAction(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("click"))
							{
								FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
								
							}	
							
							if(Object_Type.equalsIgnoreCase("titleValidation"))
							{
								FunctionLibrary.titleValidation(driver,Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("elementValidate"))
							{
								FunctionLibrary.elementValidation(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("waitElement"))
							{
								FunctionLibrary.waitforelement(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("waitforClick"))
							{
								FunctionLibrary.waitforClick(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("clicks"))
							{
								FunctionLibrary.clicks(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							
							if(Object_Type.equalsIgnoreCase("closeApp"))
							{
								FunctionLibrary.closeBrowser(driver);
								
							}
							
							if(Object_Type.equalsIgnoreCase("pageScrollDown"))
							{
								FunctionLibrary.pageScrollDown(driver);
								
							}
							
							if(Object_Type.equalsIgnoreCase("pageScrollUP"))
							{
								FunctionLibrary.pageScrollUP(driver);
								
							}
							
							
							if(Object_Type.equalsIgnoreCase("mouseOver"))
							{
								FunctionLibrary.mouseActions(driver, Locator_Type, Locator_Value);
								
							}
							
							
							if(Object_Type.equalsIgnoreCase("takeText"))
							{
								FunctionLibrary.takeText(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							
							if(Object_Type.equalsIgnoreCase("tableValidation"))
							{
								System.out.println("tableValidation in Driver Script ");
								
								FunctionLibrary.tableValidation(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("searchText"))
							{
								System.out.println("tableValidation in Driver Script ");
								
								FunctionLibrary.searchText(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("DBValidation"))
							{
								System.out.println("DBValidation in Driver Script ");
								
								FunctionLibrary.DBValidation();
								
							}
							
							if(Object_Type.equalsIgnoreCase("dBEmailValidation"))
							{
								System.out.println("dBEmailValidation in Driver Script ");
								
								FunctionLibrary.dBEmailValidation();
								
							}
							

							
							
							if(Object_Type.equalsIgnoreCase("currentDateSelection"))
							{
								FunctionLibrary.currentDateSelection(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("fileUpload"))
							{
								FunctionLibrary.fileUpload(driver, Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("multiWindow"))
							{
								FunctionLibrary.multiWindow(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("closeNewTab"))
							{
								FunctionLibrary.closeNewTab(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("webEleValidate"))
							{
								FunctionLibrary.webEleValidate(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							if(Object_Type.equalsIgnoreCase("textValidation"))
							{
								FunctionLibrary.textValidation(driver, Locator_Type, Locator_Value, Test_Data);
								
							}
							
							
							
							
							
							
							
							
							
							
							
							excel.setData(TCModule, j, 5, "Pass");	
							
							ModuleStatus="true";
							
						
							
						}
						
						catch(Exception e)
						{
							excel.setData(TCModule, j, 5, "Fail");						
							
							ModuleStatus="False";
							System.out.println("Driver Script Exception Error Message: "+e);
							
							excel.setData("Sheet1", i, 5, Description+"  Step Failed : " +e.toString().substring(0, 100));
							
							
							
							// For Generating Screenshot
							
							File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
							
							FileUtils.copyFile(scrFile, new File("..//..//Reports//Results//Screenshots//"+TCModule+"_"+Description+"_"+FunctionLibrary.getRandomNumberFromDate()+".jpg"));
							
							
							
							
							
							break;
							
							
						}
						
						catch(AssertionError a)
						{
							excel.setData(TCModule, j, 5, "Fail");						
								
							ModuleStatus="False";
							
							
							System.out.println("Driver Script Exception Error Message: "+a);
								
							excel.setData("Sheet1", i, 5, Description+"  Step Failed  : "+a.toString().substring(0, 55));
							
								
							// For Generating Screenshot
								
							File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
								
							FileUtils.copyFile(scrFile, new File("..//..//Reports//Results//Screenshots//"+TCModule+"_"+Description+"_"+FunctionLibrary.getRandomNumberFromDate()+".jpg"));
								
							
								
							break;
							
						}
							
					  }
						
					//	excel.setDataCreate("Sheet1", i, 0, Musecase);
						
						Calendar calendar2 = Calendar.getInstance();
					     long endTime = calendar2.getTimeInMillis(); 
					     
					     long diff = endTime - startTime;
				         long TimeDiff = diff / 1000;
				         
				         Date currentDate=new Date(startTime);
						 DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						 
						 
						 excel.setData("Sheet1", i, 3,df.format(currentDate) );
						
						 excel.setData("Sheet1", i, 4,TimeDiff+ "  Sec" );
						 
						
						if(ModuleStatus.equalsIgnoreCase("true"))
						{
							excel.setData("Sheet1", i, 2, "Pass");
							excel.setData("Sheet1", i, 5, "NA");
							
						}
						else
						{
							excel.setData("Sheet1", i, 2, "Fail");
							
							
							
							
						}				
				    }
					
					
					
					else 
					{		
						excel.setData("Sheet1", i, 2, "Not Executed");
						
					}
					
					
					
					
					
							
				}

				
				
				
				
				m=++m;
				
			}
			
			
		}
		
		
		 
		try {
			
			
				
				ExcelFileUtil exl=new ExcelFileUtil("..\\..\\Reports\\Results\\BrowserBasedUseCases.xls", "..\\..\\Reports\\Results\\BrowserBasedUseCases.xls");
				
				for (int i = 1; i <=exl.rowCount("Sheet1"); i++) {
					
					if (exl.getData("Sheet1", i, 2).equalsIgnoreCase("Not Executed")) {
						
						exl.createRow("Sheet1", i);
						
					}else{
						
						
						exl.setData("Sheet1", i, 6, " ");
						exl.setData("Sheet1", i, 7, " ");
						exl.setData("Sheet1", i, 8, " ");
						
						
						
					}
					
				}
				
			
			
			
			
		
			
			
			} catch (Exception e) {
				
				
			System.out.println("Error in Sheet Write  : "+ e);
		}
		  
		
		
		
		
		
		
			}
}