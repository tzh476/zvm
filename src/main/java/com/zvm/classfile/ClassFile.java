package com.zvm.classfile;

import com.zvm.utils.TypeUtils;
import com.zvm.basestruct.u1;
import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;
import com.zvm.classfile.attribute.*;
import com.zvm.classfile.attribute.LocalVariableTable.local_variable;
import com.zvm.classfile.attribute.LocalVariableTypeTable.local_variable_type;
import com.zvm.classfile.attribute.RuntimeVisibleParameterAnnotations.parameter_annotation;
import com.zvm.classfile.attribute.code.exception_table;
import com.zvm.classfile.attribute.innerClasses.classes;
import com.zvm.classfile.attribute.lineNumberTable.line_number;
import com.zvm.classfile.attribute.runtimeVisibleAnnotations.*;
import com.zvm.classfile.attribute.stackmaptable.*;
import com.zvm.classfile.attribute.stackmaptable.verificationtypeinfo.*;
import com.zvm.classfile.constantpool.*;

public class ClassFile {
    public u4 magic;
    public u2 minor_version;
    public u2 major_version;
    public u2 constant_pool_count;
    public cp_info constant_pool = new cp_info();
    public u2 access_flags;
    public u2 this_class;
    public u2 super_class;
    public u2 interface_count;
    public u2[] interfaces;
    public u2 field_count;
    public field_info[] fields ;
    public u2 methods_count;
    public method_info[] methods ;
    public u2 attributes_count;
    public Attribute_Base[] attributes ;



    public void processByteCode(byte[] bytecode){
        IOUtils.bytecode = bytecode;
        IOUtils.index = 0;
        magic = IOUtils.read_u4();
        minor_version = IOUtils.read_u2();
        major_version = IOUtils.read_u2();
        constant_pool_count = IOUtils.read_u2();

        Integer pool_size = TypeUtils.byteArr2Int(constant_pool_count.u2);

        constant_pool.cp_info = new CONSTANT_Base[pool_size];

        /*读取常量池*/
        for(Integer i = 0; i < pool_size - 1; i++){
        u1 tag = IOUtils.read_u1();

            Integer integer_tag = TypeUtils.byteArr2Int(tag.u1);
            if(integer_tag == 1){
                CONSTANT_Utf8 constant_utf8 = new CONSTANT_Utf8();
                constant_utf8.tag = tag;
                constant_utf8.length = IOUtils.read_u2();
                Integer utf8_len = TypeUtils.byteArr2Int( constant_utf8.length.u2);
                constant_utf8.bytes = new u1[utf8_len];
                for(Integer j = 0; j < utf8_len; j ++){
                    constant_utf8.bytes[j] = IOUtils.read_u1();
                }
                constant_pool.cp_info[i] = constant_utf8;
            } else if(integer_tag == 3){
                CONSTANT_Integer constant_integer = new CONSTANT_Integer();
                constant_integer.tag = tag;
                constant_integer.bytes = IOUtils.read_u4();
                constant_pool.cp_info[i] = constant_integer;
            } else if(integer_tag == 4){
                CONSTANT_Float constant_float = new CONSTANT_Float();
                constant_float.tag = tag;
                constant_float.bytes = IOUtils.read_u4();
                constant_pool.cp_info[i] = constant_float;
            }else if (integer_tag == 5){
                CONSTANT_Long constant_long = new CONSTANT_Long();
                constant_long.tag = tag;
                constant_long.high_bytes = IOUtils.read_u4();
                constant_long.low_bytes = IOUtils.read_u4();
                /*double和long类型会跳过一个常量标识*/
                constant_pool.cp_info[i ++] = constant_long;
            }else if(integer_tag == 6){
                CONSTANT_Double constant_double = new CONSTANT_Double();
                constant_double.tag = tag;
                constant_double.high_bytes = IOUtils.read_u4();
                constant_double.low_bytes = IOUtils.read_u4();
                /*double和long类型会跳过一个常量标识*/
                constant_pool.cp_info[i ++] = constant_double;
            }else if(integer_tag == 7){
                CONSTANT_Class constant_class = new CONSTANT_Class();
                constant_class.tag = tag;
                constant_class.name_index = IOUtils.read_u2();
                constant_pool.cp_info[i] = constant_class;
            }else if(integer_tag == 8){
                CONSTANT_String constant_string = new CONSTANT_String();
                constant_string.tag = tag;
                constant_string.string_index = IOUtils.read_u2();
                constant_pool.cp_info[i] = constant_string;
            }else if(integer_tag == 9){
                CONSTANT_Fieldref constant_fieldref = new CONSTANT_Fieldref();
                constant_fieldref.tag = tag;
                constant_fieldref.class_index = IOUtils.read_u2();
                constant_fieldref.name_and_type_index = IOUtils.read_u2();
                constant_pool.cp_info[i] = constant_fieldref;
            }else if(integer_tag == 10){
                CONSTANT_Methodref constant_methodref = new CONSTANT_Methodref();
                constant_methodref.tag = tag;
                constant_methodref.class_index = IOUtils.read_u2();
                constant_methodref.name_and_type_index = IOUtils.read_u2();
                constant_pool.cp_info[i] = constant_methodref;
            }else if(integer_tag == 11){
                CONSTANT_InterfaceMethodref constant_interface_methodref = new CONSTANT_InterfaceMethodref();
                constant_interface_methodref.tag = tag;
                constant_interface_methodref.class_index = IOUtils.read_u2();
                constant_interface_methodref.name_and_type_index = IOUtils.read_u2();
                constant_pool.cp_info[i] = constant_interface_methodref;
            }else if(integer_tag == 12){
                CONSTANT_NameAndType constant_nameAndType = new CONSTANT_NameAndType();
                constant_nameAndType.tag = tag;
                constant_nameAndType.name_index = IOUtils.read_u2();
                constant_nameAndType.descriptor_index = IOUtils.read_u2();
                constant_pool.cp_info[i] = constant_nameAndType;
            }else if(integer_tag == 15){
                CONSTANT_MethodHandle constant_methodHandle = new CONSTANT_MethodHandle();
                constant_methodHandle.tag = tag;
                constant_methodHandle.reference_kind = IOUtils.read_u1();
                constant_methodHandle.reference_index = IOUtils.read_u2();
                constant_pool.cp_info[i] = constant_methodHandle;
            }else if(integer_tag == 16){
                CONSTANT_MethodType constant_methodType = new CONSTANT_MethodType();
                constant_methodType.tag = tag;
                constant_methodType.descriptor_index = IOUtils.read_u2();
                constant_pool.cp_info[i] = constant_methodType;
            //}else if(integer_tag == 17){
            }else if(integer_tag == 18){
                CONSTANT_InvokeDynamic constant_invokeDynamic = new CONSTANT_InvokeDynamic();
                constant_invokeDynamic.tag = tag;
                constant_invokeDynamic.bootstrap_method_attr_index = IOUtils.read_u2();
                constant_invokeDynamic.name_and_type_index = IOUtils.read_u2();
                constant_pool.cp_info[i] = constant_invokeDynamic;
            }else {
                System.out.println("constant_pool integer_tag " + integer_tag);
                return;
            }
        }

        access_flags = IOUtils.read_u2();
        this_class = IOUtils.read_u2();
        super_class = IOUtils.read_u2();
        interface_count = IOUtils.read_u2();
        Integer interface_count_integer = TypeUtils.byteArr2Int(interface_count.u2);
        interfaces = new u2[interface_count_integer];
        for(Integer i = 0; i < interface_count_integer; i ++){
            interfaces[i] = IOUtils.read_u2();
        }

        field_count  = IOUtils.read_u2();
        Integer field_count_integer = TypeUtils.byteArr2Int(field_count.u2);
        fields = new field_info[field_count_integer];
        for(Integer i = 0; i < field_count_integer; i ++){
            fields[i] = new field_info();
            fields[i].access_flags = IOUtils.read_u2();
            fields[i].name_index = IOUtils.read_u2();
            fields[i].descriptor_index = IOUtils.read_u2();
            fields[i].attribute_count = IOUtils.read_u2();
            Integer temp_attributes_count = TypeUtils.byteArr2Int(fields[i].attribute_count.u2);
            fields[i].attributes =  new Attribute_Base[temp_attributes_count];
            for(Integer j = 0; j < temp_attributes_count; j ++){
                processAttribute( j, fields[i].attributes);
            }
        }

        methods_count  = IOUtils.read_u2();
        Integer methods_count_integer = TypeUtils.byteArr2Int(methods_count.u2);
        methods = new method_info[methods_count_integer];
        for(Integer i = 0; i < methods_count_integer; i ++){
            methods[i] = new method_info();
            methods[i].access_flags = IOUtils.read_u2();
            methods[i].name_index = IOUtils.read_u2();
            methods[i].descriptor_index = IOUtils.read_u2();
            methods[i].attribute_count = IOUtils.read_u2();
            Integer temp_attributes_count = TypeUtils.byteArr2Int(methods[i].attribute_count.u2);
            methods[i].attributes =  new Attribute_Base[temp_attributes_count];
            for(Integer j = 0; j < temp_attributes_count; j ++){
                processAttribute( j, methods[i].attributes);
            }
        }

        attributes_count = IOUtils.read_u2();
        Integer temp_attributes_count = TypeUtils.byteArr2Int(attributes_count.u2);
        attributes =  new Attribute_Base[temp_attributes_count];
        for(Integer i = 0; i < temp_attributes_count; i ++){
            processAttribute( i, attributes);
        }
    }
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
    public void processAttribute( Integer index, Attribute_Base[] attributes){
        u2 attributes_name_index = IOUtils.read_u2();
        Integer tempIndex = TypeUtils.byteArr2Int(attributes_name_index.u2);
        CONSTANT_Base constant_base = constant_pool.cp_info[tempIndex - 1];
        CONSTANT_Utf8 constant_utf8 = (CONSTANT_Utf8)constant_base;
        if(constant_utf8.tag.u1[0] != 0x1){
            return ;
        }

        String s = TypeUtils.u12String(constant_utf8.bytes);
        if(TypeUtils.compare(s, attributeStrs[0])){
            ConstantValue_attribute constantValue = new ConstantValue_attribute();
            constantValue.attribute_name_index = attributes_name_index;
            /*恒等于2*/
            constantValue.attribute_length = IOUtils.read_u4();
            constantValue.constantvalue_index = IOUtils.read_u2();
            attributes[index] = constantValue;
        }else if(TypeUtils.compare(s, attributeStrs[1])){
            Code_attribute code_attribute = new Code_attribute();
            code_attribute.attribute_name_index = attributes_name_index;
            code_attribute.attribute_length = IOUtils.read_u4();
            code_attribute.max_stack = IOUtils.read_u2();
            code_attribute.max_locals = IOUtils.read_u2();
            code_attribute.code_length = IOUtils.read_u4();
            Integer len = TypeUtils.byteArr2Int(code_attribute.code_length.u4);
            code_attribute.code = new u1[len];
            for(Integer i = 0; i < len; i++){
                code_attribute.code[i] = IOUtils.read_u1();
            }
            code_attribute.exception_table_length = IOUtils.read_u2();
            len = TypeUtils.byteArr2Int(code_attribute.exception_table_length.u2);
            code_attribute.exception_table = new exception_table[len];
            for(Integer i = 0; i < len; i ++){
                code_attribute.exception_table[i] = new exception_table();
                code_attribute.exception_table[i].start_pc = IOUtils.read_u2();
                code_attribute.exception_table[i].end_pc = IOUtils.read_u2();
                code_attribute.exception_table[i].handler_pc = IOUtils.read_u2();
                code_attribute.exception_table[i].catch_type = IOUtils.read_u2();
            }
            code_attribute.attribute_count = IOUtils.read_u2();
            Integer temp_attributes_count = TypeUtils.byteArr2Int(code_attribute.attribute_count.u2);
            code_attribute.attributes =  new Attribute_Base[temp_attributes_count];
            for(Integer i = 0; i < temp_attributes_count; i ++){

                processAttribute(i, code_attribute.attributes);
            }
            attributes[index] = code_attribute;
        }else if(TypeUtils.compare(s, attributeStrs[2])){
            /* https://hllvm-group.iteye.com/group/topic/26545 */
            StackMapTable_attribute stackMapTable_attribute = new StackMapTable_attribute();
            stackMapTable_attribute.attribute_name_index = attributes_name_index;
            processStackMapTable(stackMapTable_attribute);
            attributes[index] = stackMapTable_attribute;
        }else if(TypeUtils.compare(s, attributeStrs[3])){
            Exceptions_attribute exceptions_attribute = new Exceptions_attribute();
            exceptions_attribute.attribute_name_index = attributes_name_index;
            exceptions_attribute.attribute_length = IOUtils.read_u4();
            exceptions_attribute.number_of_exceptions = IOUtils.read_u2();
            Integer exception_size = TypeUtils.byteArr2Int(exceptions_attribute.number_of_exceptions.u2);
            exceptions_attribute.exception_index_table = new u2[exception_size];
            for(Integer j = 0; j < exception_size; j ++){
                exceptions_attribute.exception_index_table[j] = IOUtils.read_u2();
            }
            attributes[index] = exceptions_attribute;
        }else if(TypeUtils.compare(s, attributeStrs[4])){
            InnerClasses_attribute innerClasses_attribute = new InnerClasses_attribute();
            innerClasses_attribute.attribute_name_index = attributes_name_index;
            innerClasses_attribute.attribute_length = IOUtils.read_u4();
            innerClasses_attribute.number_of_classes = IOUtils.read_u2();
            Integer classes_size = TypeUtils.byteArr2Int(innerClasses_attribute.number_of_classes.u2);
            innerClasses_attribute.classes = new classes[classes_size];
            for(Integer j = 0; j < classes_size; j ++){
                innerClasses_attribute.classes[j] = new classes();
                innerClasses_attribute.classes[j].inner_class_info_index = IOUtils.read_u2();
                innerClasses_attribute.classes[j].outer_class_info_index = IOUtils.read_u2();
                innerClasses_attribute.classes[j].inner_name_index = IOUtils.read_u2();
                innerClasses_attribute.classes[j].inner_class_access_flags = IOUtils.read_u2();
            }
            attributes[index] = innerClasses_attribute;
        }else if(TypeUtils.compare(s, attributeStrs[5])){
            EnclosingMethod_attribute enclosingMethod_attribute = new EnclosingMethod_attribute();
            enclosingMethod_attribute.attribute_name_index = attributes_name_index;
            enclosingMethod_attribute.attribute_length = IOUtils.read_u4();
            enclosingMethod_attribute.class_index = IOUtils.read_u2();
            enclosingMethod_attribute.class_index = IOUtils.read_u2();
            attributes[index] = enclosingMethod_attribute;
        }else if(TypeUtils.compare(s, attributeStrs[6])){
            Synthetic_attribute synthetic_attribute = new Synthetic_attribute();
            synthetic_attribute.attribute_name_index = attributes_name_index;
            synthetic_attribute.attribute_length = IOUtils.read_u4();
            attributes[index] = synthetic_attribute;
        }else if(TypeUtils.compare(s, attributeStrs[7])){
            Signature_attribute signature_attribute = new Signature_attribute();
            signature_attribute.attribute_name_index = attributes_name_index;
            signature_attribute.attribute_length = IOUtils.read_u4();
            signature_attribute.signature_index = IOUtils.read_u2();
            attributes[index] = signature_attribute;
        }else if(TypeUtils.compare(s, attributeStrs[8])){
            SourceFile_attribute sourceFile_attribute = new SourceFile_attribute();
            sourceFile_attribute.attribute_name_index = attributes_name_index;
            sourceFile_attribute.attribute_length = IOUtils.read_u4();
            sourceFile_attribute.sourcefile_index = IOUtils.read_u2();
            attributes[index] = sourceFile_attribute;
        }else if(TypeUtils.compare(s, attributeStrs[9])){
            SourceDebugExtension_attribute sourceDebugExtension_attribute = new SourceDebugExtension_attribute();
            sourceDebugExtension_attribute.attribute_name_index = attributes_name_index;
            sourceDebugExtension_attribute.attribute_length = IOUtils.read_u4();
            Integer debug_size = TypeUtils.byteArr2Int(sourceDebugExtension_attribute.attribute_length.u4);
            sourceDebugExtension_attribute.debug_extension = new u1[debug_size];
            for(Integer j = 0; j < debug_size; j ++){
                sourceDebugExtension_attribute.debug_extension[j] = IOUtils.read_u1();
            }
            attributes[index] = sourceDebugExtension_attribute;
        }else if(TypeUtils.compare(s, attributeStrs[10])){
            LineNumberTable_attribute lineNumberTable_attribute = new LineNumberTable_attribute();
            lineNumberTable_attribute.attribute_name_index = attributes_name_index;
            lineNumberTable_attribute.attribute_length = IOUtils.read_u4();
            lineNumberTable_attribute.line_number_table_length = IOUtils.read_u2();
            Integer line_number_size = TypeUtils.byteArr2Int(lineNumberTable_attribute.line_number_table_length.u2);
            lineNumberTable_attribute.line_number_table = new line_number[line_number_size];
            for(Integer j = 0; j < line_number_size; j ++){
                lineNumberTable_attribute.line_number_table[j] = new line_number();
                lineNumberTable_attribute.line_number_table[j].start_pc = IOUtils.read_u2();
                lineNumberTable_attribute.line_number_table[j].line_number = IOUtils.read_u2();
            }
            attributes[index] = lineNumberTable_attribute;
        }else if(TypeUtils.compare(s, attributeStrs[11])){
            LocalVariableTable_attribute localVariableTable_attribute = new LocalVariableTable_attribute();
            localVariableTable_attribute.attribute_name_index = attributes_name_index;
            localVariableTable_attribute.attribute_length = IOUtils.read_u4();
            localVariableTable_attribute.local_variable_table_length = IOUtils.read_u2();
            Integer local_variable_size = TypeUtils.byteArr2Int(localVariableTable_attribute.local_variable_table_length.u2);
            localVariableTable_attribute.local_variable_table = new local_variable[local_variable_size];
            for(Integer j = 0; j < local_variable_size; j ++){
                local_variable local_variable = new local_variable();
                local_variable.start_pc = IOUtils.read_u2();
                local_variable.length = IOUtils.read_u2();
                local_variable.name_index = IOUtils.read_u2();
                local_variable.descriptor_index = IOUtils.read_u2();
                local_variable.index = IOUtils.read_u2();
                localVariableTable_attribute.local_variable_table[j] = local_variable;
            }
            attributes[index] = localVariableTable_attribute;
        }else if(TypeUtils.compare(s, attributeStrs[12])){
            LocalVariableTypeTable_attribute localVariableTypeTable = new LocalVariableTypeTable_attribute();
            localVariableTypeTable.attribute_name_index = attributes_name_index;
            localVariableTypeTable.attribute_length = IOUtils.read_u4();
            localVariableTypeTable.local_variable_type_table_length = IOUtils.read_u2();
            Integer local_variable_type_size = TypeUtils.byteArr2Int(localVariableTypeTable.local_variable_type_table_length.u2);
            localVariableTypeTable.local_variable_type_table = new local_variable_type[local_variable_type_size];
            for(Integer j = 0; j < local_variable_type_size; j ++){
                local_variable_type local_variable_type = new local_variable_type();
                local_variable_type.start_pc = IOUtils.read_u2();
                local_variable_type.length = IOUtils.read_u2();
                local_variable_type.name_index = IOUtils.read_u2();
                local_variable_type.signature_index = IOUtils.read_u2();
                local_variable_type.index = IOUtils.read_u2();
                localVariableTypeTable.local_variable_type_table[j] = local_variable_type;
            }
            attributes[index] = localVariableTypeTable;
        }else if(TypeUtils.compare(s, attributeStrs[13])){
            Deprecated_attribute deprecated_attribute = new Deprecated_attribute();
            deprecated_attribute.attribute_name_index = attributes_name_index;
            deprecated_attribute.attribute_length = IOUtils.read_u4();
            attributes[index] = deprecated_attribute;
        }else if(TypeUtils.compare(s, attributeStrs[14])){
            RuntimeVisibleAnnotations_attribute runtimeVisibleAnnotations = new RuntimeVisibleAnnotations_attribute();
            runtimeVisibleAnnotations.attribute_name_index = attributes_name_index;
            runtimeVisibleAnnotations.attribute_length = IOUtils.read_u4();
            runtimeVisibleAnnotations.num_annotations = IOUtils.read_u2();
            Integer annotations_size = TypeUtils.byteArr2Int(runtimeVisibleAnnotations.num_annotations.u2);
            runtimeVisibleAnnotations.annotations = new annotation[annotations_size];
            for(Integer j = 0; j < annotations_size; j ++){
                annotation annotation = runtimeVisibleAnnotations.annotations[j] = new annotation();
                processAnnotation(annotation);
            }
            attributes[index] = runtimeVisibleAnnotations;
        }else if(TypeUtils.compare(s, attributeStrs[15])){
            RuntimeInvisibleAnnotations_attribute runtimeInvisibleAnnotations = new RuntimeInvisibleAnnotations_attribute();
            runtimeInvisibleAnnotations.attribute_name_index = attributes_name_index;
            runtimeInvisibleAnnotations.attribute_length = IOUtils.read_u4();
            runtimeInvisibleAnnotations.num_annotations = IOUtils.read_u2();
            Integer annotations_size = TypeUtils.byteArr2Int(runtimeInvisibleAnnotations.num_annotations.u2);
            for(Integer j = 0; j < annotations_size; j ++){
                annotation annotation = runtimeInvisibleAnnotations.annotations[j];
                processAnnotation(annotation);
            }
            attributes[index] = runtimeInvisibleAnnotations;
        }else if(TypeUtils.compare(s, attributeStrs[16])){
            RuntimeVisibleParameterAnnotations_attribute runtimeVisibleParameterAnnotations = new RuntimeVisibleParameterAnnotations_attribute();
            runtimeVisibleParameterAnnotations.attribute_name_index = attributes_name_index;
            runtimeVisibleParameterAnnotations.attribute_length = IOUtils.read_u4();
            runtimeVisibleParameterAnnotations.num_parameters = IOUtils.read_u1();
            Integer parameters_size = TypeUtils.byteArr2Int(runtimeVisibleParameterAnnotations.num_parameters.u1);
            for(Integer j = 0; j < parameters_size; j ++){
                parameter_annotation parameter_annotation = runtimeVisibleParameterAnnotations.parameter_annotations[j];
                processParameter_annotation(parameter_annotation);
            }
            attributes[index] = runtimeVisibleParameterAnnotations;
        }else if(TypeUtils.compare(s, attributeStrs[17])){
            RuntimeInvisibleParameterAnnotations_attribute runtimeInvisibleParameterAnnotations = new RuntimeInvisibleParameterAnnotations_attribute();
            runtimeInvisibleParameterAnnotations.attribute_name_index = attributes_name_index;
            runtimeInvisibleParameterAnnotations.attribute_length = IOUtils.read_u4();
            runtimeInvisibleParameterAnnotations.num_parameters = IOUtils.read_u1();
            Integer parameters_size = TypeUtils.byteArr2Int(runtimeInvisibleParameterAnnotations.num_parameters.u1);
            for(Integer j = 0; j < parameters_size; j ++){
                parameter_annotation parameter_annotation = runtimeInvisibleParameterAnnotations.parameter_annotations[j];
                processParameter_annotation(parameter_annotation);
            }
            attributes[index] = runtimeInvisibleParameterAnnotations;

            /*java8增加 RuntimeVisibleTypeAnnotations_attribute*/
        }else if(TypeUtils.compare(s, attributeStrs[18])){

            /*java8增加 RuntimeInvisibleTypeAnnotations_attribute*/
        }else if(TypeUtils.compare(s, attributeStrs[19])){

        }else if(TypeUtils.compare(s, attributeStrs[20])){
            AnnotationDefault_attribute annotationDefault = new AnnotationDefault_attribute();
            annotationDefault.attribute_name_index = attributes_name_index;
            annotationDefault.attribute_length = IOUtils.read_u4();
            processElement_value(annotationDefault.default_value);
            attributes[index] = annotationDefault;
        }else if(TypeUtils.compare(s, attributeStrs[21])){
            BootstrapMethods_attribute bootstrapMethods = new BootstrapMethods_attribute();
            bootstrapMethods.attribute_name_index = attributes_name_index;
            bootstrapMethods.attribute_length = IOUtils.read_u4();
            bootstrapMethods.num_bootstrap_methods = IOUtils.read_u2();
            Integer bootstrapMethods_size = TypeUtils.byteArr2Int(bootstrapMethods.num_bootstrap_methods.u2);
            bootstrapMethods.bootstrap_methods = new bootstrap_method[bootstrapMethods_size];
            for(Integer j = 0; j < bootstrapMethods_size; j ++){
                bootstrap_method bootstrap_method = new bootstrap_method();
                bootstrap_method.bootstrap_method_ref = IOUtils.read_u2();
                bootstrap_method.num_bootstrap_arguments = IOUtils.read_u2();
                Integer arguments_size = TypeUtils.byteArr2Int(bootstrap_method.num_bootstrap_arguments.u2);
                bootstrap_method.bootstrap_arguments = new u2[arguments_size];
                for(Integer k = 0; k < arguments_size; k ++){
                    bootstrap_method.bootstrap_arguments[k] = IOUtils.read_u2();
                }
                bootstrapMethods.bootstrap_methods[j] = bootstrap_method;
            }
            attributes[index] = bootstrapMethods;

            /*java8增加 MethodParameters_attribute*/
        }else if(TypeUtils.compare(s, attributeStrs[22])){

        }
    }

    void processParameter_annotation(parameter_annotation parameter_annotation){
        parameter_annotation.num_annotations = IOUtils.read_u2();
        Integer annotations_size = TypeUtils.byteArr2Int(parameter_annotation.num_annotations.u2);
        parameter_annotation.annotations = new annotation[annotations_size];
        for(Integer k = 0; k < annotations_size; k ++){
            parameter_annotation.annotations[k] = new annotation();
            processAnnotation(parameter_annotation.annotations[k]);
        }
    }

    void processAnnotation(annotation annotation){
        annotation.type_index = IOUtils.read_u2();
        annotation.num_element_value_pairs = IOUtils.read_u2();
        Integer pairs_size = TypeUtils.byteArr2Int(annotation.num_element_value_pairs.u2);
        annotation.element_value_pairs = new element_value_pair[pairs_size];
        for(Integer k = 0; k < pairs_size; k ++){
            annotation.element_value_pairs[k] = new element_value_pair();
            processElement_value_pair(annotation.element_value_pairs[k]);
        }
    }

    void processElement_value_pair(element_value_pair element_value_pair){
        element_value_pair.element_name_index = IOUtils.read_u2();
        processElement_value(element_value_pair.value);
    }

    void processElement_value(element_value element_value ){
        u1 element_value_tag = IOUtils.read_u1();
        Integer tag_integer = TypeUtils.byteArr2Int(element_value_tag.u1);
//        if(tag_integer == Integer.valueOf('B')){
//
//        }else if(tag_integer == Integer.valueOf('C')){
//
//        }else if(tag_integer == Integer.valueOf('D')){
//
//        }else if(tag_integer == Integer.valueOf('F')){
//
//        }else if(tag_integer == Integer.valueOf('I')){
//
//        }else if(tag_integer == Integer.valueOf('J')){
//
//        }else if(tag_integer == Integer.valueOf('S')){
//
//        }else if(tag_integer == Integer.valueOf('Z')){
//
//        }else if(tag_integer == Integer.valueOf('s')){
//            const_value_index const_value_index =  new const_value_index();
//            const_value_index.tag = element_value_tag;
//            const_value_index.const_value_index = IOUtils.read_u2();
//            element_value = const_value_index;
//        }
        if(tag_integer == Integer.valueOf('B')
                ||tag_integer == Integer.valueOf('C')
                ||tag_integer == Integer.valueOf('D')
                ||tag_integer == Integer.valueOf('F')
                ||tag_integer == Integer.valueOf('I')
                ||tag_integer == Integer.valueOf('J')
                ||tag_integer == Integer.valueOf('S')
                ||tag_integer == Integer.valueOf('Z')
                ||tag_integer == Integer.valueOf('s')){
            const_value_index const_value_index =  new const_value_index();
            const_value_index.tag = element_value_tag;
            const_value_index.const_value_index = IOUtils.read_u2();
            element_value = const_value_index;
        }
        else if(tag_integer == Integer.valueOf('e')){
            enum_const_value enum_const_value =  new enum_const_value();
            enum_const_value.tag = element_value_tag;
            enum_const_value.type_name_index = IOUtils.read_u2();
            enum_const_value.const_name_index = IOUtils.read_u2();
            element_value = enum_const_value;
        }else if(tag_integer == Integer.valueOf('c')){
            class_info_index class_info_index =  new class_info_index();
            class_info_index.tag = element_value_tag;
            class_info_index.class_info_index = IOUtils.read_u2();
            element_value = class_info_index;
        }else if(tag_integer == Integer.valueOf('@')){
            value_annotation value_annotation = new value_annotation();
            value_annotation.tag = element_value_tag;
            value_annotation.annotation_value = new annotation();
            processAnnotation(value_annotation.annotation_value);
            element_value = value_annotation;
        }else if(tag_integer == Integer.valueOf('[')){
            array_value array_value =  new array_value();
            array_value.tag = element_value_tag;
            array_value.num_values = IOUtils.read_u2();
            Integer num_value_integer = TypeUtils.byteArr2Int(array_value.num_values.u2);
            element_value[] element_values = new element_value[num_value_integer];
            for(Integer i = 0; i < num_value_integer; i++){
                processElement_value(element_values[i]);
            }
            array_value.values = element_values;
            element_value = array_value;
        }

    }

    void processStackMapTable(StackMapTable_attribute stackMapTable_attribute){
        stackMapTable_attribute.attribute_length = IOUtils.read_u4();
        stackMapTable_attribute.number_of_entries = IOUtils.read_u2();
        Integer len = TypeUtils.byteArr2Int(stackMapTable_attribute.number_of_entries.u2);
        stackMapTable_attribute.entries = new stack_map_frame[len];
        for(Integer i = 0; i < len; i++){
            // stackMapTable_attribute.entries[i];
            u1 frame_tag = IOUtils.read_u1();
            Integer frame_tag_integer = TypeUtils.byteArr2Int(frame_tag.u1);
            /* 128 至 246是预留的*/
            /*same_frame */
            if(frame_tag_integer >= 0 && frame_tag_integer <= 63 ){
                same_frame same_frame = new same_frame();
                same_frame.frame_type = frame_tag;
                stackMapTable_attribute.entries[i] = same_frame;

                /*same_locals_1_stack_item_frame*/
            }else if(frame_tag_integer >= 64 && frame_tag_integer <= 127){
                same_locals_1_stack_item_frame same_locals_1_stack_item_frame = new same_locals_1_stack_item_frame();
                same_locals_1_stack_item_frame.frame_type = frame_tag;
                same_locals_1_stack_item_frame.stack = new verification_type_info[1];
                u1 verification_type_tag = IOUtils.read_u1();
                same_locals_1_stack_item_frame.stack[0] = getVerificationTypeTag(verification_type_tag);
                stackMapTable_attribute.entries[i] = same_locals_1_stack_item_frame;

                /*same_locals_1_stack_item_frame_extended*/
            }else if(frame_tag_integer == 247){
                same_locals_1_stack_item_frame_extended same_locals_1_stack_item_frame_extended = new same_locals_1_stack_item_frame_extended();
                same_locals_1_stack_item_frame_extended.frame_type = frame_tag;
                same_locals_1_stack_item_frame_extended.offset_delta = IOUtils.read_u2();
                same_locals_1_stack_item_frame_extended.stack = new verification_type_info[1];
                u1 verification_type_tag = IOUtils.read_u1();
                same_locals_1_stack_item_frame_extended.stack[0] = getVerificationTypeTag(verification_type_tag);
                stackMapTable_attribute.entries[i] = same_locals_1_stack_item_frame_extended;

                /*chop_frame*/
            }else if(frame_tag_integer >= 248 && frame_tag_integer <= 250){
                chop_frame chop_frame = new chop_frame();
                chop_frame.frame_type = frame_tag;
                chop_frame.offset_delta = IOUtils.read_u2();
                stackMapTable_attribute.entries[i] = chop_frame;

                /*same_frame_extended*/
            }else if(frame_tag_integer == 251){
                same_frame_extended same_frame_extended = new same_frame_extended();
                same_frame_extended.frame_type = frame_tag;
                same_frame_extended.offset_delta = IOUtils.read_u2();
                stackMapTable_attribute.entries[i] = same_frame_extended;

                /*append_frame*/
            }else if(frame_tag_integer >= 252 && frame_tag_integer <= 254){
                append_frame append_frame = new append_frame();
                append_frame.frame_type = frame_tag;
                append_frame.offset_delta = IOUtils.read_u2();
                int locals_size = frame_tag_integer - 251;
                append_frame.locals = new verification_type_info[locals_size];
                for(Integer j = 0; j < locals_size; j ++){
                    u1 verification_type_tag = IOUtils.read_u1();
                    append_frame.locals[j] = getVerificationTypeTag(verification_type_tag);
                }
                stackMapTable_attribute.entries[i] = append_frame;

                /*full_frame*/
            }else if(frame_tag_integer >= 255 ){
                full_frame full_frame = new full_frame();
                full_frame.frame_type = frame_tag;
                full_frame.offset_delta = IOUtils.read_u2();
                full_frame.number_of_locals = IOUtils.read_u2();
                int locals_size = TypeUtils.byteArr2Int(full_frame.number_of_locals.u2);
                full_frame.locals = new verification_type_info[locals_size];
                for(Integer j = 0; j < locals_size; j ++){
                    u1 verification_type_tag = IOUtils.read_u1();
                    full_frame.locals[j] = getVerificationTypeTag(verification_type_tag);
                }

                full_frame.number_of_stack_items = IOUtils.read_u2();
                int stack_items_size = TypeUtils.byteArr2Int(full_frame.number_of_stack_items.u2);
                full_frame.stack = new verification_type_info[stack_items_size];
                for(Integer j = 0; j < stack_items_size; j ++){
                    u1 verification_type_tag = IOUtils.read_u1();
                    full_frame.stack[j] = getVerificationTypeTag(verification_type_tag);
                }

                stackMapTable_attribute.entries[i] = full_frame;
            }
        }
    }

    verification_type_info getVerificationTypeTag(u1 tag){
        Integer tag_integer = TypeUtils.byteArr2Int(tag.u1);
        if(tag_integer == 0){
            Top_variable_info top_variable_info = new Top_variable_info();
            top_variable_info.tag = tag;
            return top_variable_info;
        }else if(tag_integer == 1){
            Integer_variable_info integer_variable_info = new Integer_variable_info();
            integer_variable_info.tag = tag;
            return integer_variable_info;
        }else if(tag_integer == 2){
            Float_variable_info float_variable_info = new Float_variable_info();
            float_variable_info.tag = tag;
            return float_variable_info;
        }else if(tag_integer == 4){
            Long_variable_info long_variable_info = new Long_variable_info();
            long_variable_info.tag = tag;
            return long_variable_info;
        }else if(tag_integer == 3){
            Double_variable_info double_variable_info = new Double_variable_info();
            double_variable_info.tag = tag;
            return double_variable_info;
        }else if(tag_integer == 5){
            Null_variable_info null_variable_info = new Null_variable_info();
            null_variable_info.tag = tag;
            return null_variable_info;
        }else if(tag_integer == 6){
            UninitializedThis_variable_info uninitializedThis_variable_info = new UninitializedThis_variable_info();
            uninitializedThis_variable_info.tag = tag;
            return uninitializedThis_variable_info;
        }else if(tag_integer == 7){
            Object_variable_info object_variable_info = new Object_variable_info();
            object_variable_info.tag = tag;
            object_variable_info.cpool_index = IOUtils.read_u2();
            return object_variable_info;
        }else if (tag_integer == 8){
            Uninitialized_variable_info uninitialized_variable_info = new Uninitialized_variable_info();
            uninitialized_variable_info.tag = tag;
            uninitialized_variable_info.offset = IOUtils.read_u2();
            return uninitialized_variable_info;
        }
        return null;
    }

    enum TAG{
        CONSTANT_Utf8("CONSTANT_Utf8", (byte)0x1),
        CONSTANT_Integer("CONSTANT_Integer", (byte)0x3),
        CONSTANT_Float("CONSTANT_Float", (byte)0x4),
        CONSTANT_Long("CONSTANT_Long", (byte)0x5),
        CONSTANT_Double("CONSTANT_Double", (byte)0x6),
        CONSTANT_Class("CONSTANT_Class", (byte)0x7),
        CONSTANT_String("CONSTANT_String", (byte)0x8),
        CONSTANT_Fieldref("CONSTANT_Fieldref", (byte)0x9),
        CONSTANT_Methodref("CONSTANT_Methodref", (byte)0x10),
        CONSTANT_InterfaceMethodref("CONSTANT_InterfaceMethodref", (byte)0x11),
        CONSTANT_NameAndType("CONSTANT_NameAndType", (byte)0x12),
        CONSTANT_MethodHandle("CONSTANT_MethodHandle", (byte)0x15),
        CONSTANT_MethodType("CONSTANT_MethodType", (byte)0x16),
        CONSTANT_InvokeDynamic("CONSTANT_InvokeDynamic", (byte)0x17);

        public String name;
        public byte index;
        private TAG(String name, byte index){
            this.name = name;
            this.index = index;
        }
    }

}


//class verification_type_info  {
//    Top_variable_info top_variable_info;
//    Integer_variable_info integer_variable_info;
//    Float_variable_info float_variable_info;
//    Long_variable_info long_variable_info;
//    Double_variable_info double_variable_info;
//    Null_variable_info null_variable_info;
//    UninitializedThis_variable_info uninitializedThis_variable_info;
//}

