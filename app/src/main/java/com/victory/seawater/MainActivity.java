package com.victory.seawater;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.victory.view.WaterWaveView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_hide;
    private Button btn_water;
    private Button btn_water_quality;
    private Button btn_smog;

    private ImageView iv_back;
    private ImageView iv_overlay;
    private ImageView iv_smog;

    private WaterWaveView bwv_water;
    private LinearLayout ll_bubble;

    private int level = 0;
    private int quality = 0;
    private int smogType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_hide = findViewById(R.id.btn_hide);
        btn_water = findViewById(R.id.btn_water);
        btn_water_quality = findViewById(R.id.btn_water_quality);
        btn_smog = findViewById(R.id.btn_smog);

        iv_back = findViewById(R.id.iv_back);
        iv_overlay = findViewById(R.id.iv_overlay);
        iv_smog=findViewById(R.id.iv_smog);
        bwv_water = findViewById(R.id.bwv_water);
        ll_bubble = findViewById(R.id.ll_bubble);

        btn_hide.setOnClickListener(this);
        btn_water.setOnClickListener(this);
        btn_water_quality.setOnClickListener(this);
        btn_smog.setOnClickListener(this);

        showWater();
        showSmog();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_hide:
                if (iv_back.getVisibility() == View.VISIBLE) {
                    iv_back.setVisibility(View.INVISIBLE);
                    iv_overlay.setVisibility(View.INVISIBLE);
                    iv_smog.setVisibility(View.INVISIBLE);
                    btn_hide.setText("显示锅炉");
                } else {
                    iv_back.setVisibility(View.VISIBLE);
                    iv_overlay.setVisibility(View.VISIBLE);
                    iv_smog.setVisibility(View.VISIBLE);
                    btn_hide.setText("隐藏锅炉");
                }
                break;
            case R.id.btn_water:
                if (level == 0) {
                    level = 1;
                } else if (level == 1) {
                    level = -1;
                } else {
                    level = 0;
                }
                showWater();
                break;
            case R.id.btn_water_quality:
                quality = quality == 0 ? 1 : 0;
                showWater();
                break;
            case R.id.btn_smog:
                smogType = smogType == 1 ? 2 : 1;
                showSmog();
                break;
            default:
                break;
        }
    }


    /**
     * 水位/水质
     */
    private void showWater() {
        if (quality == 1) {
            //绿色
            bwv_water.setBackColor(ContextCompat.getColor(this, R.color.color_4D44d428));
        } else {
            //蓝色
            bwv_water.setBackColor(ContextCompat.getColor(this, R.color.color_4D0780ed));
        }

        ViewGroup.LayoutParams waterBwvParams = bwv_water.getLayoutParams();
        ViewGroup.LayoutParams bubbleLlParams = ll_bubble.getLayoutParams();
        if (level > 0) {
            //高水位
            waterBwvParams.height = UiUtils.dp2px(this, 340);
            bubbleLlParams.height = UiUtils.dp2px(this, 170);
        } else if (level < 0) {
            //低水位
            waterBwvParams.height = UiUtils.dp2px(this, 250);
            bubbleLlParams.height = UiUtils.dp2px(this, 125);
        } else {
            //中水位
            waterBwvParams.height = UiUtils.dp2px(this, 290);
            bubbleLlParams.height = UiUtils.dp2px(this, 145);
        }
        bwv_water.setLayoutParams(waterBwvParams);
    }

    /**
     * 烟雾
     */
    private void showSmog() {
        //type =0 无烟，=1 黄烟 =2 白烟
        String color = "";
        if (smogType == 0) {
            iv_smog.setVisibility(View.INVISIBLE);
        } else {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_smog.getLayoutParams();
            //动态计算烟雾所在位置
            layoutParams.rightMargin = (int) (getResources().getDisplayMetrics().widthPixels * 0.125);
            iv_smog.setLayoutParams(layoutParams);

            if (smogType == 1) {
                color = "yellow";
            } else if (smogType == 2) {
                color = "white";
            }
            AnimationDrawable anim = new AnimationDrawable();
            for (int i = 1; i <= 14; i++) {
                int id = getResources().getIdentifier("smog_" + color + i, "drawable", getPackageName());
                Drawable drawable = getResources().getDrawable(id);
                anim.addFrame(drawable, 100);
            }
            anim.setOneShot(false);
            iv_smog.setBackground(anim);
            anim.start();
            iv_smog.setVisibility(View.VISIBLE);
        }
    }
}
