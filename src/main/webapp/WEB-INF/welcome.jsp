<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
         Welcome to the logged in area
    </jsp:attribute>

            <jsp:attribute name="footer">
    </jsp:attribute>

    <jsp:body>

        <h2>Ordersite</h2>
        <c:if test="${sessionScope.user!= null}">
            <h3> Welcome "${sessionScope.user.name}"</h3>
        </c:if>


        <table class="align-center mt-5">
            <thead>
            <tr>
                <th>Base</th>
                <th>Topping</th>
                <th>Price</th>
                <th>number</th>
                <th>Action</th>
            </tr>
            </thead>

            <tr>
                    <%--********************************************
                        select the cupcake base with a drop down
                    ********************************************--%>
                <form action="list" method="post">
                    <td>
                        <select name="cupcakebase" id="base">
                            <c:forEach items="${requestScope.cupcakebaselist}" var="cupcakebase">
                                <option value="${cupecakebaseId}"> ${cupcakebase.name}, ${cupcakebase.price}</option>
                            </c:forEach>
                        </select>
                    </td>
                        <%--********************************************
                           select the cupcake topping with a drop down
                        ********************************************--%>
                    <td>
                        <Select name="cupcaketopping" id="topping">
                            <c:forEach items="${requestScope.cupcaketoppinglist}" var="cupcaketopping">
                                <option value="${cupcaketoppingId}"> ${cupcaketopping.name}, ${cupcaketopping.price}</option>
                            </c:forEach>
                        </select>
                    </td>
                        <%--********************************************
                           see the total price for the cupcake
                        ********************************************--%>
                    <td>
                        <c:if test="${base!=null && topping!=null}">
                            ${cupcakebaseList.price + cupcaketopping.price}
                        </c:if>
                    </td>
                        <%--********************************************
                           choose how many cupcakes you want
                        ********************************************--%>
                    <td>
                        <input id="number" classs="d-inline form-control w-10" type="number" name="quantity"
                               placeholder="quantity"/>
                    </td>
                        <%--********************************************
                           press the button to add to basket
                        ********************************************--%>
                    <td>
                        <button formaction="Add" class="btn btn-outline-dark" name="cupcakeId"
                                value="${cupcake.cupcakeId}"/>
                    </td>
                </form>
            </tr>
        </table>

    </jsp:body>

</t:pagetemplate>