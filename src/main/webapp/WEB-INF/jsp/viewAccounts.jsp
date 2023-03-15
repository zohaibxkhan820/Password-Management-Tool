<%@include file="header1.jsp" %>
    <script>
        $(".toggle-password").click(function () {

            $(this).toggleClass("fa-eye fa-eye-slash");
            var input = $($(this).attr("toggle"));
            if (input.attr("type") == "password") {
                input.attr("type", "text");
            } else {
                input.attr("type", "password");
            }
        });
    </script>
    <div class="container container1">
    <%--<a class="btn btn-primary btn-sm" href="/accounts/account" role="button">Add Account</a>--%>
    <%--<a class="btn btn-primary btn-sm"	href="${pageContext.request.contextPath}/groups/group" role="button">Add Group</a>--%>
        <h1 class="m-4">Account Listings</h1>
        <div id="accordion" class="m-4">
            <c:forEach items="${allGroups}" var="group">

                <c:set var="sizeOfList" value="${group.accounts.size()}" />
                <div class="card m-4">
                    <div class="card-header">
                        <a class="collapsed card-link" data-toggle="collapse" href="#${group.groupName}">
                            ${group.groupName} (${sizeOfList})
                        </a>
                    </div>
                    <div id="${group.groupName}" class="collapse" data-parent="#accordion">
                        <div class="card-body">
                            <c:if test="${group.groupId != 0}">
                                <a href="/groups/group/${group.groupId}" class="btn btn-warning m-2">Update Group</a>
                                <c:if test="${sizeOfList == 0}">
                                <form action="/groups/group/delete/${group.groupId}" method="POST">
                                    <button type="submit" onclick="return confirm('Are you sure you want to delete this Account?');" class="btn btn-danger m-2">Delete</button>
                                </form>
                                </c:if>
                            </c:if>
                            <div class="row">
                                <c:forEach items="${group.accounts}" var="account">
                                    <div class="card m-2" style="width: 18rem;">

                                        <div class="card-body">
                                            <h5 class="card-title">${account.accountName}</h5>
                                            <ul class="list-group list-group-flush">
                                                <li class="list-group-item">
                                                    User Name : ${account.userName}
                                                </li>
                                                <li class="list-group-item">
                                                    Password : ${account.password}
                                                </li>
                                                <li class="list-group-item">
                                                    URL : <br>
                                                    <a href="${account.url}" target="_blank"> ${account.url}</a>

                                                </li>
                                            </ul>
                                            <div class="row m-2">
                                                <a href="/accounts/account/${account.accountId}"
                                                    class="btn btn-warning m-2">Update</a>
                                                <form action="/accounts/account/delete/${account.accountId}"
                                                    method="POST">
                                                    <button type="submit"
                                                        onclick="return confirm('Are you sure you want to delete this Account?');"
                                                        class="btn btn-danger m-2">Delete</button>
                                                </form>

                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>

                            </div>
                        </div>
                    </div>
                </div>

            </c:forEach>
        </div>
        <script>
            $(".toggle-password").click(function () {

                $(this).toggleClass("fa-eye fa-eye-slash");
                var input = $($(this).attr("toggle"));
                if (input.attr("type") == "password") {
                    input.attr("type", "text");
                } else {
                    input.attr("type", "password");
                }
            });
        </script>
    </div>