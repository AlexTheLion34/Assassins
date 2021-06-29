<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 ">
            <div class="panel panel-primary">
                <div class="panel-heading">Report</div>
                <div class="panel-body">
                    <form:form method="post" action="/add-report" enctype="multipart/form-data">
                        <p><input name="file" id="fileToUpload" type="file" /></p>
                        <p><input type="submit" value="Upload  report"></p>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="common/footer.jspf"%>