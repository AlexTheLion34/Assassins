<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<%@ page contentType="text/html;charset=utf-8" %>

<div class="container">
    <table>
        <tr>
            <td width="40%">Имя пользователя :</td>
            <td width="40%">${user.username}</td>
        </tr>
    </table>
    <br>
    <div class="panel panel-primary">
        <c:if test="${user.getRole().name() eq 'MASTER_ASSASSIN'}">
            <div class="panel-heading">
                <h3>${id}</h3>
            </div>
            <div class="panel-body">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th width="40%">Имя</th>
                        <th width="40%">Рейтинг</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${team}" var="r">

                        <tr>
                            <td>${r.username}</td>
                            <c:if test="${r.role eq 'EXECUTOR'}">
                                <td>${r.userInfo.rating}</td>
                            </c:if>
                            <td>
                                <form:form method="post" action="/change-team?requestId=${id}&role=${r.role}">
                                    <a type="submit" class="btn btn-primary">Выбрать</a>
                                </form:form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>