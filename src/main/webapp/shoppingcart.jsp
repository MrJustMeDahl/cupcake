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


        <table>
            <c:forEach var="order" items="${sessionScope.orderlist}">
                <c:if test="${order.ordered == false && sessionScope.user.userId == order.userID}">

                    <c:forEach var="cupcake" items="${order.cupcakes}">
                <tr>


                        <td> Cupcakebund: ${cupcake.base}</td>
                        <td> Cupcaketop: ${cupcake.topping}</td>


                    <td>pris: ${cupcake.fullPrice} kr</td>

                        <%--<td>betalt: ${order.paid}</td> --%>

                        <%-- <td>orderId: ${order.orderID}</td> --%>
                        <%-- <td>bestilt: ${order.ordered}</td> --%>


                     </tr>
                         </c:forEach>
                     </c:if>

                 </c:forEach>
             </table>

         </jsp:body>

     </t:pagetemplate>