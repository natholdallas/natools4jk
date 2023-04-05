# 欢迎来到 Natools4JK 整合工具箱 📄

> 使用Apache2.0开源协议
> 请始终保持最新版!**natools4jk**工具包始终只跟着最新版**JkookAPI**前进!

## 他作用于什么? 🤡

它是依赖于[JkookAPI](https://github.com/SNWCreations/JKook)的一个工具箱

## 目录 🍚

- **NCardBuilder**: 封装`JKookAPI`中的`CardBuilder`模块,(快速/整洁)构建你的卡片
- **Transfer**: 一个简易轻量的传参挂载器

### NCardBuilder - from CardBuilder 😘

为什么封装此模块?

在传统的 **JKookAPI** 中, **CardBuilder** 虽然没有任何问题,但是它会增加整体的代码量
参考一下传统的 **CardBuilder** 中,如果我们单纯的使用卡片构建一个纯文本,需要的代码就会提升很多

使用传统方式构建:

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

使用 NCardBuilder:

```java
new NCardBuilder(Theme.DANGER, SIZE.LG).addTextModule("Hello World")
```

没错,仅仅只需要一行代码,而且由于`NCardBuilder`继承`CardBuilder`
因此我们可以在任何`JKookAPI`框架中需要`Cloneable`类型参数的方法或构造器中使用它

并且在原生的`CardBuilder`中,部分方法的参数不是非常安全,要安全的使用它,需要增加观察源码的时间
因此`NCardBuilder`类中提供了相当详细的 JavaDoc 以便大家参考

#### 特色|☁️ 论构建一些可能会抛出异常的模块

参考 ContextModule 的构造方法

```java
public ContextModule(List<BaseElement> modules) {
    Validate.isTrue(
        modules.stream().allMatch(
            IT -> ((IT instanceof PlainTextElement) || (IT instanceof MarkdownElement) || (IT instanceof ImageElement))
        ),
        "Context module only accepts plain-text, kmarkdown or image modules."
    );
    this.modules = Collections.unmodifiableList(modules);
}
```

在部分的模块中的构造方法中,它们可以传入一些基于参数类型实现的子类
但是,它们并不是安全的,因此我们需要提供了非常详细的 JavaDoc 标注了它们
以防开发者在开发卡片的过程中因在阅读源码的过程中不仔细发生的错误

```java
new NCardBuilder()
    .addContextModule(
        PlainTextElement...
        MarkdownElement...
        ImageElement...
    );
```

#### 没看出来有啥用?

> NCardBuilder 的封装意义即为让开发者不用预览过多的源码
> 他有着非常丰富的 JavaDoc, 解决你在构建卡片过程中遇到的任何问题
> 并且在不影响代码结构的情况下,节省你的代码量

#### 注意事项

> MarkdownModule/PlainTextModule/ParagraphModule 这三个模块
> 本质上都是通过封装 SectionModule 派生出的新模块,他并不存在于 CardBuilder 中的标准模块

### Transfer - Easy get 🥰

此传参器可以在`JKook`中的多个场景使用,但是,在此之前,我需要说明一下何为`Transfer`
它是一个由`java.util.Map`中抽象出的一个简易传参器,类似一个`Service`

- 定义服务的`id`,需要往里面塞入什么内容
- 通过`id`提取这个服务中的对象
- ~~通过服务的`id`关闭一个服务~~ 现在的Transfer是自动管理啦!

它应用在什么`JKook`中的什么场景,以及如何编写它?

1. 什么场景

举个栗子,在我们注册了一个`Listener`(监听器)的时候,监听一个`UserClickButton`事件
但想让机器人发送一个按钮,它需要由卡片消息发出
卡片消息的发出可能由`CommandExecutor`或`Listener`去实现
但在两个实现之间,我们没法拿到它们的实例化

因此,传参是一个问题,因此我们制作了一个非常简单的传参器,它具体如何编写?
很简单,直接在想传参的位置,挂载一个服务即可

现在我们来模拟这个场景

```java
public class A {

    public void method() {
        Transfer.mount("114514", "Hello World");
    }

}

class B {

    public void method() {
        String from114514 = Transfer.extract("114514", String.class);
        JKook.getLogger().info(from114514);
    }

}
```

### 位于包中的 newcard 是什么? 🫠

他是我们在 1.2 版本中可能会推出的新型 CardBuilder
他的目的是好的,他允许开发者可以使用 xml(一个可扩展文本标记语言) 构建一个卡片模块
(卡片模块代表一次发出去的消息可以有多张卡片,具体你需要参考 JKook 文档)
让他从代码中完全分离出来,帮助开发者大大的节省开发时间

如果你有兴趣,可以直接在最新的 commit 中根包下查看(如果你会 xml)

card.xsd
test.xml
这两个文件
