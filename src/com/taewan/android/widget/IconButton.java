package com.taewan.android.widget;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("Recycle")
public class IconButton extends RelativeLayout {

	private final int DRAWABLE_LEFT = 0;
	private final int DRAWABLE_TOP = 1;
	private final int DRAWABLE_RIGHT = 2;
	private final int DRAWABLE_BOTTOM = 3;
	private static final int SANS = 1;
	private static final int SERIF = 2;
	private static final int MONOSPACE = 3;

	private TextView mTextView;
	private ProgressBar mProgressBar;

	private Spanned mTextDefault;
	private Spanned mTextDisabled;
	private Spanned mTextPressed;
	private Spanned mTextSelected;
	private Spanned mTextSelectedPressed;

	public IconButton(Context context) {
		super(context);
	}

	public IconButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setTextAttrs(attrs);
		setProgressBarAttrs(attrs);
	}

	public IconButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setTextAttrs(attrs);
		setProgressBarAttrs(attrs);
	}

	private void setProgressBarAttrs(AttributeSet attrs) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
				R.styleable.IconButton);
		if (typedArray.getBoolean(R.styleable.IconButton_progress_used, false)) {
			int defStyle = typedArray.getResourceId(
					R.styleable.IconButton_progress_style, 0);
			if (defStyle != 0) {
				mProgressBar = new ProgressBar(getContext(), null, defStyle);
			} else {
				mProgressBar = new ProgressBar(getContext());
			}

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);

			lp.addRule(RelativeLayout.CENTER_IN_PARENT);
			mProgressBar.setLayoutParams(lp);
		}
	}

	private void setTextAttrs(AttributeSet attrs) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
				R.styleable.IconButton);

		int textColorHighlight = 0;
		ColorStateList textColor = null;
		int textSize = -1;
		int typefaceIndex = -1;
		int styleIndex = -1;
		int drawablePadding = 0;
		Drawable[] drawables;
		drawables = new Drawable[4];

		if (typedArray != null) {
			int n = typedArray.getIndexCount();
			for (int i = 0; i < n; i++) {
				int attr = typedArray.getIndex(i);

				switch (attr) {
				case R.styleable.IconButton_android_textColorHighlight:
					textColorHighlight = typedArray.getColor(attr,
							textColorHighlight);
					break;

				case R.styleable.IconButton_android_textColor:
					textColor = typedArray.getColorStateList(attr);
					break;

				case R.styleable.IconButton_android_textSize:
					textSize = typedArray.getDimensionPixelSize(attr, textSize);
					break;

				case R.styleable.IconButton_android_typeface:
					typefaceIndex = typedArray.getInt(attr, typefaceIndex);
					break;

				case R.styleable.IconButton_android_textStyle:
					styleIndex = typedArray.getInt(attr, styleIndex);
					break;

				case R.styleable.IconButton_android_drawablePadding:
					drawablePadding = typedArray.getInt(attr, drawablePadding);
					break;
				case R.styleable.IconButton_android_drawableLeft:
					drawables[DRAWABLE_LEFT] = typedArray.getDrawable(attr);
					break;
				case R.styleable.IconButton_android_drawableTop:
					drawables[DRAWABLE_TOP] = typedArray.getDrawable(attr);
					break;
				case R.styleable.IconButton_android_drawableRight:
					drawables[DRAWABLE_RIGHT] = typedArray.getDrawable(attr);
					break;
				case R.styleable.IconButton_android_drawableBottom:
					drawables[DRAWABLE_BOTTOM] = typedArray.getDrawable(attr);
					break;
				}
			}
		}
		setTextDefault(typedArray
				.getString(R.styleable.IconButton_android_text));
		setTextDisabled(typedArray
				.getString(R.styleable.IconButton_textDisabled));
		setTextPressed(typedArray.getString(R.styleable.IconButton_textPressed));
		setTextSelected(typedArray
				.getString(R.styleable.IconButton_textSelected));
		setTextSelectedPressed(typedArray
				.getString(R.styleable.IconButton_textSelectedPressed));

		this.setClickable(true);
		mTextView = new TextView(getContext());
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		mTextView.setLayoutParams(lp);
		mTextView.setGravity(Gravity.CENTER);

		mTextView.setTextColor(textColor != null ? textColor : ColorStateList
				.valueOf(0xFF000000));
		if (textColorHighlight != 0) {
			mTextView.setHighlightColor(textColorHighlight);
		}
		if(textSize != -1) mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);// [È®ÀÎ]
		mTextView.setCompoundDrawablePadding(drawablePadding);
		mTextView.setCompoundDrawablesWithIntrinsicBounds(
				drawables[DRAWABLE_LEFT], drawables[DRAWABLE_TOP],
				drawables[DRAWABLE_RIGHT], drawables[DRAWABLE_BOTTOM]);

		setTypefaceByIndex(typefaceIndex, styleIndex);

		if (this.getBackground() == null)
			this.setBackgroundResource(android.R.drawable.btn_default);

		this.update();
		this.addView(mTextView);
	}

	private void setTypefaceByIndex(int typefaceIndex, int styleIndex) {
		Typeface tf = null;
		switch (typefaceIndex) {
		case SANS:
			tf = Typeface.SANS_SERIF;
			break;

		case SERIF:
			tf = Typeface.SERIF;
			break;

		case MONOSPACE:
			tf = Typeface.MONOSPACE;
			break;
		}
		if (mTextView != null)
			mTextView.setTypeface(tf, styleIndex);
	}

	public void setProgressed(boolean progressed) {
		if (mProgressBar != null) {
			if (progressed) {
				if (mTextView != null)
					mTextView.setVisibility(View.INVISIBLE);
				if (mProgressBar.getParent() == null)
					this.addView(mProgressBar);
				this.setEnabled(false);
			} else {
				if (mTextView != null)
					mTextView.setVisibility(View.VISIBLE);
				if (mProgressBar.getParent() != null)
					this.removeView(mProgressBar);
				this.setEnabled(true);
			}
		}
	}

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (mTextView != null)
			mTextView.setEnabled(enabled);
		this.update();
	}

	public void setPressed(boolean pressed) {
		super.setPressed(pressed);
		if (mTextView != null)
			mTextView.setPressed(pressed);
		this.update();
	}

	public void setSelected(boolean selected) {
		super.setSelected(selected);
		if (mTextView != null)
			mTextView.setSelected(selected);
		this.update();
	}

	private void update() {
		if (mTextView != null) {
			if (!this.isEnabled()) {
				if (mTextDisabled != null) {
					mTextView.setText(mTextDisabled);
				}
			} else if (this.isSelected() && this.isPressed()) {
				if (mTextSelectedPressed != null) {
					mTextView.setText(mTextSelectedPressed);
				}
			} else if (this.isPressed()) {
				if (mTextPressed != null) {
					mTextView.setText(mTextPressed);
				}
			} else if (this.isSelected()) {
				if (mTextSelected != null) {
					mTextView.setText(mTextSelected);
				}
			} else {
				if (mTextPressed != null) {
					mTextView.setText(mTextDefault);
				}
			}
		}
	}

	private Spanned patternColor(String text) {
		if (text == null)
			return null;
		Pattern p = Pattern.compile("color\\(\\s*(.*?)\\s*,\\s*(.*?)\\s*\\)");
		Matcher m = p.matcher(text);
		while (m.find()) {
			String a = "<FONT COLOR=\"" + m.group(1) + "\">" + m.group(2)
					+ "</FONT>";
			text = text.replace(m.group(0), a);
		}
		Log.d("taewan", text);
		return Html.fromHtml(text);
	}

	public String getTextDefault() {
		return mTextDefault != null ? mTextDefault.toString() : null;
	}

	public void setTextDefault(String mTextDefault) {
		this.mTextDefault = patternColor(mTextDefault);
	}

	public String getTextDisabled() {
		return mTextDisabled != null ? mTextDisabled.toString() : null;
	}

	public void setTextDisabled(String mTextDisabled) {
		this.mTextDisabled = patternColor(mTextDisabled);
	}

	public String getTextPressed() {
		return mTextPressed != null ? mTextPressed.toString() : null;
	}

	public void setTextPressed(String mTextPressed) {
		this.mTextPressed = patternColor(mTextPressed);
	}

	public String getTextSelected() {
		return mTextSelected != null ? mTextSelected.toString() : null;
	}

	public void setTextSelected(String mTextSelected) {
		this.mTextSelected = patternColor(mTextSelected);
	}

	public String getTextSelectedPressed() {
		return mTextSelectedPressed != null ? mTextSelectedPressed.toString()
				: null;
	}

	public void setTextSelectedPressed(String mTextSelectedPressed) {
		this.mTextSelectedPressed = patternColor(mTextSelectedPressed);
	}
}
//
// //http://stackoverflow.com/questions/8191529/get-theme-attributes-programmatically
// class TextAppearanceConsts
// {
// private static final String LOG_TAG = "TextAppearanceConsts";
//
// public static final int[] styleable_TextAppearance;
// public static final int styleable_TextAppearance_textColor;
// public static final int styleable_TextAppearance_textSize;
// public static final int styleable_TextAppearance_typeface;
// public static final int styleable_TextAppearance_fontFamily;
// public static final int styleable_TextAppearance_textStyle;
//
// static {
// // F*ing freaking code
// int ta[] = null, taTc = 0, taTs = 0, taTf = 0, taFf = 0, taTst = 0;
// try{
// Class<?> clazz = Class.forName("com.android.internal.R$styleable", false,
// TextAppearanceConsts.class.getClassLoader());
// ta = (int[])clazz.getField("TextAppearance").get(null);
// taTc = getField(clazz, "TextAppearance_textColor");
// taTs = getField(clazz, "TextAppearance_textSize");
// taTf = getField(clazz, "TextAppearance_typeface");
// taFf = getField(clazz, "TextAppearance_fontFamily");
// taTst = getField(clazz, "TextAppearance_textStyle");
// }catch(Exception e){
// Log.e(LOG_TAG, "Failed to load styleable_TextAppearance", e);
// }
// styleable_TextAppearance = ta;
// styleable_TextAppearance_textColor = taTc;
// styleable_TextAppearance_textSize = taTs;
// styleable_TextAppearance_typeface = taTf;
// styleable_TextAppearance_fontFamily = taFf;
// styleable_TextAppearance_textStyle = taTst;
// }
//
// private static int getField(Class<?> clazz, String fieldName)
// throws IllegalAccessException
// {
// try{
// return clazz.getField(fieldName).getInt(null);
// }catch(NoSuchFieldException nsfe){
// Log.e(LOG_TAG, nsfe.toString());
// return -1;
// }
// }
//
// }