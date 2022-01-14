<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container">
    <a class="btn btn-info" href="/profile" role="button">Назад</a>
    <br>
    <br/>
    <div class="row">
        <div class="col-md-6 col-md-offset-3 ">
            <div class="panel panel-primary">
                <div class="panel-heading">Заказ</div>
                <table class="table">
                    <tr>
                        <td>Тип:</td>
                        <td>${request.requestInfo.type}</td>
                    </tr>
                    <tr>
                        <td>Цель:</td>
                        <td>${request.requestInfo.aim}</td>
                    </tr>
                    <tr>
                        <td>Цена:</td>
                        <td>${request.requestInfo.price}</td>
                    </tr>
                    <tr>
                        <td>Возможная долгота:</td>
                        <td>${request.requestInfo.possibleLongitude}</td>
                    </tr>
                    <tr>
                        <td>Возможная широта:</td>
                        <td>${request.requestInfo.possibleLatitude}</td>
                    </tr>
                    <c:if test="${request.arsenal != null and
                    user.getRole().name() eq 'EXECUTOR'}">
                        <tr>
                            <td>Мечи:</td>
                            <td>${request.arsenal.numOfSwords}</td>
                        </tr>
                        <tr>
                            <td>Комлект луков и стрел:</td>
                            <td>${request.arsenal.numOfBows}</td>
                        </tr>
                        <tr>
                            <td>Ножи:</td>
                            <td>${request.arsenal.numOfKnives}</td>
                        </tr>
                        <tr>
                            <td>Шиты:</td>
                            <td>${request.arsenal.numOfShields}</td>
                        </tr>
                    </c:if>
                    <c:if test="${request.roadEquipment != null and user.getRole().name() eq 'EXECUTOR'}">
                        <tr>
                            <td>Повозка :</td>
                            <c:if test="${request.roadEquipment.carriageRequired eq false}">
                                <td>Не требуется</td>
                            </c:if>
                            <c:if test="${request.roadEquipment.carriageRequired eq true}">
                                <td>Подготовлена</td>
                            </c:if>
                        </tr>
                        <tr>
                            <td>Комлект луков и стрел :</td>
                            <td>${request.roadEquipment.numOfHorses}</td>
                        </tr>
                    </c:if>
                    <tr>
                        <td>Описание:</td>
                        <td>
                            <textarea
                                      readonly
                                      style="text-align:left;overflow:auto;min-width: 100%">${request.requestInfo.description}</textarea>
                        </td>
                    </tr>
                </table>
                <iframe src="https://maps.google.com/maps?q=${request.requestInfo.possibleLongitude},
                ${request.requestInfo.possibleLatitude}&hl=es;z=14&amp;output=embed"
                        width="550" height="450" style="display:block;width:100%;" loading="lazy"></iframe>
                <c:if test="${user.getRole().name() eq 'EXECUTOR'
                and request.requestInfo.status.name()
                eq 'EXECUTING'}">
                    <div>
                        <div class="panel-footer">
                            <a type="button" class="btn btn-primary btn-md" href="/add-report">Добавить отчет</a>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>