package com.taiji.eap.biz.qyjbxx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taiji.eap.biz.jcdxx.bean.Jcdxx;
import com.taiji.eap.biz.jcdxx.dao.JcdxxDao;
import com.taiji.eap.biz.qyjbxx.bean.Qyfaxx;
import com.taiji.eap.biz.qyjbxx.bean.Qyjbxx;
import com.taiji.eap.biz.qyjbxx.dao.QyjbxxDao;
import com.taiji.eap.biz.qyjbxx.service.QyjbxxService;
import com.taiji.eap.biz.spider.bean.Spider;
import com.taiji.eap.biz.spider.dao.SpiderDao;
import com.taiji.eap.biz.zxjg.service.ZxjgService;
import com.taiji.eap.biz.zxjg.service.impl.ZxjgServiceImpl;
import com.taiji.eap.biz.zxjg.spider.CyswryzxxtPipeline;
import com.taiji.eap.biz.zxjg.spider.CyswryzxxtProcessor;
import com.taiji.eap.biz.zxjg.spider.CyswryzxxtSpider;
import com.taiji.eap.common.datasource.annotation.DataSource;
import com.taiji.eap.common.dictionary.bean.Dictionary;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@DataSource("oracle")
public class QyjbxxServiceImpl implements QyjbxxService{

    @Autowired
    private QyjbxxDao qyjbxxDao;

    @Autowired
    private SpiderDao spiderDao;

    @Autowired
    private JcdxxDao jcdxxDao;

    @Autowired
    private CyswryzxxtPipeline pipeline;

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long primaryKey) {
        return qyjbxxDao.deleteByPrimaryKey(primaryKey);
    }

    @Transactional
    @Override
    public int insert(Qyjbxx qyjbxx) {
        return qyjbxxDao.insert(qyjbxx);
    }

    @Override
    public Qyjbxx selectByPrimaryKey(Long primaryKey) {
        return qyjbxxDao.selectByPrimaryKey(primaryKey);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(Qyjbxx qyjbxx) {
        return qyjbxxDao.updateByPrimaryKey(qyjbxx);
    }

    @Override
    public List<Qyjbxx> list(String searchText) {
        return qyjbxxDao.list(searchText);
    }

    @Override
    public PageInfo<Qyjbxx> list(int pageNum, int pageSize, String searchText) throws Exception {
        PageHelper.startPage(pageNum,pageSize);
        List<Qyjbxx> qyjbxxs = qyjbxxDao.list(searchText);
        PageInfo<Qyjbxx> pageInfo = new PageInfo<Qyjbxx>(qyjbxxs);
        return pageInfo;
    }

    @Override
    public void spider(String qybh, String startDate, String endDate) {
        Qyjbxx qyjbxx = qyjbxxDao.selectByQybh(qybh);
        Spider s = spiderDao.selectByPrimaryKey(qyjbxx.getSpiderId());
        List<Jcdxx> jcdxxes = jcdxxDao.list(null,qyjbxx.getQybh(),null,qyjbxx.getvId());
        CyswryzxxtProcessor processor = new CyswryzxxtProcessor(jcdxxes,startDate,endDate);
        pipeline.setJcdxxes(jcdxxes);
        CyswryzxxtSpider spider = new CyswryzxxtSpider(processor,pipeline);
        spider.start(s.getLandingPage(),qyjbxx.getLoginName(),qyjbxx.getLoginPw());
    }

    @Override
    @DataSource(value = "jcpt")
    public List<Dictionary> getQybhByQymc(String qymc) {
        return qyjbxxDao.getQybhByQymc(qymc);
    }

    @Override
    @DataSource(value = "jcpt")
    public List<Qyfaxx> getQyfaxxs(String qybh) {
        return qyjbxxDao.getQyfaxxs(qybh);
    }

    @Override
    public int updateQyfaxx(String qybh, String vid) {
        return qyjbxxDao.updateQyfaxx(qybh,vid);
    }

    @Override
    public Qyjbxx selectOneByQybh(String qybh) {
        return qyjbxxDao.selectByQybh(qybh);
    }


}