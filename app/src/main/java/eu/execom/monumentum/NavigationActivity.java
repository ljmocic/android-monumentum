package eu.execom.monumentum;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;
import java.io.IOException;

import eu.execom.monumentum.adapters.TabAdapter;
import eu.execom.monumentum.utils.FileUtils;
import eu.execom.monumentum.utils.Preferences_;

@OptionsMenu(R.menu.navigation_menu)
@EActivity(R.layout.activity_navigation)
public class NavigationActivity extends AppCompatActivity {

    boolean doubleBackPressed = false;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final String PHOTO_PATH = "PHOTO_PATH";

    String currentPhotoPath;

    private static final String TAG = NavigationActivity.class.getSimpleName();

    @Extra
    String username;

    @ViewById
    TabLayout tabLayout;

    @ViewById
    ViewPager pager;

    @ViewById
    FloatingActionsMenu addMenu;

    @Bean
    FileUtils fileUtils;

    @ViewById
    com.getbase.floatingactionbutton.FloatingActionButton newMonument;

    @ViewById
    com.getbase.floatingactionbutton.FloatingActionButton newType;

    @ViewById
    com.getbase.floatingactionbutton.FloatingActionButton nearby;

    @Pref
    Preferences_ preferences;

    TabAdapter adapter;

    @AfterViews
    void setUpTabs() {
        adapter = new TabAdapter(this);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    @Click
    void newMonument() {
        addMenu.collapse();

        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            try {
                final File photoFile = fileUtils.createImage();
                currentPhotoPath = photoFile.getAbsolutePath();
                Log.d(TAG, currentPhotoPath);

                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    @Click
    void newType() {
        addMenu.collapse();

        NewTypeActivity_.intent(this).start();
    }

    @OnActivityResult(REQUEST_IMAGE_CAPTURE)
    void onResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, currentPhotoPath, Toast.LENGTH_SHORT).show();
            Log.d(TAG, currentPhotoPath);

            NewMonumentActivity_.intent(this).imagePath(currentPhotoPath).start();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(PHOTO_PATH, currentPhotoPath);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentPhotoPath = savedInstanceState.getString(PHOTO_PATH);
        }
    }

    @AfterInject
    void welcomeUser() {
        Toast.makeText(NavigationActivity.this, String.format("Welcome %s", username), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackPressed) {
            moveTaskToBack(true);
            super.onBackPressed();
            return;
        }

        doubleBackPressed = true;
        Toast.makeText(this, "Click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackPressed = false;
            }
        }, 2000);
    }

    @OptionsItem(R.id.item_logout)
    void logout() {
        preferences.id().remove();
        LoginActivity_.intent(this).start();
        finish();
    }

    @OptionsItem
    void nearby() {
        NearbyActivity_.intent(this).start();
    }
}
