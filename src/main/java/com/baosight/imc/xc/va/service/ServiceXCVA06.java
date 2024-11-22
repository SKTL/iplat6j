package com.baosight.imc.xc.va.service;

import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceBase;
import com.baosight.imc.xc.va.common.GetCamera;

/**
 * @author: lx
 * @date: 2024/10/31 15:37
 * @description: 视频监控
 */
public class ServiceXCVA06 extends ServiceBase {
    GetCamera newCamera = new GetCamera("9b62036cbef74eae8ddf7570f9330f1c");
    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        inInfo = super.initLoad(inInfo);
        return inInfo;
    }

    public EiInfo getCameraPreviewURL(EiInfo inInfo) {
        if ("1".equals(newCamera.GetCameraOnline())) {
            inInfo.set("previewUrl", newCamera.GetCameraPreviewURL());
        } else {
            inInfo.set("previewUrl", newCamera.GetCameraPlayBackURL("20241114120000", "20241115120000"));
        }
        return inInfo;
    }
    public EiInfo getOnlineStates(EiInfo inInfo){
        inInfo.set("onlineStates", newCamera.GetCameraOnline());
        return inInfo;
    }
}
