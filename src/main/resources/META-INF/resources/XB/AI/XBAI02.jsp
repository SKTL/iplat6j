<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="EF" tagdir="/WEB-INF/tags/EF" %>
<%@ page import="com.baosight.iplat4j.core.ei.EiInfo" %>
<%@ page import="com.baosight.iplat4j.core.resource.I18nMessages" %>
<%--会话管理--%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<EF:EFPage prefix="imc">
    <EF:EFRegion id="inqu" title="查询条件">
        <EF:EFSelect ename="inqu_status-0-intention" cname="业务场景" colWidth="3">
            <EF:EFOption label="全部" value=""/>
            <EF:EFOption label="情报检索" value="1"/>
            <EF:EFOption label="报告撰写" value="2"/>
            <EF:EFOption label="产品报价" value="3"/>
            <EF:EFOption label="功能推荐" value="4"/>
            <EF:EFOption label="数据查询" value="5"/>
            <EF:EFOption label="创建订货卡" value="6"/>
        </EF:EFSelect>
        <EF:EFInput ename="inqu_status-0-sessionTitle" cname="会话主题" colWidth="3" width="75"/>
        <EF:EFDatePicker ename="inqu_status-0-startTime" cname="开始时间" role="datetime"
                         format="yyyyMMddHHmmss" colWidth="3">
        </EF:EFDatePicker>
        <EF:EFDatePicker ename="inqu_status-0-endTime" cname="结束时间" role="datetime"
                         format="yyyyMMddHHmmss" colWidth="3">
        </EF:EFDatePicker>
        <EF:EFInput ename="inqu_status-0-user" cname="发起人" colWidth="3" width="75"/>
    </EF:EFRegion>
    <EF:EFRegion id="result" title="记录集">
        <input type="hidden" id="editRow"/>
        <EF:EFGrid blockId="result" isFloat="true" autoDraw="no" toolbarConfig="true" needAuth="true">
            <EF:EFColumn ename="uuid" cname="" hidden="true"/>
            <EF:EFColumn ename="sessionId" cname="" hidden="true"/>
            <EF:EFComboColumn ename="intention" cname="业务场景" enable="false" align="center"
                              columnTemplate="#=textField#" autoWidth="true"
                              itemTemplate="#=textField#" width="20">
                <EF:EFOption label="情报检索" value="1"/>
                <EF:EFOption label="报告撰写" value="2"/>
                <EF:EFOption label="产品报价" value="3"/>
                <EF:EFOption label="功能推荐" value="4"/>
                <EF:EFOption label="数据查询" value="5"/>
                <EF:EFOption label="创建订货卡" value="6"/>
            </EF:EFComboColumn>
            <EF:EFColumn ename="sessionTitle" enable="false" readonly="true" cname="会话主题" width="70"/>
            <EF:EFColumn ename="userId" enable="false" readonly="true" cname="发起人" align="center" width="20"/>
            <EF:EFColumn ename="recCreateTime" enable="false" readonly="true" width="20" cname="创建时间"
                         editType="datetime"
                         displayType="datetime" parseFormats="['yyyyMMddHHmmss']" dateFormat="yyyyMMddHHmmss"
                         align="center"/>
        </EF:EFGrid>
    </EF:EFRegion>
    <EF:EFRegion id="resultB" title="反馈明细">
        <div style="position: relative">
                <%--查询条件--%>
            <EF:EFInput ename="inqub_status-0-sessionId" cname="" type="hidden" colWidth="3"/>
            <EF:EFGrid blockId="resultB" isFloat="true" autoDraw="no" toolbarConfig="true" needAuth="true"
                       serviceName="XBAI02" queryMethod="queryInfo" autoBind="false">
                <EF:EFColumn ename="uuid" cname="" hidden="true"/>
                <EF:EFComboColumn ename="role" cname="用户角色" enable="false" align="center"
                                  columnTemplate="#=valueField#-#=textField#" autoWidth="true"
                                  itemTemplate="#=valueField#-#=textField#" width="100">
                    <EF:EFOption label="用户" value="1"/>
                    <EF:EFOption label="智能员工助理" value="2"/>
                </EF:EFComboColumn>
                <EF:EFColumn ename="content" enable="false" readonly="true" cname="问答内容" width="300"/>
                <EF:EFComboColumn ename="evaluate" cname="反馈类型" enable="false" align="center"
                                  columnTemplate="#=valueField#-#=textField#" autoWidth="true"
                                  itemTemplate="#=valueField#-#=textField#" width="70">
                    <EF:EFOption label="无" value="0"/>
                    <EF:EFOption label="好评" value="1"/>
                    <EF:EFOption label="差评" value="2"/>
                </EF:EFComboColumn>
                <EF:EFColumn ename="evaluateContent" cname="" hidden="true"/>
                <EF:EFMultiSelectColumn ename="evaluateId" cname="反馈内容" columnTemplate="#=valueField#-#=textField#" width="200" enable="false" readonly="true">
                    <EF:EFOption label="回答准确且专业" value="1001"/>
                    <EF:EFOption label="回答清晰易于理解" value="1002"/>
                    <EF:EFOption label="响应速度快" value="1003"/>
                    <EF:EFOption label="生成内容不准确" value="2001"/>
                    <EF:EFOption label="内容单调缺乏创新性" value="2002"/>
                    <EF:EFOption label="语义语法存在错误" value="2003"/>
                    <EF:EFOption label="逻辑不通顺" value="2004"/>
                    <EF:EFOption label="涉及隐私和安全" value="2005"/>
                    <EF:EFOption label="内容生成慢" value="2006"/>
                    <EF:EFOption label="生成内容冗长/过于简洁" value="2007"/>
                </EF:EFMultiSelectColumn>
                <EF:EFColumn ename="evaluateContent" cname="" hidden="true"/>
                <EF:EFColumn ename="recCreator" enable="false" readonly="true" cname="创建人" align="center"
                             width="85"/>
                <EF:EFColumn ename="recCreatorName" enable="false" readonly="true" cname="创建人姓名" width="85"
                             align="center"/>
                <EF:EFColumn ename="recCreateTime" enable="false" readonly="true" width="130" cname="创建时间"
                             editType="datetime"
                             displayType="datetime" parseFormats="['yyyyMMddHHmmss']" dateFormat="yyyyMMddHHmmss"
                             align="center"/>
                <EF:EFColumn ename="recRevisor" enable="false" cname="修改人" align="center" width="85"/>
                <EF:EFColumn ename="recRevisorName" enable="false" readonly="true" width="85" cname="修改人姓名"
                             align="center"/>
                <EF:EFColumn ename="recReviseTime" enable="false" readonly="true" width="130" cname="修改时间"
                             editType="datetime" displayType="datetime"
                             parseFormats="['yyyyMMddHHmmss']" dateFormat="yyyyMMddHHmmss" align="center"/>
            </EF:EFGrid>
        </div>
    </EF:EFRegion>
</EF:EFPage>
