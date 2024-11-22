package com.baosight.imc.xc.ii.service;

import com.baosight.imc.xc.va.domain.XCVA02;
import com.baosight.imc.xc.va.domain.XCVA01;
import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceBase;
import com.baosight.iplat4j.ed.util.SequenceGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baosight.imc.xc.va.common.CommonUtil;

public class ServiceXCIIIECXB01 extends ServiceBase {

    public EiInfo receiveShipMsg(EiInfo inInfo) {
        XCVA02 txcva02 = new XCVA02();
        for (int i = 0; i < inInfo.getBlock("result").getRowCount(); i++) {
            txcva02.setShipLotNo(inInfo.getCellStr("result", i, "shipLotNo"));
            txcva02.setShipName(inInfo.getCellStr("result", i, "shipName"));
            txcva02.setWharfName(inInfo.getCellStr("result", i, "wharfName"));
            txcva02.setWharfCode(inInfo.getCellStr("result", i, "wharfCode"));
            txcva02.setStartTime(inInfo.getCellStr("result", i, "startTime"));
            txcva02.setEndTime(inInfo.getCellStr("result", i, "endTime"));
            txcva02.setCarriersName(inInfo.getCellStr("result", i, "carriersName"));
            Map<String, String> inquMap = new HashMap<String, String>();
            inquMap.put("shipLotNo", txcva02.getShipLotNo());
            inquMap.put("wharfCode", txcva02.getWharfCode());
            inquMap.put("finishFlag", "1");
            List<XCVA02> shipList = dao.query("XCVA02.query", inquMap);
            if (shipList.size() == 0) {
                CommonUtil.fillRecCreater(txcva02);
                txcva02.setShipId(txcva02.getWharfCode() + SequenceGenerator.getNextSequence("IMCXB_SHIP_ID"));
                dao.insert("XCVA02.insert", txcva02);
            } else {
                txcva02.setShipId(shipList.get(0).getShipId());
                dao.update("XCVA02.updateEndTime", txcva02);
            }

        }
        return inInfo;
    }

    public EiInfo receiveAlterBack(EiInfo inInfo) {
        XCVA01 txcva01 = new XCVA01();
        for (int i = 0; i < inInfo.getBlock("result").getRowCount(); i++) {
            txcva01.setViolationRecordNo(inInfo.getCellStr("result", i, "violationRecodeNo"));
            txcva01.setFeedbackOpinion(inInfo.getCellStr("result", i, "feedbackOpinion"));
            CommonUtil.fillRevisor(txcva01);
            dao.update("XCVA01.updateFeedBackOpinion", txcva01);
        }
        return inInfo;
    }
}
