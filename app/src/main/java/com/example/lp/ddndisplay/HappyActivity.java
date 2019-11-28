package com.example.lp.ddndisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.lp.ddndisplay.Utils.Config.GAO_XING_HEYUAN_HAPPY;

public class HappyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_happy);
        reLoad(GAO_XING_HEYUAN_HAPPY);
    }
}
