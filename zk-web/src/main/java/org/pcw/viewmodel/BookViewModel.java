package org.pcw.viewmodel;//package org.pcw.viewmodel;

//import org.zkoss.bind.annotation.Command;
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.select.SelectorComposer;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
///**
// * WORKS BUT DOES NOT BORROW OR RETURN
// */
//public class BookViewModel extends SelectorComposer<Component> {
//
//    private String bookTitle;
//    private String username;
//
//    public BookViewModel() {
//    }
//
//
//    // Constructor to inject the LoginViewModel
//    public BookViewModel(LoginViewModel loginViewModel) {
//        this.username = loginViewModel.getUsername();
//    }
//
//    @Command
//    public void borrowBook() {
//        try {
//            URL url = new URL("http://localhost:8080/api/user/borrow/" + username + "/" + bookTitle);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.getResponseCode();
//            connection.disconnect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Command
//    public void returnBook() {
//        try {
//            URL url = new URL("http://localhost:8080/api/user/return/" + username + "/" + bookTitle);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.getResponseCode();
//            connection.disconnect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Getter and Setter for bookTitle
//
//    public void setBookTitle(String bookTitle) {
//        this.bookTitle = bookTitle;
//    }
//
//    public String getBookTitle() {
//        return bookTitle;
//    }
//
//}


import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookViewModel extends SelectorComposer<Component> {

    private String bookTitle;
    private String username;

    @Wire("#bookTitleLabel")
    private Label bookTitleLabel;

//    @Init
//    public void init(@ContextParam(ContextType.PAGE) Page page) {
//        // Retrieve the book title from the label component
//        if (bookTitleLabel != null) {
//            bookTitle = bookTitleLabel.getValue();
//        }
//    }

    @Init
    public void init() {
        // Retrieve the book title from the query parameter
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
        bookTitle = request.getParameter("title");
    }

    @Command
    public void borrowBook() {
        try {
            URL url = new URL("http://localhost:8080/api/user/borrow/" + username + "/" + bookTitle);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.getResponseCode();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Command
    public void returnBook() {
        try {
            URL url = new URL("http://localhost:8080/api/user/return/" + username + "/" + bookTitle);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.getResponseCode();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter and Setter for bookTitle and username

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}













