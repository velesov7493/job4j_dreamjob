<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<c:set var="pageTitle" value="Работа мечты!"/>
<%@ include file="../template/layouts/pageHeader.jsp" %>

<div class="row">
    <div class="card">
        <div class="card-header">
            <h4>Сегодняшние вакансии</h4>
        </div>
        <div class="card-body">
            <table class="table">
                <thead>
                <tr>
                    <th>№ п/п</th>
                    <th>Наименование</th>
                    <th>Ред.</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${posts}" var="post">
                    <tr>
                        <td><c:out value="${post.id}"/></td>
                        <td><c:out value="${post.name}"/></td>
                        <td><a href="<c:url value="posts.do?id=${post.id}"/>"><i class="fa fa-edit mr-3"></i></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="row">
    <div class="card">
        <div class="card-header">
            <h4>Сегодняшние кандидаты</h4>
        </div>
        <div class="card-body">
            <table class="table">
                <thead>
                <tr>
                    <th>№ п/п</th>
                    <th>Ф.И.О.</th>
                    <th>Претендует на позицию</th>
                    <th>Ред.</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${candidates}" var="candidate">
                    <tr>
                        <td><c:out value="${candidate.id}"/></td>
                        <td><c:out value="${candidate.name}"/></td>
                        <td><c:out value="${candidate.position}"/></td>
                        <td><a href="<c:url value="/candidates.do?id=${candidate.id}"/>"><i class="fa fa-edit mr-3"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="../template/layouts/pageFooter.jsp" %>