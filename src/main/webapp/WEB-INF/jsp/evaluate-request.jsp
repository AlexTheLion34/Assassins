<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ page contentType="text/html;charset=utf-8" %>
<div class="container">
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
                                        required="requestInfo.rating" step="1" min="1" max="5"/>
                            <form:errors path="requestInfo.rating" cssClass="text-warning"/>
                        </fieldset>
                        <button type="submit" class="btn btn-success">Оценить</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>