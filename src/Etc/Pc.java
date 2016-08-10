package Etc;

import com.gegas.ActivityGame.FastRenderView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Pc{
	private static final int BMP_COLUMS = 3;
	private static final int BMP_ROWS = 4;
	private int _posx;
	private int _posy;
	private int _posxf;
	private int _posyf;
	private int _xSpeed;
	private int _ySpeed;
	private int offsidex,offsidey;
	private int [][] Mapaobjetos;
	private FastRenderView game;
	public int distx, disty;
	private Bitmap bmp;
	private int height;
	private int width;
	boolean movx = false;
	boolean movy = false;
	private int framex=0;
	private int framey=0;
	private Rect src;
	private Rect dst;
	private int [][] Mapa;

	/*public Pc(int _Posx, int _Posy, int _xSpeed, int _ySpeed,
			FastRenderView game, Bitmap bmp, int [][] map) {
		super();
		this._posx = _Posx;
		this._posy = _Posy;
		this._xSpeed = _xSpeed;
		this._ySpeed = _ySpeed;
		this.game = game;
		this.bmp = bmp;
		this.width = (bmp.getWidth()/BMP_COLUMS);
		this.height = bmp.getHeight()/BMP_ROWS;
		Log.i("RPG","Widht: "  + game.getWidth());
		Log.i("RPG","Height: "  + game.getHeight());

		if(_posx > game.getWidth()/2)
		{
			offsidex = Math.round(_posx/32 - game.getWidth()/64);
			this._posx = Math.round(game.getWidth()/2);
			
			while(_posx%32 != 0)
			{
				_posx+=1;
			}
		}
		else
		{
			offsidex = 0;
		}
		if(_posy > game.getHeight()/2)
		{
			offsidey = Math.round(_posy/32 - game.getHeight()/64);
			_posy = Math.round(game.getHeight()/2);
			while(_posy%32 != 0)
			{
				_posy+=1;
			}
		}
		else
		{
			offsidey = 0;
		}
		src = new Rect();
		dst = new Rect();
		Mapa = map;
		Log.i("RPG", "Criou o personagem");
	}*/
	
	public Pc(int _Posx, int _Posy, int offsetx, int offsety, int _xSpeed, int _ySpeed,
			FastRenderView game, Bitmap bmp, int [][] map) {
		super();
		this._posx = _Posx;
		this._posy = _Posy;
		this._xSpeed = _xSpeed;
		this._ySpeed = _ySpeed;
		this.offsidex = offsetx;
		this.offsidey = offsety;
		this.game = game;
		this.bmp = bmp;
		this.width = (bmp.getWidth()/BMP_COLUMS);
		this.height = bmp.getHeight()/BMP_ROWS;
		src = new Rect();
		dst = new Rect();
		Mapa = map;
	}

	public int get_posx() {
		return _posx;
	}
	public void set_posx(int _posx) {
		this._posx = _posx;
	}
	public int get_posy() {
		return _posy;
	}
	public void set_posy(int _posy) {
		this._posy = _posy;
	}
	public int get_xSpeed() {
		return _xSpeed;
	}
	public void set_xSpeed(int _xSpeed) {
		this._xSpeed = _xSpeed;
	}
	public int get_ySpeed() {
		return _ySpeed;
	}
	public void set_ySpeed(int _ySpeed) {
		this._ySpeed = _ySpeed;
	}

	public Bitmap getBmp() {
		return bmp;
	}

	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}

	public int getOffsidex() {
		return offsidex;
	}

	public void setOffsidex(int offsidex) {
		this.offsidex = offsidex;
	}

	public int getOffsidey() {
		return offsidey;
	}

	public void setOffsidey(int offsidey) {
		this.offsidey = offsidey;
	}

	public int getFramey() {
		return framey;
	}

	public void setFramey(int framey) {
		this.framey = framey;
	}

	private void Update()
	{	
		if(!movx)
		{
			if(distx == 0)
			{
				movx = true;
			}
			if(_posx > _posxf) // esquerda, diminui
			{
				if(Verificatile(-1,0))
				{

				}
				else
				{
					if(_posx < game.getWidth()/2)
					{
						if(offsidex > 0)
						{
							offsidex--;
							_posx = _posx + _xSpeed;
						}
					}
					if(distx > 0)
					{
						distx = distx - _xSpeed;
						_posx = _posx - _xSpeed;
						framex = ++framex % BMP_COLUMS;
						framey = 1;
					}	
				}

			}
			else if(_posx < _posxf) //direita, aumenta
			{
				if(Verificatile(1,0))
				{

				}
				else
				{
					if(_posx > game.getWidth()/2)
					{
						if(offsidex > 83 - (game.getWidth()/_xSpeed)-1)
						{

						}
						else
						{
							distx = distx - _xSpeed;
							offsidex++;
							_posx = _posx - _xSpeed;
						}
					}

					if(distx > 0)
					{
						distx = distx - _xSpeed;
						_posx = _posx + _xSpeed;
						framex = ++framex % BMP_COLUMS;
						framey = 2;
					}
				}
			}
		}
		if(!movy)
		{
			if(disty == 0)
			{
				movy = true;
			}
			if(_posy > _posyf) //paracima
			{
				if(Verificatile(0,-1))
				{

				}
				else
				{
					if(_posy < game.getHeight()/2)
					{
						if(offsidey > 0)
						{
							offsidey--;
							_posy = _posy + _ySpeed;
						}
					}
					if(disty > 0)
					{
						disty = disty - _ySpeed;
						_posy = _posy - _ySpeed;
						framex = ++framex % BMP_COLUMS;
						framey = 3;
					}
				}


			}
			else if(_posy < _posyf) //parabaixo
			{
				if(Verificatile(0,1))
				{

				}
				else
				{
					if(_posy > game.getHeight()/2)
					{
						if(offsidey > 83 - (game.getHeight()/_ySpeed))
						{
							disty = disty - _ySpeed;
						}
						else
						{
							disty=  disty- _ySpeed;
							offsidey++;
							_posy = _posy - _ySpeed;
						}
					}
					if(disty > 0)
					{
						disty = disty - _ySpeed;
						_posy = _posy + _ySpeed;
						framex = ++framex % BMP_COLUMS;
						framey = 0;
					}
				}
			}
		}

	}

	public void GOTO(int x, int y)
	{
		while(x%32 != 0)
		{
			x+=1;
		}
		while(y%32 != 0)
		{
			y+=1;
		}
		_posxf = x-32;
		_posyf = y-32;
		distx = (int) Math.sqrt(Math.pow(_posx - _posxf, 2));
		disty = (int) Math.sqrt(Math.pow(_posy - _posyf, 2));
		movx = false;
		movy = false;
	}
	public void onDraw(Canvas canvas)
	{
		Update();
		int srcX = width*framex;
		int srcY = height*framey;
		
		//pos.set(left, top, right, bottom)
		src.left = srcX;
		src.top = srcY;
		src.right = srcX + width;
		src.bottom = srcY + height;
		dst.left = _posx;
		dst.top = _posy;
		dst.right = _posx + width;
		dst.bottom = _posy + height;
		canvas.drawBitmap(bmp, src, dst, null);
	}

	public boolean Colisao(float x, float y) {
		return x > _posx && x < _posx+width && y > _posy && y < _posy+height;
	}

	public boolean Verificatile(int x, int y)
	{
		int a = Mapa[(get_posy())/32 + y + offsidey][(get_posx())/32 + x + offsidex]%10;
		if(a == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean VerificaObjeto(int x, int y)
	{
		int a = Mapaobjetos[(get_posy())/32 + y + offsidey][(get_posx())/32 + x + offsidex]%10;
		if(a == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void MoveAcima()
	{
		if(_posy == 0)
		{

		}
		else
		{
			if(Verificatile(0,-1))
			{

			}
			else
			{
				_posy = _posy - _ySpeed;
				framex = ++framex % BMP_COLUMS;
				framey = 3;
				if(_posy < game.getHeight()/2)
				{
					if(offsidey > 0)
					{
						offsidey--;
						_posy = _posy + _ySpeed;
					}
				}

			}
		}
	}

	public void MoveAbaixo()
	{
		if(_posy >= game.getHeight() - _ySpeed)
		{

		}
		else
		{
			if(Verificatile(0,1))
			{

			}
			else
			{
				_posy = _posy + _ySpeed;
				framex = ++framex % BMP_COLUMS;
				framey = 0;
				if(_posy > game.getHeight()/2)
				{
					if(offsidey > Mapa.length - (game.getHeight()/_ySpeed)+1)
					{

					}
					else
					{
						offsidey++;
						_posy = _posy - _ySpeed;
					}
				}

			}
		}
	}

	public void MoveDireita()
	{
		if(_posx >= game.getWidth() - _xSpeed)
		{

		}
		else
		{
			if(Verificatile(1,0))
			{

			}
			else
			{
				_posx = _posx + _xSpeed;
				framex = ++framex % BMP_COLUMS;
				framey = 2;
				if(_posx > game.getWidth()/2)
				{
					if(offsidex > Mapa.length - (game.getWidth()/_xSpeed)-1)
					{

					}
					else
					{
						offsidex++;
						_posx = _posx - _xSpeed;
					}
				}
			}
		}
	}

	public void MoveEsquerda()
	{
		if(_posx == 0)
		{

		}
		else
		{
			if(Verificatile(-1,0))
			{

			}
			else
			{
				_posx = _posx - _xSpeed;
				framex = ++framex % BMP_COLUMS;
				framey = 1;
				if(_posx < game.getWidth()/2)
				{
					if(offsidex > 0)
					{
						offsidex--;
						_posx = _posx + _xSpeed;
					}
				}
			}
		}
	}
}
