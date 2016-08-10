/**package com.gegas;

import java.util.Random;

import Etc.Heroi;
import Etc.Monstro;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityBatalha extends Activity{

	public enum IDEQUIPAMENTOS
	{
		Gelo(10),
		Fogo(9),
		Raio(8),
		Clava(7),
		Escudo(6),
		Machado(5),
		Espada(4),
		Capacete(3),
		Bota(2),
		Calça(1),
		Armadura(0);

		private int id;

		IDEQUIPAMENTOS(int ID){
			id = ID;
		}

		public int getid(){
			return id;
		}
	}
	private Heroi hr;
	private Monstro monstro;
	private ProgressBar pbVida, pbVidaMonstro, pbXp;
	private Button btBatalha_Atacar, btBatalhaMagias;
	private Dialog dialogs_magia;
	private ListView lvMagias;
	private TextView tvNomeMonstro;
	private Bundle bundle;
	private int local;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bundle = getIntent().getExtras();
		local = bundle.getInt("Local");
		hr = new Heroi(this);
		monstro = new Monstro(this,local);
		dialogs_magia = new Dialog(this);
		dialogs_magia.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogs_magia.setContentView(R.layout.dialogs_magia);
		setContentView(R.layout.activity_batalha);
		atribui();
		Main();
	}
	private void atribui()
	{
		pbVidaMonstro = (ProgressBar) findViewById(R.id.pbVidamonstro);
		pbVida = (ProgressBar) findViewById(R.id.pbVida);
		pbXp = (ProgressBar) findViewById(R.id.pbXp);
		btBatalha_Atacar = (Button) findViewById(R.id.btAtacar);
		btBatalhaMagias = (Button) findViewById(R.id.btMagia);
		tvNomeMonstro = (TextView) findViewById(R.id.etNomeMonstro);
		lvMagias = (ListView) dialogs_magia.findViewById(R.id.lvMagias);
		lvMagias.setAdapter(new ArrayAdapter<String>(this,17367043,hr.getListaMagias()));
	}

	public void Main()
	{
		pbVida.setMax(hr.getVidaMax());
		pbVida.setProgress(hr.getVidaAtual());
		pbVidaMonstro.setMax(monstro.getVidaMax());
		pbVidaMonstro.setProgress(monstro.getVidaAtual());	
		tvNomeMonstro.setText(monstro.getNome());
		pbXp.setMax((int) hr.getXpproximolevel());
		pbXp.setProgress(hr.getXptotal());	

		btBatalhaMagias.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogs_magia.show();
			}
		});

		lvMagias.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
			{
				dialogs_magia.dismiss();
				int dano = hr.getMagias(paramAnonymousInt).ChamaMagia(hr.getManaAtual());
				if(dano > 0)
				{
					hr.incrementaMana(hr.getMagias(paramAnonymousInt).getMana());
				}
				if(hr.getVelocidade() > monstro.getVelocidade())
				{
					monstro.tomardano(dano);
					hr.getMagias(paramAnonymousInt).IncrementaXp(dano);
					if(monstro.getVidaAtual()<=0)
					{

					}
					else
					{
						MonstroAtaca();
					}
				}
				else
				{
					MonstroAtaca();
					if(hr.getVidaAtual()<=0)
					{

					}
					else
					{
						monstro.tomardano(dano);
						hr.getMagias(paramAnonymousInt).IncrementaXp(dano);
					}
				}
				pbVida.setProgress(hr.getVidaAtual());
				pbVidaMonstro.setProgress(monstro.getVidaAtual());
				if(monstro.getVidaAtual()<=0)
				{
					hr.IncrementaXp(monstro.getXpdada());
					hr.incrementaDinheiro(monstro.getDinheiro());
					hr.AdicionarItem(monstro.getLoot());
					if(hr.Upar())
					{

					}
					chamaMain(false);
				}
				if(hr.getVidaAtual()<=0)
				{
					hr.IncrementaXp(-hr.getXptotal()/10);
					hr.incrementaDinheiro(-hr.getDinheiro()/10);
					hr.setVidaAtual(hr.getVidaMax());

					chamaMain(true);
				}
			}
		});

		btBatalha_Atacar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if(hr.getVelocidade() > monstro.getVelocidade())
				{
					HeroiAtaca();
					if(monstro.getVidaAtual()<=0)
					{

					}
					else
					{
						MonstroAtaca();
					}
				}
				else
				{
					MonstroAtaca();
					if(hr.getVidaAtual()<=0)
					{

					}
					else
					{
						HeroiAtaca();
					}
				}
				pbVida.setProgress(hr.getVidaAtual());
				pbVidaMonstro.setProgress(monstro.getVidaAtual());
				if(monstro.getVidaAtual()<=0)
				{
					hr.IncrementaXp(monstro.getXpdada());
					hr.incrementaDinheiro(monstro.getDinheiro());
					hr.AdicionarItem(monstro.getLoot());
					if(hr.Upar())
					{

					}
					chamaMain(false);
				}
				if(hr.getVidaAtual()<=0)
				{
					hr.IncrementaXp(-hr.getXptotal()/10);
					hr.incrementaDinheiro(-hr.getDinheiro()/10);
					hr.setVidaAtual(hr.getVidaMax());

					chamaMain(true);
				}
			}
		});
	}

	private void chamaMain(boolean main)
	{
		if(main)
		{
			while(!hr.SalvaPersonagem());
			hr.fechaBancos();
			Intent chamaMain = new Intent(this,ActivityMain.class);
			startActivity(chamaMain);
			finish();
		}
		else
		{
		while(!hr.SalvaPersonagem());
		hr.fechaBancos();
		Intent chamaMain = new Intent(this,ActivityGame.class);
		chamaMain.putExtra("Local", local);
		startActivity(chamaMain);
		finish();
		}
	}

	public void HeroiAtaca()
	{

		int Dano =gerarNumero((hr.getAtaque() + hr.ModificadorAtk())/2,2*(hr.getAtaque() + hr.ModificadorAtk())) - monstro.getDefesa();
		if(Dano <= 0)
		{

		}
		else
		{
			monstro.tomardano(Dano);

			if(hr.getArma().getTipo() == IDEQUIPAMENTOS.Espada.getid())
			{
				hr.getEspadaAptidao().incrementaXp(Dano);

				if(hr.getEspadaAptidao().Upar())
				{
					Toast.makeText(this, "Novo nivel de aptidão com espada atingido!", Toast.LENGTH_LONG).show();
				}

			}
			else if(hr.getArma().getTipo() == IDEQUIPAMENTOS.Machado.getid())
			{
				hr.getMachadoAptidao().incrementaXp(Dano);

				if(hr.getMachadoAptidao().Upar())
				{
					Toast.makeText(this, "Novo nivel de aptidão com machado atingido!", Toast.LENGTH_LONG).show();
				}

			}
			else if(hr.getArma().getTipo() == IDEQUIPAMENTOS.Clava.getid())
			{
				hr.getClavaAptidao().incrementaXp(Dano);

				if(hr.getClavaAptidao().Upar())
				{
					Toast.makeText(this, "Novo nivel de aptidão com clava atingido!", Toast.LENGTH_LONG).show();
				}
			}
		}	
	}

	public void MonstroAtaca()
	{
		int Dano =gerarNumero(monstro.getAtaque()/2,2*monstro.getAtaque()) - (hr.getDefesa()+hr.ModificadorDef());
		if(Dano <= 0)
		{
		}
		else
		{
			hr.tomardano(Dano);
			if(hr.getEscudo().getDefesa() == 0)
			{

			}
			else
			{
				hr.getEscudoAptidao().incrementaXp(Dano);
				hr.getEscudoAptidao().Upar();
			}
		}
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
}**/
