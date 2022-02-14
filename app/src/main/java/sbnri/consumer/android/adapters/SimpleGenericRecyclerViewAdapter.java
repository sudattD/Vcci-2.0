package sbnri.consumer.android.adapters;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import sbnri.consumer.android.util.ActivityUtils;
import sbnri.consumer.android.util.GeneralUtilsKt;
import sbnri.consumer.android.util.Optional;


public class SimpleGenericRecyclerViewAdapter<T> extends GenericRecyclerViewAdapter<ItemEncapsulator> {

    private Class<T> type;

    SimpleGenericRecyclerViewAdapter(Class<T> cls, SimpleGenericAdapterBuilder.SimpleGenericAdapterListener listener) {
        super(listener);
        type = cls;
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

    public T get(int position) {
        return (T) mItems.get(position).getItem();
    }

    public List<T> getAll() {
        return Flowable.fromIterable(mItems)
                .filter(itemEncapsulator -> type.isInstance(itemEncapsulator.getItem()))
                .map(itemEncapsulator -> (T) itemEncapsulator.getItem())
                .toList().blockingGet();
    }

    public void publishItems(List<T> filteredList) {
        super.publishResults(GeneralUtilsKt.getItemEncapsulatorsFromObjects(filteredList));
    }

    public void resetAllItems(List<T> items) {
        resetItems(GeneralUtilsKt.getItemEncapsulatorsFromObjects(items));
    }

}
