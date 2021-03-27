package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.core.page.TableDataInfo;
import com.xinyuan.ms.entity.SysPost;
import com.xinyuan.ms.service.impl.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/system/post")
public class PostController extends BaseController{
    String prefix = "post";

    @Autowired
    PostService postService;
    /**
     * 跳转岗位页面
     * @return
     */
    @GetMapping()
    public String operlog(){
        return prefix + "/post";
    }

    /**
     * 获取岗位列表
     * @param post
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysPost post){
        List<SysPost> list = postService.postList();
        return getDataTable(list);
    }
}

