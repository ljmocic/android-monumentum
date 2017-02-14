package eu.execom.monumentum.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import eu.execom.monumentum.models.LikeComment;


public class LikeCommentDAO extends RuntimeExceptionDao<LikeComment, Integer> {

    public LikeCommentDAO(Dao<LikeComment, Integer> dao) {
        super(dao);
    }
}
