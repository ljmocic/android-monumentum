package eu.execom.monumentum.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import eu.execom.monumentum.models.MonumentType;
import eu.execom.monumentum.views.MonumentTypesView;
import eu.execom.monumentum.views.MonumentTypesView_;


@EBean
public class MonumentTypesAdapter extends BaseAdapter {

    List<MonumentType> types;

    @AfterInject
    void afterInject() {
        types = new ArrayList<>();
    }

    @RootContext
    Context context;

    public List<MonumentType> getTypes() {
        return types;
    }

    public void setTypes(List<MonumentType> types) {
        this.types = types;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public Object getItem(int i) {
        return types.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MonumentTypesView monumentTypesView;

        if (view == null) {
            monumentTypesView = MonumentTypesView_.build(context);
        } else {
            monumentTypesView = (MonumentTypesView) view;
        }

        monumentTypesView.bind((MonumentType) getItem(i));

        return monumentTypesView;
    }
}
