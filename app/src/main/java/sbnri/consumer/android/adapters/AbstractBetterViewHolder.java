package sbnri.consumer.android.adapters;

import android.view.View;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

/**
 */

public abstract class AbstractBetterViewHolder<T> extends RecyclerView.ViewHolder {

    int currentPosition;

    protected AbstractBetterViewHolder(View view, OnCommonItemClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);
    }

    protected AbstractBetterViewHolder(View view, OnPositionClickListener listener) {
        super(view);
        ButterKnife.bind(this, view);
    }

    protected AbstractBetterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public abstract void bind(T element, int position);

    @CallSuper
    public void bind(T element, int size, int position) {
        bind(element, position);
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

}
