package testbajajexample2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiTester {

    private static final String API_URL = "https://bfhldevapigw.healthrx.co.in/automation-campus/create/user";
    private static final String ROLL_NUMBER = "240350120080";

    public static void main(String[] args) {
        try {
            
            sendPostRequest("{\"firstName\": \"Test\", \"lastName\": \"Test\", \"phoneNumber\": 9999999999, \"emailId\": \"test.test@test.com\"}");

            
            sendPostRequest("{\"firstName\": \"Test1\", \"lastName\": \"Test1\", \"phoneNumber\": 9999999999, \"emailId\": \"test1.test@test.com\"}");

            
            sendPostRequest("{\"firstName\": \"Test1\", \"lastName\": \"Test1\", \"phoneNumber\": 1111111111, \"emailId\": \"duplicate.test@test.com\"}");

           
            sendPostRequestWithoutRollNumber("{\"firstName\": \"Test\", \"lastName\": \"Test\", \"phoneNumber\": 9999999999, \"emailId\": \"test.test@test.com\"}");

           
            sendPostRequest("{\"firstName\": \"Test\", \"lastName\": \"Test\", \"phoneNumber\": \"not_a_number\", \"emailId\": \"test.test@test.com\"}");

          
            sendPostRequest("{\"firstName\": \"Test\", \"lastName\": \"Test\", \"phoneNumber\": 1234567890, \"emailId\": \"invalid_email\"}");

            
            sendPostRequest("{\"firstName\": \"Test\", \"phoneNumber\": 1234567890}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendPostRequest(String jsonInputString) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("roll-number", ROLL_NUMBER);
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Response Code: " + conn.getResponseCode());
            System.out.println("Response Body: " + response.toString());
        }
    }

    private static void sendPostRequestWithoutRollNumber(String jsonInputString) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Response Code: " + conn.getResponseCode());
            System.out.println("Response Body: " + response.toString());
        }
    }
}
