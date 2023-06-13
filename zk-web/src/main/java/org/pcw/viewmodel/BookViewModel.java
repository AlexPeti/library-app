package org.pcw.viewmodel;

import org.json.JSONObject;
import org.pcw.util.GlobalData;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookViewModel extends SelectorComposer<Component> {

    private String bookTitle;
    private String username;

    @Wire("#bookTitleLabel")
    private Label bookTitleLabel;

    @Init
    public void init() {
        // Retrieve the book title
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
        bookTitle = request.getParameter("title");
    }

    @Command
    public void borrowBook() {
        try {
            String username = GlobalData.getUsername();

            URL url = new URL("http://localhost:8080/api/user/borrow");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create the JSON payload
            JSONObject payload = new JSONObject();
            payload.put("username", username);
            payload.put("bookTitle", bookTitle);

            // Write the JSON payload to the connection's output stream
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(payload.toString());
            writer.flush();

            int responseCode = connection.getResponseCode();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Command
    public void returnBook() {
        try {
            String username = GlobalData.getUsername();

            URL url = new URL("http://localhost:8080/api/user/return");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create the JSON payload
            JSONObject payload = new JSONObject();
            payload.put("username", username);
            payload.put("bookTitle", bookTitle);

            // Write the JSON payload to the connection's output stream
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(payload.toString());
            writer.flush();

            int responseCode = connection.getResponseCode();
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