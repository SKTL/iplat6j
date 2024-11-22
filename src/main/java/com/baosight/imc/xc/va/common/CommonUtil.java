package com.baosight.imc.xc.va.common;

import com.baosight.iplat4j.core.util.DateUtils;

import java.util.HashMap;
import java.util.Map;

import com.baosight.iplat4j.core.data.DaoEPBase;
import com.baosight.iplat4j.core.web.threadlocal.UserSession;
public class CommonUtil {
    public static void fillRecCreater(DaoEPBase bean) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("recCreateTime", DateUtils.curDateTimeStr14());
        map.put("recCreator", UserSession.getLoginName());
        map.put("recCreatorName", UserSession.getLoginCName());
        map.put("recReviseTime", DateUtils.curDateTimeStr14());
        map.put("recRevisor", UserSession.getLoginName());
        map.put("recRevisorName", UserSession.getLoginCName());
        bean.fromMap(map);
    }
    
    public static void fillRevisor(DaoEPBase bean) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("recReviseTime", DateUtils.curDateTimeStr14());
        map.put("recRevisor", UserSession.getLoginName());
        map.put("recRevisorName", UserSession.getLoginCName());
        bean.fromMap(map);
    }
}
