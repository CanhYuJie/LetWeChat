package com.yujie.letwechat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yujie.letwechat.R;

/**
 * Created by yujie on 16-10-30.
 */

public class CustomTitleBar extends RelativeLayout {
    public static final String TAG = CustomTitleBar.class.getSimpleName();
    /**
     * the root layout
     */
    private RelativeLayout titlebar_root;
    /**
     * buttons and title
     */
    private ImageView left_btn;
    private TextView title;
    private TextView right_btn;
    /**
     * set the title's text color,text size and title content
     */
    private int title_text_color;


    private String title_text_text;
    private int title_text_size;
    /**
     * set left button's background image if the button is visible
     */
    private int left_btn_background;
    private boolean show_left_btn = true;
    /**
     * set right button's text size,color,content
     * and background image if the button is visible
     */
    private int right_btn_background;
    private int right_btn_text_color;
    private int right_btn_text_size;
    private String right_btn_text;
    private boolean show_right_btn = false;
    /**
     * set the left button and right button's click listener if the buttons are visible
     */
    private TitleBarClickListener listener;

    public interface TitleBarClickListener{
        void onLeftClick();
        void onRightClick();
    }


    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.costum_titlebar_layout, this, true);
        titlebar_root = (RelativeLayout) findViewById(R.id.titlebar_root);
        left_btn = (ImageView) findViewById(R.id.left_btn);
        title = (TextView) findViewById(R.id.center_title);
        right_btn = (TextView) findViewById(R.id.right_btn);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);
        /**
         * title's attribute
         * */
        title_text_color = typedArray.getColor(R.styleable.CustomTitleBar_title_text_color, Color.WHITE);
        title_text_size = (int) typedArray.getDimension(R.styleable.CustomTitleBar_title_text_size,20L);
        title_text_text = typedArray.getString(R.styleable.CustomTitleBar_title_text);
        /**
         * left button's attribute
         */
        left_btn_background = typedArray.getResourceId(R.styleable.CustomTitleBar_left_btn_background,R.mipmap.back_nomal);
        show_left_btn = typedArray.getBoolean(R.styleable.CustomTitleBar_show_left_btn,true);
        /**
         * right button's attribute
         */
        right_btn_background = typedArray.getResourceId(R.styleable.CustomTitleBar_right_btn_img,0);
        right_btn_text = typedArray.getString(R.styleable.CustomTitleBar_right_btn_text);
        right_btn_text_color = typedArray.getColor(R.styleable.CustomTitleBar_right_btn_text_color, Color.WHITE);
        right_btn_text_size = (int) typedArray.getDimension(R.styleable.CustomTitleBar_right_btn_text_size,22L);
        show_right_btn = typedArray.getBoolean(R.styleable.CustomTitleBar_show_right_btn,false);

        setTitle_text_color(title_text_color);
        setTitle_text_size(title_text_size);
        setTitle_text_text(title_text_text);
        setShow_left_btn(show_left_btn);
        setShow_right_btn(show_right_btn);
        if (show_right_btn){
            if (!TextUtils.isEmpty(right_btn_text)){
                setRight_btn_text(right_btn_text);
                setRight_btn_text_color(right_btn_text_color);
                setRight_btn_text_size(right_btn_text_size);
            }else {
                setRight_btn_background(right_btn_background);
            }
            right_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onRightClick();
                    }
                }
            });
        }

        if (show_left_btn){
            setLeft_btn_background(left_btn_background);
            left_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onLeftClick();
                    }
                }
            });
        }



    }

    /**
     * get left button
     * @return
     */
    public ImageView getLeft_btn() {
        return left_btn;
    }

    /**
     * get title
     * @return
     */
    public TextView getTitle() {
        return title;
    }

    /**
     * get right button
     * @return
     */
    public TextView getRight_btn() {
        return right_btn;
    }

    /**
     * set title's text color
     * @param title_text_color
     */
    public void setTitle_text_color(int title_text_color) {
        this.title.setTextColor(title_text_color);
    }

    /**
     * set title's content
     * @param title_text_text
     */
    public void setTitle_text_text(String title_text_text) {
        this.title.setText(title_text_text);
    }

    /**
     * set title's text size
     * @param title_text_size
     */
    public void setTitle_text_size(int title_text_size) {
        this.title.setTextSize(title_text_size);
    }

    /**
     * set left button's background image
     * @param left_btn_background
     */
    public void setLeft_btn_background(int left_btn_background) {
        this.left_btn.setBackgroundResource(left_btn_background);
    }

    /**
     * show left btn or not
     * @param show_left_btn
     */
    public void setShow_left_btn(boolean show_left_btn) {
        this.show_left_btn = show_left_btn;
        if (show_left_btn){
            left_btn.setVisibility(View.VISIBLE);
        }else {
            left_btn.setVisibility(View.GONE);
        }
    }

    /**
     * set right button's background image
     * @param right_btn_background
     */
    public void setRight_btn_background(int right_btn_background) {
        this.right_btn.setBackgroundResource(right_btn_background);
    }

    /**
     * set right button's text color
     * @param right_btn_text_color
     */
    public void setRight_btn_text_color(int right_btn_text_color) {
        this.right_btn.setTextColor(right_btn_text_color);
    }

    /**
     * set right button's text size
     * @param right_btn_text_size
     */
    public void setRight_btn_text_size(int right_btn_text_size) {
        this.right_btn.setTextSize(right_btn_text_size);
    }

    /**
     * set right button's text content
     * @param right_btn_text
     */
    public void setRight_btn_text(String right_btn_text) {
        this.right_btn.setText(right_btn_text);
    }

    /**
     * show right button or not
     * @param show_right_btn
     */
    public void setShow_right_btn(boolean show_right_btn) {
        this.show_right_btn = show_right_btn;
        if (show_right_btn){
            right_btn.setVisibility(View.VISIBLE);
        }else {
            right_btn.setVisibility(View.GONE);
        }
    }

    /**
     * set onclick listener
     * @param listener
     */
    public void setListener(TitleBarClickListener listener) {
        this.listener = listener;
    }
}
