package devexchanges.info.imagegallerybyviewpager;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PageFragment extends Fragment {

    private String imageResource;
    private Bitmap bitmap;

    public static PageFragment getInstance(String resourceID) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putString("image_source", resourceID);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageResource = getArguments().getString("image_source");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewPager);

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        o.inSampleSize = 4;
        o.inDither = false;


        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("/storage/sdcard0/dcim/IMG_20170406_164424.jpg");
        arrayList.add("/storage/sdcard0/dcim/IMG_20170425_181009.jpg");

        for (int i = 0; i < arrayList.size(); i++) {
            Uri uri = Uri.parse(arrayList.get(i));
            Glide.with(this)
                    .load(new File(uri.getPath()))
                    .into(imageView);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bitmap.recycle();
        bitmap = null;
    }

    private static Bitmap getScaledBitmapFromUrl(Uri imageUrl, int requiredWidth, int requiredHeight) throws IOException {
        URL url = new URL(imageUrl.getPath());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
        options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
        options.inJustDecodeBounds = false;
        //don't use same inputstream object as in decodestream above. It will not work because
        //decode stream edit input stream. So if you create
        //InputStream is =url.openConnection().getInputStream(); and you use this in  decodeStream
        //above and bellow it will not work!
        Bitmap bm = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
        return bm;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

}
