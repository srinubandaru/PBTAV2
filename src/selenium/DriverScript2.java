package selenium;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class DriverScript2 {

	public static void main(String[] args) throws Exception {
		
             new DriverScript2().startTest();
	}

	
	
	public static WebDriver driver;
	
	public void startTest() throws Exception
	{
       Multimap<String, String> multimap = ArrayListMultimap.create();
		
		
		
		ExcelFileUtil Mexcel=new ExcelFileUtil("..\\..\\TestInputs\\MasterSheet_New.xls","");
		
		//ExcelFileUtil excel = new ExcelFileUtil("..\\..\\TestInputs\\BrowserCasesTemplate.xls","..\\..\\TestInputs\\BrowserCasesTemplate_Result.xls");
		
		ExcelFileUtil excel = new ExcelFileUtil("..\\..\\TestInputs\\BrowserCasesTemplate_Result.xls","..\\..\\Reports\\Results\\BrowserCases.xls");
		
		int m=1;
		int n=1;
		long startTime;
		long endTime;
		long TimeDiff = 0;
		DateFormat df = null;
		Date currentDate = null;
		Row row=excel.newRow("Sheet1", m);
		String error="";
		
		System.out.println("Master sheet total rows : "+Mexcel.rowCount("TestCaseMaster"));
		
		for (int mc = 1; mc <=Mexcel.rowCount("TestCaseMaster"); mc++) {
			String us=Mexcel.getData("TestCaseMaster", mc, 2);
			String Eu=Mexcel.getData("TestCaseMaster", mc, 4);
			String Uc=Mexcel.getData("TestCaseMaster", mc, 0);
			
			String ModuleStatus = null;
			
			if (Mexcel.getData("TestCaseMaster", mc, 2).equalsIgnoreCase("WAP")&&Mexcel.getData("TestCaseMaster", mc, 4).equalsIgnoreCase("Yes")) {
				
				String Musecase=Mexcel.getData("TestCaseMaster", mc, 0);
				
				
				
				Calendar calendar1 = Calendar.getInstance();
				startTime = calendar1.getTimeInMillis(); 
				
				
				for (int i = 1; i <=Mexcel.rowCount("BrowserCases"); i++) 
				{	
					
					
					
					
					
					if (Mexcel.getData("TestCaseMaster", mc, 4).equalsIgnoreCase("Yes")&&Mexcel.getData("BrowserCases", i, 0).equalsIgnoreCase(Musecase)) 
					{
						
												
						// Define Module Name
						String TCModule=Mexcel.getData("BrowserCases", i, 0);	
						
						
						
						
						
							String Usecase = Mexcel.getData("BrowserCases", i, 0);
							String Description = Mexcel.getData("BrowserCases", i, 1);
							String Object_Type = Mexcel.getData("BrowserCases", i, 2);
							String Locator_Type = Mexcel.getData("BrowserCases", i, 3);
							String Locator_Value = Mexcel.getData("BrowserCases", i, 4);
							String Test_Data = Mexcel.getData("BrowserCases", i, 5);
							
							if (Usecase.equalsIgnoreCase(Musecase)) {
								
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
									
									
									
									
									
									
									
									
									
									multimap.put(Usecase, "Pass");
									
										
									
									ModuleStatus="true";
									
								
									
								}
								
								catch(Exception e)
								{
									multimap.put(Usecase, "Fail");						
									
									ModuleStatus="False";
									
									
									if (e.toString().length()<151) {
										
										   error=Description+" Step Failed : "+e.toString();
											
										   }else{
												
											   error=Description+" Step Failed : "+e.toString().substring(0, 150);
												
												}
									
									System.out.println("Driver Script Exception Error Message: "+e);
									
									
									// For Generating Screenshot
									try {
										
							File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
										
							FileUtils.copyFile(scrFile, new File("..//..//Reports//Results//Screenshots//"+TCModule+"_"+Description+"_"+FunctionLibrary.getRandomNumberFromDate()+".jpg"));
										
									} catch (Exception e2) {
										
										System.out.println("Unable take screenshot : "+e2);
									}
									
									
									
									break;
									
									
								}
								
								catch(AssertionError a)
								{
									multimap.put(Usecase, "Fail");					
										
									ModuleStatus="False";
									
									if (a.toString().length()<151) {
										
									   error=Description+" Step Failed : "+a.toString();
										
									   }else{
											
										   error=Description+" Step Failed : "+a.toString().substring(0, 150);
											
											}
									
									
									System.out.println("Driver Script Exception Error Message: "+a);
										
									
									
										
									// For Generating Screenshot
									try {
										
							File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
										
							FileUtils.copyFile(scrFile, new File("..//..//Reports//Results//Screenshots//"+TCModule+"_"+Description+"_"+FunctionLibrary.getRandomNumberFromDate()+".jpg"));
										
									} catch (Exception e2) {
										
										System.out.println("Unable take screenshot : "+e2);
									}
									
										
									break;
									
								}
								 
								 

								
							}
							
							
							/*Calendar calendar2 = Calendar.getInstance();
						     endTime = calendar2.getTimeInMillis(); 
						     
						     long diff = endTime - startTime;
					         TimeDiff = diff / 1000;
					         
					         currentDate=new Date(startTime);
							 df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");*/
							
													
					  
						
									
				    }
					
					
					
					
					
					
					
					
							
				}

				Calendar calendar2 = Calendar.getInstance();
			     endTime = calendar2.getTimeInMillis(); 
			     
			     long diff = endTime - startTime;
		         TimeDiff = diff / 1000;
		         
		         currentDate=new Date(startTime);
				 df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				 
				 
				 excel.setDataRow("Sheet1", row, 3,df.format(currentDate) );
					
				 excel.setDataRow("Sheet1", row, 4,TimeDiff+ "  Sec" );
				
				
				excel.setDataRow("Sheet1", row, 0,Musecase);
				excel.setDataRow("Sheet1", row, 1,"Final Output");
				 
				/*excel.setDataRow("Sheet1", row, 3,df.format(currentDate) );
				
				 excel.setDataRow("Sheet1", row, 4,TimeDiff+ "  Sec" );*/
				 
				
				if(!multimap.containsEntry(Musecase, "Fail"))
				{
					excel.setDataRow("Sheet1", row, 2, "Pass");
				    excel.setDataRow("Sheet1", row, 5, "NA");
				    
				    excel.setDataRow("Sheet1", row, 6, " ");
				    excel.setDataRow("Sheet1", row, 7, " ");
				    excel.setDataRow("Sheet1", row, 8, " ");
					 
					 
				}
				else
				{
					excel.setDataRow("Sheet1", row, 2, "Fail");
					excel.setDataRow("Sheet1", row, 5, error);
					 excel.setDataRow("Sheet1", row, 6, " ");
					 excel.setDataRow("Sheet1", row, 7, " ");
					 excel.setDataRow("Sheet1", row, 8, " ");
					
					
					
				}
				
				//row=excel.newRow("Sheet1", m);
				
				m=++m;
				
				row=excel.newRow("Sheet1", m);
				
			}
			
			
		}
		
		
		 
	
		  
		
		for (String key : multimap.keySet()) {
			
			 System.out.println(key+"<=====>"+multimap.get(key));
			 
			 
			
		}
		
		
		
		
		
		
			}
	
	
	
	
	
	
	
}
