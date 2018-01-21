/*
 * 文 件 名:  ExploteTableInfo.java
 * 版    权:  Unis Cloud Information Technology Co., Ltd. Copyright 2018,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  胡斌
 * 修改时间:  2018年1月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.hubin.mysqltools.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  胡斌
 * @version  [版本号, 2018年1月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ExproteTableInfoDao {
	
	/**
	 * 导出数据库表结构
	 * @param dbName 数据库名
	 * @param tableName  表名
	 * @return [参数说明]
	 * @author  胡斌 [2018年1月19日]
	 * @return List<Map<String,Object>> [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
    List<Map<String,Object>> exploteTableStruct(@Param("dbName")String dbName,@Param("tableName")String tableName);

	/**
	 * 获取连接的数据库表名
	 * @return [参数说明]
	 * @author  胡斌 [2018年1月19日]
	 * @return String[] [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	String []showTables(@Param("dbName")String dbName);

}
