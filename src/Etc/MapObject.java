package Etc;

import java.util.Random;

import android.graphics.Bitmap;

public class MapObject {
	private int id;
	private String name;
	private int amount;
	private Bitmap bmp;
	
	public MapObject(int ID, String Name, int Amount, Bitmap Bmp)
	{
		this.id = ID;
		this.name = Name;
		this.amount = Amount;
		this.bmp = Bmp;
	}
	
	public MapObject(int ID, String Name,  Bitmap Bmp)
	{
		this.id = ID;
		this.name = Name;
		Random random = new Random();
		this.amount = random.nextInt(4)+1;
		this.bmp = Bmp;
	}
}
