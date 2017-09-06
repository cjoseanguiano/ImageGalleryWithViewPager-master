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

    private ArrayList<String> imageResource;
    private Bitmap bitmap;

    public static PageFragment getInstance(ArrayList<String> resourceID) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("image_source", resourceID);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageResource = getArguments().getStringArrayList("image_source");
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

        for (int i = 0; i < imageResource.size(); i++) {
            Uri uri = Uri.parse(imageResource.get(i));
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

}
