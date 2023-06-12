package org.pcw.viewmodel;

import org.zkoss.json.JSONObject;
import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

public class LoginViewModel {

    @Wire
    private Textbox username;
    @Wire
    private Textbox password;

    public void login() {
        String enteredUsername = username.getValue();
        String enteredPassword = password.getValue();

        try {
            // Construct the request URL
            URL url = new URL("http://localhost:8080/api/user/login");

            // Create the connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Construct the JSON data
            JSONObject jsonData = new JSONObject();
            jsonData.put("username", enteredUsername);
            jsonData.put("password", enteredPassword);

            // Send the request
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonData.toString().getBytes());
            outputStream.flush();

            // Get the response
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Login successful
                // TODO: Process the response and handle successful login
                Messagebox.show("Login successful");

                // Redirect to the desired page
                Executions.sendRedirect("/pages/main.zul");
            } else {
                // Login failed
                // TODO: Handle failed login
                Messagebox.show("Login failed");
            }

            // Close the connection
            connection.disconnect();
        } catch (java.net.ConnectException e) {
            // Connection error occurred
            e.printStackTrace();
            Messagebox.show("Failed to connect to the backend server. Please make sure it is running.");
        } catch (Exception e) {
            // Other error occurred
            e.printStackTrace();
            Messagebox.show("An error occurred during login. Please try again later.");
        }
    }
}
