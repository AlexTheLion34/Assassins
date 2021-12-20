<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <div style="background: #eee">
        <nav class="bg-dark navbar-dark container navbar navbar-expand-sm  flex-column flex-sm-row">
            <a class="navbar-brand" href="/">Assassin's Creed</a>
        </nav>
    </div>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css">
    <meta charset="utf-8">
    <title>Login</title>

    <style>
        .col-centered {
            float: none;
            margin: 0 auto;
        }
    </style>

</head>
<body>
<div class="container">
    <div class="col-xs-12 col-sm-8 col-md-4 col-lg-4 col-centered">
        <div class="jumbotron">
            <h4>Вход</h4>
            <form name="loginForm" action="login" method='POST'>
                <c:if test="${param.error != null}">
                    <div class="alert alert-danger" role="alert">
                        Неверные имя пользователя и пароль.
                    </div>
                </c:if>
                <c:if test="${param.logout != null}">
                    <div class="alert alert-info" role="alert">
                        Вы вышли из системы.
                    </div>
                </c:if>
                <div class="form-group">
                    <input type="text" name='username' class="form-control" placeholder="Имя пользователя">
                </div>
                <div class="form-group">
                    <input type="password" name='password' class="form-control" placeholder="Пароль">
                </div>
                <input type="submit" class="btn btn-primary btn-md" value="Войти"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>