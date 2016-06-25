package cn.wps.graphics.shape3d;

import java.util.ArrayList;

import org.example.localbrowser.AnyObjPool;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

public abstract class ModelBase implements PathDivision.DivisionListener {
	protected int mCacheArrayCount; // 缓存总float数
	protected float[] mVerts;
	protected float[] mTexs;
	protected int[] mColors; // 虽然只用到一半，但要保留和顶点一样的长度，否则崩溃
	protected short[] mIndices;
    
    protected ArrayList<Vector3f> mListVerts = new ArrayList<Vector3f>();
    protected ArrayList<Vector3f> mListNormals = new ArrayList<Vector3f>();
    protected MatrixState mMatrixState = new MatrixState();
    
    protected Paint mPaint = new Paint();
    
    protected AnyObjPool mAnyObjPool = AnyObjPool.getPool();
    
	public ModelBase() {
		
	}
	
	public void init(RectF viewPort) {
		mMatrixState.init(viewPort);
		initVerts();
	}
	
	protected void initVerts() {
		mListVerts.clear();
		mListNormals.clear();
		PathDivision division = new PathDivision(this);
		division.makeVertexs();
		division.dispose();
	}
	
	public void addVertex(Vector3f v, Vector3f n) {
		if (v == null || n == null) {
			return;
		}
		mListVerts.add(v);
		mListNormals.add(n);
	}
	
	public abstract Path getShapePath();
	
	public void draw(Canvas canvas) {
		drawPoints(canvas);
//		Bitmap textureBimatp = getTextureBitmap();
//		Shader s = new BitmapShader(textureBimatp, Shader.TileMode.CLAMP,
//                Shader.TileMode.CLAMP);
//		mPaint.setShader(s);
//		
//		canvas.save();
//        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, mCacheArrayCount, mVerts, 0,
//        		mTexs, 0, mColors, 0, mIndices, 0, mIndices == null ? 0 : mIndices.length, mPaint);
//        mPaint.setShader(null);
//        canvas.restore();
	}
	
	private void drawPoints(Canvas canvas) {
		
		mPaint.setStrokeWidth(5);
		for (int i = 0, size = mListVerts.size(); i < size; i++) {
			if (i == size - 1) {
				mPaint.setColor(0xffff0000);
			} else {
				mPaint.setColor(0xff00ff00);
			}
			Vector3f v = mListVerts.get(i);
			canvas.drawPoint(v.x, v.y, mPaint);
		}
	}
	
	protected abstract Bitmap getTextureBitmap();
}
