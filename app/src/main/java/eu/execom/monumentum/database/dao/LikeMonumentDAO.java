package eu.execom.monumentum.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import eu.execom.monumentum.models.LikeMonument;


public class LikeMonumentDAO extends RuntimeExceptionDao<LikeMonument, Integer> {

    public LikeMonumentDAO(Dao<LikeMonument, Integer> dao) {
        super(dao);
    }
}
