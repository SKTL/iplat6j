package com.baosight.imc.xc.va.service;

import com.baosight.imc.xc.va.common.CommonUtil;
import com.baosight.imc.xc.va.domain.XCVA01;
import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceBase;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 违规记录管理
 */
public class ServiceXCVA01 extends ServiceBase {

    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        return super.initLoad(inInfo);
    }

    @Override
    public EiInfo query(EiInfo inInfo) {
        EiInfo outInfo = super.query(inInfo, "XCVA01.getPageList");
        return outInfo;
    }

    @Override
    public EiInfo insert(EiInfo inInfo) {
        return super.insert(inInfo);
    }

    @Override
    public EiInfo update(EiInfo inInfo) {
        int rowCount = inInfo.getBlock("result").getRowCount();
        for (int i = 0; i < rowCount; i++) {
            XCVA01 xcva01 = new XCVA01();
            xcva01.fromMap(inInfo.getBlock("result").getRow(i));
            xcva01.setModifyFlag("1");
            CommonUtil.fillRevisor(xcva01);
            dao.update("XCVA01.updateConfirmOpinionById",xcva01);
        }
        return inInfo;
    }

    @Override
    public EiInfo delete(EiInfo inInfo) {
        return super.delete(inInfo);
    }

    public EiInfo queryConfirmOpinionType(EiInfo inInfo){
        return super.query(inInfo,"XCVA01.queryConfirmOpinionType");
    }

    public EiInfo queryViolationType(EiInfo inInfo){
        return super.query(inInfo,"XCVA01.queryViolationType");
    }

    public EiInfo queryAll(EiInfo inInfo) {
        List<Map> data = dao.query("XCVA01.queryAll", inInfo.getBlock("inqu_status").getRow(0));
        EiInfo outInfo = new EiInfo();
        Gson gson = new Gson();
        String dataStr = gson.toJson(data);
        outInfo.set("data",dataStr);
        return outInfo;
    }

    public EiInfo updateById(EiInfo inInfo) {
        Map<String,String> data = (Map)inInfo.get("data");
        XCVA01 xcva01 = new XCVA01();
        xcva01.setViolationRecordNo(data.get("violationRecordNo"));
        xcva01.setViolationType(data.get("violationType"));
        xcva01.setConfirmOpinionType(data.get("confirmOpinionType"));
        xcva01.setModifyFlag("1");
        CommonUtil.fillRevisor(xcva01);
        int update = dao.update("XCVA01.updateById", xcva01);
        EiInfo outInfo = new EiInfo();
        if(update < 0){
            outInfo.setStatus(-1);
        }
        return outInfo;
    }
}
