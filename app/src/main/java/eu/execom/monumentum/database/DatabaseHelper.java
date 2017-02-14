package eu.execom.monumentum.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import eu.execom.monumentum.models.Comment;
import eu.execom.monumentum.models.LikeComment;
import eu.execom.monumentum.models.LikeMonument;
import eu.execom.monumentum.models.Monument;
import eu.execom.monumentum.models.MonumentType;
import eu.execom.monumentum.models.User;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "monumentum.db";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, MonumentType.class);
            TableUtils.createTable(connectionSource, Monument.class);
            TableUtils.createTable(connectionSource, LikeMonument.class);
            TableUtils.createTable(connectionSource, Comment.class);
            TableUtils.createTable(connectionSource, LikeComment.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, LikeComment.class, true);
            TableUtils.dropTable(connectionSource, Comment.class, true);
            TableUtils.dropTable(connectionSource, LikeMonument.class, true);
            TableUtils.dropTable(connectionSource, Monument.class, true);
            TableUtils.dropTable(connectionSource, MonumentType.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
