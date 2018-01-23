package com.hubin.mysqltools.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hubin.mysqltools.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/jdbc/jdbc.xml"}) 
public class IUserDaoTest {

	@Resource
	IUserDao userDao;
	@Test
	public void testQueryAll() {
		PageHelper.startPage(1, 2);
		List<User> list = userDao.queryAll();
	    // 取分页信息
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        System.out.println("查询出的用户共有："+list.size());
        System.out.println("共有用户：" + pageInfo.getTotal());
	}

}
