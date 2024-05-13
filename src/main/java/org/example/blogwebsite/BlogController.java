package org.example.blogwebsite;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("blog")
public class BlogController {
    private volatile int BLOG_ID = 1;
    private Map<Integer, Blog> blogDB = new LinkedHashMap<>();


    @GetMapping("create")
    private ModelAndView showPostForm(){
        return new ModelAndView("blogForm", "blog", new BlogForm());
    }

    @GetMapping("view/{blogId}")
    private ModelAndView viewPost(Model model, @PathVariable("blogId")int blogId){
        Blog blog = blogDB.get(blogId);

        if (blog == null) {
            return new ModelAndView(new RedirectView("blog/list", true, false));
        }

        model.addAttribute("blog", blog);
        model.addAttribute("blogId", blogId);

        return new ModelAndView("viewBlog");
    }

    @GetMapping("/{blogId}/image/{image:.+}")
    private View downloadImage(@PathVariable("blogId")int blogId, @PathVariable("image")String name){
        Blog blog = blogDB.get(blogId);

        if (blog == null) {
            return new RedirectView("blog/list", true, false);
        }

        Image image = blog.getImage();
        if (image == null) {
            return new RedirectView("blog/list", true, false);
        }

        return new DownloadView(image.getName(), image.getContents());

    }

    @RequestMapping(value={"list", ""})
    private String listPosts(Model model){
        model.addAttribute("blogDB", blogDB);
        return "listBlogs";
    }

    @PostMapping("create")
    private View createPost(@ModelAttribute("blog")BlogForm form) throws IOException  {
        // create a blog object to add to db
        Blog blog = new Blog();
        blog.setTitle(form.getTitle());
        blog.setDateNow();
        blog.setBody(form.getBody());

        MultipartFile file = form.getImage();
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setContents(file.getBytes());

        if((image.getName() != null && image.getName().length() > 0) ||
                (image.getContents() != null && image.getContents().length > 0)) {
            blog.setImage(image);
        }

        int id;
        synchronized(this) {
            id = this.BLOG_ID++;
            blogDB.put(id, blog);
        }

        //System.out.println(blog);
        return new RedirectView("view/" + id, true, false);
    }

    public static class BlogForm {
        private String title;
        private String body;
        private LocalDate date;
        private MultipartFile image;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate() {
            this.date = LocalDate.now();
        }

        public MultipartFile getImage() {
            return image;
        }

        public void setImage(MultipartFile image) {
            this.image = image;
        }
    }
}
