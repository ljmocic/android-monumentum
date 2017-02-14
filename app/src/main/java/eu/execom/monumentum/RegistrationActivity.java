package eu.execom.monumentum;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import eu.execom.monumentum.models.User;
import eu.execom.monumentum.repository.UserDAORepository;
import eu.execom.monumentum.utils.Preferences_;

@EActivity(R.layout.activity_registration)
    public class RegistrationActivity extends AppCompatActivity {

    @ViewById
    EditText username;

    @ViewById
    EditText email;

    @ViewById
    EditText password;

    @ViewById
    EditText passwordd;

    @Pref
    Preferences_ prefs;

    @Bean
    UserDAORepository userDAORepository;

    @EditorAction(R.id.password)
    @Click(R.id.registration)
    void register() {
        if (email.getText().toString().equals("") ||
                password.getText().toString().equals("") ||
                username.getText().toString().equals("")) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_LONG).show();
            return;
        }
      //  Log.d("Mihajlo log: ",password.getText()+ " " + passwordd.getText());
        if( !password.getText().toString().equals(passwordd.getText().toString())) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_LONG).show();
            return;
        }

        User user = new User();
        
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setName(username.getText().toString());

        if (!userDAORepository.emailTaken(user.getEmail())) {
            userDAORepository.register(user);
            Toast.makeText(this, "Registration successful.", Toast.LENGTH_SHORT).show();
            NavigationActivity_.intent(this).username(user.getName()).start();
        } else {
            Toast.makeText(this, "Registration failed, email already exists.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
