package eu.execom.monumentum.database.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import eu.execom.monumentum.models.Comment;


public class CommentDAO extends RuntimeExceptionDao<Comment, Integer> {

    public CommentDAO(Dao<Comment, Integer> dao) {
        super(dao);
    }
}
