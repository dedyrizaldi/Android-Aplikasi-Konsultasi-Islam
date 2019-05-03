package com.example.user.konsultasi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Pertanyaan extends AppCompatActivity {
	String ip="";
	String kode_pertanyaan0="";

	
	EditText txtpertanyaan;


	Button btntanya;
	Button btncek;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";

	String kode_pengguna, nama_pengguna;
	RadioButton radPublik, radPrivat , radKirim, radDraft;
	String status="Publik", status_tanya="Kirim";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_pertanyaan);

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Pertanyaan.this);
		Boolean Registered = sharedPref.getBoolean("Registered", false);
		if (!Registered){
			finish();
		}else {
			kode_pengguna= sharedPref.getString("kode_pengguna", "");
			nama_pengguna = sharedPref.getString("nama_pengguna", "");
		}

		ip=jsonParser.getIP();
		callMarquee();


		txtpertanyaan= (EditText) findViewById(R.id.txtpertanyaan);

		btntanya= (Button) findViewById(R.id.btntanya);
		btncek = (Button) findViewById(R.id.btncek);
		radPublik=(RadioButton) findViewById(R.id.radPublik);
		radPrivat=(RadioButton) findViewById(R.id.radPrivat);
		radPublik.isChecked();


		radKirim=(RadioButton) findViewById(R.id.radKirim);
		radDraft=(RadioButton) findViewById(R.id.radDraft);
		radKirim.isChecked();

		Intent i = getIntent();
		kode_pertanyaan0 = i.getStringExtra("pk");



		btntanya.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				String lpertanyaan= txtpertanyaan.getText().toString();

				if(radPublik.isChecked()){status="Publik";}
				else {status="Privat";}

				if(radKirim.isChecked()){status_tanya="Kirim";}
				else {status_tanya="Draft";}

				if(lpertanyaan.length()<1){lengkapi("pertanyaan");}
				else{
					new save().execute();
					}
			}});

		btncek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String lcek= txtpertanyaan.getText().toString();
				Intent i = new Intent(Pertanyaan.this,List_cekpertanyaan.class);
				i.putExtra("cek",lcek);
				startActivity(i);
			}});
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	class save extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Pertanyaan.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			String lpertanyaan= txtpertanyaan.getText().toString();
			if(radPublik.isChecked()){status="Publik";}
			else {status="Privat";}

			if(radKirim.isChecked()){status_tanya="Kirim";}
			else {status_tanya="Draft";}

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_pengguna", kode_pengguna));
			params.add(new BasicNameValuePair("pertanyaan", lpertanyaan));
			params.add(new BasicNameValuePair("status", status));
			params.add(new BasicNameValuePair("status_tanya", status_tanya));

			String url=ip+"pertanyaan/pertanyaan_add.php";
			Log.v("add",url);
			JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
			Log.d("add", json.toString());
			try {
				int sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					Intent i = getIntent();
					setResult(100, i);
					finish();
				} else {
					// gagal update data
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {pDialog.dismiss();}
	}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	


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
