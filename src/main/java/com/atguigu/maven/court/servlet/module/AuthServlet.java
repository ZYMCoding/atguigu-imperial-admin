package com.atguigu.maven.court.servlet.module;

import com.atguigu.maven.court.exception.LoginFailedException;
import com.atguigu.maven.court.pojo.Emp;
import com.atguigu.maven.court.service.api.EmpService;
import com.atguigu.maven.court.service.impl.EmpServiceImpl;
import com.atguigu.maven.court.servlet.base.ModelBaseServlet;
import com.atguigu.maven.court.util.ImperialCourtConst;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends ModelBaseServlet {

    private EmpService empService = new EmpServiceImpl();

    protected void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            // 1、获取请求参数
            String loginAccount = request.getParameter("loginAccount");
            String loginPassword = request.getParameter("loginPassword");

            // 2、调用 EmpService 方法执行登录逻辑,service层已经保证查询到的对象部位空
            Emp emp = empService.getEmpByLoginAccount(loginAccount, loginPassword);

            // 3、通过 request 获取 HttpSession 对象
            HttpSession session = request.getSession();

            // 4、将查询到的 Emp 对象存入 Session 域
            session.setAttribute(ImperialCourtConst.LOGIN_EMP_ATTR_NAME, emp);

            // 5、前往临时页面视图
//            String templateName = "temp";
//            processTemplate(templateName, request, response);

            System.out.println(request.getServletPath());

            //前往正式目标
            response.sendRedirect("/work?method=showMemorialsDigestList");

        } catch (Exception e) {
            e.printStackTrace();

            // 6、判断此处捕获到的异常是否是登录失败异常
            if (e instanceof LoginFailedException) {
                // 7、如果是登录失败异常则跳转回登录页面
                // ①将异常信息存入请求域
                request.setAttribute("message", e.getMessage());

                // ②处理视图：index，返回到登录页面
                processTemplate("index", request, response);

            }else {
                // 8、如果不是登录异常则封装为运行时异常继续抛出
                throw new RuntimeException(e);
            }
        }
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 1、通过 request 对象获取 HttpSession 对象
        HttpSession session = request.getSession();

        // 2、将 HttpSession 对象强制失效
        session.invalidate();

        // 3、回到首页
        String templateName = "index";
        processTemplate(templateName, request, response);
    }
}
