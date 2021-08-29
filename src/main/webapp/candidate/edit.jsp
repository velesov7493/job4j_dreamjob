<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <title>Работа мечты</title>
</head>
<body>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <a class="btn btn-light" style="float: left" href="<%=request.getContextPath()%>/candidates.do">&lt;&lt;</a>
                <c:if test="${candidate.id == 0}">
                <h2>Новый кандидат</h2>
                </c:if>
                <c:if test="${candidate.id != 0}">
                <h2>Редактирование кандидата</h2>
                </c:if>
            </div>
            <div class="card-body">
                <form action="<c:url value="/candidates.do"/>" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <div class="col-lg-12 col-md-12 col-sm-12" style="padding-bottom: 2em">
                            <img class="col-lg-9 col-md-12 col-sm-12" src="<c:url value="/image?id=${candidate.id}"/>" alt="Фото кандидата"/>
                            <ul class="nav" style="margin-top: 1em">
                                <li class="nav-item" style="margin-right: 10px"><a class="btn btn-outline-primary" href="<c:url value="/image?id=${candidate.id}&delete=1"/>">Удалить фото</a></li>
                                <li class="nav-item" style="margin-right: 10px"><a class="btn btn-outline-primary" href="<c:url value="/image?id=${candidate.id}&download=1"/>">Скачать фото</a></li>
                            </ul>
                        </div>
                        <label>Ф.И.О.</label>
                        <input name="nName" type="text" class="form-control" value="<c:out value="${candidate.name}"/>"/>
                        <label>Претендует на позицию</label>
                        <input name="nPosition" type="text" class="form-control" value="<c:out value="${candidate.position}"/>"/>
                        <label>Фото</label>
                        <input name="nPhoto" type="file"/>
                        <input name="nCandidateId" type="hidden" value="<c:out value="${candidate.id}"/>"/>
                    </div>
                    <input type="submit" class="btn btn-primary" style="float: right" value="Сохранить"/>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>