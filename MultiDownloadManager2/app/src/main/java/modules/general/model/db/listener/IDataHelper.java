package modules.general.model.db.listener;

import java.util.HashMap;
import java.util.List;

import modules.general.model.db.SqliteCallBack;


/**
 * Created by Net5 on 2/25/2018.
 */

public interface IDataHelper<G> {

    void insertData(G object);

    void updateData(G object);

    void saveData(G object);

    void deleteData(G object);

    void deleteAll(G object);

    void insertData(G object, SqliteCallBack sqliteCallBack);

    void updateData(G object, SqliteCallBack sqliteCallBack);

    void deleteData(G object, SqliteCallBack sqliteCallBack);

    void getAll(G object, SqliteCallBack sqliteCallBack);

    public List<G> getAll(G object);

    public void getItemByID(G object, int id, SqliteCallBack sqliteCallBack);

    public G getItemByID(G object, int id);

    public void setItemWithCustomData(HashMap hashMap);

    public void getItemWithCustomData(G object, SqliteCallBack sqliteCallBack);

    public G getItemWithCustomData(G object);

    public void getItemsArrWithCustomData(G object, SqliteCallBack sqliteCallBack);

    public List<G> getItemsArrWithCustomData(G object);

    public void deleteItemWithCustomData(G object);


}
