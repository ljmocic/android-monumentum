package eu.execom.monumentum;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

import eu.execom.monumentum.models.Monument;
import eu.execom.monumentum.repository.MonumentDAORepository;
import eu.execom.monumentum.repository.MonumentTypeDAORepository;

@EActivity(R.layout.activity_new_monument)
public class NewMonumentActivity extends AppCompatActivity {

    @Extra
    String imagePath;

    @ViewById
    SimpleDraweeView imageCaptured;

    @ViewById
    EditText name;

    @ViewById
    EditText description;

    @ViewById
    Spinner dropDown;

    @Bean
    MonumentDAORepository monumentDAORepository;

    @Bean
    MonumentTypeDAORepository monumentTypeDAORepository;

    double Latitude;

    double Longitude;

    @AfterViews
    void setImageCaptured() {
        Uri uri = new Uri.Builder().scheme(UriUtil.LOCAL_FILE_SCHEME).path(imagePath).build();
        imageCaptured.setImageURI(uri);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, monumentTypeDAORepository.getStringTypes());
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropDown.setAdapter(adapter);
    }

    @Click
    @EditorAction(R.id.description)
    void save() {
        if (dropDown.getSelectedItem() == null) {
            Toast.makeText(this, "Type must be selected!", Toast.LENGTH_LONG).show();
            return;
        }

        if (name.getText().toString().equals("")) {
            Toast.makeText(this, "Name must not be empty!", Toast.LENGTH_LONG).show();
            return;
        }

        Monument monument = new Monument();
        monument.setName(name.getText().toString());
        monument.setImageUrl(imagePath);
        Toast.makeText(this, imagePath, Toast.LENGTH_LONG).show();
        monument.setMonumentType(monumentTypeDAORepository.getMonumentTypeByName(dropDown.getSelectedItem().toString()));
        monument.setDescription(description.getText().toString());
        monument.setTimestamp(Monument.sdf.format(new Date()));
        monument.setLocationLatitude(Latitude);
        monument.setLocationLongitude(Longitude);
        monumentDAORepository.createMonument(monument);
        finish();
    }

    @OnActivityResult(0)
    void result(Intent data){
        Latitude = data.getDoubleExtra("first", 44.0165);
        Longitude = data.getDoubleExtra("second", 21.0059);
    }

    @Click(R.id.set_location)
    void setLocation() {
        MapsActivity_.intent(this).startForResult(0);
    }

}
