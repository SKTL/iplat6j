package com.baosight.imc.xb.ai.common.constant;


/**
 * @description: 服务注册和电文号常量类
 * @author: lifeng
 * @createTime:2022-05-16 15:52
 */
public class MsgCodeConstants {

    /**
     * 平台推送短信服务
     */
    public static final String S_EPLAT_04 = "S_EPLAT_04";

    /**
     * 消息推送微服务
     */
    public static final String R_VZ_BM_80 = "R_VZ_BM_80";

    /**
     * MQ消息队列
     */
    public static final String R_VZ_BM_80_1 = "R_R_VZ_BM_80_1";

    /**
     * 平台获取节假日服务
     */
    public static final String S_BE_CM_102 = "S_BE_CM_102";

    /**
     * 平台获取秘钥参数
     */
    public static final String SHARED_OS_SECRET = "eplat.security.client.clientSecret";

    /**
     * 平台获取节假日url参数
     */
    public static final String SHARED_WEEKDAY_URL = "eplat.baocloud.url.adds";
    public static final String EPLAT_BAOCLOUD_URL = "eplat.baocloud.url";

    /**
     * 发送状态：
     * WAIT : 未发送(没在短信时间段内或短时间内无法发送短信)
     * SENDING : 发送中(通过异步推送无法同步获取推送结果)
     * SEND : 发送成功
     * EXCEPTION : 发送异常
     * PUSH_STATUS_SWITCH_WAIT : 短信总开关不允许发送
     */
    public static final String PUSH_STATUS_WAIT = "WAIT";
    public static final String PUSH_STATUS_SENDING = "SENDING";
    public static final String PUSH_STATUS_SEND = "SEND";
    public static final String PUSH_STATUS_EXCEPTION = "EXCEPTION";
    public static final String PUSH_STATUS_SWITCH_WAIT = "SWITCH_WAIT";

    /**
     * 方法名称
     */
    public static final String TYPE_INSERT = "INSERT";
    public static final String TYPE_UPDATE = "UPDATE";

    /**
     * 模板最多字数
     */
    public static final Integer MAX_CONTENT_LENGTH = 500;

    /**
     * 发送类型
     * A(工作日8:30-17:00),B(工作日8:00-20:00),C(8:00-20:00),D(实时)
     */
    public static final String SEND_STATUS_NORMAL = "A";
    public static final String SEND_STATUS_NORMAL_LARGE = "B";
    public static final String SEND_STATUS_HOLIDAY = "C";
    public static final String SEND_STATUS_ALL = "D";

    /**
     * 报错编码
     * VZBM0810005 查询
     * VZBM0810002 新增
     * VZBM0810006 修改
     * VZBM0810007 删除
     */
    public static final String ERROR_CODE_SEND = "VZBM0800001";
    public static final String ERROR_CODE_QUERY = "VZBM0800105";
    public static final String ERROR_CODE_ADD = "VZBM0800102";
    public static final String ERROR_CODE_UPDATE = "VZBM0800103";
    public static final String ERROR_CODE_DELETE = "VZBM0800104";
}
