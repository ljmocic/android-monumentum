package eu.execom.monumentum.repository;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;

import eu.execom.monumentum.database.DatabaseHelper;
import eu.execom.monumentum.database.dao.LikeMonumentDAO;

import eu.execom.monumentum.models.LikeMonument;
import eu.execom.monumentum.models.Monument;
import eu.execom.monumentum.utils.ConstantsDatabase;
import eu.execom.monumentum.utils.Preferences_;

/**
 * Created by Helena Zecevic on 27.05.2016..
 */

@EBean
public class LikeMonumentDAORepository {

    private static final String TAG = LikeMonumentDAORepository.class.getSimpleName();

    @Bean
    UserDAORepository userDAORepository;

    @Pref
    Preferences_ preferences;

    @Bean
    MonumentDAORepository monumentDAORepository;

    @OrmLiteDao(helper = DatabaseHelper.class)
    LikeMonumentDAO likeDAO;

    public LikeMonument userLiked(int postID) {
        try {
            return likeDAO.queryBuilder().where().eq(ConstantsDatabase.MONUMENT_FIELD_NAME, postID).and()
                    .eq(ConstantsDatabase.USER_FIELD_NAME, preferences.id().get()).queryForFirst();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    public boolean toggleLike(Monument monument) {

        final LikeMonument like = userLiked(monument.getId());

        if (like == null) {
            likeDAO.create(new LikeMonument(userDAORepository.getLoggedInUser(), monument));
            monumentDAORepository.addLike(monument);
            return true;
        } else {
            likeDAO.delete(like);
            monumentDAORepository.removeLike(monument);
            return false;
        }
    }

}
