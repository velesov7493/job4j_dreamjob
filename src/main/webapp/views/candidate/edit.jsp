<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<c:set var="pageTitle" value="Редактирование кандидата" />
<c:set var="pageScript" value="candidate.js" />
<%@ include file="../../template/layouts/pageHeader.jsp" %>

<div class="row">
    <div class="card">
        <div class="card-header">
            <a class="btn btn-default pull-left" href="<%=request.getContextPath()%>/candidates.do">&lt;&lt;</a>
            <c:if test="${candidate.id == 0}">
            <h2>Новый кандидат</h2>
            </c:if>
            <c:if test="${candidate.id != 0}">
            <h2>Редактирование кандидата</h2>
            </c:if>
        </div>
        <img class="stretch" src="<c:url value="/image?id=${candidate.id}"/>" alt="Фото кандидата"/>
        <div class="col-lg-12 col-md-12 col-sm-12">
            <ul class="menu">
                <li class="item-left"><a class="btn btn-default" href="<c:url value="/image?id=${candidate.id}&delete=1"/>">Удалить фото</a></li>
                <li class="item-left"><a class="btn btn-default" href="<c:url value="/image?id=${candidate.id}&download=1"/>">Скачать фото</a></li>
            </ul>
        </div>
        <div class="card-body">
            <form action="<c:url value="/candidates.do"/>" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label>Ф.И.О.</label>
                    <input id="name" name="nName" type="text" class="form-control" value="<c:out value="${candidate.name}"/>" title="Заполните имя кандидата!" required/>
                    <label>Претендует на позицию</label>
                    <input id="position" name="nPosition" type="text" class="form-control" value="<c:out value="${candidate.position}"/>" title="Заполните позицию, на которую претендует кандидат!" required/>
                    <label>Город</label>
                    <select id="cityList" class="form-control">
                        <option id="null" selected="1">Нет города</option>
                    </select>
                    <input type="hidden" name="nSelectedCityId" id="selectedCityId" value="<c:out value="${candidate.cityId}"/>"/>
                </div>
                <div class="form-group">
                    <label>Фото</label>
                    <input name="nPhoto" type="file"/>
                    <input name="nCandidateId" type="hidden" value="<c:out value="${candidate.id}"/>"/>
                </div>
                <button type="submit" class="btn btn-primary pull-right" onclick="prepareSave()">Сохранить</button>
            </form>
        </div>
    </div>
</div>

<%@ include file="../../template/layouts/pageFooter.jsp" %>