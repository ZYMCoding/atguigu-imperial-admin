package com.atguigu.maven.court.dao;

import com.atguigu.maven.court.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BaseDao<T> {
    //DBUtils提供的数据库操作对象
    private QueryRunner runner = new QueryRunner();

    /**
     * 查询单个实例
     * @param sql sql语句
     * @param entityClass 查询对象的实例
     * @param parameters 填入sql的值
     * @return 查询对象
     */
    public T getSingleBean(String sql, Class<T> entityClass, Object ... parameters) {

        try {
            // 获取数据库连接
            Connection connection = JDBCUtils.getConnection();

            return runner.query(connection, sql, new BeanHandler<>(entityClass), parameters);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通用的增删改方法，insert、delete、update 操作都可以用这个方法
     * @param sql 执行操作的 SQL 语句
     * @param parameters SQL 语句的参数
     * @return 受影响的行数
     */
    public int update(String sql, Object ... parameters) {
        try {
            Connection connection = JDBCUtils.getConnection();
            return runner.update(connection, sql, parameters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询返回多个对象的方法
     * @param sql 执行查询操作的 SQL 语句
     * @param entityClass 实体类的 Class 对象
     * @param parameters SQL 语句的参数
     * @return 查询结果
     */
    public List<T> getBeanList(String sql, Class<T> entityClass, Object ... parameters) {
        try {
            // 获取数据库连接
            Connection connection = JDBCUtils.getConnection();

            return runner.query(connection, sql, new BeanListHandler<>(entityClass), parameters);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
