package Etc;

import java.util.ArrayList;
import java.util.List;

public class Caminho {
	private int xInicial;
	private int xFinal;
	private int yInicial;
	private int yFinal;
	private int[][] Mapa;
	List <Coordenada>coordenada = new ArrayList<Coordenada>();
	private int Tamanho;
	
	public Caminho(int xInicial, int xFinal, int yInicial, int yFinal,int tamanho ,
			int[][] mapa) {
		super();
		this.xInicial = xInicial/32;
		this.xFinal = xFinal/32;
		this.yInicial = yInicial/32;
		this.yFinal = yFinal/32;
		Mapa = mapa;
		Tamanho = tamanho;
	}
	
	public void EncontraCaminho()
	{
		boolean Caminho = false;
		if(Mapa[xFinal][yFinal]%10 == 1)
		{
			System.out.print("Impossivel chegar!");
		}
		else
		{
		while(!Caminho)
		{
				if(xInicial > xFinal) //Movendo Para a esquerda.
				{
					if(Mapa[xInicial-1][yInicial]%10 == 1)
					{
						if(yInicial == 0)
						{
							yInicial++;
							coordenada.add(new Coordenada(0,1));
						}
						else
						{
							yInicial--;
							coordenada.add(new Coordenada(0,-1));
						}
					}
					else
					{
						xInicial--;
						coordenada.add(new Coordenada(1,0));
					}

				}
				else if(xInicial < xFinal)//Movendo Para a direita.
				{
					if(Mapa[xInicial+1][yInicial]%10 == 1)
					{
						if(yInicial == Tamanho)
						{
							
						}
						else
						{
							yInicial++;
							coordenada.add(new Coordenada(0,1));
						}	
					}
					else
					{
						xInicial++;
						coordenada.add(new Coordenada(1,0));
					}
				}
				else if(yInicial > yFinal)//Movendo Para Cima
				{
					if(Mapa[xInicial][yInicial-1]%10 == 1)
					{
						xInicial++;
						coordenada.add(new Coordenada(1,0));
					}
					else
					{
						yInicial--;
						coordenada.add(new Coordenada(0,-1));
					}
				}
				else if(yInicial < yFinal) //Movendo Para Cima
				{
					if(Mapa[xInicial][yInicial+1]%10 == 1)
					{
						xInicial++;
						coordenada.add(new Coordenada(1,0));
					}
					else
					{
						yInicial++;
						coordenada.add(new Coordenada(0,1));
					}
				}
				System.out.print("Na Matriz é: "+ Mapa[xInicial][yInicial] +" x: " + xInicial + " y: " + yInicial + "\n");
				if(xInicial == xFinal)
				{
					if(yInicial == yFinal)
					{
						Caminho = true;
					}
				}
			}
		}
	}
}