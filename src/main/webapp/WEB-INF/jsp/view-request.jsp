<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<%@ page contentType="text/html;charset=utf-8" %>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 ">
            <div class="panel panel-primary">
                <div class="panel-heading">Заказ</div>
                <table>
                    <tr>
                        <td width="40%">Тип :</td>
                        <td width="40%">${request.type}</td>
                    </tr>
                    <tr>
                        <td width="40%">Цель :</td>
                        <td width="40%">${request.aim}</td>
                    </tr>
                    <tr>
                        <td width="40%">Цена :</td>
                        <td width="40%">${request.price}</td>
                    </tr>
                    <tr>
                        <td width="40%">Описание :</td>
                        <td width="40%">${request.description}</td>
                    </tr>
                    <tr>
                        <td width="40%">Возможная долгота :</td>
                        <td width="40%">${request.possibleLongitude}</td>
                    </tr>
                    <tr>
                        <td width="40%">Возможная ширина :</td>
                        <td width="40%">${request.possibleLatitude}</td>
                    </tr>
                </table>
                <iframe src="https://maps.google.com/maps?q=${request.possibleLongitude},${request.possibleLatitude}&hl=es;z=14&amp;output=embed"
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