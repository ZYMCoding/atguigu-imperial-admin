package com.atguigu.maven.court.servlet.module;

import com.atguigu.maven.court.pojo.Memorials;
import com.atguigu.maven.court.service.api.MemorialsService;
import com.atguigu.maven.court.service.impl.MemorialsServiceImpl;
import com.atguigu.maven.court.servlet.base.ModelBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class WorkServlet extends ModelBaseServlet {

    private MemorialsService memorialsService = new MemorialsServiceImpl();

    protected void showMemorialsDigestList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //调用Service方法
        List<Memorials> memorialsList = memorialsService.getALlMemorialsDigest();

        //数据存入请求域
        request.setAttribute("memorialsList", memorialsList);

        String templateName = "memorials-list";
        processTemplate(templateName, request, response);
    }

    protected void showMemorialsDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1、从请求参数读取 memorialsId
        String memorialsId = request.getParameter("memorialsId");

        // 2、根据 memorialsId 从 Service 中查询 Memorials 对象
        Memorials memorials = memorialsService.getMemorialsDetailById(memorialsId);

        // **********************补充功能**********************
        // 获取当前奏折对象的状态
        Integer memorialsStatus = memorials.getMemorialsStatus();

        // 判断奏折状态
        if (memorialsStatus == 0) {
            // 更新奏折状态：数据库修改
            memorialsService.updateMemorialsStatusToRead(memorialsId);

            // 更新奏折状态：当前对象修改
            memorials.setMemorialsStatus(1);
        }
        // **********************补充功能**********************

        // 3、将 Memorials 对象存入请求域
        request.setAttribute("memorials", memorials);

        // 4、解析渲染视图
        String templateName = "memorials-detail";
        processTemplate(templateName, request, response);
    }

    protected void feedBack(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 获取表单提交的请求参数
        String memorialsId = request.getParameter("memorialsId");
        String feedbackContent = request.getParameter("feedbackContent");

        // 执行更新
        memorialsService.updateMemorialsFeedBack(memorialsId, feedbackContent);

        // 重定向回显示奏折列表页面
        response.sendRedirect("/work?method=showMemorialsDigestList");
    }
}
