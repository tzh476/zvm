package com.zvm.classfile.attribute;

import com.zvm.basestruct.u2;
import com.zvm.basestruct.u4;

public class BootstrapMethods_attribute extends Attribute_Base {
public u2 attribute_name_index;
public u4 attribute_length;
public u2 num_bootstrap_methods;
    public bootstrap_method[] bootstrap_methods;
}
