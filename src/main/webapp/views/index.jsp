<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<c:set var="pageTitle" value="Работа мечты!" />
<%@ include file="../template/layouts/pageHeader.jsp" %>

<div class="container">
    <div class="row">
        <div class="card">
            <div class="card-header"><h2>Разделы</h2></div>
            <div class="card-body">
                <ul class="menu">
                    <li class="item-left"><a class="btn btn-default" href="<%=request.getContextPath()%>/posts.do">Вакансии</a></li>
                    <li class="item-left"><a class="btn btn-default" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a></li>
                    <li class="item-left"><a class="btn btn-default" href="<%=request.getContextPath()%>/posts.do?id=0">Добавить вакансию</a></li>
                    <li class="item-left"><a class="btn btn-default" href="<%=request.getContextPath()%>/candidates.do?id=0">Добавить кандидата</a></li>
                    <li class="item-right"><a class="btn btn-default" href="<%=request.getContextPath()%>/auth.do"><i class="fa fa-lock"></i>&nbsp;Вход</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="card">
            <div class="card-header">
                <h4>Сегодняшние вакансии</h4>
            </div>
            <div class="card-body">
            </div>
        </div>
    </div>
    <div class="row">
        <div class="card">
            <div class="card-header">
                <h4>Сегодняшние кандидаты</h4>
            </div>
            <div class="card-body">
            </div>
        </div>
    </div>
</div>

<%@ include file="../template/layouts/pageFooter.jsp" %>