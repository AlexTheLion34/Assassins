<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container">
    <a class="btn btn-info" href="/change-request?id=${id}" role="button">Назад</a>
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
                <h3>${title}</h3>
            </div>
            <div class="panel-body">
                <table class="table">
                    <c:if test="${title eq 'Ассассины'}">
                    <colgroup>
                        <col>
                        <col>
                        <col style="width: 33%;">
                    </colgroup>
                    <thead class="thead-light">
                    <tr>
                        <th scope="col">Имя</th>
                        <th scope="col">Рейтинг</th>
                        <th scope="col">Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${team}" var="r">

                        <tr>
                            <td>${r.username}</td>
                            <td>${r.rating}</td>
                            <td>
                                <form:form method="post"
                                           action="/change-team?requestId=${id}&userToChangeId=${r.getId()}">
                                    <button type="submit" class="btn btn-primary button">Выбрать</button>
                                </form:form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    </c:if>
                    <c:if test="${title != 'Ассассины'}">
                        <colgroup>
                            <col>
                            <col style="width: 33%;">
                        </colgroup>
                        <thead class="thead-light">
                        <tr>
                            <th scope="col">Имя</th>
                            <th scope="col">Действия</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${team}" var="r">

                            <tr>
                                <td>${r.username}</td>
                                <td>
                                    <form:form method="post"
                                               action="/change-team?requestId=${id}&userToChangeId=${r.getId()}">
                                        <button type="submit" class="btn btn-primary button">Выбрать</button>
                                    </form:form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </c:if>
                </table>
            </div>
        </c:if>
    </div>
</div>