package com.zvm.classfile;

import com.zvm.basestruct.U1;
import com.zvm.basestruct.U2;
import com.zvm.classfile.attribute.innerClasses.Classes;
import com.zvm.utils.TypeUtils;
import com.zvm.basestruct.U4;
import com.zvm.classfile.attribute.*;
import com.zvm.classfile.attribute.LocalVariableTable.LocalVariable;
import com.zvm.classfile.attribute.LocalVariableTypeTable.LocalVariableType;
import com.zvm.classfile.attribute.RuntimeVisibleParameterAnnotations.ParameterAnnotation;
import com.zvm.classfile.attribute.code.ExceptionTable;
import com.zvm.classfile.attribute.lineNumberTable.LineNumber;
import com.zvm.classfile.attribute.runtimeVisibleAnnotations.*;
import com.zvm.classfile.attribute.stackmaptable.*;
import com.zvm.classfile.attribute.stackmaptable.verificationtypeinfo.*;
import com.zvm.classfile.constantpool.*;

@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
public class ClassFile {
    public U4 magic;
    public U2 minorVersion;
    public U2 majorVersion;
    public U2 constantPoolCount;
    public CpInfo constantPool = new CpInfo();
    public U2 accessFlags;
    public U2 thisClass;
    public U2 superClass;
    public U2 interfaceCount;
    public U2[] interfaces;
    public U2 fieldCount;
    public FieldInfo[] fields ;
    public U2 methodsCount;
    public MethodInfo[] methods ;
    public U2 attributesCount;
    public AttributeBase[] attributes ;

    /**
     * 将字节码转换为ClassFile
     * @param bytecode
     */
    public void processByteCode(byte[] bytecode){
        IOUtils.bytecode = bytecode;
        IOUtils.index = 0;
        magic = IOUtils.readU4();
        minorVersion = IOUtils.readU2();
        majorVersion = IOUtils.readU2();
        constantPoolCount = IOUtils.readU2();

        Integer poolSize = TypeUtils.byteArr2Int(constantPoolCount.u2);
        /*读取常量池*/
        processConstantPool(constantPool, poolSize);

        accessFlags = IOUtils.readU2();
        thisClass = IOUtils.readU2();
        superClass = IOUtils.readU2();
        interfaceCount = IOUtils.readU2();
        Integer interfaceCountInteger = TypeUtils.byteArr2Int(interfaceCount.u2);
        interfaces = new U2[interfaceCountInteger];
        for(Integer i = 0; i < interfaceCountInteger; i ++){
            interfaces[i] = IOUtils.readU2();
        }

        fieldCount = IOUtils.readU2();
        Integer fieldCountInteger = TypeUtils.byteArr2Int(fieldCount.u2);
        fields = processFields( fieldCountInteger);

        methodsCount = IOUtils.readU2();
        Integer methodsCountInteger = TypeUtils.byteArr2Int(methodsCount.u2);
        methods = processMethods( methodsCountInteger);

        attributesCount = IOUtils.readU2();
        Integer tempAttributesCount = TypeUtils.byteArr2Int(attributesCount.u2);
        attributes =  new AttributeBase[tempAttributesCount];
        for(Integer i = 0; i < tempAttributesCount; i ++){
            processAttribute( i, attributes);
        }
    }

    /**
     * 解析字节码中的方法
     * @param methodsCountInteger
     * @return
     */
    private MethodInfo[] processMethods(Integer methodsCountInteger) {
        MethodInfo[] methodInfos = new MethodInfo[methodsCountInteger];
        for(Integer i = 0; i < methodsCountInteger; i ++){
            methodInfos[i] = new MethodInfo();
            methodInfos[i].accessFlags = IOUtils.readU2();
            methodInfos[i].nameIndex = IOUtils.readU2();
            methodInfos[i].descriptorIndex = IOUtils.readU2();
            methodInfos[i].attributeCount = IOUtils.readU2();
            Integer tempAttributesCount = TypeUtils.byteArr2Int(methodInfos[i].attributeCount.u2);
            methodInfos[i].attributes =  new AttributeBase[tempAttributesCount];
            for(Integer j = 0; j < tempAttributesCount; j ++){
                processAttribute( j, methodInfos[i].attributes);
            }
        }
        return methodInfos;
    }

    /**
     * 解析字节码中的字段
     * @param fieldCountInteger
     * @return
     */
    private FieldInfo[] processFields(Integer fieldCountInteger) {
        FieldInfo[] fieldInfos = new FieldInfo[fieldCountInteger];
        for(Integer i = 0; i < fieldCountInteger; i ++){
            fieldInfos[i] = new FieldInfo();
            fieldInfos[i].accessFlags = IOUtils.readU2();
            fieldInfos[i].nameIndex = IOUtils.readU2();
            fieldInfos[i].descriptorIndex = IOUtils.readU2();
            fieldInfos[i].attributeCount = IOUtils.readU2();
            Integer tempAttributesCount = TypeUtils.byteArr2Int(fieldInfos[i].attributeCount.u2);
            fieldInfos[i].attributes =  new AttributeBase[tempAttributesCount];
            for(Integer j = 0; j < tempAttributesCount; j ++){
                processAttribute( j, fieldInfos[i].attributes);
            }
        }
        return fieldInfos;
    }

    /**
     * 解析字节码中的常量池
     * @param constantPool
     * @param poolSize
     */
    private void processConstantPool(CpInfo constantPool, Integer poolSize) {
        constantPool.cpInfo = new ConstantBase[poolSize];
        for(Integer i = 0; i < poolSize - 1; i++){
            U1 tag = IOUtils.readU1();

            Integer integerTag = TypeUtils.byteArr2Int(tag.u1);
            if(integerTag == 1){
                ConstantUtf8 constantUtf8 = new ConstantUtf8();
                constantUtf8.tag = tag;
                constantUtf8.length = IOUtils.readU2();
                Integer utf8Len = TypeUtils.byteArr2Int( constantUtf8.length.u2);
                constantUtf8.bytes = new U1[utf8Len];
                for(Integer j = 0; j < utf8Len; j ++){
                    constantUtf8.bytes[j] = IOUtils.readU1();
                }
                constantPool.cpInfo[i] = constantUtf8;
            } else if(integerTag == 3){
                ConstantInteger constantInteger = new ConstantInteger();
                constantInteger.tag = tag;
                constantInteger.bytes = IOUtils.readU4();
                constantPool.cpInfo[i] = constantInteger;
            } else if(integerTag == 4){
                ConstantFloat constantFloat = new ConstantFloat();
                constantFloat.tag = tag;
                constantFloat.bytes = IOUtils.readU4();
                constantPool.cpInfo[i] = constantFloat;
            }else if (integerTag == 5){
                ConstantLong constantLong = new ConstantLong();
                constantLong.tag = tag;
                constantLong.highBytes = IOUtils.readU4();
                constantLong.lowBytes = IOUtils.readU4();
                /*double和long类型会跳过一个常量标识*/
                constantPool.cpInfo[i ++] = constantLong;
            }else if(integerTag == 6){
                ConstantDouble constantDouble = new ConstantDouble();
                constantDouble.tag = tag;
                constantDouble.highBytes = IOUtils.readU4();
                constantDouble.lowBytes = IOUtils.readU4();
                /*double和long类型会跳过一个常量标识*/
                constantPool.cpInfo[i ++] = constantDouble;
            }else if(integerTag == 7){
                ConstantClass constantClass = new ConstantClass();
                constantClass.tag = tag;
                constantClass.nameIndex = IOUtils.readU2();
                constantPool.cpInfo[i] = constantClass;
            }else if(integerTag == 8){
                ConstantString constantString = new ConstantString();
                constantString.tag = tag;
                constantString.stringIndex = IOUtils.readU2();
                constantPool.cpInfo[i] = constantString;
            }else if(integerTag == 9){
                ConstantFieldref constantFieldref = new ConstantFieldref();
                constantFieldref.tag = tag;
                constantFieldref.classIndex = IOUtils.readU2();
                constantFieldref.nameAndTypeIndex = IOUtils.readU2();
                constantPool.cpInfo[i] = constantFieldref;
            }else if(integerTag == 10){
                ConstantMethodref constantMethodref = new ConstantMethodref();
                constantMethodref.tag = tag;
                constantMethodref.classIndex = IOUtils.readU2();
                constantMethodref.nameAndTypeIndex = IOUtils.readU2();
                constantPool.cpInfo[i] = constantMethodref;
            }else if(integerTag == 11){
                ConstantInterfacemethodref constantInterfaceMethodref = new ConstantInterfacemethodref();
                constantInterfaceMethodref.tag = tag;
                constantInterfaceMethodref.classIndex = IOUtils.readU2();
                constantInterfaceMethodref.nameAndTypeIndex = IOUtils.readU2();
                constantPool.cpInfo[i] = constantInterfaceMethodref;
            }else if(integerTag == 12){
                ConstantNameandtype constantNameAndType = new ConstantNameandtype();
                constantNameAndType.tag = tag;
                constantNameAndType.nameIndex = IOUtils.readU2();
                constantNameAndType.descriptorIndex = IOUtils.readU2();
                constantPool.cpInfo[i] = constantNameAndType;
            }else if(integerTag == 15){
                ConstantMethodhandle constantMethodHandle = new ConstantMethodhandle();
                constantMethodHandle.tag = tag;
                constantMethodHandle.referenceKind = IOUtils.readU1();
                constantMethodHandle.referenceIndex = IOUtils.readU2();
                constantPool.cpInfo[i] = constantMethodHandle;
            }else if(integerTag == 16){
                ConstantMethodtype constantMethodType = new ConstantMethodtype();
                constantMethodType.tag = tag;
                constantMethodType.descriptorIndex = IOUtils.readU2();
                constantPool.cpInfo[i] = constantMethodType;
                //}else if(integer_tag == 17){
            }else if(integerTag == 18){
                ConstantInvokedynamic constantInvokeDynamic = new ConstantInvokedynamic();
                constantInvokeDynamic.tag = tag;
                constantInvokeDynamic.bootstrapMethodAttrIndex = IOUtils.readU2();
                constantInvokeDynamic.nameAndTypeIndex = IOUtils.readU2();
                constantPool.cpInfo[i] = constantInvokeDynamic;
            }else {
                System.out.println("constantPool integer_tag " + integerTag);
                return;
            }
        }
    }

    /*属性的各种类型，共23个*/
    @SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
    public static String[] attributeStrs = {
            "ConstantValue",
            "Code",
            "StackMapTable",
            "Exceptions",
            "InnerClasses",
            "EnclosingMethod",
            "Synthetic",
            "Signature",
            "SourceFile",
            "SourceDebugExtension",
            "LineNumberTable",
            "LocalVariableTable",
            "LocalVariableTypeTable",
            "Deprecated",
            "RuntimeVisibleAnnotations",
            "RuntimeInvisibleAnnotations",
            "RuntimeVisibleParameterAnnotations",
            "RuntimeInvisibleParameterAnnotations",
            /*java8增加*/
            "RuntimeVisibleTypeAnnotations",
            /*java8增加*/
            "RuntimeInvisibleTypeAnnotations",
            "AnnotationDefault",
            "BootstrapMethods",
            /*java8增加*/
            "MethodParameters"
    };

    /**
     * 解析字节码中的属性
     * @param index
     * @param attributes
     */
    public void processAttribute( Integer index, AttributeBase[] attributes){
        U2 attributesNameIndex = IOUtils.readU2();
        Integer tempIndex = TypeUtils.byteArr2Int(attributesNameIndex.u2);
        ConstantBase constantBase = constantPool.cpInfo[tempIndex - 1];
        ConstantUtf8 constantUtf8 = (ConstantUtf8)constantBase;
        if(constantUtf8.tag.u1[0] != 0x1){
            return ;
        }

        String s = TypeUtils.u12String(constantUtf8.bytes);
        if(TypeUtils.compare(s, attributeStrs[0])){
            ConstantValueAttribute constantValue = new ConstantValueAttribute();
            constantValue.attributeNameIndex = attributesNameIndex;
            /*恒等于2*/
            constantValue.attributeLength = IOUtils.readU4();
            constantValue.constantvalueIndex = IOUtils.readU2();
            attributes[index] = constantValue;
        }else if(TypeUtils.compare(s, attributeStrs[1])){
            CodeAttribute codeAttribute = new CodeAttribute();
            codeAttribute.attributeNameIndex = attributesNameIndex;
            codeAttribute.attributeLength = IOUtils.readU4();
            codeAttribute.maxStack = IOUtils.readU2();
            codeAttribute.maxLocals = IOUtils.readU2();
            codeAttribute.codeLength = IOUtils.readU4();
            Integer len = TypeUtils.byteArr2Int(codeAttribute.codeLength.u4);
            codeAttribute.code = new U1[len];
            for(Integer i = 0; i < len; i++){
                codeAttribute.code[i] = IOUtils.readU1();
            }
            codeAttribute.exceptionTableLength = IOUtils.readU2();
            len = TypeUtils.byteArr2Int(codeAttribute.exceptionTableLength.u2);
            codeAttribute.exceptionTables = new ExceptionTable[len];
            for(Integer i = 0; i < len; i ++){
                codeAttribute.exceptionTables[i] = new ExceptionTable();
                codeAttribute.exceptionTables[i].startPc = IOUtils.readU2();
                codeAttribute.exceptionTables[i].endPc = IOUtils.readU2();
                codeAttribute.exceptionTables[i].handlerPc = IOUtils.readU2();
                codeAttribute.exceptionTables[i].catchType = IOUtils.readU2();
            }
            codeAttribute.attributeCount = IOUtils.readU2();
            Integer tempAttributesCount = TypeUtils.byteArr2Int(codeAttribute.attributeCount.u2);
            codeAttribute.attributes =  new AttributeBase[tempAttributesCount];
            for(Integer i = 0; i < tempAttributesCount; i ++){

                processAttribute(i, codeAttribute.attributes);
            }
            attributes[index] = codeAttribute;
        }else if(TypeUtils.compare(s, attributeStrs[2])){
            /* https://hllvm-group.iteye.com/group/topic/26545 */
            StackMapTableAttribute stackMapTableAttribute = new StackMapTableAttribute();
            stackMapTableAttribute.attributeNameIndex = attributesNameIndex;
            processStackMapTable(stackMapTableAttribute);
            attributes[index] = stackMapTableAttribute;
        }else if(TypeUtils.compare(s, attributeStrs[3])){
            ExceptionsAttribute exceptionsAttribute = new ExceptionsAttribute();
            exceptionsAttribute.attributeNameIndex = attributesNameIndex;
            exceptionsAttribute.attributeLength = IOUtils.readU4();
            exceptionsAttribute.numberOfExceptions = IOUtils.readU2();
            Integer exceptionSize = TypeUtils.byteArr2Int(exceptionsAttribute.numberOfExceptions.u2);
            exceptionsAttribute.exceptionIndexTable = new U2[exceptionSize];
            for(Integer j = 0; j < exceptionSize; j ++){
                exceptionsAttribute.exceptionIndexTable[j] = IOUtils.readU2();
            }
            attributes[index] = exceptionsAttribute;
        }else if(TypeUtils.compare(s, attributeStrs[4])){
            InnerClassesAttribute innerClassesAttribute = new InnerClassesAttribute();
            innerClassesAttribute.attributeNameIndex = attributesNameIndex;
            innerClassesAttribute.attributeLength = IOUtils.readU4();
            innerClassesAttribute.numberOfClasses = IOUtils.readU2();
            Integer classesSize = TypeUtils.byteArr2Int(innerClassesAttribute.numberOfClasses.u2);
            innerClassesAttribute.classes = new Classes[classesSize];
            for(Integer j = 0; j < classesSize; j ++){
                innerClassesAttribute.classes[j] = new Classes();
                innerClassesAttribute.classes[j].innerClassInfoIndex = IOUtils.readU2();
                innerClassesAttribute.classes[j].outerClassInfoIndex = IOUtils.readU2();
                innerClassesAttribute.classes[j].innerNameIndex = IOUtils.readU2();
                innerClassesAttribute.classes[j].innerClassAccessFlags = IOUtils.readU2();
            }
            attributes[index] = innerClassesAttribute;
        }else if(TypeUtils.compare(s, attributeStrs[5])){
            EnclosingMethodAttribute enclosingMethodAttribute = new EnclosingMethodAttribute();
            enclosingMethodAttribute.attributeNameIndex = attributesNameIndex;
            enclosingMethodAttribute.attributeLength = IOUtils.readU4();
            enclosingMethodAttribute.classIndex = IOUtils.readU2();
            enclosingMethodAttribute.classIndex = IOUtils.readU2();
            attributes[index] = enclosingMethodAttribute;
        }else if(TypeUtils.compare(s, attributeStrs[6])){
            SyntheticAttribute syntheticAttribute = new SyntheticAttribute();
            syntheticAttribute.attributeNameIndex = attributesNameIndex;
            syntheticAttribute.attributeLength = IOUtils.readU4();
            attributes[index] = syntheticAttribute;
        }else if(TypeUtils.compare(s, attributeStrs[7])){
            SignatureAttribute signatureAttribute = new SignatureAttribute();
            signatureAttribute.attributeNameIndex = attributesNameIndex;
            signatureAttribute.attributeLength = IOUtils.readU4();
            signatureAttribute.signatureIndex = IOUtils.readU2();
            attributes[index] = signatureAttribute;
        }else if(TypeUtils.compare(s, attributeStrs[8])){
            SourceFileAttribute sourceFileAttribute = new SourceFileAttribute();
            sourceFileAttribute.attributeNameIndex = attributesNameIndex;
            sourceFileAttribute.attributeLength = IOUtils.readU4();
            sourceFileAttribute.sourcefileIndex = IOUtils.readU2();
            attributes[index] = sourceFileAttribute;
        }else if(TypeUtils.compare(s, attributeStrs[9])){
            SourceDebugExtensionAttribute sourceDebugExtensionAttribute = new SourceDebugExtensionAttribute();
            sourceDebugExtensionAttribute.attributeNameIndex = attributesNameIndex;
            sourceDebugExtensionAttribute.attributeLength = IOUtils.readU4();
            Integer debugSize = TypeUtils.byteArr2Int(sourceDebugExtensionAttribute.attributeLength.u4);
            sourceDebugExtensionAttribute.debugExtension = new U1[debugSize];
            for(Integer j = 0; j < debugSize; j ++){
                sourceDebugExtensionAttribute.debugExtension[j] = IOUtils.readU1();
            }
            attributes[index] = sourceDebugExtensionAttribute;
        }else if(TypeUtils.compare(s, attributeStrs[10])){
            LineNumberTableAttribute lineNumberTableAttribute = new LineNumberTableAttribute();
            lineNumberTableAttribute.attributeNameIndex = attributesNameIndex;
            lineNumberTableAttribute.attributeLength = IOUtils.readU4();
            lineNumberTableAttribute.lineNumberTableLength = IOUtils.readU2();
            Integer lineNumberSize = TypeUtils.byteArr2Int(lineNumberTableAttribute.lineNumberTableLength.u2);
            lineNumberTableAttribute.lineNumbers = new LineNumber[lineNumberSize];
            for(Integer j = 0; j < lineNumberSize; j ++){
                lineNumberTableAttribute.lineNumbers[j] = new LineNumber();
                lineNumberTableAttribute.lineNumbers[j].startPc = IOUtils.readU2();
                lineNumberTableAttribute.lineNumbers[j].lineNumber = IOUtils.readU2();
            }
            attributes[index] = lineNumberTableAttribute;
        }else if(TypeUtils.compare(s, attributeStrs[11])){
            LocalVariableTableAttribute localVariableTableAttribute = new LocalVariableTableAttribute();
            localVariableTableAttribute.attributeNameIndex = attributesNameIndex;
            localVariableTableAttribute.attributeLength = IOUtils.readU4();
            localVariableTableAttribute.localVariableTableLength = IOUtils.readU2();
            Integer localVariableSize = TypeUtils.byteArr2Int(localVariableTableAttribute.localVariableTableLength.u2);
            localVariableTableAttribute.localVariables = new LocalVariable[localVariableSize];
            for(Integer j = 0; j < localVariableSize; j ++){
                LocalVariable localVariable = new LocalVariable();
                localVariable.startPc = IOUtils.readU2();
                localVariable.length = IOUtils.readU2();
                localVariable.nameIndex = IOUtils.readU2();
                localVariable.descriptorIndex = IOUtils.readU2();
                localVariable.index = IOUtils.readU2();
                localVariableTableAttribute.localVariables[j] = localVariable;
            }
            attributes[index] = localVariableTableAttribute;
        }else if(TypeUtils.compare(s, attributeStrs[12])){
            LocalVariableTypeTableAttribute localVariableTypeTable = new LocalVariableTypeTableAttribute();
            localVariableTypeTable.attributeNameIndex = attributesNameIndex;
            localVariableTypeTable.attributeLength = IOUtils.readU4();
            localVariableTypeTable.localVariableTypeTableLength = IOUtils.readU2();
            Integer localVariableTypeSize = TypeUtils.byteArr2Int(localVariableTypeTable.localVariableTypeTableLength.u2);
            localVariableTypeTable.localVariableTypes = new LocalVariableType[localVariableTypeSize];
            for(Integer j = 0; j < localVariableTypeSize; j ++){
                LocalVariableType localVariableType = new LocalVariableType();
                localVariableType.startPc = IOUtils.readU2();
                localVariableType.length = IOUtils.readU2();
                localVariableType.nameIndex = IOUtils.readU2();
                localVariableType.signatureIndex = IOUtils.readU2();
                localVariableType.index = IOUtils.readU2();
                localVariableTypeTable.localVariableTypes[j] = localVariableType;
            }
            attributes[index] = localVariableTypeTable;
        }else if(TypeUtils.compare(s, attributeStrs[13])){
            DeprecatedAttribute deprecatedAttribute = new DeprecatedAttribute();
            deprecatedAttribute.attributeNameIndex = attributesNameIndex;
            deprecatedAttribute.attributeLength = IOUtils.readU4();
            attributes[index] = deprecatedAttribute;
        }else if(TypeUtils.compare(s, attributeStrs[14])){
            RuntimeVisibleAnnotationsAttribute runtimeVisibleAnnotations = new RuntimeVisibleAnnotationsAttribute();
            runtimeVisibleAnnotations.attributeNameIndex = attributesNameIndex;
            runtimeVisibleAnnotations.attributeLength = IOUtils.readU4();
            runtimeVisibleAnnotations.numAnnotations = IOUtils.readU2();
            Integer annotationsSize = TypeUtils.byteArr2Int(runtimeVisibleAnnotations.numAnnotations.u2);
            runtimeVisibleAnnotations.annotations = new Annotation[annotationsSize];
            for(Integer j = 0; j < annotationsSize; j ++){
                Annotation annotation = runtimeVisibleAnnotations.annotations[j] = new Annotation();
                processAnnotation(annotation);
            }
            attributes[index] = runtimeVisibleAnnotations;
        }else if(TypeUtils.compare(s, attributeStrs[15])){
            RuntimeInvisibleAnnotationsAttribute runtimeInvisibleAnnotations = new RuntimeInvisibleAnnotationsAttribute();
            runtimeInvisibleAnnotations.attributeNameIndex = attributesNameIndex;
            runtimeInvisibleAnnotations.attributeLength = IOUtils.readU4();
            runtimeInvisibleAnnotations.numAnnotations = IOUtils.readU2();
            Integer annotationsSize = TypeUtils.byteArr2Int(runtimeInvisibleAnnotations.numAnnotations.u2);
            for(Integer j = 0; j < annotationsSize; j ++){
                Annotation annotation = runtimeInvisibleAnnotations.annotations[j];
                processAnnotation(annotation);
            }
            attributes[index] = runtimeInvisibleAnnotations;
        }else if(TypeUtils.compare(s, attributeStrs[16])){
            RuntimeVisibleParameterAnnotationsAttribute runtimeVisibleParameterAnnotations = new RuntimeVisibleParameterAnnotationsAttribute();
            runtimeVisibleParameterAnnotations.attributeNameIndex = attributesNameIndex;
            runtimeVisibleParameterAnnotations.attributeLength = IOUtils.readU4();
            runtimeVisibleParameterAnnotations.numParameters = IOUtils.readU1();
            Integer parametersSize = TypeUtils.byteArr2Int(runtimeVisibleParameterAnnotations.numParameters.u1);
            for(Integer j = 0; j < parametersSize; j ++){
                ParameterAnnotation parameterAnnotation = runtimeVisibleParameterAnnotations.parameterAnnotations[j];
                processParameterAnnotation(parameterAnnotation);
            }
            attributes[index] = runtimeVisibleParameterAnnotations;
        }else if(TypeUtils.compare(s, attributeStrs[17])){
            RuntimeInvisibleParameterAnnotationsAttribute runtimeInvisibleParameterAnnotations = new RuntimeInvisibleParameterAnnotationsAttribute();
            runtimeInvisibleParameterAnnotations.attributeNameIndex = attributesNameIndex;
            runtimeInvisibleParameterAnnotations.attributeLength = IOUtils.readU4();
            runtimeInvisibleParameterAnnotations.numParameters = IOUtils.readU1();
            Integer parametersSize = TypeUtils.byteArr2Int(runtimeInvisibleParameterAnnotations.numParameters.u1);
            for(Integer j = 0; j < parametersSize; j ++){
                ParameterAnnotation parameterAnnotation = runtimeInvisibleParameterAnnotations.parameterAnnotations[j];
                processParameterAnnotation(parameterAnnotation);
            }
            attributes[index] = runtimeInvisibleParameterAnnotations;

            /*java8增加 RuntimeVisibleTypeAnnotationsAttribute*/
        }else if(TypeUtils.compare(s, attributeStrs[18])){

            /*java8增加 RuntimeInvisibleTypeAnnotationsAttribute*/
        }else if(TypeUtils.compare(s, attributeStrs[19])){

        }else if(TypeUtils.compare(s, attributeStrs[20])){
            AnnotationDefaultAttribute annotationDefault = new AnnotationDefaultAttribute();
            annotationDefault.attributeNameIndex = attributesNameIndex;
            annotationDefault.attributeLength = IOUtils.readU4();
            annotationDefault.defaultValue = processElementValue();
            attributes[index] = annotationDefault;
        }else if(TypeUtils.compare(s, attributeStrs[21])){
            BootstrapMethodsAttribute bootstrapMethods = new BootstrapMethodsAttribute();
            bootstrapMethods.attributeNameIndex = attributesNameIndex;
            bootstrapMethods.attributeLength = IOUtils.readU4();
            bootstrapMethods.numBootstrapMethods = IOUtils.readU2();
            Integer bootstrapMethodsSize = TypeUtils.byteArr2Int(bootstrapMethods.numBootstrapMethods.u2);
            bootstrapMethods.bootstrapMethods = new BootstrapMethod[bootstrapMethodsSize];
            for(Integer j = 0; j < bootstrapMethodsSize; j ++){
                BootstrapMethod bootstrapMethod = new BootstrapMethod();
                bootstrapMethod.bootstrapMethodRef = IOUtils.readU2();
                bootstrapMethod.numBootstrapArguments = IOUtils.readU2();
                Integer argumentsSize = TypeUtils.byteArr2Int(bootstrapMethod.numBootstrapArguments.u2);
                bootstrapMethod.bootstrapArguments = new U2[argumentsSize];
                for(Integer k = 0; k < argumentsSize; k ++){
                    bootstrapMethod.bootstrapArguments[k] = IOUtils.readU2();
                }
                bootstrapMethods.bootstrapMethods[j] = bootstrapMethod;
            }
            attributes[index] = bootstrapMethods;

            /*java8增加 MethodParametersAttribute*/
        }else if(TypeUtils.compare(s, attributeStrs[22])){

        }
    }

    void processParameterAnnotation(ParameterAnnotation parameterAnnotation){
        parameterAnnotation.numAnnotations = IOUtils.readU2();
        Integer annotations_size = TypeUtils.byteArr2Int(parameterAnnotation.numAnnotations.u2);
        parameterAnnotation.annotations = new Annotation[annotations_size];
        for(Integer k = 0; k < annotations_size; k ++){
            parameterAnnotation.annotations[k] = new Annotation();
            processAnnotation(parameterAnnotation.annotations[k]);
        }
    }

    void processAnnotation(Annotation annotation){
        annotation.typeIndex = IOUtils.readU2();
        annotation.numElementValuePairs = IOUtils.readU2();
        Integer pairsSize = TypeUtils.byteArr2Int(annotation.numElementValuePairs.u2);
        annotation.elementValuePairs = new ElementValuePair[pairsSize];
        for(Integer k = 0; k < pairsSize; k ++){
            annotation.elementValuePairs[k] = new ElementValuePair();
            processElementValuePair(annotation.elementValuePairs[k]);
        }
    }

    void processElementValuePair(ElementValuePair elementValuePair){
        elementValuePair.elementNameIndex = IOUtils.readU2();
        elementValuePair.value = processElementValue();
    }

    ElementValue processElementValue(){
        ElementValue elementValue = null;
        U1 elementValueTag = IOUtils.readU1();
        Integer tagInteger = TypeUtils.byteArr2Int(elementValueTag.u1);

        if(Integer.valueOf('B').equals(tagInteger)
                || Integer.valueOf('C').equals(tagInteger)
                || Integer.valueOf('D').equals(tagInteger)
                || Integer.valueOf('F').equals(tagInteger)
                || Integer.valueOf('I').equals(tagInteger)
                || Integer.valueOf('J').equals(tagInteger)
                || Integer.valueOf('S').equals(tagInteger)
                || Integer.valueOf('Z').equals(tagInteger)
                || Integer.valueOf('s').equals(tagInteger)){
            ConstValueIndex constValueIndex =  new ConstValueIndex();
            constValueIndex.tag = elementValueTag;
            constValueIndex.constValueIndex = IOUtils.readU2();
            elementValue = constValueIndex;
        }
        else if( Integer.valueOf('e').equals(tagInteger)){
            EnumConstValue enumConstValue =  new EnumConstValue();
            enumConstValue.tag = elementValueTag;
            enumConstValue.typeNameIndex = IOUtils.readU2();
            enumConstValue.constNameIndex = IOUtils.readU2();
            elementValue = enumConstValue;
        }else if(Integer.valueOf('c').equals(tagInteger)){
            ClassInfoIndex classInfoIndex =  new ClassInfoIndex();
            classInfoIndex.tag = elementValueTag;
            classInfoIndex.classInfoIndex = IOUtils.readU2();
            elementValue = classInfoIndex;
        }else if( Integer.valueOf('@').equals(tagInteger)){
            ValueAnnotation valueAnnotation = new ValueAnnotation();
            valueAnnotation.tag = elementValueTag;
            valueAnnotation.annotationValue = new Annotation();
            processAnnotation(valueAnnotation.annotationValue);
            elementValue = valueAnnotation;
        }else if(Integer.valueOf('[').equals(tagInteger)){
            ArrayValue arrayValue =  new ArrayValue();
            arrayValue.tag = elementValueTag;
            arrayValue.numValues = IOUtils.readU2();
            Integer numValueInteger = TypeUtils.byteArr2Int(arrayValue.numValues.u2);
            ElementValue[] elementValues = new ElementValue[numValueInteger];
            for(Integer i = 0; i < numValueInteger; i++){
                elementValues[i] = processElementValue();
            }
            arrayValue.values = elementValues;
            elementValue = arrayValue;
        }
        return elementValue;

    }

    void processStackMapTable(StackMapTableAttribute stackMapTableAttribute){
        stackMapTableAttribute.attributeLength = IOUtils.readU4();
        stackMapTableAttribute.numberOfEntries = IOUtils.readU2();
        Integer len = TypeUtils.byteArr2Int(stackMapTableAttribute.numberOfEntries.u2);
        stackMapTableAttribute.entries = new StackMapFrame[len];
        for(Integer i = 0; i < len; i++){
            // stackMapTable_attribute.entries[i];
            U1 frameTag = IOUtils.readU1();
            Integer frameTagInteger = TypeUtils.byteArr2Int(frameTag.u1);
            /* 128 至 246是预留的*/
            /*SameFrame */
            if(frameTagInteger >= 0 && frameTagInteger <= 63 ){
                SameFrame sameFrame = new SameFrame();
                sameFrame.frameType = frameTag;
                stackMapTableAttribute.entries[i] = sameFrame;

                /*SameLocals1StackItemFrame*/
            }else if(frameTagInteger >= 64 && frameTagInteger <= 127){
                SameLocals1StackItemFrame sameLocals1StackItemFrame = new SameLocals1StackItemFrame();
                sameLocals1StackItemFrame.frameType = frameTag;
                sameLocals1StackItemFrame.stack = new VerificationTypeInfo[1];
                U1 verificationTypeTag = IOUtils.readU1();
                sameLocals1StackItemFrame.stack[0] = getVerificationTypeTag(verificationTypeTag);
                stackMapTableAttribute.entries[i] = sameLocals1StackItemFrame;

                /*SameLocals1StackItemFrameExtended*/
            }else if(frameTagInteger == 247){
                SameLocals1StackItemFrameExtended sameLocals1StackItemFrameExtended = new SameLocals1StackItemFrameExtended();
                sameLocals1StackItemFrameExtended.frameType = frameTag;
                sameLocals1StackItemFrameExtended.offsetDelta = IOUtils.readU2();
                sameLocals1StackItemFrameExtended.stack = new VerificationTypeInfo[1];
                U1 verificationTypeTag = IOUtils.readU1();
                sameLocals1StackItemFrameExtended.stack[0] = getVerificationTypeTag(verificationTypeTag);
                stackMapTableAttribute.entries[i] = sameLocals1StackItemFrameExtended;

                /*ChopFrame*/
            }else if(frameTagInteger >= 248 && frameTagInteger <= 250){
                ChopFrame chopFrame = new ChopFrame();
                chopFrame.frameType = frameTag;
                chopFrame.offsetDelta = IOUtils.readU2();
                stackMapTableAttribute.entries[i] = chopFrame;

                /*SameFrameExtended*/
            }else if(frameTagInteger == 251){
                SameFrameExtended sameFrameExtended = new SameFrameExtended();
                sameFrameExtended.frameType = frameTag;
                sameFrameExtended.offsetDelta = IOUtils.readU2();
                stackMapTableAttribute.entries[i] = sameFrameExtended;

                /*AppendFrame*/
            }else if(frameTagInteger >= 252 && frameTagInteger <= 254){
                AppendFrame appendFrame = new AppendFrame();
                appendFrame.frameType = frameTag;
                appendFrame.offsetDelta = IOUtils.readU2();
                int localsSize = frameTagInteger - 251;
                appendFrame.locals = new VerificationTypeInfo[localsSize];
                for(Integer j = 0; j < localsSize; j ++){
                    U1 verificationTypeTag = IOUtils.readU1();
                    appendFrame.locals[j] = getVerificationTypeTag(verificationTypeTag);
                }
                stackMapTableAttribute.entries[i] = appendFrame;

                /*FullFrame*/
            }else if(frameTagInteger >= 255 ){
                FullFrame fullFrame = new FullFrame();
                fullFrame.frameType = frameTag;
                fullFrame.offsetDelta = IOUtils.readU2();
                fullFrame.numberOfLocals = IOUtils.readU2();
                int localsSize = TypeUtils.byteArr2Int(fullFrame.numberOfLocals.u2);
                fullFrame.locals = new VerificationTypeInfo[localsSize];
                for(Integer j = 0; j < localsSize; j ++){
                    U1 verificationTypeTag = IOUtils.readU1();
                    fullFrame.locals[j] = getVerificationTypeTag(verificationTypeTag);
                }

                fullFrame.numberOfStackItems = IOUtils.readU2();
                int stackItemsSize = TypeUtils.byteArr2Int(fullFrame.numberOfStackItems.u2);
                fullFrame.stack = new VerificationTypeInfo[stackItemsSize];
                for(Integer j = 0; j < stackItemsSize; j ++){
                    U1 verificationTypeTag = IOUtils.readU1();
                    fullFrame.stack[j] = getVerificationTypeTag(verificationTypeTag);
                }

                stackMapTableAttribute.entries[i] = fullFrame;
            }
        }
    }

    VerificationTypeInfo getVerificationTypeTag(U1 tag){
        Integer tagInteger = TypeUtils.byteArr2Int(tag.u1);
        if(tagInteger == 0){
            TopVariableInfo topVariableInfo = new TopVariableInfo();
            topVariableInfo.tag = tag;
            return topVariableInfo;
        }else if(tagInteger == 1){
            IntegerVariableInfo integerVariableInfo = new IntegerVariableInfo();
            integerVariableInfo.tag = tag;
            return integerVariableInfo;
        }else if(tagInteger == 2){
            FloatVariableInfo floatVariableInfo = new FloatVariableInfo();
            floatVariableInfo.tag = tag;
            return floatVariableInfo;
        }else if(tagInteger == 4){
            LongVariableInfo longVariableInfo = new LongVariableInfo();
            longVariableInfo.tag = tag;
            return longVariableInfo;
        }else if(tagInteger == 3){
            DoubleVariableInfo doubleVariableInfo = new DoubleVariableInfo();
            doubleVariableInfo.tag = tag;
            return doubleVariableInfo;
        }else if(tagInteger == 5){
            NullVariableInfo nullVariableInfo = new NullVariableInfo();
            nullVariableInfo.tag = tag;
            return nullVariableInfo;
        }else if(tagInteger == 6){
            UninitializedThisVariableInfo uninitializedThisVariableInfo = new UninitializedThisVariableInfo();
            uninitializedThisVariableInfo.tag = tag;
            return uninitializedThisVariableInfo;
        }else if(tagInteger == 7){
            ObjectVariableInfo objectVariableInfo = new ObjectVariableInfo();
            objectVariableInfo.tag = tag;
            objectVariableInfo.cpoolIndex = IOUtils.readU2();
            return objectVariableInfo;
        }else if (tagInteger == 8){
            UninitializedVariableInfo uninitializedVariableInfo = new UninitializedVariableInfo();
            uninitializedVariableInfo.tag = tag;
            uninitializedVariableInfo.offset = IOUtils.readU2();
            return uninitializedVariableInfo;
        }
        return null;
    }

    enum TAG{
        CONSTANT_Utf8("ConstantUtf8", (byte)0x1),
        CONSTANT_Integer("ConstantInteger", (byte)0x3),
        CONSTANT_Float("ConstantFloat", (byte)0x4),
        CONSTANT_Long("ConstantLong", (byte)0x5),
        CONSTANT_Double("ConstantDouble", (byte)0x6),
        CONSTANT_Class("ConstantClass", (byte)0x7),
        CONSTANT_String("ConstantString", (byte)0x8),
        CONSTANT_Fieldref("ConstantFieldref", (byte)0x9),
        CONSTANT_Methodref("ConstantMethodref", (byte)0x10),
        CONSTANT_InterfaceMethodref("ConstantInterfacemethodref", (byte)0x11),
        CONSTANT_NameAndType("ConstantNameandtype", (byte)0x12),
        CONSTANT_MethodHandle("ConstantMethodhandle", (byte)0x15),
        CONSTANT_MethodType("ConstantMethodtype", (byte)0x16),
        CONSTANT_InvokeDynamic("ConstantInvokedynamic", (byte)0x17);

        public String name;
        public byte index;
        private TAG(String name, byte index){
            this.name = name;
            this.index = index;
        }
    }

}


//class VerificationTypeInfo  {
//    TopVariableInfo top_variable_info;
//    IntegerVariableInfo integer_variable_info;
//    FloatVariableInfo float_variable_info;
//    LongVariableInfo long_variable_info;
//    DoubleVariableInfo double_variable_info;
//    NullVariableInfo null_variable_info;
//    UninitializedThisVariableInfo uninitializedThis_variable_info;
//}

