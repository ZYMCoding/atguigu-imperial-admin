package com.atguigu.maven.court.pojo;

public class Emp {
    private Integer emp_Id;
    private String emp_name;
    private String emp_position;
    private String login_account;
    private String login_password;

    public Emp(Integer emp_Id, String emp_name, String emp_position, String login_account, String login_password) {
        this.emp_Id = emp_Id;
        this.emp_name = emp_name;
        this.emp_position = emp_position;
        this.login_account = login_account;
        this.login_password = login_password;
    }

    public Emp(){}

    @Override
    public String toString() {
        return "Emp{" +
                "empId=" + emp_Id +
                ", empName='" + emp_name + '\'' +
                ", empPosition='" + emp_position + '\'' +
                ", loginAccount='" + login_account + '\'' +
                ", loginPassword='" + login_password + '\'' +
                '}';
    }

    public Integer getEmp_Id() {
        return emp_Id;
    }

    public void setEmp_Id(Integer emp_Id) {
        this.emp_Id = emp_Id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_position() {
        return emp_position;
    }

    public void setEmp_position(String emp_position) {
        this.emp_position = emp_position;
    }

    public String getLogin_account() {
        return login_account;
    }

    public void setLogin_account(String login_account) {
        this.login_account = login_account;
    }

    public String getLogin_password() {
        return login_password;
    }

    public void setLogin_password(String login_password) {
        this.login_password = login_password;
    }
}
