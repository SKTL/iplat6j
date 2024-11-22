package com.baosight.imc.xb.vb.service;

import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceBase;

/**
 * 违规记录管理
 */
public class ServiceXBVB0103 extends ServiceBase {

    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        return super.initLoad(inInfo);
    }

    @Override
    public EiInfo query(EiInfo inInfo) {
        EiInfo outInfo = super.query(inInfo, "XBVB0103.queryWarnInfo");
        return outInfo;
    }


}
