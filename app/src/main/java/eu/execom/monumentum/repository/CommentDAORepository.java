package eu.execom.monumentum.repository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eu.execom.monumentum.database.DatabaseHelper;
import eu.execom.monumentum.database.dao.CommentDAO;
import eu.execom.monumentum.database.dao.UserDAO;
import eu.execom.monumentum.models.Comment;
import eu.execom.monumentum.models.Monument;
import eu.execom.monumentum.utils.ConstantsDatabase;
import eu.execom.monumentum.utils.Preferences_;

/**
 * Created by Helena Zecevic on 27.05.2016..
 */
@EBean
public class CommentDAORepository {

    private static final String TAG = LikeMonumentDAORepository.class.getSimpleName();

    @Bean
    UserDAORepository userDAORepository;

    @Pref
    Preferences_ preferences;

    @Bean
    MonumentDAORepository monumentDAORepository;

    @OrmLiteDao(helper = DatabaseHelper.class)
    CommentDAO commentDAO;

    @OrmLiteDao(helper = DatabaseHelper.class)
    UserDAO userDAO;

    public List<Comment> findCommentsByUser(Monument monument) {
        try {
            List<Comment> comments = commentDAO.queryBuilder().where()
                    .eq(ConstantsDatabase.USER_FIELD_NAME, userDAORepository.getLoggedInUser().getId())
                    .query();
            refreshUsers(comments);
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Comment> findCommentsByMonument(Monument monument) {
        try {
            List<Comment> comments = commentDAO.queryBuilder().where()
                    .eq(ConstantsDatabase.MONUMENT_FIELD_NAME, monument.getId()).query();
            refreshUsers(comments);
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void addLike(Comment comment) {
        try {
            final Comment tempComment = commentDAO.queryBuilder().where()
                    .eq(ConstantsDatabase.ID_FIELD_NAME, comment.getId()).queryForFirst();
            tempComment.setLikeNumbers(tempComment.getLikeNumbers() + 1);
            commentDAO.update(tempComment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeLike(Comment comment) {
        try {
            final Comment tempComment = commentDAO.queryBuilder().where()
                    .eq(ConstantsDatabase.ID_FIELD_NAME, comment.getId()).queryForFirst();
            tempComment.setLikeNumbers(tempComment.getLikeNumbers() - 1);
            commentDAO.update(tempComment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLNumber(Comment comment) {
        try {
            return commentDAO.queryBuilder().where()
                    .eq(ConstantsDatabase.ID_FIELD_NAME, comment.getId())
                    .queryForFirst().getLikeNumbers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void createComment(String content, Monument monument) {
        Comment comment = new Comment(userDAORepository.getLoggedInUser(), monument, content);
        createComment(comment);
    }

    public void createComment(Comment comment) {
        commentDAO.create(comment);
    }

    private void refreshUsers(List<Comment> comments) {
        for (Comment c : comments) {
            userDAO.refresh(c.getUser());
        }
    }

}
