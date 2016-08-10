package Etc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.gegas.ActivityGame.FastRenderView;

public class Mapa {
	private int Tamanho;
	private FastRenderView game;
	int [][] Mapa;
	
	public Mapa(FastRenderView Game)
	{
		game = Game;
	}
	
	public int getTamanho() {
		return Tamanho;
	}

	public int setTamanho(int tamanho) {
		Tamanho = tamanho;
		return tamanho;
	}
	
	public FastRenderView getGame() {
		return game;
	}

	public void setGame(FastRenderView game) {
		this.game = game;
	}

	public int[][] getMapa() {
		return Mapa;
	}

	public void setMapa(int[][] mapa) {
		Mapa = mapa;
	}

	public int[][] CarregaMapa(String Nome) {
		try {
			InputStream input;
			String Teste;
			if(Nome.contains(".txt"))
			{
				input = game.getContext().getAssets().open(Nome);
			}
			else
			{
				input = game.getContext().getAssets().open(Nome + ".txt");
			}
			BufferedReader r = new BufferedReader(new InputStreamReader(input));
			StringBuilder total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
				total.append(line);
			}
			Teste = total.toString();
			String[] Split =  Teste.split("[ \n]");
			 int size = setTamanho(Integer.valueOf(Split[0]));

			Mapa = new int [size][size];
			for (int i = 0; i <size; i++) {
				for (int j = 0; j < size; j++) {
					Mapa[i][j] = Integer.valueOf(Split[(j+1) + i*size]);
				}
			}	
			return Mapa;
		} 
		catch (Exception e){

		}
		return null;
	}
}
