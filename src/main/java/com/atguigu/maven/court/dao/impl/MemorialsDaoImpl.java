package com.atguigu.maven.court.dao.impl;

import com.atguigu.maven.court.dao.BaseDao;
import com.atguigu.maven.court.dao.api.MemorialsDao;
import com.atguigu.maven.court.pojo.Memorials;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MemorialsDaoImpl extends BaseDao<Memorials> implements MemorialsDao {

    @Override
    public List<Memorials> selectAllMemorialsDigest() {
        String sql = "SELECT memorials_id memorialsId,\n" +
                "       memorials_title memorialsTitle,\n" +
                "       concat(left(memorials_content, 15), '...') memorialsContentDigest,\n" +
                "       emp_name memorialsEmpName,\n" +
                "       memorials_create_time memorialsCreateTime,\n" +
                "       memorials_status memorialsStatus\n" +
                "FROM t_memorials m LEFT JOIN t_emp e ON m.memorials_emp = e.emp_id";
        return super.getBeanList(sql, Memorials.class);
    }

    @Override
    public Memorials selectMemorialById(String memorialsId) {
        String sql = "SELECT memorials_id memorialsId,\n" +
                "       memorials_title memorialsTitle,\n" +
                "       memorials_content memorialsContent,\n" +
                "       emp_name memorialsEmpName,\n" +
                "       memorials_create_time memorialsCreateTime,\n" +
                "       memorials_status memorialsStatus,\n" +
                "       feedback_time feedbackTime,\n" +
                "       feedback_content feedbackContent\n" +
                "FROM t_memorials m LEFT JOIN t_emp e ON m.memorials_emp = e.emp_id\n" +
                "WHERE memorials_id = ?";
        return super.getSingleBean(sql, Memorials.class, memorialsId);
    }

    @Override
    public void updateMemorialsStatusToRead(String memorialsId) {
        String sql = "update t_memorials set memorials_status = 1 where memorials_id = ?";
        update(sql, memorialsId);
    }

    @Override
    public void updateMemorialsFeedBack(String memorialsId, String feedbackContent) {

        String feedbackTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String sql = "update t_memorials set memorials_status=2,feedback_content=?,feedback_time=? where memorials_id=?";

        super.update(sql, feedbackContent, feedbackTime, memorialsId);
    }
}
