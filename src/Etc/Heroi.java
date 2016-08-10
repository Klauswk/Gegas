package Etc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Heroi
{
	private Item Arma;
	private Item Armadura;
	private int Ataque;
	private Item Bota;
	private Item Calca;
	private Item Capacete;
	private int Defesa;
	private Item Escudo;
	public boolean Existente;
	private String[] ListaMagias;
	private int ManaAtual;
	private int ManaMax;
	private String Nome;
	private SQLiteDatabase Personagem = null;
	private int Velocidade;
	private int VidaAtual;
	private int VidaMax;
	private int Xp;
	private double Xpproximolevel;
	private Context context;
	private Cursor cursor;
	private double dinheiro;
	private Aptidao escudoAptidao;
	private Aptidao espadaAptidao;
	private Aptidao lenhadorAptidao;
	private int level;
	private Aptidao machadoAptidao;
	private List<Magias> magias;
	private Aptidao mineracaoAptidao;
	private Aptidao clavaAptidao;

	public Heroi(Context paramContext)
	{
		context = paramContext;
		Personagem = paramContext.openOrCreateDatabase("RPGSimples.db", 0, null);
		Personagem.execSQL("CREATE TABLE IF NOT EXISTS Personagem (vidamax Text, manamax Text,vidaatual Text, manaatual Text ,ataque Text,defesa Text, velocidade Text , Xp Text, level Text, dinheiro Text, nome Text, EspadaAptidao Text, MachadoAptidao Text,ClavaAptidao Text,EscudoAptidao Text, MineracaoAptidao Text,LenhadorAptidao Text,EspadaAptidaoXp Text,MachadoAptidaoXp Text,ClavaAptidaoXp Text,EscudoAptidaoXp Text,MineracaoAptidaoXp Text,LenhadorAptidaoXp Text,idArmadura Text, idCalca Text, idBota Text, idCapacete Text, idArma Text, idEscudo Text);");
		Personagem = paramContext.openOrCreateDatabase("RPGSimples.db", 0, null);
		Personagem.execSQL("CREATE TABLE IF NOT EXISTS Items (id Text, pos INTEGER PRIMARY KEY , nome Text, dano Text,defesa Text, velocidade Text, tipo Text ,descricao Text);");
		Personagem = paramContext.openOrCreateDatabase("RPGSimples.db", 0, null);
		Personagem.execSQL("CREATE TABLE IF NOT EXISTS Magias ( id TEXT NOT NULL,nome       TEXT NOT NULL,ataque     TEXT NOT NULL,mana       TEXT NOT NULL,modxp      TEXT NOT NULL,level\t\tTEXT NOT NULL,experiencia TEXT NOT NULL,tipo\t\tTEXT NOT NULL,descricao  TEXT NOT NULL)");
		cursor = Personagem.query("Personagem", null, null, null, null, null, null);
		
		if (this.cursor.getCount() == 0)
		{
			Existente = false;
			VidaMax = 30;
			ManaMax = 10;
			setManaAtual(ManaMax);
			setVidaAtual(VidaMax);
			magias = new ArrayList<Magias>();
			Ataque = 10;
			Defesa = 5;
			Xp = 0;
			level = 1;
			dinheiro = 0.0D;
			Arma = new Item("WEAPON0", 0, "Maos", 0, 0, 0, 0, "Apenas nu");
			Armadura = new Item("LEGS0", 0, "Pelado", 0, 0, 0, 0, "Apenas nu");
			Calca = new Item("LEGS0", 0, "Pelado", 0, 0, 0, 0, "Apenas nu");
			Bota = new Item("LEGS0", 0, "Pelado", 0, 0, 0, 0, "Apenas nu");
			Capacete = new Item("LEGS0", 0, "Pelado", 0, 0, 0, 0, "Apenas nu");
			Escudo = new Item("LEGS0", 0, "Pelado", 0, 0, 0, 0, "Apenas nu");
			Nome = "Oi";
			Velocidade = 5;
			espadaAptidao = new Aptidao("Espada", 1, 0.0F, 3, 1, 1);
			machadoAptidao = new Aptidao("Machado", 1, 0.0F, 5, 0, 0);
			escudoAptidao = new Aptidao("Escudo", 1, 0.0F, 0, 4, 1);
			clavaAptidao = new Aptidao("Clava",1,0,2,2,1);
			
			setMineracaoAptidao(new Aptidao("Minera�ao", 1, 0.0F, 0, 0, 0));
			setLenhadorAptidao(new Aptidao("Lenhador", 1, 0.0F, 0, 0, 0));
			setXpproximolevel();
			SalvaPersonagem();
			AprenderMagia(new FogoPequeno());
			AprenderMagia(new GeloPequeno());
			AprenderMagia(new RaioPequeno());
			return;
		}
		try
		{
			Existente = true;
			cursor.moveToFirst();
			setNome(cursor.getString(cursor.getColumnIndex("nome")));
			setLevel(Integer.parseInt(cursor.getString(cursor.getColumnIndex("level"))));
			setVidaMax(Integer.parseInt(cursor.getString(cursor.getColumnIndex("vidamax"))));
			setVidaAtual(Integer.parseInt(cursor.getString(cursor.getColumnIndex("vidaatual"))));
			setManaMax(Integer.parseInt(cursor.getString(cursor.getColumnIndex("manamax"))));
			setManaAtual(Integer.parseInt(cursor.getString(cursor.getColumnIndex("manaatual"))));
			setAtaque(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ataque"))));
			setDefesa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("defesa"))));
			setVelocidade(Integer.parseInt(cursor.getString(cursor.getColumnIndex("velocidade"))));
			setDinheiro(Double.parseDouble(cursor.getString(cursor.getColumnIndex("dinheiro"))));
			setXp(Integer.parseInt(cursor.getString(cursor.getColumnIndex("Xp"))));
			setXpproximolevel();
			setEspadaAptidao(new Aptidao("Espada", Integer.parseInt(cursor.getString(cursor.getColumnIndex("EspadaAptidao"))), Float.parseFloat(cursor.getString(cursor.getColumnIndex("EspadaAptidaoXp"))), 3, 1, 1));
			setMachadoAptidao(new Aptidao("Machdo", Integer.parseInt(cursor.getString(cursor.getColumnIndex("MachadoAptidao"))), Float.parseFloat(cursor.getString(cursor.getColumnIndex("MachadoAptidaoXp"))), 5, 0, 0));
			setEscudoAptidao(new Aptidao("Escudo", Integer.parseInt(cursor.getString(cursor.getColumnIndex("EscudoAptidao"))), Float.parseFloat(cursor.getString(cursor.getColumnIndex("EscudoAptidaoXp"))), 0, 4, 1));
			setMineracaoAptidao(new Aptidao("Mineracao", Integer.parseInt(cursor.getString(cursor.getColumnIndex("MineracaoAptidao"))), Float.parseFloat(cursor.getString(cursor.getColumnIndex("MineracaoAptidaoXp"))), 0, 0, 0));
			setClavaAptidao(new Aptidao("Clava", Integer.parseInt(cursor.getString(cursor.getColumnIndex("ClavaAptidao"))), Float.parseFloat(cursor.getString(cursor.getColumnIndex("ClavaAptidaoXp"))), 0, 0, 0));
			setLenhadorAptidao(new Aptidao("Lenhador", Integer.parseInt(cursor.getString(cursor.getColumnIndex("LenhadorAptidao"))), Float.parseFloat(cursor.getString(cursor.getColumnIndex("LenhadorAptidaoXp"))), 0, 0, 0));
			
			String str1 = cursor.getString(cursor.getColumnIndex("idArma"));
			String str2 = cursor.getString(cursor.getColumnIndex("idArmadura"));
			String str3 = cursor.getString(cursor.getColumnIndex("idBota"));
			String str4 = cursor.getString(cursor.getColumnIndex("idCapacete"));
			String str5 = cursor.getString(cursor.getColumnIndex("idCalca"));
			String str6 = cursor.getString(cursor.getColumnIndex("idEscudo"));
			setWeapon(PegaItem(str1));
			setArmadura(PegaItem(str2));
			setBota(PegaItem(str3));
			setCapacete(PegaItem(str4));
			setCalca(PegaItem(str5));
			setEscudo(PegaItem(str6));
			cursor = Personagem.query("Magias", new String[] { "id", "nome", "level ", " experiencia", "mana", "ataque", "modxp", " tipo", "descricao" }, null, null, null, null, null);
			cursor.moveToFirst();
			if (cursor.getCount() == 0)
			{
				setListaMagias(new String[] { "Não há magias" });
				return;
			}
		}
		catch (Exception localException)
		{
			Log.e("Erro", String.valueOf(localException));
			return;
		}
		StringBuilder localStringBuilder = new StringBuilder();
		ArrayList<Magias> localArrayList = new ArrayList<Magias>();
		for (int i = 0;; i++)
		{
			if (i >= cursor.getCount())
			{
				setListaMagias(localStringBuilder.toString().split("#"));
				setMagias(localArrayList);
				return;
			}
			Magias localMagias;
			try {
				localMagias = (Magias)Class.forName("Etc." + cursor.getString(cursor.getColumnIndex("nome")).replace(" ", "")).newInstance();
				localMagias.setID(cursor.getString(cursor.getColumnIndex("id")));
				localMagias.setNome(cursor.getString(cursor.getColumnIndex("nome")));
				localMagias.setLevel(Integer.parseInt(cursor.getString(cursor.getColumnIndex("level"))));
				localMagias.setExperiencia(Integer.parseInt(cursor.getString(cursor.getColumnIndex("experiencia"))));
				localMagias.setMana(Integer.parseInt(cursor.getString(cursor.getColumnIndex("mana"))));
				localMagias.setAtaque(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ataque"))));
				localMagias.setModificadorExperiencia(Float.parseFloat(cursor.getString(cursor.getColumnIndex("modxp"))));
				localMagias.setTipo(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tipo"))));
				localMagias.setDescriaco(cursor.getString(cursor.getColumnIndex("descricao")));
				localMagias.setExperienciaProximoLevel();
				localArrayList.add(localMagias);
				localStringBuilder.append(localMagias.getNome());
				localStringBuilder.append("#");
				cursor.moveToNext();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}

		}
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
			this.Personagem.insert("Items", null, localContentValues);
			return;
		}
		catch (Exception localException)
		{
			Log.i("Erro", String.valueOf(localException));
		}
	}

	public void AprenderMagia(Magias paramMagias)
	{
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("id", String.valueOf(paramMagias.getID()));
		localContentValues.put("nome", String.valueOf(paramMagias.getNome()));
		localContentValues.put("ataque", String.valueOf(paramMagias.getAtaque()));
		localContentValues.put("mana", String.valueOf(paramMagias.getMana()));
		localContentValues.put("modxp", String.valueOf(paramMagias.getModificadorExperiencia()));
		localContentValues.put("level", String.valueOf(paramMagias.getLevel()));
		localContentValues.put("experiencia", String.valueOf(paramMagias.getExperiencia()));
		localContentValues.put("tipo", String.valueOf(paramMagias.getTipo()));
		localContentValues.put("descricao", String.valueOf(paramMagias.getDescricao()));
		this.Personagem.insert("Magias", null, localContentValues);
	}

	public int ChamaMagia(int paramInt)
	{
		int i = getMagias(paramInt).ChamaMagia(this.ManaAtual);
		getMagias(paramInt).IncrementaXp(i);
		if (i == 0) {
			return 0;
		}
		ManaAtual -= getMagias(paramInt).getMana();
		return i;
	}

	public Item Equipa(Item paramItem)
	{
		Item localItem1 = new Item();
		switch (paramItem.getTipo())
		{
		default: 
			return localItem1;
		case 0: 
			Item localItem8 = getArmadura();
			setArmadura(paramItem);
			return localItem8;
		case 1: 
			Item localItem7 = getArmadura();
			setCalca(paramItem);
			return localItem7;
		case 2: 
			Item localItem6 = getBota();
			setBota(paramItem);
			return localItem6;
		case 3: 
			Item localItem5 = getCapacete();
			setCapacete(paramItem);
			return localItem5;
		case 4: 
			Item localItem4 = getArma();
			setWeapon(paramItem);
			return localItem4;
		case 5: 
			Item localItem3 = getArma();
			setWeapon(paramItem);
			return localItem3;
		case 6:
			Item localItem2 = getEscudo();
			setEscudo(paramItem);
			return localItem2;
		}
	}

	public void IncrementaXp(int paramInt)
	{
		setXp(paramInt + getXptotal());
	}

	public int ModificadorAtk()
	{
		if (this.Arma.getTipo() == 4) {
			return this.Arma.getDano() + this.Armadura.getDano() + this.Calca.getDano() + this.Bota.getDano() + this.Capacete.getDano() + this.espadaAptidao.getBoostAtk();
		}
		if (this.Arma.getTipo() == 5) {
			return this.Arma.getDano() + this.Armadura.getDano() + this.Calca.getDano() + this.Bota.getDano() + this.Capacete.getDano() + this.machadoAptidao.getBoostAtk();
		}
		return this.Arma.getDano() + this.Armadura.getDano() + this.Calca.getDano() + this.Bota.getDano() + this.Capacete.getDano();
	}

	public int ModificadorDef()
	{
		if (this.Arma.getTipo() == 4) {
			return this.Arma.getDefesa() + this.Armadura.getDefesa() + this.Calca.getDefesa() + this.Bota.getDefesa() + this.Capacete.getDefesa() + this.espadaAptidao.getBoostDef();
		}
		if (this.Arma.getTipo() == 5) {
			return this.Arma.getDefesa() + this.Armadura.getDefesa() + this.Calca.getDefesa() + this.Bota.getDefesa() + this.Capacete.getDefesa() + this.machadoAptidao.getBoostDef();
		}
		return this.Arma.getDefesa() + this.Armadura.getDefesa() + this.Calca.getDefesa() + this.Bota.getDefesa() + this.Capacete.getDefesa();
	}

	public int ModificadorSpd()
	{
		if (this.Arma.getTipo() == 4) {
			return this.Arma.getVelocidade() + this.Armadura.getVelocidade() + this.Calca.getVelocidade() + this.Bota.getVelocidade() + this.Capacete.getVelocidade() + this.espadaAptidao.getBoostSpd();
		}
		if (this.Arma.getTipo() == 5) {
			return this.Arma.getVelocidade() + this.Armadura.getVelocidade() + this.Calca.getVelocidade() + this.Bota.getVelocidade() + this.Capacete.getVelocidade() + this.machadoAptidao.getBoostSpd();
		}
		return this.Arma.getVelocidade() + this.Armadura.getVelocidade() + this.Calca.getVelocidade() + this.Bota.getVelocidade() + this.Capacete.getVelocidade();
	}

	public Item PegaItem(String paramString)
	{
		try
		{
			SQLiteDatabase localSQLiteDatabase = context.openOrCreateDatabase(android.os.Environment.getExternalStorageDirectory()+"/Gegas/BancoDados.db", 0, null);
			cursor = localSQLiteDatabase.rawQuery("select * from ListaItems where id like '" + paramString + "'", null);
			cursor.moveToFirst();
			Item localItem = new Item();
			localItem.setId(cursor.getString(cursor.getColumnIndex("id")));
			localItem.setNome(cursor.getString(cursor.getColumnIndex("nome")));
			localItem.setDano(Integer.parseInt(cursor.getString(cursor.getColumnIndex("dano"))));
			localItem.setDefesa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("defesa"))));
			localItem.setVelocidade(Integer.parseInt(cursor.getString(cursor.getColumnIndex("velocidade"))));
			localItem.setTipo(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tipo"))));
			localItem.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
			localSQLiteDatabase.close();
			return localItem;
		}
		catch (Exception localException)
		{
			Log.e("RPG", localException.toString());
		}
		return null;
	}

	public void SalvaMagias()
	{
		for (int i = 0;; i++)
		{
			if (i >= this.magias.size()) {
				return;
			}
			ContentValues localContentValues = new ContentValues();
			localContentValues.put("id", String.valueOf(magias.get(i).getID()));
			localContentValues.put("nome", String.valueOf(magias.get(i).getNome()));
			localContentValues.put("ataque", String.valueOf(magias.get(i).getAtaque()));
			localContentValues.put("mana", String.valueOf((magias.get(i)).getMana()));
			localContentValues.put("modxp", String.valueOf((magias.get(i)).getModificadorExperiencia()));
			localContentValues.put("level", String.valueOf((magias.get(i)).getLevel()));
			localContentValues.put("experiencia", String.valueOf((magias.get(i)).getExperiencia()));
			localContentValues.put("tipo", String.valueOf((magias.get(i)).getTipo()));
			localContentValues.put("descricao", String.valueOf((magias.get(i)).getDescricao()));
			Personagem.update("Magias", localContentValues, "id = '" + (this.magias.get(i)).getID() + "'", null);
		}
	}

	public boolean SalvaPersonagem()
	{
		try
		{
			ContentValues localContentValues = new ContentValues();
			localContentValues.put("nome", String.valueOf(getNome()));
			localContentValues.put("level", String.valueOf(getLevel()));
			localContentValues.put("vidamax", String.valueOf(getVidaMax()));
			localContentValues.put("vidaatual", String.valueOf(getVidaAtual()));
			localContentValues.put("manamax", String.valueOf(getManaMax()));
			localContentValues.put("manaatual", String.valueOf(getManaAtual()));
			localContentValues.put("ataque", String.valueOf(getAtaque()));
			localContentValues.put("defesa", String.valueOf(getDefesa()));
			localContentValues.put("velocidade", String.valueOf(getVelocidade()));
			localContentValues.put("dinheiro", String.valueOf(getDinheiro()));
			localContentValues.put("Xp", String.valueOf(getXptotal()));
			localContentValues.put("EspadaAptidao", String.valueOf(getEspadaAptidao().getLevel()));
			localContentValues.put("MachadoAptidao", String.valueOf(getMachadoAptidao().getLevel()));
			localContentValues.put("ClavaAptidao", String.valueOf(getClavaAptidao().getLevel()));
			localContentValues.put("EscudoAptidao", String.valueOf(getEscudoAptidao().getLevel()));
			localContentValues.put("MineracaoAptidao", String.valueOf(getMachadoAptidao().getLevel()));
			localContentValues.put("LenhadorAptidao", String.valueOf(getEscudoAptidao().getLevel()));
			localContentValues.put("EspadaAptidaoXp", String.valueOf(getEspadaAptidao().getXp()));
			localContentValues.put("MachadoAptidaoXp", String.valueOf(getMachadoAptidao().getXp()));
			localContentValues.put("EscudoAptidaoXp", String.valueOf(getEscudoAptidao().getXp()));
			localContentValues.put("MineracaoAptidaoXp", String.valueOf(getMineracaoAptidao().getXp()));
			localContentValues.put("LenhadorAptidaoXp", String.valueOf(getLenhadorAptidao().getXp()));
			localContentValues.put("ClavaAptidaoXp", String.valueOf(getClavaAptidao().getXp()));
			localContentValues.put("idArmadura", getArmadura().getId());
			localContentValues.put("idCalca", getCalca().getId());
			localContentValues.put("idBota", getBota().getId());
			localContentValues.put("idCapacete", getCapacete().getId());
			localContentValues.put("idArma", getArma().getId());
			localContentValues.put("idEscudo", getEscudo().getId());
			int i = this.Personagem.update("Personagem", localContentValues, null, null);
			SalvaMagias();
			if (i == 0) {
				this.Personagem.insert("Personagem", null, localContentValues);
			}
			return true;
		}
		catch (Exception localException)
		{
			Log.i("Erro", String.valueOf(localException));
			return false;
		}
	}

	public boolean Upar()
	{
		Random localRandom = new Random();
		boolean bool1 = getXptotal() < getXpproximolevel();
		boolean bool2 = false;
		if (!bool1)
		{
			this.level = (1 + this.level);
			setVidaMax(10 + this.VidaMax);
			setAtaque(getAtaque() + localRandom.nextInt(5));
			setDefesa(getDefesa() + localRandom.nextInt(5));
			setVelocidade(getVelocidade() + localRandom.nextInt(5));
			setManaMax(10 + this.ManaMax);
			setXp(0);
			setXpproximolevel();
			bool2 = true;
		}
		return bool2;
	}

	public boolean buscarDados()
	{
		try
		{
			cursor = Personagem.query("Items", null, null, null, null, null, null);
			if (cursor.getCount() != 0) {
				return true;
			}
			Log.i("Lista", "A lista está vazia");
			return false;
		}
		catch (Exception localException)
		{
			Log.e("Erro", "Ocorreu o seguinte erro: " + localException);
			return false;
		}
	}

	public void fechaBancos()
	{
		this.Personagem.close();
	}

	public Item getArma()
	{
		return this.Arma;
	}

	public Item getArmadura()
	{
		return this.Armadura;
	}

	public int getAtaque()
	{
		return this.Ataque;
	}

	public Item getBota()
	{
		return this.Bota;
	}

	public Item getCalca()
	{
		return this.Calca;
	}

	public Item getCapacete()
	{
		return this.Capacete;
	}

	public int getDefesa()
	{
		return this.Defesa;
	}

	public double getDinheiro()
	{
		return this.dinheiro;
	}

	public Item getEscudo()
	{
		return this.Escudo;
	}

	public Aptidao getEscudoAptidao()
	{
		return this.escudoAptidao;
	}

	public Aptidao getEspadaAptidao()
	{
		return this.espadaAptidao;
	}

	public Aptidao getLenhadorAptidao()
	{
		return this.lenhadorAptidao;
	}

	public int getLevel()
	{
		return this.level;
	}

	public String[] getListaMagias()
	{
		return this.ListaMagias;
	}

	public Aptidao getMachadoAptidao()
	{
		return this.machadoAptidao;
	}

	public Magias getMagias(int paramInt)
	{
		return (Magias)this.magias.get(paramInt);
	}

	public int getManaAtual()
	{
		return this.ManaAtual;
	}

	public int getManaMax()
	{
		return this.ManaMax;
	}

	public Aptidao getMineracaoAptidao()
	{
		return this.mineracaoAptidao;
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

	public double getXpproximolevel()
	{
		return this.Xpproximolevel;
	}

	public int getXptotal()
	{
		return this.Xp;
	}

	public void incrementaDinheiro(double paramDouble)
	{
		setDinheiro(paramDouble + getDinheiro());
	}

	public void setArmadura(Item paramItem)
	{
		this.Armadura = paramItem;
	}

	public void setAtaque(int paramInt)
	{
		this.Ataque = paramInt;
	}

	public void setBota(Item paramItem)
	{
		this.Bota = paramItem;
	}

	public void setCalca(Item paramItem)
	{
		this.Calca = paramItem;
	}

	public void setCapacete(Item paramItem)
	{
		this.Capacete = paramItem;
	}

	public void setDefesa(int paramInt)
	{
		this.Defesa = paramInt;
	}

	public void setDinheiro(double paramDouble)
	{
		this.dinheiro = paramDouble;
	}

	public void setEscudo(Item paramItem)
	{
		this.Escudo = paramItem;
	}

	public void setEscudoAptidao(Aptidao paramAptidao)
	{
		this.escudoAptidao = paramAptidao;
	}

	public void setEspadaAptidao(Aptidao paramAptidao)
	{
		this.espadaAptidao = paramAptidao;
	}

	public void setLenhadorAptidao(Aptidao paramAptidao)
	{
		this.lenhadorAptidao = paramAptidao;
	}

	public void setLevel(int paramInt)
	{
		this.level = paramInt;
	}

	public void setListaMagias(String[] paramArrayOfString)
	{
		this.ListaMagias = paramArrayOfString;
	}

	public void setMachadoAptidao(Aptidao paramAptidao)
	{
		this.machadoAptidao = paramAptidao;
	}

	public void setMagias(List<Magias> paramList)
	{
		this.magias = paramList;
	}

	public void setManaAtual(int paramInt)
	{
		this.ManaAtual = paramInt;
	}

	public void setManaMax(int paramInt)
	{
		this.ManaMax = paramInt;
	}

	public void setMineracaoAptidao(Aptidao paramAptidao)
	{
		this.mineracaoAptidao = paramAptidao;
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

	public void setWeapon(Item paramItem)
	{
		this.Arma = paramItem;
	}

	public Aptidao getClavaAptidao() {
		return clavaAptidao;
	}

	public void setClavaAptidao(Aptidao clavaAptidao) {
		this.clavaAptidao = clavaAptidao;
	}

	public void setXp(int paramInt)
	{
		this.Xp = paramInt;
	}

	public void setXpproximolevel()
	{
		this.Xpproximolevel = ((50 * this.level * this.level * this.level - 150 * this.level * this.level + 400 * this.level) / 3);
	}

	public int tomardano(int paramInt)
	{
		setVidaAtual(getVidaAtual() - paramInt);
		return paramInt;
	}
	public void incrementaMana(int mana)
	{
		setManaAtual(ManaAtual - mana);
	}
}