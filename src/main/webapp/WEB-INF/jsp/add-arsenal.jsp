<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ page contentType="text/html;charset=utf-8" %>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 ">
            <div class="panel panel-primary">
                <div class="panel-heading">Арсенал</div>
                <div class="panel-body">
                    <form:form method="post" modelAttribute="requestArsenal" action="/add-arsenal?requestId=${requestId}">
                        <form:hidden path="id" />
                        <fieldset class="form-group">
                            <form:label path="numOfSwords">Мечи</form:label>
                            <form:input path="numOfSwords" type="number" class="form-control"
                                        required="required" step="1" min="0"/>
                            <form:errors path="numOfSwords" cssClass="text-warning"/>
                        </fieldset>
                        <fieldset class="form-group">
                            <form:label path="numOfKnives">Ножи</form:label>
                            <form:input path="numOfKnives" type="number" class="form-control"
                                        required="required" step="1" min="0"/>
                            <form:errors path="numOfKnives" cssClass="text-warning"/>
                        </fieldset>
                        <fieldset class="form-group">
                            <form:label path="numOfBows">Комплект луков со стрелами</form:label>
                            <form:input path="numOfBows" type="number" class="form-control"
                                        required="required" step="1" min="0"/>
                            <form:errors path="numOfBows" cssClass="text-warning"/>
                        </fieldset>
                        <fieldset class="form-group">
                            <form:label path="numOfShields">Щиты</form:label>
                            <form:input path="numOfShields" type="number" class="form-control"
                                        required="required" step="1" min="0"/>
                            <form:errors path="numOfShields" cssClass="text-warning"/>
                        </fieldset>
                        <button type="submit" class="btn btn-success">Добавить</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>