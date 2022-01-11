<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ page contentType="text/html;charset=utf-8" %>
<div class="container">
    <a class="btn btn-info" href="/profile" role="button">Назад</a>
    <br>
    <br/>
    <div class="row">
        <div class="col-md-6 col-md-offset-3 ">
            <div class="panel panel-primary">
                <div class="panel-heading">Арсенал</div>
                <div class="panel-body">
                    <form:form method="post" modelAttribute="request" action="/evaluate?requestId=${request.id}">
                        <form:hidden path="id" />
                        <fieldset class="form-group">
                            <form:label path="requestInfo.rating">Оценка выполнения заказа</form:label>
                            <form:input path="requestInfo.rating" type="number" class="form-control"
                                        required="required"
                                        step="1"
                                        min="1"
                                        max="5"
                                        oninvalid="this.setCustomValidity('Выставьте оценку')"
                                        oninput="this.setCustomValidity('')"
                                        onkeyup="digitsOnly(this)"/>
                        </fieldset>
                        <button type="submit" class="btn btn-success">Оценить</button>
                    </form:form>
                    <script>
                        function digitsOnly(input) {
                            const regex = /[^1-5]/gi;
                            input.value = input.value.replace(regex, "");
                        }
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>