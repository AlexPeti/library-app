package com.pcw.lendinglibrary.dto;

public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private UserDTO borrower;

    public BookDTO() {}

    public BookDTO(Long id, String title, String author, UserDTO borrower) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.borrower = borrower;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public UserDTO getBorrower() {
        return borrower;
    }

    public void setBorrower(UserDTO borrower) {
        this.borrower = borrower;
    }
}
