package Etc;

public class GeloPequeno extends Magias {

	public GeloPequeno() {
		super("MAGIA2", "Gelo Pequeno", 1, 5, 0, 3,
				1.5f,8);
	}

	public GeloPequeno(String iD, String nome, int level, int ataque,
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
			return getAtaque()+2;
		}
	}
}
