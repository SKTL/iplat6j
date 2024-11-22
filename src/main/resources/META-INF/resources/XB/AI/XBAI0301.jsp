<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="EF" tagdir="/WEB-INF/tags/EF" %>
<%@ page import="com.baosight.iplat4j.core.ei.EiInfo" %>
<%@ page import="com.baosight.iplat4j.core.resource.I18nMessages" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<EF:EFPage prefix="imc">
    <EF:EFRegion id="inqu" title="查询条件">
        <div class="row">
            <EF:EFInput ename="inqu_status-0-subscribeInfo" cname="订阅信息" colWidth="3"
                        width="75"/>
            <EF:EFInput ename="inqu_status-0-subscribeRevisor" cname="订阅人" colWidth="3"
                        width="75"/>
            <EF:EFInput ename="inqu_status-0-subscribeName" cname="订阅人姓名" colWidth="3"
                        width="75"/>
            <EF:EFInput ename="inqu_status-0-subscribeSegNo" cname="订阅人业务单元" colWidth="3"
                        width="75"/>
            <EF:EFInput ename="inqu_status-0-pushHour" cname="时" colWidth="3"
                        width="75"/>
            <EF:EFInput ename="inqu_status-0-parentUuid" cname="父ID" type="hidden" colWidth="3"
                        width="75"/>
            <EF:EFSelect ename="inqu_status-0-msgType" autoWidth="true" template="#=valueField#-#=textField#"
                         cname="发送类别" colWidth="3" valueTemplate="#=valueField#-#=textField#" filter="contains">
                <EF:EFOption label="全部" value=""/>
                <EF:EFOption label="工作日" value="A"/>
                <EF:EFOption label="全天不限" value="B"/>
            </EF:EFSelect>
        </div>
    </EF:EFRegion>
    <EF:EFRegion id="result" title="记录集">
        <input type="hidden" id="editRow"/>
        <EF:EFGrid blockId="result" isFloat="true" autoDraw="no" toolbarConfig="true" needAuth="true">
            <EF:EFColumn ename="uuid" cname="" hidden="true"/>
            <EF:EFColumn ename="subscribeInfo" editType="textarea" required="true" readonly="true" cname='推送信息'
                         width="300"/>
            <EF:EFColumn ename="subscribeRevisorTemp" cname='订阅人' required="true" readonly="true" width="130"/>
            <%--            <EF:EFColumn ename="subscribeSegNo" cname='订阅人业务单元' enable="false" readonly="true" width="120"/>--%>
            <EF:EFColumn ename="subscribeSegNoTemp" align="center" cname='订阅人业务单元' enable="false" readonly="true"
                         width="180"/>
            <EF:EFColumn ename="subscribeMobile" cname='订阅人手机号' enable="false" readonly="true" width="100"/>
            <EF:EFColumn ename="subscribeEmail" cname='订阅人邮箱' enable="false" readonly="true" width="200"/>
            <EF:EFComboColumn ename="msgType" align="center" autoWidth="true" readonly="true" cname="发送类别"
                              textField="textField"
                              width="150"
                              valueField="valueField" columnTemplate="#=valueField#-#=textField#"
                              itemTemplate="#=valueField#-#=textField#" required="true">
                <EF:EFOption label=" " value=" "/>
                <EF:EFOption label="工作日" value="A"/>
                <EF:EFOption label="全天不限" value="B"/>
            </EF:EFComboColumn>
            <EF:EFComboColumn ename="status" autoWidth="true" align="center" cname="状态" textField="textField"
                              width="100" readonly="true"
                              valueField="valueField" columnTemplate="#=valueField#-#=textField#"
                              itemTemplate="#=valueField#-#=textField#" required="true">
                <EF:EFOption label="未发" value="0"/>
                <EF:EFOption label="已发" value="1"/>
            </EF:EFComboColumn>
            <EF:EFColumn ename="sendTime" enable="false" readonly="true" width="130" cname="发送时间"
                         editType="datetime"
                         displayType="datetime" parseFormats="['yyyyMMddHHmmss']" dateFormat="yyyyMMddHHmmss"
                         align="center"/>
            <EF:EFColumn ename="pushHour" maxLength="5" data-regex="/^([01]?[0-9]|2[0-3]):([0-5][0])$/"
                         data-errorprompt="时间输入异常" cname='时(0-23)' width="100"/>
            <EF:EFColumn ename="recCreateTime" enable="false" readonly="true" width="130" cname="创建时间"
                         editType="datetime"
                         displayType="datetime" parseFormats="['yyyyMMddHHmmss']" dateFormat="yyyyMMddHHmmss"
                         align="center"/>
        </EF:EFGrid>
    </EF:EFRegion>
    <div style="display:none;">
        <EF:EFWindow id="clip" title="粘贴板导入" height="10%" width="25%">
            <textarea id="clipContent" name="clipContent" class="json_input" rows="16" style="width: 99%; height: 95%;"
                      spellcheck="false" placeholder="请粘贴"></textarea>
        </EF:EFWindow>
    </div>
</EF:EFPage>
