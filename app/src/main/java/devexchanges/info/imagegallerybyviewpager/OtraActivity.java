package devexchanges.info.imagegallerybyviewpager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class OtraActivity extends AppCompatActivity {

    private ImageView imageView;
    private String filePath;
    private String imageName;
    private NinePatch parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otra);
        imageView = (ImageView) findViewById(R.id.imagenula);
        filePath = "/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20170723-WA0002.jpg";

        File imgFile = new File(filePath);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            imageView.setImageBitmap(myBitmap);

        }

    }

}
