package nathol.jkook.tools.natools.card;

import nathol.jkook.tools.natools.card.module.BaseBuilder;
import nathol.jkook.tools.natools.card.module.ImageBuilder;
import nathol.jkook.tools.natools.card.module.InteractBuilder;
import nathol.jkook.tools.natools.card.module.ParagraphBuilder;
import nathol.jkook.tools.natools.utils.EasyBuilder;

import org.jetbrains.annotations.NotNull;
import snw.jkook.entity.abilities.Accessory;
import snw.jkook.message.component.FileComponent;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.CardScopeElement;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.InteractElement;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.*;
import snw.jkook.message.component.card.structure.Paragraph;

import java.util.Random;

/**
 * 继承自 {@link CardBuilder} <br/>
 * 此类开启了建造者模式,链式调用 <br/>
 * 因此大多数不需要关心返回值
 */
@SuppressWarnings("all")
public class NCardBuilder extends CardBuilder {
    /**
     * 此构造方法可以帮您设置好卡片的默认值 <br/>
     * 主题为随机主题,大小设置为标准的 LG
     */
    public NCardBuilder() {
        setTheme(EasyBuilder.randowTheme());
        setSize(Size.LG);
    }

    /**
     * 此构造方法需要自己设置主题与大小
     * @param theme 主题,参见{@link Theme}
     * @param size 大小,参见{@link Size}
     */
    public NCardBuilder(Theme theme, Size size) {
        setTheme(theme);
        setSize(size);
    }

    /**
     * 添加存放 {@link InteractElement} 的组模块, 此方法封装了 {@link CardBuilder} addModule() 方法
     * @param interactBuilder 封装了 {@link ActionGroupModule} 的构造方法, 详情见 {@link InteractBuilder}
     * <blockquote><pre>
     * {@code
     *      interactBuilder = new interactBuilder()
     *           .addButton(Theme, String, ButtonElement.EventType, MarkdownElement)
     *           .addButton(Theme, String, MarkdownElement)
     *           .addButton(Theme, String, ButtonElement.EventType, PlainTextElement)
     *           .addButton(Theme, String, PlainTextElement)
     *      new NCardBuilder().addActionGroupModule(interactBuilder);
     * }
     * 可能需要预览的类{@link EasyBuilder}, {@link Theme}, {@link String}, {@link ButtonElement.EventType}, {@link MarkdownElement}, {@link PlainTextElement}
     */
    public NCardBuilder addActionGroupModule(@NotNull InteractBuilder interactBuilder) {
        addModule(new ActionGroupModule(interactBuilder.build()));
        return this;
    }

    /**
     * 添加存放图像元素的组模块, 此方法封装了 {@link CardBuilder} addModule() 方法
     * @param imageBuilder 封装了 {@link ContainerModule} 的构造方法, 详情见 {@link  ImageBuilder}
     * <blockquote><pre>
     * {@code
     *      imageBuilder imageBuilder = new imageBuilder()
     *         .add("src", "alt", true)
     *         .add("src", "alt", Size.LG, true);
     *      new NCardBuilder().addContainerModule(imageBuilder)
     * }
     */
    public NCardBuilder addContainerModule(@NotNull ImageBuilder imageBuilder) {
        addModule(new ContainerModule(imageBuilder.build()));
        return this;
    }

    /**
     * 添加备注, 此方法封装了 {@link CardBuilder} addModule() 方法
     * @param baseBuilder 封装了 {@link ContextModule} 的构造方法, 详情见 {@link  BaseBuilder}
     * <blockquote><pre>
     * {@code
     *      baseBuilder baseBuilder = new baseBuilder()
     *          .addPlainText("hello world")
     *          .addPlainText("hello world")
     *          .addImage("src", "alt", Size.LG, false)
     *          .addImage("src", "alt", false)
     *          .addMarkdown("hello world");
     *      new NCardBuilder().addContextModule(baseBuilder)
     * }
     */
    public NCardBuilder addContextModule(@NotNull BaseBuilder baseBuilder) {
        addModule(new ContextModule(baseBuilder.build()));
        return this;
    }

    /**
     * 添加倒计时, 此方法封装了 {@link CardBuilder} addModule() 方法
     * @param type 倒计时的类型,详情见 {@link CountdownModule.Type}
     * @param endTime 结束时间
     * <blockquote><pre>
     * {@code
     *      new NCardBuilder().addCountdownModule(CountdownModule.Type.DAY, 114514);
     * }
     */
    public NCardBuilder addCountdownModule(CountdownModule.Type type, long endTime) {
        addModule(new CountdownModule(type, endTime));
        return this;
    }

    /**
     * 添加分割线, 此方法封装了 {@link CardBuilder} addModule() 方法
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
     * 添加文件, 此方法封装了 {@link CardBuilder} addModule() 方法
     * @param type 文件消息组件的展示形式, 详情见 {@link  FileComponent.Type}
     * @param src 文件路径
     * @param title 文件标题
     * @param cover 封面的路径
     * <blockquote><pre>
     * {@code
     *      new NCardBuilder()
     *          .addFileModule(FileComponent.Type.FILE, "src", "title", "coverSrc")
     * }
     */
    public NCardBuilder addFileModule(FileComponent.Type type, String src, String title, String cover) {
        addModule(new FileModule(type, src, title, cover));
        return this;
    }

    /**
     * 添加标题,只接收纯文本, 此方法封装了 {@link CardBuilder} addModule() 方法
     * @param content 纯文本信息
     * @param emoji 是否开启渲染 emoji 语法
     * <blockquote><pre>
     * {@code
     *      new NCardBuilder()
     *          .addHeaderModule(":smile:", true)
     * }
     */
    public NCardBuilder addHeaderModule(String content, boolean emoji) {
        addModule(new HeaderModule(new PlainTextElement(content, emoji)));
        return this;
    }
    public NCardBuilder addHeaderModule(String content) {
        addModule(new HeaderModule(content));
        return this;
    }

    /**
     * {@link ImageGroupModule} 的构造方法中的参数虽然为 List, 传入 {@link snw.jkook.message.component.card.element.ImageElement} <br>
     * 但它并不能安全使用,因此我们封装了此构造方法第一个参数, 使得他是安全的 <br>
     * 添加一个用于存储图像的组模块, 此方法封装了 {@link CardBuilder} addModule() 方法
     * @param imageBuilder 封装了 {@link ImageGroupModule} 的构造方法, 详情见 {@link ImageBuilder}
     * <blockquote><pre>
     * {@code
     *      new NCardBuilder()
     *          .addImageGroupModule(
     *              new imageBuilder()
     *                  .add("src", "alt", false)
     *                  .add("src", "alt", Size.LG, false)
     *          );
     * }
     */
    public NCardBuilder addImageGroupModule(@NotNull ImageBuilder imageBuilder) {
        addModule(new ImageGroupModule(imageBuilder.build()));
        return this;
    }

    /**
     * 添加一个存储邀请链接/邀请码的模块
     * @param code 链接/邀请码, 详情见 {@link InviteModule}
     * <blockquote><pre>
     * {@code
     *      new NCardBuilder()
     *          .addInviteModule("code");
     * }
     */
    public NCardBuilder addInviteModule(String code) {
        addModule(new InviteModule(code));
        return this;
    }

    /**
     * {@link SectionModule} 的构造方法中的第一个参数虽然是 {@link CardScopeElement} <br>
     * 但他仍然不能安全使用,因此我们封装了此构造方法第一个参数并做出重载 <br>
     * 我们分为了三个模块,它们分别是 {@link PlainTextElement}, {@link  MarkdownElement}, {@link Paragraph} <br>
     * 为什么原生的 SectionModule 构造不安全? <br>
     * (因为在构造方法中的第一个参数 {@link CardScopeElement},他的实现有许多,但是在构造方法中 <br>
     * 需要验证传入的参数必须为 {@link PlainTextElement}, {@link  MarkdownElement}, {@link Paragraph} <br>
     * 否则将会抛出异常,因此我做了此封装,可以完完全全的安全使用) <br>
     */
    public NCardBuilder addSectionModule(PlainTextElement plainTextElement, Accessory accessory, Accessory.Mode mode) {
        addModule(new SectionModule(plainTextElement, accessory, mode));
        return this;
    }
    public NCardBuilder addSectionModule(MarkdownElement markdownElement, Accessory accessory, Accessory.Mode mode) {
        addModule(new SectionModule(markdownElement, accessory, mode));
        return this;
    }
    public NCardBuilder addSectionModule(ParagraphBuilder paragraphBuilder, Accessory accessory, Accessory.Mode mode) {
        addModule(new SectionModule(paragraphBuilder.build(), accessory, mode));
        return this;
    }

    public NCardBuilder addParagraphModule(ParagraphBuilder paragraphBuilder){
        addModule(new SectionModule(paragraphBuilder.build(), null, null));
        return this;
    }

    public NCardBuilder addMarkdownModule(String content) {
        addModule(new SectionModule(new MarkdownElement(content), null, null));
        return this;
    }

    public NCardBuilder addTextModule(String content, boolean emoji) {
        addModule(new SectionModule(new PlainTextElement(content, emoji), null, null));
        return this;
    }
    public NCardBuilder addTextModule(String content) {
        addModule(new SectionModule(new PlainTextElement(content), null, null));
        return this;
    }
}
