<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<%@ page contentType="text/html;charset=utf-8" %>

<div class="container">
    <a class="btn btn-info" href="/profile" role="button">Назад</a>
    <br>
    <br/>
    <div class="row">
        <div class="col-md-6 col-md-offset-3 ">
            <div class="panel panel-primary">
                <div class="panel-heading">Заказ</div>
                <table>
                    <tr>
                        <td width="40%">Тип :</td>
                        <td width="40%">${request.requestInfo.type}</td>
                    </tr>
                    <tr>
                        <td width="40%">Цель :</td>
                        <td width="40%">${request.requestInfo.aim}</td>
                    </tr>
                    <tr>
                        <td width="40%">Цена :</td>
                        <td width="40%">${request.requestInfo.price}</td>
                    </tr>
                    <tr>
                        <td width="40%">Описание :</td>
                        <td width="40%">${request.requestInfo.description}</td>
                    </tr>
                    <tr>
                        <td width="40%">Возможная долгота :</td>
                        <td width="40%">${request.requestInfo.possibleLongitude}</td>
                    </tr>
                    <tr>
                        <td width="40%">Возможная ширина :</td>
                        <td width="40%">${request.requestInfo.possibleLatitude}</td>
                    </tr>
                    <c:if test="${request.arsenal != null and user.getRole().name() eq 'EXECUTOR'}">
                        <tr>
                            <td width="40%">Мечи :</td>
                            <td width="40%">${request.arsenal.numOfSwords}</td>
                        </tr>
                        <tr>
                            <td width="40%">Комлект луков и стрел :</td>
                            <td width="40%">${request.arsenal.numOfBows}</td>
                        </tr>
                        <tr>
                            <td width="40%">Ножи :</td>
                            <td width="40%">${request.arsenal.numOfKnives}</td>
                        </tr>
                        <tr>
                            <td width="40%">Шиты :</td>
                            <td width="40%">${request.arsenal.numOfShields}</td>
                        </tr>
                    </c:if>
                    <c:if test="${request.roadEquipment != null and user.getRole().name() eq 'EXECUTOR'}">
                        <tr>
                            <td width="40%">Повозка :</td>
                            <c:if test="${request.roadEquipment.carriageRequired eq false}">
                            <td width="40%">Не требуется</td>
                            </c:if>
                            <c:if test="${request.roadEquipment.carriageRequired eq true}">
                                <td width="40%">Подготовлена</td>
                            </c:if>
                        </tr>
                        <tr>
                            <td width="40%">Комлект луков и стрел :</td>
                            <td width="40%">${request.roadEquipment.numOfHorses}</td>
                        </tr>
                    </c:if>
                </table>
                <iframe src="https://maps.google.com/maps?q=${request.requestInfo.possibleLongitude},
                ${request.requestInfo.possibleLatitude}&hl=es;z=14&amp;output=embed"
                        width="550" height="450" style="border:0;" allowfullscreen="" loading="lazy"></iframe>
                <c:if test="${user.getRole().name() eq 'EXECUTOR'}">
                    <div>
                        <a type="button" class="btn btn-primary btn-md" href="/add-report">Добавить отчет</a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>