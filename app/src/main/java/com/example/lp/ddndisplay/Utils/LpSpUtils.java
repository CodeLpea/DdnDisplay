package com.example.lp.ddndisplay.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.example.lp.ddndisplay.MyApplication;

import java.util.Set;

/**
 *自用Sp管理工具
 * */
public class LpSpUtils {

    //声明Sharedpreferenced对象
    private SharedPreferences sp ;
    private SharedPreferences.Editor edit  ;
    private static final String SPNAME="DdnDisplay";


    private static LpSpUtils instance;

    public static LpSpUtils  getInstance() {
        if(instance==null){
            instance=new LpSpUtils();
        }
        return instance;
    }
    private void  LpSpUtils() {
        sp = MyApplication.getInstance().getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
        edit = sp.edit();
    }
    private void test(){

    }


    public void putString(String key, @Nullable String value) {
        edit.putString(key,value);
    }


    public void putStringSet(String key, @Nullable Set<String> values) {
        edit.putStringSet(key,values);
    }


    public void putInt(String key, int value) {
        edit.putInt(key,value);
    }


    public void putLong(String key, long value) {
        edit.putLong(key,value);
    }


    public void putFloat(String key, float value) {
        edit.putFloat(key,value);
    }


    public void putBoolean(String key, boolean value) {
        edit.putBoolean(key,value);
    }


    public String getString(String key, @Nullable String defValue) {
        return sp.getString(key,defValue);
    }
    public int getInt(String key, @Nullable int defValue) {
        return sp.getInt(key,defValue);
    }

    public long getLong(String key, long defValue) {
        return sp.getLong(key,defValue);
    }


    public float getFloat(String key, float defValue) {
        return sp.getFloat(key,defValue);
    }


    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key,defValue);
    }



    public void remove(String key) {
        edit.remove(key);
    }


    public void clear() {
        edit.clear();
    }


    private void commit() {
        edit.commit();
    }


    private void apply() {
        edit.apply();
    }


}
