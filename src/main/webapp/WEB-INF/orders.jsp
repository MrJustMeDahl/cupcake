<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
    </jsp:attribute>

    <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>
        <div class="text-center">
            <div class="card mt-2">
                <div class="card-body">
                    <c:choose>
                    <c:when test="${requestScope.allOrders.size() > 0}">
                    <h1>Sidste bestilling:</h1>
                    <table class="table table-striped">
                        <thead>
                        <th>Ordre ID: ${requestScope.lastOrder.orderID}</th>
                        <th>Bund:</th>
                        <th>Top:</th>
                        <th>Pris: ${requestScope.lastOrder.totalPrice} kr.</th>
                        <th>Betalt: <c:if test="${requestScope.lastOrder.paid}"><input type="checkbox" checked
                                                                                       disabled></c:if><c:if
                                test="${!requestScope.lastOrder.paid}"><input type="checkbox" disabled></c:if></th>
                        </thead>
                        <c:forEach var="cupcake" items="${requestScope.lastOrder.cupcakes}">
                            <tr>
                                <td></td>
                                <td>${cupcake.base.flavor}</td>
                                <td>${cupcake.topping.flavor}</td>
                                <td>${cupcake.fullPrice} kr.</td>
                                <td></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
            <div class="card mt-2">
                <div class="card-body">
                    <h1>Alle bestillinger:</h1>
                    <table class="table table-striped">
                        <thead>
                        <th>Ordre ID:</th>
                        <th>Pris:</th>
                        <th>Betalt:</th>
                        <th></th>
                        </thead>
                        <c:forEach var="order" items="${requestScope.allOrders}">
                            <c:if test="${order.ordered}">
                                <tr>
                                    <td>
                                            ${order.orderID}
                                    </td>
                                    <td>
                                            ${order.totalPrice} kr.
                                    </td>
                                    <td>
                                        <c:if test="${order.paid}">
                                            <input type="checkbox" disabled checked/>
                                        </c:if>
                                        <c:if test="${!order.paid}">
                                            <input type="checkbox" disabled/>
                                        </c:if>
                                    </td>
                                    <form>
                                        <c:choose>
                                            <c:when test="${requestScope.chosenOrder != order.orderID}">
                                                <td>
                                                    <button type="submit" class="btn btn-outline-primary btn-sm"
                                                            value="${order.orderID}"
                                                            name="orderid" , formaction="allorders"
                                                            formmethod="get">
                                                        Åben ordre
                                                    </button>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>
                                                    <button type="submit" class="btn btn-outline-primary btn-sm"
                                                            formaction="allorders" formmethod="get"
                                                            name="orderid"
                                                            value="0">
                                                        Luk ordre
                                                    </button>
                                                </td>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </tr>
                                <c:if test="${requestScope.chosenOrder == order.orderID}">
                                    <tr class="fw-bold">
                                        <td>Bund:</td>
                                        <td>Topping:</td>
                                        <td>Pris pr. stk.:</td>
                                        <td></td>
                                    </tr>
                                    <c:forEach var="cupcake"
                                               items="${requestScope.chosenOrderCupcakes}">
                                        <tr>
                                            <td>${cupcake.base.flavor}</td>
                                            <td>${cupcake.topping.flavor}</td>
                                            <td>${cupcake.fullPrice} kr.</td>
                                            <td></td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </table>
                    </c:when>
                    <c:otherwise>
                        <h1>Du har ingen ordre for øjeblikket. Se vores menu her: <a href="welcome">Menu</a></h1>
                    </c:otherwise>
    </c:choose>
                </div>
            </div>
        </div>
    </jsp:body>

</t:pagetemplate>