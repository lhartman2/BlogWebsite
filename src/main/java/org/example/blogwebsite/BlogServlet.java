package org.example.blogwebsite;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name="blog", value="/blog") // value is the URL
public class BlogServlet extends HttpServlet {
    private volatile int BLOG_ID = 1;
    private Map<Integer, Blog> blogDB = new LinkedHashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch(action) {
            case "createBlog" -> showPostForm(request, response);
            case "view" -> viewPost(request, response);
            case "download" -> downloadImage(request, response);
            default -> listPosts(request, response); // all other options
        }
        
    }

    private void showPostForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    }

    private void viewPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    }

    private void downloadImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    }

    private void listPosts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        PrintWriter out = response.getWriter();

        // heading and link to create blog
        out.println("<html><body><h2>Blog Posts</h2>");
        out.println("<a href=\"blog?action=createBlog\">Create Post</a><br><br>");

        // list out the blogs
        if (blogDB.size() == 0) {
            out.println("There are no blog posts yet...<br>");
        }
        else { // loop through and list out
            for (int id : blogDB.keySet()) {
                Blog blog = blogDB.get(id);
                out.print("Blog #" + id);
                out.print(" : <a href=\"blog?action=view&blogId=" + id + "\">");
                out.println(blog.getTitle() + "</a><br>");
            }
        }
        out.println("</body></html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch(action) {
            case "create" -> createPost(request, response);
            default -> response.sendRedirect("blog");
        }
    }

    private void createPost(HttpServletRequest request, HttpServletResponse response) {
    }
}
