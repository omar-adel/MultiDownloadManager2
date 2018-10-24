package modules.general.ui.utils.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import modules.prdownloader.outercode.ui.ViewHolders.AllDownloadsVH;
import modules.prdownloader.outercode.ui.ViewHolders.CompletedDownloadsVH;
import modules.prdownloader.outercode.ui.ViewHolders.PausedDownloadsVH;


/**
 *
 */
public   class CustomRecyclerViewAdapter <T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<T> mList = new ArrayList<>();
    int mAdapterType = 0 ;
    public Context mContext;
    public Fragment mFragment ;
    public T currentObject;
    public int mainpos;


    public static final int AllDownloadsVHType=1;
    public static final int CompletedDownloadsVHType = 2;
    public static final int PausedDownloadsVHType = 3;


    private boolean isLoadingAdded = false;
    public boolean retryPageLoad = false;
    public String errorMsg;
    ////////////////////////


    public CustomRecyclerViewAdapter(Context context,int adapterType) {
        super();
        mContext = context;
        mAdapterType = adapterType;

    }


    @Override
    public int getItemViewType(int position) {
        switch (mAdapterType) {
            case AllDownloadsVHType:
                return AllDownloadsVHType;
            case CompletedDownloadsVHType:
                return CompletedDownloadsVHType;
            case PausedDownloadsVHType:
                return PausedDownloadsVHType;
            default:
                return AllDownloadsVHType ;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case AllDownloadsVHType:
                return new AllDownloadsVH(parent.getContext(), AllDownloadsVH.getView(mContext,parent),this);
            case CompletedDownloadsVHType:
                return new CompletedDownloadsVH(parent.getContext(), CompletedDownloadsVH.getView(mContext,parent),this);
            case PausedDownloadsVHType:
                return new PausedDownloadsVH(parent.getContext(), PausedDownloadsVH.getView(mContext,parent)
                        ,this);

            default:
                return new AllDownloadsVH(parent.getContext(), AllDownloadsVH.getView(mContext,parent),this);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case AllDownloadsVHType:
                ((AllDownloadsVH)holder).bindData(getItem(position)  , position);
                break;
            case CompletedDownloadsVHType:
                ((CompletedDownloadsVH)holder).bindData(getItem(position)  , position);
                break;
            case PausedDownloadsVHType:
                ((PausedDownloadsVH)holder).bindData(getItem(position)  , position);
                break;
            default:
                ((AllDownloadsVH)holder).bindData(getItem(position)  , position);
        }




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
        isLoadingAdded = false;
        int size = this.mList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mList.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        T data=null;
        addItem(data);
    }


    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = mList.size() - 1;
        T data = getItem(position);
        mList.remove(position);
        notifyItemRemoved(position);
    }

}