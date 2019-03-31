package devfun.bookstore.common.domain;

import java.util.Date;

public class Book {
    private Long id;
    private String title;
    private String creator;
    private String type;
    private Date date;

    public Book(){

    }

    public Book(Long id, String title, String creator, String type, Date date) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.type = type;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
