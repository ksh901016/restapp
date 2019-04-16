package devfun.bookstore.rest.domain;

import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

public class BookResource extends ResourceSupport {
    // 비슷한 클래스가 나눠져 있는게 불편해 보이지만, 나눠서 개발하는 것을 권장
    // 차후 확장성을 위해서는 불편함을 감수할 수 있음
    private Long boookId;
    private String title;
    private String creator;
    private String type;
    private Date date;

    public Long getBoookId() {
        return boookId;
    }

    public void setBoookId(Long boookId) {
        this.boookId = boookId;
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
