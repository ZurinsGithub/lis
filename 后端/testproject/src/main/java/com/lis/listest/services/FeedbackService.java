package com.lis.listest.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lis.listest.dao.FeedbackDao;
import com.lis.listest.entities.FeedbackEntity;
import com.lis.listest.entities.MsgsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired
    FeedbackDao feedbackDao;
    @Autowired
    MsgsService msgsService;

    public String SendFreeback(JSONObject jsonObject) {
        // 添加到数据库
        AddFreeback(jsonObject);
        // 添加到消息
        msgsService.AddMsg(JSON.parseObject("{\n" +
                "    \"id\": 2,\n" +
                "    \"sendername\": \"反馈系统\",\n" +
                "    \"receiverid\": " + jsonObject.getJSONObject("info").getInteger("receiverid") + ",\n" +
                "    \"text\": { \"title\": \"订单反馈通知\", \"content\": \"我们已收到您的订单反馈信息，将会及时进行处理；稍后可能会有工作人员联系您，请保持联系，谢谢！\" },\n" +
                "    \"state\": \"unread\",\n" +
                "    \"remark\": {}\n" +
                "  }"));
        return "1";
    }

    public void AddFreeback(JSONObject jsonObject) {
        FeedbackEntity feedbackEntity = FeedbackEntity.fromjsonobj(jsonObject);
        feedbackDao.save(feedbackEntity);
    }
}
