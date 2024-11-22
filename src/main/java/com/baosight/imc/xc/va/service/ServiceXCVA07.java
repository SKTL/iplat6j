package com.baosight.imc.xc.va.service;

import com.baosight.imc.xc.va.common.GetCamera;
import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceBase;

/**
 * @author: lx
 * @date: 2024/11/5 11:08
 * @description: 历史视频
 */
public class ServiceXCVA07 extends ServiceBase {
    GetCamera newCamera = new GetCamera("9b62036cbef74eae8ddf7570f9330f1c");
    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        return super.initLoad(inInfo);
    }

    @Override
    public EiInfo query(EiInfo inInfo) {
        EiInfo outInfo = super.query(inInfo, "XCVA02.queryHistoryVideo");
        return outInfo;
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
    
    public EiInfo getCameraPreviewPlayBackURL(EiInfo inInfo) {
            inInfo.set("previewUrl", newCamera.GetCameraPlayBackURL("20241114120000", "20241115120000"));
        return inInfo;
    }
}
