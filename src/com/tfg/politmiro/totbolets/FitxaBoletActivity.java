package com.tfg.politmiro.totbolets;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FitxaBoletActivity extends Activity {

	String idBolet, nomBolet, imgBolet, classeBolet, nomCientific, altresNoms, habitat, gastronomia, confusio;
	TextView txtNomBolet, txtClasseBolet, txtNomCientific, txtAltresNoms, txtHabitat, txtGastronomia, txtConfusio;
	ImageView imgFitxaBolet;
	BoletDbAdapter dbBolet;
	
	Button btnEndarrere;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setWindowAnimations(R.anim.slide_left_out);
        //overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        //getWindow().getAttributes().windowAnimations = R.anim.slide_left_in;
        //getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.fitxabolet);
        
        Bundle extras = getIntent().getExtras();

		if(extras !=null)
		{
			idBolet = extras.getString("idbolet");
		}
		int position = Integer.parseInt(idBolet)-1;
		dbBolet = new BoletDbAdapter(getBaseContext());
		dbBolet.open();

		Cursor dadesBolet = dbBolet.getAll();
		dadesBolet.moveToPosition(position);
		
		//Llegim les dades de la base de dades
		nomBolet = dadesBolet.getString(dadesBolet.getColumnIndex(BoletDbAdapter.KEY_NOM));
		imgBolet = dadesBolet.getString(dadesBolet.getColumnIndex(BoletDbAdapter.KEY_IMGBOLET));
		classeBolet = dadesBolet.getString(dadesBolet.getColumnIndex(BoletDbAdapter.KEY_CLASSE));
		nomCientific = dadesBolet.getString(dadesBolet.getColumnIndex(BoletDbAdapter.KEY_NOMCIENTIFIC));
		altresNoms = dadesBolet.getString(dadesBolet.getColumnIndex(BoletDbAdapter.KEY_ALTRES));
		habitat = dadesBolet.getString(dadesBolet.getColumnIndex(BoletDbAdapter.KEY_HABITAT));
		gastronomia = dadesBolet.getString(dadesBolet.getColumnIndex(BoletDbAdapter.KEY_GASTRONOMIA));
		confusio = dadesBolet.getString(dadesBolet.getColumnIndex(BoletDbAdapter.KEY_CONFUSIO));
		
		//Creem els objectes de text
		txtNomBolet = (TextView)findViewById(R.id.txtNomBoletFitxa);
		txtClasseBolet = (TextView)findViewById(R.id.txtClasseBolet);
		txtNomCientific = (TextView)findViewById(R.id.txtNomCientificFitxa);
		txtAltresNoms = (TextView)findViewById(R.id.txtAltresFitxa);
		txtHabitat = (TextView)findViewById(R.id.txtHabitatFitxa);
		txtGastronomia = (TextView)findViewById(R.id.txtGastronomiaFitxa);
		txtConfusio = (TextView)findViewById(R.id.txtConfusioFitxa);
		
		
		//Li assignem als objectes les dades llegides de la BD
		txtNomBolet.setText(nomBolet);
		txtClasseBolet.setText(classeBolet);
		txtNomCientific.setText(nomCientific);
		txtAltresNoms.setText(altresNoms);
		txtHabitat.setText(habitat);
		txtGastronomia.setText(gastronomia);
		txtConfusio.setText(confusio);
		
		
		imgFitxaBolet = (ImageView)findViewById(R.id.imgFitxaBolet);
		InputStream thePath = null;
		try {
			thePath = getAssets().open(imgBolet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bitmap b = BitmapFactory.decodeStream(thePath);
		imgFitxaBolet.setImageBitmap(b);
		
		dbBolet.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prova, menu);
        return true;
    }
}
