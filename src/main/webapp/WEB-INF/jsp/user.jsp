<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
	<table>
		<tr>
			<td width="40%">Name :</td>
			<td width="40%">${user.username}</td>
		</tr>
		<tr>
			<td>Balance :</td>
			<td>${user.balance}</td>
		</tr>
	</table>
	<div>
		<a type="button" class="btn btn-primary btn-md" href="/add-request">New Request</a>
	</div>
	<br>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3>Your requests</h3>
		</div>
		<div class="panel-body">
			<table class="table table-striped">
				<thead>
				<tr>
					<th width="40%">Type</th>
					<th width="40%">Aim</th>
					<th width="40%">Status</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${requests}" var="request">
					<tr>
						<td>${request.type}</td>
						<td>${request.aim}</td>
						<td>${request.status}</td>
						<td><a type="button" class="btn btn-success"
							   href="/view-request?id=${request.id}">View</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<%@ include file="common/footer.jspf"%>