package sbnri.consumer.android.adapters;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import sbnri.consumer.android.util.ActivityUtils;
import sbnri.consumer.android.util.GeneralUtilsKt;
import sbnri.consumer.android.util.Optional;

public class SimpleGenericListAdapter<T> extends GenericListAdapter<ItemEncapsulator> {

    private Class<T> type;

    SimpleGenericListAdapter(Class<T> cls, @NonNull Context context, int resource, SimpleGenericAdapterBuilder.SimpleGenericAdapterListener listener) {
        super(context, resource, listener);
        type = cls;
    }

    public SimpleGenericListAdapter(Class<T> cls, @NonNull Context context, int resource, int textViewResourceId, SimpleGenericAdapterBuilder.SimpleGenericAdapterListener listener) {
        super(context, resource, textViewResourceId, listener);
    }

    public void setSelected(List<T> selectedItems) {
        super.setSelectedItems(GeneralUtilsKt.getItemEncapsulatorsFromObjects(selectedItems));
    }


    @SuppressWarnings("unchecked")
    public List<T> getSelected() {
        return Flowable.fromIterable(Optional.orElse(getSelectedItemsOfCertainClass(type), new ArrayList<ItemEncapsulator>()).get())
                .filter(itemEncapsulator -> type.isInstance(itemEncapsulator.getItem()))
                .map(itemEncapsulator -> (T) itemEncapsulator.getItem())
                .toList().blockingGet();
    }

    public void addToSelectedItems(T item) {
        super.addToSelectedItems(ActivityUtils.getItemEncapsulatorFromObject(item));
    }

    public void removeFromSelectedItem(T item) {
        super.removeFromSelectedItem(ActivityUtils.getItemEncapsulatorFromObject(item));
    }

    public void publishItems(List<T> filteredList) {
        super.publishResults(GeneralUtilsKt.getItemEncapsulatorsFromObjects(filteredList));
    }

    public T get(int position) {
        return (T) getItem(position).getItem();
    }

    public List<T> getAll() {
        return Flowable.fromIterable(mItems)
                .filter(itemEncapsulator -> type.isInstance(itemEncapsulator.getItem()))
                .map(itemEncapsulator -> (T) itemEncapsulator.getItem())
                .toList().blockingGet();
    }

    public void setItems(List<T> items) {
        ((SimpleGenericAdapterBuilder.SimpleGenericAdapterListener) listener).setListItems(GeneralUtilsKt.getItemEncapsulatorsFromObjects(items));

    }
}
