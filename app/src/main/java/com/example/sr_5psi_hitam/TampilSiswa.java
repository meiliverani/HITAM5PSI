package com.example.sr_5psi_hitam;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
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
import java.util.HashMap;

public class TampilSiswa extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "TampilSiswa";

    private Button buttonUpdate;
    private Button buttonDelete;

    private String id;
    private String selectedSex;

    private EditText editTextId;
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

    RadioButton radioMale;
    RadioButton radioFemale;

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
        setContentView(R.layout.activity_tampil_siswa);

        Intent intent = getIntent();

        id = intent.getStringExtra(konfigurasi.STUDENT_ID);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextNpm = (EditText) findViewById(R.id.editTextNpm);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPob = (EditText) findViewById(R.id.editTextPob);
        editTextDob = (EditText) findViewById(R.id.editTextDob);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextParent = (EditText) findViewById(R.id.editTextParent);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        radioMale = (RadioButton) findViewById(R.id.radio_male);
        radioFemale = (RadioButton) findViewById(R.id.radio_female);

        requestQueue = Volley.newRequestQueue(this);
        spinnerSchool = findViewById(R.id.spinnerSchool);
        spinnerPackage = findViewById(R.id.spinnerPackage);



        getEmployee();
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSiswa.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_STUD,id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json){

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
                        schoolAdapter = new ArrayAdapter<>(TampilSiswa.this, android.R.layout.simple_spinner_item, schoolList);
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
                        packageAdapter = new ArrayAdapter<>(TampilSiswa.this, android.R.layout.simple_spinner_item, packageList);
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

        editTextDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        TampilSiswa.this,
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

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String npm = c.getString(konfigurasi.TAG_STUDENT_NPM);
            String name = c.getString(konfigurasi.TAG_STUDENT_NAME);
            String sex = c.getString(konfigurasi.TAG_STUDENT_SEX);
            String pob = c.getString(konfigurasi.TAG_STUDENT_POB);
            String dob = c.getString(konfigurasi.TAG_STUDENT_DOB);
            String school = c.getString(konfigurasi.TAG_SCHOOL_ID);
            String address = c.getString(konfigurasi.TAG_STUDENT_ADR);
            String parent = c.getString(konfigurasi.TAG_STUDENT_PARENT);
            String phone = c.getString(konfigurasi.TAG_STUDENT_PHONE);
            String packages = c.getString(konfigurasi.TAG_PACK_ID);

            editTextNpm.setText(npm);
            editTextName.setText(name);
            editTextPob.setText(pob);
            editTextDob.setText(dob);
            editTextAddress.setText(address);
            editTextParent.setText(parent);
            editTextPhone.setText(phone);

            selectedSex = sex;

            radioFemale.setChecked(sex.contains("PR"));
            radioMale.setChecked(sex.contains("LK"));

            spinnerSchool.setSelection(Integer.parseInt(school), true);
            spinnerPackage.setSelection(Integer.parseInt(packages), true);
//            spinnerSchool.setSelection(1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    private void updateEmployee(){
        final String npm = editTextNpm.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String sex = radioButton == null ? selectedSex : radioButton;
        final String pob = editTextPob.getText().toString().trim();
        final String dob = editTextDob.getText().toString().trim();
        final String school = spinnerSchool.getSelectedItem().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String parent = editTextParent.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
        final String packages = spinnerPackage.getSelectedItem().toString().trim();

        final String schoolID = schoolIDList.get((int) spinnerSchool.getSelectedItemId()).trim();
        final String packageID = packageIDList.get((int) spinnerPackage.getSelectedItemId()).trim();

        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSiswa.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilSiswa.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_STUDENT_ID,id);
                hashMap.put(konfigurasi.KEY_STUDENT_NPM,npm);
                hashMap.put(konfigurasi.KEY_STUDENT_NAME,name);
                hashMap.put(konfigurasi.KEY_STUDENT_SEX,sex);
                hashMap.put(konfigurasi.KEY_STUDENT_POB,pob);
                hashMap.put(konfigurasi.KEY_STUDENT_DOB,dob);
                hashMap.put(konfigurasi.KEY_SCHOOL_ID,schoolID);
                hashMap.put(konfigurasi.KEY_STUDENT_ADR,address);
                hashMap.put(konfigurasi.KEY_STUDENT_PARENT,parent);
                hashMap.put(konfigurasi.KEY_STUDENT_PHONE,phone);
                hashMap.put(konfigurasi.KEY_PACK_ID,packageID);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_STUD,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    private void confirmDeleteEmployee(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Data Siswa ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEmployee();
                        startActivity(new Intent(TampilSiswa.this,TampilSemuaSiswa.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteEmployee(){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSiswa.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilSiswa.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_STUD, id);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            updateEmployee();
        }

        if(v == buttonDelete){
            confirmDeleteEmployee();
        }
    }
}