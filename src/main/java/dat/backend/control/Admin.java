package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Cupcake;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.UserFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Admin", value = "/admin")
public class Admin extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> allUsers = UserFacade.getAllUsers(connectionPool);
            List<Order> allOrders = new ArrayList<>();
            List<Cupcake> cupcakesForChosenOrder = new ArrayList<>();
            for(User u: allUsers){
                List<Order> ordersForUser = OrderFacade.getOrdersByUserId(u.getUserId(), connectionPool);
                for(Order o: ordersForUser){
                    allOrders.add(o);
                }
            }
            for(Order o: allOrders){
                try {
                    if (o.getOrderID() == Integer.parseInt(request.getParameter("orderid"))) {
                        cupcakesForChosenOrder = o.getCupcakes();
                    }
                } catch( Exception e){}
            }
            HttpSession session = request.getSession();
            if(request.getParameter("userid") != null) {
                session.setAttribute("chosenCustomer", request.getParameter("userid"));
            }
            request.setAttribute("chosenOrder", request.getParameter("orderid"));
            request.setAttribute("chosenOrderCupcakes", cupcakesForChosenOrder);
            request.setAttribute("alluserslist", allUsers);
            request.setAttribute("allorderslist", allOrders);
            request.getRequestDispatcher("WEB-INF/admin.jsp").forward(request, response);
        } catch (DatabaseException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
