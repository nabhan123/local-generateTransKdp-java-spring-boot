package com.jamkrindo.generate.generatesertfkatspr.response;

import com.jamkrindo.generate.generatesertfkatspr.dtos.AuthDto;
import com.jamkrindo.generate.generatesertfkatspr.dtos.PengajuanKlaimDto;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {

    public static Map<String,Object> customeResponseSpr(boolean success, String message, int responseCode, Map<String, Object> data) throws Exception{
        Map<String,Object> res = new HashMap<>();
        res.put("Success", success);
        res.put("Message", message);
        res.put("ResponseCode", responseCode);
        res.put("Data", data);
        return res;
    }
    public static Map<String,Object> failedResponse(boolean failed,String message,int responseCode, List<Map<String,Object>> data){

        Map<String,Object> map = new HashMap<>();
        map.put("failed",failed);
        map.put("responseCode",responseCode);
        map.put("message",message);
        return map;
    }
    public static Map<String,Object> successResponse(boolean success,String message,int responseCode){

        Map<String,Object> map = new HashMap<>();
        map.put("success",success);
        map.put("responseCode",responseCode);
        map.put("message",message);
        return map;
    }

}
