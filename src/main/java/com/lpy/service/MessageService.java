package com.lpy.service;

import com.lpy.dao.MessageDao;
import com.lpy.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lipeiyuan on 2018/7/26.
 */
@Service
public class MessageService {

    @Autowired
    MessageDao messageDao;

    public int addMessage(Message message) {
        return messageDao.addMessage(message);
    }

    public List<Message> getAllByConversationId(String conversationId) {
        return messageDao.selectAllByConversationId(conversationId);
    }

    public List<Message> getLimitedMessageByConversationId(String conversationId , int offset , int limit) {
        return messageDao.selectLimitedMessageByConversationId(conversationId , offset , limit);
    }
}
