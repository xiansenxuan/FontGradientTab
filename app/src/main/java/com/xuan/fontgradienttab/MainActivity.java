package com.xuan.fontgradienttab;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private FontGradientTab font_tab;
    private LinearLayout ll_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        font_tab=findViewById(R.id.font_tab);
        ll_content=findViewById(R.id.ll_content);

    }

    public void RightToLeft(View view) {
        startPerform(FontGradientTab.ORIENTATION.ORIENTATION_LEFT);
    }

    public void leftToRight(View view) {
        startPerform(FontGradientTab.ORIENTATION.ORIENTATION_RIGHT);
    }

    public void defaultTo(View view){
        startPerform(null);
    }

    private void startPerform(FontGradientTab.ORIENTATION orientation ){
        font_tab.setOrientation(orientation);

        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                font_tab.startPerform(value);
            }
        });
        valueAnimator.start();
    }

    public void viewPagerTo(View view) {
        startActivity(new Intent(this,PagerActivity.class));
    }
}
