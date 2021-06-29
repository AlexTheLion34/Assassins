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
        <tr>
            <td>Счет :</td>
            <td>${user.balance}</td>
        </tr>
    </table>
    <security:authorize access="hasRole('ROLE_CUSTOMER')">
        <div>
            <a type="button" class="btn btn-primary btn-md" href="/add-request">Новый заказ</a>
        </div>
    </security:authorize>
    <br>
    <div class="panel panel-primary">
        <security:authorize access="hasRole('ROLE_CUSTOMER')">
            <div class="panel-heading">
                <h3>Ваши закзы</h3>
            </div>
            <div class="panel-body">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th width="40%">Тип</th>
                        <th width="40%">Цель</th>
                        <th width="40%">Статус</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requests}" var="request">
                        <tr>
                            <td>${request.type}</td>
                            <td>${request.aim}</td>
                            <td>${request.status}</td>
                            <td><a type="button" class="btn btn-success"
                                   href="/view-request?id=${request.id}">Просмотр</a>
                            </td>
                            <c:if test="${request.status == 'Ожидает подтверждения'}">
                                <td><a type="button" class="btn btn-success"
                                       href="/view-report?id=${request.report.id}">Получить отчет</a>
                                </td>
                                <td><a type="button" class="btn btn-success"
                                       href="/payment?id=${request.id}">Подтвердить</a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </security:authorize>
        <security:authorize access="hasRole('ROLE_EXECUTOR')">
            <div class="panel-heading">
                <h3>Current task</h3>
            </div>
            <div class="panel-body">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th width="40%">Тип</th>
                        <th width="40%">Цель</th>
                        <th width="40%">Статус</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${task}" var="t">
                        <tr>
                            <td>${t.type}</td>
                            <td>${t.aim}</td>
                            <td>${t.status}</td>
                            <td><a type="button" class="btn btn-success"
                                   href="/view-request?id=${t.id}">Просмотр</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </security:authorize>
    </div>
</div>