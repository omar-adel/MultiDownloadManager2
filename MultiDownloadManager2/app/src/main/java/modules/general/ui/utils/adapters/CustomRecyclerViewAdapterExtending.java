package modules.general.ui.utils.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import modules.general.model.backend.ApiUtils;

public abstract class CustomRecyclerViewAdapterExtending<T>
        extends RecyclerView.Adapter<CustomRecyclerViewAdapterExtending.ViewHolder> {
    public ArrayList<T> mList = new ArrayList<>();
    private OnViewHolderClick mListener;
    public Context mContext;
    public T currentObject;
    public int mainpos;
    //public APIService mAPIService;


    public interface OnViewHolderClick {
        void onClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener

    {
        private Map<Integer, View> mMapView;
        private OnViewHolderClick mListener;

        public ViewHolder(View view, OnViewHolderClick listener) {
            super(view);
            mMapView = new HashMap<>();
            mMapView.put(0, view);
            mListener = listener;

            if (mListener != null)
                view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null)
                mListener.onClick(view, getAdapterPosition());
        }

        public void initViewList(int[] idList) {
            for (int id : idList)
                initViewById(id);
        }

        public void initViewById(int id) {
            View view = (getView() != null ? getView().findViewById(id) : null);

            if (view != null)
                mMapView.put(id, view);
        }

        public View getView() {
            return getView(0);
        }

        public View getView(int id) {
            if (mMapView.containsKey(id))
                return mMapView.get(id);
            else
                initViewById(id);

            return mMapView.get(id);
        }


    }

    protected abstract View createView(Context context, ViewGroup viewGroup, int viewType);

    protected abstract void bindView(T item, ViewHolder viewHolder, final int position);

    public CustomRecyclerViewAdapterExtending(Context context) {
        this(context, null);
    }

    public CustomRecyclerViewAdapterExtending(Context context, OnViewHolderClick listener) {
        super();
        mContext = context;
        mListener = listener;
      //  mAPIService = ApiUtils.getAPIService();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(createView(mContext, viewGroup, viewType), mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        bindView(getItem(position), viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public T getItem(int index) {
        return ((mList != null && index < mList.size()) ? mList.get(index) : null);
    }

    public Context getContext() {
        return mContext;
    }

    public void setList(ArrayList<T> list) {
        mList = list;
    }

    public List<T> getList() {
        return mList;
    }

    public void setClickListener(OnViewHolderClick listener) {
        mListener = listener;
    }


    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(T data) {
        mList.add(data);
        notifyDataSetChanged();
    }

    public void addItem(int position, T data) {
        mList.add(position, data);
        notifyDataSetChanged();
    }

    public void setAll(List<T> mItems) {
        mList.clear();
        for (int i = 0; i < mItems.size(); i++) {
            mList.add(mItems.get(i));
        }
        notifyDataSetChanged();
    }


    public void setAll(ArrayList<T> mItems) {
        mList.clear();
        for (int i = 0; i < mItems.size(); i++) {
            mList.add(mItems.get(i));
        }
        notifyDataSetChanged();
    }

    public void addAll(List<T> mItems) {
        for (int i = 0; i < mItems.size(); i++) {
            mList.add(mItems.get(i));
        }
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<T> mItems) {
        for (int i = 0; i < mItems.size(); i++) {
            mList.add(mItems.get(i));
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        int size = this.mList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mList.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }

}
