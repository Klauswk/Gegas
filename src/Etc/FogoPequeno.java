package Etc;

public class FogoPequeno extends Magias {

	public FogoPequeno() {
		super("MAGIA1", "Fogo Pequeno", 1, 5, 0, 3,
				1.5f, 9);
	}

	public FogoPequeno(String iD, String nome, int level, int ataque,
			int experiencia, int mana, float modificadorExperiencia, int tipo) {
		super(iD, nome, level, ataque, experiencia, mana,
				modificadorExperiencia, tipo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int ChamaMagia(int Quantidade_Mana) {
		if(Quantidade_Mana < getMana())
		{
			return 0;
		}
		else
		{
			return getAtaque();
		}
	}
}
