<%@include file="header.jsp"%>
	<div class="container">
		<div class="card mx-auto vcenter ">
			<div class="card-body">
			    <h2 class="card-title"><center>Register</center></h2>
				<c:if test="${errorMessage != null}">
					<div class="alert alert-danger" role="alert">${errorMessage}</div>
				</c:if>
				<form:form modelAttribute="userBean" method="post">
					<div class="form-group">
						<label for="exampleInputEmail1">Name</label> 
						<input type="text" name="name" class="form-control" required placeholder="name" value="${userBean.name}">
						<form:errors cssClass="has-error" path="name" />
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">User Name</label> 
						<input type="text" name="userName" class="form-control" required placeholder="user name" value="${userBean.userName}">
						<form:errors cssClass="has-error" path="userName" />
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Password</label> 
						<input type="password" name="password" class="form-control" required placeholder="password" value="${userBean.password}">
						<form:errors cssClass="has-error" path="password" />
					</div>
					<button type="submit" class="btn btn-primary"><center>Register</center></button>
				</form:form>
			</div>

		</div>
	</div>

</body>
</html>