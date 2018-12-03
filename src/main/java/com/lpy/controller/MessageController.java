package com.lpy.controller;

import com.lpy.model.Message;
import com.lpy.service.MessageService;
import com.lpy.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lipeiyuan on 2018/7/26.
 */
@Controller
public class MessageController {

    @Autowired
    MessageService messageService;


    @RequestMapping(value = {"/addMessage"} , method = RequestMethod.POST)
    @ResponseBody
    public Map<String , Object> addMessage(@RequestParam(value = "fromId") int fromId,
                             @RequestParam(value = "toId") int toId,
                             @RequestBody String content) {

        Message message = new Message();

        message.setFromId(fromId);
        message.setToId(toId);
        message.setContent(content);
        message.setCreateDate(new Date());
        message.setConversationId(fromId < toId ? String.format("%d_%d",fromId,toId) : String.format("%d_%d",toId,fromId));

        messageService.addMessage(message);

        List<Message> messageList = messageService.getAllByConversationId(message.getConversationId());

        Map<String , Object> map = new HashMap<>();
        map.put("code",200);
        map.put("messageList",messageList);
        return map;


    }

    @RequestMapping(value = "/getConversation" , method = RequestMethod.GET)
    @ResponseBody
    public List<Message> getMessages(@RequestParam("conversationId") String conversationId,
                                     @RequestParam("limit") int limit,
                                     @RequestParam("offset") int offset) {
        return messageService.getLimitedMessageByConversationId(conversationId,offset,limit);
    }


}
