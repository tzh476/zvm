package com.zvm.interpreter;

import com.zvm.basestruct.TypeCode;
import com.zvm.basestruct.U1;
import com.zvm.basestruct.U2;
import com.zvm.classfile.attribute.code.ExceptionTable;
import com.zvm.utils.TypeUtils;
import com.zvm.basestruct.U4;
import com.zvm.classfile.attribute.CodeAttribute;
import com.zvm.classfile.MethodInfo;
import com.zvm.memory.MethodArea;

/**
 * 作为方法调用的运行时的具体描述
 */
public class CallSite {
    public U2 accessFlags;
    public U2 nameIndex;
    public U2 descriptorIndex;
    public U2 maxStack;
    public U2 maxLocals;
    public U4 codeLength;
    public U1[] code;
    public U2 exceptionTableLength;
    public ExceptionTable[] exceptionTables;

    public CallSite(){
        maxStack = new U2();
        maxStack.u2 = new byte[1];
        maxLocals = new U2();
        maxLocals.u2 = new byte[1];
        codeLength = new U4();
    }

    /**
     * 可能是native方法，需要设置max_stack、maxLocals、注入字节码等
     * @param methodInfo
     * @param returnType
     */
    public void setCallSiteOrNative(MethodInfo methodInfo, Integer returnType){
        accessFlags = methodInfo.accessFlags;
        /*对于native方法，注入字节码*/
        if(MethodArea.isNative(accessFlags)){
            maxStack.u2 = new byte[]{0x4};
            maxLocals.u2 = new byte[]{0x5};
            codeLength.u4 = new byte[]{0x2};
            code = new U1[2];
            code[0] = new U1();
            code[0].u1 = new byte[1];
            code[1] = new U1();
            code[1].u1 = new byte[1];
            code[0].u1[0] = (byte) (0x0ff & 0xfe);

            if(TypeCode.T_EXTRA_VOID.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xb1);// return
            }else if (TypeCode.T_EXTRA_OBJECT.equals(returnType)
                    || TypeCode.T_EXTRA_ARRAY.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xb0); //ARETURN
            }else if(TypeCode.T_DOUBLE.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xaf); //dreturb
            }else if (TypeCode.T_FLOAT.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xae);//FRETURN
            }else if(TypeCode.T_LONG.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xad);//LRETURN
            }else  {
                code[1].u1[0] = (byte) (0x0ff & 0xac); //IRETURN
            }
            return;
        }
        setCallSite(methodInfo);
    }

    public void setCallSite(MethodInfo methodInfo) {
        accessFlags = methodInfo.accessFlags;
        nameIndex = methodInfo.nameIndex;
        descriptorIndex = methodInfo.descriptorIndex;
        Integer count = TypeUtils.byteArr2Int(methodInfo.attributeCount.u2);

        for(Integer i = 0; i < count; i++){
            if(methodInfo.attributes[i] instanceof CodeAttribute){
                CodeAttribute codeAttribute = (CodeAttribute) methodInfo.attributes[i];
                maxStack = codeAttribute.maxStack;
                maxLocals = codeAttribute.maxLocals;
                codeLength = codeAttribute.codeLength;
                code = codeAttribute.code;
                exceptionTableLength = codeAttribute.exceptionTableLength;
                exceptionTables = codeAttribute.exceptionTables;
                break;
            }
        }
    }
}
