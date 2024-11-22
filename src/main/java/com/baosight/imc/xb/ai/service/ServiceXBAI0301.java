package com.baosight.imc.xb.ai.service;

import com.baosight.elim.common.utils.GlobalUtils;
import com.baosight.eplat.shareservice.service.EServiceManager;
import com.baosight.imc.common.utils.ImcGlobalUtils;
import com.baosight.imc.interfaces.vz.bm.domain.VZBM1300;
import com.baosight.imc.interfaces.vz.bm.service.ServiceVZBM1300;
import com.baosight.imc.xb.ai.common.constant.AiConstant;
import com.baosight.imc.xb.ai.common.constant.MsgCodeConstants;
import com.baosight.imc.xb.ai.common.utils.CheckUtils;
import com.baosight.imc.xb.ai.domain.XBAI03;
import com.baosight.imc.xb.ai.domain.XBAI0301;
import com.baosight.iplat4j.core.ProjectInfo;
import com.baosight.iplat4j.core.data.id.UUIDHexIdGenerator;
import com.baosight.iplat4j.core.ei.EiConstant;
import com.baosight.iplat4j.core.ei.EiInfo;
import com.baosight.iplat4j.core.ei.json.Json2EiInfo2;
import com.baosight.iplat4j.core.ioc.spring.PlatApplicationContext;
import com.baosight.iplat4j.core.service.impl.ServiceEPBase;
import com.baosight.iplat4j.core.service.soa.XLocalManager;
import com.baosight.iplat4j.core.util.DateUtils;
import com.baosight.iplat4j.core.util.UserUtil;
import com.baosight.iplat4j.core.web.threadlocal.UserSession;
import com.google.common.collect.Lists;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.util.*;

public class ServiceXBAI0301 extends ServiceEPBase {
    public List<Map<String, String>> codes;

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ServiceXBAI0301.class);

    @Override
    public EiInfo initLoad(EiInfo inInfo) {
        EiInfo eiInfo = super.initLoad(inInfo, new XBAI0301());
        return eiInfo;
    }

    @Override
    public EiInfo query(EiInfo inInfo) {
        EiInfo outInfo = new EiInfo();
        inInfo.getBlock(EiConstant.queryBlock).setCell(0, "delFlag", 0);
        outInfo = super.query(inInfo, "XBAI0301.query", new XBAI0301());
        return outInfo;
    }

    @Override
    public EiInfo delete(EiInfo inInfo) {
        return super.update(inInfo, "XBAI0301.delete");
    }

    @Override
    public EiInfo update(EiInfo inInfo) {
        return super.update(inInfo, "XBAI03.update");
    }

    @Override
    public EiInfo insert(EiInfo inInfo) {
        return super.insert(inInfo, "XBAI03.insert");
    }

    private String errorString(String code, String msg) {
        VZBM1300 queryMsg = new VZBM1300();
        queryMsg.setErrorNum(code);
        queryMsg.setFormEname("XBAI03");
        GlobalUtils.setCreatorProerty(queryMsg);
        return ServiceVZBM1300.getMessageTextByErrorNumAndRecord(new String[]{msg}, queryMsg);
    }

}
