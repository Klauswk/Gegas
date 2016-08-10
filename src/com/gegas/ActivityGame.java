package com.gegas;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Etc.Botão;
import Etc.Item;
import Etc.Magias;
import Etc.Mapa;
import Etc.Monstro;
import Etc.Pc;
import Etc.Tiles;
import Etc.Time;
import Etc.Heroi;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityGame extends Activity {
	
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
	
	private FastRenderView renderView;
	public boolean running;
	private Bundle bundle;
	private int local;
	
	@SuppressLint("ClickableViewAccessibility")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bundle = getIntent().getExtras();
		local = bundle.getInt("Local");

		renderView = new FastRenderView(this,local);

		renderView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				renderView.click(event);
				return true;
			}
		});
		setContentView(renderView); 
	}      

	protected void onResume() {
		super.onResume();
		renderView.resume();
	}

	protected void onPause() {
		super.onPause();         
		renderView.pause();
	}  

	public class FastRenderView extends SurfaceView implements Runnable{
		private Thread renderThread = null;
		private Context context;
		private SurfaceHolder holder;
		public Handler mHandler;
		private float currentTime = 0;
		private float frameTime = 0;
		private float accumulator = 0.0f;
		private int ticks = 0;
		private Bitmap tiles;
		private List<Tiles> tile;
		private int[][] Mapa;
		private int[][] MapaObjetos;
		private Mapa mapa = new Mapa(this);
		private Pc player;
		private Rect pos;
		private Bitmap[] setas;
		private Botão[] seta = new Botão[4];
		private Botão[] botoes = new Botão[3];
		private int direcao = 0;
		private SharedPreferences shared;
		private boolean INICIARJOGO = false;
		private SQLiteDatabase BancoDados = null;
		private Button btStatus_Equipar;
		private Button btVoltar;
		private Cursor cursor;
		private Dialog dialogListView;
		private Dialog dialog_StatusMagia;
		private Dialog dialog_Menu;
		private Dialog dialog_batalha;
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
		private ProgressBar pbVida, pbVidaMonstro, pbXp;
		private Button btBatalha_Atacar, btBatalhaMagias;
		private Dialog dialogs_magia;
		private ListView lvMagias;
		private TextView tvNomeMonstro;
		private Monstro monstro;
		private volatile boolean MenuAberto = false;
		private int local;
		private volatile boolean emBatalha = false;

		public FastRenderView(Context context, int Local) {
			super(context);
			this.context = context;
			local = Local;
			holder = getHolder();
			tile = new ArrayList<Tiles>();
			Mapa = mapa.CarregaMapa("mapas/"+String.valueOf(local));
			MapaObjetos = mapa.CarregaMapa("mapas/Objetos1.txt");
			pos= new Rect();
			setas = new Bitmap[4];

			shared = getSharedPreferences("Preferencias", 0);

			seta[0] = new Botão(this,BitmapFactory.decodeResource(getResources(), R.drawable.setaacima));

			seta[1] = new Botão(this,BitmapFactory.decodeResource(getResources(), R.drawable.setadireita));

			seta[2] = new Botão(this,BitmapFactory.decodeResource(getResources(), R.drawable.setabaixo));

			seta[3] = new Botão(this,BitmapFactory.decodeResource(getResources(), R.drawable.setaesquerda));

			botoes[0] = new Botão(this,BitmapFactory.decodeResource(getResources(), R.drawable.botaoa));

			botoes[1] = new Botão(this,BitmapFactory.decodeResource(getResources(), R.drawable.botaob));

			botoes[2] = new Botão(this,BitmapFactory.decodeResource(getResources(), R.drawable.botaomenu));

			hr = new Heroi(context);

			dialog_Menu = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
			dialog_Menu.requestWindowFeature(1);
			dialog_Menu.setContentView(R.layout.activity_status);
			dialog_Menu.setCanceledOnTouchOutside(false);
			dialog_StatusMagia = new Dialog(context);
			dialog_StatusMagia.requestWindowFeature(1);
			dialog_StatusMagia.setContentView(R.layout.dialogs_statusmagia);
			dialogListView = new Dialog(context);
			dialogListView.requestWindowFeature(1);
			dialogListView.setContentView(R.layout.dialogs_magia);
			
			dialog_batalha = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
			dialog_batalha.requestWindowFeature(1);
			dialog_batalha.setContentView(R.layout.activity_batalha);
			dialog_batalha.setCanceledOnTouchOutside(false);
			pbVidaMonstro = (ProgressBar) dialog_batalha.findViewById(R.id.pbVidamonstro);
			pbVida = (ProgressBar) dialog_batalha.findViewById(R.id.pbVida);
			pbXp = (ProgressBar) dialog_batalha.findViewById(R.id.pbXp);
			btBatalha_Atacar = (Button) dialog_batalha.findViewById(R.id.btAtacar);
			btBatalhaMagias = (Button) dialog_batalha.findViewById(R.id.btMagia);
			tvNomeMonstro = (TextView) dialog_batalha.findViewById(R.id.etNomeMonstro);
			dialogs_magia = new Dialog(context);
			dialogs_magia.requestWindowFeature(1);
			dialogs_magia.setContentView(R.layout.dialogs_magia);
			
			lvMagias = (ListView) dialogs_magia.findViewById(R.id.lvMagias);
			lvMagias.setAdapter(new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,hr.getListaMagias()));
					
			atribui();

			holder.addCallback(new SurfaceHolder.Callback() {

				@Override
				public void surfaceDestroyed(SurfaceHolder holder) {

				}

				@Override
				public void surfaceCreated(SurfaceHolder holder) {

				}

				@Override
				public void surfaceChanged(SurfaceHolder holder, int format, int width,
						int height) {

				}
			});
			tiles = BitmapFactory.decodeResource(getResources(), R.drawable.tiles);

			setas[0] = BitmapFactory.decodeResource(getResources(), R.drawable.setaacima);

			setas[1] = BitmapFactory.decodeResource(getResources(), R.drawable.setadireita);

			setas[2] = BitmapFactory.decodeResource(getResources(), R.drawable.setabaixo);

			setas[3] = BitmapFactory.decodeResource(getResources(), R.drawable.setaesquerda);

			for(int i = 0; i < 7; i++)
			{
				for(int j = 0; j<4 ; j++)
				{
					tile.add(new Tiles(tiles,new Rect(32*j,32*i,32*j + 32,32*i + 32) ));
				}
			}
			tiles = BitmapFactory.decodeResource(getResources(), R.drawable.stone);
			tile.add(new Tiles(tiles,new Rect(0,0,16,16)));
		}


		public void resume() { 

			running = true;
			renderThread = new Thread(this);
			renderThread.start();
		}

		public void pause() {
			running = false; 
			while(true) {
				try {
					renderThread.join();
					break;
				} catch (InterruptedException e) {
					Log.e("Erro", e.toString());
				}
			}
			renderThread = null;        
		}
		public void run() {	
			while(running) {  
				if(!holder.getSurface().isValid())
					continue;

				float newTime = System.nanoTime() / 1000000000.0f;
				Time.deltaTime = frameTime = newTime - currentTime;

				if(frameTime > Time.maxDeltaTime)
					frameTime = Time.maxDeltaTime;

				currentTime = newTime;

				accumulator += frameTime;



				while(accumulator > Time.fixedDeltaTime)
				{
					accumulator -= Time.fixedDeltaTime;
					ticks++;

					if(ticks > 10)
					{
						ticks=0;
					}		
					mover();
					Canvas canvas = holder.lockCanvas();            
					drawSurface(canvas);                                           
					holder.unlockCanvasAndPost(canvas);		
				}
				++Time.frameCount;	
			} 
		}
		

		private void drawSurface(Canvas canvas) {
			if(!INICIARJOGO)
			{
				int posx = shared.getInt("Posicaox", 0);
				int posy = shared.getInt("Posicaoy", 0);
				int offsx = shared.getInt("Offsidex", 0);
				int offsy = shared.getInt("Offsidey", 0);

				seta[0].set_posx(setas[3].getWidth());
				seta[0].set_posy(getHeight() - setas[3].getHeight() - setas[2].getHeight() - setas[0].getHeight());

				seta[1].set_posx(setas[2].getWidth() + setas[3].getWidth());
				seta[1].set_posy(getHeight() -setas[1].getHeight()-setas[2].getHeight());

				seta[2].set_posx(setas[2].getHeight());
				seta[2].set_posy(getHeight() -setas[2].getHeight());

				seta[3].set_posx(0);
				seta[3].set_posy(getHeight()-setas[3].getHeight() -setas[2].getHeight());


				botoes[0].set_posx(getWidth()-botoes[0].getBmp().getWidth());
				botoes[0].set_posy(getHeight() - botoes[0].getBmp().getHeight() - 20);

				botoes[1].set_posx(getWidth() - botoes[0].getBmp().getWidth()  - botoes[1].getBmp().getWidth()-20);
				botoes[1].set_posy(getHeight() - botoes[1].getBmp().getWidth());

				botoes[2].set_posx(getWidth()/2);
				botoes[2].set_posy(0);


				player = new Pc(posx, posy, offsx,offsy, 32, 32, this, BitmapFactory.decodeResource(getResources(), R.drawable.personagem), Mapa);
				INICIARJOGO = true;
			}
			else
			{		
				if(MenuAberto)
				{
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if(dialog_Menu.isShowing())
							{
								abreMenu();
							}
							else
							{
								dialog_Menu.show();
							}
						}
					});
				}
				
				else if(emBatalha)
				{
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							if(dialog_batalha.isShowing())
							{
								batalhar();
							}
							else
							{
								Log.i("RPG","Criou o dialog");
								dialog_batalha.show();
							}
						}
					});
				}
				else
				{
					for(int i = 0; i < (getHeight()/32) +32; i++)
					{
						for(int j = 0; j < (getWidth()/32) +32; j++)
						{
							pos.left = 32*j;
							pos.top = 32*i;
							pos.right = 32*j + 32;
							pos.bottom = 32*i + 32;
							if(i + player.getOffsidey() >= Mapa.length)
							{

							}
							else if(j + player.getOffsidex() >= Mapa.length)
							{

							}
							else
							{
								tile.get(Mapa[i + player.getOffsidey()][j + player.getOffsidex()]/100).onDraw(pos,canvas);

								if(MapaObjetos[i + player.getOffsidey()][j + player.getOffsidex()] == 0)
								{}
								else
								{
									tile.get(MapaObjetos[i + player.getOffsidey()][j + player.getOffsidex()]).onDraw(pos,canvas);									
								}
							}
						}			
					}

					for (int i = 0; i < 4; i++) {
						seta[i].onDraw(canvas);
					}
					for (int i = 0; i < 3; i++) {
						botoes[i].onDraw(canvas);
					}

					player.onDraw(canvas);
				}
			}
		}

		public boolean click(MotionEvent event)
		{
			synchronized (holder) {
				int eventaction = event.getAction();
				
				switch (eventaction) {
				
				case MotionEvent.ACTION_DOWN:
					for(int i = 0 ; i < 4 ; i++)
					{
						if(seta[i].Colisao(event.getX(), event.getY()))
						{
							if(i == 0)
							{
								direcao = 1;
							}
							else if(i == 1)
							{
								direcao = 2;
							}
							else if(i == 2)
							{
								direcao = 3;
							}
							else if(i == 3)
							{
								direcao = 4;
							}
						}
					}

					for(int i = 0 ; i < 3 ; i++)
					{
						if(botoes[i].Colisao(event.getX(),event.getY()))
						{
							if(i == 0)
							{
								if(MapaObjetos[player.get_posy()/32+player.getOffsidey()][player.get_posx()/32+player.getOffsidex() +1] == 28)
								{
									MapaObjetos[player.get_posy()/32 +player.getOffsidey()][player.get_posx()/32 +player.getOffsidex() +1] = 0;
								}
							}
							else if(i == 1)
							{

							}
							else if(i == 2)
							{
								MenuAberto = true;
							}
						}
					}
				case MotionEvent.ACTION_MOVE:
					for(int i = 0 ; i < 4 ; i++)
					{
						if(seta[i].Colisao(event.getX(), event.getY()))
						{
							if(i == 0)
							{
								direcao = 1;
							}
							else if(i == 1)
							{
								direcao = 2;
							}
							else if(i == 2)
							{
								direcao = 3;
							}
							else if(i == 3)
							{
								direcao = 4;
							}
						}
					}
					break;
				case MotionEvent.ACTION_UP:   
					direcao = 0;
					break;
				}
				return super.onTouchEvent(event);
			}

		}


		public void mover()
		{

			if(direcao == 0)
			{

			}
			else
			{
				int batalha = new Random().nextInt(20);
				if(batalha == 1)
				{
					emBatalha = true;
					monstro = new Monstro(context,local);
					batalhar();
				}
				
				if(direcao == 1)
				{
					player.MoveAcima();
				}
				else if(direcao == 2)
				{
					player.MoveDireita();
				}
				else if(direcao == 3)
				{
					player.MoveAbaixo();
				}
				else if(direcao == 4)
				{
					player.MoveEsquerda();
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

		private void SalvaPosicao()
		{
			Editor editor = shared.edit();
			editor.putInt("Posicaox", player.get_posx());
			editor.putInt("Posicaoy", player.get_posy());
			editor.putInt("Offsidex", player.getOffsidex());
			editor.putInt("Offsidey", player.getOffsidey());
			editor.commit();
		}

		public void MostraMsgEquipamentos()
		{
			Builder localBuilder = new AlertDialog.Builder(context);
			localBuilder.setTitle("Equipados!");
			localBuilder.setMessage("Arma:" + hr.getArma().getNome() + "\n" + "Armadura: " + hr.getArmadura().getNome() + "\n" + " Bota: " + hr.getBota().getNome() + "\n" + " CalÃ§a: " + hr.getCalca().getNome() + "\n" + "Capacete: " + hr.getCapacete().getNome() + "\n" + "Escudo: " + hr.getEscudo().getNome());
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
				Log.e("Erro", localException.toString());
			}
			return null;
		}

		public void atribui()
		{
			tvStatus_Vida = (TextView)dialog_Menu.findViewById(R.id.tvStatus_Vida);
			tvStatus_Ataque = (TextView)dialog_Menu.findViewById(R.id.tvStatus_Ataque);
			tvStatus_Defesa = (TextView)dialog_Menu.findViewById(R.id.tvStatus_Defesa);
			tvStatus_Dinheiro = (TextView)dialog_Menu.findViewById(R.id.tvStatus_Dinheiro);
			tvStatus_Xp = (TextView)dialog_Menu.findViewById(R.id.tvStatus_Exp);
			tvStatus_Level = (TextView)dialog_Menu.findViewById(R.id.tvStatus_Level);
			btVoltar = (Button)dialog_Menu.findViewById(R.id.btStatus_Voltar);
			tvStatus_AptidaoEspada = (TextView)dialog_Menu.findViewById(R.id.tvStatus_AptidaoEspada);
			tvStatus_AptidaoMachado = (TextView)dialog_Menu.findViewById(R.id.tvStatus_AptidaoMachado);
			tvStatus_AptidaoEscudo = (TextView)dialog_Menu.findViewById(R.id.tvStatus_AptidaoEscudo);
			tvStatus_AptidaoMineracao =(TextView)dialog_Menu.findViewById(R.id.tvStatus_AptidaoMineracao);
			tvStatus_AptidaoLenhador = (TextView)dialog_Menu.findViewById(R.id.tvStatus_AptidaoLenhador);
			btStatus_Equipar = (Button)dialog_Menu.findViewById(R.id.btStatus_Equipar);
			tvStatusVelocidade = (TextView)dialog_Menu.findViewById(R.id.tvStatus_Velocidade);
			tvStatus_Mana = (TextView)dialog_Menu.findViewById(R.id.tvStatus_Mana);
			tvListaMagias = (TextView)dialog_Menu.findViewById(R.id.tvListaMagias);
			tvStatus_AptidaoClava = (TextView)dialog_Menu.findViewById(R.id.tvStatus_AptidaoClava);

			lvLista_Magias = (ListView)dialogListView.findViewById(R.id.lvMagias);
			tvStatus_nomeMagia = (TextView)dialog_StatusMagia.findViewById(R.id.tvStatus_nomeMagia);
			tvStatus_levelMagia = (TextView)dialog_StatusMagia.findViewById(R.id.tvStatus_levelMagia);
			tvStatus_experienciaMagia = (TextView)dialog_StatusMagia.findViewById(R.id.tvStatus_experienciaMagia);
			tvStatus_descricaoMagia = (TextView)dialog_StatusMagia.findViewById(R.id.tvStatus_descricaoMagia);
			tvStatus_manaMagia = (TextView)dialog_StatusMagia.findViewById(R.id.tvStatus_manaMagia);
			tvStatus_ataqueMagia = (TextView)dialog_StatusMagia.findViewById(R.id.tvStatus_ataqueMagia);

			criaBanco();
			tvStatus_Vida.setText("Vida: " + hr.getVidaMax() + "/" + hr.getVidaAtual());
			tvStatus_Mana.setText("Mana: " + hr.getManaMax() + "/" + hr.getManaAtual());
			tvStatus_Ataque.setText("Ataque: " + String.valueOf(hr.getAtaque()) + " +" + String.valueOf(hr.ModificadorAtk()));
			tvStatus_Defesa.setText("Defesa: " + String.valueOf(hr.getDefesa()) + " +" + String.valueOf(hr.ModificadorDef()));
			tvStatusVelocidade.setText("Velocidade: " + String.valueOf(hr.getVelocidade()) + " +" + String.valueOf(hr.ModificadorSpd()));
			tvStatus_Xp.setText("Xp: " + hr.getXpproximolevel() + "/" + hr.getXptotal());
			tvStatus_Level.setText("Level: " + String.valueOf(hr.getLevel()));
			tvStatus_Dinheiro.setText(String.valueOf("Dinheiro: " +(int) hr.getDinheiro()));
			tvStatus_AptidaoEspada.setText("Aptidao Com Espadas: " + String.valueOf(hr.getEspadaAptidao().getLevel()));
			tvStatus_AptidaoMachado.setText("Aptidao Com Machados: " + String.valueOf(hr.getMachadoAptidao().getLevel()));
			tvStatus_AptidaoClava.setText("Aptidao Com Clavas: "  + String.valueOf(hr.getClavaAptidao().getLevel()));
			tvStatus_AptidaoEscudo.setText("Aptidao Com Escudos: " + String.valueOf(hr.getEscudoAptidao().getLevel()));
			tvStatus_AptidaoLenhador.setText("Aptidao Com Lenha: " + String.valueOf(hr.getLenhadorAptidao().getLevel()));
			tvStatus_AptidaoMineracao.setText("Aptidao Com Mineracao: " + String.valueOf(hr.getMineracaoAptidao().getLevel()));

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
					android.R.layout.simple_list_item_1, hr.getListaMagias());

			lvLista_Magias.setAdapter(adapter);
		}

		public void criaBanco()
		{
			try
			{
				BancoDados = openOrCreateDatabase("BancoDados.db", 0, null);
				return;
			}
			catch (Exception localException)
			{
				Log.e("Erro", "Não foi possivel inicializar o Banco" + localException);
			}
		}

		protected void abreMenu()
		{
			tvListaMagias.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View paramAnonymousView)
				{
					dialogListView.show();
				}
			});
			btVoltar.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View paramAnonymousView)
				{
					MenuAberto = false;
					dialog_Menu.dismiss();
				}
			});
			btStatus_Equipar.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View paramAnonymousView)
				{
					MostraMsgEquipamentos();
				}
			});
			lvLista_Magias.setOnItemClickListener(new AdapterView.OnItemClickListener()
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
		
		/**                               BATALHA                                                         **/
		public void batalhar()
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
				Intent chamaMain = new Intent(context,ActivityMain.class);
				startActivity(chamaMain);
				finish();
			}
			else
			{
				emBatalha = false;
				while(dialog_batalha.isShowing())
				{
					dialog_batalha.dismiss();
				}
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
						Toast.makeText(context, "Novo nivel de aptidão com espada atingido!", Toast.LENGTH_LONG).show();
					}

				}
				else if(hr.getArma().getTipo() == IDEQUIPAMENTOS.Machado.getid())
				{
					hr.getMachadoAptidao().incrementaXp(Dano);

					if(hr.getMachadoAptidao().Upar())
					{
						Toast.makeText(context, "Novo nivel de aptidão com machado atingido!", Toast.LENGTH_LONG).show();
					}

				}
				else if(hr.getArma().getTipo() == IDEQUIPAMENTOS.Clava.getid())
				{
					hr.getClavaAptidao().incrementaXp(Dano);

					if(hr.getClavaAptidao().Upar())
					{
						Toast.makeText(context, "Novo nivel de aptidão com clava atingido!", Toast.LENGTH_LONG).show();
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
	}
}



