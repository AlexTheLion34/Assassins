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
                    <tr>
                        <td>${executor.username}</td>
                        <td>${executor.role}</td>
                        <td>${executor.rating}</td>
                        <td><a type="button" class="btn btn-danger"
                               href="/change-team?requestId=${id}&role=${executor.role}">Изменить</a>
                        </td>
                    </tr>
                    <tr>
                        <td>${gunsmith.username}</td>
                        <td>${gunsmith.role}</td>
                        <td></td>
                        <td><a type="button" class="btn btn-danger"
                               href="/change-team?requestId=${id}&role=${gunsmith.role}">Изменить</a>
                        </td>
                    </tr>
                    <tr>
                        <td>${cabman.username}</td>
                        <td>${cabman.role}</td>
                        <td></td>
                        <td><a type="button" class="btn btn-danger"
                               href="/change-team?requestId=${id}&role=${cabman.role}">Изменить</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>