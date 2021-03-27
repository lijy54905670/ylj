package com.xinyuan.ms.service.impl;

import com.xinyuan.ms.entity.SysPost;
import com.xinyuan.ms.mapper.PostRepository;
import com.xinyuan.ms.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService extends BaseService<PostRepository,SysPost,Long> {

    public List<SysPost> postList(){
        List<SysPost> sysPosts = bizRepository.postList();
        return sysPosts;
    }

}
