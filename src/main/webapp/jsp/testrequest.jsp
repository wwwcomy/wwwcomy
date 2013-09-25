<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello World</title>
</head>
<body>
	<h2>request.getContextPath()</h2>
	<%
		out.print(request.getContextPath());
	%>

	<h2>request.getRequestURI()</h2>
	<%
		out.print(request.getRequestURI());
	%>

	<h2>request.getServletPath()</h2>
	<%
		out.print(request.getServletPath());
	%>

	<h2>request.getRequestURL()</h2>
	<%
		out.print(request.getRequestURL());
	%>


	<h2>request.getRealPath(String) @Deprecated</h2>

	<h2>request.getQueryString()</h2>
	<%
		out.print(request.getQueryString());
	%>
</body>
</html>