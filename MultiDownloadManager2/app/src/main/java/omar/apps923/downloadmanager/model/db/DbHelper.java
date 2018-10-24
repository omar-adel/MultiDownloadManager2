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

package omar.apps923.downloadmanager.model.db;

import android.content.Context;

import java.util.HashMap;
import java.util.List;

import modules.general.model.bean.sqlite.BaseModelWithIData;
import modules.general.model.db.SqliteCallBack;
import modules.general.model.db.listener.IDataHelper;

/**
 * Created by janisharali on 08/12/16.
 */

public class DbHelper
        implements IDataHelper {

    Context dbContext;

    public DbHelper(Context context) {
        dbContext = context;
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





}
