package com.gegas;

import Etc.Heroi;
import Etc.Item;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityListadeEquipamentos
extends Activity
{
	private SQLiteDatabase BancoDados = null;
	private SQLiteDatabase Database = null;
	private ArrayAdapter<String> adapter;
	private Button btEquipamentos_Equipar;
	private Button btEquipamentos_Vender;
	private Cursor cursor;
	private Dialog dialog;
	private String[] hiraka = new String[0];
	private Heroi hr;
	private ListView lvEquipamentos;
	private TextView tvEquipamentos_Ataque;
	private TextView tvEquipamentos_Defesa;
	private TextView tvEquipamentos_Descricao;
	private TextView tvEquipamentos_Nome;
	private TextView tvEquipamentos_Velocidade;

	@Override
	protected void onPause()
	{
		super.onPause();
		finish();
	}
	
	private void atribui()
	{
		tvEquipamentos_Nome = ((TextView)dialog.findViewById(R.id.tvEquipamentos_Nome));
		tvEquipamentos_Ataque = ((TextView)dialog.findViewById(R.id.tvEquipamentos_Ataque));
		tvEquipamentos_Defesa = ((TextView)dialog.findViewById(R.id.tvEquipamentos_Defesa));
		tvEquipamentos_Velocidade = ((TextView)dialog.findViewById(R.id.tvEquipamentos_Velocidade));
		tvEquipamentos_Descricao = ((TextView)dialog.findViewById(R.id.tvEquipamentos_Descricao));
		btEquipamentos_Equipar = ((Button)dialog.findViewById(R.id.btEquipamentos_Equipar));
		btEquipamentos_Vender = ((Button)dialog.findViewById(R.id.btEquipamentos_Vender));
	}

	private boolean buscarDados()
	{
		StringBuilder localStringBuilder = new StringBuilder();
		try
		{
			cursor = Database.rawQuery("select *, count(id) from Items group by id ", null);
			if (cursor.getCount() != 0)
			{
				cursor.moveToFirst();
				for (int i = 0;; i++)
				{
					if (i >= cursor.getCount())
					{
						//Log.i("RPG", localStringBuilder.toString());
						hiraka = localStringBuilder.toString().split("#");
						adapter = new ArrayAdapter<String>(this, android.R.layout
								.simple_list_item_1, hiraka);
						lvEquipamentos.setAdapter(adapter);
						return true;
					}
					localStringBuilder.append(cursor.getString(cursor.getColumnIndex("nome")));
					localStringBuilder.append(" x");
					localStringBuilder.append(cursor.getString(cursor.getColumnIndex("count(id)")));
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

	public void AdicionarItem(Item paramItem)
	{
		try
		{
			ContentValues localContentValues = new ContentValues();
			localContentValues.put("id", String.valueOf(paramItem.getId()));
			localContentValues.put("nome", String.valueOf(paramItem.getNome()));
			localContentValues.put("dano", String.valueOf(paramItem.getDano()));
			localContentValues.put("defesa", String.valueOf(paramItem.getDefesa()));
			localContentValues.put("velocidade", String.valueOf(paramItem.getVelocidade()));
			localContentValues.put("tipo", String.valueOf(paramItem.getTipo()));
			localContentValues.put("descricao", String.valueOf(paramItem.getDescricao().toString()));
			Database.insert("Items", null, localContentValues);
			Log.i("Tudook", "O Seguinte item foi adicionado a sua mochila " + paramItem.getNome());
			return;
		}
		catch (Exception localException)
		{
			Log.i("Erro", String.valueOf(localException));
		}
	}

	public void DeletaItem(String paramString)
	{
		try
		{
			Database.delete("Items", "pos=" + paramString, null);
			if (!buscarDados())
			{
				finish();
				return;
			}
			buscarDados();
			cursor.moveToFirst();
			return;
		}
		catch (Exception localException)
		{
			Log.i("Erro", String.valueOf(localException));
		}
	}

	public Item PegaItem(String paramString)
	{
		Log.i("RPG", "ID: " + paramString);
		try
		{
			cursor = BancoDados.rawQuery("select * from ListaItems where id like '" + paramString + "'", null);
			cursor.moveToFirst();
			Log.i("RPG", "Encontrou" + cursor.getCount());
			Item localItem = new Item();
			localItem.setId(cursor.getString(cursor.getColumnIndex("id")));
			localItem.setNome(cursor.getString(cursor.getColumnIndex("nome")));
			localItem.setDano(Integer.parseInt(cursor.getString(cursor.getColumnIndex("dano"))));
			localItem.setDefesa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("defesa"))));
			localItem.setVelocidade(Integer.parseInt(cursor.getString(cursor.getColumnIndex("velocidade"))));
			localItem.setTipo(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tipo"))));
			localItem.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
			return localItem;
		}
		catch (Exception localException)
		{
			Log.e("RPG", localException.toString());
		}
		return null;
	}

	public void criaBanco()
	{
		try
		{
			Database = openOrCreateDatabase("RPGSimples.db", 0, null);
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
		requestWindowFeature(1);
		setContentView(R.layout.activity_listaequipamentos);
		hr = new Heroi(this);
		dialog = new Dialog(this);
		dialog.requestWindowFeature(1);
		dialog.setContentView(R.layout.activity_dialogs);
		criaBanco();
		buscarDados();
		atribui();
		lvEquipamentos = ((ListView)findViewById(R.id.lvListaEquipamentos));
		lvEquipamentos.setAdapter(adapter);
		lvEquipamentos.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
			{
				Log.i("RPG", "Posicao" + paramAnonymousInt);
				cursor.moveToFirst();
				int i = 0;
				while(i < paramAnonymousInt)
				{
					cursor.moveToNext();
					i++;
				}
				int j = Integer.parseInt(cursor.getString(cursor.getColumnIndex("tipo")));
				Log.i("RPG", "Num" +j);
				if(j == 10)
				{
					btEquipamentos_Equipar.setVisibility(View.INVISIBLE);
					tvEquipamentos_Ataque.setVisibility(View.INVISIBLE);
					tvEquipamentos_Defesa.setVisibility(View.INVISIBLE);
					tvEquipamentos_Velocidade.setVisibility(View.INVISIBLE);
					tvEquipamentos_Nome.setText("Nome: " + cursor.getString(cursor.getColumnIndex("nome")));
					tvEquipamentos_Descricao.setText(cursor.getString(cursor.getColumnIndex("descricao")));
				}
				else if(j == 9)
				{
					btEquipamentos_Equipar.setVisibility(View.INVISIBLE);
					tvEquipamentos_Nome.setText("Nome: " + cursor.getString(cursor.getColumnIndex("nome")));
					tvEquipamentos_Descricao.setText(cursor.getString(cursor.getColumnIndex("descricao")));
					tvEquipamentos_Ataque.setVisibility(View.INVISIBLE);
					tvEquipamentos_Defesa.setVisibility(View.INVISIBLE);
					tvEquipamentos_Velocidade.setVisibility(View.INVISIBLE);

				}
				else
				{
					btEquipamentos_Equipar.setVisibility(View.VISIBLE);
					tvEquipamentos_Ataque.setVisibility(View.VISIBLE);
					tvEquipamentos_Defesa.setVisibility(View.VISIBLE);
					tvEquipamentos_Velocidade.setVisibility(View.VISIBLE);
					tvEquipamentos_Nome.setText("Nome: " + cursor.getString(cursor.getColumnIndex("nome")));
					tvEquipamentos_Ataque.setText("Dano: " + cursor.getString(cursor.getColumnIndex("dano")));
					tvEquipamentos_Defesa.setText("Defesa: " + cursor.getString(cursor.getColumnIndex("defesa")));
					tvEquipamentos_Velocidade.setText("Velocidade: " + cursor.getString(cursor.getColumnIndex("velocidade")));
					tvEquipamentos_Descricao.setText(cursor.getString(cursor.getColumnIndex("descricao")));
				}



				dialog.show();
			}
		});
		this.btEquipamentos_Vender.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				hr.incrementaDinheiro(20.0D);
				DeletaItem(cursor.getString(cursor.getColumnIndex("pos")));
				dialog.dismiss();
			}
		});
		this.btEquipamentos_Equipar.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				Item localItem1 = new Item();
				localItem1.setId(cursor.getString(cursor.getColumnIndex("id")));
				localItem1.setNome(cursor.getString(cursor.getColumnIndex("nome")));
				localItem1.setDano(Integer.parseInt(cursor.getString(cursor.getColumnIndex("dano"))));
				localItem1.setDefesa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("defesa"))));
				localItem1.setVelocidade(Integer.parseInt(cursor.getString(cursor.getColumnIndex("velocidade"))));
				localItem1.setTipo(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tipo"))));
				localItem1.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
				Item localItem2 = hr.Equipa(localItem1);
				if ((!localItem2.getId().contains("WEAPON0")) && (!localItem2.getId().contains("SHIELD0")) && (!localItem2.getId().contains("ARMOR0")) && (!localItem2.getId().contains("BOOTS0")) && (!localItem2.getId().contains("LEGS0")) && (!localItem2.getId().contains("HELM0"))) {
					AdicionarItem(localItem2);
				}
				DeletaItem(cursor.getString(cursor.getColumnIndex("pos")));
				dialog.dismiss();
			}
		});
	}

	protected void onDestroy()
	{
		super.onDestroy();
		hr.SalvaPersonagem();
	}
}