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
            <td>${userInfo.balance}</td>
        </tr>
    </table>
    <c:if test="${user.getRole().name() eq 'CUSTOMER'}">
        <div>
            <a type="button" class="btn btn-primary btn-md" href="/add-request">Новый заказ</a>
        </div>
    </c:if>
    <br>
    <div class="panel panel-primary">
        <c:if test="${user.getRole().name() eq 'CUSTOMER'}">
            <div class="panel-heading">
                <h3>Заказы</h3>
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
                            <td>${request.requestInfo.type}</td>
                            <td>${request.requestInfo.aim}</td>
                            <td>${request.requestInfo.status}</td>
                            <td><a type="button" class="btn btn-success"
                                   href="/view-request?id=${request.id}">Просмотр</a>
                            </td>
<%--                            <c:if test="${request.requestInfo.status == 'Ожидает подтверждения'}">--%>
<%--                                <td><a type="button" class="btn btn-success"--%>
<%--                                       href="/view-report?id=${request.report.id}">Получить отчет</a>--%>
<%--                                </td>--%>
<%--                                <td><a type="button" class="btn btn-success"--%>
<%--                                       href="/payment?id=${request.id}">Подтвердить</a>--%>
<%--                                </td>--%>
<%--                            </c:if>--%>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${user.getRole().name() eq 'EXECUTOR'}">
            <div class="panel-heading">
                <h3>Текущий заказ</h3>
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
                    <c:forEach items="${tasks}" var="t">
                        <tr>
                            <td>${t.requestInfo.type}</td>
                            <td>${t.requestInfo.aim}</td>
                            <td>${t.requestInfo.status}</td>
                            <td><a type="button" class="btn btn-success"
                                   href="/view-request?id=${t.id}">Просмотр</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${user.getRole().name() eq 'MASTER_ASSASSIN'}">
            <div class="panel-heading">
                <h3>Заказы для обработки</h3>
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
                    <c:forEach items="${tasks}" var="t">
                        <tr>
                            <td>${t.requestInfo.type}</td>
                            <td>${t.requestInfo.aim}</td>
                            <td>${t.requestInfo.status}</td>
                            <td><a type="button" class="btn btn-warning"
                                   href="/change-request?id=${t.id}">Редактировать</a>
                            </td>
                            <td><a type="button" class="btn btn-success"
                                   href="/view-request?id=${t.id}">Подтвердить</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${user.getRole().name() eq 'GUNSMITH'}">
            <div class="panel-heading">
                <h3>Заказы для обработки</h3>
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
                    <c:forEach items="${tasks}" var="t">
                        <tr>
                            <td>${t.requestInfo.type}</td>
                            <td>${t.requestInfo.aim}</td>
                            <td>${t.requestInfo.status}</td>
                            <td><a type="button" class="btn btn-success"
                                   href="/view-request?id=${t.id}">Укомплектовать</a>
                            </td>
                            <td><a type="button" class="btn btn-success"
                                   href="/view-request?id=${t.id}">Подтвердить</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${user.getRole().name() eq 'CABMAN'}">
            <div class="panel-heading">
                <h3>Заказы для обработки</h3>
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
                    <c:forEach items="${tasks}" var="t">
                        <tr>
                            <td>${t.requestInfo.type}</td>
                            <td>${t.requestInfo.aim}</td>
                            <td>${t.requestInfo.status}</td>
                            <td><a type="button" class="btn btn-success"
                                   href="/view-request?id=${t.id}">Снарядить</a>
                            </td>
                            <td><a type="button" class="btn btn-success"
                                   href="/view-request?id=${t.id}">Подтвердить</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>