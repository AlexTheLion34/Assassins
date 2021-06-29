<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 ">
            <div class="panel panel-primary">
                <div class="panel-heading">Request</div>
                <table>
                    <tr>
                        <td width="40%">Type :</td>
                        <td width="40%">${request.type}</td>
                    </tr>
                    <tr>
                        <td width="40%">Aim :</td>
                        <td width="40%">${request.aim}</td>
                    </tr>
                    <tr>
                        <td width="40%">Price :</td>
                        <td width="40%">${request.price}</td>
                    </tr>
                    <tr>
                        <td width="40%">Description :</td>
                        <td width="40%">${request.description}</td>
                    </tr>
                    <tr>
                        <td width="40%">Possible longitude :</td>
                        <td width="40%">${request.possibleLongitude}</td>
                    </tr>
                    <tr>
                        <td width="40%">Possible latitude :</td>
                        <td width="40%">${request.possibleLatitude}</td>
                    </tr>
                </table>
                <iframe src="https://maps.google.com/maps?q=${request.possibleLongitude},${request.possibleLatitude}&hl=es;z=14&amp;output=embed"
                        width="550" height="450" style="border:0;" allowfullscreen="" loading="lazy"></iframe>
                <security:authorize access="hasRole('ROLE_EXECUTOR')">
                    <div>
                        <a type="button" class="btn btn-primary btn-md" href="/add-report">Add report</a>
                    </div>
                </security:authorize>
            </div>
        </div>
    </div>
</div>

<%@ include file="common/footer.jspf" %>