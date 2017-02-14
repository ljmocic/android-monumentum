package eu.execom.monumentum;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import eu.execom.monumentum.adapters.CommentsAdapter;
import eu.execom.monumentum.models.LikeMonument;
import eu.execom.monumentum.models.Monument;
import eu.execom.monumentum.repository.CommentDAORepository;
import eu.execom.monumentum.repository.LikeMonumentDAORepository;
import eu.execom.monumentum.repository.MonumentDAORepository;
import eu.execom.monumentum.utils.Preferences_;


@EActivity(R.layout.activity_monument_full_view)
public class MonumentFullActivity extends AppCompatActivity {

    @Bean
    CommentsAdapter adapter;

    @Bean
    CommentDAORepository commentDAORepository;

    @Bean
    MonumentDAORepository monumentDAORepository;

    @Bean
    LikeMonumentDAORepository likeDAORepository;

    @Extra
    int monumentSend;

    @Pref
    Preferences_ preferences;

    @ViewById
    ListView listViewComments;

    Monument monument;

    EditText textViewComment;

    TextView descriptionTextView;

    TextView likeNumber;

    TextView username;

    TextView timeStampTextView;

    Button buttonAddComment;

    ImageView likeIcon;

    SimpleDraweeView monumentPicture;

    SimpleDraweeView profilePic;

    String description;

    @AfterViews
    @UiThread(delay = 100)
    void setContent(){
        this.monument = monumentDAORepository.getMonumentById(monumentSend);
        this.description = monumentDAORepository.getMonumentById(monumentSend).getDescription().toString();

        LayoutInflater inflater = this.getLayoutInflater();
        View header = inflater.inflate(R.layout.header_view, null);

        textViewComment = (EditText) header.findViewById(R.id.textViewComment);
        descriptionTextView = (TextView) header.findViewById(R.id.descriptionTextView);
        likeNumber = (TextView) header.findViewById(R.id.likeNumber);
        likeIcon = (ImageView) header.findViewById(R.id.likeIcon);
        profilePic = (SimpleDraweeView) header.findViewById(R.id.profilePic);
        username = (TextView) header.findViewById(R.id.username);
        timeStampTextView = (TextView) header.findViewById(R.id.timeStampTextView);
        monumentPicture = (SimpleDraweeView) header.findViewById(R.id.monumentPicture);
        buttonAddComment = (Button) header.findViewById(R.id.buttonAddComment);
        listViewComments.addHeaderView(header);

        listViewComments.setAdapter(adapter);
        adapter.setComments(commentDAORepository.findCommentsByMonument(monument));
        descriptionTextView.setText(" " + this.description);
        timeStampTextView.setText(monument.getTimestamp());
        checkLiked();
        username.setText(String.valueOf(monument.getUser().getName()));
        likeNumber.setText(Integer.toString(monument.getLikeNumber()));
        monumentPicture.setImageURI(new Uri.Builder().scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(monument.getImageUrl()).build());

        if (monument.getUser().getImageUrl() != null) {
            profilePic.setImageURI(new Uri.Builder().scheme(UriUtil.LOCAL_FILE_SCHEME).
                    path(monument.getUser().getImageUrl()).build());
        }

        buttonAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textViewComment.getText().toString().isEmpty()) {
                    return;

                }
                commentDAORepository.createComment(textViewComment.getText().toString(), monument);
                textViewComment.setText("");
                adapter.setComments(commentDAORepository.findCommentsByMonument(monument));
                InputMethodManager inputManager = (InputMethodManager)
                        getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(0, 0);
            }
        });

        likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean liked = likeDAORepository.toggleLike(monument);
                updateLikeIcon(liked);
            }
        });
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
