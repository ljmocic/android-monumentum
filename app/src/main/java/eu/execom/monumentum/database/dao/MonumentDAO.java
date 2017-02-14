package eu.execom.monumentum.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import eu.execom.monumentum.models.Monument;


public class MonumentDAO extends RuntimeExceptionDao<Monument, Integer> {

    public MonumentDAO(Dao<Monument, Integer> dao) {
        super(dao);
    }
}
