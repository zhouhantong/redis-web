package com.zy.zht.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/9/18.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ModelProp {
    public String name();
    public int colIndex() default -1;
    public boolean nullable() default true;
    public String interfaceXmlName()default  "";
}
