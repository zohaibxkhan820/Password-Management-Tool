<%@include file="header.jsp"%>
<div class="container">
		<div class="card mx-auto vcenter float-lg-right" style="width=16rem;">
			<div class="card-header">
				<h2 class="card-title justify-content-center"><center>Login</center></h2>
			</div>
			<div class="card-body">
				<c:if test="${param.error != null}">
					<div class="alert alert-danger" role="alert">Invalid Username / Password</div>
				</c:if>
				<form:form action="perform_login" method="post">
					<div class="form-group">
						<label for="exampleInputEmail1">Username</label>
						<input
							name="username" required type="text" class="form-control"
							id="exampleInputEmail1" aria-describedby="emailHelp"
							placeholder="Enter username">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Password</label>
						<input
							required name="password" type="password" class="form-control"
							id="exampleInputPassword1" placeholder="Password">
					</div>
					<button type="submit" class="btn btn-primary"><center>Login</center></button>
				</form:form>
			</div>

			<div class="card-footer">
				<div class="d-flex justify-content-center links">
					Don't have an account?<a
						href="${pageContext.request.contextPath}/register">Sign Up</a>
				</div>
			</div>

		</div>
	</div>
	</div>

</body>
</html>