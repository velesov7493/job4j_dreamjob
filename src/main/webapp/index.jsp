<%@ page contentType="text/html; charset=UTF-8"%>
<%
    if (request.getAttribute("isServlet") == null) {
        response.sendRedirect(request.getContextPath() + "/index.do");
    }
%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <title>Работа мечты!</title>
</head>
<body>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header"><h2>Разделы</h2></div>
            <div class="card-body">
                <ul class="nav">
                    <li class="nav-item" style="margin-left: 10px">
                        <a class="nav-link btn btn-outline-primary" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
                    </li>
                    <li class="nav-item" style="margin-left: 10px">
                        <a class="nav-link btn btn-outline-primary" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
                    </li>
                    <li class="nav-item" style="margin-left: 10px">
                        <a class="nav-link btn btn-outline-primary" href="<%=request.getContextPath()%>/posts.do?id=0">Добавить вакансию</a>
                    </li>
                    <li class="nav-item" style="margin-left: 10px">
                        <a class="nav-link btn btn-outline-primary" href="<%=request.getContextPath()%>/candidates.do?id=0">Добавить кандидата</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="row pt-3">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <h4>Сегодняшние вакансии</h4>
            </div>
            <div class="card-body">
            </div>
        </div>
    </div>
    <div class="row pt-3">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <h4>Сегодняшние кандидаты</h4>
            </div>
            <div class="card-body">
            </div>
        </div>
    </div>
</div>
</body>
</html>