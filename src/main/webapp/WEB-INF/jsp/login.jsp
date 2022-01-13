<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
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
<style>
    .col-centered {
        float: none;
        margin: 0 auto;
    }
</style>
</body>
</html>