package com.example.lp.ddndisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.lp.ddndisplay.Utils.Config.HAPPY_URL;

public class HappyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_happy);
        reLoad(HAPPY_URL);
    }
}
