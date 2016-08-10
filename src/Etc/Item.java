package Etc;

public class Item
{
	int Dano;
	int Defesa;
	String Descricao;
	String Id;
	String Nome;
	int Tipo;
	int Velocidade;
	int Vida;
	int pos;
	int preco;

	public Item() {}

	public Item(String ID, int Pos, String Nome, int Dano, int Defesa, int Velocidade, int Tipo, String Descricao)
	{
		setId(ID);
		setNome(Nome);
		setDano(Dano);
		setDefesa(Defesa);
		setVelocidade(Velocidade);
		setTipo(Tipo);
		setPos(Pos);
		setDescricao(Descricao);
	}

	public Item(String ID, String Nome, int Dano, int Defesa, int Velocidade, int Tipo, String Descricao)
	{
		setId(ID);
		setNome(Nome);
		setDano(Dano);
		setDefesa(Defesa);
		setVelocidade(Velocidade);
		setTipo(Tipo);
		setDescricao(Descricao);
	}

	public int getDano()
	{
		return this.Dano;
	}

	public int getDefesa()
	{
		return this.Defesa;
	}

	public String getDescricao()
	{
		return this.Descricao;
	}

	public String getId()
	{
		return this.Id;
	}

	public String getNome()
	{
		return this.Nome;
	}

	public int getPos()
	{
		return this.pos;
	}

	public int getPreco()
	{
		return this.preco;
	}

	public int getTipo()
	{
		return this.Tipo;
	}

	public int getVelocidade()
	{
		return this.Velocidade;
	}

	public int getVida()
	{
		return this.Vida;
	}

	public void setDano(int Dano)
	{
		this.Dano = Dano;
	}

	public void setDefesa(int Defesa)
	{
		this.Defesa = Defesa;
	}

	public void setDescricao(String Descricao)
	{
		this.Descricao = Descricao;
	}

	public void setId(String id)
	{
		this.Id = id;
	}

	public void setNome(String nome)
	{
		this.Nome = nome;
	}

	public void setPos(int Pos)
	{
		this.pos = Pos;
	}

	public void setPreco(int preco)
	{
		this.preco = preco;
	}

	public void setTipo(int Tipo)
	{
		this.Tipo = Tipo;
	}

	public void setVelocidade(int velocidade)
	{
		this.Velocidade = velocidade;
	}

	public void setVida(int vida)
	{
		this.Vida = vida;
	}
}