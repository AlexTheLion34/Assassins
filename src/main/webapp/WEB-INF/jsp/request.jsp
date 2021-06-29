<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3 ">
			<div class="panel panel-primary">
				<div class="panel-heading">New request</div>
				<div class="panel-body">
					<form:form method="post" modelAttribute="request">
						<form:hidden path="id" />
						<fieldset class="form-group">
							<form:label path="type">Type</form:label>
							Artifact <form:radiobutton required="required" path="type" value="Artifact"/>
							Contract kill <form:radiobutton required="required" path="type" value="Contract kill"/>
						</fieldset>
						<fieldset class="form-group">
							<form:label path="aim">Aim</form:label>
							<form:input path="aim" type="text" class="form-control"
										required="required" />
							<form:errors path="aim" cssClass="text-warning"/>
						</fieldset>
						<fieldset class="form-group">
							<form:label path="price">Price</form:label>
							<form:input path="price" type="number" class="form-control"
										required="required" minvalue="0"/>
							<form:errors path="price" cssClass="text-warning"/>
						</fieldset>
						<fieldset class="form-group">
							<form:label path="possibleLongitude">Possible Longitude</form:label>
							<form:input path="possibleLongitude" type="number" class="form-control"
								required="required" step="0.000000001"/>
							<form:errors path="possibleLongitude" cssClass="text-warning" />
						</fieldset>
						<fieldset class="form-group">
							<form:label path="possibleLatitude">Possible Latitude</form:label>
							<form:input path="possibleLatitude" type="number" class="form-control"
										required="required" step="0.000000001"/>
							<form:errors path="possibleLatitude" cssClass="text-warning" />
						</fieldset>
						<fieldset class="form-group">
							<form:label path="description">Description</form:label>
							<form:input path="description" type="text" class="form-control"
										required="required" />
							<form:errors path="description" cssClass="text-warning" />
						</fieldset>
						<button type="submit" class="btn btn-success">Make</button>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="common/footer.jspf"%>