package aspros.app.aslib;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aspros.app.aslib.view.richeditor.RichEditor;

/**
 * Created by aspros on 2017/5/4.
 */

public class RichEditActivity extends AppCompatActivity implements View.OnClickListener
{
    private RichEditor mEditor;

    private int[] colors=new int[]{R.color.color_3,R.color.color_6,R.color.red,R.color.orange,R.color.myellow,R.color.green,
            R.color.navy,R.color.blue1,R.color.purple,R.color.black,R.color.gray};

    private View operatorView;
    private View colorView;
    private View sizeView;
    private View dividerView;

    private List<View> colorViews=new ArrayList<>();
    private List<TextView> sizeViews=new ArrayList<>();

    private View fontColor;




    private Context context;
    private boolean bold=false;
    private int sizeIndex=1;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richedit);

        context=this;


        mEditor = (RichEditor) findViewById(R.id.editor);
//        mEditor.setEditorHeight(200);
        mEditor.setFontSize(3);
        mEditor.setEditorFontColor(Color.BLACK);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPlaceholder("Insert text here...");
        //mEditor.setInputEnabled(false);



        operatorView=findViewById(R.id.operator_view);
        dividerView=findViewById(R.id.divider_view);
        colorView=findViewById(R.id.color_view);
        fontColor=findViewById(R.id.font_color);
        fontColor.setBackgroundResource(colors[0]);

        colorViews.add(findViewById(R.id.color3));
        colorViews.add(findViewById(R.id.color6));
        colorViews.add(findViewById(R.id.red));
        colorViews.add(findViewById(R.id.orange));
        colorViews.add(findViewById(R.id.yellow));
        colorViews.add(findViewById(R.id.green));
        colorViews.add(findViewById(R.id.navy));
        colorViews.add(findViewById(R.id.blue));
        colorViews.add(findViewById(R.id.purple));
        colorViews.add(findViewById(R.id.black));
        colorViews.add(findViewById(R.id.gray));

        for(View view:colorViews)
        {
            view.setOnClickListener(this);
        }

        sizeView=findViewById(R.id.size_view);
        sizeViews.add((TextView) findViewById(R.id.size1));
        sizeViews.add((TextView) findViewById(R.id.size2));
        sizeViews.add((TextView) findViewById(R.id.size3));
        sizeViews.add((TextView) findViewById(R.id.size4));
        sizeViews.add((TextView) findViewById(R.id.size5));
        sizeViews.add((TextView) findViewById(R.id.size6));
        sizeViews.add((TextView) findViewById(R.id.size7));
        for(View view:sizeViews)
        {
            view.setOnClickListener(this);
        }
        sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.price_color));




        mEditor.setOnDecorationChangeListener((text, types,color,fontSize) ->
        {
            if(types.contains(RichEditor.Type.BOLD))
            {
                ((ImageView)findViewById(R.id.action_bold)).setImageResource(R.mipmap.blod1);
                bold=true;
            }
            else
            {
                ((ImageView)findViewById(R.id.action_bold)).setImageResource(R.mipmap.blod0);
                bold=false;

            }
            fontColor.setBackgroundColor(color);

            sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.color_6));
            sizeIndex=fontSize-1;
            sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.price_color));
        });

        findViewById(R.id.action_color).setOnClickListener(v -> colorView.setVisibility(View.VISIBLE));
        findViewById(R.id.close_color).setOnClickListener(v -> colorView.setVisibility(View.GONE));
        findViewById(R.id.action_size).setOnClickListener(v -> sizeView.setVisibility(View.VISIBLE));
        findViewById(R.id.close_size).setOnClickListener(v -> sizeView.setVisibility(View.GONE));


        new KeyboardChangeListener(this).setKeyBoardListener((isShow, keyboardHeight) ->
        {
            Log.d("onKeyboardChange", "isShow = [" + isShow + "], keyboardHeight = [" + keyboardHeight + "]");
            if(isShow)
            {
                operatorView.setVisibility(View.VISIBLE);
                dividerView.setVisibility(View.VISIBLE);

            }
            else
            {
                dividerView.setVisibility(View.GONE);
                operatorView.setVisibility(View.GONE);

            }
        });


        findViewById(R.id.action_bold).setOnClickListener(v ->
        {
            bold=!bold;
            mEditor.setBold();
            if(bold)
            {
                ((ImageView)findViewById(R.id.action_bold)).setImageResource(R.mipmap.blod1);

            }
            else
            {
                ((ImageView)findViewById(R.id.action_bold)).setImageResource(R.mipmap.blod0);

            }

        });

        findViewById(R.id.action_image).setOnClickListener(v -> mEditor.insertImage("https://img.alicdn.com/tfs/TB1ALrVQVXXXXaAXpXXXXXXXXXX-520-280.jpg_q90_.webp", "aspros"));


        findViewById(R.id.action_hide).setOnClickListener(v -> KeyboardUtils.hideSoftInput(this));
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.color3:
                fontColor.setBackgroundResource(colors[0]);
                mEditor.setTextColor(context.getResources().getColor(colors[0]));
                colorView.setVisibility(View.GONE);
                break;
            case R.id.color6:
                fontColor.setBackgroundResource(colors[1]);
                mEditor.setTextColor(context.getResources().getColor(colors[1]));
                colorView.setVisibility(View.GONE);
                break;
            case R.id.red:
                fontColor.setBackgroundResource(colors[2]);
                mEditor.setTextColor(context.getResources().getColor(colors[2]));
                colorView.setVisibility(View.GONE);
                break;
            case R.id.orange:
                fontColor.setBackgroundResource(colors[3]);
                mEditor.setTextColor(context.getResources().getColor(colors[3]));
                colorView.setVisibility(View.GONE);
                break;
            case R.id.yellow:
                fontColor.setBackgroundResource(colors[4]);
                mEditor.setTextColor(context.getResources().getColor(colors[4]));
                colorView.setVisibility(View.GONE);
                break;
            case R.id.green:
                fontColor.setBackgroundResource(colors[5]);
                mEditor.setTextColor(context.getResources().getColor(colors[5]));
                colorView.setVisibility(View.GONE);
                break;
            case R.id.navy:
                fontColor.setBackgroundResource(colors[6]);
                mEditor.setTextColor(context.getResources().getColor(colors[6]));
                colorView.setVisibility(View.GONE);
                break;
            case R.id.blue:
                fontColor.setBackgroundResource(colors[7]);
                mEditor.setTextColor(context.getResources().getColor(colors[7]));
                colorView.setVisibility(View.GONE);
                break;
            case R.id.purple:
                fontColor.setBackgroundResource(colors[8]);
                mEditor.setTextColor(context.getResources().getColor(colors[8]));
                colorView.setVisibility(View.GONE);
                break;
            case R.id.black:
                fontColor.setBackgroundResource(colors[9]);
                mEditor.setTextColor(context.getResources().getColor(colors[9]));
                colorView.setVisibility(View.GONE);
                break;
            case R.id.gray:
                fontColor.setBackgroundResource(colors[10]);
                mEditor.setTextColor(context.getResources().getColor(colors[10]));
                colorView.setVisibility(View.GONE);
                break;




            case R.id.size1:
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.color_6));
                sizeIndex=0;
                mEditor.setFontSize(1);
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.price_color));
                sizeView.setVisibility(View.GONE);
                break;
            case R.id.size2:
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.color_6));
                sizeIndex=1;
                mEditor.setFontSize(2);
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.price_color));
                sizeView.setVisibility(View.GONE);
                break;
            case R.id.size3:
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.color_6));
                sizeIndex=2;
                mEditor.setFontSize(3);
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.price_color));
                sizeView.setVisibility(View.GONE);


                break;
            case R.id.size4:
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.color_6));
                sizeIndex=3;
                mEditor.setFontSize(4);
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.price_color));
                sizeView.setVisibility(View.GONE);
                break;
            case R.id.size5:
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.color_6));
                sizeIndex=4;
                mEditor.setFontSize(5);
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.price_color));
                sizeView.setVisibility(View.GONE);
                break;
            case R.id.size6:
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.color_6));
                sizeIndex=5;
                mEditor.setFontSize(6);
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.price_color));
                sizeView.setVisibility(View.GONE);
                break;
            case R.id.size7:
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.color_6));
                sizeIndex=6;
                mEditor.setFontSize(7);
                sizeViews.get(sizeIndex).setTextColor(context.getResources().getColor(R.color.price_color));
                sizeView.setVisibility(View.GONE);
                break;
        }
    }
}
