package org.example.blogwebsite;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
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
        request.getRequestDispatcher("WEB-INF/jsp/view/blogForm.jsp").forward(request,response);
    }

    private void viewPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idString = request.getParameter("blogId");
        Blog blog = getBlog(idString, response);

        request.setAttribute("blog", blog);
        request.setAttribute("blogId", idString);

        request.getRequestDispatcher("WEB-INF/jsp/view/viewBlog.jsp").forward(request, response);
    }

    private void downloadImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idString = request.getParameter("blogId");

        Blog blog = getBlog(idString, response);

        String name = request.getParameter("image");
        if (name == null) {
            response.sendRedirect("blog?action=view&blogId=" + idString);
        }

        Image image = blog.getImage();
        if (image == null) {
            response.sendRedirect("blog?action=view&blogId=" + idString);
            return;
        }

        response.setHeader("Content-Disposition", "image; filename=" + image.getName());
        response.setContentType("application/octet-stream");

        ServletOutputStream out = response.getOutputStream();
        out.write(image.getContents());
    }

    private void listPosts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("blogDB", blogDB);
        request.getRequestDispatcher("WEB-INF/jsp/view/listBlogs.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private Blog getBlog(String idString, HttpServletResponse response) throws ServletException, IOException {
        // empty string?
        if (idString == null || idString.length() == 0) {
            response.sendRedirect("blog");
            return null;
        }
        // if not empty try to convert to Integer and get blog to return
        try {
            int id = Integer.parseInt(idString);
            Blog blog = blogDB.get(id);
            if (blog == null) {
                response.sendRedirect("blog");
                return null;
            }

            return blog;
        }
        catch(Exception e ) {
            response.sendRedirect("blog");
            return null;
        }
    }
}
