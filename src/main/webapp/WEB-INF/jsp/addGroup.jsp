<%@include file="header1.jsp"%>
	<div class="container">
		<div class="card mx-auto vcenter vvcenter ">
			<div class="card-header">
				<h2 class="card-title"><center>${head}</center></h2>
			</div>
			<div class="card-body">
				<c:if test="${errorMessage != null}">
					<div class="alert alert-danger" role="alert">${errorMessage}</div>
				</c:if>
				<form:form modelAttribute="groupBean" method="post">
					<div class="form-group">
						<label for="exampleInputEmail1">Group Name</label> 
						<input type="text" name="groupName" class="form-control" required placeholder="Group name" value="${groupBean.groupName}">
						<form:errors cssClass="has-error" path="groupName" />
					</div>
			
					<button type="submit" class="btn btn-primary"><center>${head}</center></button>
				</form:form>
			</div>
		</div>
	</div>

</body>
</html>