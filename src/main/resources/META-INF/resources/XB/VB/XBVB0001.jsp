<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="EF" tagdir="/WEB-INF/tags/EF" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<EF:EFPage>
    <EF:EFRegion id="inqu2" title="查询条件">
        <div class="row">
            <EF:EFInput ename="inqu2_status-0-windowId" cname="ID" type="hidden"/>
            <EF:EFInput ename="inqu2_status-0-segNo" cname="业务单元代码" placeholder="模糊条件"/>
            <EF:EFInput ename="inqu2_status-0-segName" cname="业务单元简称" placeholder="模糊条件"/>
            <EF:EFInput ename="inqu2_status-0-segFullName" cname="业务单元全称" placeholder="模糊条件"/>
            <EF:EFInput ename="inqu2_status-0-isTrue" cname="是否查TVZBM81表标记"  type="hidden"/>
        </div>
    </EF:EFRegion>
    <EF:EFRegion id="result2" title="查询结果">
        <EF:EFGrid blockId="result2" checkMode="single, cell" isFloat="true" serviceName="XBVB0001" queryMethod="querySegNoOrSegFullName"
                   autoBind="false" autoDraw="no">
            <EF:EFColumn ename="segNo" cname="系统账套" readonly="true" align="center"/>
            <EF:EFColumn ename="segName" cname="业务单元简称" readonly="true" align="center"/>
            <EF:EFColumn ename="segFullName" cname="业务单元全称" readonly="true" align="center"/>
        </EF:EFGrid>
    </EF:EFRegion>
</EF:EFPage>