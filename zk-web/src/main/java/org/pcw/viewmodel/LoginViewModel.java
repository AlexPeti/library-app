package org.pcw.viewmodel;

import org.pcw.util.GlobalData;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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
        try {
            // Create the URL for the login endpoint
            URL url = new URL("http://localhost:8080/api/user/login");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

            connection.getOutputStream().write(jsonPayload.getBytes());
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                GlobalData.setUsername(username);
                Executions.sendRedirect("/pages/main.zul");
            } else {
                System.out.println("Login failed. Invalid credentials.");
            }
            connection.disconnect();
        } catch (IOException e) {
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