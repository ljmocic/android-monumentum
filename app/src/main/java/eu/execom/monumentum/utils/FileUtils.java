package eu.execom.monumentum.utils;

import android.os.Environment;
import android.util.Log;

import org.androidannotations.annotations.EBean;

import java.io.File;
import java.io.IOException;


@EBean
public class FileUtils {

    public File createImage() throws IOException {
        final String timeStamp = String.valueOf(System.currentTimeMillis());
        final String imageFileName = "JPEG_" + timeStamp + "_";
        Log.d("imageFileName", imageFileName);

        final File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        final File monumentumStorageDir = new File(String.format("%s/Monumentum", storageDir.getAbsolutePath()));
        monumentumStorageDir.mkdir();

        return File.createTempFile(imageFileName, ".jpg", monumentumStorageDir);
    }
}
