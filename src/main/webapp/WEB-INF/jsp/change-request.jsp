<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container">
    <a class="btn btn-info" href="/profile" role="button">Назад</a>
    <br>
    <br/>
    <table>
        <tr>
            <td>Имя пользователя :</td>
            <td>${user.username}</td>
        </tr>
    </table>
    <br>
    <div class="panel panel-primary">
        <c:if test="${user.getRole().name() eq 'MASTER_ASSASSIN'}">
            <div class="panel-heading">
                <h3>Команда</h3>
            </div>
            <div class="panel-body">
                <table class="table">
                    <colgroup>
                        <col>
                        <col>
                        <col>
                        <col style="width: 33%;">
                    </colgroup>
                    <thead class="thead-light">
                    <tr>
                        <th scope="col">Имя</th>
                        <th scope="col">Роль</th>
                        <th scope="col">Рейтинг</th>
                        <th scope="col">Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${executor.username}</td>
                        <td>${executor.role.getLabel()}</td>
                        <td>${executor.rating}</td>
                        <td>
                            <a type="button" class="btn btn-danger"
                               href="/change-team?requestId=${id}&role=${executor.role}">Изменить</a>
                        </td>
                    </tr>
                    <tr>
                        <td>${gunsmith.username}</td>
                        <td>${gunsmith.role.getLabel()}</td>
                        <td></td>
                        <td>
                            <a type="button" class="btn btn-danger"
                               href="/change-team?requestId=${id}&role=${gunsmith.role}">Изменить</a>
                        </td>
                    </tr>
                    <tr>
                        <td>${cabman.username}</td>
                        <td>${cabman.role.getLabel()}</td>
                        <td></td>
                        <td>
                            <a type="button" class="btn btn-danger"
                               href="/change-team?requestId=${id}&role=${cabman.role}">Изменить</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>