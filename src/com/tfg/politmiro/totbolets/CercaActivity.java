package com.tfg.politmiro.totbolets;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CercaActivity extends Activity implements OnScrollListener {

	private ListView llistaMain;
	private EditText etCercaBolet;
	private TextView txtCercaBolet; //Aixo ho canviare per una imatge currada
	Button btnCerca;
	BoletDbAdapter dbBolet;
	SimpleCursorAdapter boletSimpleAdapter;
	String cerca;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cerca);
		
		txtCercaBolet = (TextView)findViewById(R.id.txtCercaBolet);
		llistaMain = (ListView)findViewById(R.id.lvLlistaBolets);
		etCercaBolet = (EditText)findViewById(R.id.etCercaBolet);
		
		btnCerca = (Button)findViewById(R.id.btnCerca);
		txtCercaBolet.setVisibility(0);
		llistaMain.setVisibility(4);
		
		btnCerca.setOnClickListener(buscaBolet);
		
//		dbBolet = new BoletDbAdapter(getBaseContext());
//		dbBolet.open();
//		Cursor dadesBolet = dbBolet.getAll();
//		if(dadesBolet.getCount() == 0){
//			Toast.makeText(CercaActivity.this,"No hi ha bolets a la base de dades", 4).show();			
//		}else{
//			String[] from = new String[] {BoletDbAdapter.KEY_NOM,BoletDbAdapter.KEY_NOMCIENTIFIC, BoletDbAdapter.KEY_IMGBOLET};
//			int[] to = new int[] {R.id.txtNomBolet,R.id.txtNomCientific,R.id.imgBolet};
//			boletSimpleAdapter = new SimpleCursorAdapter(this.getBaseContext(),R.layout.menullista,dadesBolet,from,to);
//			boletSimpleAdapter.setViewBinder(new MyViewBinder());			
//			llistaMain.setOnItemClickListener(new LlistaMainItemClick());
//			llistaMain.setAdapter(boletSimpleAdapter);			
//		}
//		dbBolet.close();

	}
	private OnClickListener buscaBolet=new OnClickListener() {
		public void onClick(View v) {
			dbBolet = new BoletDbAdapter(getBaseContext());
			dbBolet.open();
			String cercabolet = etCercaBolet.getText().toString();
			Cursor dadesBoletNom = dbBolet.getNomBolet(cercabolet);
			if (dadesBoletNom.getCount() == 0) {
				//El nom normal no coincideix, pero potser l'usuari busca amb nom cientific
				Cursor dadesBoletNomCientific = dbBolet.getNomBoletCientific(cercabolet);
				if (dadesBoletNomCientific.getCount() == 0){
					Toast.makeText(CercaActivity.this,"No hi ha cap bolet ni amb nom ni amb nomcientific", 4).show();
					llistaMain.setVisibility(4);
					txtCercaBolet.setVisibility(0);
				}else{
					String[] from = new String[] {BoletDbAdapter.KEY_NOM,BoletDbAdapter.KEY_NOMCIENTIFIC, BoletDbAdapter.KEY_IMGBOLET};
					int[] to = new int[] {R.id.txtNomBolet,R.id.txtNomCientific,R.id.imgBolet};
					boletSimpleAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.menullista,dadesBoletNomCientific,from,to);
					boletSimpleAdapter.setViewBinder(new MyViewBinder());			
					llistaMain.setOnItemClickListener(new LlistaMainItemClick());
					llistaMain.setAdapter(boletSimpleAdapter);
					llistaMain.setVisibility(0);
					txtCercaBolet.setVisibility(4);
				}				
			}else{
				String[] from = new String[] {BoletDbAdapter.KEY_NOM,BoletDbAdapter.KEY_NOMCIENTIFIC, BoletDbAdapter.KEY_IMGBOLET};
				int[] to = new int[] {R.id.txtNomBolet,R.id.txtNomCientific,R.id.imgBolet};
				boletSimpleAdapter = new SimpleCursorAdapter(getBaseContext(),R.layout.menullista,dadesBoletNom,from,to);
				boletSimpleAdapter.setViewBinder(new MyViewBinder());			
				llistaMain.setOnItemClickListener(new LlistaMainItemClick());
				llistaMain.setAdapter(boletSimpleAdapter);
				llistaMain.setVisibility(0);
				txtCercaBolet.setVisibility(4);
			}
			dbBolet.close();
			
			//Les dos seguents linies de codi, serveixen per amagar el teclat després de fer la cerca
			InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
			inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	};

	

	private class MyViewBinder implements SimpleCursorAdapter.ViewBinder {
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			int viewId = view.getId();
			switch(viewId) {
			case R.id.txtNomBolet:
				TextView txtNomBolet = (TextView) view;
				txtNomBolet.setText(cursor.getString(columnIndex));
				break;
			case R.id.txtNomCientific:
				TextView txtNomCientific = (TextView) view;
				txtNomCientific.setText(cursor.getString(columnIndex));
				break;
			case R.id.imgBolet:
				ImageView img = (ImageView) view;
				String imatge = cursor.getString(columnIndex);				
				//String thePath = Environment.getExternalStorageDirectory().toString();
				InputStream thePath = null;
				try {
					thePath = getAssets().open(imatge);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bitmap b = BitmapFactory.decodeStream(thePath);
				img.setImageBitmap(b);
				img.layout(0, 0, 25, 25);
				break;
			}
			return true;
		}
	}

	private final class LlistaMainItemClick implements OnItemClickListener {
		// @Override
		public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		
		dbBolet = new BoletDbAdapter(getBaseContext());
		dbBolet.open();
		Cursor dadesBolet = dbBolet.getAll();
		dadesBolet.moveToPosition(position);
		String _id = dadesBolet.getString(dadesBolet.getColumnIndex(BoletDbAdapter.KEY_ID));
		String _nom = dadesBolet.getString(dadesBolet.getColumnIndex(BoletDbAdapter.KEY_NOM));
		Log.d("ADebugTag", "Bolet ID: " + _id + " i Nom: " + _nom);
		dbBolet.close();
		Bundle idbolet = new Bundle();
		idbolet.putString("idbolet",_id);
		Bundle nombolet = new Bundle();
		idbolet.putString("nombolet",_nom);
		Intent intent = new Intent(getApplicationContext(), FitxaBoletActivity.class);
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtras(idbolet);
		intent.putExtras(nombolet);
		//startActivity(intent);
		Tab2ActivityStack tab2 = (Tab2ActivityStack) getParent();
		tab2.push("FitxaBoletActivity", intent);
		
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}
}




