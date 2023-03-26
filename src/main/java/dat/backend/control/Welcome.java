package dat.backend.control;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.CupcakeBase;
import dat.backend.model.entities.CupcakeTopping;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.CupcakeBaseFacade;
import dat.backend.model.persistence.CupcakeToppingFacade;
import dat.backend.model.persistence.UserFacade;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Welcome", value = "/welcome")
public class Welcome extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            List<CupcakeBase> cupcakebaseList = CupcakeBaseFacade.getAllBases(connectionPool);
            List<CupcakeTopping> cupcaketoppingList = CupcakeToppingFacade.getAllToppings(connectionPool);
            request.setAttribute("cupcakebase", cupcakebaseList);
            request.setAttribute("cupcaketopping", cupcaketoppingList);
        } catch (DatabaseException e) {
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
        try{
            session.setAttribute("user", UserFacade.getUserByID(user.getUserId(),connectionPool));
        }catch (DatabaseException e){
            request.setAttribute("errormessage", e);
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
        request.getRequestDispatcher("WEB-INF/welcome.jsp"). forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
