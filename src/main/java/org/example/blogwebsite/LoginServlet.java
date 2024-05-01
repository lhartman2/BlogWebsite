package org.example.blogwebsite;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@WebServlet(name="loginServlet", value="/login")
public class LoginServlet extends HttpServlet {
    public static final Map<String, String> userDB = new Hashtable<>();
    static {
        userDB.put("Lucas", "admin123");
        userDB.put("John", "password123");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getParameter("logout") != null) {
            session.invalidate(); // log us out
            resp.sendRedirect("login");
            return;
        }
        // check if already logged in
         else if (session.getAttribute("username") != null) {
            resp.sendRedirect("blog");
            return;
        }

        // not logged in, go to login page
        req.setAttribute("loginFailed", false);
        req.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // check if already logged in
        if (session.getAttribute("username") != null) {
            resp.sendRedirect("blog");
            return;
        }

        // check login information
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // check for bad/can't login
        if(username == null || password == null || !userDB.containsKey(username) ||
              !password.equals(userDB.get(username))) {
            req.setAttribute("loginFailed", true);
            req.getRequestDispatcher("/WEB-INF/jsp/view/login.jsp").forward(req, resp);
        }
        else { // login successful
            session.setAttribute("username", username);
            req.changeSessionId(); // protects against session fixation attacks
            resp.sendRedirect("blog");
        }


    }
}
