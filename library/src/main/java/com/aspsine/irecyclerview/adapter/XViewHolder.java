package com.aspsine.irecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by aspsine on 16/3/12.
 */
public abstract class XViewHolder extends RecyclerView.ViewHolder {

    public XViewHolder(View itemView) {
        super(itemView);
    }

    @Deprecated
    public final int getIPosition() {
        return getPosition() - 2;
    }

    public final int getILayoutPosition() {
        return getLayoutPosition() - 2;
    }

    public final int getIAdapterPosition() {
        return getAdapterPosition() - 2;
    }

    public final int getIOldPosition() {
        return getOldPosition() - 2;
    }

    public final long getIItemId() {
        return getItemId();
    }

    public final int getIItemViewType() {
        return getItemViewType();
    }
}
