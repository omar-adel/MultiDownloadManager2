package modules.general.model.bean.sqlite.listeners;

import java.util.HashMap;
import java.util.List;

import modules.general.model.db.SqliteCallBack;

public interface IModelHelper<G> {

    void insertData(G object);

    void updateData(G object);

    void saveData(G object);

    void deleteData(G object);

    void deleteAll();

    void insertData(G object, SqliteCallBack sqliteCallBack);

    void updateData(G object, SqliteCallBack sqliteCallBack);

    void deleteData(G object, SqliteCallBack sqliteCallBack);

    void getAll(SqliteCallBack sqliteCallBack);

    public List<G> getAll();

    public void getItemByID(int id, SqliteCallBack sqliteCallBack);

    public G getItemByID(int id);

    public void setItemWithCustomData(HashMap hashMap);

    public void getItemWithCustomData(SqliteCallBack sqliteCallBack);

    public G getItemWithCustomData();

    public void getItemsArrWithCustomData(SqliteCallBack sqliteCallBack);

    public List<G> getItemsArrWithCustomData();

    public void deleteItemWithCustomData();


}
