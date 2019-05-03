package com.example.user.konsultasi;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Profil extends AppCompatActivity {
	String ip="";
	String kode_pengguna0="";

	EditText txtnama_pengguna;
	EditText txttelepon;
	EditText txtemail;
	EditText txtstatus;
	EditText txtusername;
	EditText txtpassword;

	String gambar;
	Button btnProses;
	Button btnHapus;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";

	private static final String TAG_nama_pengguna = "nama_pengguna";
	private static final String TAG_telepon = "telepon";
	private static final String TAG_email = "email";
	private static final String TAG_status = "status";
	private static final String TAG_gambar = "gambar";
	private static final String TAG_username = "username";
	private static final String TAG_password = "password";

	private static final int MY_PERMISSION_REQUEST = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil);

		//meminta izin penyimpanan
		if (ContextCompat.checkSelfPermission(Profil.this,
				Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.shouldShowRequestPermissionRationale(Profil.this,
					Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				ActivityCompat.requestPermissions(Profil.this,
						new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
			} else {
				ActivityCompat.requestPermissions(Profil.this,
						new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
			}
		} else  if (ContextCompat.checkSelfPermission(Profil.this,
				Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.shouldShowRequestPermissionRationale(Profil.this,
					Manifest.permission.CAMERA)) {
				ActivityCompat.requestPermissions(Profil.this,
						new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST);
			} else {
				ActivityCompat.requestPermissions(Profil.this,
						new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST);
			}
		} else {
			//DO NOTHING
		}

		ip=jsonParser.getIP();
		callMarquee();

		txtnama_pengguna= (EditText) findViewById(R.id.txtnama_pengguna);
		txtemail= (EditText) findViewById(R.id.txtemail);
		txttelepon= (EditText) findViewById(R.id.txttelepon);
		txtstatus= (EditText) findViewById(R.id.txtstatus);txtstatus.setEnabled(false);
		txtusername= (EditText) findViewById(R.id.txtusername);
		txtpassword= (EditText) findViewById(R.id.txtpassword);

		btnProses= (Button) findViewById(R.id.btnproses);
		btnHapus = (Button) findViewById(R.id.btnhapus);

		Intent i = getIntent();
		kode_pengguna0 = i.getStringExtra("pk");


			new get().execute();

		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String lnama_pengguna= txtnama_pengguna.getText().toString();
				String lemail= txtemail.getText().toString();
				String ltelepon= txttelepon.getText().toString();
				String lstatus= txtstatus.getText().toString();
				String lusername= txtusername.getText().toString();
				String lpassword= txtpassword.getText().toString();

				if(lnama_pengguna.length()<1){lengkapi("nama_pengguna");}
				else if(lemail.length()<1){lengkapi("email");}
				else if(ltelepon.length()<1){lengkapi("telepon");}
				else if(lstatus.length()<1){lengkapi("status");}
				else if(lusername.length()<1){lengkapi("username");}
				else if(lpassword.length()<1){lengkapi("password");}
				else{

						new update().execute();

					}


			}});

		Button btnupload=(Button)findViewById(R.id.btnupload);
		btnupload.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(Profil.this,Upload_Foto.class);
				startActivity(i);
			}});

		btnHapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}});
	}
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			}
			catch (Exception e) {Log.e("Error", e.getMessage());e.printStackTrace();}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result); }
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	class get extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Profil.this);
			pDialog.setMessage("Load data detail. Silahkan tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... params) {
			int sukses;
			try {
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("kode_pengguna", kode_pengguna0));

				String url=ip+"pengguna/pengguna_detail.php";
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
								txtnama_pengguna.setText(myJSON.getString(TAG_nama_pengguna));
								txttelepon.setText(myJSON.getString(TAG_telepon));
								txtemail.setText(myJSON.getString(TAG_email));
								txtstatus.setText(myJSON.getString(TAG_status));
								gambar=myJSON.getString(TAG_gambar);
								txtusername.setText(myJSON.getString(TAG_username));
								txtpassword.setText(myJSON.getString(TAG_password));
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
		protected void onPostExecute(String file_url) {pDialog.dismiss();
			String arUrlFoto=ip+"ypathfile/"+gambar;
			new DownloadImageTask((ImageView) findViewById(R.id.myGambar)).execute(arUrlFoto);
		}
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

	class update extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Profil.this);
			pDialog.setMessage("Mengubah data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			String lnama_pengguna= txtnama_pengguna.getText().toString();
			String lemail= txtemail.getText().toString();
			String ltelepon= txttelepon.getText().toString();
			String lstatus= txtstatus.getText().toString();
			String lusername= txtusername.getText().toString();
			String lpassword= txtpassword.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kode_pengguna", kode_pengguna0));
			params.add(new BasicNameValuePair("nama_pengguna", lnama_pengguna));
			params.add(new BasicNameValuePair("email", lemail));
			params.add(new BasicNameValuePair("telepon", ltelepon));
			params.add(new BasicNameValuePair("status", lstatus));
			params.add(new BasicNameValuePair("username", lusername));
			params.add(new BasicNameValuePair("password", lpassword));


			String url=ip+"pengguna/pengguna_update.php";
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
