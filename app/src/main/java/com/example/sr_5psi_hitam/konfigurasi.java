package com.example.sr_5psi_hitam;

public class konfigurasi {
    public static final String URL_ADD= "http://192.168.1.8/Android/kursus/tambahSiswa.php";
    public static final String URL_GET_ALL = "http://192.168.1.8/Android/kursus/tampilSemuaSiswa.php";
    public static final String URL_GET_STUD = "http://192.168.1.8/Android/kursus/tampilSiswa.php?id=";
    public static final String URL_UPDATE_STUD = "http://192.168.1.8/Android/kursus/updateSiswa.php";
    public static final String URL_DELETE_STUD = "http://192.168.1.8/Android/kursus/hapusSiswa.php?id=";
    public static final String URL_GET_SCHOOL = "http://192.168.1.8/Android/kursus/getSchools.php";
    public static final String URL_GET_PACKAGE = "http://192.168.1.8/Android/kursus/getPackages.php";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_STUDENT_ID = "id";
    public static final String KEY_STUDENT_NPM = "npm";
    public static final String KEY_STUDENT_NAME = "name";
    public static final String KEY_STUDENT_SEX = "sex";
    public static final String KEY_STUDENT_POB = "pob";
    public static final String KEY_STUDENT_DOB = "dob";
    public static final String KEY_SCHOOL_ID = "school";
    public static final String KEY_STUDENT_ADR = "address";
    public static final String KEY_STUDENT_PARENT = "parent";
    public static final String KEY_STUDENT_PHONE = "phone";
    public static final String KEY_PACK_ID = "package";
    public static final String KEY_STUDENT_PHOTO = "photo";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_STUDENT_ID = "id";
    public static final String TAG_STUDENT_NPM = "npm";
    public static final String TAG_STUDENT_NAME = "name";
    public static final String TAG_STUDENT_SEX = "sex";
    public static final String TAG_STUDENT_POB = "pob";
    public static final String TAG_STUDENT_DOB = "dob";
    public static final String TAG_SCHOOL_ID = "school";
    public static final String TAG_STUDENT_ADR = "address";
    public static final String TAG_STUDENT_PARENT = "parent";
    public static final String TAG_STUDENT_PHONE = "phone";
    public static final String TAG_PACK_ID = "package";
    public static final String TAG_STUDENT_PHOTO = "photo";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String STUDENT_ID = "student_id";
}
