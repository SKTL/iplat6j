package com.baosight.imc.xb.vb.service;
import com.baosight.imc.common.utils.SegDataUtils;
import com.baosight.imc.xb.vb.util.ErrMessages;
import com.baosight.imc.xb.vb.util.ErrorMsgUtils;
import com.baosight.iplat4j.core.ei.EiConstant;
import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.log.Logger;
import com.baosight.iplat4j.core.log.LoggerFactory;
import com.baosight.iplat4j.core.service.impl.ServiceBase;
import com.baosight.iplat4j.core.util.StringUtils;
import org.apache.commons.collections.MapUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author huanghao
 * @project: IMC.SE
 * @introduce: 业务单元代码管理中台服务-ServiceUWCM0502
 * @desc: 主要数据表：TVZBM81表
 * @date: 2022/8/29
 */
public class ServiceXBVB0001 extends ServiceBase {
    private static final Logger logger = LoggerFactory.getLogger(ServiceXBVB0001.class);
    private String loginName = "";
    private String loginCName = "";
    private static final String LOGIN_NAME = "loginName";

    private static final String USER_NAME = "userName";
    private static final String LOGINUSER_BLOCK = "loginUser";

    public static final String QUERY = "querySegNoOrSegFullName";
    public static final String QUERY_BY_SEGNO = "queryBySegNo";

    /** 
    * @Description: TODO
    * @Param: 获取业务单元代码 名称
    * @Author: liuhang
    * @Date: 13:26 2024-11-14
    * @ServiceId: 
    */ 
    public EiInfo querySegNoOrSegFullName(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        outInfo.addBlock("result2");
        try {
            //判断loginUser信息是否为空
            /*if (loginUserIfNull(inInfo)) {
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
                outInfo.setMsg("");
                return outInfo;
            }*/
            //获取loginUser信息
            //List<Map<String, Object>> loginUser = inInfo.getBlock(Constant.Iplat.LOGINUSER_BLOCK).getRows();
            //loginName = (String) loginUser.get(0).get(Constant.Iplat.LOGIN_NAME);
            //loginCName = (String) loginUser.get(0).get(Constant.Iplat.USER_NAME);
            List<Map<String, Object>> params = inInfo.getBlock("inqu2_status").getRows();
            //系统账套
            String segNo = MapUtils.getString(params.get(0), "segNo", "");
            //业务单元简称
            String segName = MapUtils.getString(params.get(0), "segName", "");
            //业务单元全称
            String segFullName = MapUtils.getString(params.get(0), "segFullName", "");

            //分页参数
            Map<String, Object> attr = inInfo.getBlock("result2").getAttr();
            int offset = MapUtils.getInteger(attr, "offset", 0);
            int limit = MapUtils.getInteger(attr, "limit", 0);
            String showCount = MapUtils.getString(attr, "showCount", "true");
            Map identityById = new HashMap();
            if (StringUtils.isNotEmpty(MapUtils.getString(params.get(0), "isTrue", ""))
                     &&  "true".equals(MapUtils.getString(params.get(0), "isTrue", ""))){
                List query = dao.query("UWCM0502.querySegNoOrSegFullName", params.get(0));
                identityById.put("data",query);
                identityById.put("count",query.size());
                identityById.put("status",1);
                identityById.put("data",query);
            }else {
                identityById = SegDataUtils.getIdentityByIdManyLike(loginName, "0004-0001", segNo, segName, segFullName, offset, limit);
            }
            int status = MapUtils.getInteger(identityById, "status", -1);
            if (status != -1) {
                List<Map<String, Object>> result = (List<Map<String, Object>>) identityById.get("data");
                if (result != null){
                    outInfo.getBlock("result2").addRows(result);
                    Map count = new HashMap();
                    count.put("offset", offset);
                    count.put("limit", limit);
                    count.put("count", (Integer) identityById.get("count"));
                    count.put("showCount", showCount);
                    outInfo.getBlock("result2").setAttr(count);
                    outInfo.setMsg("查询成功，本次查询返回" + result.size() + "条记录！");
                    outInfo.setStatus(EiConstant.STATUS_DEFAULT);
                }else {
                    outInfo.setMsg("未查得记录！");
                    outInfo.setStatus(EiConstant.STATUS_DEFAULT);
                }
            } else {
                String identityByIdMsg = MapUtils.getString(identityById,"msg","查询失败，调用业务单元筛选方法失败！请联系运维人员！");
                outInfo.setMsg(identityByIdMsg);
                outInfo.setStatus(EiConstant.STATUS_FAILURE);
            }
            return outInfo;
        } catch (Exception e) {
            //记录异常
            logger.info(e.getMessage(), e);
            //统一异常处理
            inInfo.setStatus(EiConstant.STATUS_FAILURE);
            inInfo.setMsg(ErrorMsgUtils.getMassage(new Object[]{e.getMessage()}, new String[]{ErrMessages.ERROR_QUERY,
                    "UWCM0501", QUERY, "UWCM0501"}));
            return outInfo;
        }
    }


    public static Boolean loginUserIfNull(EiInfo inInfo) {
        try {
            //获取block块
            Map<String, Object> blocks = inInfo.getBlocks();
            for (String key : blocks.keySet()) {
                //判断block块loginUser是否为空
                if (key.equals(LOGINUSER_BLOCK)) {
                    List<Map<String, Object>> loginUser = inInfo.getBlock(LOGINUSER_BLOCK).getRows();
                    //去除数据左右空格
                    trimSpace(loginUser);
                    //判断loginUser内信息是否为空
                    if (loginUser != null && loginUser.size() > 0) {
                        for (Map map : loginUser) {
                            //获取loginName
                            String loginName = MapUtils.getString(map, LOGIN_NAME, "");
                            //获取userName
                            String userName = MapUtils.getString(map, USER_NAME, "");
                            //判断loginName和userName是否为空
                            if (!org.springframework.util.StringUtils.hasText(loginName)
                                    || !org.springframework.util.StringUtils.hasText(userName)) {
                                return true;
                            }
                        }
                        return false;
                    }
                    return true;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return true;
        }
    }


    public static void trimSpace(List<Map<String,Object>> rows){
        for (Map<String, Object> row : rows) {
            for (String key : row.keySet()) {
                if (row.get(key) != null) {
                    String value = row.get(key).toString().trim();
                    row.put(key, value);
                }
            }
        }
    }
}
