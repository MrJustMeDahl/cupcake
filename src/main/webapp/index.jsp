<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">

    </jsp:attribute>

    <jsp:attribute name="footer">

    </jsp:attribute>

    <jsp:body>

        <p>Vi går op i smag, detaljer og finish! </p>
        <img src="${pageContext.request.contextPath}/images/cupcakesFigmaBillede1.jpg" width="300px;" class="img-fluid"/>

        <p>Kombinér din egen cupcake blandt vores mange muligheder af bunde og toppings!</p>
        <img src="${pageContext.request.contextPath}/images/cupcakemaking.jpg" width="300px;" class="img-fluid"/>


    </jsp:body>

</t:pagetemplate>