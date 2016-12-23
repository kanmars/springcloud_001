/**   
* @Title: ExcelTools.java 
* @Package cn.com.xcommon.common.security.excel 
* @Description: 解析excel工具类 
* @author zoufangfang   
* @date 2015-3-24 上午10:03:28 
* @company cn.com.xcommon
* @version V1.0   
*/ 


package cn.com.xcommon.commons.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/** 
 * @ClassName: ExcelTools 
 * @Description: 解析excel工具类 
 * @author zoufangfang 
 * @date 2015-3-24 上午10:03:28  
 */
public class ExcelTools {
	/**
	* @Title: excelToList 
	* @Description: 解析excel文件 
	* @param excelFile
	* @return
	* @throws IOException  
	* @return List<List<String>>    返回类型 
	* @throws
	 */
	public List<List<String>> excelToList(String excelFile) throws IOException{
		List<List<String>> data = new ArrayList<List<String>>();
		InputStream is = new FileInputStream(excelFile);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {//第一行为标题，因此从第二行开始
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}
			List<String> tmp = new ArrayList<String>();
			// 循环列Cell
			for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
				HSSFCell hssfCell = hssfRow.getCell(cellNum);
				if (hssfCell == null) {
					tmp.add("");
					continue;
				}
				tmp.add(new String(getValue(hssfCell)));
			}
			data.add(tmp);
		}
		return data;
	}
	
	public List<List<String>> excelToList(InputStream is) throws IOException{
		List<List<String>> data = new ArrayList<List<String>>();
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {//第一行为标题，因此从第二行开始
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}
			List<String> tmp = new ArrayList<String>();
			// 循环列Cell
			for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
				HSSFCell hssfCell = hssfRow.getCell(cellNum);
				if (hssfCell == null) {
					tmp.add("");
					continue;
				}
				tmp.add(new String(getValue(hssfCell)));
			}
			data.add(tmp);
		}
		return data;
	}
	
	private String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
	
	public HSSFWorkbook export(List<List<String>> list) {  
        
		HSSFWorkbook wb = new HSSFWorkbook();  
        HSSFSheet sheet = wb.createSheet("sheetx");  
        
        for (int i = 0; i < list.size(); i++) {
        	
        	HSSFRow row = sheet.createRow(i);
        	
        	for(int j = 0; j < list.get(i).size(); j++){
        		 
                row.createCell(j).setCellValue(list.get(i).get(j));  
        	}
             
        }  
        return wb;  
    }  

}
