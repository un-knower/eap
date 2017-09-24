package com.taiji.eap.common.dictionary.controller;

import com.github.pagehelper.PageInfo;
import com.taiji.eap.common.base.BaseController;
import com.taiji.eap.common.dictionary.bean.Dictionary;
import com.taiji.eap.common.dictionary.service.DictionaryService;
import com.taiji.eap.common.http.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("dictionary")
public class DictionaryController extends BaseController{

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping(value = "getPath")
    @ResponseBody
    public Response<List<Dictionary>> getPath(Long dicId){
        List<Dictionary> dictionaries = null;
        try {
            return renderSuccess(dictionaryService.getPath(dicId));
        } catch (Exception e) {
            e.printStackTrace();
            return renderError(e.getMessage());
        }
    }

    @GetMapping(value = "list")
    @ResponseBody
    public PageInfo<Dictionary> list(Long parentId,Integer pageNum,Integer pageSize,String searchText){
        PageInfo<Dictionary> pageInfo = null;
        try {
            pageInfo = dictionaryService.listByPid(parentId,pageNum,pageSize,searchText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @PostMapping(value = "add")
    @ResponseBody
    public Response<String> add(Dictionary dictionary){
        dictionary.setCreateTime(new Date());
        dictionary.setUpdateTime(new Date());
        dictionary.setValid("1");
        dictionary.setCreater(1L);
        try {
            int k = dictionaryService.insert(dictionary);
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
    public Response<String> edit(Dictionary dictionary){
        dictionary.setUpdateTime(new Date());
        dictionary.setValid("1");
        dictionary.setCreater(1L);
        try {
            int k = dictionaryService.updateByPrimaryKey(dictionary);
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
    public Response<String> delete(Long dicId){
        try {
            int k = dictionaryService.deleteByPrimaryKey(dicId);
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

    @GetMapping(value = "selectOne")
    @ResponseBody
    public Response<Dictionary> selectOne(Long dicId){
        try {
            return renderSuccess(dictionaryService.selectByPrimaryKey(dicId));
        } catch (Exception e) {
            e.printStackTrace();
            return renderError(e.getMessage());
        }
    }
}
