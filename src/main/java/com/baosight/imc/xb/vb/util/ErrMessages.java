package com.baosight.imc.xb.vb.util;

/**
 * 工程项目:IMC.
 * 开发公司:Baosight Software LTD.co Copyright (c) 2022.
 * 类的简介:通用异常信息编号常量类-ErrMessages
 * 类的描述:封装通用异常信息编号常量类
 * 开发日期:2022/5/24
 *
 * @author MaRongYao
 * @version 1.0 （开发版本）
 * @since 1.8 （JDK版本号）
 */
public class ErrMessages {
    /*
     * 通用异常编号
     * */
    public static final String ERROR_QUERY = "S0000048";   /* 通用查询异常编号*/
    public static final String ERROR_INSERT = "S0000049";   /* 通用新增异常编号*/
    public static final String ERROR_UPDATE = "S0000050";   /* 通用修改异常编号*/
    public static final String ERROR_DELETE = "S0000051";   /* 通用删除异常编号*/
    public static final String ERROR_IMPORT = "S0000054";   /* 通用导入异常编号*/
    public static final String ERROR_UPSERT = "S0000146";   /* 通用保存异常编号*/

    public static final String ERROR_QUERY_IF_NULL = "S0000163";   /* 通用查询判空异常编号*/
    public static final String ERROR_INSERT_IF_NULL = "S0000063";   /* 通用新增判空异常编号*/
    public static final String ERROR_UPDATE_IF_NULL = "S0000064";   /* 通用修改判空异常编号*/
    public static final String ERROR_DELETE_IF_NULL = "S0000065";   /* 通用删除判空异常编号*/
    public static final String ERROR_UPSERT_IF_NULL = "S0000147";   /* 通用保存异常编号*/
    public static final String LOGIN_USER_IF_NULL = "S0000057";   /* 通用loginUser判空异常编号*/

    public static final String ERROR_INSERT_IF_ONLY = "S0000061";   /* 通用新增唯一约束异常编号*/
    public static final String ERROR_UPDATE_IF_ONLY = "S0000060";   /* 通用修改唯一约束异常编号*/
    public static final String ERROR_IMPORT_IF = "S0000055";   /* 通用导入判断异常编号*/

    //调用{0}服务失败！原因：{1}     请联系运维人员！
    // 参数{0}：中心名字+服务号  例如：库存中心，XXXXX
    // 参数{1}:其他中心返回的msg或者自己定义的msg
    public static final String ERROR_CALL = "S4100004";   /* 调用其他中心失败的异常编号*/

    /*
     * 个性化异常编号
     * 注释：提示描述+处置建议
     * */
    //操作失败！{0}必须为大于等于0的数字！      请检查输入项！
    public static final String ERROR_NUMBER = "S0000062";   /* 通用数字异常编号*/
    //操作失败！{0}必须为大于0的数字！        请检查输入项！
    public static final String ERROR_NUMBER_MUST = "S0000161";   /* 通用数字异常编号(必须为数字)*/
    //操作失败,原因:{0}长度不能超过2位       请检查行数据！
    public static final String ERROR_DAYS_IF_LENGTH = "S0000162";   /* 通用天数长度判断*/
    //计算失败，原因：{0}
    public static final String ERROR_CALCULATION = "S4100001";   /* 重新计算的异常编号*/
    //操作失败，原因：{0}
    public static final String ERROR_INSERTUPDATE = "S4100002";   /* 新增修改的异常编号*/
    //操作失败！{0}格式错误！     请核实后操作！
    public static final String ERROR_FORMAT = "S4100003";   /* 格式错误异常编号*/


}
