package Etc;

import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Monstro
{
	private int Ataque;
	private int Defesa;
	private double Dinheiro;
	public boolean ExisteLoots;
	private int Fraqueza;
	private String Id;
	private int Level;
	private String[] ListaLoot;
	private Item Loot;
	private String Nome;
	private int Velocidade;
	private int VidaAtual;
	private int VidaMax;
	private int XpDada;
	private SQLiteDatabase Database;
	private Context context;
	private Cursor cursor;

	public Monstro() {}

	/**public Monstro(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble, int paramInt6)
	{
		setNome(paramString);
		setVidaMax(paramInt2 + paramInt1 * 2);
		setAtaque(paramInt3 + paramInt1 * 2);
		setDefesa(paramInt4 + paramInt1 * 2);
		setDinheiro(paramDouble + paramInt1 * 2);
		setXpDada(paramInt6);
	}

	public Monstro(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, double paramDouble, int paramInt7, String paramString3)
	{
		setNome(paramString1);
		setId(paramString2);
		setLevel(paramInt1);
		setVidaMax(paramInt2);
		setAtaque(paramInt3);
		setDefesa(paramInt4);
		setVelocidade(paramInt5);
		setDinheiro(paramDouble);
		setXpDada(paramInt7);
		setFraqueza(paramInt6);
	}**/
	
	public Monstro(Context context,int Local)
	{
		this.context = context;
		Database = context.openOrCreateDatabase(android.os.Environment.getExternalStorageDirectory()+"/Gegas/BancoDados.db", 0, null);
		cursor = Database.query("ListaMonstros", null, "local =" + Local, null, null, null, null);
		cursor.moveToFirst();
		int numero = gerarNumero(0, cursor.getCount()-1);
		for (int i = 0; i < numero; i++) {
			cursor.moveToNext();
		}
		setNome(cursor.getString(cursor.getColumnIndex("nome")));
		setId(cursor.getString(cursor.getColumnIndex("id")));
		setLevel(Integer.parseInt(cursor.getString(cursor.getColumnIndex("level"))));
		setVidaMax(Integer.parseInt(cursor.getString(cursor.getColumnIndex("vida"))));
		setAtaque(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ataque"))));
		setDefesa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("defesa"))));
		setVelocidade(Integer.parseInt(cursor.getString(cursor.getColumnIndex("velocidade"))));
		setDinheiro(Double.parseDouble(cursor.getString(cursor.getColumnIndex("dinheiro"))));
		setXpDada(Integer.parseInt(cursor.getString(cursor.getColumnIndex("xpdada"))));
		setFraqueza(Integer.parseInt(cursor.getString(cursor.getColumnIndex("fraqueza"))));
		setLoot(cursor.getString(cursor.getColumnIndex("loot")));
		
		numero = gerarNumero(1, ListaLoot.length-1);
		Log.i("RPG", ListaLoot[numero]);
		cursor = Database.query("ListaItems", null, "id like'"+ ListaLoot[numero] +"'", null, null, null, null);
		cursor.moveToFirst();
		Item localItem = new Item();
		localItem.setId(cursor.getString(cursor.getColumnIndex("id")));
		localItem.setNome(cursor.getString(cursor.getColumnIndex("nome")));
		localItem.setDano(Integer.parseInt(cursor.getString(cursor.getColumnIndex("dano"))));
		localItem.setDefesa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("defesa"))));
		localItem.setVelocidade(Integer.parseInt(cursor.getString(cursor.getColumnIndex("velocidade"))));
		localItem.setTipo(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tipo"))));
		localItem.setDescricao(this.cursor.getString(this.cursor.getColumnIndex("descricao")));
		setLoot(localItem);
		Database.close();
	}

	public int getAtaque()
	{
		return this.Ataque;
	}

	public int getDefesa()
	{
		return this.Defesa;
	}

	public double getDinheiro()
	{
		return this.Dinheiro;
	}

	public int getFraqueza()
	{
		return this.Fraqueza;
	}

	public String getId()
	{
		return this.Id;
	}

	public int getLevel()
	{
		return this.Level;
	}

	public String[] getListaLoot()
	{
		return this.ListaLoot;
	}

	public Item getLoot()
	{
		return this.Loot;
	}

	public String getNome()
	{
		return this.Nome;
	}

	public int getVelocidade()
	{
		return this.Velocidade;
	}

	public int getVidaAtual()
	{
		return this.VidaAtual;
	}

	public int getVidaMax()
	{
		return this.VidaMax;
	}

	public int getXpdada()
	{
		return this.XpDada;
	}

	public void setAtaque(int paramInt)
	{
		this.Ataque = paramInt;
	}

	public void setDefesa(int paramInt)
	{
		this.Defesa = paramInt;
	}

	public void setDinheiro(double paramDouble)
	{
		this.Dinheiro = paramDouble;
	}

	public void setFraqueza(int paramInt)
	{
		this.Fraqueza = paramInt;
	}

	public void setFraqueza(String paramString)
	{
		if (paramString == "Espada") {
			this.Fraqueza = 4;
		}

		else if (paramString == "Machado")
		{
			this.Fraqueza = 5;
			return;
		}
		else   if (paramString == "Clava")
		{
			this.Fraqueza = 8;
			return;
		}
		else    if (paramString == "Fogo")
		{
			this.Fraqueza = 9;
			return;
		}
		else   if (paramString == "Gelo")
		{
			this.Fraqueza = 10;
			return;
		}
		else if(paramString != "Eletricidade");
		{
			this.Fraqueza = 11;
		}
	}

	public void setId(String paramString)
	{
		this.Id = paramString;
	}

	public void setLevel(int paramInt)
	{
		this.Level = paramInt;
	}

	public void setLoot(Item paramItem)
	{
		this.Loot = paramItem;
	}

	public void setLoot(String paramString)
	{
		if (paramString.length() <= 1)
		{
			this.ExisteLoots = false;
			return;
		}
		this.ExisteLoots = true;
		this.ListaLoot = paramString.split("#");
	}

	public void setNome(String paramString)
	{
		this.Nome = paramString;
	}

	public void setVelocidade(int paramInt)
	{
		this.Velocidade = paramInt;
	}

	public void setVidaAtual(int paramInt)
	{
		this.VidaAtual = paramInt;
	}

	public void setVidaMax(int paramInt)
	{
		this.VidaMax = paramInt;
		setVidaAtual(paramInt);
	}

	public void setXpDada(int paramInt)
	{
		this.XpDada = paramInt;
	}

	public int tomardano(int paramInt)
	{
		setVidaAtual(getVidaAtual() - paramInt);
		return paramInt;
	}
	
	public int gerarNumero(int inicio, int maximo)
	{
		Random random = new Random();
		if (inicio > maximo) {
			while(inicio>maximo)
			{
				inicio--;
			}
		}
		long extensao = (long)maximo - (long)inicio + 1;
		long fraction = (long)(extensao * random.nextDouble());
		int Numerogerado =  (int)(fraction + inicio);    
		return Numerogerado;
	}
}