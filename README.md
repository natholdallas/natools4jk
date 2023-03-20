# 欢迎来到 Natools4JK 整合工具箱 📄 (使用Apache2.0开源协议)

## 他作用于什么? 🤡
它是依赖于[JkookAPI](https://github.com/SNWCreations/JKook)的一个工具箱

## 目录 🤣
1. `NCardBuilder`   封装了`JKookAPI`中的`CardBuilder`模块
2. `KMarkdown4J`    帮助您在编写`Markdown`文本时候更加的方便
3. `Transfer`       一个简易轻量的传参挂载器

#### NCardBuilder - from CardBuilder 😘
为什么封装此模块?

在传统的`JKookAPI`中,`CardBuilder`虽然没有任何问题,但是它会增加整体的代码量
参考一下传统的`CardBuilder`中,如果我们单纯的使用卡片构建一个纯文本,需要的代码
```java
new CardBuilder()
        .setTheme(Theme.DANGER)
        .setSize(Size.LG)
        .addModule(
            new SectionModule(
                new PlainTextElement("Hello World"),
                null,
                null
            )
        );
```
在封装后的`NCardBuilder`中
```java
new NCardBuilder(Theme.DANGER, SIZE.LG).addTextModule("Hello World")
```

没错,仅仅只需要一行代码,而且由于`NCardBuilder`继承`CardBuilder`
因此我们可以在任何`JKookAPI`框架中需要`Cloneable`类型参数的方法或构造器中使用它

并且在原生的`CardBuilder`中,部分方法的参数不是非常安全,要安全的使用它,需要增加观察源码的时间
因此`NCardBuilder`对部分参数做了封装,让它可以更加安全的使用,如在`ContextModule`中
```java
public ContextModule(List<BaseElement> modules) {
    Validate.isTrue(modules.stream().allMatch(IT -> ((IT instanceof PlainTextElement) || (IT instanceof MarkdownElement) || (IT instanceof ImageElement))), "Context module only accepts plain-text, kmarkdown or image modules.");
    this.modules = Collections.unmodifiableList(modules);
}
```
可以看到在`List<BaseElement> modules`中
`BaseElement`的实现必须为`PlainTextElement`,`MarkdownElement`和`ImageElement`
因此我们为此参数封装为`BaseBuilder`,来看看`BaseBuilder`中是如何安全构建此模块
```java
new NCardBuilder()
    .addContextModule(
        new BaseBuilder()
            .addPlainText("Hello World")
            .addMarkdown(Kmarkdown4J.bold("Hello World"))
            .addImage("./image.png", "image", true)
    )
```
此类只允许三种方式添加到`List<BaseElement> list`中,确保它不会出错

**没看出来有啥用?**
> `NCardBuilder`的封装意义即为让开发者不用预览过多的源码,只需要跟着参数傻瓜式操作即可
> 并且在不影响代码结构的情况下,节约你的代码量

#### Kmarkdown4J - form KooK Markdown 😍
在构建`MarkdownElement`和`MarkdownComponent`的过程中,我们需要使用`KMarkdown`语法
但是当语法过多的时候,我们可以使用`Kmarkdown4J`中的工具来构建语法

> `Kmarkdown`语法仅隶属于`Kook`平台中
> 这是`Kook`对`Markdown`语法的一个改进语法,因此会与原生语法稍有不同

`Kmarkdown4J`中有许多的构建,它们的返回值都是`String`,因此我们可以这么编写
```java
import static nathol.jkook.tools.natools.utils.Kmarkdown4J.*;
String log = "hello world";
new MarkdownElement(String.format(
    "%s%n%s%n%s%n%s",
    bold(log)
    italic(log)
    boldItalics(log)
    hyperLink("github", "https://natholdallas.github.io/")
));

// MarkdownComponent 与 MarkdownElement 的编写方式都可以相同
```

#### Transfer - easy get you wanna parameters 🥰
此传参器可以在`JKook`中的多个场景使用,但是,在此之前,我需要说明一下何为`Transfer`
它是一个由`java.util.Map`中抽象出的一个简易传参器,类似`Linux`的`screen`
1. 挂载服务
定义服务的`id`,需要往里面塞入什么内容
2. 通过服务提取内容
通过`id`提取这个服务中的对象
3. 关闭服务
通过服务的`id`关闭一个服务

它应用在什么`JKook`中的什么场景,以及如何编写它?
1. 什么场景

举个栗子,在我们注册了一个`Listener`(监听器)的时候,监听一个`UserClickButton`事件
但想让机器人发送一个按钮,它需要由卡片消息发出,卡片消息的发出可能由`CommandExecutor`或`Listener`去实现
但在两个实现之间,我们没法拿到它们的实例化,因此传参是一个问题,因此我们制作了一个传参器,它具体如何编写?
很简单,直接在想传参的位置,上传一个服务即可
```java
public class A{
    public void method(){
        Transfer.mount("114514", "Hello World");
    }
}

class B{
    public void method(){
        String from114514 = Transfer.extract("114514", String.class);
        JKook.getLogger().info(from114514);
        Transfer.close("114514");
    }
}
```
目前只有这么多了,正在考虑需不需要导入第三方的模块供开发者使用