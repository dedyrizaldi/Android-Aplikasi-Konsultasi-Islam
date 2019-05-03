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

public class Daftar extends AppCompatActivity {
	String ip="";
	String kode_pengguna0="";

	EditText txtnama_pengguna;
	EditText txttelepon;
	EditText txtemail;
	EditText txtusername;
	EditText txtpassword;

	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daftar);

		ip=jsonParser.getIP();
		callMarquee();

		txtnama_pengguna= (EditText) findViewById(R.id.txtnama_pengguna);
		txtemail= (EditText) findViewById(R.id.txtemail);
		txttelepon= (EditText) findViewById(R.id.txttelepon);
		txtusername= (EditText) findViewById(R.id.txtusername);
		txtpassword= (EditText) findViewById(R.id.txtpassword);

		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);


		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String lnama_pengguna= txtnama_pengguna.getText().toString();
				String lemail= txtemail.getText().toString();
				String ltelepon= txttelepon.getText().toString();
				String lusername= txtusername.getText().toString();
				String lpassword= txtpassword.getText().toString();

				if(lnama_pengguna.length()<1){lengkapi("nama_pengguna");}
				else if(lemail.length()<1){lengkapi("email");}
				else if(ltelepon.length()<1){lengkapi("telepon");}
				else if(lusername.length()<1){lengkapi("username");}
				else if(lpassword.length()<1){lengkapi("password");}
				else{
						new save().execute();
					}


			}});

		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}});
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


	class save extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Daftar.this);
			pDialog.setMessage("Menyimpan data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			String lnama_pengguna= txtnama_pengguna.getText().toString();
			String lemail= txtemail.getText().toString();
			String ltelepon= txttelepon.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("nama_pengguna", lnama_pengguna));
			params.add(new BasicNameValuePair("email", lemail));
			params.add(new BasicNameValuePair("telepon", ltelepon));
			params.add(new BasicNameValuePair("username", lusername));
			params.add(new BasicNameValuePair("password", lpassword));

			String url=ip+"pengguna/pengguna_add.php";
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
