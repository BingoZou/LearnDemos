
������д�Զ���View��ʱ�����ǳ��������һЩ��̫���۵Ķ������������湹�캯��
```
public ZTextView(Context context, AttributeSet attrs, int defStyleAttr)
```
���ǿ��Կ�������������Ϊ<code>int defStyleAttr</code>����������˭������ȥ���⣬��Ϊ�����������ճ��������ܹ��õ�����ط��Ĳ����࣬�����Լ�Ҳ��������һ�����⣬��Ȼ����ȥ�˽������������

���¹ȸ�ٷ�����Ľ���

Parameters | ��������
---|---
defStyleAttr | int: An attribute in the current theme that contains a reference to a style resource that supplies defaults values for the TypedArray. Can be 0 to not look for defaults.
defStyleRes | int: A resource identifier of a style resource that supplies default values for the TypedArray, used only if defStyleAttr is 0 or can not be found in the theme. Can be 0 to not look for defaults.

[�ٷ����ӵ�ַ](https://developer.android.com/reference/android/content/res/Resources.Theme.html#obtainStyledAttributes(android.util.AttributeSet,%20int[],%20int,%20int))

������������н��͵����ݣ����ǿ���˵�ƶ��Ƕ��ɡ�����defStyleAttr���ǿ��Լ򵥵Ľ��з��룺
```
�ڵ�ǰ������һ�����õ�ΪTypedArray�ṩĬ��ֵ����ʽ��Դ��theme�е�һ�����ԡ�
����Ϊ0������Ϊ0��ʱ��Ͳ�����ȥѰ��Ĭ�ϵġ���ע�������Ĭ��Ҳ����defStyleRes��
```
ps��������е��������£�

����ķ��������Ǵ����ܹ����ף����ǿյ��������Ǿ�ֱ�Ӹ�0�����������0��ʾ����������塣defStyleAttr�Ƕ�����theme�е�һ�����ã��������ָ��һ��style��Դ�������style��Դ������һЩTypedArray��Ĭ��ֵ��

�����ȴ�������Ŀ�ʼ����ʼ�������ǵ��Զ��岼�֡���Ŀǰʹ�õ�AS�汾��**2.1.3**�������Զ��岼�֣�������̸�Ķ����ˣ�����Ҫ�½�һ��attrs.xml��Դ�ļ���Ȼ���ٶ���һЩ�����Զ���Ĳ��֣��ҵĴ������£�
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
�ܼ򵥣�Ȼ�������ǵ��Զ��岼�֣��������£�
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
Ҳ�Ƚϼ򵥣��ǻ�ȡ����ֵ����ӡ������

Ȼ�������ǵĲ��֣�
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
���н�����£�
```
08-24 20:08:52.739 27780-27780/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:08:52.739 27780-27780/cn.zhoudl.attrdemo I/ZDL: two = null
08-24 20:08:52.739 27780-27780/cn.zhoudl.attrdemo I/ZDL: three = null
08-24 20:08:52.739 27780-27780/cn.zhoudl.attrdemo I/ZDL: four = null
```
���Կ���ֻ������xml��д�˵�����ֵ���õ��ˡ�


�޸����ǵĲ����ļ���
```
<cn.zhoudl.attrdemo.ZTextView
    style="@style/ZTV_style"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_centerInParent="true"
    android:text="Hello World!"
    zt:attr_1="one" />
```
ͬʱ����һ��ZTV_style��
```
<style name="ZTV_style">
    <item name="attr_1">style_one</item>
    <item name="attr_2">style_two</item>
</style>
```
�ٴ��������ǵĳ���
```
08-24 20:15:34.129 9301-9301/? I/ZDL: one = one
08-24 20:15:34.129 9301-9301/? I/ZDL: two = style_two
08-24 20:15:34.129 9301-9301/? I/ZDL: three = null
08-24 20:15:34.129 9301-9301/? I/ZDL: four = null
```
���Է������ǵ�attr_2����Ҳ���õ��ˡ�����Ҳ˵����һ�㣬����xml�е��������ȼ��Ǵ���style�е��������ȼ��ģ���������Ǳ�дxml��ʱ�����е��ô��ġ�
�ã��޸����ǵ�theme�����£�
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
�ٴ����У�
```
08-24 20:24:08.469 26327-26327/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:24:08.469 26327-26327/cn.zhoudl.attrdemo I/ZDL: two = style_two
08-24 20:24:08.469 26327-26327/cn.zhoudl.attrdemo I/ZDL: three = theme_three
08-24 20:24:08.469 26327-26327/cn.zhoudl.attrdemo I/ZDL: four = theme_four
```
����Ҳͬ�����õ��ˣ����Ե�������ǿ��Եõ�һ�����ۣ�
```
���Ե����ȼ��ǣ�xml > style > theme
```

��ʵ����˵����ô�࣬���Ƕ�����defStyleAttr�޹صġ�
�ǲ��ǳ����������������Ҳ����˵��ȫ�޹ذɣ�������Щ���ǻ�����
�������attrs.xml���ļ������ǿ��Կ���һ�䣺
```
<attr name="ZTV_def_style" format="reference"/>
```
�����Ҫ����������ϵ�ˡ�
����ȥstyles.xml���һ��style������AppTheme�д򿪱�ע�͵���item.
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
�ٴ����У�
```
08-24 20:31:15.659 8640-8640/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:31:15.659 8640-8640/cn.zhoudl.attrdemo I/ZDL: two = style_two
08-24 20:31:15.659 8640-8640/cn.zhoudl.attrdemo I/ZDL: three = def_style_three
08-24 20:31:15.659 8640-8640/cn.zhoudl.attrdemo I/ZDL: four = theme_four
```
���Կ������������Ե�ֵ���ˣ�����������Ƕ����defStyleAttr���������ǵĽ��ۿ��Խ�һ�����죺
```
xml > style > defStyleAttr > theme
```
�ã�֮ǰ����ȥ���Զ����õ����
```
TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZTextViewStyle, defStyleAttr, 0);
```
���ǲ�û������defStyleRes��������������һ��defStyleRes��style�ļ�
```
<style name="ZTV_default_style">
    <item name="attr_1">default_style_one</item>
    <item name="attr_2">default_style_two</item>
    <item name="attr_3">default_style_three</item>
    <item name="attr_4">default_style_four</item>
</style>
```
����ȡ���Եķ�����
```
TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZTextViewStyle, defStyleAttr, R.style.ZTV_default_style);
```
�ٴ����У�
```
08-24 20:37:56.459 22084-22084/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:37:56.459 22084-22084/cn.zhoudl.attrdemo I/ZDL: two = style_two
08-24 20:37:56.459 22084-22084/cn.zhoudl.attrdemo I/ZDL: three = def_style_three
08-24 20:37:56.459 22084-22084/cn.zhoudl.attrdemo I/ZDL: four = theme_four
```
���û�б仯��
��������ɾ��theme�еĵ��ĸ����ԣ�
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
�ٴ����У�
```
08-24 20:40:13.599 26689-26689/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:40:13.599 26689-26689/cn.zhoudl.attrdemo I/ZDL: two = style_two
08-24 20:40:13.599 26689-26689/cn.zhoudl.attrdemo I/ZDL: three = def_style_three
08-24 20:40:13.599 26689-26689/cn.zhoudl.attrdemo I/ZDL: four = null

```
���ָ���û��ȥ����Ĭ�ϵ�defStyleRes�ж�������ԣ�Ϊʲô�أ�

�뷵��ǰ����ϸ����Google�ٷ����ĵ���������ʵд�ˣ�����defStyleAttrΪ0���������Ϊtheme��û��������ԣ�����������������ȥ�����ǵ�defStyleRes������ֵ�����������������ַ�������һ�־����ڻ�ȡ���Ե�ʱ����defStyleAttr���������0���ڶ��ַ�������ע�͵�theme��������á�

��ѡ����ǵڶ��֣�ע�ͺ������У�
```
08-24 20:46:44.919 7693-7693/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:46:44.919 7693-7693/cn.zhoudl.attrdemo I/ZDL: two = style_two
08-24 20:46:44.919 7693-7693/cn.zhoudl.attrdemo I/ZDL: three = default_style_three
08-24 20:46:44.919 7693-7693/cn.zhoudl.attrdemo I/ZDL: four = default_style_four
```
���ھͳɹ��õ���defStyleRes�е����ԡ���������Ҳ������������򲻻���ȥtheme��Ѱ���ˡ�
�������ȥ��ZTV_default_style�еĵ��������ԣ������У�
```
08-24 20:50:10.929 14732-14732/cn.zhoudl.attrdemo I/ZDL: one = one
08-24 20:50:10.929 14732-14732/cn.zhoudl.attrdemo I/ZDL: two = style_two
08-24 20:50:10.929 14732-14732/cn.zhoudl.attrdemo I/ZDL: three = theme_three
08-24 20:50:10.929 14732-14732/cn.zhoudl.attrdemo I/ZDL: four = default_style_four
```
�ᷢ��threeȡ��theme�е����ԡ�����˵����**defStyleRes�����ȼ��Ǵ���theme**�ġ�


## ���ۣ�
```
���ǽ������Ե����ȼ��� xml > style > defStyleAttr > defStyleRes > theme
```

���������μ���