package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.CupcakeBaseFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cupcakeID = Integer.parseInt(req.getParameter("cupcakeid"));
        List<Cupcake> basket = (List<Cupcake>) req.getSession().getAttribute("basket");

        if (basket != null) {
            Cupcake cupcakeToRemove = CupcakeBaseFacade.findCupcakeById(cupcakeID, connectionPool);
            basket.remove(cupcakeToRemove);
        }

        resp.sendRedirect("basket.jsp");
    }
}