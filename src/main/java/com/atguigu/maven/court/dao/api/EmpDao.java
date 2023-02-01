package com.atguigu.maven.court.dao.api;

import com.atguigu.maven.court.pojo.Emp;

public interface EmpDao {
    Emp selectEmpByLoginAccount(String loginAccount, String encodedLoginPassword);
}
