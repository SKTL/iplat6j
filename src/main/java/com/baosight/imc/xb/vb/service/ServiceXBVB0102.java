package com.baosight.imc.xb.vb.service;

import com.baosight.iplat4j.core.ei.EiConstant;
import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceBase;
import com.baosight.iplat4j.core.util.DateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 违规记录管理
 */
public class ServiceXBVB0102 extends ServiceBase {

    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        return super.initLoad(inInfo);
    }

    @Override
    public EiInfo query(EiInfo inInfo) {
        EiInfo outInfo = super.query(inInfo, "XBVB0102.queryViolationInfo");
        return outInfo;
    }

    @Override
    public EiInfo update(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        List<Map<String,Object>> result = inInfo.getBlock("result").getRows();
        if(result.size()<=0){
            outInfo.setMsg("无有效数据 保存失败...!");
            outInfo.setStatus(EiConstant.STATUS_FAILURE);
            return outInfo;
        }
        for (Map<String, Object> map : result) {
            map.put("recReviseTime", DateUtils.curDateTimeStr14());
        }
        return super.update(inInfo,"XBVB0102.updateViolationInfo");
    }


    public EiInfo updateOpinion(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        try{
            Map<String,Object> paraMap= new HashMap<>();
            paraMap.put("recReviseTime", DateUtils.curDateTimeStr14());
            paraMap.put("uuid",inInfo.get("uuid"));
            paraMap.put("opinion",inInfo.get("opinion"));
            paraMap.put("suggestion",inInfo.get("suggestion"));
            dao.update("XBVB0102.updateViolationInfo",paraMap);
            outInfo.setStatus(EiConstant.STATUS_SUCCESS);
            outInfo.setMsg("保存成功...!");
        }catch (Exception e){
            outInfo.setStatus(EiConstant.STATUS_FAILURE);
            outInfo.setMsg(e.getMessage());
        }
        return outInfo;
    }

}
