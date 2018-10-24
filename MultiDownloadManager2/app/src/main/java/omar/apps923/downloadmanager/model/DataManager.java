/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package omar.apps923.downloadmanager.model;


import android.content.Context;
import android.content.ContextWrapper;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modules.general.model.bean.sqlite.BaseModelWithIData;
import modules.general.model.db.SqliteCallBack;
import modules.general.model.db.listener.IDataHelper;
import modules.general.model.shareddata.Prefs;
import omar.apps923.downloadmanager.model.db.DbHelper;


/**
 * Created by janisharali on 27/01/17.
 */

 public class DataManager  implements IDataHelper {

    private static DataManager INSTANCE = null;
    Context mContext;
     private DbHelper mDbHelper;
    SqliteCallBack mSqliteCallBack;
    SqliteCallBack mPresenterSqliteCallBack;

    public static DataManager getInstance(Context context) {
//        if (INSTANCE == null) {
//            INSTANCE = new DataManager(context);
//        }
//        return INSTANCE;
        return new DataManager(context);
    }


    public DataManager(Context context) {
        mContext = context;
        mDbHelper = new DbHelper(context);
         setSqliteCallBack();
    }


    private void setSqliteCallBack() {

        mSqliteCallBack = new SqliteCallBack() {
            @Override
            public void onDBDataListLoaded(ArrayList data, String localDbOperation) {
                super.onDBDataListLoaded(data, localDbOperation);
                if(mPresenterSqliteCallBack!=null)
                {
                    mPresenterSqliteCallBack.onDBDataListLoaded(data,localDbOperation);

                }
            }

            @Override
            public void onDBDataObjectLoaded(Object data, String localDbOperation) {
                super.onDBDataObjectLoaded(data, localDbOperation);
                if(mPresenterSqliteCallBack!=null)
                {
                    mPresenterSqliteCallBack.onDBDataObjectLoaded(data,localDbOperation);
                }
            }

            @Override
            public void onDBDataListLoaded(ArrayList data) {
                super.onDBDataListLoaded(data);
                if(mPresenterSqliteCallBack!=null) {
                    mPresenterSqliteCallBack.onDBDataListLoaded(data);
                }
            }

            @Override
            public void onDBDataObjectLoaded(Object data) {
                super.onDBDataObjectLoaded(data);
                if(mPresenterSqliteCallBack!=null) {
                    mPresenterSqliteCallBack.onDBDataObjectLoaded(data);
                }
            }
        };
    }


    public void setPresenterSqliteCallBack(SqliteCallBack callBack) {
        mPresenterSqliteCallBack = callBack;
    }

    public void initData() {
        new Prefs.Builder()
                .setContext(mContext)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(mContext.getPackageName())
                .setUseDefaultSharedPreference(false)
                .build();

        FlowManager.init(FlowConfig.builder(mContext.getApplicationContext())
                .openDatabasesOnInit(true)
                .build());
    }



    public DbHelper getDataHelper() {
        return mDbHelper;
    }

    @Override
    public void insertData(Object object) {
        ((BaseModelWithIData)object).insertData(object);
    }

    @Override
    public void updateData(Object object) {
        ((BaseModelWithIData)object).updateData(object);
    }

    @Override
    public void saveData(Object object) {
        ((BaseModelWithIData)object).saveData(object);
    }

    @Override
    public void deleteData(Object object) {
        ((BaseModelWithIData)object).deleteData(object);
    }

    @Override
    public void deleteAll(Object object) {
        ((BaseModelWithIData)object).deleteAll();

    }

    @Override
    public void insertData(Object object, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).insertData(object,sqliteCallBack);
    }

    @Override
    public void updateData(Object object, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).updateData(object,sqliteCallBack);

    }

    @Override
    public void deleteData(Object object, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).deleteData(object,sqliteCallBack);

    }

    @Override
    public void getAll(Object object, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).getAll(sqliteCallBack);
    }

    @Override
    public List getAll(Object object) {
        return ((BaseModelWithIData)object).getAll();
    }




    @Override
    public Object getItemByID(Object object, int id) {
        return ((BaseModelWithIData)object).getItemByID(id);
    }

    @Override
    public void getItemByID(Object object, int id, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).getItemByID(id,sqliteCallBack);
    }





    @Override
    public void setItemWithCustomData(HashMap hashMap) {

    }


    @Override
    public void getItemWithCustomData(Object object, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).getItemWithCustomData(sqliteCallBack);

    }




    @Override
    public List getItemsArrWithCustomData(Object object) {
        return ((BaseModelWithIData)object).getItemsArrWithCustomData();
    }

    @Override
    public void getItemsArrWithCustomData(Object object, SqliteCallBack sqliteCallBack) {
        ((BaseModelWithIData)object).getItemsArrWithCustomData(sqliteCallBack);
    }

    @Override
    public Object getItemWithCustomData(Object object) {

        return  ((BaseModelWithIData)object).getItemWithCustomData();
    }

    @Override
    public void deleteItemWithCustomData(Object object) {
        ((BaseModelWithIData)object).deleteItemWithCustomData();

    }





//    public void getDownloadModel(String downloadId) {
//        mDbHelper.getDownloadModel(downloadId,mSqliteCallBack);
//    }

}
