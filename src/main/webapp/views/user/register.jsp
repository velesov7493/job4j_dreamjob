<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<c:set var="pageTitle" value="Регистрация" />
<%@ include file="../../template/layouts/pageHeaderSimple.jsp" %>

<div class="row vert-center">
    <div class="card">
        <div class="card-header"><h2>Регистрация</h2></div>
        <div class="card-body">
            <form action="<%=request.getContextPath()%>/register.do" method="post">
                <div class="form-group">
                    <label>Ф.И.О:</label>
                    <input type="text" class="form-control" name="nName" required>
                    <label>Почта:</label>
                    <input type="text" class="form-control" name="nEmail" required>
                    <label>Пароль:</label>
                    <input type="password" class="form-control" name="nPassword" required>
                    <label>Проверка пароля (пароль еще раз):</label>
                    <input type="password" class="form-control" name="nCheckPassword" required>
                </div>
                <button type="submit" class="btn btn-primary pull-right">Зарегистрироваться</button>
                <c:if test="${not empty error}">
                    <div class="error-panel"><c:out value="${error}"/></div>
                </c:if>
            </form>
        </div>
    </div>
</div>

<%@ include file="../../template/layouts/pageFooter.jsp" %>