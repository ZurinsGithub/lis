package com.lis.listest.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lis.listest.services.APIService;
import com.lis.listest.services.ManageService;
import com.lis.listest.services.StationService;
import com.lis.listest.tools.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Station {
    @Autowired
    StationService stationService;
    @Autowired
    APIService apiService;
    @Autowired
    ManageService manageService;

    @PostMapping("/testproject/apis/getstationorders")
    public String getstationorders(@RequestBody JSONObject jsonObject){
        return stationService.getOrders(jsonObject).toString();
    }

    @PostMapping("/testproject/station/createtransport")
    public String createtransport(@RequestBody JSONObject obj){
        return stationService.CreateTransport(obj);
    }

    @PostMapping("/testproject/station/gettransport")
    public String gettransport(@RequestBody JSONObject obj){
        return stationService.getTransport(obj.getInteger("stationid")).toString();
    }

    @PostMapping("/testproject/station/deletetransport")
    public String deletetransport(@RequestBody JSONObject obj){
        return stationService.DeleteTransportById(obj.getInteger("id"));
    }

    @PostMapping("/testproject/station/finishorder")
    public String finishorder(@RequestBody JSONObject obj){
        return stationService.finishorder(obj.getString("number"));
    }

    @PostMapping("/testproject/station/get")
    public String getStation(@RequestBody JSONObject obj){
        return stationService.GetStation(obj.getInteger("id"));
    }

    @PostMapping("/testproject/station/modify")
    public String ModifyStation(@RequestBody JSONObject obj){
        return stationService.ModifyStation(obj);
    }
    @PostMapping("/testproject/station/accept")
    public String Accept(@RequestBody JSONObject obj){
        apiService.ModifyState(obj);
        apiService.addSourceRecord(obj.getString("number"),obj.getString("sourcetext"));
        return "1";
    }

    @PostMapping("/testproject/station/sort")
    public String Sort(@RequestBody JSONObject obj){
        apiService.ModifyStateInBuntch(obj);
        JSONObject json = JSON.parseObject("{\n" +
                "                \"type\": \"transportinfo\",\n" +
                "                \"params\": {\n" +
                "                    \"transid\": "+ obj.getJSONObject("transportinfo").getInteger("id") +"\n" +
                "                },\n" +
                "                \"mark\": \"\",\n" +
                "                \"pagename\": \"transportinfo\"\n" +
                "            }");
        Log.l("TransportSource:\n" + json.toString());
        JSONArray ordernumbers = obj.getJSONArray("ordernumbers");
        String sourcetext = obj.getString("sourcetext");
        apiService.addSourceRecordInBuntch(ordernumbers,sourcetext,json);
        return "1";
    }
}
