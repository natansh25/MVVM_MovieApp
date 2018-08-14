package com.example.natan.movietralierapp1.Network;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.natan.movietralierapp1.Adapter.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by natan on 11/18/2017.
 */

public class NetworkUtils {

    final static String MOVIE_DB_URL = "http://api.themoviedb.org/3/movie/";


    final static String API_KEY = "api_key";

    final static String api_key = "053130b8fdf68ca19c58155b4bd37bdd";


    final static String LANGUAGE = "language";
    final static String language = "en-US";
    final static String SORT_BY = "sort_by";
    final static String INCLUDE_ADULT = "include_adult";
    final static String include_adult = "false";
    final static String INCLUDE_VIDEO = "include_video";
    final static String include_video = "false";
    final static String PAGE = "page";
    final static String page = "1";


    //Fetching the json response

    public static List<Movie> fetchMovieData(URL url) {
        Log.i("xyz", String.valueOf(url));

        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Movie> movies = extractFeaturesFromJson(jsonResponse);
        return movies;

    }


    //Building URL used to query MOVIEDB

    public static URL buildURl(String sort) {
        Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(sort)
                .appendQueryParameter(API_KEY,api_key)
                .build();
        Log.i("NewUrl", String.valueOf(builtUri));
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return url;
    }

    //Method for getting response from Network

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    //Method for json parsing

    private static List<Movie> extractFeaturesFromJson(String movieJson) {

        if (TextUtils.isEmpty((movieJson))) {
            return null;
        }

        //creating empty array list to add the movies
        List<Movie> movie = new ArrayList<>();

        try {


            JSONObject baseJsonResponse = new JSONObject(movieJson);


            JSONArray movieArray = baseJsonResponse.getJSONArray("results");


            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject currentMovie = movieArray.getJSONObject(i);

                String img_path = currentMovie.getString("poster_path");

                String vote_average = currentMovie.getString("vote_average");

                String release_date = currentMovie.getString("release_date");

                String plot = currentMovie.getString("overview");

                String title = currentMovie.getString("title");

                String backImage=currentMovie.getString("backdrop_path");


                Movie movie1 = new Movie(img_path, title, release_date, vote_average, plot,backImage);

                movie.add(movie1);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movie;
    }


}
