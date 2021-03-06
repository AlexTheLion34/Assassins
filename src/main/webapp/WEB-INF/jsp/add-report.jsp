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
                <div class="panel-heading">Отчет</div>
                <div class="panel-body">
                    <form:form method="post" action="/add-report" enctype="multipart/form-data">
                        <p><input name="file" id="fileToUpload" type="file" required="required"
                                  accept=".pdf"
                                  oninvalid="this.setCustomValidity('Прикрепите файл')"
                                  oninput="this.setCustomValidity('')"/></p>
                        <p><input type="submit" class="btn btn-primary btn-md" value="Загрузить отчет"></p>
                    </form:form>
                    <script>
                        var file = document.getElementById('fileToUpload');
                        file.onchange = function(e) {
                            var ext = this.value.match(/\.([^.]+)$/)[1];
                            switch (ext) {
                                case 'pdf':
                                    break;
                                default:
                                    alert('Формат файла для загрузки: .pdf');
                                    this.value = '';
                            }
                        };
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>