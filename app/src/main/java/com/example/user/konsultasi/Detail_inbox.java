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
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Detail_inbox extends AppCompatActivity {
	String ip="";
	String kode_pertanyaan0="";

	EditText txttanggal;
	EditText txtjam;
	EditText txtpertanyaan;
	EditText txtjawaban;
	EditText txtkode_pengguna;


	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_kode_pengguna = "nama_pengguna";
	private static final String TAG_tanggal = "tanggal";
	private static final String TAG_jam = "jam";
	private static final String TAG_pertanyaan = "pertanyaan";
	private static final String TAG_jawaban = "jawaban";


	String kode_user="", nama_user="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_inbox);

		ip=jsonParser.getIP();
		callMarquee();

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Detail_inbox.this);
		Boolean Registered = sharedPref.getBoolean("Registered", false);
		if (!Registered){
			finish();
		}else {
			kode_user= sharedPref.getString("kode_user", "");
			nama_user= sharedPref.getString("nama_user", "");
		}

		txtkode_pengguna= (EditText) findViewById(R.id.txtkode_pengguna);txtkode_pengguna.setEnabled(false);
		txttanggal= (EditText) findViewById(R.id.txttanggal);txttanggal.setEnabled(false);
		txtjam= (EditText) findViewById(R.id.txtjam);txtjam.setEnabled(false);
		txtpertanyaan= (EditText) findViewById(R.id.txtpertanyaan);txtpertanyaan.setEnabled(false);
		txtjawaban= (EditText) findViewById(R.id.txtjawaban);

		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);

		Intent i = getIntent();
		kode_pertanyaan0 = i.getStringExtra("pk");


			new get().execute();

		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				String lkode_pengguna= txtkode_pengguna.getText().toString();
				String ltanggal= txttanggal.getText().toString();
				String ljam= txtjam.getText().toString();
				String lpertanyaan= txtpertanyaan.getText().toString();
				String ljawaban= txtjawaban.getText().toString();

				if(lkode_pengguna.length()<1){lengkapi("kode_pengguna");}
				else if(ltanggal.length()<1){lengkapi("tanggal");}
				else if(ljam.length()<1){lengkapi("jam");}
				else if(lpertanyaan.length()<1){lengkapi("pertanyaan");}
				else if(ljawaban.length()<1){lengkapi("jawaban");}
				else{

						new update().execute();
				}//else

			}});

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
			pDialog = new ProgressDialog(Detail_inbox.this);
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

								txtkode_pengguna.setText(myJSON.getString(TAG_kode_pengguna));
								txttanggal.setText(myJSON.getString(TAG_tanggal));
								txtjam.setText(myJSON.getString(TAG_jam));
								txtpertanyaan.setText(myJSON.getString(TAG_pertanyaan));
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
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	


	class update extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Detail_inbox.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {

			String lkode_pengguna= txtkode_pengguna.getText().toString();
			String ltanggal= txttanggal.getText().toString();
			String ljam= txtjam.getText().toString();
			String lpertanyaan= txtpertanyaan.getText().toString();
			String ljawaban= txtjawaban.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_pertanyaan", kode_pertanyaan0));
			params.add(new BasicNameValuePair("kode_user", kode_user));
			params.add(new BasicNameValuePair("kode_pengguna", lkode_pengguna));
			params.add(new BasicNameValuePair("tanggal", ltanggal));
			params.add(new BasicNameValuePair("jam", ljam));
			params.add(new BasicNameValuePair("pertanyaan", lpertanyaan));
			params.add(new BasicNameValuePair("jawaban", ljawaban));


			String url=ip+"pertanyaan/pertanyaan_update_inbox.php";
			Log.v("update",url);
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
		String kata="Selamat Datang Aplikasi Android Konsultasi Islam  "+stgl+"/"+sjam+" #";
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
