package org.example.blogwebsite;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name="blog", value="/blog") // value is the URL
@MultipartConfig(fileSizeThreshold = 5_242_880, maxFileSize = 20_971_520L, maxRequestSize = 41_943_040L)
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
        PrintWriter out = response.getWriter();

        out.println("<html><body><h2>Create New Post</h2>");
        out.println("<form method=\"POST\" action=\"blog\" enctype=\"multipart/form-data\">\n" +
                "        <input type=\"hidden\" name=\"action\" value=\"create\">\n" +
                "        Title:<br>\n" +
                "        <input type=\"text\" name=\"title\"><br><br>\n" +
                "        Body: <br>\n" +
                "        <textarea name=\"body\" rows=\"15\" cols=\"50\"></textarea><br><br>\n" +
                "        Image: <br>\n" +
                "        <input type=\"file\" name=\"file1\"><br><br>\n" +
                "        <input type=\"submit\" value=\"Submit\">\n" +
                "    </form>");

        out.println("</body></html>");
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

    private void createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // create a blog object to add to db
        Blog blog = new Blog();
        blog.setTitle(request.getParameter("title"));
        blog.setDateNow();
        blog.setBody(request.getParameter("body"));

        Part file = request.getPart("file1");
        if (file != null) {
            Image image = processImage(file);
            if (image != null) {
                blog.setImage(image);
            }
        }

        int id;
        synchronized(this) {
            id = this.BLOG_ID++;
            blogDB.put(id, blog);
        }

        //System.out.println(blog);
         response.sendRedirect("blog?action=view&blogId=" + id);
    }

    public Image processImage(Part file) throws IOException {
        InputStream in = file.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // processing the binary data to bytes
        int read;
        final byte[] bytes = new byte[1024];
        while ((read = in.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }

        Image image = new Image();
        image.setName(file.getSubmittedFileName());
        image.setContents(out.toByteArray());

        return image;

    }
}
