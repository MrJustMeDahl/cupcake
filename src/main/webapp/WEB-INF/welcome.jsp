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

        <h2>Ordreside</h2>
        <c:if test="${sessionScope.user!= null}">
            <p> Welcome "${sessionScope.user.name}"</p>
        </c:if>


        <table class="align-center mt-5">
            <thead>
            <tr>
                <th>Base</th>
                <th>Topping</th>
                <th>Antal</th>
                <th>Tilføj</th>
            </tr>
            </thead>

            <tr>
                    <%--********************************************
                        select the cupcake base with a drop down
                    ********************************************--%>
                <form action="list" method="post">
                    <td>
                        <select name="cupcakebase" id="base">
                            <c:forEach items="${requestScope.cupcakebase}" var="cupcakebase">
                                <option value="${cupcakebase.baseID}"> ${cupcakebase.flavor} - ${cupcakebase.price}kr
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                        <%--********************************************
                           select the cupcake topping with a drop down
                        ********************************************--%>
                    <td>
                        <Select name="cupcaketopping" id="topping">
                            <c:forEach items="${requestScope.cupcaketopping}" var="cupcaketopping">
                                <option value="${cupcaketopping.toppingID}"> ${cupcaketopping.flavor} - ${cupcaketopping.price}kr
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                        <%--********************************************
                           choose how many cupcakes you want
                        ********************************************--%>
                    <td>
                        <input id="number" classs="d-inline form-control w-10" type="number" name="number"
                               placeholder="quantity" min="1"/>
                    </td>
                        <%--********************************************
                    press the button to add to basket or link to go to checkout
                        ********************************************--%>
                    <td>
                        <button
                                formaction="addcupcaketoorder" class="btn btn-outline-dark" name="cupcakeId">Tilføj
                        </button> <br/>
                        <a href="indkøbskurv">Indkøbskurv</a>

                    </td>
                </form>
            </tr>
        </table>

    </jsp:body>

</t:pagetemplate>