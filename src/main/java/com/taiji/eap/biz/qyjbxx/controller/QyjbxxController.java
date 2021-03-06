package com.taiji.eap.biz.qyjbxx.controller;

import com.github.pagehelper.PageInfo;
import com.taiji.eap.biz.qyjbxx.bean.Qyfaxx;
import com.taiji.eap.biz.qyjcxx.bean.Qyjcxx;
import com.taiji.eap.common.base.BaseController;
import com.taiji.eap.biz.qyjbxx.bean.Qyjbxx;
import com.taiji.eap.biz.qyjbxx.service.QyjbxxService;
import com.taiji.eap.common.dictionary.annotation.DictionaryResponse;
import com.taiji.eap.common.http.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("qyjbxx")
public class QyjbxxController extends BaseController{

    @Autowired
    private QyjbxxService qyjbxxService;

    @GetMapping(value = "list")
    @ResponseBody
    public PageInfo<Qyjbxx> list(Integer pageNum,Integer pageSize,String searchText){
        PageInfo<Qyjbxx> pageInfo = null;
        try {
            pageInfo = qyjbxxService.list(pageNum,pageSize,searchText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }
    @PostMapping(value = "add")
    @ResponseBody
    public Response<String> add(Qyjbxx qyjbxx){
        try {
            int k = qyjbxxService.insert(qyjbxx);
            if(k>0){
                return renderSuccess("添加成功");
            }else {
                return renderError("失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return renderError(e.getMessage());
        }
    }

    @PostMapping(value = "edit")
    @ResponseBody
    public Response<String> edit(Qyjbxx qyjbxx){
        try {
            int k = qyjbxxService.updateByPrimaryKey(qyjbxx);
            if(k>0){
                return renderSuccess("修改成功");
            }else {
                return renderError("失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return renderError(e.getMessage());
        }
    }

    @PostMapping(value = "delete")
    @ResponseBody
    public Response<String> delete(Long id){
        try {
            int k = qyjbxxService.deleteByPrimaryKey(id);
            if(k>0){
                return renderSuccess("删除成功");
            }else {
                return renderError("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return renderError(e.getMessage());
        }
    }

    @PostMapping(value = "spider")
    @ResponseBody
    public Response<String> spider(String qybh,String startDate,String endDate){
        try {
            qyjbxxService.spider(qybh,startDate,endDate);
            return renderSuccess("爬取成功");
        }catch (Exception e){
            e.printStackTrace();
            return renderError(e.getMessage());
        }
    }

    @GetMapping(value = "selectOne")
    @ResponseBody
    public Response<Qyjbxx> selectOne(Long id){
         try {
            return renderSuccess(qyjbxxService.selectByPrimaryKey(id));
         } catch (Exception e) {
             e.printStackTrace();
             return renderError(e.getMessage());
          }
    }

    @GetMapping(value = "selectOneByQybh")
    @ResponseBody
    public Response<Qyjbxx> selectOneByQybh(String qybh){
        try {
            return renderSuccess(qyjbxxService.selectOneByQybh(qybh));
        } catch (Exception e) {
            e.printStackTrace();
            return renderError(e.getMessage());
        }
    }

    @GetMapping(value = "selectQyfaxx")
    @ResponseBody
    public Response<List<Qyfaxx>> selectQyfaxx(String qybh){
        return renderSuccess(qyjbxxService.getQyfaxxs(qybh));
    }

    @PostMapping(value = "updateQyfaxx")
    @ResponseBody
    public Response<String> updateQyfaxx(String qybh,String vid){
        int k = qyjbxxService.updateQyfaxx(qybh,vid);
        if(k>0){
           return renderSuccess("修改成功");
        }else {
            return renderError("修改失败");
        }
    }
}