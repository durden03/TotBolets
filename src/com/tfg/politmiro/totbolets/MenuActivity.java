package com.tfg.politmiro.totbolets;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MenuActivity extends TabActivity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		setTabs() ;
	}
	
	//Creo les pestanyes del menu
	private void setTabs(){
		addTab("TotBolet", R.drawable.tab_totbolet, Tab1ActivityStack.class);
		addTab("Cerca", R.drawable.tab_cerca, Tab2ActivityStack.class);
		addTab("Joc", R.drawable.tab_joc, Tab2ActivityStack.class);		
	}
	
	private void addTab(String labelId, int drawableId, Class<?> c){
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);	

		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		title.setTextSize(9.f);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);

		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
}
