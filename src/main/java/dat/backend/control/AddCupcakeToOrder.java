package dat.backend.control;

import com.mysql.cj.Session;
import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "AddCupcakeToOrder", value = "/addcupcaketoorder")
public class AddCupcakeToOrder extends HttpServlet {

    private static ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cupcake cupcake = null;
        int cupcakebaseId = Integer.parseInt(request.getParameter("cupcakebase"));
        int cupcaketoppingId = Integer.parseInt(request.getParameter("cupcaketopping"));
        int quantity = Integer.parseInt(request.getParameter("number"));
        boolean customerHasActiveOrder = false;
        int activeOrderId = 0;
        try {
            cupcake = new Cupcake(CupcakeToppingFacade.getOneToppings(cupcaketoppingId, connectionPool), CupcakeBaseFacade.getOneBase(cupcakebaseId, connectionPool));
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }


        for (Order o : user.getAllOrders()) {
            if (!o.getOrdered()) {
                activeOrderId = o.getOrderID();
                customerHasActiveOrder = true;
            }
        }
        if (customerHasActiveOrder) {
            for (int i = 0; i < quantity; i++) {
                try {
                    OrderFacade.insertCupcakeForOrder(activeOrderId, cupcake, connectionPool);
                    OrderFacade.updateTotalBalanceForOrder(activeOrderId, connectionPool);
                } catch (DatabaseException e) {
                    request.setAttribute("errormessage", e);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }

            response.sendRedirect("welcome");
        } else {
            Order newOrder = null;
            try {
                newOrder = OrderFacade.createOrder(user.getUserId(), cupcake, connectionPool);
                user.getAllOrders().add(newOrder);
            } catch (DatabaseException e) {
                request.setAttribute("errormessage", e);
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }

            for (int i = 0; i < quantity - 1; i++) {
                try {

                    OrderFacade.insertCupcakeForOrder(newOrder.getOrderID(), cupcake, connectionPool);
                } catch (DatabaseException e) {
                    request.setAttribute("errormessage", e);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }

            response.sendRedirect("welcome");
        }
    }
}
