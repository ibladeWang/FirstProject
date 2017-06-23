package com.firstopenglproject.android;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by ylwang on 2017/4/10.
 */
public class FirstOpenGLRenderer implements GLSurfaceView.Renderer {
    private static final int BYTES_PER_FLOAT = 4;
    private FloatBuffer vertexData;

    public FirstOpenGLRenderer() {
        float[] tableVertices = {
                //Triangle1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,
                //Triangle2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,
                //Line1
                -0.5f, 0f,
                0.5f, 0f,
                //Mallets
                0f, -0.25f,
                0f, 0.25f,
                //test Triangle
                -0.25f, -0.3f,
                0.1f, 0.45f,
                -0.3f, 0.3f,

                -0.3f, -0.3f,
                0.3f, -0.3f,
                0.3f, 0.31f,
        };
        vertexData = ByteBuffer
                .allocateDirect(tableVertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVertices);
    }

    private int program;
    private static final String U_COLOR = "u_Color";
    private int uColorLocation;
    private int aPositionLocation;
    private static final String A_POSITION = "a_Position";
    private static final int POSITION_COMPONENT_COUNT = 2;//一个顶点的两个分量x，y

    /**
     * Called when the surface is created or recreated.
     *
     * @param gl     the GL interface. Use <code>instanceof</code> to
     *               test if the interface supports GL11 or higher interfaces.
     * @param config the EGLConfig of the created surface. Can be used
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        int vertexShader = ShaderHelper.compileVertexShader(ShaderHelper.VERTEX_SHADER);
        int fragmentShader = ShaderHelper.compileFragmentShader(ShaderHelper.FRAG_SHADER);
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        ShaderHelper.validateProgram(program);//验证是否有效
        glUseProgram(program);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        vertexData.position(0);//位置放到首位，不然默认末位会崩溃。
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);
    }

    /**
     * Called when the surface changed size.
     *
     * @param gl     the GL interface. Use <code>instanceof</code> to
     *               test if the interface supports GL11 or higher interfaces.
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);//视图尺寸
    }

    /**
     * Called to draw the current frame.
     *
     * @param gl the GL interface. Use <code>instanceof</code> to
     *           test if the interface supports GL11 or higher interfaces.
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);//RGBA四个通道
        glDrawArrays(GL_TRIANGLES, 0, 6);
        //画棒球
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);

        //画测试框
        glUniform4f(uColorLocation, 0.5f, 0.5f, 0.0f, 1.0f);//RGBA四个通道
        glDrawArrays(GL_TRIANGLES, 10, 6);
    }
}
