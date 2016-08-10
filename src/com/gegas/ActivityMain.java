package com.gegas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import Etc.Heroi;
import Etc.Item;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMain
extends Activity
{
	public static String SQLLISTAITEMS = "CREATE TABLE IF NOT EXISTS ListaItems ( id \t    TEXT NOT NULL,imagem     TEXT , nome       TEXT NOT NULL,dano       TEXT NOT NULL,defesa     TEXT NOT NULL, velocidade TEXT NOT NULL, tipo TEXT NOT NULL,valor TEXT ,descricao  TEXT NOT NULL)";
	private SQLiteDatabase BancoDados = null;
	private Button btBatalhar;
	private Button btCuraCancelar;
	private Button btCuraOk;
	private Button btEquipamentos;
	private Button btStatus;
	private Button btareasCaedes;
	private Button btareasCampo;
	private Button btareasCaverna;
	private Button btareasCidades;
	private Button btareasFloresta;
	private Button btareasGlacies;
	private Button btareasIgnes;
	private Button btareasMasmorra;
	private Button btareasPantano;
	private Button btareasSolum;
	private Button btmain_Craft;
	private Button btmain_Curar;
	private Button btmain_loja;
	private Bundle bundle;
	private Cursor cursor;
	private Dialog dialog;
	private Heroi hr;
	private TextView tvCuraMsg;

	private void CopiarDatabase()
			throws IOException
			{
		try
		{
			InputStream localInputStream = getAssets().open("db/BancoDados.db");
			File file = new File(android.os.Environment.getExternalStorageDirectory()+"/Gegas");
			file.mkdir();

			FileOutputStream localFileOutputStream = new FileOutputStream(android.os.Environment.getExternalStorageDirectory()+"/Gegas/BancoDados.db");

			byte[] buffer = new byte[1024];
			int length;
			while ((length = localInputStream.read(buffer)) > 0) {
				localFileOutputStream.write(buffer, 0, length);
			}
			localFileOutputStream.flush();
			localFileOutputStream.close();
			localInputStream.close();
		}
		catch (Exception localException)
		{
			Log.e("error", localException.toString());
		}
			}

	@Override
	public void onBackPressed() {
		final Dialog dialog = new Dialog(this);
		Button btDialogSair, btdialogCancelar;
		dialog.setContentView(R.layout.dialogsair);
		btDialogSair = (Button) dialog.findViewById(R.id.btOk);
		btdialogCancelar = (Button) dialog.findViewById(R.id.btCancelar);
		dialog.show();
		btdialogCancelar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		btDialogSair.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				finish();
			}
		});
	}
	private void EscolheLocal()
	{
		setContentView(R.layout.activity_areas);
		btareasCidades = ((Button)findViewById(R.id.btareasCidade));
		btareasCampo = ((Button)findViewById(R.id.btareasCampo));
		btareasCaverna = ((Button)findViewById(R.id.btareasCaverna));
		btareasFloresta = ((Button)findViewById(R.id.btareasFloresta));
		btareasCaedes = ((Button)findViewById(R.id.btareasCaedes));
		btareasSolum = ((Button)findViewById(R.id.btareasSolum));
		btareasPantano = ((Button)findViewById(R.id.btareasPantano));
		btareasMasmorra = ((Button)findViewById(R.id.btareasMasmorra));
		btareasIgnes = ((Button)findViewById(R.id.btareasIgnes));
		btareasGlacies = ((Button)findViewById(R.id.btareasGlacies));

		btareasPantano.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				try {
					if(Arrays.asList(getResources().getAssets().list("mapas")).contains("6.txt"))
					{
						bundle.putInt("Local", 6);
						Intent localIntent = new Intent(getBaseContext(), ActivityGame.class);
						localIntent.putExtras(bundle);
						startActivity(localIntent);
						finish();
					}
					else
					{
						Toast.makeText(getBaseContext(), "Mapa ainda não desenvolvido!", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.btareasGlacies.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				try {
					if(Arrays.asList(getResources().getAssets().list("mapas")).contains("7.txt"))
					{
						bundle.putInt("Local", 7);
						Intent localIntent = new Intent(getBaseContext(), ActivityGame.class);
						localIntent.putExtras(bundle);
						startActivity(localIntent);
						finish();
					}
					else
					{
						Toast.makeText(getBaseContext(), "Mapa ainda não desenvolvido!", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.btareasCidades.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{

				try {
					if(Arrays.asList(getResources().getAssets().list("mapas")).contains("1.txt"))
					{
						bundle.putInt("Local", 1);
						Intent localIntent = new Intent(getBaseContext(), ActivityGame.class);
						localIntent.putExtras(bundle);
						startActivity(localIntent);
						finish();
					}
					else
					{
						Toast.makeText(getBaseContext(), "Mapa ainda não desenvolvido!", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.btareasCampo.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				try {
					if(Arrays.asList(getResources().getAssets().list("mapas")).contains("2.txt"))
					{
						bundle.putInt("Local", 2);
						Intent localIntent = new Intent(getBaseContext(), ActivityGame.class);
						localIntent.putExtras(bundle);
						startActivity(localIntent);
						finish();
					}
					else
					{
						Toast.makeText(getBaseContext(), "Mapa ainda não desenvolvido!", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		this.btareasCaverna.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				try {
					if(Arrays.asList(getResources().getAssets().list("mapas")).contains("4.txt"))
					{
						bundle.putInt("Local", 4);
						Intent localIntent = new Intent(getBaseContext(), ActivityGame.class);
						localIntent.putExtras(bundle);
						startActivity(localIntent);
						finish();
					}
					else
					{
						Toast.makeText(getBaseContext(), "Mapa ainda não desenvolvido!", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.btareasFloresta.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				try {
					if(Arrays.asList(getResources().getAssets().list("mapas")).contains("2.txt"))
					{
						bundle.putInt("Local", 3);
						Intent localIntent = new Intent(getBaseContext(), ActivityGame.class);
						localIntent.putExtras(bundle);
						startActivity(localIntent);
						finish();
					}
					else
					{
						Toast.makeText(getBaseContext(), "Mapa ainda não desenvolvido!", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.btareasCaedes.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				try {
					if(Arrays.asList(getResources().getAssets().list("mapas")).contains("10.txt"))
					{
						bundle.putInt("Local", 10);
						Intent localIntent = new Intent(getBaseContext(), ActivityGame.class);
						localIntent.putExtras(bundle);
						startActivity(localIntent);
						finish();
					}
					else
					{
						Toast.makeText(getBaseContext(), "Mapa ainda não desenvolvido!", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.btareasSolum.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				try {
					if(Arrays.asList(getResources().getAssets().list("mapas")).contains("9.txt"))
					{
						bundle.putInt("Local", 9);
						Intent localIntent = new Intent(getBaseContext(), ActivityGame.class);
						localIntent.putExtras(bundle);
						startActivity(localIntent);
						finish();
					}
					else
					{
						Toast.makeText(getBaseContext(), "Mapa ainda não desenvolvido!", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.btareasMasmorra.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				try {
					if(Arrays.asList(getResources().getAssets().list("mapas")).contains("5.txt"))
					{
						bundle.putInt("Local", 5);
						Intent localIntent = new Intent(getBaseContext(), ActivityGame.class);
						localIntent.putExtras(bundle);
						startActivity(localIntent);
						finish();
					}
					else
					{
						Toast.makeText(getBaseContext(), "Mapa ainda não desenvolvido!", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.btareasIgnes.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				try {
					if(Arrays.asList(getResources().getAssets().list("mapas")).contains("8.txt"))
					{
						bundle.putInt("Local", 8);
						Intent localIntent = new Intent(getBaseContext(), ActivityGame.class);
						localIntent.putExtras(bundle);
						startActivity(localIntent);
						finish();
					}
					else
					{
						Toast.makeText(getBaseContext(), "Mapa ainda não desenvolvido!", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void MostraMsgCura(String paramString1, String paramString2)
	{
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle(paramString1);
		localBuilder.setMessage(paramString2);
		localBuilder.setNegativeButton("No", null);
		localBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
			{
				if (ActivityMain.this.hr.getDinheiro() - (ActivityMain.this.hr.getVidaMax() - ActivityMain.this.hr.getVidaAtual()) < 0.0D)
				{
					ActivityMain.this.Msg("", "VocÃª nÃ£o tem dinheiro para isto!");
					return;
				}
				ActivityMain.this.hr.incrementaDinheiro(-(ActivityMain.this.hr.getVidaMax() - ActivityMain.this.hr.getVidaAtual()));
				ActivityMain.this.hr.setVidaAtual(ActivityMain.this.hr.getVidaMax());
				ActivityMain.this.hr.SalvaPersonagem();
			}
		});
		localBuilder.create();
		localBuilder.show();
	}

	public void Msg(String paramString1, String paramString2)
	{
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle(paramString1);
		localBuilder.setMessage(paramString2);
		localBuilder.setNegativeButton("Ok", null);
		localBuilder.create();
		localBuilder.show();
	}

	public Item PegaItem(String paramString)
	{
		try
		{
			this.cursor = this.BancoDados.rawQuery("select * from ListaItems where id like '" + paramString + "'", null);
			this.cursor.moveToFirst();
			Item localItem = new Item();
			localItem.setId(this.cursor.getString(this.cursor.getColumnIndex("id")));
			localItem.setNome(this.cursor.getString(this.cursor.getColumnIndex("nome")));
			localItem.setDano(Integer.parseInt(this.cursor.getString(this.cursor.getColumnIndex("dano"))));
			localItem.setDefesa(Integer.parseInt(this.cursor.getString(this.cursor.getColumnIndex("defesa"))));
			localItem.setVelocidade(Integer.parseInt(this.cursor.getString(this.cursor.getColumnIndex("velocidade"))));
			localItem.setTipo(Integer.parseInt(this.cursor.getString(this.cursor.getColumnIndex("tipo"))));
			localItem.setDescricao(this.cursor.getString(this.cursor.getColumnIndex("descricao")));
			return localItem;
		}
		catch (Exception localException)
		{
			Log.e("RPG", localException.toString());
		}
		return null;
	}

	public void Perguntanome()
	{
		chamaMain();
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle("Bem Vindo!");
		localBuilder.setMessage("Bem vindo ao mundo, digite seu nome");
		localBuilder.setCancelable(false);
		final EditText localEditText = new EditText(this);
		localBuilder.setView(localEditText);
		localBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
			{
				String str = localEditText.getText().toString();
				ActivityMain.this.hr.setNome(str);
				ActivityMain.this.hr.SalvaPersonagem();
			}
		});
		localBuilder.show();
	}

	public void atribui()
	{
		this.btEquipamentos = ((Button)findViewById(R.id.btmain_equipamentos));
		this.btmain_Curar = ((Button)findViewById(R.id.btmain_Curar));
		this.btBatalhar = ((Button)findViewById(R.id.btmain_Batalhar));
		this.btStatus = ((Button)findViewById(R.id.btmain_Status));
		this.btCuraOk = ((Button)this.dialog.findViewById(R.id.btCuraOk));
		this.btCuraCancelar = ((Button)this.dialog.findViewById(R.id.btCuraCancelar));
		this.btmain_loja = ((Button)findViewById(R.id.btmain_loja));
		this.btmain_Craft = ((Button)findViewById(R.id.btmain_Craft));
		this.tvCuraMsg = ((TextView)this.dialog.findViewById(R.id.tvCuraMsg));
	}

	public void chamaMain()
	{
		setContentView(R.layout.activity_main);
		atribui();
		this.btBatalhar.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				EscolheLocal();
			}
		});
		this.btStatus.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				Intent localIntent = new Intent(ActivityMain.this.getBaseContext(), ActivityStatus.class);
				ActivityMain.this.startActivity(localIntent);
			}
		});
		this.btmain_Curar.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				ActivityMain.this.tvCuraMsg.setText("Para curar-se serÃ¡ cobrado uma taxa de: " + (ActivityMain.this.hr.getVidaMax() - ActivityMain.this.hr.getVidaAtual() + ActivityMain.this.hr.getManaMax() - ActivityMain.this.hr.getManaAtual()));
				ActivityMain.this.dialog.show();
			}
		});
		this.btEquipamentos.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				if (!ActivityMain.this.hr.buscarDados())
				{
					ActivityMain.this.Msg("Mochila Vazia", "NÃ£o hÃ¡ equipamentos disponiveis na sua mochila!");
					return;
				}
				Intent localIntent = new Intent(ActivityMain.this.getBaseContext(), ActivityListadeEquipamentos.class);
				ActivityMain.this.startActivity(localIntent);
			}
		});
		this.btCuraOk.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				int i = ActivityMain.this.hr.getVidaMax() - ActivityMain.this.hr.getVidaAtual() + ActivityMain.this.hr.getManaMax() - ActivityMain.this.hr.getManaAtual();
				if (ActivityMain.this.hr.getDinheiro() - i < 0.0D) {
					ActivityMain.this.Msg("Uma Pena!", "Você nãio tem dinheiro para isto!");
				}
				else
				{
					ActivityMain.this.dialog.dismiss();
					ActivityMain.this.hr.incrementaDinheiro(-i);
					ActivityMain.this.hr.setVidaAtual(ActivityMain.this.hr.getVidaMax());
					ActivityMain.this.hr.setManaAtual(ActivityMain.this.hr.getManaMax());
					ActivityMain.this.hr.SalvaPersonagem();
				}
			}
		});
		this.btCuraCancelar.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				ActivityMain.this.dialog.dismiss();
			}
		});
		this.btmain_loja.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				Intent localIntent = new Intent(ActivityMain.this.getBaseContext(), ActivityLoja.class);
				ActivityMain.this.startActivity(localIntent);
			}
		});
		this.btmain_Craft.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View paramAnonymousView)
			{
				//	Intent localIntent = new Intent(ActivityMain.this.getBaseContext(), ActivityCraft.class);
				//	ActivityMain.this.startActivity(localIntent);
			}
		});
	}

	@Override
	protected void onCreate(Bundle paramBundle)
	{
		super.onCreate(paramBundle);
		this.bundle = new Bundle();
		this.hr = new Heroi(this);
		this.dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialogs_cura);
		try
		{
			CopiarDatabase();
			if (!hr.Existente)
			{
				Perguntanome();
				return;
			}
			chamaMain();
			return;
		}
		catch (Exception localException) {
			Log.e("RPG",localException.toString());
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		this.hr.SalvaPersonagem();
	}
}