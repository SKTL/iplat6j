package com.baosight.imc.xc.ii.service;

import com.baosight.iplat4j.core.ei.EiConstant;
import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceBase;
import com.baosight.iplat4j.core.service.soa.XServiceManager;
import com.baosight.iplat4j.eu.dm.PlatFileUploadUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import com.baosight.iplat4j.core.util.DateUtils;
import com.baosight.eplat.shareservice.service.EServiceManager;
import com.baosight.imc.common.utils.ImcGlobalUtils;
import com.baosight.imc.common.utils.ImcServiceBase;
import com.baosight.imc.xc.va.common.CommonUtil;
import com.baosight.imc.xc.va.domain.XCVA01;

public class ServiceXCIIPYXB01 extends ServiceBase {

    /**
     * 服务标识号：
     * 接收违规图片接口
     * 
     * @param inInfo
     * @return
     */
    public EiInfo receiveCVImg(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        try {
            String payload = inInfo.getString("payload");
            JSONObject jsonObjectResult = JSONObject.fromObject(payload);
            //存储原图 
            String base64ImagStr = jsonObjectResult.getString("picture");
            String imgType = jsonObjectResult.getString("picType");
            imgType = "." + imgType;
            // 将字节数组写入文件
            byte[] buff = Base64.decodeBase64(base64ImagStr);
            File file = File.createTempFile("tmp", imgType);
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(buff);
            fout.close();
            // 调用接口上传对象服务器
            Map<String, String> inMap = new HashMap<>();
            String name = "CV" + jsonObjectResult.getString("alarm_number") + DateUtils.curDateMselStr17() + imgType;
            inMap.put("fileName", name);
            inMap.put("objectKey", name);
            inMap.put("configPathDefine", "CVIMGY");
            Map<String, String> uploadResult = PlatFileUploadUtils.uploadFile(file, inMap);
            //存储标记图片
            base64ImagStr = jsonObjectResult.getString("resPicture");
            imgType = jsonObjectResult.getString("resPicType");
            imgType = "." + imgType;
            // 将字节数组写入文件
            buff = Base64.decodeBase64(base64ImagStr);
            file = File.createTempFile("tmp", imgType);
            fout = new FileOutputStream(file);
            fout.write(buff);
            fout.close();
            // 调用接口上传对象服务器
            name = "CV" + jsonObjectResult.getString("alarm_number") + DateUtils.curDateMselStr17() + imgType;
            inMap.put("fileName", name);
            inMap.put("objectKey", name);
            inMap.put("configPathDefine", "CVIMG");
            uploadResult = PlatFileUploadUtils.uploadFile(file, inMap);
            //插入异常记录表
            XCVA01 txcva01 = new XCVA01();
            CommonUtil.fillRecCreater(txcva01);
            txcva01.setViolationRecordNo(name.substring(0,name.indexOf(".")));
            txcva01.setViolationType(jsonObjectResult.getString("alarm_number"));
            txcva01.setViolationTime(DateUtils.curDateTimeStr14());
            txcva01.setViolationImgUrl("http://imctest.baogang.info/imc-xb/"+uploadResult.get("docUrl").toString());
            //调用IECL接口发送报警信息
            EiInfo msgInfo = new EiInfo();
            msgInfo.setCell("result", 0, "violationRecodeNo", txcva01.getViolationRecordNo());
            msgInfo.setCell("result", 0, "violationMsg", "");
            msgInfo.setCell("result", 0, "violationTime", txcva01.getViolationTime());
            msgInfo.setCell("result", 0, "violationImgUrl", txcva01.getViolationImgUrl());
            // 按时间查询最近一条未完工的船批
            Map<String, String> inquMap = new HashMap<>();
            inquMap.put("violationTime", DateUtils.curDateTimeStr14());
            List<HashMap<String, Object>> shipList = dao.query("XCVA02.queryViolationShip", inquMap);
            if (shipList.size() > 0) {
                msgInfo.setCell("result", 0, "shipLotNo", shipList.get(0).get("shipLotNo"));
                msgInfo.setCell("result", 0, "wharfCode", shipList.get(0).get("wharfCode"));
                txcva01.setShipLotNo(shipList.get(0).get("shipLotNo").toString());//给异常记录赋值船批
                txcva01.setShipId(shipList.get(0).get("shipId").toString());
            } else {
                msgInfo.setCell("result", 0, "shipLotNo", "没有符合的船批");
                msgInfo.setCell("result", 0, "wharfCode", "00");
            }
            msgInfo.set(EiConstant.serviceId, "S_VP_TS_0176");
            msgInfo = EServiceManager.call(msgInfo, ImcGlobalUtils.getToken());
            if (msgInfo.getStatus() < EiConstant.STATUS_DEFAULT) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg(msgInfo.getMsg());
                return outInfo;
            }
            dao.insert("XCVA01.insert", txcva01);
            outInfo.setStatus(EiConstant.STATUS_SUCCESS);
        } catch (Exception e) {
            outInfo.setStatus(EiConstant.STATUS_FAILURE);
            outInfo.setMsg(e.getMessage());
        }
        return outInfo;
    }
}
