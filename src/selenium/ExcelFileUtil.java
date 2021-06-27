
package selenium;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil 
{
	
	Workbook wb;
	String inPath;
	String outPath;
	
	// It will load all the excel sheet & it will instantiate that particular workbook
	
	public ExcelFileUtil(String inExcelPath,String outExcelpath) throws Exception
	{
  // FileInputStream fis = new FileInputStream("./TestInputs/InputSheet.xlsx");
		this.inPath=inExcelPath;
		this.outPath=outExcelpath;
		
		FileInputStream fis = new FileInputStream(inPath);
		
		wb = WorkbookFactory.create(fis);
	}
	
	
	public int rowCount(String sheetname)
	{
		return wb.getSheet(sheetname).getLastRowNum();
	}
	
	
	public int colCount(String sheetname, int rowNo)
	{		
		return wb.getSheet(sheetname).getRow(rowNo).getLastCellNum();
	}
	
	
	public String getData(String sheetname, int row, int column)
	{
		String data = null;
		
		//Cell cc=wb.getSheet(sheetname).getRow(row).getCell(column);
		if (wb.getSheet(sheetname).getRow(row).getCell(column)!=null) {
			
			if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType() == Cell.CELL_TYPE_STRING)
			{
				
				data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
			}
			
			else
			{
				int celldata =(int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
				
				data = String.valueOf(celldata);
			}
			
		}
		
		
		
		return data;
	}
	
		
	public void setData(String sheetname, int row, int column, String str) throws Exception
	{
		Sheet sh = wb.getSheet(sheetname);
		
		Row rownum = sh.getRow(row);
		
		Cell cell = rownum.createCell(column);
		
		cell.setCellValue(str);
		
	//	FileOutputStream fos = new FileOutputStream("./TestInputs/OutputSheet.xlsx");
		
		FileOutputStream fos = new FileOutputStream(outPath);
		
		wb.write(fos);
		
		fos.close();		
	}	
	
	
	
	public void setDataCreate(String sheetname, int row, int column, String str) throws Exception
	{
		Sheet sh = wb.getSheet(sheetname);
		
		Row rownum = sh.createRow(row);
		
		Cell cell = rownum.createCell(column);
		
		cell.setCellValue(str);
		
	//	FileOutputStream fos = new FileOutputStream("./TestInputs/OutputSheet.xlsx");
		
		FileOutputStream fos = new FileOutputStream(outPath);
		
		wb.write(fos);
		
		fos.close();		
	}	
	
	
	public void createRow(String sheetname, int row) throws Exception
	{
		Sheet sh = wb.getSheet(sheetname);
		
		Row rownum = sh.createRow(row);
		sh.removeRow(rownum);
		
		/*
		 
		if (rownum.getCell(0)==null) {
			
			sh.shiftRows(1, sh.getLastRowNum(), -1);
			
		}    */
		
		
		
		
		
	//	FileOutputStream fos = new FileOutputStream("./TestInputs/OutputSheet.xlsx");
		
		FileOutputStream fos = new FileOutputStream(outPath);
		
		wb.write(fos);
		
		fos.close();		
	}
	
	public void setDataRow(String sheetname, Row row, int column, String str) throws Exception
	{
		Sheet sh = wb.getSheet(sheetname);
		
		Row rownum = row;
		
		Cell cell = rownum.createCell(column);
		
		cell.setCellValue(str);
		
	//	FileOutputStream fos = new FileOutputStream("./TestInputs/OutputSheet.xlsx");
		
		FileOutputStream fos = new FileOutputStream(outPath);
		
		wb.write(fos);
		
		fos.close();		
	}	
	
	
	
	public Row newRow(String sheetname, int rowno) throws Exception
	{
		Sheet sh = wb.getSheet(sheetname);
		
		return sh.createRow(rowno);
		
		
	}	
	
	public Row getRow(String sheetname, int rowno) throws Exception
	{
		Sheet sh = wb.getSheet(sheetname);
		
		return sh.getRow(rowno);
		
		
	}
	
	public Cell getcell(String sheetname, int rowno,int colNo) throws Exception
	{
		Sheet sh = wb.getSheet(sheetname);
		
		return sh.getRow(rowno).getCell(colNo);
		
		
	}
	
	
	
	
	
}