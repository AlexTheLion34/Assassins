<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container">
    <div class="alert-danger">
        <table id="error-info">
            <tr>
                <td>Код ошибки:</td>
                <td>${pageContext.response.status}</td>
            </tr>
            <tr>
                <td>Сообщение:</td>
                <td>Размер файла превышает 3мб</td>
            </tr>
        </table>
    </div>
</div>