package com.baosight.imc.xb.vb.service;

import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceBase;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

/**
 * 违规记录管理
 */
public class ServiceXBVB0101 extends ServiceBase {

    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        return super.initLoad(inInfo);
    }

    @Override
    public EiInfo query(EiInfo inInfo) {
        EiInfo outInfo = super.query(inInfo, "XBVB0101.getModelInfo");
        return outInfo;
    }

    @Override
    public EiInfo insert(EiInfo inInfo) {
        return super.insert(inInfo);
    }

    @Override
    public EiInfo update(EiInfo inInfo) {
        return super.update(inInfo,"XBVB0101.updateConfirmOpinionById");
    }


}
