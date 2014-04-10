IconButton
==========

IconButton 
IconButton


drawableLeft, drawableTop, drawableRight, drawableBottom
aways center drawableImage

====================
android:drawablePadding
android:drawableLeft
android:drawableTop
android:drawableRight
android:drawableBottom

android:typeface
android:textColor
android:textColorHighlight
android:textSize
android:textStyle
android:text

textDisabled
textPressed
textSelected
textSelectedPressed

progress_used
progress_style


====================

 Add the `IconButton`
    ```xml

	<com.taewan.android.widget.IconButton
		xmlns:app="http://schemas.android.com/apk/res-auto"
		android:id="@+id/btnicon_0"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:drawableLeft="@drawable/ic_launcher"
		android:text="0:hello"
		app:textPressed="0:pressed"
		app:textSelected="0:selected color(#ff0000,hello)"
		app:textSelectedPressed="0:selected pressed"
		app:progress_used=“true"
		app:progress_style=“@android:attr/progressBarStyleSmall”/>
    ```


====================


mIconbtn.setProgressed(true);
mIconbtn.setProgressed(false);



====================

easy change color

"color(#ff0000,hello)"

