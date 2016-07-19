package me.roneythomas.booklisting;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.roneythomas.booklisting.model.Book;

public class BookSearchTask extends AsyncTask<String, Void, ArrayList<Book>> {

    @Override
    protected ArrayList<Book> doInBackground(String... strings) {
        try {
            return downloadUrl(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Book> downloadUrl(String query) throws IOException {
        try {
            URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=" + query + "&maxResults=5");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            /*The result of the request is validated to account for a bad server response or lack of server response.*/
            /*If there is no book for a given query the server responds with 304*/
            if (conn.getResponseCode() == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer buffer = new StringBuffer();
                while ((inputLine = bufferedReader.readLine()) != null) {
                    buffer.append(inputLine);
                }
                bufferedReader.close();
                return getBooksArrayList(new JSONObject(String.valueOf(buffer)));
            }
            return null;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Book> getBooksArrayList(JSONObject jsonObject) throws JSONException {
        /*We are checking if the server has responded with 0 books or not*/
        if (!jsonObject.getString("totalItems").equals("0")) {
            ArrayList<Book> bookArrayList = new ArrayList<>();
            JSONArray jsonList = jsonObject.getJSONArray("items");
            for (int a = 0; a < jsonList.length(); a++) {
                JSONObject json = jsonList.getJSONObject(a).getJSONObject("volumeInfo");
                String authors = "";
                try {
                    /*Because authors can be null*/
                    authors = json.getJSONArray("authors").toString().replaceAll("\\[|\\]", "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Book book = new Book(json.getString("title"), authors);
                bookArrayList.add(book);
            }
            return bookArrayList;
        }
        return null;
    }
}
