package eu.execom.monumentum.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import eu.execom.monumentum.MonumentByTypesActivity_;
import eu.execom.monumentum.R;
import eu.execom.monumentum.models.MonumentType;


@EViewGroup(R.layout.view_monument_types)
public class MonumentTypesView extends LinearLayout {

    @ViewById
    TextView monumentName;

    public MonumentTypesView(Context context) {
        super(context);
    }

    public void bind(MonumentType type) {
        monumentName.setText(type.getName());
    }

    @Click(R.id.monumentName)
    void openMonumentsByType() {
        MonumentByTypesActivity_.intent(getContext()).monumentType(monumentName.getText().toString()).start();
    }
}
