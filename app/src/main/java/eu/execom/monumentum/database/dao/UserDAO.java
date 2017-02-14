package eu.execom.monumentum.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import eu.execom.monumentum.models.User;


public class UserDAO extends RuntimeExceptionDao<User, Integer> {

    public UserDAO(Dao<User, Integer> dao) {
        super(dao);
    }
}
