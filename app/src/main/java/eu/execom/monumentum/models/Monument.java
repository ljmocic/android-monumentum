package eu.execom.monumentum.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;

import eu.execom.monumentum.utils.ConstantsDatabase;


@DatabaseTable(tableName = "Monument")
public class Monument {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    @DatabaseField(columnName = ConstantsDatabase.ID_FIELD_NAME, generatedId = true)
    private int id;

    @DatabaseField(columnName = ConstantsDatabase.IMAGEURL_FIELD_NAME, canBeNull = false)
    private String imageUrl;

    @DatabaseField(columnName = ConstantsDatabase.NAME_FIELD_NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = ConstantsDatabase.USER_FIELD_NAME, canBeNull = false, foreign = true)
    private User user;

    @DatabaseField(columnName = ConstantsDatabase.TYPE_FIELD_NAME, canBeNull = false, foreign = true)
    private MonumentType monumentType;

    @DatabaseField(columnName = ConstantsDatabase.DESCRIPTION_FIELD_NAME, canBeNull = false)
    private String description;

    @DatabaseField(columnName = ConstantsDatabase.LIKE_NUMBER_FIELD_NAME, canBeNull = true)
    private int likeNumber;

    @DatabaseField(columnName = ConstantsDatabase.TIMESTAMP_FIELD_NAME)
    private String timestamp;

    @DatabaseField(columnName = ConstantsDatabase.LATITUDE_FIELD_NAME, canBeNull = true)
    private double locationLatitude;

    @DatabaseField(columnName = ConstantsDatabase.LONGITUDE_FIELD_NAME, canBeNull = true)
    private double locationLongitude;

    public Monument() {
    }

    public Monument(String imageUrl, String name, User user) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.user = user;
        this.likeNumber = 0;
        this.timestamp = sdf.format(new Date());
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MonumentType getMonumentType() {
        return monumentType;
    }

    public void setMonumentType(MonumentType monumentType) {
        this.monumentType = monumentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }
}
