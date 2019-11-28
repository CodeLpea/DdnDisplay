package com.example.lp.ddndisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.lp.ddndisplay.Utils.Config.GAO_XING_HEYUAN_DATA;

public class DataWatchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_data_watch);
        reLoad(GAO_XING_HEYUAN_DATA);
    }
}
