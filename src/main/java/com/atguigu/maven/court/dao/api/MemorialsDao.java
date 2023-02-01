package com.atguigu.maven.court.dao.api;

import com.atguigu.maven.court.pojo.Memorials;

import java.util.List;

public interface MemorialsDao {
    List<Memorials> selectAllMemorialsDigest();

    Memorials selectMemorialById(String memorialsId);

    void updateMemorialsStatusToRead(String memorialsId);

    void updateMemorialsFeedBack(String memorialsId, String feedbackContent);
}
