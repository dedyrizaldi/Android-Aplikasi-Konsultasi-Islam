package com.example.user.konsultasi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Notif extends AppCompatActivity {
	String ip="";
	String kode_pertanyaan0="";

	EditText txttanggal_jawab;
	EditText txtkode_pengguna;
	EditText txtkode_user;
	EditText txttanggal;
	EditText txtstatus_jawab;
	EditText txtstatus_tanya;
	EditText txtpertanyaan;
	EditText txtstatus;
	EditText txtjawaban;


	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_kode_pengguna = "nama_pengguna";
	private static final String TAG_kode_user = "nama_user";
	private static final String TAG_tanggal_jawab = "tanggal_jawab";
	private static final String TAG_jam_jawab = "jam_jawab";
	private static final String TAG_status_jawab = "status_jawab";
	private static final String TAG_status_tanya = "status_tanya";
	private static final String TAG_tanggal = "tanggal";
	private static final String TAG_jam = "jam";
	private static final String TAG_pertanyaan = "pertanyaan";
	private static final String TAG_status = "status";
	private static final String TAG_jawaban = "jawaban";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_outbox);

		ip=jsonParser.getIP();
		callMarquee();

		txttanggal_jawab= (EditText) findViewById(R.id.txttanggal_jawab);txttanggal_jawab.setEnabled(false);
		txtkode_user= (EditText) findViewById(R.id.txtkode_user);txtkode_user.setEnabled(false);
		txtkode_pengguna= (EditText) findViewById(R.id.txtkode_pengguna);txtkode_pengguna.setEnabled(false);
		txttanggal= (EditText) findViewById(R.id.txttanggal);txttanggal.setEnabled(false);
		txtstatus_jawab= (EditText) findViewById(R.id.txtstatus_jawab);txtstatus_jawab.setEnabled(false);
		txtstatus_tanya= (EditText) findViewById(R.id.txtstatus_tanya);txtstatus_tanya.setEnabled(false);
		txtpertanyaan= (EditText) findViewById(R.id.txtpertanyaan);txtpertanyaan.setEnabled(false);
		txtstatus= (EditText) findViewById(R.id.txtstatus);txtstatus.setEnabled(false);
		txtjawaban= (EditText) findViewById(R.id.txtjawaban);txtjawaban.setEnabled(false);

		//btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);

		Intent i = getIntent();
		kode_pertanyaan0 = i.getStringExtra("kode_pertanyaan");


		new get().execute();



		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}});
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Notif.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
			int sukses;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("kode_pertanyaan", kode_pertanyaan0));

				String url=ip+"pertanyaan/pertanyaan_detail.php";
				Log.v("detail",url);
				JSONObject json = jsonParser.makeHttpRequest(url, "GET", params1);
				Log.d("detail", json.toString());
				sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					JSONArray myObj = json.getJSONArray(TAG_record); // JSON Array
					final JSONObject myJSON = myObj.getJSONObject(0);
					runOnUiThread(new Runnable() {
						public void run() {
							try {
								txttanggal_jawab.setText(myJSON.getString(TAG_tanggal_jawab) +"#"+myJSON.getString(TAG_jam_jawab));
								txtkode_pengguna.setText(myJSON.getString(TAG_kode_pengguna));
								txtkode_user.setText(myJSON.getString(TAG_kode_user));
								txttanggal.setText(myJSON.getString(TAG_tanggal) +"#"+myJSON.getString(TAG_jam));
								txtstatus_jawab.setText(myJSON.getString(TAG_status_jawab));
								txtstatus_tanya.setText(myJSON.getString(TAG_status_tanya));
								txtpertanyaan.setText(myJSON.getString(TAG_pertanyaan));
								txtstatus.setText(myJSON.getString(TAG_status));
								txtjawaban.setText(myJSON.getString(TAG_jawaban));
							}
							catch (JSONException e) {e.printStackTrace();}
						}});
				}
				else{
					// jika id tidak ditemukan
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}

	public void lengkapi(String item){
		new AlertDialog.Builder(this)
				.setTitle("Lengkapi Data")
				.setMessage("Silakan lengkapi data "+item +" !")
				.setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {

					}}).show();
	}


	void callMarquee(){
		Calendar cal = Calendar.getInstance();
		int jam = cal.get(Calendar.HOUR);
		int menit= cal.get(Calendar.MINUTE);
		int detik= cal.get(Calendar.SECOND);

		int tgl= cal.get(Calendar.DATE);
		int bln= cal.get(Calendar.MONTH)+1;
		int thn= cal.get(Calendar.YEAR);

		String stgl=String.valueOf(tgl)+"-"+String.valueOf(bln)+"-"+String.valueOf(thn);
		String sjam=String.valueOf(jam)+":"+String.valueOf(menit)+":"+String.valueOf(detik);

		TextView  txtMarquee=(TextView)findViewById(R.id.txtMarquee);
		txtMarquee.setSelected(true);
		String kata="Selamat Datang Aplikasi Android Konsultasi Islam "+stgl+"/"+sjam+" #";
		String kalimat=String.format("%1$s",TextUtils.htmlEncode(kata));
		txtMarquee.setText(Html.fromHtml(kalimat+kalimat+kalimat));
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
