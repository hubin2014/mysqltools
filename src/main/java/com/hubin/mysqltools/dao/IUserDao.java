package com.hubin.mysqltools.dao;

import java.util.List;

import com.hubin.mysqltools.entity.User;

public interface IUserDao {
	
	public List<User> queryAll();

}
