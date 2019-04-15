package com.yodo.caz.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.HandlerThread;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.HttpsURLConnection;

public final class NetworkAdapter {

    private static final int    TIMEOUT = 3000;
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";


    public static String httpRequest(final String urlString, final String requestType, final JSONObject content, final Map<String, String> headerProperties) {
        String            result     = "";
        InputStream       stream     = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            // Timeout for reading InputStream arbitrarily set to 3000ms.
            connection.setReadTimeout(TIMEOUT);
            // Timeout for connection.connect() arbitrarily set to 3000ms.
            connection.setConnectTimeout(TIMEOUT);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            // For this use case, set HTTP method to GET.
            connection.setRequestMethod(requestType);

            if (headerProperties != null) {
                for (Map.Entry<String, String> entry : headerProperties.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            connection.setDoInput(true);

            if ((requestType.equals("POST") || requestType.equals("PUT")) && content != null) {
                connection.setRequestProperty("Content-Type","application/json"); //<<<<ADD THIS
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(content.toString().getBytes());
                outputStream.close();
            } else if (requestType.equals(GET) || requestType.equals(DELETE)) {
                // Open communications link (network traffic occurs here).
                connection.connect();
            }
            //            publishProgress(DownloadCallback.Progress.CONNECT_SUCCESS);
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                result = Integer.toString(responseCode);
                throw new IOException("HTTP error code: " + responseCode);
            }
            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();
            // publishProgress(DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS, 0);
            if (stream != null) {
                // Converts Stream to String with max length of 500.
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder  sb     = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                result = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }

        }
        return result;
    }
}