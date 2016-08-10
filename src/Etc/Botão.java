package Etc;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.gegas.ActivityGame.FastRenderView;

public class Botão {

	private int _posx;
	private int _posy;
	private FastRenderView game;
	private Bitmap bmp;
	private Rect dst;
	private Rect src;
	
	public Botão()
	{
		super();
		dst = new Rect();
		src = new Rect();
	}
	public Botão(int _posx, int _posy,
			FastRenderView game, Bitmap bmp) {
		super();
		this._posx = _posx;
		this._posy = _posy;
		this.game = game;
		this.bmp = bmp;
		dst = new Rect();
		src = new Rect();
	}
	
	public Botão(FastRenderView game, Bitmap bmp) {
		super();
		this.game = game;
		this.bmp = bmp;
		dst = new Rect();
		src = new Rect();
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
	
	public Bitmap getBmp() {
		return bmp;
	}

	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}

	public FastRenderView getGame() {
		return game;
	}

	public void setGame(FastRenderView game) {
		this.game = game;
	}

	public void onDraw(Canvas canvas)
	{
		src.left = 0;
		src.top = 0;
		src.right = bmp.getWidth();
		src.bottom = bmp.getHeight();
		
		dst.left = _posx;
		dst.top = _posy;
		dst.right = _posx + bmp.getWidth();
		dst.bottom = _posy + bmp.getHeight();
		
		canvas.drawBitmap(bmp,src , dst, null);
	}

	public boolean Colisao(float x, float y) {
		return x > _posx && x < _posx+bmp.getWidth() && y > _posy && y < _posy+bmp.getHeight();
	}

}
