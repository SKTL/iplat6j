package com.baosight.imc.xb.vb.service;

import com.alibaba.fastjson2.JSON;
import com.baosight.eplat.shareservice.service.EServiceManager;
import com.baosight.imc.common.utils.ImcGlobalUtils;
import com.baosight.imc.xc.va.common.CommonUtil;
import com.baosight.imc.xc.va.domain.XCVA01;
import com.baosight.iplat4j.core.data.id.UUIDHexIdGenerator;
import com.baosight.iplat4j.core.ei.EiConstant;
import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.service.impl.ServiceBase;
import com.baosight.iplat4j.core.service.soa.XServiceManager;
import com.baosight.iplat4j.core.util.DateUtils;
import com.baosight.iplat4j.core.util.StringUtils;
import com.baosight.iplat4j.eu.dm.PlatFileUploadUtils;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Param:
 * @Author: liuhang
 * @Date: 9:20 2024-11-15
 * @ServiceId:
 */
public class ServiceXBVB0104 extends ServiceBase {
    /**
     * @Description: TODO 现场安全感知-接收违规信息
     * @Param:
     * @Author: liuhang
     * @Date: 9:22 2024-11-15
     * @ServiceId: S_XB_VB_0104
     */
    public EiInfo receiveViolationInfo(EiInfo eiInfo) {

        EiInfo outInfo = new EiInfo();
        try {
            String payload = eiInfo.getString("payload");
            if(!StringUtils.isNotEmpty(payload)){
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg("无有效参数 接收失败...!");
                return outInfo;
            }

            Map paraMap = JSON.parseObject(payload,Map.class);
            String segNo = (String) paraMap.get("segNo"); // 账套
            String modelAreaCode = (String) paraMap.get("modelAreaCode"); // 模型作业区代码
            String modelAreaName = ""; // 模型作业区名称
            String violationType = (String) paraMap.get("violationType"); // 违规类型代码
            String violationName = ""; // 违规名称
            String modelCode = (String) paraMap.get("modelCode"); // 模型代码
            String modelName = "";// 模型名称
            String pictureCode = (String) paraMap.get("pictureCode"); // 图片代码
            String ip = (String) paraMap.get("ip"); // ip
            String violationTime = (String) paraMap.get("violationTime"); // 违规时间
            if(!StringUtils.isNotEmpty(segNo)){
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg("业务单元代码(segNo)必填...!");
                return outInfo;
            }

            List<Map<String, Object>> modelList = judgeCodeList("imc.vb.modelCode"); // 模型代码值集
            List<Map<String, Object>> violationList = judgeCodeList("imc.vb.violationType"); // 违规代码值集
            List<Map<String, Object>> modelAreaList = judgeCodeList("imc.vb.modelAreaCode"); // 模型作业区值集
            if (StringUtils.isNotEmpty(modelAreaCode)) {
                for (Map<String, Object> objectMap : modelAreaList) {
                    if (objectMap.get("itemCode").equals(modelAreaCode)) {
                        modelAreaName = (String) objectMap.get("itemCname");
                    }
                }
            }
            if (StringUtils.isNotEmpty(violationType)) {
                for (Map<String, Object> objectMap : violationList) {
                    if (objectMap.get("itemCode").equals(violationType)) {
                        violationName = (String) objectMap.get("itemCname");
                    }
                }
            }
            if (StringUtils.isNotEmpty(modelCode)) {
                for (Map<String, Object> objectMap : modelList) {
                    if (objectMap.get("itemCode").equals(modelCode)) {
                        modelName = (String) objectMap.get("itemCname");
                    }
                }
            }
            //存储原图
           /* String picture = (String) paraMap.get("picture");
            String picType = (String) paraMap.get("picType");
            picType = "." + picType;
            // 将字节数组写入文件
            byte[] buff = Base64.decodeBase64(picture);
            File file = File.createTempFile("tmp", picType);
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(buff);
            fout.close();
            // 调用接口上传对象服务器
            Map<String, String> inMap = new HashMap<>();
            String name = "CV" + paraMap.get("modelAreaCode") + DateUtils.curDateMselStr17() + picType;
            inMap.put("fileName", name);
            inMap.put("objectKey", name);
            inMap.put("configPathDefine", "VBIMG");
            Map<String, String> uploadResult = PlatFileUploadUtils.uploadFile(file, inMap);*/
            //存储标记图片
            String resPicture = (String) paraMap.get("resPicture");
            String resPicType = (String) paraMap.get("resPicType");
            resPicType = "." + resPicType;
            // 将字节数组写入文件
            byte[] buff = Base64.decodeBase64(resPicture);
            File file = File.createTempFile("tmp", resPicType);
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(buff);
            fout.close();
            // 调用接口上传对象服务器
            String name = "VB" + paraMap.get("modelAreaCode") + DateUtils.curDateMselStr17() + resPicType;
            Map<String, String> inMap = new HashMap<>();
            inMap.put("fileName", name);
            inMap.put("objectKey", name);
            inMap.put("configPathDefine", "VBIMG");
            Map<String, String> uploadResult = PlatFileUploadUtils.uploadFile(file, inMap);
            String docUrl = uploadResult.get("docUrl");
            if (!StringUtils.isNotEmpty(docUrl)) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg("图片上传失败...!");
                return outInfo;
            }
            //插入异常记录表
            Map<String,Object> map = new HashMap<>();
            map.put("uuid", UUIDHexIdGenerator.generate());
            map.put("recCreateTime",DateUtils.curDateTimeStr14());;
            map.put("segNo",segNo);
            map.put("modelAreaCode",modelAreaCode);
            map.put("modelAreaName",modelAreaName);
            map.put("violationType",violationType);
            map.put("violationName",violationName);
            map.put("modelCode",modelCode);
            map.put("modelName",modelName);
            map.put("pictureCode",pictureCode);
            map.put("violationTime",violationTime);
            map.put("ip",ip);
            map.put("violationImgUrl","http://imctest.baogang.info/imc-xb/" + uploadResult.get("docUrl").toString());
            dao.insert("XBVB0102.insertViolationInfo", map);
            outInfo.setStatus(EiConstant.STATUS_SUCCESS);
            outInfo.setMsg("图片保存成功...!");
        } catch (Exception e) {
            outInfo.setStatus(EiConstant.STATUS_FAILURE);
            outInfo.setMsg(e.getMessage());
        }
        return outInfo;
    }


    /**
     * 获取小代码值集
     * @param code
     * @return
     */
    public static List<Map<String,Object>> judgeCodeList(String code) {
        //准备传入参数
        EiInfo eiInfo = new EiInfo();
        //设置serviceId
        eiInfo.set(EiConstant.serviceId, "S_ED_36");
        //设置小代码值集
        eiInfo.set("codesetCode", code);
        //请求
        EiInfo outInfo = XServiceManager.call(eiInfo);
        List listValue = (List) outInfo.get(EiConstant.resultBlock);
        String s = com.alibaba.fastjson.JSON.toJSONString(listValue);
        //获取到返回的数据
        List<Object> list = com.alibaba.fastjson.JSON.parseArray(s);

        List<Map<String, Object>> listw = new ArrayList<Map<String, Object>>();
        for (Object object : list) {
            Map<String, Object> ageMap = new HashMap<String, Object>();
            Map<String, Object> ret = (Map<String, Object>) object;//取出list里面的值转为map
            listw.add(ret);
        }
        return listw;
    }

}
