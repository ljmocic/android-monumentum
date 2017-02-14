package eu.execom.monumentum.repository;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;

import eu.execom.monumentum.database.DatabaseHelper;
import eu.execom.monumentum.database.dao.UserDAO;
import eu.execom.monumentum.models.User;
import eu.execom.monumentum.utils.ConstantsDatabase;
import eu.execom.monumentum.utils.Preferences_;

/**
 * Created by Helena Zecevic on 17.05.2016..
 */
@EBean
public class UserDAORepository {

    @Pref
    Preferences_ preferences;

    @OrmLiteDao(helper = DatabaseHelper.class)
    UserDAO userDAO;

    public User logIn(String email, String password) {
        User user = null;

        try {
            user = userDAO.queryBuilder().where().eq(ConstantsDatabase.EMAIL_FIELD_NAME, email)
                    .and().eq(ConstantsDatabase.PASSWORD_FIELD_NAME, password).queryForFirst();
            if (user != null) {
                preferences.id().put(user.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void register(User user) {
        userDAO.create(user);
        preferences.id().put(user.getId());
    }

    public void updatePicture(String newUrl) {
        User user = getLoggedInUser();
        user.setImageUrl(newUrl);
        userDAO.update(user);
    }

    public boolean emailTaken(String email) {
        try {
            return userDAO.queryBuilder().where().eq(ConstantsDatabase.EMAIL_FIELD_NAME, email)
                    .countOf() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User findById(int id) {
        return userDAO.queryForId(id);
    }

    public User getLoggedInUser() {
        return findById(preferences.id().get());
    }
}
