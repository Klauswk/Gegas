package Etc;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Tiles {
	
	private static final int Width = 32;
	private static final int Height = 32;
	private Bitmap Bmp;
	private Rect src;

	public Tiles(Bitmap bmp, Rect Src)
	{
		this.Bmp = bmp;
		this.src = Src;
	}
	public Bitmap getBmp() {
		return Bmp;
	}

	public void setBmp(Bitmap bmp) {
		this.Bmp = bmp;
	}

	public Rect getSrc() {
		return src;
	}

	public void setSrc(Rect src) {
		this.src = src;
	}

	/**public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(Bmp, src, new Rect(0,0,32,32), null);
	}**/
	public void onDraw(Rect dst,Canvas canvas)
	{
		canvas.drawBitmap(Bmp, src, dst, null);
	}
}
