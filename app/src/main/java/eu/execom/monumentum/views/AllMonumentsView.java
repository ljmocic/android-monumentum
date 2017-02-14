package eu.execom.monumentum.views;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import eu.execom.monumentum.MonumentFullActivity_;
import eu.execom.monumentum.R;
import eu.execom.monumentum.models.LikeMonument;
import eu.execom.monumentum.models.Monument;
import eu.execom.monumentum.models.User;
import eu.execom.monumentum.repository.LikeMonumentDAORepository;
import eu.execom.monumentum.repository.MonumentDAORepository;


@EViewGroup(R.layout.view_all_monuments)
public class AllMonumentsView extends LinearLayout {

    @ViewById
    SimpleDraweeView profilePic;

    @ViewById
    TextView username;

    @ViewById
    TextView likeNumber;

    @ViewById
    ImageView likeIcon;

    @ViewById
    SimpleDraweeView monumentPicture;

    @Bean
    LikeMonumentDAORepository likeDAORepository;

    @Bean
    MonumentDAORepository monumentDAORepository;

    private Monument monument;

    public AllMonumentsView(Context context) {
        super(context);
    }

    public void bind(Monument monument) {
        this.monument = monument;
        final User user = monument.getUser();
        checkLiked();

        if (user.getImageUrl() != null && !user.getImageUrl().isEmpty()) {
            profilePic.setImageURI(new Uri.Builder().scheme(UriUtil.LOCAL_FILE_SCHEME).path(user.getImageUrl()).build());
        }

        username.setText(user.getName());
        monumentPicture.setImageURI(new Uri.Builder().scheme(UriUtil.LOCAL_FILE_SCHEME).path(monument.getImageUrl()).build());
    }

    @Click(R.id.monumentPicture)
    void openFullView(){
        final User user = monument.getUser();
        MonumentFullActivity_.intent(getContext()).monumentSend(monument.getId()).start();
    }

    @Click(R.id.likeIcon)
    @Background
    void toggle() {
        final boolean liked = likeDAORepository.toggleLike(monument);
        updateLikeIcon(liked);
    }

    @Background
    void checkLiked() {
        final LikeMonument liked = likeDAORepository.userLiked(monument.getId());
        updateLikeIcon(liked != null);
    }

    @UiThread
    void updateLikeIcon(boolean liked) {
        likeIcon.setImageResource(liked ? R.drawable.ic_favorite_white_36dp : R.drawable.ic_favorite_border_white_36dp);
        likeNumber.setText(String.valueOf(monumentDAORepository.getLNumber(monument)));
    }
}
