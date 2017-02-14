package eu.execom.monumentum.repository;

import org.androidannotations.annotations.EBean;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eu.execom.monumentum.database.DatabaseHelper;
import eu.execom.monumentum.database.dao.MonumentTypeDAO;
import eu.execom.monumentum.models.MonumentType;
import eu.execom.monumentum.utils.ConstantsDatabase;

/**
 * Created by Helena Zecevic on 17.05.2016..
 */
@EBean
public class MonumentTypeDAORepository {

    @OrmLiteDao(helper = DatabaseHelper.class)
    MonumentTypeDAO monumentTypeDAO;

    public List<MonumentType> findAll() {
        return monumentTypeDAO.queryForAll();
    }

    public void createMonumentType(MonumentType monumentType) {
        monumentTypeDAO.create(monumentType);
    }

    public ArrayList<String> getStringTypes() {
        ArrayList types = (ArrayList<MonumentType>) findAll();
        ArrayList<String> typeStrings = new ArrayList<>();

        for (int i = 0; i < types.size(); i++) {
            typeStrings.add(((MonumentType) types.get(i)).getName());
        }

        return typeStrings;
    }

    public MonumentType getMonumentTypeByName(String name) {
        try {
            return monumentTypeDAO.queryBuilder().where().eq(ConstantsDatabase.NAME_FIELD_NAME, name).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean nameTaken(String name) {
        try {
            return monumentTypeDAO.queryBuilder().where().eq(ConstantsDatabase.NAME_FIELD_NAME, name).countOf() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
