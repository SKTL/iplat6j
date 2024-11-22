package com.baosight.imc.xb.vb.util;

import com.baosight.imc.interfaces.vz.bm.domain.VZBM1300;
import com.baosight.imc.interfaces.vz.bm.service.ServiceVZBM1300;
import com.baosight.iplat4j.core.log.Logger;
import com.baosight.iplat4j.core.log.LoggerFactory;
import com.baosight.iplat4j.core.service.soa.XLocalManager;
import com.baosight.iplat4j.core.util.DateUtil;
import com.baosight.iplat4j.core.util.StringUtils;
import com.baosight.iplat4j.core.web.threadlocal.UserSession;

/*
 * 工程项目:IMC.
 * 开发公司:Baosight Software LTD.co Copyright (c) 2022. 
 * 类的简介:用于获取IMC统一报错信息
 * 类的描述:
 * 开发日期:2022/4/19 
 * @author LiWei
 */
public class ErrorMsgUtils {
    private static final Logger logger = LoggerFactory.getLogger(XLocalManager.class);

    /**
     * @param:[param] 
     * @return: java.lang.String
     * @Service:
     * @Description: param[0] 异常编号  param[1] 画面编号 param[2] 按钮编号 param[3] 微服务编号
     */
    public static String getMassage(Object [] input,String [] param){

        String messageText = "";

        if(param.length <= 0){
            logger.info("传入参数为空！");
            return "";
        }
        if (StringUtils.isNotEmpty(param[0])){
            try {
                VZBM1300 vzbm1300 = new VZBM1300();
                //异常编号
                vzbm1300.setErrorNum(param[0]);
                //画面编号
                vzbm1300.setFormEname(param[1]);
                //按钮编号
                vzbm1300.setButtonEname(param[2]);
                //微服务编号
                vzbm1300.setServiceEname(param[3]);
                vzbm1300.setAppCode("imc-se");
                //如果UserSession信息为空，获取参数赋值
                if (!org.springframework.util.StringUtils.hasText(UserSession.getLoginName())
                        || !org.springframework.util.StringUtils.hasText(UserSession.getLoginCName()) ){
                    vzbm1300.setRecCreator(param[4]);
                    vzbm1300.setRecCreatorName(param[5]);
                }else {
                    vzbm1300.setRecCreator(UserSession.getLoginName());
                    vzbm1300.setRecCreatorName(UserSession.getLoginCName());
                }
                vzbm1300.setRecCreateTime(DateUtil.curDateTimeStr14());
                logger.info("传入参数："+ vzbm1300);
                messageText = ServiceVZBM1300.getMessageTextByErrorNumAndRecord(input,vzbm1300);
                logger.info("传出参数："+ messageText);
            }catch (Exception e){
                logger.info("调用获取统一报错信息接口异常！");
            }
        }else{
            logger.info("传入参数'异常编号'为空！");
        }

        return messageText;
    }

    public static String getSolution(String erroeNum){
        String solutionByErrorNum = "";

        if(erroeNum.isEmpty()){
            logger.info("传入参数'异常编号'为空！");
            return "";
        }
            try {
                 solutionByErrorNum = ServiceVZBM1300.getSolutionByErrorNum(erroeNum);
                logger.info("传出参数："+ solutionByErrorNum);
            }catch (Exception e){
                logger.info("调用获取统一报错信息接口异常！");
            }
        return solutionByErrorNum;
    }
}
