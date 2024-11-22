package com.baosight.imc.xb.ai.service;

import com.alibaba.fastjson2.JSON;
import com.baosight.elim.common.utils.GlobalUtils;
import com.baosight.eplat.shareservice.service.EServiceManager;
import com.baosight.imc.common.utils.ImcGlobalUtils;
import com.baosight.imc.xb.ai.common.constant.AiConstant;
import com.baosight.imc.xb.ai.common.constant.MsgCodeConstants;
import com.baosight.imc.xb.ai.common.utils.CheckUtils;
import com.baosight.imc.interfaces.vz.bm.domain.VZBM1300;
import com.baosight.imc.interfaces.vz.bm.service.ServiceVZBM1300;
import com.baosight.imc.xb.ai.constant.AssistantCodeEnums;
import com.baosight.imc.xb.ai.domain.XBAI03;
import com.baosight.imc.xb.ai.domain.XBAI0301;
import com.baosight.iplat4j.core.ProjectInfo;
import com.baosight.iplat4j.core.data.id.UUIDHexIdGenerator;
import com.baosight.iplat4j.core.ei.EiConstant;
import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.ei.json.Json2EiInfo2;
import com.baosight.iplat4j.core.ioc.spring.PlatApplicationContext;
import com.baosight.iplat4j.core.service.impl.ServiceEPBase;
import com.baosight.iplat4j.core.util.DateUtils;
import com.baosight.iplat4j.core.util.UserUtil;
import com.baosight.iplat4j.core.web.threadlocal.UserSession;
import com.baosight.iplat4j.ed.util.SequenceGenerator;
import com.google.common.collect.Lists;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.pegdown.PegDownProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.util.*;

public class ServiceXBAI03 extends ServiceEPBase {
    public List<Map<String, String>> codes;

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ServiceXBAI03.class);

    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        EiInfo eiInfo = super.initLoad(inInfo, new XBAI03());
        return eiInfo;
    }

    @Override
    public EiInfo query(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        inInfo.getBlock(EiConstant.queryBlock).setCell(0, "delFlag", 0);
        List query = dao.query("XBAI03.queryManageUser", new HashMap<String, String>() {{
            put("empNo", UserUtil.getOUserId());
        }});
        if (CollectionUtils.isEmpty(query) || query.size() < 1) {
            inInfo.getBlock(EiConstant.queryBlock).setCell(0, "recCreator", UserUtil.getOUserId());
        }
        outInfo = super.query(inInfo, "XBAI03.query", new XBAI03());
        return outInfo;
    }

    @Override
    public EiInfo delete(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        List<String> uuidS = new ArrayList<>();
        for (int i = 0; i < inInfo.getBlock(EiConstant.resultBlock).getRowCount(); i++) {
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "recRevisor", UserUtil.getOUserId());
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "recRevisorName", UserSession.getLoginCName());
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "recRevisorTime", DateUtils.curDateMselStr17());
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "delFlag", "1");
            uuidS.add(inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "uuid"));
        }
        Map parm = new HashMap();
        parm.put("uuidS", uuidS);
        dao.delete("XBAI0301.delete", parm);
        return super.update(inInfo, "XBAI03.delete");
    }

    @Override
    public EiInfo update(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        Map weekConstants = new HashMap<String, String>() {{
            put("周一", "1");
            put("周二", "2");
            put("周三", "3");
            put("周四", "4");
            put("周五", "5");
            put("周六", "6");
            put("周日", "7");
        }};
        for (int i = 0; i < inInfo.getBlock(EiConstant.resultBlock).getRowCount(); i++) {
            String subscribeRevisor = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "subscribeRevisor");
            String uuid = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "uuid");
            String subscribeInfo = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "subscribeInfo");
            String subscribeFreq = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "subscribeFreq");
            String pushDate = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "pushDate");
            String pushMonth = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "pushMonth");
            String pushDay = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "pushDay");
            String pushHour = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "pushHour");
            try {
                if (subscribeInfo.getBytes("gbk").length > 1000) {
                    outInfo.setStatus(EiConstant.STATUS_FAILURE);
                    outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅信息超长最大为500个文字！"));
                    return outInfo;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            List<Map> query1 = dao.query("XBAI03.query", new HashMap<String, String>() {{
                put("subscribeInfoEqu", subscribeInfo);
                put("subscribeFreq", subscribeFreq);
                put("subscribeRevisor", subscribeRevisor);
                put("uuidNo", uuid);
            }});
            if (CollectionUtils.isNotEmpty(query1) || query1.size() > 0) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行修改数据重复请检查[订阅信息，订阅频率，订阅人]"));
                return outInfo;
            }
            if (StringUtils.isBlank(subscribeInfo)) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅频率不可为空"));
                return outInfo;
            }
            if (StringUtils.isBlank(subscribeFreq)) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅频信息不可为空"));
                return outInfo;
            }
            if (AiConstant.YEAR.equals(subscribeFreq)) {
                if (StringUtils.isBlank(pushMonth) || StringUtils.isBlank(pushDay) || StringUtils.isBlank(pushHour)) {
                    outInfo.setStatus(EiConstant.STATUS_FAILURE);
                    outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅频率为[年]时[月、日、时]不可为空"));
                    return outInfo;
                }
            }
            if (AiConstant.MONTH.equals(subscribeFreq)) {
                if (StringUtils.isBlank(pushDay) || StringUtils.isBlank(pushHour)) {
                    outInfo.setStatus(EiConstant.STATUS_FAILURE);
                    outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅频率为[月]时[日、时]不可为空"));
                    return outInfo;
                }
            }
            if (AiConstant.WEEK.equals(subscribeFreq)) {
                if (StringUtils.isBlank(pushHour) || StringUtils.isBlank(pushDate)) {
                    outInfo.setStatus(EiConstant.STATUS_FAILURE);
                    outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅频率为[周]时[周、时]不可为空"));
                    return outInfo;
                }
            }
            if (AiConstant.DAY.equals(subscribeFreq)) {
                if (StringUtils.isBlank(pushHour)) {
                    outInfo.setStatus(EiConstant.STATUS_FAILURE);
                    outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅频率为[日]时[时]不可为空"));
                    return outInfo;
                }
            }
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "recRevisor", UserUtil.getOUserId());
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "recRevisorName", UserSession.getLoginCName());
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "recRevisorTime", DateUtils.curDateMselStr17());
            Map parm = new HashMap();
            parm.put("uuid", inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "uuid"));
            parm.put("pushHour", inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "pushHour"));
            parm.put("subscribeInfo", inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "subscribeInfo"));
            int update = dao.update("XBAI0301.updateHour", parm);
            if (update != 1) {
                Map row = inInfo.getBlock(EiConstant.resultBlock).getRow(i);
                String date14 = DateUtils.curDateTimeStr14();
                String week = CheckUtils.DateTurnWeek(date14.substring(0, 8));
                String month = date14.substring(4, 6);
                String day = date14.substring(6, 8);
                String Hour = date14.substring(8, 10) + date14.substring(10, 12);
                //年|月，日
                if (AiConstant.YEAR.equals(subscribeFreq)) {
                    if (month.equals(pushMonth) && day.equals(pushDay)) {
                        XBAI0301 xbai0301 = new XBAI0301();
                        xbai0301.fromMap(row);
                        xbai0301.setStatus("0");
                        xbai0301.setParentUuid(xbai0301.getUuid());
                        xbai0301.setUuid(UUIDHexIdGenerator.generate().toString());
                        xbai0301.setRecCreateTime(DateUtils.curDateMselStr17());
                        dao.insert("XBAI0301.insert", xbai0301);
                    }
                }
                //月|日
                if (AiConstant.MONTH.equals(subscribeFreq)) {
                    if (day.equals(pushDay)) {
                        XBAI0301 xbai0301 = new XBAI0301();
                        xbai0301.fromMap(row);
                        xbai0301.setStatus("0");
                        xbai0301.setParentUuid(xbai0301.getUuid());
                        xbai0301.setUuid(UUIDHexIdGenerator.generate().toString());
                        xbai0301.setRecCreateTime(DateUtils.curDateMselStr17());
                        dao.insert("XBAI0301.insert", xbai0301);
                    }
                }
                //周|周
                if (AiConstant.WEEK.equals(subscribeFreq)) {
                    if (week.equals(weekConstants.get(week))) {
                        XBAI0301 xbai0301 = new XBAI0301();
                        xbai0301.fromMap(row);
                        xbai0301.setStatus("0");
                        xbai0301.setParentUuid(xbai0301.getUuid());
                        xbai0301.setUuid(UUIDHexIdGenerator.generate().toString());
                        xbai0301.setRecCreateTime(DateUtils.curDateMselStr17());
                        dao.insert("XBAI0301.insert", xbai0301);
                    }
                }
                //日|时
                if (AiConstant.DAY.equals(subscribeFreq)) {
                    if (Integer.valueOf(pushHour.replace(":", "")) >= Integer.valueOf(Hour)) {
                        XBAI0301 xbai0301 = new XBAI0301();
                        xbai0301.fromMap(row);
                        xbai0301.setStatus("0");
                        xbai0301.setParentUuid(xbai0301.getUuid());
                        xbai0301.setUuid(UUIDHexIdGenerator.generate().toString());
                        xbai0301.setRecCreateTime(DateUtils.curDateMselStr17());
                        dao.insert("XBAI0301.insert", xbai0301);
                    }
                }
            }
        }
        return super.update(inInfo, "XBAI03.update");
    }

    @Override
    public EiInfo insert(EiInfo inInfo) {
        List<String> unique = new ArrayList<>();
        Map weekConstants = new HashMap<String, String>() {{
            put("周一", "1");
            put("周二", "2");
            put("周三", "3");
            put("周四", "4");
            put("周五", "5");
            put("周六", "6");
            put("周日", "7");
        }};
        EiInfo outInfo = new EiInfo();
        List<XBAI0301> datas = new ArrayList<>();
        for (int i = 0; i < inInfo.getBlock(EiConstant.resultBlock).getRowCount(); i++) {
            String subscribeInfo = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "subscribeInfo");
            String subscribeFreq = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "subscribeFreq");
            String pushDate = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "pushDate");
            String pushMonth = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "pushMonth");
            String pushDay = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "pushDay");
            String pushHour = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "pushHour");
            String subscribeRevisor = inInfo.getBlock(EiConstant.resultBlock).getCellStr(i, "subscribeRevisor");
            try {
                if (subscribeInfo.getBytes("gbk").length > 1000) {
                    outInfo.setStatus(EiConstant.STATUS_FAILURE);
                    outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅信息超长最大为500个文字！"));
                    return outInfo;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            if (unique.contains(subscribeInfo + "-" + subscribeFreq + "-" + subscribeRevisor)) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行新增数据重复请检查[订阅信息，订阅频率，订阅人]"));
                return outInfo;
            }
            unique.add(subscribeInfo + "-" + subscribeFreq + "-" + subscribeRevisor);
            List<Map> query1 = dao.query("XBAI03.query", new HashMap<String, String>() {{
                put("subscribeInfoEqu", subscribeInfo);
                put("subscribeFreq", subscribeFreq);
                put("subscribeRevisor", subscribeRevisor);
            }});
            if (CollectionUtils.isNotEmpty(query1) || query1.size() > 0) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行新增数据重复请检查[订阅信息，订阅频率，订阅人]"));
                return outInfo;
            }
            if (StringUtils.isBlank(subscribeFreq)) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅频率不可为空"));
                return outInfo;
            }
            if (StringUtils.isBlank(subscribeInfo)) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅信息不可为空"));
                return outInfo;
            }
            if (AiConstant.YEAR.equals(subscribeFreq)) {
                if (StringUtils.isBlank(pushMonth) || StringUtils.isBlank(pushDay) || StringUtils.isBlank(pushHour)) {
                    outInfo.setStatus(EiConstant.STATUS_FAILURE);
                    outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅频率为[年]时[月、日、时]不可为空"));
                    return outInfo;
                }
                inInfo.getBlock(EiConstant.resultBlock).setCell(i, "pushDate", "");
            }
            if (AiConstant.MONTH.equals(subscribeFreq)) {
                if (StringUtils.isBlank(pushDay) || StringUtils.isBlank(pushHour)) {
                    outInfo.setStatus(EiConstant.STATUS_FAILURE);
                    outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅频率为[月]时[日、时]不可为空"));
                    return outInfo;
                }
                inInfo.getBlock(EiConstant.resultBlock).setCell(i, "pushDate", "");
                inInfo.getBlock(EiConstant.resultBlock).setCell(i, "pushMonth", "");
            }
            if (AiConstant.WEEK.equals(subscribeFreq)) {
                if (StringUtils.isBlank(pushHour) || StringUtils.isBlank(pushDate)) {
                    outInfo.setStatus(EiConstant.STATUS_FAILURE);
                    outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅频率为[周]时[周、时]不可为空"));
                    return outInfo;
                }
                inInfo.getBlock(EiConstant.resultBlock).setCell(i, "pushMonth", "");
                inInfo.getBlock(EiConstant.resultBlock).setCell(i, "pushDay", "");
            }
            if (AiConstant.DAY.equals(subscribeFreq)) {
                if (StringUtils.isBlank(pushHour)) {
                    outInfo.setStatus(EiConstant.STATUS_FAILURE);
                    outInfo.setMsg(errorString("VZBM0810002", "第" + (i + 1) + "行订阅频率为[日]时[时]不可为空"));
                    return outInfo;
                }
                inInfo.getBlock(EiConstant.resultBlock).setCell(i, "pushDay", "");
                inInfo.getBlock(EiConstant.resultBlock).setCell(i, "pushMonth", "");
                inInfo.getBlock(EiConstant.resultBlock).setCell(i, "pushDate", "");
            }
            Map parMap2 = new HashMap();
            parMap2.put("subscribeRevisor", subscribeRevisor);
            List<Map<String, String>> query = dao.query("XBAI03.queryUser", parMap2);
            if (CollectionUtils.isEmpty(query) || query.size() < 1) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg(errorString("VZBM0800105", "订阅工号不可为空，或不存在！"));
                return outInfo;
            }
            String subscribeName = query.get(0).get("subscribeName");
            String subscribeEmail = query.get(0).get("subscribeEmail");
            String subscribeMobile = query.get(0).get("subscribeMobile");
            String subscribeSegNo = query.get(0).get("subscribeSegNo");

            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "delFlag", 0); //记录删除标记
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "uuid", UUIDHexIdGenerator.generate().toString());
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "recCreator", UserSession.getUserId());
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "recCreatorName", UserSession.getLoginCName());
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "recCreateTime", DateUtils.curDateMselStr17());
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "recRevisor", UserSession.getUserId());
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "recRevisorName", UserSession.getLoginCName());
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "recRevisorTime", DateUtils.curDateMselStr17());
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "segNo", "");
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "archiveFlag", "");
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "subscribeName", subscribeName);
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "subscribeEmail", subscribeEmail);
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "subscribeMobile", subscribeMobile);
            inInfo.getBlock(EiConstant.resultBlock).setCell(i, "subscribeSegNo", subscribeSegNo);
            Map row = inInfo.getBlock(EiConstant.resultBlock).getRow(i);
            String date14 = DateUtils.curDateTimeStr14();
            String week = CheckUtils.DateTurnWeek(date14.substring(0, 8));
            String month = date14.substring(4, 6);
            String day = date14.substring(6, 8);
            String Hour = date14.substring(8, 10) + date14.substring(10, 12);
            //年|月，日
            if (AiConstant.YEAR.equals(subscribeFreq)) {
                if (month.equals(pushMonth) && day.equals(pushDay)) {
                    XBAI0301 xbai0301 = new XBAI0301();
                    xbai0301.fromMap(row);
                    xbai0301.setStatus("0");
                    xbai0301.setParentUuid(xbai0301.getUuid());
                    xbai0301.setUuid(UUIDHexIdGenerator.generate().toString());
                    xbai0301.setRecCreateTime(DateUtils.curDateMselStr17());
                    datas.add(xbai0301);
                }
            }
            //月|日
            if (AiConstant.MONTH.equals(subscribeFreq)) {
                if (day.equals(pushDay)) {
                    XBAI0301 xbai0301 = new XBAI0301();
                    xbai0301.fromMap(row);
                    xbai0301.setStatus("0");
                    xbai0301.setParentUuid(xbai0301.getUuid());
                    xbai0301.setUuid(UUIDHexIdGenerator.generate().toString());
                    xbai0301.setRecCreateTime(DateUtils.curDateMselStr17());
                    datas.add(xbai0301);
                }
            }
            //周|周
            if (AiConstant.WEEK.equals(subscribeFreq)) {
                if (week.equals(weekConstants.get(week))) {
                    XBAI0301 xbai0301 = new XBAI0301();
                    xbai0301.fromMap(row);
                    xbai0301.setStatus("0");
                    xbai0301.setParentUuid(xbai0301.getUuid());
                    xbai0301.setUuid(UUIDHexIdGenerator.generate().toString());
                    xbai0301.setRecCreateTime(DateUtils.curDateMselStr17());
                    datas.add(xbai0301);
                }
            }
            //日|时
            if (AiConstant.DAY.equals(subscribeFreq)) {
                if (Integer.valueOf(pushHour.replace(":", "")) >= Integer.valueOf(Hour)) {
                    XBAI0301 xbai0301 = new XBAI0301();
                    xbai0301.fromMap(row);
                    xbai0301.setStatus("0");
                    xbai0301.setParentUuid(xbai0301.getUuid());
                    xbai0301.setUuid(UUIDHexIdGenerator.generate().toString());
                    xbai0301.setRecCreateTime(DateUtils.curDateMselStr17());
                    datas.add(xbai0301);
                }
            }
        }
        dao.insertBatch("XBAI0301.insert", datas);
        return super.insert(inInfo, "XBAI03.insert");
    }


    public EiInfo getUserInformation(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        String subscribeRevisor = inInfo.getString("subscribeRevisor");
        if (!StringUtils.isBlank(subscribeRevisor)) {
            Map parMap2 = new HashMap();
            parMap2.put("subscribeRevisor", subscribeRevisor);
            List<Map<String, String>> query = dao.query("XBAI03.queryUser", parMap2);
            if (CollectionUtils.isEmpty(query) || query.size() < 1) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg(errorString("VZBM0800105", "订阅工号不可为空，或不存在！"));
                return outInfo;
            }
            String subscribeName = query.get(0).get("subscribeName");
            String subscribeEmail = query.get(0).get("subscribeEmail");
            String subscribeMobile = query.get(0).get("subscribeMobile");
            String subscribeSegNo = query.get(0).get("subscribeSegNo");
            String subscribeSegNoTemp = query.get(0).get("subscribeSegNoTemp");
            outInfo.setStatus(EiConstant.STATUS_SUCCESS);
            outInfo.set("subscribeName", subscribeName);
            outInfo.set("subscribeRevisor", subscribeRevisor);
            outInfo.set("subscribeEmail", subscribeEmail);
            outInfo.set("subscribeMobile", subscribeMobile);
            outInfo.set("subscribeSegNo", subscribeSegNo);
            outInfo.set("subscribeSegNoTemp", subscribeSegNoTemp);
        } else {
            outInfo.setStatus(EiConstant.STATUS_FAILURE);
            outInfo.setMsg("订阅工号不可为空");
            return outInfo;
        }
        return outInfo;
    }

    /**
     * 人员查询
     *
     * @param info
     * @return
     */
    public EiInfo queryUser(EiInfo info) {
        EiInfo outInfo = super.query(info, "XBAI03.queryUser", null, null, "inqu_user", "resultUser", "resultUser", false);
        return outInfo;
    }

    /**
     * 生成当天需要推送的信息，在每天定时任务00：00执行
     *
     * @param inInfo
     * @return
     * @serviceId:S_XB_AI_01
     */
    public EiInfo subscriptionPush(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        String date14 = DateUtils.curDateTimeStr14();
        String week = CheckUtils.DateTurnWeek(date14.substring(0, 8));
        String month = date14.substring(4, 6);
        String day = date14.substring(6, 8);
        String Hour = date14.substring(8, 10) + ":" + date14.substring(10, 12);
        Map weekConstants = new HashMap<String, String>() {{
            put("周一", "1");
            put("周二", "2");
            put("周三", "3");
            put("周四", "4");
            put("周五", "5");
            put("周六", "6");
            put("周日", "7");
        }};
        List<XBAI03> data = new ArrayList<>();
        Map queryParame = new HashMap();
        //查询年，月，日，时
        queryParame.put("subscribeFreq", AiConstant.YEAR);
        queryParame.put("pushMonth", month);
        queryParame.put("pushDay", day);
        List<XBAI03> queryYear = dao.queryAll("XBAI03.queryData", queryParame);
        if (CollectionUtils.isNotEmpty(queryYear) && queryYear.size() > 0) {
            data.addAll(queryYear);
        }
        queryParame.clear();
        //查询月，日，时
        queryParame.put("subscribeFreq", AiConstant.MONTH);
        queryParame.put("pushDay", day);
        List<XBAI03> queryMonth = dao.queryAll("XBAI03.queryData", queryParame);
        if (CollectionUtils.isNotEmpty(queryMonth) && queryMonth.size() > 0) {
            data.addAll(queryMonth);
        }
        queryParame.clear();
        //查询周，周，时
        queryParame.put("subscribeFreq", AiConstant.WEEK);
        queryParame.put("pushDate", weekConstants.get(week));
        List<XBAI03> queryWeek = dao.queryAll("XBAI03.queryData", queryParame);
        if (CollectionUtils.isNotEmpty(queryWeek) && queryWeek.size() > 0) {
            data.addAll(queryWeek);
        }
        queryParame.clear();
        //查询日，时
        queryParame.put("subscribeFreq", AiConstant.DAY);
        List<XBAI03> queryHour = dao.queryAll("XBAI03.queryData", queryParame);
        if (CollectionUtils.isNotEmpty(queryHour) && queryHour.size() > 0) {
            data.addAll(queryHour);
        }
        queryParame.clear();
        List<XBAI0301> datas = new ArrayList<>();
        data.forEach(xbai03 -> {
            XBAI0301 xbai0301 = new XBAI0301();
            xbai0301.fromMap(xbai03.toMap());
            xbai0301.setStatus("0");
            xbai0301.setParentUuid(xbai0301.getUuid());
            xbai0301.setUuid(UUIDHexIdGenerator.generate().toString());
            xbai0301.setRecCreateTime(DateUtils.curDateMselStr17());
            datas.add(xbai0301);
        });
        dao.insertBatch("XBAI0301.insert", datas);
        EiInfo sendInfo = new EiInfo();
        EiInfo eiInfo = sendMsg(sendInfo);
        return eiInfo;
    }

    /**
     * 发送邮件
     *
     * @param inInfo
     * @return
     * @serviceId:S_XB_AI_02
     */
    public EiInfo sendMsg(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        LocalTime currentTime = LocalTime.now(); // 获取当前时间
        LocalTime compareTime = LocalTime.of(0, 30); // 00:30
        if (currentTime.isBefore(compareTime)) {
            outInfo.setStatus(EiConstant.STATUS_SUCCESS);
        }
        String weekDay = getWeekDay(DateUtils.curDateStr8());
        //全部未执行任务
        List<XBAI0301> data = dao.query("XBAI0301.query", new HashMap<String, String>() {{
            put("delFlag", "0");
            put("status", "0");
            if ("2".equals(weekDay)) {
                put("msgType", AiConstant.MSG_TYPE_B);
            }
        }});
        String date14 = DateUtils.curDateTimeStr14();
        String Hour = date14.substring(8, 10) + ":" + date14.substring(10, 12);
        Map weekConstants = new HashMap<String, String>() {{
            put("周一", "1");
            put("周二", "2");
            put("周三", "3");
            put("周四", "4");
            put("周五", "5");
            put("周六", "6");
            put("周日", "7");
        }};
        List<String> empNo = new ArrayList<>();
        List<XBAI0301> xbai0301s = new ArrayList<>();
        data.forEach(xbai0301 -> {
            String pushHour = xbai0301.getPushHour();
            String[] pushHour1 = pushHour.split(":");
            String HH1 = pushHour1[0];
            String SS1 = pushHour1[1];
            String[] hour1 = Hour.split(":");
            String HH2 = hour1[0];
            String SS2 = hour1[1];
            if (pushHour.equals(Hour) || (HH1.equals(HH2) && Integer.valueOf(SS2) >= Integer.valueOf(SS1))) {
                EiInfo chatInfo = new EiInfo();
                chatInfo.set("content", xbai0301.getSubscribeInfo());
                //chatInfo.set(EiConstant.serviceName, "XBAI02");
                //chatInfo.set(EiConstant.methodName, "chat");
                //EiInfo call = XLocalManager.call(chatInfo);
                ServiceXBAI02 chatWithxunfeiApp = new ServiceXBAI02();
                try {
                    List<JSONObject> jsonObjects = chatWithxunfeiApp.chatWithXunfeiApp(chatInfo, AssistantCodeEnums.getCodeByIntention("2"));
                    System.out.println("contents1 = " + JSONObject.toJSONString(jsonObjects));
                    EiInfo call = handlePayloadList(jsonObjects);
                    if (call.getStatus() != EiConstant.STATUS_FAILURE) {
                        List<Map> contents = (List<Map>) call.get("contents");
                        StringBuffer content = new StringBuffer("");
                        StringBuffer str = new StringBuffer("");
                        System.out.println("contents = " + JSONObject.toJSONString(contents));
                        contents.forEach(map -> {
                            String contentType = MapUtils.getString(map, "contentType");
                            if (AiConstant.OUT_TYPE.equals(contentType)) {
                                str.append(MapUtils.getString(map, "content", "").replaceAll("\\[\\^\\d+\\^\\]", ""));
                            } else if (AiConstant.OUT_TYPE_OUT.equals(contentType)) {
                                Map map2 = (Map) map.get("content");
                                str.append(MapUtils.getString(map2, "content", "").replaceAll("\\[\\^\\d+\\^\\]", ""));
                                List<Map> resource = (List<Map>) map2.get("resource");
                                if (CollectionUtils.isNotEmpty(resource)) {
                                    String resource1 = getResource(resource);
                                    content.append(resource1.replaceAll("\\[\\^\\d+\\^\\]", ""));
                                }
                            }
                        });
                        String datas = mdToHtml(str.toString()) + content.toString();

                        if (StringUtils.isNotBlank(datas)) {
                            if (AiConstant.SEND_TYPE_EMAIL.equals(xbai0301.getSendType())) {
                                EiInfo eiInfo = sendEmail(Lists.newArrayList(xbai0301.getSubscribeEmail()), new ArrayList<String>(), "IMC: 数智南方订阅消息推送", new ArrayList<Map>(), datas, "true");
                                if (eiInfo.getStatus() == EiConstant.STATUS_SUCCESS) {
                                    xbai0301.setStatus("1");
                                    xbai0301.setSubscribeInfo(datas);
                                    xbai0301.setSendTime(DateUtils.curDateMselStr17());
                                    xbai0301.setIsRead(AiConstant.SEND_NO_READ);
                                    empNo.add(xbai0301.getSubscribeRevisorTemp());
                                    xbai0301s.add(xbai0301);
                                }
                            } else if (AiConstant.SEND_TYPE_iBSL.equals(xbai0301.getSendType())) {
                                productionMessages(Jsoup.parse(datas).text(), "IMC: 数智南方订阅消息推送", xbai0301.getSubscribeRevisor().split(","));
                                xbai0301.setStatus("1");
                                xbai0301.setSubscribeInfo(datas);
                                xbai0301.setSendTime(DateUtils.curDateMselStr17());
                                xbai0301.setIsRead(AiConstant.SEND_NO_READ);
                                empNo.add(xbai0301.getSubscribeRevisorTemp());
                                xbai0301s.add(xbai0301);
                            } else if (AiConstant.SEND_TYPE_IMC.equals(xbai0301.getSendType())) {
                                String text = Jsoup.parse(datas).text();
                                EiInfo eiInfo = sendMsg(text.length() > 5000 ? text.substring(0, 5000) : text, xbai0301.getSubscribeRevisor());
                                if (eiInfo.getStatus() == EiConstant.STATUS_SUCCESS) {
                                    xbai0301.setStatus("1");
                                    xbai0301.setSubscribeInfo(datas);
                                    xbai0301.setSendTime(DateUtils.curDateMselStr17());
                                    xbai0301.setIsRead(AiConstant.SEND_NO_READ);
                                    empNo.add(xbai0301.getSubscribeRevisorTemp());
                                    xbai0301s.add(xbai0301);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
        if (CollectionUtils.isNotEmpty(xbai0301s) && xbai0301s.size() > 0) {
            dao.updateBatch("XBAI0301.update", xbai0301s);
        }
        outInfo.setStatus(EiConstant.STATUS_SUCCESS);
        outInfo.setMsg("本次推送人员" + empNo.toString());
        return outInfo;
    }

    private String getResource(List<Map> resource) {
        StringBuffer str = new StringBuffer("<p>数据来源：</p><ul>");
        resource.forEach(map -> {
            String docName = MapUtils.getString(map, "docName");
            str.append("<li>" + docName);
            String sourceContent = MapUtils.getString(map, "sourceContent");
            str.append("<span  style=\"font-size: 12px;\"><br>" + sourceContent + "</span></li>");
        });
        str.append("</ul>");
        return str.toString();
    }

    /**
     * 邮件推送公共方法
     *
     * @param addresseeList 收件人
     * @param copyEmailList 抄送人
     * @param emailTitle    邮件主题
     * @param fileList      邮件附件 Map:fileUrl附件地址  fileName附件名称
     * @param message       邮件正文
     * @param isHtml        是否html
     * @return
     */
    public static EiInfo sendEmail(List<String> addresseeList, List<String> copyEmailList, String emailTitle, List<Map> fileList, String message, String isHtml) {
        EiInfo eiInfo = new EiInfo();
        eiInfo.set("emailList", addresseeList);//设置接收人
        eiInfo.set("subject", emailTitle);//设置邮件主题
        eiInfo.set("projectEname", ProjectInfo.getComponentName());//使用此接口的项目组件名称
        eiInfo.set("message", message);//设置正文
        eiInfo.set("copyEmailList", copyEmailList);//设置正文
        eiInfo.set("isHtml", isHtml);//设置正文
        eiInfo.set(EiConstant.serviceId, "S_EPLAT_12");
        EiInfo outInfo = EServiceManager.call(eiInfo, ImcGlobalUtils.getToken());
        return outInfo;
    }

    private String mdToHtml(String mdText) {
        PegDownProcessor processor = new PegDownProcessor();
        String html = processor.markdownToHtml(mdText);
        return html;
    }

    private EiInfo handlePayloadList(List<JSONObject> payloadList) {
        EiInfo outInfo = new EiInfo();
        Map<String, Object> contentMap = new HashMap();
        for (JSONObject payload : payloadList) {
            if (Objects.equals(payload.getJSONObject("choices").getString("status"), "0")) {
                outInfo.set("sessionId", payload.getString("sessionId"));
                outInfo.set("chatId", payload.getJSONObject("context").getString("chatId"));
            }
            JSONArray texts = payload.getJSONObject("choices").getJSONArray("text");
            if (!texts.isEmpty()) {
                for (int i = 0; i < texts.size(); i++) {
                    JSONObject o = texts.getJSONObject(i);
                    if (!"assistant".equals(o.getString("role"))) {
                        continue;
                    }
                    if ("text".equals(o.getString("content_type"))) {
                        String content = contentMap.get("text") != null ? String.valueOf(contentMap.get("text")) : "";
                        content += o.getString("content");
                        contentMap.put("text", content);
                    } else if ("progress".equals(o.getString("content_type"))) {
                        JSONObject content = o.getJSONObject("progress");
                        if (content.containsKey("skill")) {
                            JSONObject skill = content.getJSONObject("skill");
                            if (skill.containsKey("skillOutput")) {
                                try {
                                    JSONObject output = JSON.parseObject(skill.getString("skillOutput")).getJSONObject("output");
                                    contentMap.putAll(output);
                                } catch (Exception e) {
                                    contentMap.put("skill", skill.getString("skillOutput"));
                                }
                            }
                        }
                    } else {
                        contentMap.put(o.getString("content_type"), o.get(o.getString("content_type")));
                    }
                }
            }
            if (Objects.equals(payload.getJSONObject("choices").getString("status"), "2")) {
                List<Map<String, Object>> contents = new ArrayList<>();
                for (Map.Entry<String, Object> entry : contentMap.entrySet()) {
                    Map<String, Object> obj = new HashMap<>();
                    obj.put("contentType", entry.getKey());
                    obj.put("content", entry.getValue());
                    contents.add(obj);
                }
                outInfo.set("contents", contents);
            }
        }
        if (contentMap.containsKey("text")) {
            contentMap.put("text", String.valueOf(contentMap.get("text")).replaceAll("<end>", ""));
        }
        return outInfo;
    }

    /**
     * 调用平台方法获取是否是节假日
     *
     * @param dateString
     * @return 0 表示工作日，1 表示周末，2 表示节假日，3 查询失败。
     */
    private String getWeekDay(String dateString) {
        String url = PlatApplicationContext.getProperty(MsgCodeConstants.EPLAT_BAOCLOUD_URL);
        EiInfo info = new EiInfo();
        info.set("date", dateString);
        String xplatToken = ImcGlobalUtils.getToken();
        EiInfo outInfo = new EiInfo();
        String returnValue = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url + "/S_BE_CM_102");  // 域名 + /service + 服务号
            StringEntity requestEntity = new StringEntity(info.toJSONString(), "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            //控制参数放到header
            httpPost.setHeader("Xplat-Token", xplatToken); // Xplat-Token放到header头参数
            httpPost.setEntity(requestEntity);
            log("调用集团服务URL：" + url + "/S_BE_CM_102");
            returnValue = httpClient.execute(httpPost, responseHandler);
            log("调用集团服务returnValue：" + returnValue);
            net.sf.json.JSONObject jsonObjectRuslt = net.sf.json.JSONObject.fromObject(returnValue);
            outInfo = Json2EiInfo2.parse(String.valueOf(jsonObjectRuslt));
            return null == outInfo.get("flag") ? "" : outInfo.get("flag").toString();
        } catch (Exception e) {
            e.printStackTrace();
            log(GlobalUtils.strFmt("调用集团服务失败：{0}") + e);
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                log("调用集团服务失败：" + e.getMessage());
            }
        }
        return "";
    }

    /**
     * @param code
     * @param msg
     * @return
     */
    private String errorString(String code, String msg) {
        VZBM1300 queryMsg = new VZBM1300();
        queryMsg.setErrorNum(code);
        queryMsg.setFormEname("XBAI03");
        GlobalUtils.setCreatorProerty(queryMsg);
        return ServiceVZBM1300.getMessageTextByErrorNumAndRecord(new String[]{msg}, queryMsg);
    }

    /**
     * 智慧圈app通知生产服务
     *
     * @param msgContent
     * @param title
     * @param empCodes
     */
    public void productionMessages(String msgContent, String title, String[] empCodes) {
        EiInfo eiInfo = new EiInfo();
        Map sendMap = new HashMap();
        sendMap.put("msgContent", msgContent);
        sendMap.put("title", title);
        sendMap.put("empCodes", empCodes);
        if (null == empCodes || empCodes.length <= 0) {
            return;
        }
        String jsonString = JSONObject.toJSONString(sendMap);
        eiInfo.set(EiConstant.serviceId, "S_EPLAT_04");
        eiInfo.set("messageKey", "R_VZ_BM_8801");
        eiInfo.set("messageBody", jsonString);
        EiInfo outInfo = EServiceManager.call(eiInfo);
        if (outInfo.getStatus() < 1) {
            logger.error("当前消息【" + jsonString + "】生产失败");
        }
    }

    public EiInfo sendMsg(String content, String empCode) {
        EiInfo eiInfo = new EiInfo();
        String seqTypeId = "VZBM_TASK_ID";
        String sequence = SequenceGenerator.getNextSequence(seqTypeId);
        //消息ID由 系统-应用-日期(YYMMDD)和5位流水号组成   示例:  IMC-imc-fc-22092700018（注：用户可使用平台序列号定义(EDMDM2页面)进行自动生成。示例配置号：VZBM_TASK_ID）
        eiInfo.set("messageId", "IAI-" + "-imc-xb-" + sequence);//(必填.唯一)
        eiInfo.set("systemId", "IAI");//(必填)
        eiInfo.set("projectName", "imc-xb");//(必填)
        eiInfo.set("proxyType", "数智南方订阅消息推送");//(必填)
        eiInfo.set("messageLabel", "数智南方订阅消息推送");//(必填)
        eiInfo.set("messageContent", content);//(必填)
        eiInfo.set("codesetUrl", "web/XBAI03");//(必填)
        eiInfo.set("sendAuditor", "system");//(必填)
        eiInfo.set("receiveId", empCode);//(必填)
        eiInfo.set(EiConstant.serviceId, "S_VZ_BM_9002");
        EiInfo outInfo = EServiceManager.call(eiInfo, ImcGlobalUtils.getToken()); //非V6应用设置token httpPost.setHeader("Xplat-Token", xplatToken);
        return outInfo;
    }

    /**
     * 查询消息
     *
     * @param inInfo
     * @return
     * @ServiceId:S_XB_AI_0303
     */
    public EiInfo queryNoMsg(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        String userId = inInfo.getString("userId");
        if (StringUtils.isBlank(userId)) {
            outInfo.setStatus(EiConstant.STATUS_FAILURE);
            outInfo.setMsg(errorString("VZBM0800105", "查询时工号不可以为空！"));
            return outInfo;
        }
        Map queryParame = new HashMap();
        queryParame.put("subscribeRevisor", userId);
        queryParame.put("isRead", AiConstant.SEND_NO_READ);
        queryParame.put("status", "1");
        List<Map> query = dao.queryAll("XBAI0301.queryMsg", queryParame);
        outInfo.set("data", query);
        outInfo.set("count", query.size());
        outInfo.setStatus(EiConstant.STATUS_SUCCESS);
        return outInfo;
    }

    /**
     * 消息阅毕
     *
     * @param inInfo
     * @return
     * @ServiceId:S_XB_AI_0304
     */
    public EiInfo updateYesMsg(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        String uuid = inInfo.getString("uuid");
        if (StringUtils.isBlank(uuid)) {
            outInfo.setStatus(EiConstant.STATUS_FAILURE);
            outInfo.setMsg(errorString("XBAI03001", "阅毕时uuid不可为空！"));
            return outInfo;
        }
        Map queryParame = new HashMap();
        queryParame.put("uuid", uuid);
        queryParame.put("isRead", AiConstant.SEND_YES_READ);
        dao.update("XBAI0301.updateIsRead", queryParame);
        outInfo.setStatus(EiConstant.STATUS_SUCCESS);
        outInfo.setMsg("阅毕成功！");
        return outInfo;
    }

}
