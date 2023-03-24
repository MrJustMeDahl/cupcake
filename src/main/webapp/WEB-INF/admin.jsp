<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
        <div class="text-center">
            <div class="card">
                <div class="card-body">
                    <h1>Ongoing orders:</h1>
                    <table class="table table-striped mt-4">
                        <thead>
                        <th>Ordre ID:</th>
                        <th>Totalbeløb:</th>
                        <th>Bestilt:</th>
                        <th>Betalt:</th>
                        </thead>
                        <tr>
                            <td>

                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

    </jsp:body>

</t:pagetemplate>
