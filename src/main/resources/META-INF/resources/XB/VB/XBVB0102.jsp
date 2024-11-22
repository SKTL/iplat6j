<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="EF" tagdir="/WEB-INF/tags/EF" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<EF:EFPage prefix="imc">
    <EF:EFRegion id="inqu" title="查询条件">
        <div class="row">

            <EF:EFInput ename="inqu_status-0-unitCode" cname="业务单元代码" colWidth="3"/>
            <EF:EFInput ename="inqu_status-0-unitName" cname="业务单元简称" colWidth="3"/>
            <EF:EFInput ename="inqu_status-0-segNo" cname="系统账套" type="hidden"/>
            <EF:EFSelect ename="inqu_status-0-modelAreaName" cname="模型作业区名称" ratio="4:8" colWidth="3">
                <EF:EFOption label="全部" value=""/>
                <EF:EFCodeOption codeName="imc.vb.modelAreaCode"/>
            </EF:EFSelect>
            <EF:EFSelect ename="inqu_status-0-modelName" cname="模型名称" defaultValue=" " ratio="4:8" colWidth="3">
                <EF:EFOption label="全部" value=""/>
                <EF:EFCodeOption codeName="imc.vb.modelCode"/>
            </EF:EFSelect>
            <EF:EFSelect ename="inqu_status-0-violationType" cname="违规类型">
                <EF:EFOption label="请选择" value="" />
                <EF:EFCodeOption codeName="imc.vb.violationType" />
            </EF:EFSelect>
            <EF:EFDateSpan startName="inqu_status-0-violationstartTime" endName="inqu_status-0-violationEndTime"
                           role="date" required="false" bindWidth="3"
                           bindName="违规时间" extChar="-" format="yyyyMMdd"/>
        </div>
    </EF:EFRegion>
    <EF:EFRegion id="result" title="记录集">
        <EF:EFGrid blockId="result" checkMode="multiple, cell" serviceName="XBVB0102" queryMethod="query"
                   autoBind="false" autoDraw="no" readonly="true">
            <EF:EFColumn ename="unitCode" cname="业务单元代码" primaryKey="true" ></EF:EFColumn>
            <EF:EFColumn ename="uuid" cname="uuid"  hidden="true"></EF:EFColumn>
            <EF:EFColumn ename="unitName" cname="业务单元简称" primaryKey="true" ></EF:EFColumn>
            <EF:EFColumn ename="modelAreaNme" cname="模型作业区" readonly="true"></EF:EFColumn>
            <EF:EFColumn ename="modelCode" cname="模型代码" readonly="true"></EF:EFColumn>
            <EF:EFColumn ename="modelName" cname="模型名称" readonly="true"></EF:EFColumn>
            <EF:EFColumn ename="pictureCode" cname="图片代码" readonly="true"></EF:EFColumn>
            <EF:EFColumn ename="violationImgUrl" cname="图片地址" readonly="true" hidden="true" ></EF:EFColumn>
            <EF:EFColumn ename="violationType" cname="违规类型代码" readonly="true"></EF:EFColumn>
            <EF:EFColumn ename="violationName" cname="违规类型名称" readonly="true"></EF:EFColumn>
            <EF:EFColumn ename="violationTime" cname="发生时间" readonly="true"></EF:EFColumn>
            <EF:EFColumn ename="opinion" cname="现场确认意见"></EF:EFColumn>
            <EF:EFColumn ename="suggestion" cname="建议整改举措"></EF:EFColumn>
            <EF:EFColumn ename="operation" cname="操作" readonly="true"/>
        </EF:EFGrid>
    </EF:EFRegion>
    <%--    业务单元代码弹框--%>
    <EF:EFWindow url="${ctx}/web/XBVB0001" id="unitInfo" height="80%" width="80%"/>

    <%--<EF:EFWindow id="imgDialog" height="80%" width="90%" top="80px" left="100px" title="查看图片">
        <EF:EFRegion id="auth_part" title="零件信息" heigth="95%">
            <div class="row">
                <EF:EFInput ename="operationEdit_status-0-usedQuant" cname="零件日均消耗量(吨)" valueType="N" align="right"
                            colWidth="3"/>

            </div>
        </EF:EFRegion>
        <div class="row" id="auth_but" style="text-align:center;">
            <EF:EFButton ename="operation_cancel" cname="取消" style="width:60px;height:30px"></EF:EFButton>
            <EF:EFButton ename="operation_save" cname="保存" style="width:60px;height:30px"></EF:EFButton>
        </div>
    </EF:EFWindow>--%>
</EF:EFPage>


