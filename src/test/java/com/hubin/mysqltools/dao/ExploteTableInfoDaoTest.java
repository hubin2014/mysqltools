/*
 * 文 件 名:  ExploteTableInfoDaoTest.java
 * 版    权:  Unis Cloud Information Technology Co., Ltd. Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  胡斌
 * 修改时间:  2018年1月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.hubin.mysqltools.dao;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 
 * 
 * @author  胡斌
 * @version  [版本号, 2018年1月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/jdbc/jdbc.xml"}) 
public class ExploteTableInfoDaoTest {

	@Resource
	ExproteTableInfoDao exporteTableInfoDao;

	@Test
	public void testExploteTableStruct() {
		List<Map<String,Object>> list=exporteTableInfoDao.exploteTableStruct("adyx_okami","tb_okami_order");
		System.out.println(list);
	}


	@Test
	public void testShowTables() {
		String tables[]=exporteTableInfoDao.showTables("adyx_okami");
		for(String tableName:tables) {
			System.out.println(tableName);
		}
		
	}
	
	/**
	 * 导出数据库中表字段
	 * @author  胡斌 [2018年1月19日]
	 * @return void [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	
	@Test
	public void testExploteTables() {
		
		String [][]column_alias= {
				{"COLUMN_NAME", "列名"},
				{"COLUMN_TYPE","数据类型"},
				{"DATA_TYPE","字段类型"},
				{"CHARACTER_MAXIMUM_LENGTH","长度"},
				{"IS_NULLABLE","是否为空值"},
				{"COLUMN_DEFAULT"," 默认值"},
				{"COLUMN_DEFAULT"," 默认值"},
				{"INDEX_NAME","索引名称"},
				{"INDEX_TYPE"," 索引方法"},
				{"COLUMN_COMMENT","备注"}
		};
		Map<String,List<Map<String,Object>>> tableMap=new HashMap<String,List<Map<String,Object>>>();
		try {
			OutputStream outXlsx = new FileOutputStream("D://表结构"+System.currentTimeMillis()+".xlsx");
			//获取数据库中所有表名
			String []tables=exporteTableInfoDao.showTables("adyx_okami");
			
			for(String tt:tables) {
				List<Map<String,Object>> list=exporteTableInfoDao.exploteTableStruct("adyx_okami",tt);
				tableMap.put(tt, list);
			}
			com.hubin.mysqltools.utils.ExcelUtils.exportExcelX(column_alias, tableMap,outXlsx);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
