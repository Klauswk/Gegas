package com.gegas;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
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
import Etc.Heroi;
import Etc.Item;
import Etc.Magias;

public class ActivityStatus
  extends Activity
{
  private SQLiteDatabase BancoDados = null;
  private Button btStatus_Equipar;
  private Button btVoltar;
  private Cursor cursor;
  private Dialog dialogListView;
  private Dialog dialog_StatusMagia;
  private Heroi hr;
  private ListView lvLista_Magias;
  private TextView tvListaMagias;
  private TextView tvStatusVelocidade;
  private TextView tvStatus_AptidaoEscudo;
  private TextView tvStatus_AptidaoEspada;
  private TextView tvStatus_AptidaoLenhador;
  private TextView tvStatus_AptidaoMachado;
  private TextView tvStatus_AptidaoMineracao;
  private TextView tvStatus_Ataque;
  private TextView tvStatus_Defesa;
  private TextView tvStatus_Dinheiro;
  private TextView tvStatus_Level;
  private TextView tvStatus_Mana;
  private TextView tvStatus_Vida;
  private TextView tvStatus_Xp;
  private TextView tvStatus_ataqueMagia;
  private TextView tvStatus_descricaoMagia;
  private TextView tvStatus_experienciaMagia;
  private TextView tvStatus_levelMagia;
  private TextView tvStatus_manaMagia;
  private TextView tvStatus_nomeMagia;
  private TextView tvStatus_AptidaoClava;
  
  public void MostraMsgEquipamentos()
  {
    Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("Equipados!");
    localBuilder.setMessage("Arma:" + this.hr.getArma().getNome() + "\n" + "Armadura: " + this.hr.getArmadura().getNome() + "\n" + " Bota: " + this.hr.getBota().getNome() + "\n" + " Cal√ßa: " + this.hr.getCalca().getNome() + "\n" + "Capacete: " + this.hr.getCapacete().getNome() + "\n" + "Escudo: " + this.hr.getEscudo().getNome());
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
  
  public void atribui()
  {
    this.tvStatus_Vida = ((TextView)findViewById(R.id.tvStatus_Vida));
    this.tvStatus_Ataque = ((TextView)findViewById(R.id.tvStatus_Ataque));
    this.tvStatus_Defesa = ((TextView)findViewById(R.id.tvStatus_Defesa));
    this.tvStatus_Dinheiro = ((TextView)findViewById(R.id.tvStatus_Dinheiro));
    this.tvStatus_Xp = ((TextView)findViewById(R.id.tvStatus_Exp));
    this.tvStatus_Level = ((TextView)findViewById(R.id.tvStatus_Level));
    this.btVoltar = ((Button)findViewById(R.id.btStatus_Voltar));
    this.tvStatus_AptidaoEspada = ((TextView)findViewById(R.id.tvStatus_AptidaoEspada));
    this.tvStatus_AptidaoMachado = ((TextView)findViewById(R.id.tvStatus_AptidaoMachado));
    this.tvStatus_AptidaoEscudo = ((TextView)findViewById(R.id.tvStatus_AptidaoEscudo));
    this.tvStatus_AptidaoMineracao = ((TextView)findViewById(R.id.tvStatus_AptidaoMineracao));
    this.tvStatus_AptidaoLenhador = ((TextView)findViewById(R.id.tvStatus_AptidaoLenhador));
    this.btStatus_Equipar = ((Button)findViewById(R.id.btStatus_Equipar));
    this.tvStatusVelocidade = ((TextView)findViewById(R.id.tvStatus_Velocidade));
    this.tvStatus_Mana = ((TextView)findViewById(R.id.tvStatus_Mana));
    this.tvListaMagias = ((TextView)findViewById(R.id.tvListaMagias));
    tvStatus_AptidaoClava = (TextView) findViewById(R.id.tvStatus_AptidaoClava);
    this.lvLista_Magias = ((ListView)this.dialogListView.findViewById(R.id.lvMagias));
    this.tvStatus_nomeMagia = ((TextView)this.dialog_StatusMagia.findViewById(R.id.tvStatus_nomeMagia));
    this.tvStatus_levelMagia = ((TextView)this.dialog_StatusMagia.findViewById(R.id.tvStatus_levelMagia));
    this.tvStatus_experienciaMagia = ((TextView)this.dialog_StatusMagia.findViewById(R.id.tvStatus_experienciaMagia));
    this.tvStatus_descricaoMagia = ((TextView)this.dialog_StatusMagia.findViewById(R.id.tvStatus_descricaoMagia));
    this.tvStatus_manaMagia = ((TextView)this.dialog_StatusMagia.findViewById(R.id.tvStatus_manaMagia));
    this.tvStatus_ataqueMagia = ((TextView)this.dialog_StatusMagia.findViewById(R.id.tvStatus_ataqueMagia));
  }
  
  public void criaBanco()
  {
    try
    {
      this.BancoDados = openOrCreateDatabase("BancoDados.db", 0, null);
      this.BancoDados.execSQL(ActivityMain.SQLLISTAITEMS);
      return;
    }
    catch (Exception localException)
    {
      Log.e("Erro", "N„o foi possivel inicializar o Banco" + localException);
    }
  }
  
  @Override
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_status);
    this.hr = new Heroi(this);
    this.dialog_StatusMagia = new Dialog(this);
    this.dialog_StatusMagia.requestWindowFeature(1);
    this.dialog_StatusMagia.setContentView(R.layout.dialogs_statusmagia);
    this.dialogListView = new Dialog(this);
    this.dialogListView.requestWindowFeature(1);
    this.dialogListView.setContentView(R.layout.dialogs_magia);
    
    criaBanco();
    atribui();
    this.tvStatus_Vida.setText("Vida: " + this.hr.getVidaMax() + "/" + this.hr.getVidaAtual());
    this.tvStatus_Mana.setText("Mana: " + this.hr.getManaMax() + "/" + this.hr.getManaAtual());
    this.tvStatus_Ataque.setText("Ataque: " + String.valueOf(this.hr.getAtaque()) + " +" + String.valueOf(this.hr.ModificadorAtk()));
    this.tvStatus_Defesa.setText("Defesa: " + String.valueOf(this.hr.getDefesa()) + " +" + String.valueOf(this.hr.ModificadorDef()));
    this.tvStatusVelocidade.setText("Velocidade: " + String.valueOf(this.hr.getVelocidade()) + " +" + String.valueOf(this.hr.ModificadorSpd()));
    this.tvStatus_Xp.setText("Xp: " + this.hr.getXpproximolevel() + "/" + this.hr.getXptotal());
    this.tvStatus_Level.setText("Level: " + String.valueOf(this.hr.getLevel()));
    this.tvStatus_Dinheiro.setText(String.valueOf("Dinheiro: " + this.hr.getDinheiro()));
    this.tvStatus_AptidaoEspada.setText("Aptidao Com Espadas: " + String.valueOf(this.hr.getEspadaAptidao().getLevel()));
    this.tvStatus_AptidaoMachado.setText("Aptidao Com Machados: " + String.valueOf(this.hr.getMachadoAptidao().getLevel()));
    this.tvStatus_AptidaoClava.setText("Aptidao Com Clavas: "  + String.valueOf(this.hr.getClavaAptidao().getLevel()));
    this.tvStatus_AptidaoEscudo.setText("Aptidao Com Escudos: " + String.valueOf(this.hr.getEscudoAptidao().getLevel()));
    this.tvStatus_AptidaoLenhador.setText("Aptidao Com Lenha: " + String.valueOf(this.hr.getLenhadorAptidao().getLevel()));
    this.tvStatus_AptidaoMineracao.setText("Aptidao Com Mineracao: " + String.valueOf(this.hr.getMineracaoAptidao().getLevel()));
    
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_list_item_1, hr.getListaMagias());
    
    lvLista_Magias.setAdapter(adapter);
    
    this.tvListaMagias.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
       dialogListView.show();
      }
    });
    this.btVoltar.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent(ActivityStatus.this.getBaseContext(), ActivityMain.class);
        ActivityStatus.this.startActivity(localIntent);
        ActivityStatus.this.finish();
      }
    });
    this.btStatus_Equipar.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ActivityStatus.this.MostraMsgEquipamentos();
      }
    });
    this.lvLista_Magias.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        dialogListView.dismiss();
        Magias localMagias = hr.getMagias(paramAnonymousInt);
        tvStatus_nomeMagia.setText("Nome: " + localMagias.getNome());
        tvStatus_levelMagia.setText("Level: " + localMagias.getLevel());
        tvStatus_experienciaMagia.setText("Experiencia: " + localMagias.getExperiencia());
        tvStatus_descricaoMagia.setText("Descricao: " + localMagias.getDescricao());
        tvStatus_manaMagia.setText("Mana gasta: " + localMagias.getMana());
        tvStatus_ataqueMagia.setText("Ataque: " + localMagias.getAtaque());
        dialog_StatusMagia.show();
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