package com.example.lp.ddndisplay;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static com.example.lp.ddndisplay.Utils.NavigationBarUtil.hideNavigationBar;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        //hideBottomUIMenu();
        hideNavigationBar(this.getWindow());
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();

    }


}
