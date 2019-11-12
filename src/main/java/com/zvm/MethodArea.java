package com.zvm;

import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;
import com.zvm.runtime.struct.Slot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zvm.JavaClass.CLASS_BYTECODE_FILE_MAX;

public class MethodArea {
    //public JavaClass[] javaClasses = new JavaClass[10];
    Map<String,JavaClass> javaClasses = new HashMap<>();
    List<String> loadedClasses = new ArrayList<>();
    List<String> linkedClasses = new ArrayList<>();
    List<String> initedClasses = new ArrayList<>();


    ZvmClassLoader zvmClassLoader = new ZvmClassLoader();

    /**
     * 加载类
     * @param classPath
     */
    public JavaClass loadClass(String classPath){
        JavaClass javaClass = new JavaClass(classPath);

        byte[] bytecode = readClass(classPath);

        javaClass.readBytecode2ClassFile(bytecode);
        javaClasses.put(classPath, javaClass);
        return javaClass;
    }

    private byte[] readClass(String path)  {
        path = Cmd.processPath(path);

        File classFile = new File(path);

        byte[] byteCode= new byte[CLASS_BYTECODE_FILE_MAX];

        try {
            FileInputStream in = new FileInputStream(classFile);
            in.read(byteCode);
        } catch (FileNotFoundException e) {
            System.out.println("file not found " + path);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println("读取 "+ path + " 失败");
            e.printStackTrace();
            return null;
        }
        return byteCode;
    }

    public JavaClass findClass(String className){
        boolean hasClass =  javaClasses.containsKey(className);
        if(hasClass){
            return javaClasses.get(className);
        }
        return null;
    }

    /**
     * 链接类
     * @param
     */
    public void linkClass(String classPath){
        if(linkedClasses.contains(classPath)){
            return;
        }
        JavaClass javaClass = findClass(classPath);
        verification();
        prepare(javaClass);
        resolved();
    }

    /**
     * 验证
     */
    public void verification(){

    }

    /**
     * 准备
     * @param javaClass
     */
    public void prepare(JavaClass javaClass){
        calcInstanceFieldSlotIds(javaClass);
        calcStaticFieldSlotIds(javaClass);
        allocAndInitStaticVars(javaClass);
    }

    private void allocAndInitStaticVars(JavaClass javaClass) {
        ClassFile classFile = javaClass.getClassFile();
        field_info[] field_infos = classFile.fields;
        CONSTANT_Base[] cp =  classFile.constant_pool.cp_info;
        Integer staticSlotCount = javaClass.staticSlotCount;
        javaClass.staticVars = new StaticVars(staticSlotCount);
        StaticVars staticVars = javaClass.staticVars;
        for (int i = 0, len = field_infos.length; i < len; i++){
            field_info field_info = field_infos[i];
            u2 access_flags = field_info.access_flags;
            int constValueIndex = getConstValueIndex(field_info.attributes);

            if(isFinal(access_flags) && isStatic(access_flags)){
                int slotId = field_info.slotId;
                int descriptorIndex = TypeUtils.byteArr2Int(field_infos[i].descriptor_index.u2);
                CONSTANT_Utf8 constant_utf8 = (CONSTANT_Utf8) cp[descriptorIndex - 1];
                String descriptorName = TypeUtils.u12String(constant_utf8.bytes);
                char s = descriptorName.charAt(0);
                if(s == 'Z' || s == 'B' || s == 'C' || s == 'S' || s == 'I'){
                    CONSTANT_Integer constant_integer = (CONSTANT_Integer) cp[constValueIndex];
                    int value = TypeUtils.byteArr2Int(constant_integer.bytes.u4);
                    staticVars.putIntByIndex(slotId, value);
                }else if ( s == 'J' ){
                    CONSTANT_Long constant_long = (CONSTANT_Long) cp[constValueIndex];
                    u4 highBytes = constant_long.high_bytes;
                    u4 lowBytes = constant_long.low_bytes;
                    staticVars.putLong(slotId, TypeUtils.byteArr2Int(highBytes.u4), TypeUtils.byteArr2Int(lowBytes.u4));
                }else if (s == 'F'){
                    CONSTANT_Float constant_float = (CONSTANT_Float) cp[constValueIndex];
                    u4 ldcBytes = constant_float.bytes;
                    float value = TypeUtils.byteArr2Float(ldcBytes.u4);
                    staticVars.putFloat(slotId, value);
                }else if (s == 'D'){
                    CONSTANT_Double constant_double = (CONSTANT_Double) cp[constValueIndex];
                    u4 highBytes = constant_double.high_bytes;
                    u4 lowBytes = constant_double.low_bytes;
                    staticVars.putLong(slotId, TypeUtils.byteArr2Int(highBytes.u4), TypeUtils.byteArr2Int(lowBytes.u4));
                }else if(s == 'L' || s== '['){
                    /*to do*/
                }

            }
        }
    }

    private int getConstValueIndex(Attribute_Base[] attributes) {
        int constValueIndex = 0;
        int len = attributes.length;
        for(int i = 0; i < len; i++){
            if(attributes[i] instanceof ConstantValue_attribute){
                ConstantValue_attribute constantValue_attributes = (ConstantValue_attribute) attributes[i];
                constValueIndex = TypeUtils.byteArr2Int(constantValue_attributes.constantvalue_index.u2);
                return constValueIndex;
            }
        }
        return constValueIndex;
    }

    private void calcStaticFieldSlotIds(JavaClass javaClass) {
        ClassFile classFile = javaClass.getClassFile();
        field_info[] field_infos = classFile.fields;
        CONSTANT_Base[] cp =  classFile.constant_pool.cp_info;
        int len = field_infos.length;
        int slotId = 0;
        for(int i = 0; i < len; i ++){
            u2 access_flags = field_infos[i].access_flags;
            if(isStatic(access_flags)){
                field_infos[i].slotId = slotId;
                slotId ++;
                int descriptorIndex = TypeUtils.byteArr2Int(field_infos[i].descriptor_index.u2);
                CONSTANT_Utf8 constant_utf8 = (CONSTANT_Utf8) cp[descriptorIndex - 1];
                String descriptorName = TypeUtils.u12String(constant_utf8.bytes);
                if(isLongOrDouble(descriptorName)){
                    slotId++;
                }
            }
        }
        javaClass.staticSlotCount = slotId;
    }

    private boolean isLongOrDouble(String descriptorName) {
        if(descriptorName.startsWith("J") || descriptorName.startsWith("D")){
            return true;
        }
        return false;
    }

    static public boolean isStatic(u2 access_flags) {
        int flags = TypeUtils.byteArr2Int(access_flags.u2);
        if(0 != (flags & AccessFlag.ACC_STATIC)){
            return true;
        }
        return false;
    }

    private boolean isFinal(u2 access_flags) {
        int flags = TypeUtils.byteArr2Int(access_flags.u2);
        if(0 != (flags & AccessFlag.ACC_FINAL)){
            return true;
        }
        return false;
    }

    private void calcInstanceFieldSlotIds(JavaClass javaClass) {

    }

    /**
     * 解析
     */
    public void resolved(){

    }

    /**
     * 初始化类
     */
    public void initClass(String classPath){

    }
}
