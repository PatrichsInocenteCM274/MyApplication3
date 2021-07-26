package com.example.myapplicationglv2;

import android.content.Context;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;


import android.widget.Toast;

public class MyGLSurfaceView extends GLSurfaceView{
    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context){
        super(context);
        setEGLContextClientVersion(2);
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    }
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                /*
                float dx = ;
                float dy = y - mPreviousY;
                */
                if (y < getHeight() / 3 && x < getWidth() / 2) {

                    mRenderer.setfront1(mRenderer.getfront1() + 0.05f);
                    mRenderer.setCenterx(mRenderer.getcenterx() + 0.05f);
                }
                if (y < getHeight() / 3 && x > getWidth() / 2) {
                    mRenderer.setfront1(mRenderer.getfront1() - 0.05f);
                    mRenderer.setCenterx(mRenderer.getcenterx() - 0.05f);
                }

                if (y > getHeight() / 3  && y < 2*getHeight() / 3  && x < getWidth() / 2) {
                    mRenderer.setfront2(mRenderer.getfront2() + 0.05f);
                    mRenderer.setCentery(mRenderer.getcentery() + 0.05f);
                }
                if (y > getHeight() / 3 && y < 2*getHeight() / 3 && x > getWidth() / 2) {
                    mRenderer.setfront2(mRenderer.getfront2() - 0.05f);
                    mRenderer.setCentery(mRenderer.getcentery() - 0.05f);
                }

                if (y > 2*getHeight() / 3 && x < getWidth() / 2) {
                    mRenderer.setfront3(mRenderer.getfront3() + 0.05f);
                    mRenderer.setCenterz(mRenderer.getcenterz() + 0.05f);
                }
                if (y > 2*getHeight() / 3 && x > getWidth() / 2) {
                    mRenderer.setfront3(mRenderer.getfront3() - 0.05f);
                    mRenderer.setCenterz(mRenderer.getcenterz() - 0.05f);
                }

                requestRender();
        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }



}
