package Etc;

public class Aptidao
{
  private int Level;
  private String Nome;
  private int boostAtk;
  private int boostDef;
  private int boostSpd;
  private double next;
  private float xp;
  
  public Aptidao(String paramString, int paramInt1, float paramFloat, int paramInt2, int paramInt3, int paramInt4)
  {
    this.Nome = paramString;
    this.Level = paramInt1;
    this.xp = paramFloat;
    this.boostAtk = paramInt2;
    this.boostDef = paramInt3;
    this.boostSpd = paramInt4;
    experienceForLevel();
  }
  
  private void experienceForLevel()
  {
    this.next = ((50 * this.Level * this.Level * this.Level - 150 * this.Level * this.Level + 400 * this.Level) / 3);
  }
  
  public boolean Upar()
  {
    if (this.xp >= this.next)
    {
      this.Level = (1 + this.Level);
      experienceForLevel();
      this.boostAtk = (1 + this.boostAtk);
      this.boostDef = (1 + this.boostDef);
      this.boostSpd = (1 + this.boostSpd);
      this.xp = 0.0F;
      return true;
    }
    return false;
  }
  
  public int getBoostAtk()
  {
    return this.boostAtk;
  }
  
  public int getBoostDef()
  {
    return this.boostDef;
  }
  
  public int getBoostSpd()
  {
    return this.boostSpd;
  }
  
  public int getLevel()
  {
    return this.Level;
  }
  
  public double getNext()
  {
    return this.next;
  }
  
  public String getNome()
  {
    return this.Nome;
  }
  
  public float getXp()
  {
    return this.xp;
  }
  
  public void incrementaXp(int paramInt)
  {
    this.xp += paramInt;
    Upar();
  }
  
  public void setBoostAtk(int paramInt)
  {
    this.boostAtk = paramInt;
  }
  
  public void setBoostDef(int paramInt)
  {
    this.boostDef = paramInt;
  }
  
  public void setBoostSpd(int paramInt)
  {
    this.boostSpd = paramInt;
  }
  
  public void setLevel(int paramInt)
  {
    this.Level = paramInt;
  }
  
  public void setNome(String paramString)
  {
    this.Nome = paramString;
  }
  
  public void setXp(float paramFloat)
  {
    this.xp = paramFloat;
  }
}


/* Location:           D:\Documents\Downloads\batalha\classes_dex2jar.jar
 * Qualified Name:     classes.Aptidao
 * JD-Core Version:    0.7.0.1
 */