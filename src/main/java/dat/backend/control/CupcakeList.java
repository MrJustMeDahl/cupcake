package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.CupcakeBase;
import dat.backend.model.entities.CupcakeTopping;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.CupcakeBaseFacade;
import dat.backend.model.persistence.CupcakeToppingFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CupcakeList", value = "/cupcakelist")
public class CupcakeList extends HttpServlet {

    private static ConnectionPool connectionPool = ApplicationStart.getConnectionPool();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CupcakeBase> cupcakebaseList = CupcakeBaseFacade.getAllBases(connectionPool);
        List<CupcakeTopping> cupcaketoppingList = CupcakeToppingFacade.getAllToppings(connectionPool);
        request.setAttribute("cupcakebase", cupcakebaseList);
        request.setAttribute("cupcaketopping", cupcaketoppingList);




        request.getRequestDispatcher("WEB-INF/welcome.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




    }
}
