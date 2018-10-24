package modules.general.model.db.listener;

import java.util.ArrayList;

/**
 * Created by Net22 on 11/15/2017.
 */

public interface ISqliteCallBack<T> {

    void onDBDataListLoaded(ArrayList<T> data, String localDbOperation);
    void onDBDataObjectLoaded(T data, String localDbOperation);
    void onDBDataListLoaded(ArrayList<T> data);
    void onDBDataObjectLoaded(T data);
}
