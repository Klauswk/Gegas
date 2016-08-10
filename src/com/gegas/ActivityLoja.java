package com.gegas;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import Etc.Heroi;
import Etc.Item;

public class ActivityLoja
extends Activity
{
	private SQLiteDatabase BancoDados = null;
	private String[] ItemsVenda = new String[0];
	private ArrayAdapter<String> adapter;
	private Button btLojaComprar;
	private Cursor cursor;
	private Dialog dialog_compra;
	private Heroi hr;
	private ListView lvLoja_Resultado;
	private Spinner spLoja_Separador;
	private TextView tvLojaValor;

	private boolean buscarDados()
	{
		StringBuilder localStringBuilder = new StringBuilder();
		try
		{
			cursor = BancoDados.query("ListaItems", null, "tradeable = 1", null, null, null, null, null);
			if (cursor.getCount() != 0)
			{
				cursor.moveToFirst();
				for (int i = 0;; i++)
				{
					if (i >= cursor.getCount())
					{
						ItemsVenda = localStringBuilder.toString().split("#");
						return true;
					}
					localStringBuilder.append(cursor.getString(cursor.getColumnIndex("nome")));
					localStringBuilder.append("#");
					cursor.moveToNext();
				}
			}
			Log.i("Lista", "A lista está vazia");
			return false;
		}
		catch (Exception localException)
		{
			Log.i("Erro", "Ocorreu o seguinte erro: " + localException);
		}
		return false;
	}

	public void criaBanco()
	{
		try
		{
			BancoDados = openOrCreateDatabase(android.os.Environment.getExternalStorageDirectory()+"/Gegas/BancoDados.db", 0, null);
			return;
		}
		catch (Exception localException)
		{
			Log.i("Erro", "Não foi possivel inicializar o Banco" + localException);
		}
	}

	protected void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_loja);
		dialog_compra = new Dialog(this);
		dialog_compra.requestWindowFeature(1);
		dialog_compra.setContentView(R.layout.dialog_compra);
		hr = new Heroi(this);
		String[] arrayOfString = { "Todos", "Armas", "Equipamentos", "Espadas", "Machados", "Clavas", "Armaduras", "Capacetes", "Calcas", "Botas", "Escudos" };
		criaBanco();
		buscarDados();
		lvLoja_Resultado = ((ListView)findViewById(R.id.lvLoja_Resultado));

		spLoja_Separador = (Spinner) findViewById(R.id.spLoja_Separador);

		spLoja_Separador.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayOfString));

		btLojaComprar = ((Button) dialog_compra.findViewById(R.id.btComprar));
		tvLojaValor = ((TextView) dialog_compra.findViewById(R.id.tvLojaPreco));

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ItemsVenda);

		lvLoja_Resultado.setAdapter(adapter);

		lvLoja_Resultado.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
			{
				//Log.i("RPG", "Posicao" + paramAnonymousInt);
				cursor = BancoDados.query("ListaItems", null, "nome like '" + ((TextView)paramAnonymousView).getText().toString() + "'", null, null, null, null);
				cursor.moveToFirst();
				tvLojaValor.setText("O preço é: " + cursor.getString(ActivityLoja.this.cursor.getColumnIndex("valor")));
				dialog_compra.show();
			}
		});
		btLojaComprar.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if (hr.getDinheiro() >= Integer.parseInt(cursor.getString(cursor.getColumnIndex("valor"))))
				{
					dialog_compra.dismiss();
					hr.incrementaDinheiro(-Integer.parseInt(cursor.getString(cursor.getColumnIndex("valor"))));
					hr.AdicionarItem(new Item(cursor.getString(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("nome")), Integer.parseInt(cursor.getString(cursor.getColumnIndex("dano"))), Integer.parseInt(cursor.getString(cursor.getColumnIndex("defesa"))), Integer.parseInt(cursor.getString(cursor.getColumnIndex("velocidade"))), Integer.parseInt(cursor.getString(cursor.getColumnIndex("tipo"))), cursor.getString(cursor.getColumnIndex("descricao"))));
					hr.SalvaPersonagem();
				}
			}
		});
	}
	@Override
	protected void onPause()
	{
		super.onPause();
		finish();
	}
}