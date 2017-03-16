package com.example.administrator.ormlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/3/16.
 */
// 数据库的帮助类：创建数据库和表等还有更新
public class DBhelp extends OrmLiteSqliteOpenHelper {

    private static DBhelp dBhelp;
    // 建议应用中只持有一个实例：对他做单例
    public DBhelp(Context context) {
        super(context, "testone.db", null, 1);
    }
//写单利：因为这是一个帮助类，后续会用到；我们做的懒汉式的单利，线程不安全，所以加
    //同步锁，保证安全
    public static synchronized DBhelp getInstance(Context context){
        if(dBhelp==null){
            dBhelp=new DBhelp(context.getApplicationContext());
        }
        return dBhelp;
    }
    //创建
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        //Ormlite不需要写sql语句
        try {
            TableUtils.createTableIfNotExists(connectionSource,User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 更新
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //先删除，在创建
        try {
            TableUtils.dropTable(connectionSource,User.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
