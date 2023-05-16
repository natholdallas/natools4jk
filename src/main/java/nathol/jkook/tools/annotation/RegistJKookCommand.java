package nathol.jkook.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegistJKookCommand {

    String value();

    String description() default "";

    String helpContent() default "";

    String[] prefixes() default {};

    String[] aliases() default {};

    Class<?>[] subCommands() default {};

    boolean forceRegist() default false;

}
