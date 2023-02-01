package com.atguigu.maven.court.service.api;

import com.atguigu.maven.court.pojo.Emp;

public interface EmpService {
    Emp getEmpByLoginAccount(String loginAccount, String loginPassword);
}
