package com.firstopenglproject.android;


import static android.opengl.GLES20.*;

/**
 * Created by ylwang on 2017/5/12.
 */

public class ShaderHelper {
    public static final String TAG = "ShaderHelper";
    public static String VERTEX_SHADER =
            "attribute vec4 a_Position;            \n" +
                    "void main()                   \n" +
                    "{                             \n" +
                    "   gl_Position = a_Position;  \n" +
                    "   gl_PointSize = 10.0;        \n" +
                    "}                             \n";
    public static String FRAG_SHADER =
            "precision mediump float;               \n" +
                    "uniform vec4 u_Color;          \n" +
                    "void main(void)                \n" +
                    "{                              \n" +
                    "   gl_FragColor =u_Color;      \n" +
                    "}                              \n";

    /**
     * 创建顶点着色器
     *
     * @param shaderCode
     * @return
     */
    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    /**
     * 创建片段着色器
     *
     * @param shaderCode
     * @return
     */
    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        int shaderObjectId = glCreateShader(type);//创建shader
        if (shaderObjectId == 0) {
            Logs.e(TAG, "创建Shader失败！");
            return 0;
        }
        glShaderSource(shaderObjectId, shaderCode);//着色器关联源码
        glCompileShader(shaderObjectId);//编译着色器
        final int[] compileStatus = new int[1];
        //TODO：Android平台上OpenGL取值通用办法,长度为一的数组，放在0位置，然后数组传给OpenGL调用
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);//取出着色器编译状态
        Logs.d(TAG, "Result of compiling shader:\n" + glGetShaderInfoLog(shaderObjectId));
        if (compileStatus[0] == 0) {//如果着色器编译失败，直接删除。
            glDeleteShader(shaderObjectId);
            Logs.e(TAG, "编译Shader失败！");
            return 0;
        }
        return shaderObjectId;
    }

    /**
     * 给程序对象链接着色器
     *
     * @param vertexShader
     * @param fragmentShader
     * @return
     */
    public static int linkProgram(int vertexShader, int fragmentShader) {
        int programObjectId = glCreateProgram();//创建程序对象
        if (programObjectId == 0) {
            Logs.e(TAG, "创建program失败！");
            return 0;
        }
        //为程序对象附上着色器
        glAttachShader(programObjectId, vertexShader);
        glAttachShader(programObjectId, fragmentShader);
        glLinkProgram(programObjectId);//连接着色器
        int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        Logs.d(TAG, "Result of linking program :\n " + glGetProgramInfoLog(programObjectId));
        if (linkStatus[0] == 0) {//联结失败，直接删除
            glDeleteProgram(programObjectId);
            Logs.e(TAG, "创建program失败！");
            return 0;
        }
        return programObjectId;
    }

    /**
     * 验证程序是否有效
     *
     * @param programObjectId
     * @return
     */
    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Logs.d(TAG, "Result of validating program:\n" + validateStatus[0]
                + "\nLog = " + glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }

}
