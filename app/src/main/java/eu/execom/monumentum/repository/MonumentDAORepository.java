package eu.execom.monumentum.repository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eu.execom.monumentum.database.DatabaseHelper;
import eu.execom.monumentum.database.dao.MonumentDAO;
import eu.execom.monumentum.database.dao.MonumentTypeDAO;
import eu.execom.monumentum.database.dao.UserDAO;
import eu.execom.monumentum.models.Monument;
import eu.execom.monumentum.models.MonumentType;
import eu.execom.monumentum.utils.ConstantsDatabase;

/**
 * Created by Helena Zecevic on 17.05.2016..
 */
@EBean
public class MonumentDAORepository {

    @OrmLiteDao(helper = DatabaseHelper.class)
    MonumentDAO monumentDAO;

    @OrmLiteDao(helper = DatabaseHelper.class)
    UserDAO userDAO;

    @OrmLiteDao(helper = DatabaseHelper.class)
    MonumentTypeDAO monumentTypeDAO;

    @Bean
    UserDAORepository userDAORepository;

    public List<Monument> findMonumentsByUser() {
        try {
            final List<Monument> monuments = monumentDAO.queryBuilder().where()
                    .eq(ConstantsDatabase.USER_FIELD_NAME, userDAORepository.getLoggedInUser()
                            .getId()).query();
            refreshUsers(monuments);
            refreshTypes(monuments);
            return monuments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Monument> findAll() {
        final List<Monument> monuments = monumentDAO.queryForAll();
        refreshTypes(monuments);
        refreshUsers(monuments);
        return monuments;
    }

    public List<Monument> findByType(MonumentType monumentType) {
        try {
            final List<Monument> monuments = monumentDAO.queryBuilder().where()
                    .eq("type", monumentType).query();
            refreshUsers(monuments);
            return monuments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void createMonument(Monument monument) {
        monument.setUser(userDAORepository.getLoggedInUser());
        monumentDAO.create(monument);
    }

    public Monument getMonumentById(int monId) {
        try {
            Monument m = monumentDAO.queryBuilder().where().eq(ConstantsDatabase.ID_FIELD_NAME, monId)
                    .queryForFirst();
            userDAO.refresh(m.getUser());
            return m;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addLike(Monument monument) {
        try {
            final Monument tempMonument = monumentDAO.queryBuilder().where()
                    .eq(ConstantsDatabase.ID_FIELD_NAME, monument.getId()).queryForFirst();
            tempMonument.setLikeNumber(tempMonument.getLikeNumber() + 1);
            monumentDAO.update(tempMonument);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeLike(Monument monument) {
        final Monument tempMonument;
        try {
            tempMonument = monumentDAO.queryBuilder().where()
                    .eq(ConstantsDatabase.ID_FIELD_NAME, monument.getId()).queryForFirst();
            tempMonument.setLikeNumber(tempMonument.getLikeNumber() - 1);
            monumentDAO.update(tempMonument);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLNumber(Monument monument) {
        try {
            return monumentDAO.queryBuilder().where().eq(ConstantsDatabase.ID_FIELD_NAME, monument.getId())
                    .queryForFirst().getLikeNumber();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void refreshUsers(List<Monument> monuments) {
        for (Monument m : monuments) {
            userDAO.refresh(m.getUser());
        }
    }

    private void refreshTypes(List<Monument> monuments) {
        for (Monument m : monuments) {
            monumentTypeDAO.refresh(m.getMonumentType());
        }
    }

}
