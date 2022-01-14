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
                <div class="panel-heading">Новый заказ</div>
                <div class="panel-body">
                    <form:form method="post" modelAttribute="requestInfo">
                        <form:hidden path="id"/>
                        <fieldset class="form-group">
                            <form:label path="type">Тип</form:label>
                            <br>
                            <form:select path="type">
                                <form:option value="Артефакт">Артефакт</form:option>
                                <form:option value="Заказное убийство">Заказное убийство</form:option>
                            </form:select>
                        </fieldset>
                        <fieldset class="form-group">
                            <form:label path="aim">Цель</form:label>
                            <form:input path="aim" type="text" class="form-control"
                                        required="required"
                                        oninvalid="this.setCustomValidity('Введите цель')"
                                        oninput="this.setCustomValidity('')"
                                        onkeyup="lettersOnly(this)"
                                        maxlength="20"/>
                        </fieldset>
                        <fieldset class="form-group">
                            <form:label path="price">Цена</form:label>
                            <form:input path="price" type="number" class="form-control"
                                        required="required" min="1" max="${maxPrice}"
                                        oninvalid="this.setCustomValidity('Введите число не менее 1 и не более ${maxPrice}')"
                                        oninput="this.value = Math.round(this.value);
										this.setCustomValidity('')" step="1"/>
                        </fieldset>
                        <fieldset class="form-group">
                            <form:label path="possibleLongitude">Возможная долгота</form:label>
                            <form:input path="possibleLongitude" type="number" class="form-control"
                                        required="required" step="0.1"
                                        min="-180"
                                        max="180"
                                        oninvalid="this.setCustomValidity('Введите число не менее -180 и не более 180 (допускается 1 знак после запятой)')"
                                        oninput="this.setCustomValidity('')"/>
                        </fieldset class="form-group" no>
                        <fieldset class="form-group">
                            <form:label path="possibleLatitude">Возможная широта</form:label>
                            <form:input path="possibleLatitude" type="number" class="form-control"
                                        required="required"
                                        min="-90"
                                        max="90"
                                        step="0.1"
                                        oninvalid="this.setCustomValidity('Введите число не менее -90 и не более 90 (допускается 1 знак после запятой)')"
                                        oninput="this.setCustomValidity('')"/>
                        </fieldset>
                        <fieldset class="form-group">
                            <form:label path="description">Описание</form:label>
                            <form:textarea path="description"
                                        type="textarea"
                                        class="form-control"
                                        required="required" onkeyup="lettersOnly(this)" maxlength="250"
                                        oninvalid="this.setCustomValidity('Введите описание')"
                                        oninput="this.setCustomValidity('')"/>
                        </fieldset>
                        <button type="submit" class="btn btn-success">Создать</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <script>
        function lettersOnly(input) {
            const regex = /[^?!,.а-яА-ЯёЁa-zA-Z0-9\s]+$/gi;
            input.value = input.value.replace(regex, "");
        }

        function digitsOnly(input) {
            const regex = /[^1-9][^0-9]*$/gi;
            input.value = input.value.replace(regex, "");
        }
    </script>
</div>