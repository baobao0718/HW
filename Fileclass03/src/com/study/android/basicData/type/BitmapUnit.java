package com.study.android.basicData.type;

import android.graphics.Bitmap;

import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;

//import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 位图类型
 * @author: tanqiang
 * @create-time: 2008-7-15
 */
public class BitmapUnit extends DataUnit {

	private static final long serialVersionUID = 1L;
	
	//private BufferedImage bitmap = null;
	private Bitmap bitmap = null;
	
	public BitmapUnit(short width, short height) {
		super(width, height, DataType.TYPE_BITMAP);
	}

	public BitmapUnit(BitmapUnit bitmapUnit) {
		super(bitmapUnit.getWidth(), bitmapUnit.getHeight(), DataType.TYPE_BITMAP);
		this.setWidth(bitmapUnit.getWidth());
		this.setHeight(bitmapUnit.getHeight());
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}

	//public void setBitmap(BufferedImage img) {
	public void setBitmap(Bitmap img) {
		this.bitmap = img;
	}

	public BitmapUnit clone(){                  //??在做什么？
		return new BitmapUnit(this);
	}
	
	/**
	 * 取位图的像素
	 * @return int[height][width]：像素整型值的二维数组保存时可能要用到
	 */
	//public int[][] getPixel() {
	//	if(this.bitmap == null) {
	//		return null;
	//	}
	//	int[][] pixel = new int[this.getHeight()][this.getWidth()];
		//for(int y = 0; y < this.getHeight(); ++y) {
		//	for(int x = 0; x < this.getWidth(); ++x) {
		//		pixel[y][x] = this.bitmap.getRGB(x, y);
		//		bitmap.getPixels(pixels, offset, stride, x, y, width, height)
		//	}
		//}
		
	//	return pixel;
	//}

	public List<BasePoint> getPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addPoint(BasePoint p, int... color) {
		// TODO Auto-generated method stub
		
	}
	/**
	public static java.nio.ByteBuffer loadImage(int id) 
	{ 
		int imageWidth = -1;
		int  imageHeight = -1;
		 java.io.InputStream is = context.getResources().openRawResource(id); 
		 Bitmap image = BitmapFactory.decodeStream(is); 
		 imageWidth = image.width(); 
		 imageHeight = image.height();
		  int[] pixels = new int[imageWidth]; 
		  ByteBuffer buf = ByteBuffer.allocateDirect(imageWidth*imageHeight*4); 
		  for (int y = 0; y < imageHeight; y ) 
		  { 
		  image.getPixels(pixels, 0, imageWidth, 0, y, imageWidth, 1);
		   for (int x = 0; x < imageWidth; x )
		    { 
		    int argb = pixels[x]; 
		    int r = 0xff0000 & (argb >> 16);
		    int g = 0x00ff00 & (argb >> 8); 
		    int b = 0x0000ff & (argb >> 0); 
		    buf.put((byte)r); 
		    buf.put((byte)g);
		    buf.put((byte)b);
			buf.put((byte)0xff);
			} 
		   } 
			buf.rewind(); 
			return buf; 
		}
		**/
	

}



//这个用来实现J2ME中类似getRGB方法的




