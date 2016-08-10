package Etc;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.gegas.ActivityGame.FastRenderView;

public class Sprite {
	private static final int BMP_COLUMS = 3;
	private static final int BMP_ROWS = 4;
	public int vida;
	private int _posx;
	private int _posy;
	private int _xSpeed;
	private int _ySpeed;
	private FastRenderView game;
	private Bitmap bmp;
	private Random random;
	private int height;
	private int width;
	int framex=0;
	int framey=0;
	private Rect src;
	private Rect dst;
	
	public Sprite(int _posx, int _posy, int _xSpeed, int _ySpeed,
			FastRenderView game, Bitmap bmp) {
		super();
		this._posx = _posx;
		this._posy = _posy;
		this._xSpeed = _xSpeed;
		this._ySpeed = _ySpeed;
		this.game = game;
		this.bmp = bmp;
		this.width = (bmp.getWidth()/BMP_COLUMS);
		this.height = bmp.getHeight()/BMP_ROWS;
		random = new Random();
	}
	
	public Sprite(int ScreenWidth, int ScreenHeight,
			FastRenderView game, Bitmap bmp) {
		super();
		this._posx = random.nextInt(ScreenHeight);
		this._posy = random.nextInt(ScreenWidth);
		this.game = game;
		this.bmp = bmp;
		this.width = (bmp.getWidth()/BMP_COLUMS);
		this.height = bmp.getHeight()/BMP_ROWS;
		random = new Random();
		this._xSpeed = random.nextInt(30)-5;
		this._ySpeed = random.nextInt(30)-5;
	}
	
	public Sprite(FastRenderView fastRenderView, Bitmap bmp) {
		super();
		this.game = fastRenderView;
		this.bmp = bmp;
		this.width = bmp.getWidth()/BMP_COLUMS;
		this.height = bmp.getHeight()/BMP_ROWS;
		random = new Random();
		this._xSpeed = random.nextInt(15)+5;
		this._ySpeed = random.nextInt(15)+5;
		this._posx = random.nextInt(400);
		this._posy = random.nextInt(400);
		vida = 1;
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

	private void Update()
	{
		
		if(_posx > game.getWidth() - width - _xSpeed || _posx+ _xSpeed< 0)
		{
			_xSpeed = -_xSpeed;
		}
		_posx = _posx + _xSpeed;
		if(_posy > game.getHeight() - height - _ySpeed || _posy+ _ySpeed< 20)
		{
			_ySpeed = -_ySpeed;
		}
		_posy = _posy + _ySpeed;
		
		
		//Frames

		if((_xSpeed > 0)&&(_ySpeed<0)||(_xSpeed < 0)&&(_ySpeed<0)||((_xSpeed == 0)&&(_ySpeed<0)))
		{
			framex = ++framex % BMP_COLUMS;
			framey = 3;
		}
		if((_xSpeed > 0)&&(_ySpeed>0)||(_xSpeed < 0)&&(_ySpeed>0)||((_xSpeed == 0)&&(_ySpeed>0)))
		{
			framex = ++framex % BMP_COLUMS;
			framey = 0;
		}
		else if((_xSpeed > 0)&&(_ySpeed<0)||(_xSpeed < 0)&&(_ySpeed<0)||((_xSpeed == 0)&&(_ySpeed<0)))
		{
			framex = ++framex % BMP_COLUMS;
			framey = 3;
		}
		else if((_xSpeed > 0)&&(_ySpeed==0))
		{
			framex = ++framex % BMP_COLUMS;
			framey = 2;
		}
		else if((_xSpeed < 0)&&(_ySpeed==0))
		{
			framex = ++framex % BMP_COLUMS;
			framey = 1;
		}

	}
	public void onDraw(Canvas canvas, boolean Pausado)
	{
		if(!Pausado)
		{
			Update();
		}
		
		int srcX = width*framex;
		int srcY = height*framey;
		src = new Rect(srcX,srcY,srcX + width, srcY + height);
		dst = new Rect(_posx,_posy,_posx + width, _posy + height);
		canvas.drawBitmap(bmp, src, dst, null);
	}

	
	public boolean Colisao(float x, float y) {
		return x > _posx && x < _posx+width && y > _posy && y < _posy+height;
	}
	
	public boolean DiminuiVida()
	{
		vida--;
		if(vida <= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
