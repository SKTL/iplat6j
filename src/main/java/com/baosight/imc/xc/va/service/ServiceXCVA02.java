package com.baosight.imc.xc.va.service;

import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceBase;

/**
 * @author: lx
 * @date: 2024/10/21 14:24
 * @description: 船批信息service
 */
public class ServiceXCVA02 extends ServiceBase {
    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        return super.initLoad(inInfo);
    }

    @Override
    public EiInfo query(EiInfo inInfo) {
        return super.query(inInfo);
    }

    @Override
    public EiInfo insert(EiInfo inInfo) {
        return super.insert(inInfo);
    }

    @Override
    public EiInfo update(EiInfo inInfo) {
        return super.update(inInfo);
    }

    @Override
    public EiInfo delete(EiInfo inInfo) {
        return super.delete(inInfo);
    }

    public EiInfo getWharfCode(EiInfo inInfo){
        return super.query(inInfo,"XCVA02.getWharfCode");
    }
}
