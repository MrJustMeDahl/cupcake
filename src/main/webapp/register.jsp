<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
             Register
    </jsp:attribute>

    <jsp:attribute name="footer">
            Register
    </jsp:attribute>

    <jsp:body>

        <form action="register" method="post">
            <label for="name">Indtast Fulde navn: </label>
            <br/>
            <input type="text" id="name" name="name"/>
            <br/>
            <label for="username">Indtast Email: </label>
            <br/>
            <input type="text" id="username" name="username"/>
            <br/>
            <label for="password">Indtast Kodeord: </label>
            <br/>
            <input type="password" id="password" name="password"/>
            <br/>
            <input type="submit"  value="Opret bruger!"/>

        </form>



    </jsp:body>
</t:pagetemplate>