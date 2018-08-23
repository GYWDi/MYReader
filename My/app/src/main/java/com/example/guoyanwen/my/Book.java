package com.example.guoyanwen.my;

import java.sql.Date;

public class Book {
    private String bookname;
    private String publish;
    private String ISBN;
    private Date publishdate;
    private String writer;
    private String id;

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String toString() {
        return "Book [ISBN="+ISBN+",publish="+publish+",writer="+writer+",bookid=" + id + ", bookname=" + bookname + ",publishdate=" + publishdate + "]";
    }
}
