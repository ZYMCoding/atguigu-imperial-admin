package com.atguigu.maven.court.filter;

import com.atguigu.maven.court.util.JDBCUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * 通过Filter实现事务
 */
public class TransactionFilter implements Filter {

    //声明集合保存静态资源拓展名
    private static Set<String> staticResourceExtNameSet;

    static {
        staticResourceExtNameSet = new HashSet<>();
        staticResourceExtNameSet.add(".png");
        staticResourceExtNameSet.add(".jpg");
        staticResourceExtNameSet.add(".css");
        staticResourceExtNameSet.add(".js");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //排除静态资源
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String servletPath = request.getServletPath();
        if (servletPath.contains(".")) {
            String extName = servletPath.substring(servletPath.lastIndexOf("."));
            if (staticResourceExtNameSet.contains(extName)) {
                // 如果检测到当前请求确实是静态资源，则直接放行，不做事务操作
                filterChain.doFilter(servletRequest, servletResponse);
                // 当前方法立即返回
                return;
            }
        }

        Connection connection = null;

        try {
            //获得连接
            connection = JDBCUtils.getConnection();

            //取消自动提交
            connection.setAutoCommit(false);

            //核心操作
            filterChain.doFilter(servletRequest, servletResponse);

            //提交
            connection.commit();
        } catch (Exception e) {
            try {
                //事务回滚
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            //将异常发送到指定页面显示
            //获得异常信息
            String message = e.getMessage();
            request.setAttribute("systemMessage", message);

            //异常存入作用域并将请求转发到首页
            request.getRequestDispatcher("/").forward(request, servletResponse);
        }finally {
            JDBCUtils.releaseConnection(connection);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
