package com.example.lp.ddndisplay.Utils.appstart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.lp.ddndisplay.BaseActivity;

/**
 * Created by Long on 2018/10/11.
 */

public class AppRebootBroadCastReceiver extends BroadcastReceiver {
    private static final String TAG = "RebootBroadCastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, intent.getAction());
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Toast.makeText(context , "监听到启动APP的广播" , Toast.LENGTH_LONG).show();
            bootApplication(context);
        }else if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
            Toast.makeText(context , "监听到系统广播替换" , Toast.LENGTH_LONG).show();
            //bootApplication(context);
        } else if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
            Toast.makeText(context , "监听到系统广播添加" , Toast.LENGTH_LONG).show();
        }else if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
            Toast.makeText(context , "监听到系统广播移除" , Toast.LENGTH_LONG).show();
        }
    }

    private void bootApplication(Context context){
        //重启APP
        Intent launchIntent = new Intent(context, BaseActivity.class);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launchIntent);
    }

}
