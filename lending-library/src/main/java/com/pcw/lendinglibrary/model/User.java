package com.pcw.lendinglibrary.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(name = "USERNAME", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", length = 500, nullable = false, unique = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "BORROWED_BOOK_ID", unique = true, nullable = true)
    private Book borrowedBook;

    public User() {}

    public User(Long id, String username, String password, Book borrowedBook) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.borrowedBook = borrowedBook;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Book getBorrowedBook() {
        return borrowedBook;
    }

    public void setBorrowedBook(Book borrowedBook) {
        this.borrowedBook = borrowedBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!username.equals(user.username)) return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
