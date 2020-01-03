StringBuilderTest.class中 String var1 = "hello,"; 调用链  
```
()V StringBuilder.<init>()
    (I)V AbstractStringBuilder.<init>()
        (Ljava/lang/String;)Ljava/lang/StringBuilder; StringBuilder.append()
            (Ljava/lang/String;)Ljava/lang/AbstractStringBuilder; AbstractStringBuilder.append()
                (I)V AbstractStringBuilder.ensureCapacityInternal()
                    (II[CI)V Stirng.getChars()
                        (Ljava/lang/Object;ILjava/lang/Object;II)V System.arraycopy()  --native方法
```
