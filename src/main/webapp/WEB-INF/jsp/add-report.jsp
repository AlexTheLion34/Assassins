<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ page contentType="text/html;charset=utf-8" %>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 ">
            <div class="panel panel-primary">
                <div class="panel-heading">Отчет</div>
                <div class="panel-body">
                    <form:form method="post" action="/add-report" enctype="multipart/form-data">
                        <p><input name="file" id="fileToUpload" type="file" required="required"/></p>
                        <p><input type="submit" class="btn btn-primary btn-md" value="Загрузить отчет"></p>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>