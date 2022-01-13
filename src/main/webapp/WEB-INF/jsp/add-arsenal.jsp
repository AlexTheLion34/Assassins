<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="container">
    <a class="btn btn-info" href="/profile" role="button">Назад</a>
    <br>
    <br/>
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
                                        required="required" step="1" min="0"
                                        oninvalid="this.setCustomValidity('Введите количество')"
                                        oninput="this.setCustomValidity('')"
                                        onkeyup="digitsOnly(this)"/>
                        </fieldset>
                        <fieldset class="form-group">
                            <form:label path="numOfKnives">Ножи</form:label>
                            <form:input path="numOfKnives" type="number" class="form-control"
                                        required="required" step="1" min="0"
                                        oninvalid="this.setCustomValidity('Введите количество')"
                                        oninput="this.setCustomValidity('')"
                                        onkeyup="digitsOnly(this)"/>
                        </fieldset>
                        <fieldset class="form-group">
                            <form:label path="numOfBows">Комплект луков со стрелами</form:label>
                            <form:input path="numOfBows" type="number" class="form-control"
                                        required="required" step="1" min="0"
                                        oninvalid="this.setCustomValidity('Введите количество')"
                                        oninput="this.setCustomValidity('')"
                                        onkeyup="digitsOnly(this)"/>
                        </fieldset>
                        <fieldset class="form-group">
                            <form:label path="numOfShields">Щиты</form:label>
                            <form:input path="numOfShields" type="number" class="form-control"
                                        required="required" step="1" min="0"
                                        oninvalid="this.setCustomValidity('Введите количество')"
                                        oninput="this.setCustomValidity('')"
                                        onkeyup="digitsOnly(this)"/>
                        </fieldset>
                        <button type="submit" class="btn btn-success">Добавить</button>
                    </form:form>
                    <script>
                        function digitsOnly(input) {
                            const regex = /[^1-9]/gi;
                            input.value = input.value.replace(regex, "");
                        }
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>