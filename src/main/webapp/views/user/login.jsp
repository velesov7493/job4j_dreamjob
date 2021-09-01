<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<c:set var="pageTitle" value="Вход в систему" />
<%@ include file="../../template/layouts/pageHeader.jsp" %>

<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header"><h2>Авторизация</h2></div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/auth.do" method="post">
                    <div class="form-group">
                        <label>Почта:</label>
                        <input type="text" class="form-control" name="email">
                    </div>
                    <div class="form-group">
                        <label>Пароль:</label>
                        <input type="password" class="form-control" name="password">
                    </div>
                    <button type="submit" class="btn btn-primary pull-right">Войти</button>
                    <c:if test="${not empty error}">
                        <div class="error-panel">${error}</div>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="../../template/layouts/pageFooter.jsp" %>