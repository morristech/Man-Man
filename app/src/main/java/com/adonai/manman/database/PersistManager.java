package com.adonai.manman.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.adonai.manman.entities.ManPage;
import com.adonai.manman.entities.ManSectionItem;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by adonai on 29.06.14.
 */
public class PersistManager extends OrmLiteSqliteOpenHelper {
    private static final String TAG = PersistManager.class.getSimpleName();

    private static final String DATABASE_NAME ="mansion.db";

    private static final int DATABASE_VERSION = 1;

    //Dao fast access links
    private RuntimeExceptionDao<ManSectionItem, String> manChaptersDao;
    private RuntimeExceptionDao<ManPage, String> manPagesDao;

    private final Context mContext;

    public PersistManager(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    //Выполняется, когда файл с БД не найден на устройстве
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ManSectionItem.class);
            TableUtils.createTable(connectionSource, ManPage.class);
        } catch (SQLException e) {
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer, int newVer) {

    }

    @NonNull
    public RuntimeExceptionDao<ManSectionItem, String> getManChaptersDao() {
        if (manChaptersDao == null) {
            manChaptersDao = getRuntimeExceptionDao(ManSectionItem.class);
        }
        return manChaptersDao;
    }

    @NonNull
    public RuntimeExceptionDao<ManPage, String> getManPagesDao() {
        if (manPagesDao == null) {
            manPagesDao = getRuntimeExceptionDao(ManPage.class);
        }
        return manPagesDao;
    }

    @Override
    public void close() {
        super.close();
    }
}