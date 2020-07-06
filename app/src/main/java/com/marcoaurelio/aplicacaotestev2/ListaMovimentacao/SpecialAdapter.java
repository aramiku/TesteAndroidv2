package com.marcoaurelio.aplicacaotestev2.ListaMovimentacao;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.marcoaurelio.aplicacaotestev2.R;

import java.util.HashMap;
import java.util.List;

public class SpecialAdapter extends SimpleAdapter {
	private int[] colors = new int[] { 0xFFcfcfcf, 0xFFFFFFFF };
	private Typeface font;
	private int fontcolor;

	public SpecialAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to, int themepass) {
		super(context, items, resource, from, to);
		//font = Typeface.createFromAsset(context.getAssets(), "fonts/macintosh.ttf");
		fontcolor = 0xFF000000;

	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
	  View view = super.getView(position, convertView, parent);
	  TextView numlst = (TextView) view.findViewById(R.id.item1);
	  numlst.setTextColor(fontcolor);
	  numlst.setTypeface(font);
	  TextView cod_acao = (TextView) view.findViewById(R.id.item2);
	  cod_acao.setTypeface(null, Typeface.BOLD);
	  //cod_acao.setTypeface(font);
	  cod_acao.setTextColor(fontcolor);
	  TextView oscilacao = (TextView) view.findViewById(R.id.item3);
	  oscilacao.setTypeface(null, Typeface.ITALIC);
		oscilacao.setTextColor(fontcolor);
	  
	  /*String oscil = oscilacao.getText().toString();
	  oscil = oscil.replace("%", "");
	  oscil.trim();
	  //Log.e("valor oscilacal",oscil.toString());
	  if(!oscil.equals("null")){
		  
		  Float porcent = Float.parseFloat(oscil.toString());
		  if(porcent >= 0){
			  oscilacao.setTextColor(0xFF6D819C);
		  }else{
			  oscilacao.setTextColor(0xFFF5BCA9);
		  }
	  }else{
		  oscilacao.setTextColor(0xFFF3AF93);
	  }*/
	  //oscilacao.setTypeface(font);
	  //oscilacao.setTextColor(fontcolor);
	  TextView valor = (TextView) view.findViewById(R.id.item4);
	  //valor.setTypeface(font);
	  valor.setTextColor(fontcolor);
	  int colorPos = position % colors.length;
	  view.setBackgroundColor(colors[colorPos]);
	  return view;
	}

}
