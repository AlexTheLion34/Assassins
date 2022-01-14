<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container">
    <div class="alert-danger">
        <table id="error-info">
            <c:if test="${pageContext.response.status eq 403}">
                <tr>
                    <td>Сообщение:</td>
                    <td>Нет прав доступа</td>
                </tr>
            </c:if>
            <c:if test="${pageContext.response.status != 403}">
                <tr>
                    <td>Сообщение:</td>
                    <td>Неизвестная ошибка</td>
                </tr>
            </c:if>
        </table>
    </div>
</div>