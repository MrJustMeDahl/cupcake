package dat.backend.control;

import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AllOrders", value = "/allorders")
public class AllOrders extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<Order> allOrders = user.getAllOrders();
        request.setAttribute("allOrders", allOrders);
        for(Order o: user.getAllOrders()){
            if(request.getParameter("orderid") != null && o.getOrderID() == Integer.parseInt(request.getParameter("orderid"))){
                request.setAttribute("chosenOrderCupcakes", o.getCupcakes());
            }
        }
        if(allOrders.size() > 0) {
            request.setAttribute("lastOrder", allOrders.get(allOrders.size() - 1));
        }
        request.setAttribute("chosenOrder", request.getParameter("orderid"));
        request.getRequestDispatcher("WEB-INF/orders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
