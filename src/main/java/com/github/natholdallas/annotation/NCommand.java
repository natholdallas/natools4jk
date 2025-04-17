package com.github.natholdallas.annotation;

import snw.jkook.command.JKookCommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参考 {@link JKookCommand}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NCommand {

    /**
     * 命令
     *
     * @return {@code JKookCommand.rootName}
     */
    String value();

    /**
     * 说明
     *
     * @return {@code JKookCommand.description}
     */
    String description() default "";

    /**
     * 帮助内容
     *
     * @return {@code JKookCommand.helpContent}
     */
    String helpContent() default "";

    /**
     * 前缀
     *
     * @return {@code JKookCommand.prefixes}
     */
    String[] prefixes() default {};

    /**
     * 别名
     *
     * @return {@code JKookCommand.aliases}
     */
    String[] aliases() default {};

    /**
     * 参数
     *
     * @return {@code JKookCommand.arguments}
     */
    Class<?>[] arguments() default {};

    /**
     * 添加子命令
     *
     * @return {@code JKookCommand.subCommands}
     */
    Class<?>[] subCommands() default {};

}
