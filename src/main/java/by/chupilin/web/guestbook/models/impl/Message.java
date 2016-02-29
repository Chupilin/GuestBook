package by.chupilin.web.guestbook.models.impl;

import by.chupilin.web.guestbook.models.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "message")
public class Message extends Model {

    private static final long serialVersionUID = 6950378700472560874L;

    @Column(name = "author")                // DATATYPE - VARCHAR(255)
    private String author;

    @Column(name = "text",
            columnDefinition = "TEXT")      // DATATYPE - TEXT
    private String text;

    @Column(name = "date_create")
    private Date dateCreate = new Date(System.currentTimeMillis());

    public Message() {
        super();
    }

    public Message(Long id, String author, String text) {
        super(id);
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

}
