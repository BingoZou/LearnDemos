
经常在写自定义View的时候，我们常常会忽略一些不太起眼的东西，比如下面构造函数
```
public ZTextView(Context context, AttributeSet attrs, int defStyleAttr)
```
我们可以看到第三个参数为<code>int defStyleAttr</code>，但是往往谁都不会去在意，因为好像在我们日常开发中能够用到这个地方的并不多，今天自己也是碰到了一个问题，陡然间想去了解下这个参数。

看下谷歌官方具体的解释

Parameters | 参数解释
---|---
defStyleAttr | int: An attribute in the current theme that contains a reference to a style resource that supplies defaults values for the TypedArray. Can be 0 to not look for defaults.
defStyleRes | int: A resource identifier of a style resource that supplies default values for the TypedArray, used only if defStyleAttr is 0 or can not be found in the theme. Can be 0 to not look for defaults.

[官方链接地址](https://developer.android.com/reference/android/content/res/Resources.Theme.html#obtainStyledAttributes(android.util.AttributeSet,%20int[],%20int,%20int))

根据上述表格中解释的内容，我们可以说似懂非懂吧。对于defStyleAttr我们可以简单的进行翻译：
```
在当前包含了一个引用到为TypedArray提供默认值的样式资源的theme中的一种属性。
可以为0，但是为0的时候就不会再去寻找默认的。（注：这里的默认也就是defStyleRes）
```
ps：翻译的有点渣，见谅！

上面的翻译中我们大致能够明白，凡是空的引用我们就直接给0，所以上面的0表示的是这个含义。defStyleAttr是定义在theme中的一个引用，这个引用指向一个style资源，而这个style资源包含了一些TypedArray的默认值。

我们先从最基本的开始，开始定义我们的自定义布局。我目前使用的AS版本是**2.1.3**，至于自定义布局，老生常谈的东西了，先是要新建一个attrs.xml资源文件，然后再定义一些我们自定义的布局，我的代码如下：
```
attrs.xml

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="ZTextViewStyle">
        <attr name="attr_1" format="string" />
        <attr name="attr_2" format="string" />
        <attr name="attr_3" format="string" />
        <attr name="attr_4" format="string" />
    </declare-styleable>

    <attr name="ZTV_def_style" format="reference"/>

</resources>
```
很简单，然后定义我们的自定义布局，代码如下：
```
ZTextView.java

package cn.zhoudl.attrdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by dave on 2016/8/24 0024 17:28
 */
public class ZTextView extends TextView {

    public ZTextView(Context context) {
        super(context);
    }

    public ZTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.ZTV_def_style);
    }

    public ZTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parse(context, attrs, defStyleAttr);
    }

    private void parse(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZTextViewStyle, defStyleAttr, 0);
        String one = typedArray.getString(R.styleable.ZTextViewStyle_attr_1);
        String two = typedArray.getString(R.styleable.ZTextViewStyle_attr_2);
        String three = typedArray.getString(R.styleable.ZTextViewStyle_attr_3);
        String four = typedArray.getString(R.styleable.ZTextViewStyle_attr_4);

        log("one = " + one);
        log("two = " + two);
        log("three = " + three);
        log("four = " + four);

        typedArray.recycle();
    }

    private void log(String msg) {
        Log.i("ZDL", msg);
    }

}

```
也比较简单，是获取属性值并打印出来。

然后是我们的布局：
```
activity_main.xml

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:zt="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="cn.zhoudl.attrdemo.MainActivity">

    <cn.zhoudl.attrdemo.ZTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:text="Hello World!"
        zt:attr_1="one" />

</RelativeLayout>

```
运行结果如下：
```
08-24 20:08:52.739 27780-27780/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:08:52.739 27780-27780/cn.zhoudl.attrdemo I/ZDL: two = null
08-24 20:08:52.739 27780-27780/cn.zhoudl.attrdemo I/ZDL: three = null
08-24 20:08:52.739 27780-27780/cn.zhoudl.attrdemo I/ZDL: four = null
```
可以看到只有我们xml中写了的属性值被拿到了。


修改我们的布局文件：
```
<cn.zhoudl.attrdemo.ZTextView
    style="@style/ZTV_style"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_centerInParent="true"
    android:text="Hello World!"
    zt:attr_1="one" />
```
同时新增一个ZTV_style。
```
<style name="ZTV_style">
    <item name="attr_1">style_one</item>
    <item name="attr_2">style_two</item>
</style>
```
再次运行我们的程序。
```
08-24 20:15:34.129 9301-9301/? I/ZDL: one = one
08-24 20:15:34.129 9301-9301/? I/ZDL: two = style_two
08-24 20:15:34.129 9301-9301/? I/ZDL: three = null
08-24 20:15:34.129 9301-9301/? I/ZDL: four = null
```
可以发现我们的attr_2属性也被拿到了。这里也说明了一点，我们xml中的属性优先级是大于style中的属性优先级的，这个对我们编写xml有时还是有点用处的。
好，修改我们的theme，如下：
```
<!-- Base application theme. -->
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>

    <item name="attr_1">theme_one</item>
    <item name="attr_2">theme_two</item>
    <item name="attr_3">theme_three</item>
    <item name="attr_4">theme_four</item>

    <!--<item name="ZTV_def_style">@style/ZTV_theme_def_style</item>-->
</style>
```
再次运行：
```
08-24 20:24:08.469 26327-26327/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:24:08.469 26327-26327/cn.zhoudl.attrdemo I/ZDL: two = style_two
08-24 20:24:08.469 26327-26327/cn.zhoudl.attrdemo I/ZDL: three = theme_three
08-24 20:24:08.469 26327-26327/cn.zhoudl.attrdemo I/ZDL: four = theme_four
```
属性也同样被拿到了，所以到这里，我们可以得到一个结论：
```
属性的优先级是：xml > style > theme
```

其实上面说了那么多，但是都是与defStyleAttr无关的。
是不是超级无语？哈哈，不过也不能说完全无关吧，至少这些都是基础。
从上面的attrs.xml的文件中我们可以看到一句：
```
<attr name="ZTV_def_style" format="reference"/>
```
下面就要与他产生关系了。
我们去styles.xml添加一个style，并在AppTheme中打开被注释掉的item.
```
<style name="ZTV_theme_def_style">
    <item name="attr_1">def_style_one</item>
    <item name="attr_2">def_style_two</item>
    <item name="attr_3">def_style_three</item>
</style>
```
```
<!-- Base application theme. -->
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>

    <item name="attr_1">theme_one</item>
    <item name="attr_2">theme_two</item>
    <item name="attr_3">theme_three</item>
    <item name="attr_4">theme_four</item>

    <item name="ZTV_def_style">@style/ZTV_theme_def_style</item>
</style>
```
再次运行：
```
08-24 20:31:15.659 8640-8640/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:31:15.659 8640-8640/cn.zhoudl.attrdemo I/ZDL: two = style_two
08-24 20:31:15.659 8640-8640/cn.zhoudl.attrdemo I/ZDL: three = def_style_three
08-24 20:31:15.659 8640-8640/cn.zhoudl.attrdemo I/ZDL: four = theme_four
```
可以看到第三个属性的值变了，这个就是我们定义的defStyleAttr。这样我们的结论可以进一步延伸：
```
xml > style > defStyleAttr > theme
```
好，之前我们去属性都是用的这个
```
TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZTextViewStyle, defStyleAttr, 0);
```
我们并没有设置defStyleRes，下面我们新增一个defStyleRes的style文件
```
<style name="ZTV_default_style">
    <item name="attr_1">default_style_one</item>
    <item name="attr_2">default_style_two</item>
    <item name="attr_3">default_style_three</item>
    <item name="attr_4">default_style_four</item>
</style>
```
更改取属性的方法：
```
TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZTextViewStyle, defStyleAttr, R.style.ZTV_default_style);
```
再次运行：
```
08-24 20:37:56.459 22084-22084/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:37:56.459 22084-22084/cn.zhoudl.attrdemo I/ZDL: two = style_two
08-24 20:37:56.459 22084-22084/cn.zhoudl.attrdemo I/ZDL: three = def_style_three
08-24 20:37:56.459 22084-22084/cn.zhoudl.attrdemo I/ZDL: four = theme_four
```
结果没有变化。
现在我们删除theme中的第四个属性：
```
<!-- Base application theme. -->
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>

    <item name="attr_1">theme_one</item>
    <item name="attr_2">theme_two</item>
    <item name="attr_3">theme_three</item>

    <item name="ZTV_def_style">@style/ZTV_theme_def_style</item>
</style>
```
再次运行：
```
08-24 20:40:13.599 26689-26689/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:40:13.599 26689-26689/cn.zhoudl.attrdemo I/ZDL: two = style_two
08-24 20:40:13.599 26689-26689/cn.zhoudl.attrdemo I/ZDL: three = def_style_three
08-24 20:40:13.599 26689-26689/cn.zhoudl.attrdemo I/ZDL: four = null

```
发现根本没有去我们默认的defStyleRes中定义的属性，为什么呢？

请返回前面仔细看看Google官方的文档，那里其实写了，除非defStyleAttr为0（可以理解为theme中没有相关属性），否则程序根本不会去从我们的defStyleRes找属性值。所以这里你有两种方法，第一种就是在获取属性的时候，在defStyleAttr属性那里给0，第二种方法就是注释掉theme下面的引用。

我选择的是第二种，注释后在运行：
```
08-24 20:46:44.919 7693-7693/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:46:44.919 7693-7693/cn.zhoudl.attrdemo I/ZDL: two = style_two
08-24 20:46:44.919 7693-7693/cn.zhoudl.attrdemo I/ZDL: three = default_style_three
08-24 20:46:44.919 7693-7693/cn.zhoudl.attrdemo I/ZDL: four = default_style_four
```
现在就成功拿到了defStyleRes中的属性。但是我们也发现了这里程序不会再去theme中寻找了。
如果我们去掉ZTV_default_style中的第三个属性，在运行：
```
08-24 20:50:10.929 14732-14732/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:50:10.929 14732-14732/cn.zhoudl.attrdemo I/ZDL: two = style_two
08-24 20:50:10.929 14732-14732/cn.zhoudl.attrdemo I/ZDL: three = theme_three
08-24 20:50:10.929 14732-14732/cn.zhoudl.attrdemo I/ZDL: four = default_style_four
```
会发现three取了theme中的属性。这里说明了**defStyleRes的优先级是大于theme**的。


## 结论：
```
我们解析属性的优先级： xml > style > defStyleAttr > defStyleRes > theme
```

具体代码请参见：