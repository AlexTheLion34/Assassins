<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3>Профиль</h3>
        </div>
        <div class="panel-body">
            <div class="container">
                <label id="user_name">
                    Имя пользователя: ${user.username}
                </label>
                <br>
                <c:if test="${user.getRole().name() eq 'CUSTOMER'
        or user.getRole().name() eq 'EXECUTOR'}">
                    <label id="user_balance">
                        Баланс: ${user.balance}
                    </label>
                </c:if>
                <br>
                <c:if test="${user.getRole().name() eq 'EXECUTOR'}">
                    <label id="user_rating">
                        Рейтинг: ${user.rating}
                    </label>
                </c:if>
            </div>
            <div class="panel panel-default">
                <c:if test="${user.getRole().name() eq 'CUSTOMER'}">
                    <div class="panel-heading">
                        <h3>Заказы</h3>
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
                                <th scope="col">Тип</th>
                                <th scope="col">Цель</th>
                                <th scope="col">Статус</th>
                                <th scope="col">Действия</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requests}" var="request">
                                <tr>
                                    <td>${request.requestInfo.type}</td>
                                    <td>${request.requestInfo.aim}</td>
                                    <td>${request.requestInfo.status.getLabel()}</td>
                                    <c:if test="${request.requestInfo.status != 'CONFIRMING' &&
                            request.requestInfo.status != 'EVALUATING'}">
                                        <td >
                                            <a type="button" class="btn btn-primary btn-md"
                                               href="/view-request?id=${request.id}">Просмотреть</a>
                                        </td>
                                    </c:if>
                                    <c:if test="${request.requestInfo.status eq 'CONFIRMING'}">
                                        <td>
                                            <a type="button" class="btn btn-primary btn-md"
                                               href="/view-request?id=${request.id}">Просмотреть</a>
                                            <a type="button" class="btn btn-warning"
                                               href="/view-report?id=${request.report.id}">Получить отчет</a>
                                            <a type="button" class="btn btn-success"
                                               href="/payment?id=${request.id}">Оплатить</a>
                                        </td>
                                    </c:if>
                                    <c:if test="${request.requestInfo.status eq 'EVALUATING'}">
                                        <td>
                                            <a type="button" class="btn btn-primary btn-md"
                                               href="/view-request?id=${request.id}">Просмотреть</a>
                                            <a type="button" class="btn btn-primary btn-md"
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
                        <table class="table">
                            <colgroup>
                                <col>
                                <col>
                                <col>
                                <col style="width: 33%;">
                            </colgroup>
                            <thead class="thead-light">
                            <tr>
                                <th scope="col">Тип</th>
                                <th scope="col">Цель</th>
                                <th scope="col">Статус</th>
                                <th scope="col">Действия</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${tasks}" var="t">
                                <c:if test="${t.requestInfo.status eq 'EXECUTING'
                        or t.requestInfo.status eq 'CONFIRMING'
                        or t.requestInfo.status eq 'EVALUATING'
                        or t.requestInfo.status eq 'PAYMENT_CONFIRMING'}">
                                    <tr>
                                        <td>${t.requestInfo.type}</td>
                                        <td>${t.requestInfo.aim}</td>
                                        <td>${t.requestInfo.status.getLabel()}</td>
                                        <c:if test="${t.requestInfo.status != 'PAYMENT_CONFIRMING'}">
                                            <td>
                                                <a type="button" class="btn btn-primary btn-md"
                                                   href="/view-request?id=${t.id}">Просмотреть</a>
                                            </td>
                                        </c:if>
                                        <c:if test="${t.requestInfo.status eq 'PAYMENT_CONFIRMING'}">
                                            <td>
                                                <form:form method="post" action="/payment-confirm?id=${t.id}">
                                                    <button type="submit" class="btn btn-primary button">Подтвердить
                                                    </button>
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
                        <table class="table">
                            <colgroup>
                                <col>
                                <col>
                                <col>
                                <col style="width: 33%;">
                            </colgroup>
                            <thead class="thead-light">
                            <tr>
                                <th scope="col">Тип</th>
                                <th scope="col">Цель</th>
                                <th scope="col">Статус</th>
                                <th scope="col">Действия</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${tasks}" var="t">
                                <c:if test="${t.requestInfo.status eq 'MODERATING'}">
                                    <tr>
                                        <td>${t.requestInfo.type}</td>
                                        <td>${t.requestInfo.aim}</td>
                                        <td>${t.requestInfo.status.getLabel()}</td>
                                        <td>
                                            <a type="button" class="btn btn-primary btn-md"
                                               href="/view-request?id=${t.id}">Просмотр</a>
                                            <a type="button" class="btn btn-warning"
                                               href="/change-request?id=${t.id}">Редактировать</a>
                                            <a type="button" class="btn btn-success"
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
                        <table class="table">
                            <colgroup>
                                <col>
                                <col>
                                <col>
                                <col style="width: 33%;">
                            </colgroup>
                            <thead class="thead-light">
                            <tr>
                                <th scope="col">Тип</th>
                                <th scope="col">Цель</th>
                                <th scope="col">Статус</th>
                                <th scope="col">Действия</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${tasks}" var="t">
                                <c:if test="${t.requestInfo.status eq 'PACKING_1'}">
                                    <tr>
                                        <td>${t.requestInfo.type}</td>
                                        <td>${t.requestInfo.aim}</td>
                                        <td>${t.requestInfo.status.getLabel()}</td>
                                        <td>
                                            <a type="button" class="btn btn-primary btn-md"
                                               href="/view-request?id=${t.id}">Просмотр</a>
                                            <a type="button" class="btn btn-success"
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
                        <table class="table">
                            <colgroup>
                                <col>
                                <col>
                                <col>
                                <col style="width: 33%;">
                            </colgroup>
                            <thead class="thead-light">
                            <tr>
                                <th scope="col">Тип</th>
                                <th scope="col">Цель</th>
                                <th scope="col">Статус</th>
                                <th scope="col">Действия</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${tasks}" var="t">
                                <c:if test="${t.requestInfo.status eq 'PACKING_2'}">
                                    <tr>
                                        <td>${t.requestInfo.type}</td>
                                        <td>${t.requestInfo.aim}</td>
                                        <td>${t.requestInfo.status.getLabel()}</td>
                                        <td>
                                            <a type="button" class="btn btn-primary btn-md"
                                               href="/view-request?id=${t.id}">Просмотр</a>
                                            <a type="button" class="btn btn-success"
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
        <div class="panel-footer">
            <c:if test="${user.getRole().name() eq 'CUSTOMER'}">
                <div>
                    <a type="button"
                       id="add_request"
                       class="btn btn-primary btn-md"
                       href="/add-request">Новый заказ</a>
                </div>
            </c:if>
        </div>
    </div>
</div>