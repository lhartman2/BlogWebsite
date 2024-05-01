package org.example.blogwebsite;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(value={"/blog", "/sessions"})
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)servletRequest).getSession(false);
        // no one logged in?
        if (session == null || session.getAttribute("username") == null) {
            ((HttpServletResponse)servletResponse).sendRedirect("login");
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);// go to the servlet, already logged.
        }
    }

    @Override
    public void destroy() {
    }
}
