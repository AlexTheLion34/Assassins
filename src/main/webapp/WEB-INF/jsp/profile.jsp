<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container">
    <table id="user_info">
        <tr>
            <td>Имя пользователя: </td>
            <td>${user.username}</td>
        </tr>
        <c:if test="${user.getRole().name() eq 'CUSTOMER' or user.getRole().name() eq 'EXECUTOR'}">
            <tr>
                <td>Счет: </td>
                <td>${user.balance}</td>
            </tr>
        </c:if>
        <c:if test="${user.getRole().name() eq 'EXECUTOR'}">
            <tr>
                <td>Рейтинг :</td>
                <td>${user.rating}</td>
            </tr>
        </c:if>
    </table>
    <c:if test="${user.getRole().name() eq 'CUSTOMER'}">
        <div>
            <a type="button" id="add_request" class="btn btn-primary btn-md" href="/add-request">Новый заказ</a>
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
                            <td>${request.requestInfo.status.getLabel()}</td>
                            <td><a type="button" class="btn btn-primary btn-md"
                                   href="/view-request?id=${request.id}">Просмотр</a>
                            </td>
                            <c:if test="${request.requestInfo.status eq 'CONFIRMING'}">
                                <td><a type="button" class="btn btn-success"
                                       href="/view-report?id=${request.report.id}">Получить отчет</a>
                                </td>
                                <td><a type="button" class="btn btn-success"
                                       href="/payment?id=${request.id}">Оплатить</a>
                                </td>
                            </c:if>
                            <c:if test="${request.requestInfo.status eq 'EVALUATING'}">
                                <td><a type="button" class="btn btn-primary btn-md"
                                       href="/evaluate?id=${request.id}">Оценить</a>
                                </td>
                            </c:if>
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
                        <c:if test="${t.requestInfo.status eq 'EXECUTING' or t.requestInfo.status eq 'CONFIRMING' or
                        t.requestInfo.status eq 'EVALUATING' or t.requestInfo.status eq 'PAYMENT_CONFIRMING'}">
                            <tr>
                                <td>${t.requestInfo.type}</td>
                                <td>${t.requestInfo.aim}</td>
                                <td>${t.requestInfo.status.getLabel()}</td>
                                <td><a type="button" class="btn btn-primary btn-md"
                                       href="/view-request?id=${t.id}">Просмотр</a>
                                </td>
                                <c:if test="${t.requestInfo.status eq 'PAYMENT_CONFIRMING'}">
                                    <td>
                                        <form:form method="post" action="/payment-confirm?id=${t.id}">
                                            <button type="submit" class="btn btn-primary button">Подтвердить</button>
                                        </form:form>
                                    </td>
                                </c:if>
                            </tr>
                        </c:if>
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
                        <c:if test="${t.requestInfo.status eq 'MODERATING'}">
                            <tr>
                                <td>${t.requestInfo.type}</td>
                                <td>${t.requestInfo.aim}</td>
                                <td>${t.requestInfo.status.getLabel()}</td>
                                <td><a type="button" class="btn btn-primary btn-md"
                                       href="/view-request?id=${t.id}">Просмотр</a>
                                </td>
                                <td><a type="button" class="btn btn-warning"
                                       href="/change-request?id=${t.id}">Редактировать</a>
                                </td>
                                <td><a type="button" class="btn btn-success"
                                       href="/confirm-request?requestId=${t.id}">Подтвердить</a>
                                </td>
                            </tr>
                        </c:if>
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
                        <c:if test="${t.requestInfo.status eq 'PACKING_1'}">
                            <tr>
                                <td>${t.requestInfo.type}</td>
                                <td>${t.requestInfo.aim}</td>
                                <td>${t.requestInfo.status.getLabel()}</td>
                                <td><a type="button" class="btn btn-primary btn-md"
                                       href="/view-request?id=${t.id}">Просмотр</a>
                                </td>
                                <td><a type="button" class="btn btn-success"
                                       href="/add-arsenal?id=${t.id}">Укомплектовать</a>
                                </td>
                            </tr>
                        </c:if>
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
                        <c:if test="${t.requestInfo.status eq 'PACKING_2'}">
                            <tr>
                                <td>${t.requestInfo.type}</td>
                                <td>${t.requestInfo.aim}</td>
                                <td>${t.requestInfo.status.getLabel()}</td>
                                <td><a type="button" class="btn btn-primary btn-md"
                                       href="/view-request?id=${t.id}">Просмотр</a>
                                </td>
                                <td><a type="button" class="btn btn-success"
                                       href="/add-road-eq?id=${t.id}">Снарядить</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>