package eu.execom.monumentum.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;


import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import eu.execom.monumentum.MonumentFullActivity_;
import eu.execom.monumentum.R;
import eu.execom.monumentum.models.Monument;


@EViewGroup(R.layout.view_my_monuments)
public class MyMonumentsView extends CardView {

    Monument monument;

    @ViewById
    SimpleDraweeView monumentPicture;

    @Click(R.id.monumentPicture)
    void openFullView() {
        MonumentFullActivity_.intent(getContext()).monumentSend(monument.getId()).start();
    }

    public MyMonumentsView(Context context) {
        super(context);
    }

    public void bind(Monument monument) {
        monumentPicture.setImageURI(new Uri.Builder().scheme(UriUtil.LOCAL_FILE_SCHEME).path(monument.getImageUrl()).build());
        this.monument = monument;
    }
}
