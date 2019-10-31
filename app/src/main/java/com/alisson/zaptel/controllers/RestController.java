package com.alisson.zaptel.controllers;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alisson.zaptel.MainActivity;
import com.alisson.zaptel.R;
import com.alisson.zaptel.fragments.ContactsFragment;
import com.alisson.zaptel.models.User;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RestController {
    String API_BASE_URL = "http://178.128.7.94:3001/api/users/";

    public void loginUser(final Context context, final FragmentManager fragmentManager, String email, String password) {
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
                            MainActivity.tokenId = response.getString("id");
                            MainActivity.userId = response.getInt("userId");
                            ContactsFragment contactsFragment = new ContactsFragment();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container, contactsFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void createUser(final Context context, User user) {
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
                        Toast.makeText(context, "User create successfully", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public String getUser(final Context context){
        final User user = new User();
        final String url = API_BASE_URL + MainActivity.userId + "?access_token=" + MainActivity.tokenId;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            user.setName(response.getString("name"));
                            user.setPhone(response.getString("phone"));
                            user.setDateOfBirth(response.getString("dateOfBirth"));
                            user.setGender(response.getString("gender"));
                            user.setEmail(response.getString("email"));
                            user.setId(response.getLong("id"));
                            Toast.makeText(context, "a:" + user.getName(), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, "User got successfully", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
        return user.getName();
    }

}
