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
                <h3>Команда</h3>
            </div>
            <div class="panel-body">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th width="40%">Имя</th>
                        <th width="40%">Роль</th>
                        <th width="40%">Рейтинг</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestTeam}" var="r">
                        <tr>
                            <td>${r.username}</td>
                            <td>${r.role}</td>
                            <td>${r.userInfo.rating}</td>
                            <td><a type="button" class="btn btn-danger"
                                   href="/change-team?requestId=${id}&role=${r.role}">Изменить</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>