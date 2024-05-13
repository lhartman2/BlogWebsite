package org.example.blogwebsite;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.View;

import java.util.Map;

public class DownloadView implements View {
    private final String filename;
    private final byte[] contents;

    public DownloadView(String filename, byte[] contents) {
        this.filename = filename;
        this.contents = contents;
    }

    @Override
    public String getContentType() {
        return View.super.getContentType();
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "image; filename=" + filename);
        response.setContentType("application/octet-stream");

        ServletOutputStream out = response.getOutputStream();
        out.write(contents);
    }
}
