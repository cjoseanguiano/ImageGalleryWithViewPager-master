package devexchanges.info.imagegallerybyviewpager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

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
        //Todo SAMSUNG
        //filePath = "/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20170723-WA0002.jpg";
        //Todo LEVONO
        filePath = "/storage/sdcard0/dcim/IMG_20170406_164424.jpg";

        File imgFile = new File(filePath);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            imageView.setImageBitmap(myBitmap);

        }

    }

}
