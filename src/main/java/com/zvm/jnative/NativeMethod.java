package com.zvm.jnative;

import com.zvm.runtime.JavaFrame;
import com.zvm.runtime.RunTimeEnv;

public interface NativeMethod {
    /**
     * 调用本地方法
     * @param javaFrame
     */
    void invoke(RunTimeEnv runTimeEnv, JavaFrame javaFrame);
}
