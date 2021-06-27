
package ericssonframework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

//import test.DriverFuncAutomation;
import ericssonframework.GenerateResults;
//import test.EmailTestReport.SMTPAuthenticator;

public class EmailReport {
	
	static Properties emailProps = new Properties(); //Properties object
	static private String smtpHostName = null;
	static private String recipient = null; //Email recipients
	static private String subject = null; //Email subject
	static private String port = null; //SMTP port
	static private String smtp = null;  //SMTP protocol
	static public String smtpUser = null; //SMTP user
	static public String smtpPswd = null; //SMTP password
	static public String FinalResDir=null;
	
	public static void postMail() throws MessagingException, SQLException, IOException
	{
		try {
			InputStream emailProperties = new FileInputStream("..\\..\\TestInputs\\Properties.txt");
			emailProps.load(emailProperties);
			emailProperties.close();
		}
		catch(IOException io)
		{
			io.printStackTrace();
		}
		System.out.println("Postmail method");
		boolean bdebug = false;
		
		Message msg; 
		String emailFrom = null;
		emailFrom = emailProps.getProperty("EmailFrom");
		System.out.println(emailFrom);
		String[] smtpUserdtls = emailFrom.split("/");
		smtpUser = smtpUserdtls[0];
		System.out.println(smtpUser);
		smtpPswd = smtpUserdtls[1];
		System.out.println(smtpPswd);
		
		smtpHostName = emailProps.getProperty("SMTP_HOST_NAME");
		System.out.println(smtpHostName);
		
//		from = emailProps.getProperty("smtpUser");
		subject = emailProps.getProperty("Subject");
		System.out.println(subject);
		smtp = emailProps.getProperty("SMTP_PROTOCOL");
		System.out.println(smtp);
		port = emailProps.getProperty("SMTP_PORT");
		System.out.println(port);
		
		String path = "..\\..\\TestInputs\\MasterSheet_New.xls";
	 	FileInputStream inputStream=new FileInputStream(path);
		HSSFWorkbook workbook=new HSSFWorkbook(inputStream);
		HSSFSheet sheet=workbook.getSheet("GlobalVariables");
		int rowCount=sheet.getLastRowNum();
		Row row=sheet.getRow(0);
		String recipient=row.getCell(1).getStringCellValue();

		//recipient=emailProps.getProperty("recipients");
		System.out.println(recipient);
		
//		Os= emailProps.getProperty("OS");
		emailProps.put("mail.smtp.host", smtpHostName);
		emailProps.put("mail.smtp.auth", "true");
		
		Properties emailProps = new Properties();
		emailProps.setProperty("mail.transport.protocol", smtp);
		emailProps.setProperty("mail.host", smtpHostName);
		emailProps.setProperty("mail.port", port);
		
		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(emailProps, auth);
		session.setDebug(bdebug);
		// create a message
			msg = new MimeMessage(session);
		
		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(smtpUser);
		msg.setFrom(addressFrom);
		
		String[] recipients = recipient.split(",");
		System.out.println(recipients.length);
		InternetAddress[] addressTo =new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++)
		{
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		// Setting the Subject and Content Type
		
	   
		int passedCnt=GenerateResults.pcnt;
	    System.out.println(passedCnt);
		int failedCnt=GenerateResults.fcnt;
		System.out.println(failedCnt);
		int skippedCnt=GenerateResults.scnt;
		System.out.println(skippedCnt);
		int execnCnt = passedCnt + failedCnt;	
		int totalCnt=passedCnt + failedCnt + skippedCnt;
		subject=subject +" - "+ (new java.util.Date()).toString();
		System.out.println(subject);
		if(failedCnt >= 1) subject=subject + " -- Contains Failed Cases";
		msg.setSubject(subject);
    	//String message = GenerateResults.readFileAsString("D:\\Ericsson\\Documents\\testxml\\CalculatorService\\HTMLReports\\");
		String message = GenerateResults.emailSource;
		
	

		
        msg.setContent(message, "text/html");
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		
//		String str = "<html><img src=\"cid:image_cid\"></html>";  //This is added in the souce string mentioned above (L132)		
//		messageBodyPart.setContent(str, "text/html");  
		
		/*FinalResDir = emailProps.getProperty("ResultDir");
		
		
		File f = new File(FinalResDir+"TestSuite.png");
		if(f.exists()){
// Create another bodypart to include the image attachment.  
		messageBodyPart = new MimeBodyPart();
		  
		
		// Read image from file system.  
		DataSource ds1 = new FileDataSource("D:\\Ericsson\\Documents\\testxml\\CalculatorService\\MailReport\\"+"TestSuite.png");  
		messageBodyPart.setDataHandler(new DataHandler(ds1));  
		  
		// Set the content-ID of the image attachment.  
		// Enclose the image CID with the lesser and greater signs.  
		messageBodyPart.setHeader("Content-ID", "<TestSuiteGraph>");  
		  
		// Add image attachment to multipart.  
		multipart.addBodyPart(messageBodyPart);  
		}*/
		
// Create another bodypart to include the image attachment. 
				messageBodyPart = new MimeBodyPart();
				  
				// Read image from file system.  
				DataSource ds2 = new FileDataSource("..\\..\\TestInputs\\TestCase.jpg");  
				
				messageBodyPart.setDataHandler(new DataHandler(ds2));  
				  
				// Set the content-ID of the image attachment.  
				// Enclose the image CID with the lesser and greater signs.  
				messageBodyPart.setHeader("Content-ID", "<TestCaseGraph>");  
				  
				// Add image attachment to multipart.  
				multipart.addBodyPart(messageBodyPart); 
		
		  
		String testResultPath = GenerateResults.testResultPath;
		System.out.println(testResultPath);
		messageBodyPart = new MimeBodyPart();
		DataSource tResult = new FileDataSource(testResultPath);
		messageBodyPart.setDataHandler(new DataHandler(tResult));
		messageBodyPart.setFileName(testResultPath.substring(testResultPath.lastIndexOf('\\')+ 1, testResultPath.length()));
		multipart.addBodyPart(messageBodyPart);
		msg.setContent(multipart);
		try{
			Transport.send(msg);
			System.out.println("Email sent");
		}
		catch(SendFailedException sfe)
		{
			System.out.println("Email sending failed");
			sfe.printStackTrace();
		} 
	} 
	
	/**
	* SimpleAuthenticator is used to do simple authentication
	* when the SMTP server requires it.
	*/
	static class SMTPAuthenticator extends javax.mail.Authenticator
	{
		public PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication(smtpUser, smtpPswd);
		}
		
	}
		

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	
		postMail();
	}

}
