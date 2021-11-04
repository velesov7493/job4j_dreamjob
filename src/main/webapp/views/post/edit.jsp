<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<c:set var="pageTitle" value="Редактирование вакансии"/>
<c:set var="pageScript" value="post.js"/>
<%@ include file="../../template/layouts/pageHeader.jsp" %>

<div class="row">
    <div class="card">
        <div class="card-header">
            <a class="btn btn-default pull-left" href="<%=request.getContextPath()%>/posts.do">&lt&lt</a>
            <c:if test="${post.id == 0}">
                <h2>Новая вакансия</h2>
            </c:if>
            <c:if test="${post.id != 0}">
                <h2>Редактирование вакансии</h2>
            </c:if>
        </div>
        <div class="card-body">
            <form action="<c:url value="/posts.do?id=${post.id}"/>" method="post">
                <div class="form-group">
                    <label>Наименование вакансии:</label>
                    <input id="name" name="nPosition" type="text" class="form-control"
                           value="<c:out value="${post.name}"/>" title="Заполните наименование вакансии!" required>
                    <label>Описание вакансии:</label>
                    <textarea id="description" name="nDescription" class="form-control"
                              title="Заполните описание вакансии!" required><c:out
                            value="${post.description}"/></textarea>
                </div>
                <button type="submit" class="btn btn-primary pull-right" onclick="prepareSave()">Сохранить</button>
            </form>
        </div>
    </div>
</div>

<%@ include file="../../template/layouts/pageFooter.jsp" %>
