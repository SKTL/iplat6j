package com.baosight.imc.xc.va.common;

import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class GetCamera {
    private  String cameraIndexCode="";
    // 设置OpenAPI接口的上下文
    final  String ARTEMIS_PATH = "/artemis";
    final  String contentType = "application/json";


    public GetCamera(String cameraIndexCode) {
        this.cameraIndexCode = cameraIndexCode;
        // 设置平台参数，根据实际情况,设置host appkey appsecret 三个参数
        ArtemisConfig.host = "video.baocloud.cn:443"; // artemis网关服务器ip端口
        ArtemisConfig.appKey = "20280067"; // 秘钥appkey
        ArtemisConfig.appSecret = "1l6fXEKckellWKZZzspm";// 秘钥appSecret
    }
    //获取直播链接
    public  String GetCameraPreviewURL() {
        /**
         * STEP3：设置接口的URI地址
         */
        final String previewURLsApi = ARTEMIS_PATH + "/api/video/v2/cameras/previewURLs";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);// 根据现场环境部署确认是http还是https
            }
        };
        /**
         * STEP5：组装请求参数
         */
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", cameraIndexCode);
        jsonBody.put("streamType", 0);
        jsonBody.put("protocol", "wss");
        jsonBody.put("transmode", 1);
        jsonBody.put("expand", "transcode=0");
        jsonBody.put("streamform", "ps");
        String body = jsonBody.toJSONString();
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType, null);// post请求application/json类型参数
        JSONObject resultJson = JSONObject.parseObject(result);
        return resultJson.getJSONObject("data").getString("url");
    }
    //获取回放链接
    public  String GetCameraPlayBackURL(String beginTime,String endTime) {
        /**
         * STEP3：设置接口的URI地址
         */
        final String previewURLsApi = ARTEMIS_PATH + "/api/video/v2/cameras/playbackURLs";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);// 根据现场环境部署确认是http还是https
            }
        };
        /**
         * STEP5：组装请求参数
         */
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", cameraIndexCode);
        jsonBody.put("protocol", "wss");
        jsonBody.put("recordLocation", "0");
        jsonBody.put("transmode", 1);
        jsonBody.put("beginTime", convertTimeStr(beginTime));
        jsonBody.put("endTime", convertTimeStr(endTime));
        String body = jsonBody.toJSONString();
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType, null);// post请求application/json类型参数
        JSONObject resultJson = JSONObject.parseObject(result);
        return resultJson.getJSONObject("data").getString("url");
    }

    // 获取摄像头是否在线
    public String GetCameraOnline() {
        /**
         * STEP3：设置接口的URI地址
         */
        final String previewURLsApi = ARTEMIS_PATH + "/api/nms/v1/online/camera/get";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);// 根据现场环境部署确认是http还是https
            }
        };
        /**
         * STEP5：组装请求参数
         */
        String[] cameraIndexCodes = new String[]{ cameraIndexCode};
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("indexCodes", cameraIndexCodes);
        String body = jsonBody.toJSONString();
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType, null);// post请求application/json类型参数
        JSONObject resultJson = JSONObject.parseObject(result);
        return resultJson.getJSONObject("data").getJSONArray("list").getJSONObject(0).getString("online");
    }
    public  String convertTimeStr(String str) {
        LocalDateTime dateTime = LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // 使用 ISO_DATE_TIME 格式化时间
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME)+".000+08:00";
    }
}
