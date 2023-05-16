package nathol.jkook.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解的作用是方便你在不想移除掉一个 <br/>
 * Natools4jk 的功能组件的代码内容情况下使用的注解 <br/>
 * 被此注解标注的所有功能组件会在装载配置的时候忽略
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Ignored {
}
