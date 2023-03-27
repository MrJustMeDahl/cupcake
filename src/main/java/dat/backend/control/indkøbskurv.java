package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.UserFacade;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "indkøbskurv", urlPatterns = {"/shoppingbasket"} )
public class indkøbskurv extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        try {
            User user = (User) session.getAttribute("user");

            List<Order> orderList = OrderFacade.getOrdersByUserId(user.getUserId(), connectionPool);

            request.setAttribute("orderlist", orderList);
            for (Order o: orderList) {
                if(!o.getOrdered()){

                    request.setAttribute("order", o);

                }
            }

            request.getRequestDispatcher("shoppingcart.jsp").forward(request, response);

        } catch (DatabaseException e) {
            e.printStackTrace();
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {

    }

}