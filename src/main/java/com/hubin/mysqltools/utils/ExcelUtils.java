/*
 * 文 件 名:  ExcelUtils.java
 * 版    权:  Unis Cloud Information Technology Co., Ltd. Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  胡斌
 * 修改时间:  2018年1月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.hubin.mysqltools.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


public class ExcelUtils {
	public static String NO_DEFINE = "no_define";//未定义的字段
    public static String DEFAULT_DATE_PATTERN="yyyy-MM-dd HH:mm:ss";//默认日期格式
    public static int DEFAULT_COLOUMN_WIDTH = 17;
    
	   public static void exportExcelX(String [][]column_alias, Map<String,List<Map<String,Object>>> tableList,OutputStream out) {
	        
	        // 声明一个工作薄
	        SXSSFWorkbook workbook = new SXSSFWorkbook(20000);//缓存
	        workbook.setCompressTempFiles(true);
	         //表头样式
	        CellStyle titleStyle = workbook.createCellStyle();
	        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        Font titleFont = workbook.createFont();
	        titleFont.setFontHeightInPoints((short) 20);
	        titleFont.setBoldweight((short) 700);
	        titleStyle.setFont(titleFont);
	        // 列头样式
	        CellStyle headerStyle = workbook.createCellStyle();
	        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        Font headerFont = workbook.createFont();
	        headerFont.setFontHeightInPoints((short) 12);
	        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        headerStyle.setFont(headerFont);
	        // 单元格样式
	        CellStyle cellStyle = workbook.createCellStyle();
	        //背景填充
	        //cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        //自动换行
	        cellStyle.setWrapText(true);
	        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        Font cellFont = workbook.createFont();
	        cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	        cellStyle.setFont(cellFont);
	        // 生成一个(带标题)表格
	        SXSSFSheet sheet = workbook.createSheet();
	        //设置列宽
	        int minBytes = DEFAULT_COLOUMN_WIDTH;//至少字节数
	        
	        int[] arrColWidth = new int[column_alias.length];
	        // 产生表格标题行,以及设置列宽
	        String[] properties = new String[column_alias.length];
	        String[] headers = new String[column_alias.length];
	        int ii = 0;
	        for (int j=0;j<column_alias.length;j++) {
	            String fieldName = column_alias[j][0];
	            properties[ii] = fieldName;
	            headers[ii] =column_alias[j][1];
	            int bytes = fieldName.getBytes().length;
	            arrColWidth[ii] =  bytes < minBytes ? minBytes : bytes;
	            sheet.setColumnWidth(ii,arrColWidth[ii]*256);
	            ii++;
	        }
	        // 遍历集合数据，产生数据行
	        int rowIndex = 0;
	       Set<String> tablNameSet=tableList.keySet();
	       //遍历表名
	       for(String tableName:tablNameSet) {
	    	   //获取某一张表结构
	    	   List<Map<String,Object>> list=tableList.get(tableName);
	    	   //表头
	    	   SXSSFRow titleRow = sheet.createRow(rowIndex);
	    	   titleRow.createCell(0).setCellValue(tableName);
	           titleRow.getCell(0).setCellStyle(titleStyle);
	           sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0,column_alias.length - 1));
	           rowIndex=rowIndex+1;
	           
	           SXSSFRow headRow = sheet.createRow(rowIndex);
	           int  count=0;
	           for(int i=0;i<column_alias.length;i++) {
    			   SXSSFCell newCell = headRow.createCell(count);
    			   newCell.setCellValue(column_alias[i][1]);
	               newCell.setCellStyle(cellStyle);
	               count++;
    		   }
	           rowIndex=rowIndex+1;
	           
	    	   //遍历表中的字段
	    	   for(Map<String,Object> tabMap:list) {
	    		   SXSSFRow dataRow = sheet.createRow(rowIndex);
	    		   //表头信息集合
	    		   int cellCount=0;
	    		   for(int i=0;i<column_alias.length;i++) {
	    			   SXSSFCell newCell = dataRow.createCell(cellCount);
	    			   newCell.setCellValue(tabMap.get(column_alias[i][0])==null?"":tabMap.get(column_alias[i][0]).toString());
		               newCell.setCellStyle(cellStyle);
		               cellCount++;
	    			   
	    		   }
	    		   rowIndex++;
	    	   }
	    	   rowIndex++;
	       }
	       try {
	            workbook.write(out);
	            workbook.close();
	            workbook.dispose();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	   
	    }
}