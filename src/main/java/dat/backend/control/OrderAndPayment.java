package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.UserFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "orderandpayment", urlPatterns = {"/orderandpayment"} )
public class OrderAndPayment extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        try {
            OrderFacade.updateOrderOrdered(Integer.parseInt(request.getParameter("OrderId")), true, connectionPool);
            response.sendRedirect("shoppingbasket");
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        try {

            User user = (User) session.getAttribute("user");
            Order order = OrderFacade.getOrderByOrderId(Integer.parseInt(request.getParameter("OrderId")), connectionPool);
            //Order order = (Order) session.getAttribute("order");
            if(user.getBalance()>= order.getTotalPrice()) {

                UserFacade.updateBalance(order.getTotalPrice()*-1, user.getUserId(), connectionPool);
                System.out.println(user.getUserId()+ order.getTotalPrice());
                OrderFacade.updateOrderOrdered(Integer.parseInt(request.getParameter("OrderId")), true, connectionPool);
                OrderFacade.updateOrderPaid(Integer.parseInt(request.getParameter("OrderId")), true, connectionPool);

                session.setAttribute("user", UserFacade.getUserByID(user.getUserId(), connectionPool));
                response.sendRedirect("shoppingbasket");
            }
            else {
                System.out.println("Du har ikke penge nok på kontoen");
                response.sendRedirect("shoppingbasket");
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

}