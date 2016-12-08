package dk.nodes.okhttputils;

/**
 * Created by joso on 29/11/2016.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
public @interface PayloadName {
    String value() default "";
}
