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
                <div class="panel-heading">Снаряжение для дороги</div>
                <div class="panel-body">
                    <form:form method="post" modelAttribute="requestRoadEq" action="/add-road-eq?requestId=${requestId}">
                        <form:hidden path="id" />
                        <fieldset class="form-group">
                            <form:label path="carriageRequired">Повозка</form:label>
                            <br>
                            <form:select path="carriageRequired">
                                <form:option value="true">Требуется</form:option>
                                <form:option value="false">Не требуется</form:option>
                            </form:select>
                        </fieldset>
                        <fieldset class="form-group">
                            <form:label path="numOfHorses">Лошади</form:label>
                            <form:input path="numOfHorses" type="number" class="form-control"
                                        required="required" step="1" min="0"
                                        oninvalid="this.setCustomValidity('Введите количество')"
                                        oninput="this.setCustomValidity('')"
                                        onkeyup="digitsOnly(this)"
                            />
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