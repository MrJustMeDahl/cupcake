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
        for (Order o : user.getAllOrders()) {
            if (!o.getOrdered()) {

                int cupcakebaseId = Integer.parseInt(request.getParameter("cupcakebase"));
                int cupcaketoppingId = Integer.parseInt(request.getParameter("cupcaketopping"));
                int quantity = Integer.parseInt(request.getParameter("number"));
                int orderId = o.getOrderID();
                Cupcake cupcake = null;

                try {
                    cupcake = new Cupcake(CupcakeToppingFacade.getOneToppings(cupcaketoppingId, connectionPool), CupcakeBaseFacade.getOneBase(cupcakebaseId, connectionPool));
                }
                catch(DatabaseException e){
                    request.setAttribute("errormessage", e);
                request.getRequestDispatcher("/error.jsp");
                }


                for (int i = 0; i < quantity; i++) {
                    try {
                        OrderFacade.insertCupcakeForOrder(orderId, cupcake, connectionPool);
                    }
                    catch(DatabaseException e){
                        request.setAttribute("errormessage", e);
                        request.getRequestDispatcher("/error.jsp");
                    }
                }
                try {
                    List<CupcakeBase> cupcakebaseList = CupcakeBaseFacade.getAllBases(connectionPool);
                    List<CupcakeTopping> cupcaketoppingList = CupcakeToppingFacade.getAllToppings(connectionPool);
                    request.setAttribute("cupcakebase", cupcakebaseList);
                    request.setAttribute("cupcaketopping", cupcaketoppingList);
                }catch(DatabaseException e){
                    request.setAttribute("errormessage", e);
                    request.getRequestDispatcher("/error.jsp");
                }
                request.getRequestDispatcher("WEB-INF/welcome.jsp").forward(request, response);
            }

        }
        //  response.setContentType("text/html");
        List<Cupcake> cupcakes = new ArrayList<>();


        int cupcakebaseId = Integer.parseInt(request.getParameter("cupcakebase"));
        int cupcaketoppingId = Integer.parseInt(request.getParameter("cupcaketopping"));
        int quantity = Integer.parseInt(request.getParameter("number"));

        // CupcakeBase cupcakebase = CupcakeBaseMapper.getOneBase(connectionPool);
        // CupcakeTopping cupcakeTopping = CupcakeToppingMapper.getOneTopping(connectionPool);

        Cupcake newCupcake = new Cupcake(cupcakeTopping, cupcakebase);


        for (int i = 0; i < quantity; i++) {
            cupcakes.add(newCupcake);
            String sql = "INSERT INTO cupcake (baseId, toppingId) VALUES (?,?)";
        }


        ShoppingBasket shoppingBasket = new ShoppingBasket(orderId, userId, cupcakes, false, false);
        request.getRequestDispatcher("WEB-INF/welcome.jsp").forward(request, response);

    }
}
