package com.example.user.konsultasi;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class List_pertanyaan extends ListActivity {
String ip="";

	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	JSONArray myJSON = null;
	
	ArrayList<HashMap<String, String>> arrayList;
	private static final String TAG_SUKSES = "sukses";
	private static final String TAG_record = "record";
	
	private static final String TAG_kode_pertanyaan = "kode_pertanyaan";
	private static final String TAG_kode_pengguna = "kode_pengguna";
	private static final String TAG_pertanyaan = "pertanyaan";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_list);
		arrayList = new ArrayList<HashMap<String, String>>();
		ip=jParser.getIP();
			
		new load().execute();
		
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				String pk = ((TextView) view.findViewById(R.id.kode_k)).getText().toString();
				Intent i = new Intent(getApplicationContext(), Detail_pertanyaan.class);
				i.putExtra("pk", pk);
				startActivityForResult(i, 100);
			}});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 100) {// jika result code 100
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}
	}

	class load extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(List_pertanyaan.this);
			pDialog.setMessage("Load data. Silahkan Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(ip+"pertanyaan/pertanyaan_show.php", "GET", params);
			Log.d("show: ", json.toString());
			try {
				int sukses = json.getInt(TAG_SUKSES);
				if (sukses == 1) {
					myJSON = json.getJSONArray(TAG_record);
					for (int i = 0; i < myJSON.length(); i++) {
						JSONObject c = myJSON.getJSONObject(i);
						String kode_pertanyaan= c.getString(TAG_kode_pertanyaan);
						String kode_pengguna = c.getString("nama_pengguna");
						String pertanyaan = c.getString(TAG_pertanyaan)+"\nTgl :"+ c.getString("tanggal")+" - :"+ c.getString("jam");
						
						HashMap<String, String> map = new HashMap<String, String>();
							map.put(TAG_kode_pertanyaan, kode_pertanyaan);
							map.put(TAG_kode_pengguna, kode_pengguna);
							map.put(TAG_pertanyaan, pertanyaan);
						
						arrayList.add(map);
					}
				} else {
//					Intent i = new Intent(getApplicationContext(),Detail_pertanyaan.class);
//					i.putExtra("pk", "");
//					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					startActivity(i);
				}
			} 
			catch (JSONException e) {e.printStackTrace();}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					ListAdapter adapter = new SimpleAdapter(List_pertanyaan.this, arrayList,R.layout.desain_list, new String[] { TAG_kode_pertanyaan,TAG_kode_pengguna, TAG_pertanyaan,},new int[] { R.id.kode_k, R.id.txtNamalkp ,R.id.txtDeskripsilkp});
					setListAdapter(adapter);
				}
			});}
	}
	
//	public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, 1, 0, "Add New").setIcon(R.drawable.add);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//        case 1:
//        	Intent i = new Intent(getApplicationContext(), Detail_pertanyaan.class);
//			i.putExtra("pk", "");
//			startActivityForResult(i, 100);
//            return true;
//        }
//        return false;
//    }

    
public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		finish();
		return true;
		}
		return super.onKeyDown(keyCode, event);
		}   
	
}
