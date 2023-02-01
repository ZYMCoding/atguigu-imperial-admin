package com.atguigu.maven.court.service.impl;

import com.atguigu.maven.court.dao.api.MemorialsDao;
import com.atguigu.maven.court.dao.impl.MemorialsDaoImpl;
import com.atguigu.maven.court.pojo.Memorials;
import com.atguigu.maven.court.service.api.MemorialsService;

import java.util.List;

public class MemorialsServiceImpl implements MemorialsService {

    private MemorialsDao memorialsDao = new MemorialsDaoImpl();

    @Override
    public List<Memorials> getALlMemorialsDigest() {
        return memorialsDao.selectAllMemorialsDigest();
    }

    @Override
    public Memorials getMemorialsDetailById(String memorialsId) {
        return memorialsDao.selectMemorialById(memorialsId);
    }

    @Override
    public void updateMemorialsStatusToRead(String memorialsId) {
        memorialsDao.updateMemorialsStatusToRead(memorialsId);
    }

    @Override
    public void updateMemorialsFeedBack(String memorialsId, String feedbackContent) {
        memorialsDao.updateMemorialsFeedBack(memorialsId, feedbackContent);
    }
}
