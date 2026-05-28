package com.uteq.software.swm2;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class listalumnos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listalumnos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText txtalumnos =findViewById(R.id.txtalumnos);
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "https://reqres.in/api/collections/alumnos/records?project_id=20874",
                null,
                response -> {
                    try {
                        StringBuilder texto = new StringBuilder();
                        JSONArray data = response.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonAlumno = data.getJSONObject(i).getJSONObject("data");
                            texto.append((i+1) + ".- Nombres:   " + jsonAlumno.optString("nombres","") + "\n");
                            texto.append("Correo " + jsonAlumno.optString("correo","")+ "\n");
                            texto.append("Paralelo " + jsonAlumno.optString("paralelo","")+ "\n");
                            texto.append("Periodo " + jsonAlumno.optString("periodoacademico","")+ "\n\n");
                        }
                        txtalumnos.setText(texto.toString());
                    } catch (Exception e) {
                        txtalumnos.setText("Error procesando datos:\n" + e.getMessage());
                    }
                },

                error -> txtalumnos.setText("Error API:\n" + error.toString())

        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("x-api-key", "pub_bf56644bb321c3b811fdd148594b6f596de208551a5d8fc67e5e632f08e013a1");
                return headers;
            }
        };

        queue.add(request);
    }
}


