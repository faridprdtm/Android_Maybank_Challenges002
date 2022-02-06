package com.farid.challenges002;

public class Konfigurasi {
    //url dimana web api berada
    public  static final String URL_GET_ALL = "http://192.168.145.127/nasabah/tampilSemuaNasabah.php";
    public static final  String URL_GET_DETAIL = "http://192.168.145.127/nasabah/tampilNasabah.php?id=";
//    public static final String URL_ADD ="http://192.168.145.127t/pegawai/tambahPgw.php";
//    public static final String URL_UPDATE="http://192.168.145.127/pegawai/updatePgw.php";
//    public static final String URL_DELETE="http://192.168.145.127/pegawai/hapusPgw.php";

    //key and value JSON yang muncul di browser
    public static final String KEY_NASABAH_ID="id";
    public static final String KEY_NASABAH_NAMA="nama";
    public static final String KEY_NASABAH_JENIS_KELAMIN="jenis_kelamin";
    public static final String KEY_NASABAH_UMUR="umur";
    public static final String KEY_NASABAH_PEKERJAAN= "pekerjaan";

    //flag JSON
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_JSON_ID="id";
    public static final String TAG_JSON_NAMA="nama";
    public static final String TAG_JSON_JENIS_KELAMIN="jenis_kelamin";
    public static final String TAG_JSON_UMUR="umur";
    public static final String TAG_JSON_PEKERJAAN="pekerjaan";
    //variable ID pegawai
    public static final String NASABAH_ID="nasabah_id";
}
