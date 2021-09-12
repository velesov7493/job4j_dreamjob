<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<c:set var="pageTitle" value="Список вакансий" />
<%@ include file="../../template/layouts/pageHeader.jsp" %>

<div class="row">
    <div class="card">
        <div class="card-header">
            <a class="btn btn-default pull-left" href="<%=request.getContextPath()%>/index.do">&lt;&lt;</a>
            <h2>Вакансии</h2>
        </div>
        <div class="card-body">
            <table class="table">
                <thead>
                    <tr>
                        <th>№ п/п</th>
                        <th>Наименование</th>
                        <th>Операции</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${posts}" var="post">
                    <tr>
                        <td><c:out value="${post.id}"/></td>
                        <td><c:out value="${post.name}"/></td>
                        <td>
                            <a href="<c:url value="posts.do?id=${post.id}"/>"><i class="fa fa-edit mr-3"></i></a>&nbsp;
                            <a href="<c:url value="posts.do?id=${post.id}&delete=1"/>"><i class="fa fa-times mr-3"></i></a>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="../../template/layouts/pageFooter.jsp" %>