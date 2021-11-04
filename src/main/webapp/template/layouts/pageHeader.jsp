<%@ page import="ru.job4j.dreamjob.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    User user = (User) request.getSession().getAttribute("user");
%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${pageTitle}"/></title>
    <link href="<%=request.getContextPath()%>/template/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/template/css/font-awesome.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/template/images/favicon.png" rel="shortcut icon" type="image/png">
    <script src="<%=request.getContextPath()%>/template/js/jquery.js"></script>
    <script src="<%=request.getContextPath()%>/template/js/bootstrap.min.js"></script>
    <c:if test="${not empty pageScript}">
        <script src="<%=request.getContextPath()%>/template/js/<c:out value="${pageScript}"/>"></script>
    </c:if>
    <link href="<%=request.getContextPath()%>/template/css/main.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <header id="header">
        <div class="row header-top">
            <p class="pull-right">Приветствуем, <%= user == null ? "Гость" : user.getName()%>
            </p>
        </div>
        <div class="row header-bottom">
            <ul class="menu">
                <li class="item-left"><a class="btn btn-default" href="<%=request.getContextPath()%>/posts.do"><i
                        class="fa fa-tasks"></i> Вакансии</a></li>
                <li class="item-left"><a class="btn btn-default" href="<%=request.getContextPath()%>/candidates.do"><i
                        class="fa fa-users"></i> Кандидаты</a></li>
                <li class="item-left"><a class="btn btn-default" href="<%=request.getContextPath()%>/posts.do?id=0"><i
                        class="fa fa-plus-square"></i> Добавить вакансию</a></li>
                <li class="item-left"><a class="btn btn-default"
                                         href="<%=request.getContextPath()%>/candidates.do?id=0"><i
                        class="fa fa-plus-circle"></i> Добавить кандидата</a></li>
                <% if (user == null) { %>
                <li class="item-right"><a class="btn btn-default" href="<%=request.getContextPath()%>/auth.do"><i
                        class="fa fa-lock"></i> Вход</a></li>
                <% } else { %>
                <li class="item-right"><a class="btn btn-default" href="<%=request.getContextPath()%>/logout.do"><i
                        class="fa fa-unlock"></i> Выход</a></li>
                <% } %>
            </ul>
        </div>
    </header>