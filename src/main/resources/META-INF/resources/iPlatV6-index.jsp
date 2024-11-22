<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.baosight.iplat4j.core.FrameworkInfo" %>
<%@ page import="com.baosight.iplat4j.core.ei.EiConstant" %>
<%@ page import="com.baosight.iplat4j.core.ei.EiInfo" %>
<%@ page import="com.baosight.iplat4j.core.ioc.spring.PlatApplicationContext" %>
<%@ page import="com.baosight.iplat4j.core.log.Logger" %>
<%@ page import="com.baosight.iplat4j.core.log.LoggerFactory" %>
<%@ page import="com.baosight.iplat4j.core.security.SecurityTokenFilter" %>
<%@ page import="com.baosight.iplat4j.core.service.soa.XServiceManager" %>
<%@ page import="com.baosight.iplat4j.core.util.StringUtils" %>
<%@ page import="com.baosight.iplat4j.core.web.threadlocal.UserSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="EF" tagdir="/WEB-INF/tags/EF" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%
    UserSession.web2Service(request);
    String userName = UserSession.getLoginCName();
    String loginName = UserSession.getLoginName();
    request.setAttribute("userName", userName);
    request.setAttribute("loginName", loginName);
    String efSecurityToken = null;

    if (PlatApplicationContext.containsBean("securityTokenFilter")) {
        SecurityTokenFilter securityTokenFilter = (SecurityTokenFilter) PlatApplicationContext.getBean("securityTokenFilter");
        efSecurityToken = securityTokenFilter.getSecurityToken(request);
    }

    // 获取iPlatUI静态资源地址
    String iPlatStaticURL = FrameworkInfo.getPlatStaticURL(request);

    // 获取Context根路径，考虑到分布式部署的场景，不能直接使用WebContext
    String iPlatContext = FrameworkInfo.getPlatWebContext(request);

    String theme = org.apache.commons.lang.StringUtils.defaultIfEmpty(PlatApplicationContext.getProperty("theme"), "ant");

    // 获取首页菜单目录初始化参数
    String menuRoot = null;
    try {
        String projectName = PlatApplicationContext.getProperty("projectName");
        String moduleName = PlatApplicationContext.getProperty("moduleName");
        if (null != projectName && null != moduleName) {
            EiInfo eiInfo = new EiInfo();
            eiInfo.set(EiConstant.serviceId, "S_ED_21");
            eiInfo.set("project", projectName.toUpperCase());
            eiInfo.set("module", moduleName.toUpperCase());
            eiInfo.set("key", "menuRoot");
            EiInfo outInfo = XServiceManager.call(eiInfo);
            if ("".equals(outInfo.get("menuRoot")) ||
                    " ".equals(outInfo.get("menuRoot")) ||
                    null == outInfo.get("menuRoot")) {
                menuRoot = "root";
            } else {
                menuRoot = (String) outInfo.get("menuRoot");
            }
        }

    } catch (Exception e) {
        final Logger logger = LoggerFactory.getLogger("index");
        logger.error("无法获取首页菜单目录", e);
    }

    request.setAttribute("menuRoot", menuRoot);

//  获取首页APM指标刷新间隔参数(min)
    String apmRefresh = PlatApplicationContext.getProperty("apmRefresh").isEmpty() ? "5" :
            PlatApplicationContext.getProperty("apmRefresh");

    request.setAttribute("apmRefresh", apmRefresh);

    String projectCname = FrameworkInfo.getProjectCname();
    String projectTypeDesc = FrameworkInfo.getProjectTypeDesc();


%>
<%
    String logoutUrl = "login.jsp";
    try {
        if (null != Class.forName("com.baosight.eplat.be.aa.client.config.OauthClientConfig")) {
            logoutUrl = PlatApplicationContext.getProperty("eplat.security.client.logoutUrl");
            if (!StringUtils.isNotEmpty(logoutUrl)) {
                String clientAddrProperty = PlatApplicationContext.getProperty("eplat.security.client.app.url");
                String serverAddrProperty = PlatApplicationContext.getProperty("eplat.security.client.auth.url");
                logoutUrl = serverAddrProperty + "/logout?callback=" + clientAddrProperty + "/logout";
            }
            logoutUrl = logoutUrl + "&token=" + session.getAttribute("oauth2_token");
        }
    } catch (ClassNotFoundException ex) {
        final Logger logger = LoggerFactory.getLogger("index");
        logger.info("未对接认证，使用默认登出链接");
    }
    request.setAttribute("logoutUrl", logoutUrl);
%>

<c:set var="ctx" value="<%=iPlatContext%>"/>
<c:set var="iPlatStaticURL" value="<%=iPlatStaticURL%>"/>

<c:set var="theme" value="<%=theme%>" scope="session"/>

<!--[if IE 9]>
<html class="ie9 no-focus" xmlns="http://www.w3.org/1999/xhtml">
<![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-focus" xmlns="http://www.w3.org/1999/xhtml">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <meta name="robots" content="noindex, nofollow"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Cache-Control" content="public">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0"/>

    <% if (StringUtils.isNotEmpty(projectCname) && StringUtils.isNotEmpty(projectTypeDesc)) { %>
    <title><%=projectCname%>[<%=projectTypeDesc%>]首页</title>
    <% } else { %>
    <title>首页</title>
    <% } %>

    <%@include file="/WEB-INF/fragments/kendo-ui-css.tagf" %>
    <link rel="shortcut icon" href="${iPlatStaticURL}/iplat.ico" type="image/x-icon">
    <link href="${iPlatStaticURL}/iPlatV6-index.css" rel="stylesheet" type="text/css"/>

    <link rel="stylesheet" href="${iPlatStaticURL}/iplatui/assets/js/plugins/slick/slick.min.css">
    <link rel="stylesheet" href="${iPlatStaticURL}/iplatui/assets/js/plugins/slick/slick-theme.min.css">

    <script src="${iPlatStaticURL}/kendoui/js/jquery.min.js"></script>
    <script src="${iPlatStaticURL}/kendoui/js/kendo.all.min.js"></script>
    <script src="${iPlatStaticURL}/kendoui/js/messages/kendo.messages.zh-CN.min.js"></script>
    <script src="${iPlatStaticURL}/kendoui/js/cultures/kendo.culture.zh-CN.min.js"></script>
    <script src="${iPlatStaticURL}/iplatui/js/lib/underscore.min.js"></script>
    <script src="${iPlatStaticURL}/iplatui/js/lib/echarts.min.js"></script>

    <!--[if lte IE 8]>
    <link href="${iPlatStaticURL}/iPlatV6-index-ie.css" rel="stylesheet" type="text/css"/>

    <![endif]-->

    <script src="${iPlatStaticURL}/iplatui/js/iplat.ui.config.js"></script>
    <script src="${iPlatStaticURL}/iplatui/assets/js/plugins/slick/slick.min.js"></script>
    <script>
        IPLATUI.CONTEXT_PATH = "${ctx}";
        var taskRefeshTime = "${taskRefeshTime}";
        var needRefresh = "${needRefresh}" == 'true';
    </script>

    <script src="${iPlatStaticURL}/iplatui/assets/js/iplat.ui.bootstrap.min.js"></script>
    <script src="${iPlatStaticURL}/iplatui/js/iplat.ui.min.js"></script>
    <script src="${iPlatStaticURL}/iPlatV6-index.js"></script>
</head>

<body class="i-theme-${theme}">
<div id="page-container" class="i-index-content sidebar-l sidebar-o side-scroll header-navbar-fixed">
    <input type="hidden" id="efSecurityToken" value="<%=efSecurityToken%>"/>
    <div id="sidebar">
        <div class="sidebar-content">
            <!-- Side Header -->
            <div class="side-header">
                <span class="logo"></span>
                <%--<span class="projectType">[<%=projectTypeDesc%>]</span>--%>
            </div>
            <!-- END Side Header -->

            <!-- Side Content -->
            <div id="iplat-menu" class="side-content">
            </div>

            <div id="side-toggle" class="side-content-toggle">
                <span class="hide-mini fa fa-thumb-tack"></span>
                <span class="hide-normal fa fa-bars"></span>
            </div>
            <!-- END Side Content -->
        </div>
        <!-- Sidebar Content -->
    </div>

    <div id="header-navbar">
        <ul class="nav-header pull-right">

            <%--<li style="margin-right: 0"><a href="#" class="fa fa-search">
                <span style="margin-left:3px">搜索</span></a>
            </li>--%>

            <li class="search-input">
                <input id="inqu_status-0-form_ename" name="inqu_status-0-form_ename" placeholder="请输入页面号">
                <i class="fa fa-search iconPosition"></i>
            </li>
            <li id="user-info-dropdown">
                <a href="#" class="fa fa-user" id="user-info" data-toggle="dropdown" aria-expanded="true">
                    <span><%=userName%></span>
                </a>
                <%--DAM包中使用--%>
                <%--<ul class="dropdown-menu dropdown-menu-right">--%>
                <%--<li>--%>
                <%--<a id='myInfo' href="#">--%>
                <%--<i class="fa fa-user" style="margin-right: 5px"></i>--%>
                <%--我的信息</a>--%>
                <%--</li>--%>
                <%--<li class="divider"></li>--%>
                <%--<li><a id='modifiedPassWord' href="#"><i class="fa fa-pencil-square-o"--%>
                <%--style="margin-right: 5px"></i>修改密码</a></li>--%>
                <%--<li class="divider"></li>--%>
                <%--</ul>--%>

                <%--DAM包中注释掉此段 ul 开始--%>
                <%--<ul class="dropdown-menu dropdown-menu-right profile" data-stopPropagation="true">
                    <span class="dropdown-triangle"></span>
                    <li>
                        <div class="info-title">
                            <span class="user-name">${userName}</span>
                            <span class="information">工号:</span>
                            <span class="detail-info">${userId}</span>
                            <span class="information">区域:</span>
                            <span class="detail-info"></span>
                            <a class="k-link change-password" target="_blank" href="${ctx}/web/XS0104">修改密码</a>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="information">岗号:</span>
                            <span class="detail-info"></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="information">部门:</span>
                            <span class="detail-info"></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="information">领用账套:</span>
                            <span class="detail-info"></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="information">采购账套:</span>
                            <span class="detail-info"></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="information i-last">采购组织:</span>
                            <span class="detail-info"></span>
                        </div>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <div>
                            <span class="information i-first">联系方式:</span>
                            <span class="detail-info"></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="information">邮件:</span>
                            <span class="detail-info"></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="information">地址:</span>
                            <span class="detail-info"></span>
                        </div>
                    </li>
                </ul>--%>
                <div class="dropdown-menu dropdown-menu-right profile" data-stopPropagation="true">
                    <span class="dropdown-triangle"></span>
                    <div class="fl">
                        <div class="headPortrait">
                            <img src="iplatui/img/index/headPortrait.png" alt="headPortrait">
                        </div>
                        <div>
                            <ul class="personal-information">
                                <li class="userName">
                                    <span>${userName}</span>
                                </li>
                                <li>
                                    <span class="job-number">工号:</span>
                                    <span>${loginName}</span>
                                </li>
                                <li>
                                    <span class="information">组织机构:</span>
                                    <span class="detail-info user-org"></span>
                                </li>
                                <%--<li data-accountset="true">
                                    <span class="information k-link change-org">公司别:</span>
                                    <span class="detail-info user-company"></span>
                                </li>
                                <li data-accountset="true">
                                    <span class="information k-link change-org">账套:</span>
                                    <span class="detail-info user-accountset"></span>
                                </li>--%>
                            </ul>
                        </div>
                        <div class="change-password">
                            <a target="_blank" href="${ctx}/web/XS0104">修改密码</a>
                        </div>
                    </div>
                    <%--<div class="cut-off-rule"></div>--%>
                    <div>
                        <ul class="fl left-distance">
                            <%--<li data-org="purc">
                                <ul class="ul-left-distance-one">
                                    <li class="purchase">
                                        <img src="iplatui/img/index/采购组织.png" alt="purchase">
                                    </li>
                                    <li class="purchase-inf">
                                        <span>采购组织</span>
                                    </li>
                                    <li class="three-info">
                                        <span class="detail-info user-purc-org"></span>
                                        <span class="detail-info user-purc-org-oth"></span>
                                    </li>
                                </ul>
                            </li>
                            <div class="li-line"></div>
                            <li class="li-left-distance li-right-margin" data-org="sale">
                                <ul class="ul-left-distance-two">
                                    <li class="sale">
                                        <img src="iplatui/img/index/营销组织.png" alt="sale">
                                    </li>
                                    <li class="sale-inf">
                                        <span>营销组织</span>
                                    </li>
                                    <li class="three-info">
                                        <span class="detail-info user-sale-org"></span>
                                        <span class="detail-info user-sale-org-oth"></span>
                                    </li>
                                </ul>
                            </li>
                            <div class="li-line"></div>
                            <li class="li-left-distance" data-org="req">
                                <ul class="ul-left-distance-two">
                                    <li class="purchase">
                                        <img src="iplatui/img/index/申请组织.png" alt="req">
                                    </li>
                                    <li class="req-inf">
                                        <span>申请组织</span>
                                    </li>
                                    <li class="three-info">
                                        <span class="detail-info user-req-org"></span>
                                        <span class="detail-info user-req-org-oth"></span>
                                    </li>
                                </ul>
                            </li>--%>
                        </ul>
                    </div>
                </div>
                <%--DAM包中注释掉此段 ul 结束--%>

            </li>
            <li id="toggle-view-mode">
                <a href="#" class="fa fa-files-o" id="new-window-view">
                    <span data-toggle="tooltip" data-placement="bottom"
                          data-original-title="在新窗口中打开页面">多页展示</span>
                </a>
                <a href="#" class="fa fa-file-o" id="tab-view" style="display: none">
                    <span data-toggle="tooltip" data-placement="bottom"
                          data-original-title="在当前窗口中打开页面">单页展示</span>
                </a>
            </li>
            <li>
                <%--<a class="logout" href="login.jsp" data-t oggle="tooltip" data-placement="bottom"
                   data-original-title="注销">
                    <i class="fa fa-sign-out"></i>
                </a>--%>
                <a class="logout" href="${logoutUrl}" data-toggle="tooltip" data-placement="bottom"
                   data-original-title="注销">
                    <i class="fa fa-sign-out"></i>
                </a>

            </li>
        </ul>
        <ul class="nav-header pull-left">
        </ul>
    </div>

    <div id="main-container" class="main-content">
        <EF:EFTab id="page-list" showClose="false" active="0">
            <div title="首页" style="padding: 8px">
                <div class="row">
                    <div class="col-sm-9 i-index-todo i-index-top">
                        <EF:EFTab id="info-board" class="i-index-top">
                            <div class="i-index-tab" title="待办">
                                <div>
                                    <ul id="todo"></ul>
                                </div>
                                <a class="for-refresh"><i class="fa fa-refresh"></i> 刷新</a>
                                <div class="refresh-timer hide">距离下次刷新时间还剩<span class="spare-seconds">00:00</span>/<span
                                        class="refresh-limits">00:00</span></div>
                            </div>
                            <div class="i-index-tab" title="提醒">
                                <div id="information"></div>
                                    <%--<a class="for-more"><i class="fa fa-angle-double-right"></i> 查看更多</a>--%>
                                <a class="for-refresh"><i class="fa fa-refresh"></i> 刷新</a>
                            </div>
                            <div class="i-index-tab" title="跟踪">
                                <div id="trace"></div>
                                <a class="for-more"><i class="fa fa-angle-double-right"></i> 查看更多</a>
                                <a class="for-refresh"><i class="fa fa-refresh"></i> 刷新</a>
                            </div>
                            <div class="i-index-tab" title="已办">
                                <div id="record"></div>
                                <a class="for-more"><i class="fa fa-angle-double-right"></i> 查看更多</a>
                                <a class="for-refresh"><i class="fa fa-refresh"></i> 刷新</a>
                            </div>
                            <div class="i-index-tab" title="通知">
                                <div id="notification"></div>
                                <a class="for-more"><i class="fa fa-angle-double-right"></i> 查看更多</a>
                                <a class="for-refresh"><i class="fa fa-refresh"></i> 刷新</a>
                            </div>
                            <div class="i-index-tab" title="公告">
                                <div id="notice" style="height: 100%; overflow:hidden;"></div>
                                <a class="for-refresh"><i class="fa fa-refresh"></i> 刷新</a>
                            </div>
                        </EF:EFTab>
                    </div>
                    <div class="col-sm-3">
                        <div class="index-apm i-index-top">
                                <%--<div class="i-region-header">
                                    <span class="index-meet"></span>日历
                                </div>--%>
                            <div class="i-region-picture">
                                <img id="solarTermsPic" src="">
                            </div>
                            <div class="i-region-content">
                                <div class="fp-calendar">
                                    <script>
                                        $(".fp-calendar").kendoCalendar({
                                            culture: "zh-CN",
                                            footer: false
                                        });
                                    </script>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row" style="padding-top: 8px">
                    <div class="col-sm-9">
                        <div class="index-charts i-index-bottom">
                            <div class="i-region-header"><span class="index-dashboard"></span>业务快捷</div>
                            <div class="i-region-content" id="i-region-dots-append">
                                <div class="slick">
                                    <div style="background-color:rgba(255, 255, 255, 0.8)" class=" ">
                                        <ul class="aliceblue">
                                            <li>
                                                <img class="onclickOpenFrom1" src="iplatui/img/index/工时填报.png"
                                                     alt="项目模块信息管理">
                                                <p class="onclickOpenFrom1">项目模块管理</p>
                                            </li>
                                            <li>
                                                <img class="onclickOpenFrom2" src="iplatui/img/index/报销录入.png"
                                                     alt="页面信息管理">
                                                <p class="onclickOpenFrom2">页面信息管理</p>
                                            </li>
                                            <li>
                                                <img class="onclickOpenFrom3" src="iplatui/img/index/我的报销.png"
                                                     alt="按钮信息管理">
                                                <p class="onclickOpenFrom3">按钮信息管理</p>
                                            </li>
                                            <li>
                                                <img class="onclickOpenFrom4" src="iplatui/img/index/报销额度.png"
                                                     alt="菜单信息管理">
                                                <p class="onclickOpenFrom4">菜单信息管理</p>
                                            </li>
                                        </ul>
                                    </div>
                                    <div style="background-color:rgba(255, 255, 255, 0.8)" class=" ">
                                        <ul class="aliceblue">
                                            <li>
                                                <img class="onclickOpenFrom5" src="iplatui/img/index/项目查询.png"
                                                     alt="微服务信息管理">
                                                <p class="onclickOpenFrom5">微服务信息管理</p>
                                            </li>
                                            <li>
                                                <img class="onclickOpenFrom6" src="iplatui/img/index/项目变更.png"
                                                     alt="微服务事件管理">
                                                <p class="onclickOpenFrom6">微服务事件管理</p>
                                            </li>
                                            <li>
                                                <img class="onclickOpenFrom7" src="iplatui/img/index/项目状态报告.png"
                                                     alt="配置环境">
                                                <p class="onclickOpenFrom7">配置环境管理</p>
                                            </li>
                                            <li>
                                                <img class="onclickOpenFrom8" src="iplatui/img/index/完工办理.png"
                                                     alt="配置信息">
                                                <p class="onclickOpenFrom8">配置信息管理</p>
                                            </li>
                                        </ul>
                                    </div>

                                    <div style="background-color:rgba(255, 255, 255, 0.8)" class=" ">
                                        <ul class="aliceblue">
                                            <li>
                                                <img class="onclickOpenFrom9" src="iplatui/img/index/销售合同评审.png"
                                                     alt="用户信息管理">
                                                <p class="onclickOpenFrom9">用户信息管理</p>
                                            </li>
                                            <li>
                                                <img class="onclickOpenFrom10" src="iplatui/img/index/合同执行跟踪.png"
                                                     alt="用户群组管理">
                                                <p class="onclickOpenFrom10">用户群组管理</p>
                                            </li>
                                            <li>
                                                <img class="onclickOpenFrom11" src="iplatui/img/index/采购申请.png"
                                                     alt="代码类别维护">
                                                <p class="onclickOpenFrom11">代码类别管理</p>
                                            </li>
                                            <li>
                                                <img class="onclickOpenFrom12" src="iplatui/img/index/外协审签.png"
                                                     alt="序列号定义管理">
                                                <p class="onclickOpenFrom12">序列号管理</p>
                                            </li>
                                        </ul>
                                    </div>

                                    <div style="background-color:rgba(255, 255, 255, 0.8)" class=" ">
                                        <ul class="aliceblue">
                                            <li>
                                                <img class="onclickOpenFrom13" src="iplatui/img/index/预算送审.png"
                                                     alt="资源信息管理">
                                                <p class="onclickOpenFrom13">资源信息管理</p>
                                            </li>
                                            <li>
                                                <img class="onclickOpenFrom14" src="iplatui/img/index/预算履历.png"
                                                     alt="资源组信息管理">
                                                <p class="onclickOpenFrom14">资源组管理</p>
                                            </li>
                                            <li>
                                                <img class="onclickOpenFrom15" src="iplatui/img/index/划转审批.png"
                                                     alt="资源群组成员信息管理">
                                                <p class="onclickOpenFrom15">资源群组成员管理</p>
                                            </li>
                                            <li>
                                                <img class="onclickOpenFrom16" src="iplatui/img/index/划转录入.png"
                                                     alt="授权管理">
                                                <p class="onclickOpenFrom16">授权管理</p>
                                            </li>
                                        </ul>
                                    </div>
                                        <%--                                <div style="background-color: aliceblue"></div>
                                                                        <div style="background-color: cornsilk"></div>
                                                                        <div style="background-color: whitesmoke"></div>
                                                                        <div style="background-color: ghostwhite"></div>
                                                                        <div style="background-color: aliceblue"></div>
                                                                        <div style="background-color: cornsilk"></div>
                                                                        <div style="background-color: whitesmoke"></div>
                                                                        <div style="background-color: ghostwhite"></div>
                                                                        <div style="background-color: aliceblue"></div>
                                                                        <div style="background-color: cornsilk"></div>
                                                                        <div style="background-color: whitesmoke"></div>
                                                                        <div style="background-color: ghostwhite"></div>--%>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 i-index-todo">
                        <EF:EFTab id="info">
                            <div class="i-index-tab index-favorite" title="收藏">
                                <div class="i-region-content">
                                    <div id="fav-list" class="fav-list"></div>
                                </div>
                                <a class="for-more moreCollection"><i class="fa fa-angle-double-right"></i> 查看更多</a>
                            </div>
                            <div class="i-index-tab index-links" title="友情链接">
                                <div>
                                    <div class="dropup open">
                                        <ul class="dropdown-menu" role="menu">
                                            <li>
                                                <div class="link-list">
                                                        <%--<span class="index-icon index-related-clip"></span>--%>
                                                    <a>暂无友情链接！</a>
                                                </div>
                                            </li>
                                                <%--<li>
                                                    <div class="link-list">
                                                        <span class="index-icon index-related-clip"></span>
                                                        <a target="_blank" href="${ctx}/web/EDFA10">收藏页</a>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="link-list">
                                                        <span class="index-icon index-related-clip"></span>
                                                        <a target="_blank" href="${ctx}/web/EEDM4004">AutoComplete示例</a>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="link-list">
                                                        <span class="index-icon index-related-clip"></span>
                                                        <a target="_blank" href="${ctx}/web/EDFA61">用户自选风格管理表</a>
                                                    </div>
                                                </li>--%>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </EF:EFTab>
                    </div>
                </div>
            </div>
            <%--第一项删除会被关闭--%>
        </EF:EFTab>
    </div>
</div>

<%--菜单结点名称--%>
<input type="hidden" name="iplatMenuName" value="${menuRoot}" validate="false" novalidate="novalidate"/>

<EF:EFFragment/>
<script id="menu-template" type="text/x-kendo-template">
    <ul class="iplat-menu-ul">
        #for(var index in menus){ var menu = menus[index]#
        <li class="iplat-menu" data-page="#:menu.label#" data-url="#:menu.nodeUrl#"
            data-param="#:menu.nodeParam#" data-leaf="#:menu.leaf#">
            <a class="i-sub-#:menu.leaf#" href="\\#">
                <i class="i-menu-icon # var iconCss = IPLAT.Util.parseBtnClass(menu.imagePath).css;
                if (iconCss !== '') {# #:iconCss# #} else {# fa fa-cube #} #"></i>
                <span class="sidebar-mini-hide iplat-menu-title">#:menu.text#</span>
            </a>
            <div id="tree#=menu.label#" class="iplat-menu-content" data-first="true">
            </div>
        </li>
        #}#
    </ul>
</script>
<script id="menu-item-template" type="text/x-kendo-template">
    #: item.text #
    # if ( !item.leaf && IPLATUI.Config.Layout.MENU_WITH_FORM_ENAME) {#
    [#: item.label #]
    #}#
</script>

<script id="process-template" type="text/x-kendo-template">
    <p style="margin-bottom: 0">您有<a class="i-index-process"
                                     target="_blank"
                                     href="${ctx}/web/#:form#">[#:count#]</a>个#:processName##:taskDefName#
    </p>
</script>
<script id="task-template" type="text/x-kendo-template">
    <a target="_blank"
       href="${ctx}/web/#:form#?procInstId=#:procInstId#">#:subject#</a>
</script>
<script id="favorite-template" type="text/x-kendo-template">
    <div class="k-link k-header" ename="#:form_ename#"><span
            class="index-icon index-clip"></span>#:form_ename# - #:form_cname#
    </div>
</script>

<script>
    window.IPLAT_INDEX_WINDOW = "rootWindow";
    window.APM_REFRESH = "${apmRefresh}";
    $(function () {
        V6Index();
    });

</script>
<!--[if lte IE 8]>
<script src="${iPlatStaticURL}/iplatui/assets/js/polyfills/iplat.ui.ie8.polyfills.min.js"></script>
<![endif]-->
</body>
</html>
