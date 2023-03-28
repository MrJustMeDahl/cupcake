package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Cupcake;
import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.CupcakeBaseFacade;
import dat.backend.model.persistence.OrderFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RemoveCupcakeFromOrder", value = {"/removecupcake"})
public class RemoveCupcakeFromOrder extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cupcakeID = Integer.parseInt(request.getParameter("cupcakeId"));
        try {
            OrderFacade.removeCupcakeFromOrder(cupcakeID, connectionPool);
        } catch (DatabaseException e) {
            request.setAttribute("errormessage",e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }


        response.sendRedirect("shoppingbasket");
    }
}