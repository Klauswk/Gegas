package Etc;

public class Magias {
	
	public enum TIPOSMAGIAS
	{
		BuffVelocidade(5),
		BuffDefesa(4),
		BuffAtaque(3),
		Cura(2),
		Ataque(1);

		private int id;

		TIPOSMAGIAS(int ID){
			id = ID;
		}

		public int getid(){
			return id;
		}
	}
	
	private String ID;
	private String Nome;
	private int Level;
	private int Experiencia;
	private int ExperienciaProximoLevel;
	private int Mana;
	private int Ataque;
	private float ModificadorExperiencia;
	private int Tipo;
	private String Descricao;

	public Magias()
	{

	}

	public Magias(String iD, String nome, int level,int ataque ,int experiencia,
			int mana, float modificadorExperiencia, int tipo) {
		super();
		ID = iD;
		Nome = nome;
		Level = level;
		Experiencia = experiencia;
		setExperienciaProximoLevel();
		Mana = mana;
		ModificadorExperiencia = modificadorExperiencia;
		Tipo = tipo;
		Ataque = ataque;
	}

	public Magias(String iD, String nome, int level,int ataque ,int experiencia,
			int mana, float modificadorExperiencia, String tipo) {
		super();
		ID = iD;
		Nome = nome;
		Level = level;
		Experiencia = experiencia;
		setExperienciaProximoLevel();
		Mana = mana;
		ModificadorExperiencia = modificadorExperiencia;
		Ataque = ataque;
		tipo = tipo.toLowerCase();
		if(tipo.contains("ataque"))
		{
			Tipo = 1;
		}
		else if(tipo.contains("cura"))
		{
			Tipo = 2;
		}
		else if(tipo.contains("buffataque"))
		{
			Tipo = 3;
		}
		else if(tipo.contains("buffdefesa"))
		{
			Tipo = 4;
		}
		else if(tipo.contains("buffvelocidade"))
		{
			Tipo = 5;
		}
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public int getLevel() {
		return Level;
	}

	public void setLevel(int level) {
		Level = level;
	}

	public int getExperiencia() {
		return Experiencia;
	}

	public void setExperiencia(int experiencia) {
		Experiencia = experiencia;
	}

	public int getAtaque() {
		return Ataque;
	}

	public void setAtaque(int ataque) {
		Ataque = ataque;
	}

	public int getExperienciaProximoLevel() {
		return ExperienciaProximoLevel;
	}

	public void setExperienciaProximoLevel() {
		int aux = Level;
		aux = (50 * (Level) * (Level) * (Level) - 150 * (Level) * (Level) + 400 * (Level)) / 3;
		ExperienciaProximoLevel = (int) (aux*ModificadorExperiencia);
	}

	public int getMana() {
		return Mana;
	}

	public void setMana(int mana) {
		Mana = mana;
	}

	public int getTipo() {
		return Tipo;
	}

	public void setTipo(int tipo) {
		Tipo = tipo;
	}

	public String getDescricao() {
		return Descricao;
	}

	public void setDescriaco(String descricao) {
		Descricao = descricao;
	}

	public float getModificadorExperiencia() {
		return ModificadorExperiencia;
	}

	public void setModificadorExperiencia(float modificadorExperiencia) {
		ModificadorExperiencia = modificadorExperiencia;
	}

	public void IncrementaXp(int Exp)
	{
		int aux = getExperiencia() + Exp;
		setExperiencia(aux);
	}

	public boolean Upar()
	{
		if(getExperiencia() >= getExperienciaProximoLevel())
		{
			Level++;
			setExperiencia(0);
			Ataque=+5;
			setExperienciaProximoLevel();
			return true;
		}
		return false;
	}

	public int ChamaMagia(int Quantidade_Mana)
	{
		return 0;
	}
}
