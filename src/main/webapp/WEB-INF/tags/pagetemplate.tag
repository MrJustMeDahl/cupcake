<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>

<!DOCTYPE html>
<html lang="da">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>
        <jsp:invoke fragment="header"/>
    </title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/images/cupcakeheader.png" width="70%;" class="mx-auto d-block"/>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container">
            <div class="row d-flex justify-content-center">
                <div class="col-3 col-lg-2  d-inline">
                    <a class="navbar-brand" href="index.jsp">
                        <img src="${pageContext.request.contextPath}/images/Olskerbagerilogo.png"
                             class="img-fluid"/>
                    </a>
                </div>
                <div class="d-inline col-5">
                    <h1 class="text-center">Olsker Bageri</h1>
                </div>
                <div class="col-4">
                    <button class="navbar-toggler float-right" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarNavAltMarkup"
                            aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse justify-content-end" id="navbarNavAltMarkup">
                        <div class="navbar-nav">
                            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/">Page 1</a>
                            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/">Page 2</a>
                            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/">Page 3</a>
                            <c:if test="${sessionScope.user == null }">
                                <a class="nav-item nav-link"
                                   href="${pageContext.request.contextPath}/login.jsp">Login</a>
                            </c:if>
                            <c:if test="${sessionScope.user != null }">
                                <a class="nav-item nav-link" href="${pageContext.request.contextPath}/logout">Log
                                    out</a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</header>

<div id="body" class="container mt-4" style="min-height: 400px;">
    <h1>
        <jsp:invoke fragment="header"/>
    </h1>
    <jsp:doBody/>
</div>

<!-- Footer -->
<div class="container mt-3">
    <hr/>
    <div class="row mt-4">
        <div class="col">
            Olsker, Lindegårdsvej 1<br/>
            3770 Allinge
        </div>
        <div class="col">
            <jsp:invoke fragment="footer"/>
            <br/>
            <p><a href="aboutus">Om os</a></p>
        </div>
        <div class="col">
            Datamatikeruddannelsen<br/>
            2. semester forår 2023
        </div>
    </div>

</div>

</div>

<!-- Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

</body>
</html>