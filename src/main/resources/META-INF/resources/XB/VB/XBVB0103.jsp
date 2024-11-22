<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="EF" tagdir="/WEB-INF/tags/EF" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<EF:EFPage prefix="imc">
    <EF:EFRegion id="inqu" title="查询条件">
        <div class="row">
                <%--<EF:EFPopupInput ename="inqu_status-0-unitCode" cname="业务单元代码" resizable="true" colWidth="3"
                                 ratio="4:8" required="true"
                                 readonly="true" backFillFieldIds="inqu_status-0-segNo,inqu_status-0-segName"
                                 containerId="unitInfo" popupWidth="600" pupupHeight="300" originalInput="true"
                                 center="true"
                                 popupTitle="业务套账查询">
                </EF:EFPopupInput>--%>
            <EF:EFInput ename="inqu_status-0-unitCode" cname="业务单元代码" colWidth="3"/>
            <EF:EFInput ename="inqu_status-0-unitName" cname="业务单元简称" colWidth="3"/>
            <EF:EFInput ename="inqu_status-0-segNo" cname="系统账套" type="hidden"/>
            <EF:EFSelect ename="inqu_status-0-modelAreaName" cname="模型作业区名称" ratio="4:8" colWidth="3">
                <EF:EFOption label="全部" value=""/>
                <EF:EFCodeOption codeName="imc.vb.modelAreaCode"/>
            </EF:EFSelect>
            <EF:EFSelect ename="inqu_status-0-modelName" cname="模型代码" defaultValue=" " ratio="4:8" colWidth="3">
                <EF:EFOption label="全部" value=""/>
                <EF:EFCodeOption codeName="imc.vb.modelCode"/>
            </EF:EFSelect>
            <%--<EF:EFInput ename="inqu_status-0-modelName" cname="模型名称" ratio="4:8" colWidth="3"/>--%>
            <EF:EFSelect ename="inqu_status-0-warnType" cname="预警类型" defaultValue=" " ratio="4:8" colWidth="3">
                <EF:EFOption label="全部" value=""/>
                <EF:EFOption label="现场声音预警" value="YJ01"/>
                <EF:EFOption label="短信预警" value="YJ02"/>
            </EF:EFSelect>
            <EF:EFDateSpan startName="inqu_status-0-warnStartTime" endName="inqu_status-0-warnEndTime"
                           role="date" required="false" bindWidth="3"
                           bindName="预警时间" extChar="-" format="yyyyMMdd"/>
        </div>
    </EF:EFRegion>
    <EF:EFRegion id="result" title="记录集">
        <EF:EFGrid blockId="result" checkMode="multiple, cell" serviceName="XBVB0103" queryMethod="query"
                   autoBind="false" autoDraw="no" readonly="true">
            <EF:EFColumn ename="uuid" cname="ID" hidden="true"/>
            <EF:EFColumn ename="segNo" cname="业务单元代码" align="center" width="110" enable="false"/>
            <EF:EFColumn ename="unitCode" cname="系统账套" width="100" enable="false" hidden="true"/>
            <EF:EFColumn ename="unitName" cname="业务单元简称" enable="false" width="150"/>
            <EF:EFColumn ename="modelAreaName" cname="模型作业区名称" enable="false" width="150"/>
            <EF:EFColumn ename="modelCode" cname="模型代码" align="center" enable="false" width="150"/>
            <EF:EFColumn ename="modelName" cname="模型名称" align="center" enable="false" width="150"/>
            <EF:EFColumn ename="warnType" cname="预警类型" enable="false" width="150"/>
            <EF:EFColumn ename="warnTime" cname="预警时间" enable="false" width="150"/>
            <EF:EFColumn ename="warnInfo" cname="预警类容" enable="false" width="150"/>
        </EF:EFGrid>
    </EF:EFRegion>
    <%--    业务单元代码弹框--%>
    <EF:EFWindow url="${ctx}/web/XBVB0001" id="unitInfo" height="80%" width="80%"/>
</EF:EFPage>


