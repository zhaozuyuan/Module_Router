package com.module_router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Route {
    /**
     * who module
     */
    String module() default "";

    /**
     * class nickname
     */
    String name() default "";

    /**
     * describe
     */
    String desc() default "";
}
