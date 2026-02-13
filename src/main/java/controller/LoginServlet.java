package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        /*if(UserDAO.login(userName, password)){
            req.getSession().setAttribute("userName", userName);
            //resp.sendRedirect("chat.jsp");
        } else {
            //resp.sendRedirect("page d'erreur");
        }*/
    }
}
