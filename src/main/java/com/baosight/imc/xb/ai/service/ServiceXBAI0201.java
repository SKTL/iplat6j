package com.baosight.imc.xb.ai.service;

import com.baosight.elim.common.utils.GlobalUtils;
import com.baosight.imc.interfaces.vz.bm.domain.VZBM1300;
import com.baosight.imc.interfaces.vz.bm.service.ServiceVZBM1300;
import com.baosight.imc.xb.ai.domain.XBAI02;
import com.baosight.imc.xb.ai.domain.XBAI0201;
import com.baosight.imc.xb.ai.domain.XBAI0202;
import com.baosight.iplat4j.core.ei.EiConstant;
import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceEPBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceXBAI0201 extends ServiceEPBase {

    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        List<XBAI0202> evaluates = dao.query("XBAI0202.query", new HashMap<>(), 0, -999999);
        Map<String, String> evaluateMap = evaluates.stream().collect(Collectors.toMap(XBAI0202::getEvaluateId, XBAI0202::getEvaluateType));
        EiInfo eiInfo = super.initLoad(inInfo, new XBAI02());
        eiInfo.set("evaluateMap", evaluateMap);
        return eiInfo;
    }

    @Override
    public EiInfo query(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        inInfo.getBlock(EiConstant.queryBlock).setCell(0, "delFlag", 0);
        inInfo.getBlock(EiConstant.queryBlock).setCell(0, "role", "2");
        outInfo = super.query(inInfo, "XBAI0201.query02", new XBAI0201());
        return outInfo;
    }

    @Override
    public EiInfo delete(EiInfo inInfo) {
        return super.update(inInfo, "XBAI0201.delete");
    }

    @Override
    public EiInfo update(EiInfo inInfo) {
        return super.update(inInfo, "XBAI03.update");
    }

    @Override
    public EiInfo insert(EiInfo inInfo) {
        return super.insert(inInfo, "XBAI03.insert");
    }


}
