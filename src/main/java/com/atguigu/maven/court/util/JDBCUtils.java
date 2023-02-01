package com.atguigu.maven.court.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 1.从数据库连接池获得数据库连接
 * 2.将连接池绑定到本地线程（ThreadLocal）
 * 3.释放线程时和本地线程解除绑定
 */
public class JDBCUtils {
    //数据源变量设置为静态资源，保证大对象的单例性
    private static DataSource dataSource;

    //将连接放在唯一的ThreadLocal里从而使连接可以跨类访问
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    //静态代码块初始化数据源
    static {
        //从properties获取数据库信息
        //resources 下的内容经过构建操作中的打包操作后在WEB-INF/classes下

        //获得当前类的类加载器
        ClassLoader classLoader = JDBCUtils.class.getClassLoader();

        //得到输入流
        InputStream resourceAsStream = classLoader.getResourceAsStream("jdbc.properties");

        //封装属性文件中的数据
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            //避免真正抛出异常后捕获到异常将问题掩盖
            throw new RuntimeException(e);
        }
    }

    //获取数据库连接并返回
    public static Connection getConnection() {
        Connection connection = null;
        try {
            //先从本地线程看是否存在线程
            connection = threadLocal.get();

            if (connection == null) {
                //如果ThreadLocal没有连接，从连接池中获取
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //释放数据库连接
    public static void releaseConnection(Connection connection) {

        if (connection != null) {

            try {
                // 在数据库连接池中将当前连接对象标记为空闲
                connection.close();

                // 将当前数据库连接从当前线程上移除
                threadLocal.remove();

            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
