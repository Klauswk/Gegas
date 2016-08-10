package Etc;

import java.util.Vector;

public class Areas
{
  private int Id;
  private Vector<Monstro> ListaMonstros;
  private String Nome;
  private String Requerimento;
  
  public Areas() {}
  
  public Areas(String paramString1, int paramInt, String paramString2, Vector<Monstro> paramVector)
  {
    setNome(paramString1);
    setId(paramInt);
    setRequerimento(paramString2);
    setListaMonstros(paramVector);
  }
  
  public int getId()
  {
    return this.Id;
  }
  
  public Vector<Monstro> getListaMonstros()
  {
    return this.ListaMonstros;
  }
  
  public String getNome()
  {
    return this.Nome;
  }
  
  public String getRequerimento()
  {
    return this.Requerimento;
  }
  
  public void setId(int paramInt)
  {
    this.Id = paramInt;
  }
  
  public void setListaMonstros(Vector<Monstro> paramVector)
  {
    this.ListaMonstros = paramVector;
  }
  
  public void setNome(String paramString)
  {
    this.Nome = paramString;
  }
  
  public void setRequerimento(String paramString)
  {
    this.Requerimento = paramString;
  }
}


/* Location:           D:\Documents\Downloads\batalha\classes_dex2jar.jar
 * Qualified Name:     classes.Areas
 * JD-Core Version:    0.7.0.1
 */