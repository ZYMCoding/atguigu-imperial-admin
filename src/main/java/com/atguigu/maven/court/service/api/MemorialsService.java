package com.atguigu.maven.court.service.api;

import com.atguigu.maven.court.pojo.Memorials;

import java.util.List;

public interface MemorialsService {
    List<Memorials> getALlMemorialsDigest();

    Memorials getMemorialsDetailById(String memorialsId);

    void updateMemorialsStatusToRead(String memorialsId);

    void updateMemorialsFeedBack(String memorialsId, String feedbackContent);
}
