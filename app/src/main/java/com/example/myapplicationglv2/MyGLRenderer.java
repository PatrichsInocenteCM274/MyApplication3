package com.example.myapplicationglv2;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.FloatBuffer;
import java.util.concurrent.TimeUnit;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private static final float CUBE_ROTATION_INCREMENT = 0.6f;
    private static final int REFRESH_RATE_FPS = 60;
    private static final float FRAME_TIME_MILLIS = TimeUnit.SECONDS.toMillis(1) / REFRESH_RATE_FPS;

    private final float[] mMVPMatrix;
    private final float[] mProjectionMatrix;
    private final float[] mViewMatrix;
    private final float[] mRotationMatrix;
    private final float[] mFinalMVPMatrix;
    private final float[] mFinalMVPMatrix_laser1;
    private final float[] mTranslateMatrix;
    /** Store our model data in a float buffer. */


    /** This will be used to pass in the texture. */
    private int mTextureUniformHandle;

    /** This will be used to pass in model texture coordinate information. */
    private int mTextureCoordinateHandle;

    /** Size of the texture coordinate data in elements. */
    private final int mTextureCoordinateDataSize = 2;

    /** This is a handle to our texture data. */
    private int mTextureDataHandle;
    private Squaree mSquare;
    private EasyCube laser1;
    private EasyCube mEcube[] = new EasyCube[22];

    float forfront1;
    float forfront2;
    float forfront3;
    float centerx;
    float centery;
    float centerz;
    float jugador1x;
    float jugador1y;
    float jugador1z;
    float laser1x;
    float laser1y;
    float laser1z;
    private float mCubeRotation;
    private float mCubeTranslation;
    private long mLastUpdateMillis;

    public MyGLRenderer(){
        mMVPMatrix = new float[16];
        mProjectionMatrix = new float[16];
        mTranslateMatrix = new float[16];
        mViewMatrix = new float[16];
        mRotationMatrix = new float[16];
        mFinalMVPMatrix = new float[16];
        mFinalMVPMatrix_laser1 = new float[16];

        forfront1 = 0.0f;
        forfront2 = -6.2f;
        forfront3= 2.5f;
        centerx=0.0f;
        centery=-1.2f;
        centerz=1.0f;
        jugador1x=0.0f;
        jugador1y=0.0f;
        jugador1z=0.0f;
        laser1x=0.0f;
        laser1y=0.0f;
        laser1z=0.0f;
        Matrix.setLookAtM(mViewMatrix, 0, -4.0f, -5.0f, -3.0f, centerx, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClearDepthf(1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);

        mEcube[0] = new EasyCube(-1.f,0.0f,0.5f,1.0f,3.0f,1.0f,0.0f,0.0f);
        mEcube[1] =  new EasyCube(1.f,0.0f,0.5f,1.0f,3.0f,1.0f,0.0f,0.0f);
        mEcube[2] = new EasyCube(-3.f,0.f,0.5f,1.0f,3.0f,1.0f,0.0f,0.0f);
        mEcube[3] = new EasyCube(3.0f,0.0f,0.5f,1.0f,3.0f,1.0f,0.0f,0.0f);

        mEcube[4] = new EasyCube(-1.f,2.0f,0.5f,1.0f,3.0f,0.0f,1.0f,0.0f);
        mEcube[5] =  new EasyCube(1.f,2.0f,0.5f,1.0f,3.0f,0.0f,1.0f,0.0f);
        mEcube[6] = new EasyCube(-3.f,2.f,0.5f,1.0f,3.0f,0.0f,1.0f,0.0f);
        mEcube[7] = new EasyCube(3.0f,2.0f,0.5f,1.0f,3.0f,0.0f,1.0f,0.0f);

        mEcube[8] = new EasyCube(-1.f,4.0f,0.5f,1.0f,3.0f,0.0f,0.0f,1.0f);
        mEcube[9] =  new EasyCube(1.f,4.0f,0.5f,1.0f,3.0f,0.0f,0.0f,1.0f);
        mEcube[10] = new EasyCube(-3.f,4.f,0.5f,1.0f,3.0f,0.0f,0.0f,1.0f);
        mEcube[11] = new EasyCube(3.0f,4.0f,0.5f,1.0f,3.0f,0.0f,0.0f,1.0f);

        mEcube[12] = new EasyCube(-1.f,6.0f,0.5f,1.0f,3.0f,1.0f,0.0f,0.0f);
        mEcube[13] =  new EasyCube(1.f,6.0f,0.5f,1.0f,3.0f,1.0f,0.0f,0.0f);
        mEcube[14] = new EasyCube(-3.f,6.f,0.5f,1.0f,3.0f,1.0f,0.0f,0.0f);
        mEcube[15] = new EasyCube(3.0f,6.0f,0.5f,1.0f,3.0f,1.0f,0.0f,0.0f);

        mEcube[16] = new EasyCube(-1.f,-2.0f,0.5f,1.0f,3.0f,0.239f,0.127f,0.26f);
        mEcube[17] =  new EasyCube(1.f,-2.0f,0.5f,1.0f,3.0f,0.239f,0.127f,0.26f);
        mEcube[18] = new EasyCube(-3.f,-2.f,0.5f,1.0f,3.0f,0.239f,0.127f,0.26f);
        mEcube[19] = new EasyCube(3.0f,-2.0f,0.5f,1.0f,3.0f,0.239f,0.127f,0.26f);
        //JUGADOR PRINCIPAL
        mEcube[20] = new EasyCube(0.0f,-3.0f,0.5f,0.2f,0.2f,0.0f,0.0f,1.0f);
        //JUGADOR ENEMIGO
        mEcube[21] = new EasyCube(0.0f,3.0f,0.2f,0.2f,0.2f,1.0f,0.0f,0.0f);

        mSquare = new Squaree(0.0f,0.0f,0.0f,15.0f);
        laser1 =  new EasyCube(0.0f,-3.0f,0.5f,0.05f,0.05f,0.0f,0.0f,1.0f);

        //mTextureDataHandle = TextureHelper.loadTexture(mActivityContext, R.drawable.bumpy_bricks_public_domain);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;
        GLES20.glViewport(0, 0, width, height);
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1.0f, 1.0f, 3.0f, 9.0f);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setLookAtM(mViewMatrix, 0, forfront1, forfront2, forfront3, centerx, centery, centerz, 0.0f, 0.0f, 1.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        mSquare.draw(mMVPMatrix);

        Matrix.setRotateM(mRotationMatrix, 0, mCubeRotation, 1.0f, 1.0f, 1.0f);
        Matrix.multiplyMM(mFinalMVPMatrix, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        Matrix.multiplyMM(mFinalMVPMatrix_laser1, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        Matrix.translateM(mFinalMVPMatrix,0,jugador1x,jugador1y,jugador1z);
        Matrix.translateM(mFinalMVPMatrix_laser1,0,laser1x,laser1y,laser1z);
        //mEcube[20].draw(mFinalMVPMatrix);
        mEcube[20].draw(mFinalMVPMatrix);
        laser1.draw(mFinalMVPMatrix_laser1);
        for (int i = 0; i < 20 ; i++) {
            mEcube[i].draw(mMVPMatrix);
        }
        mEcube[21].draw(mMVPMatrix);

        //mSquare = new Squaree(0.0f,4.0f,0.0f,15.0f);
        //updateCubeRotation();
        updateLookAt();
    }

    private void updateLookAt(){
        if (mLastUpdateMillis != 0) {
            //float factor = (SystemClock.elapsedRealtime() - mLastUpdateMillis) / FRAME_TIME_MILLIS;
            //mCubeRotation += CUBE_ROTATION_INCREMENT * factor;
            //forfront = forfront + 0.01f;
        }
        mLastUpdateMillis = SystemClock.elapsedRealtime();
    }

    private void updateCubeRotarion(){
        if (mLastUpdateMillis != 0) {
            float factor = (SystemClock.elapsedRealtime() - mLastUpdateMillis) / FRAME_TIME_MILLIS;
            mCubeRotation += CUBE_ROTATION_INCREMENT * factor;
        }
        mLastUpdateMillis = SystemClock.elapsedRealtime();
    }
    public float getfront1(){return forfront1;}
    public void setfront1(float front1){
        forfront1 = front1;
    }
    public float getfront2(){return forfront2;}
    public void setfront2(float front2){
        forfront2 = front2;
    }

    public float getfront3(){return forfront3;}
    public void setfront3(float front3){
        forfront3 = front3;
    }

    public float getcenterx(){return centerx;}
    public void setCenterx(float centeritox){
        centerx = centeritox;
    }

    public float getcentery(){return centery;}
    public void setCentery(float centeritoy){
        centery = centeritoy;
    }
    public float getcenterz(){return centerz;}
    public void setCenterz(float centeritoz){
        centerz = centeritoz;
    }

    public float getjugador1x(){return jugador1x;}
    public void setjugador1x(float jugadorcitox){
        jugador1x = jugadorcitox;
    }

    public float getjugador1y(){return jugador1y;}
    public void setjugador1y(float jugadorcitoy){
        jugador1y = jugadorcitoy;
    }

    public float getjugador1z(){return jugador1z;}
    public void setjugador1z(float jugadorcitoz){
        jugador1z = jugadorcitoz;
    }

    public float getlaser1x(){return laser1x;}
    public void setlaser1x(float lasercito1x){ laser1x = lasercito1x; }

    public float getlaser1y(){return laser1y;}
    public void setlaser1y(float lasercito1y){ laser1y = lasercito1y; }

    public float getlaser1z(){return laser1z;}
    public void setlaser1z(float lasercito1z){ laser1z = lasercito1z; }


}
