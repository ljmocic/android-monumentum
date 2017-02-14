package eu.execom.monumentum;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.ViewById;

import eu.execom.monumentum.models.MonumentType;
import eu.execom.monumentum.repository.MonumentTypeDAORepository;

@EActivity(R.layout.activity_new_type)
public class NewTypeActivity extends AppCompatActivity {

    @ViewById
    EditText name;

    @ViewById
    Button save;

    @Bean
    MonumentTypeDAORepository monumentTypeDAORepository;

    @EditorAction(R.id.name)
    @Click
    void save() {
        if (name.getText().toString().equals("")) {
            Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!monumentTypeDAORepository.nameTaken(name.getText().toString())) {
            MonumentType monumentType = new MonumentType();
            monumentType.setName(name.getText().toString());
            monumentTypeDAORepository.createMonumentType(monumentType);
            finish();
        } else {
            Toast.makeText(this, "Type name already exists.", Toast.LENGTH_LONG).show();
        }
    }
}
