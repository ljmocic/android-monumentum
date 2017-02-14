package eu.execom.monumentum.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import eu.execom.monumentum.models.MonumentType;


public class MonumentTypeDAO extends RuntimeExceptionDao<MonumentType, Integer> {

    public MonumentTypeDAO(Dao<MonumentType, Integer> dao) {
        super(dao);
    }
}
