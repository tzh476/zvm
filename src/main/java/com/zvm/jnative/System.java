package com.zvm.jnative;

import com.zvm.memory.ArrayFields;
import com.zvm.runtime.JavaFrame;
import com.zvm.runtime.LocalVars;
import com.zvm.runtime.OperandStack;
import com.zvm.runtime.RunTimeEnv;
import com.zvm.runtime.struct.JObject;

public class System {
    /**
     * 本地方法 arraycopy
     * @param javaFrame
     */
    public static void arraycopy(RunTimeEnv runTimeEnv, JavaFrame javaFrame) {
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;
        JObject src = localVars.getJObject(0);
        int srcPos = localVars.getIntByIndex(1);
        JObject dest = localVars.getJObject(2);
        int descPos = localVars.getIntByIndex(3);
        int length = localVars.getIntByIndex(4);

        ArrayFields srcFields = runTimeEnv.javaHeap.arrayContainer.get(src.offset);
        ArrayFields destFields = runTimeEnv.javaHeap.arrayContainer.get(dest.offset);

        for(int i = 0; i < length; i ++){
            /*暂时只考虑char*/
            destFields.putChar(descPos + i, srcFields.getChar(srcPos + i));
        }
    }
}
