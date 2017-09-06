package devexchanges.info.imagegallerybyviewpager;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

class PhotoViewerAdapter extends PagerAdapter {
    private static final int MAX_WIDTH = 150;
    private static final int MAX_HEIGHT = 1280;

    private static int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
    private ArrayList<String> paths = new ArrayList<>();
    private final Activity _activity;
    private ArrayList<String> _imagePaths;

    @Override
    public int getItemPosition(Object object) {
        for (int i = 0; i < paths.size(); i++) {
            if (paths.get(i).equals(object))
                return i;
        }
        return 0;
    }

    // constructor
    public PhotoViewerAdapter(Activity activity,
                              ArrayList<String> imagePaths) {
        this._activity = activity;
        this.paths = imagePaths;

//        for (ChatMessage chatMessage:_imagePaths) {
//            MessageAttachment attachment = null;
//            if(chatMessage.getMessageType().equals(IMAGE)
//                    ||chatMessage.getMessageType().equals(VIDEO)){
//                paths.add(chatMessage);
//            }
//        }
    }

    @Nullable
    public String getCurrentItem(int position) {
        if (paths.size() > 0) {
            return paths.get(position);
        } else
            return null;
    }

    @Override
    public int getCount() {
        return this.paths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position + " of " + getCount();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final TouchImageView imgDisplay;

        ImageButton btnPlay;
        final ImageButton btnDownload;
        final ProgressBar progressBar;
        TextView messageTextView;

        LayoutInflater inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.item_fullscreen_image, container, false);

        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
        messageTextView = (TextView) viewLayout.findViewById(R.id.photoDescription);
        btnPlay = (ImageButton) viewLayout.findViewById(R.id.btnPlay);
        btnDownload = (ImageButton) viewLayout.findViewById(R.id.btnDownload);
        progressBar = (ProgressBar) viewLayout.findViewById(R.id.progressBar);

        messageTextView.setVisibility(View.GONE);
        btnPlay.setVisibility(View.GONE);
        btnDownload.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        String localPath = paths.get(position);

//        File thumbnailFile = AndroidUtilities.createImageThumbnail(localPath);

        Glide.with(_activity)
                .load(new File(localPath))
                .into(imgDisplay);
        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);

    }
}
