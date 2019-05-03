package com.example.user.konsultasi;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Cari extends Activity{
EditText edCari;
ListView listview;
Button btnCari;

String ip="";

private ProgressDialog pDialog;
JSONParser jParser = new JSONParser();
JSONArray myJSON = null;

ArrayList<HashMap<String, String>> arrayList;
private static final String TAG_SUKSES = "sukses";
private static final String TAG_record = "record";
	


private static final String TAG_kode_pertanyaan= "kode_pertanyaan";
private static final String TAG_pertanyaan = "pertanyaan";
private static final String TAG_tanggal= "tanggal";
private static final String TAG_jam= "jam";
private static final String TAG_kode_pengguna = "kode_pengguna";
private static final String TAG_status= "status";
private static final String TAG_jawaban= "jawaban";
private static final String TAG_kode_user= "kode_user";
private static final String TAG_tanggal_jawab= "tanggal_jawab";
	private static final String TAG_jam_jawab= "jam_jawab";
	private static final String TAG_status_jawab= "status_jawab";
	private static final String TAG_status_tanya= "status_tanya";


int jd;
String[]arr_kode_pertanyaan;
String[]arr_pertanyaan;
String[]arr_jam;
String[]arr_tanggal;
String[]arr_kode_pengguna;
String[]arr_status;//..
String[]arr_tanggal_jawab;//..
String[]arr_jawaban;
String[]arr_kode_user;
String[]arr_jam_jawab;
String[]arr_status_jawab;
String[]arr_status_tanya;
int[]arr_gbr;

String[]arr_kode_pertanyaan2;
String[]arr_pertanyaan2;
String[]arr_tanggal2;
String[]arr_jam2;
String[]arr_kode_pengguna2;
String[]arr_tanggal_jawab2;
String[]arr_status2;
String[]arr_jawaban2;
String[]arr_kode_user2;
String[]arr_jam_jawab2;
String[]arr_status_jawab2;
String[]arr_status_tanya2;

int[]arr_gbr2;

String[]arr_kode_pertanyaan3;
String[]arr_pertanyaan3;
String[]arr_tanggal3;
String[]arr_jam3;
String[]arr_kode_pengguna3;
String[]arr_tanggal_jawab3;//
String[]arr_status3;
String[]arr_jawaban3;
String[]arr_kode_user3;
String[]arr_jam_jawab3;
String[]arr_status_jawab3;
String[]arr_status_tanya3;
int[]arr_gbr3;


int textlength = 0;
ArrayList<String> text_sort = new ArrayList<String>();
ArrayList<Integer> image_sort = new ArrayList<Integer>();

public void onCreate(Bundle savedInstanceState){
super.onCreate(savedInstanceState);
setContentView(R.layout.listviewcolorcari);

ip=jParser.getIP(); 

//Intent io = this.getIntent();
//myLati=io.getStringExtra("myLati");


new loads().execute();

btnCari = (Button) findViewById(R.id.btnCari);
edCari = (EditText) findViewById(R.id.edCari);

}


void lanjut(){
	listview = (ListView) findViewById(R.id.listCari);
	listview.setAdapter(new MyCustomAdapter(arr_pertanyaan, arr_gbr));
	listview.setOnItemClickListener(new OnItemClickListener()
		{
		   @Override
		   public void onItemClick(AdapterView<?> a, View v, int p, long id)
		   { 
			   
			   Intent i = new Intent(Cari.this, Detail_pertanyaan.class);
			    i.putExtra("pk", arr_kode_pertanyaan[p]);
				i.putExtra("nama", arr_pertanyaan[p]);
				i.putExtra("jam", arr_jam[p]);
				i.putExtra("kode_pengguna", arr_kode_pengguna[p]);
				i.putExtra("web", arr_status[p]);//--//
				i.putExtra("tanggal_jawab", arr_tanggal_jawab[p]);
				i.putExtra("jawaban", arr_jawaban[p]);
				i.putExtra("kode_user", arr_kode_user[p]);
			   i.putExtra("kode_user", arr_jam_jawab[p]);
			   i.putExtra("kode_user", arr_status_jawab[p]);
			   i.putExtra("kode_user", arr_status_tanya[p]);
				i.putExtra("gambar", arr_gbr[p]);
				startActivity(i);
			    
Toast.makeText(getBaseContext(), "Anda telah memilih no: "+p+"="+ arr_pertanyaan[p], Toast.LENGTH_LONG).show();
		        		}  
		        });

	btnCari.setOnClickListener(new OnClickListener()
	{
		public void onClick(View v){textlength = edCari.getText().length();
		text_sort.clear();
		image_sort.clear();
		String scari=edCari.getText().toString().toLowerCase();

		int ada=0;
		for (int i = 0; i < jd; i++)
		{
			String snama=arr_pertanyaan[i].toLowerCase();
		if (textlength <= arr_pertanyaan[i].length()){
		if (snama.indexOf(scari)>=0)
		{	//huruf yg awalannya sama
			text_sort.add(arr_pertanyaan[i]);
			image_sort.add(arr_gbr[i]);

			arr_kode_pertanyaan2[ada]=arr_kode_pertanyaan[i];
			arr_pertanyaan2[ada]=arr_pertanyaan[i];
			arr_tanggal2[ada]=arr_tanggal[i];
			arr_jam2[ada]=arr_jam[i];
			arr_kode_pengguna2[ada]=arr_kode_pengguna[i];
			arr_status2[ada]=arr_status[i];//--//
			arr_tanggal_jawab2[ada]=arr_tanggal_jawab[i];//--//
			arr_gbr2[ada]=arr_gbr[i];
			arr_jawaban2[ada]=arr_jawaban[i];
		   	arr_kode_user2[ada]=arr_kode_user[i];
			arr_jam_jawab2[ada]=arr_jam_jawab[i];
			arr_status_tanya2[ada]=arr_status_tanya[i];
			arr_status_jawab2[ada]=arr_status_jawab[i];
		   	
		   	ada=ada+1;
		}
	  }
	}

	arr_kode_pertanyaan3=new String[ada];
	arr_pertanyaan3=new String[ada];
	arr_tanggal3=new String[ada];
	arr_jam3=new String[ada];
	arr_kode_pengguna3=new String[ada];
	arr_status3=new String[ada];//--//
	arr_tanggal_jawab3=new String[ada];//--//
	arr_gbr3=new int[ada];
	arr_jawaban3=new String[ada];
	arr_kode_user3=new String[ada];
	arr_jam_jawab3=new String[ada];
	arr_status_tanya3=new String[ada];
	arr_status_jawab3=new String[ada];


	for (int i = 0; i < ada; i++)
	{
		arr_kode_pertanyaan3[i]=arr_kode_pertanyaan2[i];
		arr_pertanyaan3[i]=arr_pertanyaan2[i];
		arr_tanggal3[i]=arr_tanggal2[i];
		arr_jam3[i]=arr_jam2[i];
		arr_kode_pengguna3[i]=arr_kode_pengguna2[i];
		arr_status3[i]=arr_status2[i];//--//
		arr_tanggal_jawab3[i]=arr_tanggal_jawab2[i];//--//
		arr_gbr3[i]=arr_gbr2[i];
		arr_jawaban3[i]=arr_jawaban2[i];
	   	arr_kode_user3[i]=arr_kode_user2[i];
		arr_jam_jawab3[i]=arr_jam_jawab2[i];
		arr_status_jawab3[i]=arr_status_jawab2[i];
		arr_status_tanya3[i]=arr_status_tanya2[i];

	}

		listview.setAdapter(new MyCustomAdapter(text_sort, image_sort));
		listview.setOnItemClickListener(new OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> a, View v, int p, long id) { 
				   
				   	Intent i = new Intent(Cari.this, Detail_pertanyaan.class);
				   	i.putExtra("pk", arr_kode_pertanyaan3[p]);
					i.putExtra("pertanyaan", arr_pertanyaan3[p]);
				    i.putExtra("tanggal", arr_tanggal3[p]);
				    i.putExtra("jam", arr_jam3[p]);
					i.putExtra("kode_pengguna", arr_kode_pengguna3[p]);
					i.putExtra("web", arr_status3[p]);//---//
					i.putExtra("tanggal_jawab", arr_tanggal_jawab3[p]);//---//
					i.putExtra("jawaban", arr_jawaban3[p]);
					i.putExtra("kode_user", arr_kode_user3[p]);
				    i.putExtra("jam_jawab", arr_jam_jawab3[p]);
				    i.putExtra("status_jawab", arr_status_jawab3[p]);
				    i.putExtra("status_tanya", arr_status_tanya3[p]);
					i.putExtra("gambar", arr_gbr3[p]);
					startActivity(i);
				   
				   
			        		Toast.makeText(getBaseContext(), "Anda telah memilih no "+p+"="+ arr_pertanyaan3[p], Toast.LENGTH_LONG).show();
			        	}  
			        });
		}});
	
}

class MyCustomAdapter extends BaseAdapter
		{
			String[] data_text;
			int[] data_image;
		MyCustomAdapter(){}
		
		MyCustomAdapter(String[] text, int[] image){
			data_text = text;
			data_image = image;
		}
		
		MyCustomAdapter(ArrayList<String> text, ArrayList<Integer> image){
			data_text = new String[text.size()];
			data_image = new int[image.size()];
				for (int i = 0; i < text.size(); i++) {
					data_text[i] = text.get(i);
					data_image[i] = image.get(i);
				}
		}
		
		public int getCount(){return data_text.length;}
		public String getItem(int position){return null;}
		public long getItemId(int position){return position;}
		public View getView(int p, View convertView, ViewGroup parent){
			LayoutInflater inflater = getLayoutInflater();
			View row;
			row = inflater.inflate(R.layout.listviewcolordetail, parent, false);
			TextView textview = (TextView) row.findViewById(R.id.txtCari);
			ImageView imageview = (ImageView) row.findViewById(R.id.imgCari);
			textview.setText(data_text[p]);
			imageview.setImageResource(data_image[p]);
			return (row);
			}
		
	}


class loads extends AsyncTask<String, String, String> {
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(Cari.this);
		pDialog.setMessage("Load data. Silahkan Tunggu...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}
	protected String doInBackground(String... args) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject json = jParser.makeHttpRequest(ip+"pertanyaan/pertanyaan_show_cari.php", "GET", params);
		Log.d("show: ", json.toString());
		try {
			int sukses = json.getInt(TAG_SUKSES);
			if (sukses == 1) {
				myJSON = json.getJSONArray(TAG_record);
				
				jd=myJSON.length();
				arr_kode_pertanyaan=new String[jd];
				arr_pertanyaan=new String[jd];
				arr_jam=new String[jd];
				arr_tanggal=new String[jd];
				arr_kode_pengguna=new String[jd];
				arr_status=new String[jd];
				arr_tanggal_jawab=new String[jd];
				arr_jawaban=new String[jd];
				arr_kode_user=new String[jd];
				arr_jam_jawab=new String[jd];
				arr_status_jawab=new String[jd];
				arr_status_tanya=new String[jd];
				arr_gbr=new int[jd];

				arr_kode_pertanyaan2=new String[jd];
				arr_pertanyaan2=new String[jd];
				arr_tanggal2=new String[jd];
				arr_jam2=new String[jd];
				arr_kode_pengguna2=new String[jd];
				arr_status2=new String[jd];
				arr_tanggal_jawab2=new String[jd];
				arr_jawaban2=new String[jd];
				arr_kode_user2=new String[jd];
				arr_jam_jawab2=new String[jd];
				arr_status_jawab2=new String[jd];
				arr_status_tanya2=new String[jd];
				arr_gbr2=new int[jd];
				
				for (int i = 0; i < jd; i++) {
					JSONObject c = myJSON.getJSONObject(i);
					String kode_pertanyaan= c.getString(TAG_kode_pertanyaan);
					String pertanyaan = c.getString(TAG_pertanyaan);
					String tanggal = c.getString(TAG_tanggal);
					String jam= c.getString(TAG_jam);
					String kode_pengguna= c.getString(TAG_kode_pengguna);
					String status= c.getString(TAG_status);
					String jawaban= c.getString(TAG_jawaban);
					String kode_user= c.getString(TAG_kode_user);
					String tanggal_jawab= c.getString(TAG_tanggal_jawab);
					String jam_jawab= c.getString(TAG_jam_jawab);
					String status_jawab= c.getString(TAG_status_jawab);
					String status_tanya= c.getString(TAG_status_tanya);

					arr_kode_pertanyaan[i]=kode_pertanyaan;
					arr_pertanyaan[i]=pertanyaan;
					arr_jam[i]=jam;
					arr_tanggal[i]=tanggal;
					arr_kode_pengguna[i]=kode_pengguna;
					arr_status[i]=status;
					arr_tanggal_jawab[i]=tanggal_jawab;
					arr_jawaban[i]=jawaban;
					arr_kode_user[i]=kode_user;
					arr_jam_jawab[i]=jam_jawab;
					arr_status_jawab[i]=status_jawab;
					arr_status_tanya[i]=status_tanya;
					arr_gbr[i]=R.drawable.images;

					

				}
			} 
		} 
		catch (JSONException e) {e.printStackTrace();}
		return null;
	}

	protected void onPostExecute(String file_url) {
		pDialog.dismiss();
		runOnUiThread(new Runnable() {
			public void run() {
				lanjut();
			}
		});}
}



}


