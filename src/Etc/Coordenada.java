package Etc;

public class Coordenada {
	int coordx;
	int coordy;
	
	public Coordenada()
	{
		
	}
	public Coordenada(int coordx, int coordy) {
		super();
		this.coordx = coordx;
		this.coordy = coordy;
	}
	
	public int getCoordx() {
		return coordx;
	}
	public void setCoordx(int coordx) {
		this.coordx = coordx;
	}
	public int getCoordy() {
		return coordy;
	}
	public void setCoordy(int coordy) {
		this.coordy = coordy;
	}
}
