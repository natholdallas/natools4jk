package com.github.natholdallas.utils;

import org.jetbrains.annotations.NotNull;
import snw.jkook.entity.abilities.Accessory;
import snw.jkook.message.component.FileComponent;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.CardScopeElement;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.*;
import snw.jkook.message.component.card.module.*;
import snw.jkook.message.component.card.structure.Paragraph;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * 继承自 {@link CardBuilder} <br/>
 * 此类开启了建造者模式,链式调用
 */
public final class NCardBuilder extends CardBuilder {

    /**
     * 此构造方法可以帮您设置好卡片的默认值 <br/>
     * 主题为随机主题,大小设置为标准的 LG
     */
    public NCardBuilder() {
        setTheme(Theme.values()[new Random().nextInt(1, Theme.values().length)]);
        setSize(Size.LG);
    }

    /**
     * 此构造方法需要自己设置主题与大小, 它们不允许为 null.
     *
     * @param theme 主题,参见{@link Theme}
     * @param size  大小,参见{@link Size}
     */
    public NCardBuilder(@NotNull Theme theme, @NotNull Size size) {
        setTheme(Objects.requireNonNull(theme));
        setSize(Objects.requireNonNull(size));
    }

    /**
     * 你可以通过此方法在卡片模块中添加一张新的卡片.
     */
    public @NotNull NCardBuilder newCard() {
        super.newCard();
        return this;
    }

    /**
     * <blockquote><pre>
     * {@code
     *      // 使用 UnsafeModuleBuilder 构建
     *      new NCardBuilder()
     *          .addActionGroupModule(
     *              InteractElement...
     *          );
     * }
     * </blockquote></pre>
     *
     * @param interactElements InteractElement 的数量必须在(1~4)之间, 目前他的实现类都可以安全插入
     */
    public NCardBuilder addActionGroupModule(@NotNull InteractElement... interactElements) {
        addModule(new ActionGroupModule(List.of(interactElements)));
        return this;
    }

    /**
     * 此方法允许你用原生的形式构建 {@link ActionGroupModule}
     * 此方法为重载,具体的 Doc 请参见上面的说明.
     *
     * @param interactElements InteractElement 的数量必须在(1~4)之间, 目前他的实现类都可以安全插入
     */
    public NCardBuilder addActionGroupModule(@NotNull List<InteractElement> interactElements) {
        addModule(new ActionGroupModule(interactElements));
        return this;
    }

    /**
     * 添加存放图像元素的组模块, 此方法封装了 {@link CardBuilder} addModule() 方法 <br/>
     *
     * @param imageElements 封装了 {@link ImageGroupModule} 的构造方法, ImageElement 的数量必须在(1~9)之间
     *                      <blockquote><pre>
     *                                           {@code
     *                                                // 使用 UnsafeModuleBuilder 构建
     *                                                new NCardBuilder()
     *                                                    .addImageGroupModule(
     *                                                        ImageElement...
     *                                                    );
     *                                           }
     */
    public NCardBuilder addContainerModule(@NotNull ImageElement... imageElements) {
        addModule(new ContainerModule(List.of(imageElements)));
        return this;
    }

    /**
     * 此方法允许你用原生的形式构建 {@link ContainerModule}
     * 此方法为重载,具体的 Doc 请参见上面的说明.
     *
     * @param imageElements ImageElement 的数量必须在(1~9)之间
     */
    public NCardBuilder addContainerModule(@NotNull List<ImageElement> imageElements) {
        addModule(new ContainerModule(imageElements));
        return this;
    }

    /**
     * 添加备注, 此方法封装了 {@link CardBuilder} addModule() 方法 <br/>
     * 一个可以安全插入进 List 的元素:
     * <ul>
     *      <li>{@link PlainTextElement}</li>
     *      <li>{@link MarkdownElement}</li>
     *      <li>{@link ImageElement}</li>
     * </ul>
     *
     * @param baseElements 此参数中的 List 中传入的参数参见上面的例举,否则会抛出异常
     *                     <blockquote><pre>
     *                                         {@code
     *                                              // 使用 UnsafeModuleBuilder 构建
     *                                              new NCardBuilder()
     *                                                  .addContextModule(
     *                                                      PlainTextElement...
     *                                                      MarkdownElement...
     *                                                      ImageElement...
     *                                                  );
     *                                         }
     */
    public NCardBuilder addContextModule(@NotNull BaseElement... baseElements) {
        addModule(new ContextModule(List.of(baseElements)));
        return this;
    }

    /**
     * 此方法允许你用原生的形式构建 {@link BaseElement}
     * 此方法为重载,具体的 Doc 请参见上面的说明.
     * 一个可以安全插入进 List 的元素:
     * <ul>
     *      <li>{@link PlainTextElement}</li>
     *      <li>{@link MarkdownElement}</li>
     *      <li>{@link ImageElement}</li>
     * </ul>
     *
     * @param baseElements 此参数中的 List 中传入的参数参见上面的例举,否则会抛出异常
     */
    public NCardBuilder addContextModule(@NotNull List<BaseElement> baseElements) {
        addModule(new ContextModule(baseElements));
        return this;
    }

    /**
     * 添加倒计时, 此方法封装了 {@link CardBuilder} addModule() 方法.
     *
     * @param type    倒计时的类型,详情见 {@link CountdownModule.Type}
     * @param endTime 结束时间
     *                <blockquote><pre>
     *                               {@code
     *                                    new NCardBuilder().addCountdownModule(CountdownModule.Type, long);
     *                               }
     */
    public NCardBuilder addCountdownModule(CountdownModule.Type type, long endTime) {
        addModule(new CountdownModule(type, endTime));
        return this;
    }

    /**
     * 添加分割线, 此方法封装了 {@link CardBuilder} addModule() 方法.
     *
     * <blockquote><pre>
     * {@code
     *      new NCardBuilder().addDividerModule();
     * }
     */
    public NCardBuilder addDividerModule() {
        addModule(DividerModule.INSTANCE);
        return this;
    }

    /**
     * 添加文件, 此方法封装了 {@link CardBuilder} addModule() 方法.
     *
     * @param type  文件消息组件的展示形式, 详情见 {@link  FileComponent.Type}
     * @param src   文件路径
     * @param title 文件标题
     * @param cover 封面的路径
     *              <blockquote><pre>
     *                           {@code
     *                                new NCardBuilder()
     *                                    .addFileModule(FileComponent.Type, String, String, String);
     *                           }
     */
    public NCardBuilder addFileModule(FileComponent.Type type, String src, String title, String cover) {
        addModule(new FileModule(type, src, title, cover));
        return this;
    }

    /**
     * 添加标题,只接收纯文本, 此方法封装了 {@link CardBuilder} addModule() 方法.
     *
     * @param content 纯文本信息
     * @param emoji   是否开启渲染 emoji 语法
     *                <blockquote><pre>
     *                               {@code
     *                                    new NCardBuilder()
     *                                        .addHeaderModule(String, boolean);
     *                               }
     */
    public NCardBuilder addHeaderModule(String content, boolean emoji) {
        addModule(new HeaderModule(new PlainTextElement(content, emoji)));
        return this;
    }

    /**
     * (重载方法)
     * 添加标题,只接收纯文本, 此方法封装了 {@link CardBuilder} addModule() 方法.
     *
     * @param content 纯文本信息
     *                <blockquote><pre>
     *                               {@code
     *                                    new NCardBuilder()
     *                                        .addHeaderModule(String);
     *                               }
     */
    public NCardBuilder addHeaderModule(String content) {
        addModule(new HeaderModule(content));
        return this;
    }

    /**
     * 添加一个用于存储图像的组模块, 此方法封装了 {@link CardBuilder} addModule() 方法 <br/>
     *
     * @param imageElements 封装了 {@link ImageGroupModule} 的构造方法, ImageElement 的数量必须在(1~9)之间
     *                      <blockquote><pre>
     *                                           {@code
     *                                                // 使用 UnsafeModuleBuilder 构建
     *                                                new NCardBuilder()
     *                                                    .addImageGroupModule(
     *                                                        ImageElement...
     *                                                    )
     *                                           }
     */
    public NCardBuilder addImageGroupModule(@NotNull ImageElement... imageElements) {
        addModule(new ImageGroupModule(List.of(imageElements)));
        return this;
    }

    /**
     * 此方法允许你用原生的形式构建 {@link ImageGroupModule}
     * 此方法为重载,具体的 Doc 请参见上面的说明.
     *
     * @param imageElements ImageElement 的数量必须在(1~9)之间
     */
    public NCardBuilder addImageGroupModule(@NotNull List<ImageElement> imageElements) {
        addModule(new ImageGroupModule(imageElements));
        return this;
    }

    /**
     * 添加一个存储邀请链接/邀请码的模块.
     *
     * @param code 链接/邀请码, 详情见 {@link InviteModule}
     *             <blockquote><pre>
     *                         {@code
     *                              new NCardBuilder()
     *                                  .addInviteModule(String);
     *                         }
     */
    public NCardBuilder addInviteModule(String code) {
        addModule(new InviteModule(code));
        return this;
    }

    /**
     * {@link SectionModule} 的构造方法中的第一个参数虽然是 {@link CardScopeElement} <br/>
     * 但他仍然不能安全使用,因此我们封装了此构造方法第一个参数并做出重载 <br/>
     * 我们分为了三个模块,它们分别是 {@link PlainTextElement}, {@link  MarkdownElement}, {@link Paragraph} <br/>
     * 为什么原生的 SectionModule 构造不安全? <br/>
     * 在构造方法中的第一个参数 {@link CardScopeElement},他的实现有许多,但是在构造方法中 <br/>
     * 需要验证传入的参数必须为 {@link PlainTextElement}, {@link  MarkdownElement}, {@link Paragraph} <br/>
     * 否则将会抛出异常,因此我做了此封装,可以完完全全的安全使用.
     *
     * @param plainTextElement 文本
     * @param accessory        填入 {@link Accessory} 的实现即可
     * @param mode             填入 {@link Accessory.Mode} 中的枚举值
     *                         <blockquote><pre>
     *                                                 {@code
     *                                                      new NCardBuilder()
     *                                                          .addSectionModule(PlainTextElement, Accessory, Accessory.Mode);
     *                                                 }
     */
    public NCardBuilder addSectionModule(PlainTextElement plainTextElement, Accessory accessory, Accessory.Mode mode) {
        addModule(new SectionModule(plainTextElement, accessory, mode));
        return this;
    }

    /**
     * (重载方法)
     * 参见上面的说明.
     *
     * @param markdownElement KMarkdown 文本
     * @param accessory       填入 {@link Accessory} 的实现即可
     * @param mode            填入 {@link Accessory.Mode} 中的枚举值
     *                        <blockquote><pre>
     *                                               {@code
     *                                                    new NCardBuilder()
     *                                                        .addSectionModule(MarkdownElement, Accessory, Accessory.Mode);
     *                                               }
     */
    public NCardBuilder addSectionModule(MarkdownElement markdownElement, Accessory accessory, Accessory.Mode mode) {
        addModule(new SectionModule(markdownElement, accessory, mode));
        return this;
    }

    /**
     * (重载方法)
     * 参见上面的说明.
     *
     * @param paragraph 参见 {@link Paragraph} 的构造方法
     * @param accessory 填入 {@link Accessory} 的实现即可
     * @param mode      填入 {@link Accessory.Mode} 中的枚举值
     *                  <blockquote><pre>
     *                                   {@code
     *                                        new NCardBuilder()
     *                                            .addSectionModule(Paragraph, Accessory, Accessory.Mode);
     *                                   }
     */
    public NCardBuilder addSectionModule(Paragraph paragraph, Accessory accessory, Accessory.Mode mode) {
        addModule(new SectionModule(paragraph, accessory, mode));
        return this;
    }

    /**
     * 多列文本模块.
     * 基于 {@link SectionModule} 封装的模块,你可以快速构建出一个 {@link Paragraph}.
     * 一个可以安全插入进 Collection 的元素:
     * <ul>
     *      <li>{@link PlainTextElement}</li>
     *      <li>{@link MarkdownElement}</li>
     * </ul>
     *
     * @param column       行数, {@link Paragraph} 中构造方法的第一个参数
     * @param baseElements 此参数中的 Collection 中传入的参数参见上面的例举,否则会抛出异常
     *                     <blockquote><pre>
     *                                         {@code
     *                                              new NCardBuilder()
     *                                                  .addParagraphModule(
     *                                                      int,
     *                                                      PlainTextElement...
     *                                                      MarkdownElement...
     *                                                  );
     *                                         }
     */
    public NCardBuilder addParagraphModule(int column, BaseElement... baseElements) {
        addModule(new SectionModule(new Paragraph(column, List.of(baseElements)), null, null));
        return this;
    }

    /**
     * 此方法允许你用原生的形式构建 {@link Paragraph}
     * 此方法为重载,具体的 Doc 请参见上面的说明.
     * 一个可以安全插入进 Collection 的元素:
     * <ul>
     *      <li>{@link PlainTextElement}</li>
     *      <li>{@link MarkdownElement}</li>
     * </ul>
     *
     * @param baseElements 此参数中的 Collection 中传入的参数参见上面的例举,否则会抛出异常
     */
    public NCardBuilder addParagraphModule(int column, @NotNull List<BaseElement> baseElements) {
        addModule(new SectionModule(new Paragraph(column, baseElements), null, null));
        return this;
    }

    /**
     * Markdown 文本模块.
     *
     * @param content 文本
     *                <blockquote><pre>
     *                               {@code
     *                                    new NCardBuilder()
     *                                        .addMarkdownModule(String);
     *                               }
     */
    public NCardBuilder addMarkdownModule(String content) {
        addModule(new SectionModule(new MarkdownElement(content), null, null));
        return this;
    }

    /**
     * 普通文本模块.
     *
     * @param content 文本
     * @param emoji   Emoji 表情
     *                <blockquote><pre>
     *                               {@code
     *                                    new NCardBuilder()
     *                                        .addTextModule(String, boolean);
     *                               }
     */
    public NCardBuilder addTextModule(String content, boolean emoji) {
        addModule(new SectionModule(new PlainTextElement(content, emoji), null, null));
        return this;
    }

    /**
     * (重载方法)
     * 普通文本模块.
     *
     * @param content 文本
     *                <blockquote><pre>
     *                               {@code
     *                                    new NCardBuilder()
     *                                        .addTextModule(String);
     *                               }
     */
    public NCardBuilder addTextModule(String content) {
        addModule(new SectionModule(new PlainTextElement(content), null, null));
        return this;
    }

}
