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
            <EF:EFSelect ename="inqu_status-0-subscribeFreq" filter="contains" cname="订阅频率"
                         template="#=valueField#-#=textField#"
                         valueTemplate="#=valueField#-#=textField#" textField="textField" valueField="valueField"
                         autoWidth="true"
                         colWidth="3">
                <EF:EFOption label="" value=""/>
                <EF:EFOption label="每天" value="D"/>
                <EF:EFOption label="每周" value="W"/>
                <EF:EFOption label="每月" value="M"/>
                <EF:EFOption label="每年" value="Y"/>
            </EF:EFSelect>
            <EF:EFSelect ename="inqu_status-0-pushMonth" filter="contains" cname="月"
                         template="#=valueField#-#=textField#"
                         valueTemplate="#=valueField#-#=textField#" textField="textField" valueField="valueField"
                         autoWidth="true"
                         colWidth="3">
                <EF:EFOption label="" value=""/>
                <EF:EFOption label="一月" value="01"/>
                <EF:EFOption label="二月" value="02"/>
                <EF:EFOption label="三月" value="03"/>
                <EF:EFOption label="四月" value="04"/>
                <EF:EFOption label="五月" value="05"/>
                <EF:EFOption label="六月" value="06"/>
                <EF:EFOption label="七月" value="07"/>
                <EF:EFOption label="八月" value="08"/>
                <EF:EFOption label="九月" value="09"/>
                <EF:EFOption label="十月" value="10"/>
                <EF:EFOption label="十一月" value="11"/>
                <EF:EFOption label="十二月" value="12"/>
            </EF:EFSelect>
            <EF:EFSelect ename="inqu_status-0-pushDate" filter="contains" cname="周"
                         template="#=valueField#-#=textField#"
                         valueTemplate="#=valueField#-#=textField#" textField="textField" valueField="valueField"
                         autoWidth="true"
                         colWidth="3">
                <EF:EFOption label="" value=""/>
                <EF:EFOption label="周一" value="1"/>
                <EF:EFOption label="周二" value="2"/>
                <EF:EFOption label="周三" value="3"/>
                <EF:EFOption label="周四" value="4"/>
                <EF:EFOption label="周五" value="5"/>
                <EF:EFOption label="周六" value="6"/>
                <EF:EFOption label="周日" value="7"/>
            </EF:EFSelect>
            <EF:EFInput ename="inqu_status-0-pushDay" cname="日" colWidth="3"
                        width="75"/>
            <EF:EFInput ename="inqu_status-0-pushHour" cname="时" colWidth="3"
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
            <EF:EFColumn ename="subscribeInfo" editType="textarea" required="true" cname='订阅信息' width="280"/>
            <EF:EFComboColumn ename="sendType" cname='推送方式'
                              width="90" textField="textField" filter="contains" autoWidth="true"
                              columnTemplate="#=valueField#-#=textField#"
                              itemTemplate="#=valueField#-#=textField#"
                              valueField="valueField" required="true" defaultValue=" " align="center">
                <EF:EFOption label="邮件" value="EMAIL"/>
                <EF:EFOption label="IMC" value="IMC"/>
                <EF:EFOption label="智慧圈" value="iBSL"/>
            </EF:EFComboColumn>
            <EF:EFPopupColumn ename="subscribeRevisor" align="center" width="50" cname="工号"
                              colWidth="4"
                              resizable="true" containerId="queryEmpCode" popupWidth="925"
                              popupHeight="500"
                              popupTitle="请选择:"
                              maxLength="8">
            </EF:EFPopupColumn>
            <EF:EFColumn ename="subscribeName" align="center" cname='姓名' readonly="true"
                         width="70"/>
            <EF:EFColumn ename="subscribeSegNo" align="center" cname='业务单元' readonly="true"
                         width="80"/>
            <EF:EFColumn ename="subscribeSegNoTemp" align="center" cname='业务单元简称' readonly="true"
                         width="105"/>
            <EF:EFColumn ename="subscribeMobile" cname='订阅人手机号' readonly="true" width="100"/>
            <EF:EFColumn ename="subscribeEmail" cname='订阅人邮箱' readonly="true" width="200"/>
            <EF:EFComboColumn ename="subscribeFreq" cname='订阅频率'
                              width="90" textField="textField" filter="contains" autoWidth="true"
                              columnTemplate="#=valueField#-#=textField#"
                              itemTemplate="#=valueField#-#=textField#"
                              valueField="valueField" readonly="true" required="true" defaultValue=" " align="center">
                <EF:EFOption label="每天" value="D"/>
                <EF:EFOption label="每周" value="W"/>
                <EF:EFOption label="每月" value="M"/>
                <EF:EFOption label="每年" value="Y"/>
            </EF:EFComboColumn>
            <EF:EFComboColumn ename="msgType" autoWidth="true" cname="发送类别" textField="textField" width="100"
                              valueField="valueField" columnTemplate="#=valueField#-#=textField#"
                              itemTemplate="#=valueField#-#=textField#" align="center" required="true">
                <EF:EFOption label=" " value=" "/>
                <EF:EFOption label="工作日" value="A"/>
                <EF:EFOption label="全天不限" value="B"/>
            </EF:EFComboColumn>
            <EF:EFComboColumn ename="pushDate" cname='周'
                              width="70" textField="textField" filter="contains" autoWidth="true"
                              columnTemplate="#=valueField#-#=textField#"
                              itemTemplate="#=valueField#-#=textField#"
                              valueField="valueField" defaultValue=" " align="center">
                <EF:EFOption label=" " value=" "/>
                <EF:EFOption label="周一" value="1"/>
                <EF:EFOption label="周二" value="2"/>
                <EF:EFOption label="周三" value="3"/>
                <EF:EFOption label="周四" value="4"/>
                <EF:EFOption label="周五" value="5"/>
                <EF:EFOption label="周六" value="6"/>
                <EF:EFOption label="周日" value="7"/>
            </EF:EFComboColumn>
            <EF:EFComboColumn ename="pushMonth" cname='月'
                              width="70" filter="contains" autoWidth="true" textField="textField"
                              columnTemplate="#=valueField#-#=textField#"
                              itemTemplate="#=valueField#-#=textField#"
                              valueField="valueField" defaultValue=" " align="center">
                <EF:EFOption label=" " value=" "/>
                <EF:EFOption label="一月" value="01"/>
                <EF:EFOption label="二月" value="02"/>
                <EF:EFOption label="三月" value="03"/>
                <EF:EFOption label="四月" value="04"/>
                <EF:EFOption label="五月" value="05"/>
                <EF:EFOption label="六月" value="06"/>
                <EF:EFOption label="七月" value="07"/>
                <EF:EFOption label="八月" value="08"/>
                <EF:EFOption label="九月" value="09"/>
                <EF:EFOption label="十月" value="10"/>
                <EF:EFOption label="十一月" value="11"/>
                <EF:EFOption label="十二月" value="12"/>
            </EF:EFComboColumn>
            <EF:EFComboColumn ename="pushDay" cname='日'
                              width="70" filter="contains" autoWidth="true" textField="textField"
                              columnTemplate="#=textField#"
                              itemTemplate="#=textField#"
                              valueField="valueField" defaultValue=" " align="center">
                <EF:EFOption label="1号" value="01"/>
                <EF:EFOption label="2号" value="02"/>
                <EF:EFOption label="3号" value="03"/>
                <EF:EFOption label="4号" value="04"/>
                <EF:EFOption label="5号" value="05"/>
                <EF:EFOption label="6号" value="06"/>
                <EF:EFOption label="7号" value="07"/>
                <EF:EFOption label="8号" value="08"/>
                <EF:EFOption label="9号" value="09"/>
                <EF:EFOption label="10号" value="10"/>
                <EF:EFOption label="11号" value="11"/>
                <EF:EFOption label="12号" value="12"/>
                <EF:EFOption label="13号" value="13"/>
                <EF:EFOption label="14号" value="14"/>
                <EF:EFOption label="15号" value="15"/>
                <EF:EFOption label="16号" value="16"/>
                <EF:EFOption label="17号" value="17"/>
                <EF:EFOption label="18号" value="18"/>
                <EF:EFOption label="19号" value="19"/>
                <EF:EFOption label="20号" value="20"/>
                <EF:EFOption label="21号" value="21"/>
                <EF:EFOption label="22号" value="22"/>
                <EF:EFOption label="23号" value="23"/>
                <EF:EFOption label="24号" value="24"/>
                <EF:EFOption label="25号" value="25"/>
                <EF:EFOption label="26号" value="26"/>
                <EF:EFOption label="27号" value="27"/>
                <EF:EFOption label="28号" value="28"/>
                <EF:EFOption label="29号" value="29"/>
                <EF:EFOption label="30号" value="30"/>
                <EF:EFOption label="31号" value="31"/>
            </EF:EFComboColumn>
            <EF:EFComboColumn ename="pushHour" cname='时'
                              width="105" filter="contains" autoWidth="true" textField="textField"
                              columnTemplate="#=textField#"
                              itemTemplate="#=textField#"
                              valueField="valueField" defaultValue=" " align="center">
                <EF:EFOption label="00:00" value="00:00"/>
                <EF:EFOption label="00:30" value="00:30"/>
                <EF:EFOption label="01:00" value="01:00"/>
                <EF:EFOption label="01:30" value="01:30"/>
                <EF:EFOption label="02:00" value="02:00"/>
                <EF:EFOption label="02:30" value="02:30"/>
                <EF:EFOption label="03:00" value="03:00"/>
                <EF:EFOption label="03:30" value="03:30"/>
                <EF:EFOption label="04:00" value="04:00"/>
                <EF:EFOption label="04:30" value="04:30"/>
                <EF:EFOption label="05:00" value="05:00"/>
                <EF:EFOption label="05:30" value="05:30"/>
                <EF:EFOption label="06:00" value="06:00"/>
                <EF:EFOption label="06:30" value="06:30"/>
                <EF:EFOption label="07:00" value="07:00"/>
                <EF:EFOption label="07:30" value="07:30"/>
                <EF:EFOption label="08:00" value="08:00"/>
                <EF:EFOption label="08:30" value="08:30"/>
                <EF:EFOption label="09:00" value="09:00"/>
                <EF:EFOption label="09:30" value="09:30"/>
                <EF:EFOption label="10:00" value="10:00"/>
                <EF:EFOption label="10:30" value="10:30"/>
                <EF:EFOption label="11:00" value="11:00"/>
                <EF:EFOption label="11:30" value="11:30"/>
                <EF:EFOption label="12:00" value="12:00"/>
                <EF:EFOption label="12:30" value="12:30"/>
                <EF:EFOption label="13:00" value="13:00"/>
                <EF:EFOption label="13:30" value="13:30"/>
                <EF:EFOption label="14:00" value="14:00"/>
                <EF:EFOption label="14:30" value="14:30"/>
                <EF:EFOption label="15:00" value="15:00"/>
                <EF:EFOption label="15:30" value="15:30"/>
                <EF:EFOption label="16:00" value="16:00"/>
                <EF:EFOption label="16:30" value="16:30"/>
                <EF:EFOption label="17:00" value="17:00"/>
                <EF:EFOption label="17:30" value="17:30"/>
                <EF:EFOption label="18:00" value="18:00"/>
                <EF:EFOption label="18:30" value="18:30"/>
                <EF:EFOption label="19:00" value="19:00"/>
                <EF:EFOption label="19:30" value="19:30"/>
                <EF:EFOption label="20:00" value="20:00"/>
                <EF:EFOption label="20:30" value="20:30"/>
                <EF:EFOption label="21:00" value="21:00"/>
                <EF:EFOption label="21:30" value="21:30"/>
                <EF:EFOption label="22:00" value="22:00"/>
                <EF:EFOption label="22:30" value="22:30"/>
                <EF:EFOption label="23:00" value="23:00"/>
                <EF:EFOption label="23:30" value="23:30"/>
            </EF:EFComboColumn>
            <EF:EFColumn ename="apprResume" align="center" cname="发送履历"/>
            <EF:EFColumn ename="recCreator" enable="false" readonly="true" cname="创建人" align="center" width="85"/>
            <EF:EFColumn ename="recCreatorName" enable="false" readonly="true" cname="创建人姓名" width="85"
                         align="center"/>
            <EF:EFColumn ename="recCreateTime" enable="false" readonly="true" width="130" cname="创建时间"
                         editType="datetime"
                         displayType="datetime" parseFormats="['yyyyMMddHHmmss']" dateFormat="yyyyMMddHHmmss"
                         align="center"/>
            <EF:EFColumn ename="recRevisor" enable="false" cname="修改人" align="center" width="85"/>
            <EF:EFColumn ename="recRevisorName" enable="false" readonly="true" width="85" cname="修改人姓名"
                         align="center"/>
            <EF:EFColumn ename="recRevisorTime" enable="false" readonly="true" width="130" cname="修改时间"
                         editType="datetime" displayType="datetime"
                         parseFormats="['yyyyMMddHHmmss']" dateFormat="yyyyMMddHHmmss" align="center"/>
        </EF:EFGrid>
    </EF:EFRegion>
    <div style="display:none;">
        <EF:EFWindow id="clip" title="粘贴板导入" height="10%" width="25%">
            <textarea id="clipContent" name="clipContent" class="json_input" rows="16" style="width: 99%; height: 95%;"
                      spellcheck="false" placeholder="请粘贴"></textarea>
        </EF:EFWindow>
    </div>
    <EF:EFWindow id="window" url="" height="80%" width="95%" left="2%"/>
    <div style="display: none;" id="queryEmpCode">
        <EF:EFRegion id="inquUser" title="查询条件">
            <div class="row">
                <EF:EFInput ename="inqu_user-0-subscribeRevisor" cname="工号" colWidth="3"/>
                <EF:EFInput ename="inqu_user-0-subscribeName" cname="姓名" colWidth="3"/>
                    <%--                <EF:EFInput ename="inqu_user-0-idcardNo" cname="员工类型" colWidth="3"/>--%>
                <EF:EFInput ename="inqu_user-0-subscribeSegNo" cname="业务单元" colWidth="3"/>
            </div>
            <div class="col-xs-4" style="text-align: right; float: right" id="inqu_group">
                <EF:EFButton ename="QUERY_USERB" cname="查询"></EF:EFButton>
            </div>
        </EF:EFRegion>

        <EF:EFRegion id="resultUser" title="用户信息">
            <EF:EFGrid blockId="resultUser" enable="false" serviceName="XBAI03" queryMethod="queryUser"
                       autoDraw="override" isFloat="true" height="360" checkMode="single">
                <EF:EFColumn ename="subscribeRevisor" cname="工号" width="60" align="center" enable="false"/>
                <EF:EFColumn ename="subscribeName" cname="姓名" width="60" align="center" enable="false"/>
                <EF:EFColumn ename="subscribeSegNo" cname="业务单元" width="60" align="center" enable="false"/>
                <EF:EFColumn ename="subscribeEmail" cname="邮箱" width="80" enable="false"/>
                <EF:EFColumn ename="subscribeMobile" cname="手机号" width="80" enable="false"/>
            </EF:EFGrid>
        </EF:EFRegion>
    </div>
</EF:EFPage>
