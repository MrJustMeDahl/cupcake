<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
        Indkøbskurv

    </jsp:attribute>

    <jsp:attribute name="footer">
        Indkøbskurv

    </jsp:attribute>

    <jsp:body>


        <p>Her er din indkøbskurv</p>


        <table class="table">
            <thead>
            <th>Cupcakebund</th>
            <th>Cupcaketop</th>
            <th>Samlet pris: ${requestScope.order.totalPrice} kr</th>
            <th></th>
            </thead>

                    <c:forEach var="cupcake" items="${requestScope.order.cupcakes}">
                <tr>

                        <td> Cupcakebund: ${cupcake.base}</td>
                        <td> Cupcaketop: ${cupcake.topping}</td>
                        <td>pris: ${cupcake.fullPrice} kr</td>
                    <td>
                    <form action="removecupcake" method="post">
                        <button class="btn btn-outline-danger" type="submit" value="${cupcake.cupcakeId}" name="cupcakeId">fjern</button>
                    </form></td>
                        <%--<td>betalt: ${order.paid}</td> --%>
                        <%-- <td>orderId: ${order.orderID}</td> --%>
                        <%-- <td>bestilt: ${order.ordered}</td> --%>

                     </tr>
                         </c:forEach>

             </table>
        <form action="orderandpayment" method="get">
            <button class="btn btn-outline-primary" type="submit" value="${requestScope.order.orderID}" name="OrderId">Bestil</button>
        </form>
        <form action="orderandpayment" method="post">
            <button class="btn btn-outline-primary" type="submit" value="${requestScope.order.orderID}" name="OrderId">Betal</button>
        </form>

         </jsp:body>

     </t:pagetemplate>