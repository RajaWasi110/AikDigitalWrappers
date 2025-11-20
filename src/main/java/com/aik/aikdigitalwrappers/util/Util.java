package com.aik.aikdigitalwrappers.util;



import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

@SuppressWarnings("all")
public class Util {


    static Logger logger = Logger.getLogger(String.valueOf(Util.class));



    public static JSONObject getSoapResponseFromDebitWsdlWithProxy(String url1, String xmlInputs) {
        JSONObject jObject = null;
        try {
            String responseString = "";
            StringBuffer outputString = new StringBuffer();

            URL url = new URL(url1);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.16.72.50",3128)); // Replace with actual values
            URLConnection connection = url.openConnection(proxy);
            HttpURLConnection httpConn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();

            String xmlInput = xmlInputs;

            logger.info(xmlInput);
            byte[] buffer;
            buffer = xmlInput.getBytes();
            bout.write(buffer);
            byte[] b = bout.toByteArray();

            // Set the appropriate HTTP parameters.
            httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpConn.setRequestProperty("SOAPAction", "");
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Host", "mb-jsblintegration.ermispk.com");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStream out = httpConn.getOutputStream();
            // Write the content of the request to the outputstream of the HTTP
            // Connection.
            out.write(b);
            out.close();
            // Ready with sending the request.

            // Read the response.
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
            BufferedReader in = new BufferedReader(isr);

            while ((responseString = in.readLine()) != null) {
                outputString.append(responseString);
            }

            jObject = XML.toJSONObject(outputString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jObject;
    }


    public static JSONObject getSoapResponseFromDebitWsdl(String url1, String xmlInputs) {
        JSONObject jObject = null;
        HttpURLConnection httpConn = null;

        try {
            System.out.println("===== SOAP REQUEST STARTED =====");
            System.out.println("Target URL: " + url1);

            // Step 1: Prepare the XML request
            String xmlInput = xmlInputs;
            System.out.println("SOAP Request XML:\n" + xmlInput);

            // Step 2: Open connection
            URL url = new URL(url1);
            URLConnection connection = url.openConnection();
            httpConn = (HttpURLConnection) connection;
            System.out.println("HTTP connection opened successfully.");

            // Step 3: Convert XML input to bytes
            byte[] b = xmlInput.getBytes("UTF-8");
            System.out.println("SOAP request byte size: " + b.length);

            // Step 4: Set HTTP headers
            httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpConn.setRequestProperty("SOAPAction", "");
            httpConn.setRequestProperty("Host", "192.168.130.31");
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            System.out.println("HTTP headers set successfully.");

            // Step 5: Send request
            System.out.println("Sending SOAP request...");
            try (OutputStream out = httpConn.getOutputStream()) {
                out.write(b);
                out.flush();
            }
            System.out.println("SOAP request sent successfully.");

            // Step 6: Read response
            int responseCode = httpConn.getResponseCode();
            String responseMessage = httpConn.getResponseMessage();
            System.out.println("Received HTTP Response: Code = " + responseCode + ", Message = " + responseMessage);

            StringBuilder outputString = new StringBuilder();
            try (InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), "UTF-8");
                 BufferedReader in = new BufferedReader(isr)) {
                String responseLine;
                while ((responseLine = in.readLine()) != null) {
                    outputString.append(responseLine);
                }
            }

            System.out.println("Raw SOAP Response:\n" + outputString);

            // Step 7: Convert XML to JSON
            jObject = XML.toJSONObject(outputString.toString());
            System.out.println("SOAP response successfully converted to JSON.");
            System.out.println("JSON Response:\n" + jObject.toString(2));

            System.out.println("===== SOAP REQUEST COMPLETED SUCCESSFULLY =====");

        } catch (IOException e) {
            System.out.println("I/O Error during SOAP request: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error during SOAP request: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
                System.out.println("HTTP connection closed.");
            }
        }
        return jObject;
    }



}
