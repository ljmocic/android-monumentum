package eu.execom.monumentum.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;

import eu.execom.monumentum.utils.ConstantsDatabase;

@DatabaseTable(tableName = "comment")
public class Comment {

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    @DatabaseField(columnName = ConstantsDatabase.ID_FIELD_NAME, generatedId = true)
    private int id;

    @DatabaseField(columnName = ConstantsDatabase.USER_FIELD_NAME, foreign = true)
    private User user;

    @DatabaseField(columnName = ConstantsDatabase.MONUMENT_FIELD_NAME, foreign = true)
    private Monument monument;

    @DatabaseField(columnName = ConstantsDatabase.CONTENT_FIELD_NAME, canBeNull = false)
    private String content;

    @DatabaseField(columnName = ConstantsDatabase.LIKE_NUMBERS_FIELD_NAME)
    private int likeNumbers;

    @DatabaseField(columnName = ConstantsDatabase.TIMESTAMP_FIELD_NAME)
    private String timestamp;

    public Comment() {

    }

    public Comment(User user, Monument monument, String content) {
        this.user = user;
        this.monument = monument;
        this.content = content;
        this.likeNumbers = 0;
        this.timestamp = sdf.format(new Date());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Monument getMonument() {
        return monument;
    }

    public void setMonument(Monument monument) {
        this.monument = monument;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeNumbers() {
        return likeNumbers;
    }

    public void setLikeNumbers(int likeNumbers) {
        this.likeNumbers = likeNumbers;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
