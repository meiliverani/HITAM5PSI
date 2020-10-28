package com.example.sr_5psi_hitam;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    private Button buttonAdd;
    private Button buttonView;

    private EditText editTextNpm;
    private EditText editTextName;
    private EditText editTextPob;
    private EditText editTextDob;
    private EditText editTextAddress;
    private EditText editTextParent;
    private EditText editTextPhone;

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    Spinner spinnerSchool;
    Spinner spinnerPackage;
    String radioButton;
    String dateOfBirth;

    ArrayList<String> schoolList = new ArrayList<>();
    ArrayList<String> schoolIDList = new ArrayList<>();
    ArrayList<String> packageList = new ArrayList<>();
    ArrayList<String> packageIDList = new ArrayList<>();

    ArrayAdapter<String> schoolAdapter;
    ArrayAdapter<String> packageAdapter;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNpm = (EditText) findViewById(R.id.editTextNpm);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPob = (EditText) findViewById(R.id.editTextPob);
        editTextDob = (EditText) findViewById(R.id.editTextDob);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextParent = (EditText) findViewById(R.id.editTextParent);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);

        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(this);
        spinnerSchool = findViewById(R.id.spinnerSchool);
        spinnerPackage = findViewById(R.id.spinnerPackage);

        editTextDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG,"onDateSet: dd/mm/yyy: "+ dayOfMonth + "/" + month + "/" + year);

                String date = dayOfMonth + "/" + month + "/" + year;
                editTextDob.setText(date);
                dateOfBirth = date;
            }
        };

        String url = konfigurasi.URL_GET_SCHOOL;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("result");
                    for (int i=0; i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String school = jsonObject.optString("name");
                        String schoolID = jsonObject.optString("id");
                        schoolList.add(school);
                        schoolIDList.add(schoolID);
                        schoolAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, schoolList);
                        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSchool.setAdapter(schoolAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        String url1 = konfigurasi.URL_GET_PACKAGE;
                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, url1, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("result");
                                    for (int i=0; i<jsonArray.length();i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String packages = jsonObject.optString("name");
                                        String packageID = jsonObject.optString("id");
                        packageList.add(packages);
                        packageIDList.add(packageID);
                        packageAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, packageList);
                        packageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerPackage.setAdapter(packageAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest1);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    radioButton = "LK";
                break;
            case R.id.radio_female:
                if (checked)
                    radioButton = "PR";
                break;
        }
    }

    private void addStudent(){
        final String npm = editTextNpm.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String sex = radioButton;
        final String pob = editTextPob.getText().toString().trim();
        final String school = spinnerSchool.getSelectedItem().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String parent = editTextParent.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
        final String packages = spinnerPackage.getSelectedItem().toString().trim();

        final String schoolID = schoolIDList.get((int) spinnerSchool.getSelectedItemId()).trim();
        final String packageID = packageIDList.get((int) spinnerPackage.getSelectedItemId()).trim();

        class AddEmployee extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_STUDENT_NPM,npm);
                params.put(konfigurasi.KEY_STUDENT_NAME,name);
                params.put(konfigurasi.KEY_STUDENT_SEX,sex);
                params.put(konfigurasi.KEY_STUDENT_POB,pob);
                params.put(konfigurasi.KEY_STUDENT_DOB,dateOfBirth);
                params.put(konfigurasi.KEY_SCHOOL_ID,schoolID);
                params.put(konfigurasi.KEY_STUDENT_ADR,address);
                params.put(konfigurasi.KEY_STUDENT_PARENT,parent);
                params.put(konfigurasi.KEY_STUDENT_PHONE,phone);
                params.put(konfigurasi.KEY_PACK_ID,packageID);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addStudent();
        }

        if(v == buttonView){
            startActivity(new Intent(this,TampilSemuaSiswa.class));
        }
    }
}