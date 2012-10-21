package com.tfg.politmiro.totbolets;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class parseXML {

	Context _baseContext;
	int aux = 0;
	public void _parseXml()
	{
		
		String thePath = Environment.getExternalStorageDirectory().toString(); 
		String dataFileBolet = thePath + "/totbolet/bolet.xml";
		
		try {
			SAXParserFactory spf= SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			DataHandler dataHandler = new DataHandler(this._baseContext);

			xr.setContentHandler(dataHandler);
			xr.parse(new InputSource(new FileInputStream(dataFileBolet)));
		}
		catch(ParserConfigurationException pce) {
			Log.e("SAX XML","sax parse error",pce);
		} catch (SAXException se) {
			Log.e("SAX XML","sax error",se);
		} catch (FileNotFoundException fe) {
			Log.e("SAX XML","file not found",fe);
		} catch (IOException ioe) {
			Log.e("SAX XML","sax parse io error",ioe);
		}
	}

	public parseXML(Context baseContext)
	{
		this._baseContext=baseContext;
	}

	@SuppressWarnings("unused") parseXML()
	{

	}
	public class DataHandler extends DefaultHandler { 

		// booleans that check whether it's in a specific tag or not 
		private boolean _inBolet;
		
		private Context _BaseContext;
		
		// this holds the data 
		private Bolet _dataBolet;

		private DataHandler()
		{

		}
		public DataHandler(Context context)
		{
			this._BaseContext=context;
		}
		
		public Bolet getDataBolet(){
			return _dataBolet;
		}
		
		
		@Override 
		public void startDocument() throws SAXException { 
			_dataBolet = new Bolet();
			
		} 

		@Override 
		public void endDocument() throws SAXException { 

		} 

		@Override 
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException { 
			
			if(localName.equals("bolet")){
				_inBolet = true;
				_dataBolet.id = Integer.parseInt(atts.getValue("id"));
				_dataBolet.nom = atts.getValue("nom");
				_dataBolet.nomcientific = atts.getValue("nomcientific");
				_dataBolet.altres = atts.getValue("altres");
				_dataBolet.classe = atts.getValue("classe");
				_dataBolet.habitat = atts.getValue("habitat");
				_dataBolet.gastronomia = atts.getValue("gastronomia");
				_dataBolet.confusio = atts.getValue("confusio");
				_dataBolet.imgbolet = atts.getValue("imgbolet");		
			}
		} 


		@Override 
		public void endElement(String namespaceURI, String localName, String qName) throws SAXException { 
			Log.v("endElement", localName); 

			if(localName.equals("bolet")) { 
				BoletDbAdapter taulabolets = new BoletDbAdapter(this._BaseContext);
				taulabolets.open();
				taulabolets.insert(_dataBolet);
				_inBolet= false;
				taulabolets.close();
			}		} 


		@Override 
		public void characters(char ch[], int start, int length) { 
			//			String chars = new String(ch, start, length); 
			//			chars = chars.trim();

		} 
	} 

}

