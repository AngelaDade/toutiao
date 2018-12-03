package com.lpy.controller;

import com.lpy.constant.EntityType;
import com.lpy.model.Comment;
import com.lpy.model.HostHolder;
import com.lpy.model.News;
import com.lpy.service.CommentService;
import com.lpy.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by lipeiyuan on 2018/7/24.
 */
@Controller
public class CommentController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    NewsService newsService;

    @RequestMapping(value = {"/addComment"} , method = RequestMethod.POST)
    @ResponseBody
    public News addComment(@RequestParam("newsId") int newsId ,
                           @RequestBody() String content) {
        Comment comment  = new Comment();
        comment.setUserId(hostHolder.getUser().getId());
        comment.setContent(content);
        comment.setEntityId(EntityType.ENTITY_NEWS);
        comment.setEntityId(newsId);
        comment.setCreateDate(new Date());
        comment.setStatus(0);
        commentService.addComment(comment);

        //更新news里评论的数量
        int count = commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
        newsService.updateCommentCount(comment.getEntityId(),count);

        //怎么异步化

        return newsService.getById(comment.getEntityId());



    }


}
