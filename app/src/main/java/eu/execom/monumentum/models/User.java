package eu.execom.monumentum.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

import eu.execom.monumentum.utils.ConstantsDatabase;


@DatabaseTable(tableName = "User")
public class User {

    @DatabaseField(columnName = ConstantsDatabase.ID_FIELD_NAME, generatedId = true)
    private int id;

    @DatabaseField(columnName = ConstantsDatabase.USERNAME_FIELD_NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = ConstantsDatabase.IMAGEURL_FIELD_NAME, canBeNull = true)
    private String imageUrl;

    @DatabaseField(columnName = ConstantsDatabase.EMAIL_FIELD_NAME, canBeNull = false)
    private String email;

    @DatabaseField(columnName = ConstantsDatabase.PASSWORD_FIELD_NAME, canBeNull = false)
    private String password;

    List<Monument> monuments = new ArrayList<>();

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Monument> getMonuments() {
        return monuments;
    }

    public void setMonuments(List<Monument> monuments) {
        this.monuments = monuments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
