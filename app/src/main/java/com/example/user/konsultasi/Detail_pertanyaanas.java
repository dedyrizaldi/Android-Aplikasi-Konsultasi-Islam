//package com.example.user.konsultasiislam2018;
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.text.Html;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//public class Detail_pertanyaanas extends AppCompatActivity {
//	String ip="";
//	String kode_pertanyaan;
//	String kode_pertanyaan0="";
//
//	EditText txtKode_pertanyaan;
//	EditText txttanggal_jawab;
//	EditText txtkode_pengguna;
//	EditText txtkode_user;
//	EditText txtjam_jawab;
//	EditText txttanggal;
//	EditText txtstatus_jawab;
//	EditText txtstatus_tanya;
//	EditText txtjam;
//	EditText txtpertanyaan;
//	EditText txtstatus;
//	EditText txtjawaban;
//
//
//	Button btnProses;
//	Button btnHapus;
//
//	private ProgressDialog pDialog;
//	JSONParser jsonParser = new JSONParser();
//
//	private static final String TAG_SUKSES = "sukses";
//	private static final String TAG_record = "record";
//
//	private static final String TAG_kode_pengguna = "kode_pengguna";
//	private static final String TAG_kode_user = "kode_user";
//	private static final String TAG_tanggal_jawab = "tanggal_jawab";
//	private static final String TAG_jam_jawab = "jam_jawab";
//	private static final String TAG_status_jawab = "status_jawab";
//	private static final String TAG_status_tanya = "status_tanya";
//	private static final String TAG_tanggal = "tanggal";
//	private static final String TAG_jam = "jam";
//	private static final String TAG_pertanyaan = "pertanyaan";
//	private static final String TAG_status = "status";
//	private static final String TAG_jawaban = "jawaban";
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.detail_pertanyaan);
//
//		ip=jsonParser.getIP();
//		callMarquee();
//
//		txtKode_pertanyaan = (EditText) findViewById(R.id.txtkode_pertanyaan);
//		txttanggal_jawab= (EditText) findViewById(R.id.txttanggal_jawab);
//		txtkode_user= (EditText) findViewById(R.id.txtkode_user);
//		txtkode_pengguna= (EditText) findViewById(R.id.txtkode_pengguna);
//		txtjam_jawab= (EditText) findViewById(R.id.txtjam_jawab);
//		txttanggal= (EditText) findViewById(R.id.txttanggal);
//		txtstatus_jawab= (EditText) findViewById(R.id.txtstatus_jawab);
//		txtstatus_tanya= (EditText) findViewById(R.id.txtstatus_tanya);
//		txtjam= (EditText) findViewById(R.id.txtjam);
//		txtpertanyaan= (EditText) findViewById(R.id.txtpertanyaan);
//		txtstatus= (EditText) findViewById(R.id.txtstatus);
//		txtjawaban= (EditText) findViewById(R.id.txtjawaban);
//
//		btnProses= (Button) findViewById(R.id.btnproses);
//		btnHapus = (Button) findViewById(R.id.btnhapus);
//
//		Intent i = getIntent();
//		kode_pertanyaan0 = i.getStringExtra("pk");
//
//		if(kode_pertanyaan0.length()>0){
//			new get().execute();
//			btnProses.setText("Update Data");
//			btnHapus.setVisibility(View.VISIBLE);
//		}
//		else{
//			btnProses.setText("Add New Data");
//			btnHapus.setVisibility(View.GONE);
//		}
//
//		btnProses.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				kode_pertanyaan= txtKode_pertanyaan.getText().toString();
//				String ltanggal_jawab= txttanggal_jawab.getText().toString();
//				String lkode_user= txtkode_user.getText().toString();
//				String lkode_pengguna= txtkode_pengguna.getText().toString();
//				String ljam_jawab= txtjam_jawab.getText().toString();
//				String ltanggal= txttanggal.getText().toString();
//				String lstatus_jawab= txtstatus_jawab.getText().toString();
//				String lstatus_tanya= txtstatus_tanya.getText().toString();
//				String ljam= txtjam.getText().toString();
//				String lpertanyaan= txtpertanyaan.getText().toString();
//				String lstatus= txtstatus.getText().toString();
//				String ljawaban= txtjawaban.getText().toString();
//
//				if(kode_pertanyaan.length()<1){lengkapi("kode_pertanyaan");}
//				else if(ltanggal_jawab.length()<1){lengkapi("tanggal_jawab");}
//				else if(lkode_user.length()<1){lengkapi("kode_user");}
//				else if(lkode_pengguna.length()<1){lengkapi("kode_pengguna");}
//				else if(ljam_jawab.length()<1){lengkapi("jam_jawab");}
//				else if(ltanggal.length()<1){lengkapi("tanggal");}
//				else if(lstatus_jawab.length()<1){lengkapi("status_jawab");}
//				else if(lstatus_tanya.length()<1){lengkapi("status_tanya");}
//				else if(ljam.length()<1){lengkapi("jam");}
//				else if(lpertanyaan.length()<1){lengkapi("pertanyaan");}
//				else if(lstatus.length()<1){lengkapi("status");}
//				else if(ljawaban.length()<1){lengkapi("jawaban");}
//				else{
//					if(kode_pertanyaan0.length()>0){
//						new update().execute();
//					}
//					else{
//						new save().execute();
//					}
//				}//else
//
//			}});
//
//		btnHapus.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				new del().execute();
//			}});
//	}
//
//	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	class get extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Detail_pertanyaanas.this);
//			pDialog.setMessage("Load data detail. Silahkan tunggu...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... params) {
//			int sukses;
//			try {
//				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
//				params1.add(new BasicNameValuePair("kode_pertanyaan", kode_pertanyaan0));
//
//				String url=ip+"pertanyaan/pertanyaan_detail.php";
//				Log.v("detail",url);
//				JSONObject json = jsonParser.makeHttpRequest(url, "GET", params1);
//				Log.d("detail", json.toString());
//				sukses = json.getInt(TAG_SUKSES);
//				if (sukses == 1) {
//					JSONArray myObj = json.getJSONArray(TAG_record); // JSON Array
//					final JSONObject myJSON = myObj.getJSONObject(0);
//					runOnUiThread(new Runnable() {
//						public void run() {
//							try {
//								txtKode_pertanyaan.setText(kode_pertanyaan0);
//								txttanggal_jawab.setText(myJSON.getString(TAG_tanggal_jawab));
//								txtkode_pengguna.setText(myJSON.getString(TAG_kode_pengguna));
//								txtkode_user.setText(myJSON.getString(TAG_kode_user));
//								txtjam_jawab.setText(myJSON.getString(TAG_jam_jawab));
//								txttanggal.setText(myJSON.getString(TAG_tanggal));
//								txtstatus_jawab.setText(myJSON.getString(TAG_status_jawab));
//								txtstatus_tanya.setText(myJSON.getString(TAG_status_tanya));
//								txtjam.setText(myJSON.getString(TAG_jam));
//								txtpertanyaan.setText(myJSON.getString(TAG_pertanyaan));
//								txtstatus.setText(myJSON.getString(TAG_status));
//								txtjawaban.setText(myJSON.getString(TAG_jawaban));
//							}
//							catch (JSONException e) {e.printStackTrace();}
//						}});
//				}
//				else{
//					// jika id tidak ditemukan
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//		protected void onPostExecute(String file_url) {pDialog.dismiss();}
//	}
//	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//	class save extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Detail_pertanyaanas.this);
//			pDialog.setMessage("Menyimpan data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			kode_pertanyaan = txtKode_pertanyaan.getText().toString();
//			String ltanggal_jawab= txttanggal_jawab.getText().toString();
//			String lkode_user= txtkode_user.getText().toString();
//			String lkode_pengguna= txtkode_pengguna.getText().toString();
//			String ljam_jawab= txtjam_jawab.getText().toString();
//			String ltanggal= txttanggal.getText().toString();
//			String lstatus_jawab= txtstatus_jawab.getText().toString();
//			String lstatus_tanya= txtstatus_tanya.getText().toString();
//			String ljam= txtjam.getText().toString();
//			String lpertanyaan= txtpertanyaan.getText().toString();
//			String lstatus= txtstatus.getText().toString();
//			String ljawaban= txtjawaban.getText().toString();
//
//
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("kode_pertanyaan0", kode_pertanyaan0));
//			params.add(new BasicNameValuePair("kode_pertanyaan", kode_pertanyaan));
//			params.add(new BasicNameValuePair("tanggal_jawab", ltanggal_jawab));
//			params.add(new BasicNameValuePair("kode_user", lkode_user));
//			params.add(new BasicNameValuePair("kode_pengguna", lkode_pengguna));
//			params.add(new BasicNameValuePair("jam_jawab", ljam_jawab));
//			params.add(new BasicNameValuePair("tanggal", ltanggal));
//			params.add(new BasicNameValuePair("status_jawab", lstatus_jawab));
//			params.add(new BasicNameValuePair("status_tanya", lstatus_tanya));
//			params.add(new BasicNameValuePair("jam", ljam));
//			params.add(new BasicNameValuePair("pertanyaan", lpertanyaan));
//			params.add(new BasicNameValuePair("status", lstatus));
//			params.add(new BasicNameValuePair("jawaban", ljawaban));
//
//			String url=ip+"pertanyaan/pertanyaan_add.php";
//			Log.v("add",url);
//			JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
//			Log.d("add", json.toString());
//			try {
//				int sukses = json.getInt(TAG_SUKSES);
//				if (sukses == 1) {
//					Intent i = getIntent();
//					setResult(100, i);
//					finish();
//				} else {
//					// gagal update data
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		protected void onPostExecute(String file_url) {pDialog.dismiss();}
//	}
////++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//	class update extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Detail_pertanyaanas.this);
//			pDialog.setMessage("Mengubah data ...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//		protected String doInBackground(String... args) {
//			kode_pertanyaan = txtKode_pertanyaan.getText().toString();
//			String ltanggal_jawab= txttanggal_jawab.getText().toString();
//			String lkode_user= txtkode_user.getText().toString();
//			String lkode_pengguna= txtkode_pengguna.getText().toString();
//			String ljam_jawab= txtjam_jawab.getText().toString();
//			String ltanggal= txttanggal.getText().toString();
//			String lstatus_jawab= txtstatus_jawab.getText().toString();
//			String lstatus_tanya= txtstatus_tanya.getText().toString();
//			String ljam= txtjam.getText().toString();
//			String lpertanyaan= txtpertanyaan.getText().toString();
//			String lstatus= txtstatus.getText().toString();
//			String ljawaban= txtjawaban.getText().toString();
//
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("kode_pertanyaan0", kode_pertanyaan0));
//			params.add(new BasicNameValuePair("kode_pertanyaan", kode_pertanyaan));
//			params.add(new BasicNameValuePair("tanggal_jawab", ltanggal_jawab));
//			params.add(new BasicNameValuePair("kode_user", lkode_user));
//			params.add(new BasicNameValuePair("kode_pengguna", lkode_pengguna));
//			params.add(new BasicNameValuePair("jam_jawab", ljam_jawab));
//			params.add(new BasicNameValuePair("tanggal", ltanggal));
//			params.add(new BasicNameValuePair("status_jawab", lstatus_jawab));
//			params.add(new BasicNameValuePair("status_tanya", lstatus_tanya));
//			params.add(new BasicNameValuePair("jam", ljam));
//			params.add(new BasicNameValuePair("pertanyaan", lpertanyaan));
//			params.add(new BasicNameValuePair("status", lstatus));
//			params.add(new BasicNameValuePair("jawaban", ljawaban));
//
//
//			String url=ip+"pertanyaan/pertanyaan_update.php";
//			Log.v("update",url);
//			JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
//			Log.d("add", json.toString());
//			try {
//				int sukses = json.getInt(TAG_SUKSES);
//				if (sukses == 1) {
//					Intent i = getIntent();
//					setResult(100, i);
//					finish();
//				} else {
//					// gagal update data
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		protected void onPostExecute(String file_url) {pDialog.dismiss();}
//	}
//	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//	class del extends AsyncTask<String, String, String> {
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pDialog = new ProgressDialog(Detail_pertanyaanas.this);
//			pDialog.setMessage("Menghapus data...");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(true);
//			pDialog.show();
//		}
//
//		protected String doInBackground(String... args) {
//			int sukses;
//			try {
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("kode_pertanyaan", kode_pertanyaan0));
//
//				String url=ip+"pertanyaan/pertanyaan_del.php";
//				Log.v("delete",url);
//				JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);
//				Log.d("delete", json.toString());
//				sukses = json.getInt(TAG_SUKSES);
//				if (sukses == 1) {
//					Intent i = getIntent();
//					setResult(100, i);
//					finish();
//				}
//			}
//			catch (JSONException e) {e.printStackTrace();}
//			return null;
//		}
//
//		protected void onPostExecute(String file_url) {pDialog.dismiss();}
//	}
////++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//	public void lengkapi(String item){
//		new AlertDialog.Builder(this)
//				.setTitle("Lengkapi Data")
//				.setMessage("Silakan lengkapi data "+item +" !")
//				.setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dlg, int sumthin) {
//						finish();
//					}}).show();
//	}
//
//
//	void callMarquee(){
//		Calendar cal = Calendar.getInstance();
//		int jam = cal.get(Calendar.HOUR);
//		int menit= cal.get(Calendar.MINUTE);
//		int detik= cal.get(Calendar.SECOND);
//
//		int tgl= cal.get(Calendar.DATE);
//		int bln= cal.get(Calendar.MONTH);
//		int thn= cal.get(Calendar.YEAR);
//
//		String stgl=String.valueOf(tgl)+"-"+String.valueOf(bln)+"-"+String.valueOf(thn);
//		String sjam=String.valueOf(jam)+":"+String.valueOf(menit)+":"+String.valueOf(detik);
//
//		TextView  txtMarquee=(TextView)findViewById(R.id.txtMarquee);
//		txtMarquee.setSelected(true);
//		String kata="Selamat Datang @lp2maray.com Aplikasi Android  "+stgl+"/"+sjam+" #";
//		String kalimat=String.format("%1$s",TextUtils.htmlEncode(kata));
//		txtMarquee.setText(Html.fromHtml(kalimat+kalimat+kalimat));
//	}
//
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			finish();
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//
//}
