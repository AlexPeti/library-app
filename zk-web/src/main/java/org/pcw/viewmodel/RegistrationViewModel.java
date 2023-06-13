package org.pcw.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistrationViewModel {

    private String username;
    private String password;

    @Init
    public void init() {
        username = "";
        password = "";
    }

    @Command
    @NotifyChange({"username", "password"})
    public void register() {
        try {
            // Create the URL for the register endpoint
            URL url = new URL("http://localhost:8080/api/user/register");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create the JSON payload
            String jsonPayload = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

            // Write the JSON payload to the connection's output stream
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(jsonPayload);
            writer.flush();

            int responseCode = connection.getResponseCode();
            connection.disconnect();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Redirect to the login page
                Executions.sendRedirect("/pages/login.zul");
            }
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

