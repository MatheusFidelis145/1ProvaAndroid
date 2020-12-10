package com.example.prova;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewOnClickListener {

    private RecyclerView recyclerView;
    private AdpterListaCidades adapter;
    private List<Cidade> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!list.isEmpty())
            return;

        String url ="https://jsonplaceholder.typicode.com/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);

                                Cidade cidade = new Cidade();

                                //System.out.println(object.getJSONObject("address").getString("city").toString());

                                cidade.setPhrase((object.getJSONObject("company").getString("catchPhrase").toString()));

                                System.out.println(object.getString("name"));

                                cidade.setNome(object.getString("name"));

                                cidade.setLatitude(object.getJSONObject("address").getJSONObject("geo").getString("lat").toString());

                                cidade.setLongetude(object.getJSONObject("address").getJSONObject("geo").getString("lng").toString());

                                list.add(cidade);

                                onResume();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Erro " + error.getMessage(),Toast.LENGTH_LONG);

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerCidades);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplication());

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new AdpterListaCidades(list,getLayoutInflater());

        adapter.setRecyclerOnClickListener(MainActivity.this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    public void onClickListener(View view, int position) {

        System.out.println("latitude: " + list.get(position).getLatitude());

        System.out.println("longi: " + list.get(position).getLongetude());

        Intent intent = new Intent(MainActivity.this,MapsActivity.class);
        intent.putExtra("Lat",list.get(position).getLatitude());
        intent.putExtra("Lon",list.get(position).getLongetude());
        intent.putExtra("Nome",list.get(position).getNome());
        startActivity(intent);

    }

}