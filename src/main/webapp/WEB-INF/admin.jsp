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
            <form>
                <div class="container">
                    <div class="d-flex">
                        <div class="col-3">
                            <select name="userid">
                                <option>Vælg bruger</option>
                                <option value="0">Alle brugere</option>
                                <c:forEach var="user" items="${requestScope.alluserslist}">
                                    <option value="${user.userId}">${user.userId}. ${user.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-1">
                            <button type="submit" class="btn btn-outline-primary btn-sm ml-5" formaction="admin"
                                    formmethod="get">
                                Filtrér
                            </button>
                        </div>

                        <div class="ms-auto">
                            <c:choose>
                                <c:when test="${sessionScope.chosenCustomer == 0 || sessionScope.chosenCustomer == null}">
                                    Alle brugere
                                </c:when>
                                <c:otherwise>
                                        <button type="submit" name="chosenuser" value="${sessionScope.chosenCustomer}"
                                                class="btn btn-sm - btn-outline-success" formaction="giveusermoney"
                                                formmethod="post">Giv kunde kredit
                                        </button>
                                        <input type="number" name="credit" placeholder="Indtast beløb"/>
                                    <c:forEach var="user" items="${requestScope.alluserslist}">
                                        <c:if test="${user.userId == sessionScope.chosenCustomer}">
                                            Valgt bruger: ${user.name}
                                        </c:if>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </form>
            <c:choose>
                <c:when test="${sessionScope.chosenCustomer == 0 || sessionScope.chosenCustomer == null}">
                    <div class="card mt-2">
                        <div class="card-body">
                            <h1>Ongoing orders:</h1>
                            <table class="table table-striped">
                                <thead>
                                <th>Ordre ID:</th>
                                <th>Kunde:</th>
                                <th>Samlet pris:</th>
                                <th>Betalt:</th>
                                <th></th>
                                </thead>
                                <form>
                                    <c:forEach var="order" items="${requestScope.allorderslist}">
                                        <c:if test="${order.ordered && !order.paid}">
                                            <tr>
                                                <td>
                                                        ${order.orderID}
                                                </td>
                                                <td>
                                                    <c:forEach var="user" items="${requestScope.alluserslist}">
                                                        <c:if test="${order.userID == user.userId}">
                                                            ${user.name}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td>
                                                        ${order.totalPrice}
                                                </td>

                                                <td>
                                                        ${order.paid}
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${requestScope.chosenOrder != order.orderID}">
                                                            <button type="submit" class="btn btn-outline-primary btn-sm"
                                                                    value="${order.orderID}"
                                                                    name="orderid" , formaction="admin"
                                                                    formmethod="get">
                                                                Åben ordre
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button type="submit" class="btn btn-outline-success btn-sm"
                                                                    formaction="adminupdatepaid" formmethod="post"
                                                                    name="orderid"
                                                                    value="${order.orderID}">
                                                                Er betalt
                                                            </button>
                                                            <button type="submit" class="btn btn-outline-danger btn-sm"
                                                                    formaction="deleteorder"
                                                                    formmethod="post" name="orderid"
                                                                    value="${order.orderID}">
                                                                Slet ordre
                                                            </button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </form>
                            </table>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <h1>Completed Orders:</h1>
                            <table class="table table-striped">
                                <thead>
                                <th>Ordre ID:</th>
                                <th>Kunde:</th>
                                <th>Samlet pris:</th>
                                <th>Betalt:</th>
                                <th></th>
                                </thead>
                                <form>
                                    <c:forEach var="order" items="${requestScope.allorderslist}">
                                        <c:if test="${order.ordered && order.paid}">
                                            <tr>
                                                <td>
                                                        ${order.orderID}
                                                </td>
                                                <td>
                                                    <c:forEach var="user" items="${requestScope.alluserslist}">
                                                        <c:if test="${order.userID == user.userId}">
                                                            ${user.name}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td>
                                                        ${order.totalPrice}
                                                </td>
                                                <td>
                                                        ${order.paid}
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${requestScope.chosenOrder != order.orderID}">
                                                            <button type="submit" class="btn btn-outline-primary btn-sm"
                                                                    value="${order.orderID}"
                                                                    name="orderid" , formaction="admin"
                                                                    formmethod="get">
                                                                Åben ordre
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button type="submit" class="btn btn-outline-warning btn-sm"
                                                                    formaction="adminupdatepaid" formmethod="get"
                                                                    name="orderid"
                                                                    value="${order.orderID}">
                                                                Ikke betalt
                                                            </button>
                                                            <button type="submit" class="btn btn-outline-danger btn-sm"
                                                                    formaction="deleteorder"
                                                                    formmethod="post" name="orderid"
                                                                    value="${order.orderID}">
                                                                Slet ordre
                                                            </button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </form>
                            </table>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="card mt-2">
                        <div class="card-body">
                            <h1>Ongoing orders:</h1>
                            <table class="table table-striped mt-4">
                                <thead>
                                <th>Ordre ID:</th>
                                <th>Kunde:</th>
                                <th>Samlet pris:</th>
                                <th>Betalt:</th>
                                <th></th>
                                </thead>
                                <form>
                                    <c:forEach var="order" items="${requestScope.allorderslist}">
                                        <c:if test="${order.ordered && !order.paid && order.userID == sessionScope.chosenCustomer}">
                                            <tr>
                                                <td>
                                                        ${order.orderID}
                                                </td>
                                                <td>
                                                    <c:forEach var="user" items="${requestScope.alluserslist}">
                                                        <c:if test="${order.userID == user.userId}">
                                                            ${user.name}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td>
                                                        ${order.totalPrice}
                                                </td>

                                                <td>
                                                        ${order.paid}
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${requestScope.chosenOrder != order.orderID}">
                                                            <button type="submit" class="btn btn-outline-primary btn-sm"
                                                                    value="${order.orderID}"
                                                                    name="orderid" , formaction="admin"
                                                                    formmethod="get">
                                                                Åben ordre
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button type="submit" class="btn btn-outline-success btn-sm"
                                                                    formaction="adminupdatepaid" formmethod="post"
                                                                    name="orderid"
                                                                    value="${order.orderID}">
                                                                Er betalt
                                                            </button>
                                                            <button type="submit" class="btn btn-outline-danger btn-sm"
                                                                    formaction="deleteorder"
                                                                    formmethod="post" name="orderid"
                                                                    value="${order.orderID}">
                                                                Slet ordre
                                                            </button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </form>
                            </table>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <h1>Completed Orders:</h1>
                            <table class="table table-striped mt-4">
                                <thead>
                                <th>Ordre ID:</th>
                                <th>Kunde:</th>
                                <th>Samlet pris:</th>
                                <th>Betalt:</th>
                                <th></th>
                                </thead>
                                <form>
                                    <c:forEach var="order" items="${requestScope.allorderslist}">
                                        <c:if test="${order.ordered && order.paid && order.userID == sessionScope.chosenCustomer}">
                                            <tr>
                                                <td>
                                                        ${order.orderID}
                                                </td>
                                                <td>
                                                    <c:forEach var="user" items="${requestScope.alluserslist}">
                                                        <c:if test="${order.userID == user.userId}">
                                                            ${user.name}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td>
                                                        ${order.totalPrice}
                                                </td>
                                                <td>
                                                        ${order.paid}
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${requestScope.chosenOrder != order.orderID}">
                                                            <button type="submit" class="btn btn-outline-primary btn-sm"
                                                                    value="${order.orderID}"
                                                                    name="orderid" , formaction="admin"
                                                                    formmethod="get">
                                                                Åben ordre
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button type="submit" class="btn btn-outline-warning btn-sm"
                                                                    formaction="adminupdatepaid" formmethod="get"
                                                                    name="orderid"
                                                                    value="${order.orderID}">
                                                                Ikke betalt
                                                            </button>
                                                            <button type="submit" class="btn btn-outline-danger btn-sm"
                                                                    formaction="deleteorder"
                                                                    formmethod="post" name="orderid"
                                                                    value="${order.orderID}">
                                                                Slet ordre
                                                            </button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </form>
                            </table>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

    </jsp:body>

</t:pagetemplate>
