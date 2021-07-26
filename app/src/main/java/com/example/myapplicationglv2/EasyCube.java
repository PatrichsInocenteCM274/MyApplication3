package com.example.myapplicationglv2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class EasyCube {
    private final float V1[];
    /*
    private final float COLORS[] = {
            1.0f*colorR, 1.0f*colorG, 1.0f*colorB,
            0.85f*colorR, 1.0f*colorG, 1.0f*colorB,
            0.0f, 0.0f, 0.0f,
            1.0f*colorR, 1.0f*colorG, 1.0f*colorB,
            0.85f*colorR, 1.0f*colorG, 1.0f*colorB,
            0.0f, 0.0f, 0.0f,
            1.0f*colorR, 1.0f*colorG, 1.0f*colorB,
            0.85f*colorR, 1.0f*colorG, 1.0f*colorB,
    };*/
    private final byte INDICES[] = {
            0, 1, 3, 3, 1, 2, //FRENTE
            0, 1, 4, 4, 5, 1, //ABAJO
            1, 2, 5, 5, 6, 2,  //DERECHA
            2, 3, 6, 6, 7, 3,  //ARRIBA
            3, 7, 4, 4, 3, 0,  //IZQUIERDA
            4, 5, 7, 7, 6, 5,  //TRASERA
    };
    private final int COORDS_PER_VERTEX = 3;
    private final int VALUE_PER_COLOR = 3;
    private final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4;
    private final int COLOR_STRIDE = VALUE_PER_COLOR * 4;

    private final String VERTEX_SHADER_CODE =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec4 vColor;" +
                    "varying vec4 _vColor;" +
                    "void main() {" +
                    "  _vColor = vColor;" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String FRAGMENT_SHADER_CODE =
            "precision mediump float;" +
                    "varying vec4 _vColor;" +
                    "void main() {" +
                    "  gl_FragColor = _vColor;" +
                    "}";

    private final FloatBuffer mVertexBuffer;
    private final FloatBuffer mColorBuffer;
    private final ByteBuffer mIndexBuffer;
    private final int mProgram;
    private final int mPositionHandle;
    private final int mColorHandle;
    private final int mMVPMatrixHandle;

    public EasyCube(float x_, float y_, float z_, float L, float H, float colorR, float colorG, float colorB){
        V1 = new float[24];
        V1[0]=(x_-(L/2));V1[1] =(y_-(L/2));V1[2] =(z_-(L/2));
        V1[3]=(x_+(L/2));V1[4] =(y_-(L/2));V1[5] =(z_-(L/2));
        V1[6]=(x_+(L/2));V1[7] =(y_+(L/2));V1[8] =(z_-(L/2));
        V1[9]=(x_-(L/2));V1[10] =(y_+(L/2));V1[11] =(z_-(L/2));
        V1[12]=(x_-(L/2));V1[13] =(y_-(L/2));V1[14] =(z_+(H/2));
        V1[15]=(x_+(L/2));V1[16] =(y_-(L/2));V1[17] =(z_+(H/2));
        V1[18]=(x_+(L/2));V1[19] =(y_+(L/2));V1[20] =(z_+(H/2));
        V1[21]=(x_-(L/2));V1[22] =(y_+(L/2));V1[23] =(z_+(H/2));
        float COLORS[] = {
                1.0f*colorR, 1.0f*colorG, 1.0f*colorB,
                0.85f*colorR, 1.0f*colorG, 1.0f*colorB,
                0.0f, 0.0f, 0.0f,
                1.0f*colorR, 1.0f*colorG, 1.0f*colorB,
                0.85f*colorR, 1.0f*colorG, 1.0f*colorB,
                0.0f, 0.0f, 0.0f,
                1.0f*colorR, 1.0f*colorG, 1.0f*colorB,
                0.85f*colorR, 1.0f*colorG, 1.0f*colorB,
        };
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(V1.length * 4);

        byteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = byteBuffer.asFloatBuffer();
        mVertexBuffer.put(V1);
        mVertexBuffer.position(0);

        byteBuffer = ByteBuffer.allocateDirect(COLORS.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        mColorBuffer = byteBuffer.asFloatBuffer();
        mColorBuffer.put(COLORS);
        mColorBuffer.position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(INDICES.length);
        mIndexBuffer.put(INDICES);
        mIndexBuffer.position(0);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER_CODE));
        GLES20.glAttachShader(
                mProgram, loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE));
        GLES20.glLinkProgram(mProgram);

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "vColor");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void draw(float[] mvpMatrix) {
        GLES20.glUseProgram(mProgram);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(
                mPositionHandle, 3, GLES20.GL_FLOAT, false, VERTEX_STRIDE, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(
                mColorHandle, 4, GLES20.GL_FLOAT, false, COLOR_STRIDE, mColorBuffer);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, INDICES.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
    }

    public static int loadTexture(final Context context, final int resourceId)
    {
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }

    private static int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
