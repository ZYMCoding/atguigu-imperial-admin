package com.atguigu.maven.court.dao.impl;

import com.atguigu.maven.court.dao.BaseDao;
import com.atguigu.maven.court.dao.api.EmpDao;
import com.atguigu.maven.court.pojo.Emp;

public class EmpDaoImpl extends BaseDao<Emp> implements EmpDao {
    @Override
    public Emp selectEmpByLoginAccount(String loginAccount, String encodedLoginPassword) {
        // 1、编写 SQL 语句
        String sql = "SELECT emp_id, emp_name, emp_position, login_account, login_password FROM t_emp WHERE login_account = ? AND login_password = ?";

        // 2、调用父类方法查询单个对象
        return super.getSingleBean(sql, Emp.class, loginAccount, encodedLoginPassword);
    }
}
