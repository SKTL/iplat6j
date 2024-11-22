package com.baosight.imc.xb.ai.domain;

import com.baosight.iplat4j.core.data.DaoEPBase;
import com.baosight.iplat4j.core.ei.EiColumn;
import com.baosight.iplat4j.core.util.NumberUtils;
import com.baosight.iplat4j.core.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: <br>
 * Title:XBAI03.java <br>
 * Description: 测试<br>
 * <p>
 * Copyrigth:Baosight Software LTD.co Copyright (c) 2019. <br>
 *
 * @version 1.0
 * @history 2024-10-23 14:05:24 create
 */
public class XBAI0301 extends DaoEPBase {

    private static final long serialVersionUID = 1L;

    public static final String FIELD_UUID = "uuid";            /* ID*/
    public static final String FIELD_REC_CREATE_TIME = "recCreateTime";            /* 记录创建时间*/
    public static final String FIELD_DEL_FLAG = "delFlag";            /* 记录删除标记*/
    public static final String FIELD_ARCHIVE_FLAG = "archiveFlag";            /* 归档标记*/
    public static final String FIELD_SEG_NO = "segNo";            /* 系统账套*/
    public static final String FIELD_SUBSCRIBE_NAME = "subscribeName";            /* 订阅人姓名*/
    public static final String FIELD_SUBSCRIBE_REVISOR = "subscribeRevisor";            /* 订阅人*/
    public static final String FIELD_SUBSCRIBE_REVISOR_TEMP = "subscribeRevisorTemp";            /* 订阅人*/
    public static final String FIELD_SUBSCRIBE_FREQ = "subscribeFreq";            /* 订阅频率*/
    public static final String FIELD_SUBSCRIBE_EMAIL = "subscribeEmail";            /* 订阅邮箱地址*/
    public static final String FIELD_SUBSCRIBE_MOBILE = "subscribeMobile";            /* 订阅手机号*/
    public static final String FIELD_PUSH_DATE = "pushDate";            /* 推送日期*/
    public static final String FIELD_SUBSCRIBE_SEG_NO = "subscribeSegNo";            /* 订阅人业务单元*/
    public static final String FIELD_SUBSCRIBE_SEG_NO_TEMP = "subscribeSegNoTemp";            /* 订阅人业务单元*/
    public static final String FIELD_SUBSCRIBE_INFO = "subscribeInfo";            /* 订阅信息*/
    public static final String FIELD_PUSH_DAY = "pushDay";            /* 推送日*/
    public static final String FIELD_PUSH_HOUR = "pushHour";            /* 推送时*/
    public static final String FIELD_PUSH_MONTH = "pushMonth";            /* 推送月*/
    public static final String FIELD_MSG_TYPE = "msgType";            /* 发送类别*/
    public static final String FIELD_STATUS = "status";            /* 状态*/
    public static final String FIELD_PARENT_UUID = "parentUuid";            /* 状态*/
    public static final String FIELD_SEND_TIME = "sendTime";            /* 状态*/
    public static final String FIELD_SEND_TYPE = "sendType";            /* 发送类别*/
    public static final String FIELD_IS_READ = "isRead";            /* 发送类别*/

    public static final String COL_UUID = "UUID";            /* ID*/
    public static final String COL_REC_CREATE_TIME = "REC_CREATE_TIME";            /* 记录创建时间*/
    public static final String COL_DEL_FLAG = "DEL_FLAG";            /* 记录删除标记*/
    public static final String COL_ARCHIVE_FLAG = "ARCHIVE_FLAG";            /* 归档标记*/
    public static final String COL_SEG_NO = "SEG_NO";            /* 系统账套*/
    public static final String COL_SUBSCRIBE_NAME = "SUBSCRIBE_NAME";            /* 订阅人姓名*/
    public static final String COL_SUBSCRIBE_REVISOR = "SUBSCRIBE_REVISOR";            /* 订阅人*/
    public static final String COL_SUBSCRIBE_REVISOR_TEMP = "SUBSCRIBE_REVISOR_TEMP";            /* 订阅人*/
    public static final String COL_SUBSCRIBE_FREQ = "SUBSCRIBE_FREQ";            /* 订阅频率*/
    public static final String COL_SUBSCRIBE_EMAIL = "SUBSCRIBE_EMAIL";            /* 订阅邮箱地址*/
    public static final String COL_SUBSCRIBE_MOBILE = "SUBSCRIBE_MOBILE";            /* 订阅手机号*/
    public static final String COL_PUSH_DATE = "PUSH_DATE";            /* 推送日期*/
    public static final String COL_SUBSCRIBE_SEG_NO = "SUBSCRIBE_SEG_NO";            /* 订阅人业务单元*/
    public static final String COL_SUBSCRIBE_SEG_NO_TEMP = "SUBSCRIBE_SEG_NO_TEMP";            /* 订阅人业务单元*/
    public static final String COL_SUBSCRIBE_INFO = "SUBSCRIBE_INFO";            /* 订阅信息*/
    public static final String COL_PUSH_DAY = "PUSH_DAY";            /* 推送日*/
    public static final String COL_PUSH_HOUR = "PUSH_HOUR";            /* 推送时*/
    public static final String COL_PUSH_MONTH = "PUSH_MONTH";            /* 推送月*/
    public static final String COL_MSG_TYPE = "MSG_TYPE";            /* 推送月*/
    public static final String COL_STATUS = "STATUS";            /* 发送状态*/
    public static final String COL_PARENT_UUID = "PARENT_UUID";            /* 发送状态*/
    public static final String COL_SEND_TIME = "SEND_TIME";            /* 发送状态*/
    public static final String COL_SEND_TYPE = "SEND_TYPE";
    public static final String COL_IS_READ = "IS_READ";

    public static final String QUERY = "tvzbmi03.query";
    public static final String COUNT = "tvzbmi03.count";
    public static final String INSERT = "tvzbmi03.insert";
    public static final String UPDATE = "tvzbmi03.update";
    public static final String DELETE = "tvzbmi03.delete";

    private String uuid = "";        /* ID*/
    private String recCreateTime = "";        /* 记录创建时间*/
    private Integer delFlag = new Integer(0);        /* 记录删除标记*/
    private String archiveFlag = "";        /* 归档标记*/
    private String segNo = "";        /* 系统账套*/
    private String subscribeName = "";        /* 订阅人姓名*/
    private String subscribeRevisor = "";        /* 订阅人*/
    private String subscribeRevisorTemp = "";        /* 订阅人*/
    private String subscribeFreq = "";        /* 订阅频率*/
    private String subscribeEmail = "";        /* 订阅邮箱地址*/
    private String subscribeMobile = "";        /* 订阅手机号*/
    private String pushDate = "";        /* 推送日期*/
    private String subscribeSegNo = "";        /* 订阅人业务单元*/
    private String subscribeSegNoTemp = "";        /* 订阅人业务单元*/
    private String subscribeInfo = "";        /* 订阅信息*/
    private String pushDay = "";        /* 推送日*/
    private String pushHour = "";        /* 推送时*/
    private String pushMonth = "";        /* 推送月*/
    private String msgType = "";        /* 发送类别*/
    private String status = "";        /* 发送类别*/
    private String parentUuid = "";        /* 发送类别*/
    private String sendTime = "";        /* 发送类别*/
    private String sendType = "";        /* 发送类别*/
    private String isRead = "";        /* 发送类别*/

    /**
     * initialize the metadata.
     */
    public void initMetaData() {
        EiColumn eiColumn;

        eiColumn = new EiColumn(FIELD_UUID);
        eiColumn.setFieldLength(32);
        eiColumn.setDescName("ID");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_REC_CREATE_TIME);
        eiColumn.setFieldLength(17);
        eiColumn.setDescName("记录创建时间");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_DEL_FLAG);
        eiColumn.setDescName("记录删除标记");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_ARCHIVE_FLAG);
        eiColumn.setFieldLength(1);
        eiColumn.setDescName("归档标记");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SEG_NO);
        eiColumn.setFieldLength(10);
        eiColumn.setDescName("系统账套");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SUBSCRIBE_NAME);
        eiColumn.setFieldLength(100);
        eiColumn.setDescName("订阅人姓名");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SUBSCRIBE_REVISOR);
        eiColumn.setFieldLength(32);
        eiColumn.setDescName("订阅人");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SUBSCRIBE_REVISOR_TEMP);
        eiColumn.setFieldLength(32);
        eiColumn.setDescName("订阅人");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SUBSCRIBE_FREQ);
        eiColumn.setFieldLength(20);
        eiColumn.setDescName("订阅频率");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SUBSCRIBE_EMAIL);
        eiColumn.setFieldLength(64);
        eiColumn.setDescName("订阅邮箱地址");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SUBSCRIBE_MOBILE);
        eiColumn.setFieldLength(11);
        eiColumn.setDescName("订阅手机号");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_PUSH_DATE);
        eiColumn.setFieldLength(14);
        eiColumn.setDescName("推送日期");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SUBSCRIBE_SEG_NO);
        eiColumn.setFieldLength(100);
        eiColumn.setDescName("订阅人业务单元");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SUBSCRIBE_SEG_NO_TEMP);
        eiColumn.setFieldLength(100);
        eiColumn.setDescName("订阅人业务单元");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SUBSCRIBE_INFO);
        eiColumn.setFieldLength(200);
        eiColumn.setDescName("订阅信息");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_PUSH_DAY);
        eiColumn.setFieldLength(14);
        eiColumn.setDescName("推送日");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_PUSH_HOUR);
        eiColumn.setFieldLength(14);
        eiColumn.setDescName("推送时");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_PUSH_MONTH);
        eiColumn.setFieldLength(14);
        eiColumn.setDescName("推送月");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_MSG_TYPE);
        eiColumn.setFieldLength(5);
        eiColumn.setDescName("发送类别");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_STATUS);
        eiColumn.setFieldLength(2);
        eiColumn.setDescName("状态");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_PARENT_UUID);
        eiColumn.setFieldLength(50);
        eiColumn.setDescName("父ID");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SEND_TIME);
        eiColumn.setFieldLength(50);
        eiColumn.setDescName("发送时间");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_SEND_TYPE);
        eiColumn.setFieldLength(30);
        eiColumn.setDescName("推送类别");
        eiMetadata.addMeta(eiColumn);

        eiColumn = new EiColumn(FIELD_IS_READ);
        eiColumn.setFieldLength(2);
        eiColumn.setDescName("已读标记");
        eiMetadata.addMeta(eiColumn);
    }

    /**
     * the constructor.
     */
    public XBAI0301() {
        initMetaData();
    }

    /**
     * get the uuid - ID.
     *
     * @return the uuid
     */
    public String getUuid() {
        return this.uuid;
    }

    public String getSubscribeSegNoTemp() {
        return subscribeSegNoTemp;
    }

    public String getSendType() {
        return sendType;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public void setSubscribeSegNoTemp(String subscribeSegNoTemp) {
        this.subscribeSegNoTemp = subscribeSegNoTemp;
    }

    public String getSubscribeRevisorTemp() {
        return subscribeRevisorTemp;
    }

    public void setSubscribeRevisorTemp(String subscribeRevisorTemp) {
        this.subscribeRevisorTemp = subscribeRevisorTemp;
    }

    /**
     * set the uuid - ID.
     *
     * @param uuid - ID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * get the recCreateTime - 记录创建时间.
     *
     * @return the recCreateTime
     */
    public String getRecCreateTime() {
        return this.recCreateTime;
    }

    /**
     * set the recCreateTime - 记录创建时间.
     *
     * @param recCreateTime - 记录创建时间
     */
    @Override
    public void setRecCreateTime(String recCreateTime) {
        this.recCreateTime = recCreateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * get the delFlag - 记录删除标记.
     *
     * @return the delFlag
     */
    public Integer getDelFlag() {
        return this.delFlag;
    }

    /**
     * set the delFlag - 记录删除标记.
     *
     * @param delFlag - 记录删除标记
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * get the archiveFlag - 归档标记.
     *
     * @return the archiveFlag
     */
    public String getArchiveFlag() {
        return this.archiveFlag;
    }

    /**
     * set the archiveFlag - 归档标记.
     *
     * @param archiveFlag - 归档标记
     */
    @Override
    public void setArchiveFlag(String archiveFlag) {
        this.archiveFlag = archiveFlag;
    }

    /**
     * get the segNo - 系统账套.
     *
     * @return the segNo
     */
    public String getSegNo() {
        return this.segNo;
    }

    /**
     * set the segNo - 系统账套.
     *
     * @param segNo - 系统账套
     */
    public void setSegNo(String segNo) {
        this.segNo = segNo;
    }

    /**
     * get the subscribeName - 订阅人姓名.
     *
     * @return the subscribeName
     */
    public String getSubscribeName() {
        return this.subscribeName;
    }

    /**
     * set the subscribeName - 订阅人姓名.
     *
     * @param subscribeName - 订阅人姓名
     */
    public void setSubscribeName(String subscribeName) {
        this.subscribeName = subscribeName;
    }

    /**
     * get the subscribeRevisor - 订阅人.
     *
     * @return the subscribeRevisor
     */
    public String getSubscribeRevisor() {
        return this.subscribeRevisor;
    }

    /**
     * set the subscribeRevisor - 订阅人.
     *
     * @param subscribeRevisor - 订阅人
     */
    public void setSubscribeRevisor(String subscribeRevisor) {
        this.subscribeRevisor = subscribeRevisor;
    }

    /**
     * get the subscribeFreq - 订阅频率.
     *
     * @return the subscribeFreq
     */
    public String getSubscribeFreq() {
        return this.subscribeFreq;
    }

    /**
     * set the subscribeFreq - 订阅频率.
     *
     * @param subscribeFreq - 订阅频率
     */
    public void setSubscribeFreq(String subscribeFreq) {
        this.subscribeFreq = subscribeFreq;
    }

    /**
     * get the subscribeEmail - 订阅邮箱地址.
     *
     * @return the subscribeEmail
     */
    public String getSubscribeEmail() {
        return this.subscribeEmail;
    }

    /**
     * set the subscribeEmail - 订阅邮箱地址.
     *
     * @param subscribeEmail - 订阅邮箱地址
     */
    public void setSubscribeEmail(String subscribeEmail) {
        this.subscribeEmail = subscribeEmail;
    }

    /**
     * get the subscribeMobile - 订阅手机号.
     *
     * @return the subscribeMobile
     */
    public String getSubscribeMobile() {
        return this.subscribeMobile;
    }

    /**
     * set the subscribeMobile - 订阅手机号.
     *
     * @param subscribeMobile - 订阅手机号
     */
    public void setSubscribeMobile(String subscribeMobile) {
        this.subscribeMobile = subscribeMobile;
    }

    /**
     * get the pushDate - 推送日期.
     *
     * @return the pushDate
     */
    public String getPushDate() {
        return this.pushDate;
    }

    /**
     * set the pushDate - 推送日期.
     *
     * @param pushDate - 推送日期
     */
    public void setPushDate(String pushDate) {
        this.pushDate = pushDate;
    }

    /**
     * get the subscribeSegNo - 订阅人业务单元.
     *
     * @return the subscribeSegNo
     */
    public String getSubscribeSegNo() {
        return this.subscribeSegNo;
    }

    /**
     * set the subscribeSegNo - 订阅人业务单元.
     *
     * @param subscribeSegNo - 订阅人业务单元
     */
    public void setSubscribeSegNo(String subscribeSegNo) {
        this.subscribeSegNo = subscribeSegNo;
    }

    /**
     * get the subscribeInfo - 订阅信息.
     *
     * @return the subscribeInfo
     */
    public String getSubscribeInfo() {
        return this.subscribeInfo;
    }

    /**
     * set the subscribeInfo - 订阅信息.
     *
     * @param subscribeInfo - 订阅信息
     */
    public void setSubscribeInfo(String subscribeInfo) {
        this.subscribeInfo = subscribeInfo;
    }

    /**
     * get the pushDay - 推送日.
     *
     * @return the pushDay
     */
    public String getPushDay() {
        return this.pushDay;
    }

    /**
     * set the pushDay - 推送日.
     *
     * @param pushDay - 推送日
     */
    public void setPushDay(String pushDay) {
        this.pushDay = pushDay;
    }

    /**
     * get the pushHour - 推送时.
     *
     * @return the pushHour
     */
    public String getPushHour() {
        return this.pushHour;
    }

    /**
     * set the pushHour - 推送时.
     *
     * @param pushHour - 推送时
     */
    public void setPushHour(String pushHour) {
        this.pushHour = pushHour;
    }

    /**
     * get the pushMonth - 推送月.
     *
     * @return the pushMonth
     */
    public String getPushMonth() {
        return this.pushMonth;
    }

    /**
     * set the pushMonth - 推送月.
     *
     * @param pushMonth - 推送月
     */
    public void setPushMonth(String pushMonth) {
        this.pushMonth = pushMonth;
    }

    /**
     * get the value from Map.
     *
     * @param map - source data map
     */
    @Override
    public void fromMap(Map map) {

        setUuid(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_UUID)), uuid));
        setRecCreateTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_REC_CREATE_TIME)), recCreateTime));
        setDelFlag(NumberUtils.toInteger(StringUtils.toString(map.get(FIELD_DEL_FLAG)), delFlag));
        setArchiveFlag(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_ARCHIVE_FLAG)), archiveFlag));
        setSegNo(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SEG_NO)), segNo));
        setSubscribeName(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SUBSCRIBE_NAME)), subscribeName));
        setSubscribeRevisor(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SUBSCRIBE_REVISOR)), subscribeRevisor));
        setSubscribeRevisorTemp(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SUBSCRIBE_REVISOR_TEMP)), subscribeRevisorTemp));
        setSubscribeFreq(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SUBSCRIBE_FREQ)), subscribeFreq));
        setSubscribeEmail(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SUBSCRIBE_EMAIL)), subscribeEmail));
        setSubscribeMobile(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SUBSCRIBE_MOBILE)), subscribeMobile));
        setPushDate(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_PUSH_DATE)), pushDate));
        setSubscribeSegNo(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SUBSCRIBE_SEG_NO)), subscribeSegNo));
        setSubscribeSegNoTemp(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SUBSCRIBE_SEG_NO_TEMP)), subscribeSegNoTemp));
        setSubscribeInfo(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SUBSCRIBE_INFO)), subscribeInfo));
        setPushDay(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_PUSH_DAY)), pushDay));
        setPushHour(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_PUSH_HOUR)), pushHour));
        setPushMonth(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_PUSH_MONTH)), pushMonth));
        setMsgType(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_MSG_TYPE)), msgType));
        setStatus(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_STATUS)), status));
        setParentUuid(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_PARENT_UUID)), parentUuid));
        setSendTime(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SEND_TIME)), sendTime));
        setSendType(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_SEND_TYPE)), sendType));
        setIsRead(StringUtils.defaultIfEmpty(StringUtils.toString(map.get(FIELD_IS_READ)), isRead));
    }

    /**
     * set the value to Map.
     */
    @Override
    public Map toMap() {

        Map map = new HashMap();
        map.put(FIELD_UUID, StringUtils.toString(uuid, eiMetadata.getMeta(FIELD_UUID)));
        map.put(FIELD_REC_CREATE_TIME, StringUtils.toString(recCreateTime, eiMetadata.getMeta(FIELD_REC_CREATE_TIME)));
        map.put(FIELD_DEL_FLAG, StringUtils.toString(delFlag, eiMetadata.getMeta(FIELD_DEL_FLAG)));
        map.put(FIELD_ARCHIVE_FLAG, StringUtils.toString(archiveFlag, eiMetadata.getMeta(FIELD_ARCHIVE_FLAG)));
        map.put(FIELD_SEG_NO, StringUtils.toString(segNo, eiMetadata.getMeta(FIELD_SEG_NO)));
        map.put(FIELD_SUBSCRIBE_NAME, StringUtils.toString(subscribeName, eiMetadata.getMeta(FIELD_SUBSCRIBE_NAME)));
        map.put(FIELD_SUBSCRIBE_REVISOR, StringUtils.toString(subscribeRevisor, eiMetadata.getMeta(FIELD_SUBSCRIBE_REVISOR)));
        map.put(FIELD_SUBSCRIBE_REVISOR_TEMP, StringUtils.toString(subscribeRevisorTemp, eiMetadata.getMeta(FIELD_SUBSCRIBE_REVISOR_TEMP)));
        map.put(FIELD_SUBSCRIBE_FREQ, StringUtils.toString(subscribeFreq, eiMetadata.getMeta(FIELD_SUBSCRIBE_FREQ)));
        map.put(FIELD_SUBSCRIBE_EMAIL, StringUtils.toString(subscribeEmail, eiMetadata.getMeta(FIELD_SUBSCRIBE_EMAIL)));
        map.put(FIELD_SUBSCRIBE_MOBILE, StringUtils.toString(subscribeMobile, eiMetadata.getMeta(FIELD_SUBSCRIBE_MOBILE)));
        map.put(FIELD_PUSH_DATE, StringUtils.toString(pushDate, eiMetadata.getMeta(FIELD_PUSH_DATE)));
        map.put(FIELD_SUBSCRIBE_SEG_NO, StringUtils.toString(subscribeSegNo, eiMetadata.getMeta(FIELD_SUBSCRIBE_SEG_NO)));
        map.put(FIELD_SUBSCRIBE_SEG_NO_TEMP, StringUtils.toString(subscribeSegNoTemp, eiMetadata.getMeta(FIELD_SUBSCRIBE_SEG_NO_TEMP)));
        map.put(FIELD_SUBSCRIBE_INFO, StringUtils.toString(subscribeInfo, eiMetadata.getMeta(FIELD_SUBSCRIBE_INFO)));
        map.put(FIELD_PUSH_DAY, StringUtils.toString(pushDay, eiMetadata.getMeta(FIELD_PUSH_DAY)));
        map.put(FIELD_PUSH_HOUR, StringUtils.toString(pushHour, eiMetadata.getMeta(FIELD_PUSH_HOUR)));
        map.put(FIELD_PUSH_MONTH, StringUtils.toString(pushMonth, eiMetadata.getMeta(FIELD_PUSH_MONTH)));
        map.put(FIELD_MSG_TYPE, StringUtils.toString(msgType, eiMetadata.getMeta(FIELD_MSG_TYPE)));
        map.put(FIELD_STATUS, StringUtils.toString(status, eiMetadata.getMeta(FIELD_STATUS)));
        map.put(FIELD_PARENT_UUID, StringUtils.toString(parentUuid, eiMetadata.getMeta(FIELD_PARENT_UUID)));
        map.put(FIELD_SEND_TIME, StringUtils.toString(sendTime, eiMetadata.getMeta(FIELD_SEND_TIME)));
        map.put(FIELD_SEND_TYPE, StringUtils.toString(sendType, eiMetadata.getMeta(FIELD_SEND_TYPE)));
        map.put(FIELD_IS_READ, StringUtils.toString(isRead, eiMetadata.getMeta(FIELD_IS_READ)));
        return map;
    }
}
