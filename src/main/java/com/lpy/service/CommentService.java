package com.lpy.service;


import com.lpy.dao.CommentDao;
import com.lpy.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lipeiyuan on 2018/7/24.
 */
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    public List<Comment> getCommentByEntity(int entityId , int entityType) {
        return commentDao.selectByEntity(entityId,entityType);
    }

    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    public int getCommentCount(int entityId , int entityType) {
        return commentDao.getCommentCount(entityId,entityType);
    }

}
