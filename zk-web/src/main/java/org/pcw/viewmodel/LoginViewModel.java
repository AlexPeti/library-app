package org.pcw.viewmodel;

import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

//@NotifyCommands({@NotifyCommand(value = "login", onChange = "_self")})
public class LoginViewModel extends SelectorComposer<Component> {

    private String username;
    private String password;

    @Init
    public void init() {
        username = "";
        password = "";
    }

    @Command
    @NotifyChange({"username", "password"})
    public void login() {
        // Perform login logic and communication with backend here
        try {
            // Create the URL for the login endpoint
            URL url = new URL("http://localhost:8080/api/user/login");

            // Create a HttpURLConnection for making the POST request
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Construct the JSON payload for the login request
            String jsonPayload = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

            // Write the JSON payload to the request body
            connection.getOutputStream().write(jsonPayload.getBytes());

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Handle the response based on the response code
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Login successful, redirect to the main.zul page
                Executions.sendRedirect("main.zul");
            } else {
                // Login failed, display an error message or perform appropriate action
                System.out.println("Login failed. Invalid credentials.");
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            // Handle any IO exception that occurs
            e.printStackTrace();
        }
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
}
