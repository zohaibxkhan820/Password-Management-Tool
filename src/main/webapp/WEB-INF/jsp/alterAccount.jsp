<%@include file="header.jsp"%>
	<div class="container">
		<div class="card mx-auto vcenter ">
			<div class="card-body">
			    <h2 class="card-title"><center>${head}</center></h2>
				<c:if test="${errorMessage != null}">
					<div class="alert alert-danger" role="alert">${errorMessage}</div>
				</c:if>
				<form:form modelAttribute="accountBean" method="post">
					<div class="form-group">
						<label for="exampleInputEmail1">${head}</label> 
						<input type="text" class="form-control" name="accountName" required placeholder="account name" value="${accountBean.accountName}">
						<form:errors cssClass="has-error" path="accountName" />
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">User Name</label> 
						<input type="text" class="form-control" name="userName" required placeholder="user name" value="${accountBean.userName}">
						<form:errors cssClass="has-error" path="userName" />
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">URL</label> 
						<input type="text" class="form-control" name="url" placeholder="url" value="${accountBean.url}">
						<form:errors cssClass="has-error" path="url" />
					</div>
                    <div class="form-group">
						<label for="exampleInputPassword1">Password</label> 
						<input type="password" class="form-control" name="password" required placeholder="password" value="${accountBean.password}">
						<form:errors cssClass="has-error" path="password" />
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Group</label> 
						<form:select cssClass="custom-select" path="group.groupId" >
							<form:option   value = "0" label="None"/>
							<form:options  items="${groupDropDown}" itemValue="groupId" />
						</form:select>
						</div>
					<button type="submit" class="btn btn-primary"><center>${head}</center></button>
				</form:form>
			</div>
		</div>
	</div>

</body>
</html>