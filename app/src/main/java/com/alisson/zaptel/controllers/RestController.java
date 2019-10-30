package com.alisson.zaptel.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.alisson.zaptel.models.User;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RestController {
    String API_BASE_URL = "http://178.128.7.94:3001/api/users/";

    public String loginUser(final Context context, String email, String password) {
        final String[] tokenId = {""};
        String url = API_BASE_URL + "login";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            tokenId[0] = response.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, "opa: " + tokenId[0], Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        tokenId[0] = "";
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
        return tokenId[0];
    }

    public Integer createUser(final Context context, User user) {
        final int[] userId = new int[1];
        String url = API_BASE_URL;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", user.getName());
            jsonObject.put("phone", user.getPhone());
            jsonObject.put("dateOfBirth", user.getDateOfBirth());
            jsonObject.put("gender", user.getGender());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("password", user.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            userId[0] = response.getInt("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, "opa: " + userId[0], Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
        return userId[0];
    }

}
