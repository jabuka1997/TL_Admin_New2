package com.example.administrator.tl_admin_new;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login_Admin extends Fragment implements View.OnClickListener{


    ProgressDialog dialog;

    Button btnlogin, btnpwvergessen, btnregister;

    String aUsername, aPassword;
    EditText etemail, etpw;

    View view;
    public Login_Admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login__admin, container, false);

        btnlogin = (Button) view.findViewById(R.id.btn_login);
        btnregister = (Button) view.findViewById(R.id.btn_register);
        btnpwvergessen = (Button) view.findViewById(R.id.btn_pwvergessen);
        etemail = (EditText) view.findViewById(R.id.et_email);
        etpw = (EditText) view.findViewById(R.id.et_passwort);


        etpw.setText("");

        btnlogin.setOnClickListener(this);
        btnregister.setOnClickListener(this);
        btnpwvergessen.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                if (!etemail.getText().toString().trim().isEmpty() && !etpw.getText().toString().trim().isEmpty()) {
                    aUsername = etemail.getText().toString().trim();
                    aPassword = etpw.getText().toString().trim();


                    loginUser(aUsername, aPassword);





                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Geben sie einen Username und Password ein!", Toast.LENGTH_LONG).show();
                }
                break;


            case R.id.btn_pwvergessen:

                break;

        }
    }
    private void loginUser(final String username, final String password)
    {
        StringRequest strReg = new StringRequest(Request.Method.POST, Config.URLLOGIN, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {



                String errormsg = "";

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");




                    if (!error)
                    {

                        Toast.makeText(getActivity().getApplicationContext(), "User successfully logged in.", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getView().getContext(), Nav_Drawer_list.class);
                        startActivity(i);

                    }
                    else
                    {
                        errormsg = jObj.getString("error_msg");
                        Toast.makeText(view.getContext(), errormsg, Toast.LENGTH_LONG).show();     //31.01.2017
                    }

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Username", username);
                params.put("Password", password);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(strReg);
    }
}
