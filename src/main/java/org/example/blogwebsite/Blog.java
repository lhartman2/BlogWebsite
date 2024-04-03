package org.example.blogwebsite;

import java.time.LocalDate;

public class Blog {
    private String title;
    private LocalDate date;
    private String body;
    private Image image;

    public Blog() {
        super();
    }

    public Blog(String title, LocalDate date, String body, Image image) {
        this.title = title;
        this.date = date;
        this.body = body;
        this.image = image;
    }

    public Blog(String title,String body, Image image) {
        this.title = title;
        setDateNow();
        this.body = body;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDateNow() {
        date = LocalDate.now();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", body='" + body + '\'' +
                ", image=" + image +
                '}';
    }
}
