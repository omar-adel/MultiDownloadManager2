package modules.general.model.bean.sqlite;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modules.general.model.bean.sqlite.listeners.IModelHelper;
import modules.general.model.db.SqliteCallBack;


/**
 * Created by Net22 on 3/28/2018.
 */

public  abstract   class BaseModelWithIData
        extends BaseModel implements IModelHelper {

    private String localDBOperation="";

    public String getLocalDBOperation() {
        return localDBOperation;
    }

    public void setLocalDBOperation(String localDBOperation) {
        this.localDBOperation = localDBOperation;
    }

    private HashMap hashMap;

    private String orderByColumnName="";
    private boolean orderByAscending=true;

    public String getOrderByColumnName() {
        return orderByColumnName;
    }

    public void setOrderByColumnName(String orderByColumnName) {
        this.orderByColumnName = orderByColumnName;
    }

    public boolean isOrderByAscending() {
        return orderByAscending;
    }

    public void setOrderByAscending(boolean orderByAscending) {
        this.orderByAscending = orderByAscending;
    }

    public void setHashMap(HashMap hashMap) {
        this.hashMap = hashMap;
    }

    public HashMap getHashMap() {
        return hashMap;
    }

    @Override
    public void insertData(Object object) {

    }

    @Override
    public void updateData(Object object ) {

    }

    @Override
    public void saveData(Object object ) {

    }

    @Override
    public void deleteData(Object object ) {

    }
    @Override
    public void deleteAll( ) {

    }

    @Override
    public void insertData(Object object, SqliteCallBack sqliteCallBack) {

    }

    @Override
    public void updateData(Object object, SqliteCallBack sqliteCallBack) {

    }

    @Override
    public void deleteData(Object object, SqliteCallBack sqliteCallBack) {

    }


    @Override
    public void getAll( SqliteCallBack sqliteCallBack) {

    }


    @Override
    public List getAll( ) {
        return null;
    }



    @Override
    public Object getItemByID( int id) {
        return null;
    }


    @Override
    public void getItemByID( int id, SqliteCallBack sqliteCallBack) {


    }

    @Override
    public void setItemWithCustomData(HashMap hashMap) {
        setHashMap(hashMap);
    }


    @Override
    public Object getItemWithCustomData( ) {
        Where sql =  getWithCustomData();
        return sql.querySingle();
    }

    @Override
    public void getItemWithCustomData( SqliteCallBack sqliteCallBack) {
        Where sql =  getWithCustomData();
        sqliteCallBack.onDBDataObjectLoaded(sql.querySingle());
     }



    @Override
    public List getItemsArrWithCustomData( ) {
        Where sql =  getWithCustomData();
        return sql.queryList();
    }

    @Override
    public void getItemsArrWithCustomData(  SqliteCallBack sqliteCallBack) {
        Where sql =  getWithCustomData();
        sqliteCallBack.onDBDataListLoaded((ArrayList) sql.queryList());
     }
    @Override
    public void deleteItemWithCustomData( ) {
        Where sql =  getWithCustomDataNoOrder(false);
        sql.execute();
    }

    private Where getWithCustomData() {
        Where sql =  getWithCustomDataNoOrder(true);
         if(!orderByColumnName.isEmpty())
        {
            Property<String> paramsTableColumnOrder = new Property<String>(getClass(), orderByColumnName);
            sql=sql.orderBy(paramsTableColumnOrder,orderByAscending);
        }
        return sql ;
    }


    private Where   getWithCustomDataNoOrder(boolean isSelect) {
        Object[] keys=hashMap.keySet().toArray();
        Where sql;
        if(isSelect)
        {
            sql= SQLite.select()
                    .from(getClass()).where();
        }
        else
        {
            sql= SQLite.delete()
                    .from(getClass()).where();
        }

        for (int i=0;i<keys.length;i++){
            String colName=(String) keys[i];
            String val=String.valueOf(hashMap.get(colName));
            Property<String> paramsTableColumn = new Property<String>(getClass(), colName);
            sql = sql.and(paramsTableColumn.eq(val));
        }

        return sql ;
    }
}
