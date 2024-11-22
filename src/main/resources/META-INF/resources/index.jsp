<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title></title>
</head>
<body>
</body>

<script type="text/javascript">
    var load_time = new Date();
    var load_label = "indexReal" + load_time.getTime();

    window.location.href = '${ctx}/iPlatV6-index.jsp';
</script>

</html>