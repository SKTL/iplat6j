<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.baosight.iplat4j.core.FrameworkInfo" %>
<%@ page import="java.io.File" %>

<%
	String domain = FrameworkInfo.getProjectAppTopDomain();
	if (domain != null && domain.startsWith(".")) {
		domain = domain.substring(1);
%>
<script type="text/javascript">
    try {
        document.domain = '<%= domain %>';
    } catch (ex) {
        alert('model not valid[<%= domain %>]');
    }
</script>
<%
	}
%>

<%
	String directmode = request.getParameter("maintain") != null ? request.getParameter("maintain").toString() : "";
	RequestDispatcher rd;
	String appName = FrameworkInfo.getProjectAppName();

	org.springframework.security.core.context.SecurityContextHolder.clearContext();
	session.setAttribute("iplat.logout", "1");
	session.invalidate();
	String entryChoice = FrameworkInfo.getText("entryChoice");
	String url = "/DispatchAction.do?efFormEname=ECTM40&nodeId=0000000099999999&nodeType=s&templateType=0";
	if (entryChoice.equals("template")) {
		//从模板进入登录页面

		rd = request.getRequestDispatcher(url);
		try {
			rd.forward(request, response);
		} catch (ServletException e1) {
			e1.printStackTrace();
		} finally {
			out.clear();
			out = pageContext.pushBody();
		}


	} else if (entryChoice.equals("static")) {
		// 从静态页面进入登录页面
		// 判断缓存文件是否存在
		String requestURL = request.getRequestURL().toString();
		String _url = requestURL.substring(0, requestURL.indexOf(request.getContextPath())) + request.getContextPath();
		String _urlHtml = "/login.html";
		java.io.File file = new java.io.File(request.getRealPath("") + _urlHtml);

		if (!file.exists()) {
			//生成对应的html静态文件
			try {
				//+url;
				java.io.FileOutputStream blankFile = new java.io.FileOutputStream(request.getRealPath("") + _urlHtml);
				blankFile.write(new byte[0]);

				java.net.URL requestUrl = new java.net.URL(_url);

				System.out.println(_url);

				java.net.HttpURLConnection con = (java.net.HttpURLConnection) requestUrl.openConnection();
				con.connect();

				java.io.FileOutputStream fileout = new java.io.FileOutputStream(request.getRealPath("") + _urlHtml);

				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = con.getInputStream().read(bytes)) != -1) {
					fileout.write(bytes, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
				out.print("生成静态html文件出错！");
			}
		}

		File _file = new File(request.getRealPath("") + _urlHtml);
		if (_file.length() == 0) {
			rd = request.getRequestDispatcher(url);

			try {
				rd.forward(request, response);
			} catch (ServletException e1) {
				e1.printStackTrace();
			} finally {
				out.clear();
				out = pageContext.pushBody();
			}
		} else {
			response.sendRedirect(request.getContextPath() + _urlHtml);
		}


	} else {
		//以正常方式进入登录首页
//      rd = request.getRequestDispatcher("iplat-v6-login.jsp");
		rd = request.getRequestDispatcher("iPlatV6-login.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException e1) {
			e1.printStackTrace();
		} finally {
			out.clear();
			out = pageContext.pushBody();
		}
	}


%>


