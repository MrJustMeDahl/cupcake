package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Cupcake;
import dat.backend.model.entities.CupcakeOrder;
import dat.backend.model.persistence.ConnectionPool;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

@WebServlet(name = "AddCupcakeToOrder", value = "/addcupcaketoorder")
public class AddCupcakeToOrder extends HttpServlet {

    private static ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        int base = Integer.parseInt(request.getParameter("cupcakebase"));
        int topping = Integer.parseInt(request.getParameter("cupcaketopping"));
        int quantity = Integer.parseInt(request.getParameter("number"));

        Connection connection





        CupcakeOrder cupcake = new CupcakeOrder(base, topping);

        String sql = "INSERT INTO cupcake (baseId, toppingId) VALUES (?,?)";








        request.getRequestDispatcher("WEB-INF/welcome.jsp").forward(request, response);

    }
}
