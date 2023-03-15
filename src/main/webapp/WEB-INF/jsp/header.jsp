<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
			<!DOCTYPE html>
			<html>

			<head>
				<meta charset="ISO-8859-1">
				<link href="<c:url value=" /resources/bootstrap.css" />" rel="stylesheet">
				<link href="<c:url value=" /resources/style.css" />" rel="stylesheet">
				<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js" />
				<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
				<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
				<title>Password Management Tool</title>
				<style>

                        .navbar-custom {
                         background-color:#003366;
                        }
                        .head1
                        {
                            font-weight:bold;

                        }
                        .sidehead{
                            font-weight:700;
                            font-size:27px;
                            padding-left:12%;
                            padding-top:17%;
                            font-family:Bodoni MT;
                            font-style:italic;
                            }
                            .body1{
                               display:flex;
                            }
                            .changecolor{
                                color:#003366;
                            }
                            .container2{
                            margin-right:42px;
                            padding-top:5px;
                            }
                    </style>
			</head>

			<body>
				<nav class="navbar navbar-expand-lg navbar-dark navbar-custom">
					<div class="container container2">
						<a class="navbar-brand head1" href="#">Password Management Tool</a>
						<div class="collapse navbar-collapse" id="navbarSupportedContent">
							<ul class="navbar-nav ml-auto">
								<c:if test="${pageContext.request.userPrincipal.name == null}">
									<li class="nav-item active"><a class="nav-link"
											href="${pageContext.request.contextPath}/login">Login
										</a></li>
									<li class="nav-item active"><a class="nav-link"
											href="${pageContext.request.contextPath}/register">Register</a>
									</li>
								</c:if>
								<c:if test="${pageContext.request.userPrincipal.name != null}">
									<%--<li class="nav-item active"><a class="nav-link"
											href="${pageContext.request.contextPath}/accounts">View Accounts
										</a></li>--%>
									<li class="nav-item active"><a class="nav-link"
											href="${pageContext.request.contextPath}/accounts/account">Add Account
										</a></li>

									<li class="nav-item active"><a class="nav-link"
											href="${pageContext.request.contextPath}/groups/group">Add Group</a>
									</li>

									<li class="nav-item active"><a class="nav-link"
											href="${pageContext.request.contextPath}/perform_logout">LogOut</a>
									</li>
								</c:if>
							</ul>
						</div>
					</div>
				</nav>
				<div class="body1">
				    <div class="sidehead">Protect your Passwords with Strong <span class="changecolor">ENCRYPTION</span> and High <span class="changecolor">SECURITY<span> </div>
