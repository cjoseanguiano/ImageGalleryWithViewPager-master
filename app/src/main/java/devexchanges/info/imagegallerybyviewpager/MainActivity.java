package devexchanges.info.imagegallerybyviewpager;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Created by Gerardo.Robledo on 27/07/2016.
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_PHOTO_PATH = "extra_photo_path";
    public static final String EXTRA_PHOTO_ORIENTATION = "extra_photo_orientation";
    public static final String EXTRA_PHOTO_EDIT = "extra_put_edit";
    public static final String EXTRA_MEDIA_PATHS = "extra_media_paths";
    public static final String EXTRA_MEDIA_PATHS_VIDEO = "extra_media_paths_video";
    private static final String EXTRA_TYPE_BUCKET = "extra_type_bucket";
    private static final String EXTRA_TYPE_FILE = "extra_type_file";
    public static final String EXTRA_ONE = "extra_one";

    private static final String CURRENTx = "duration";
    private static final String URLx = "url";

    private EditText photoDescription;
    private ImageView imgDisplay;
    private boolean putDescription;
    private int orientation;
    private String localPath;
    private ViewPager viewPager;
    private PhotoViewerAdapter adapter;
    private ArrayList<String> mImagePaths = new ArrayList<>();
    private ImageView imageView;
    boolean iconRemove = false;
    private String newPath;
    private ImageView addPicture;
    private Bundle bundle;
    ArrayList<String> paths = new ArrayList<>();
    private String typeBucket;
    private String typeFile;
    private int currentPage;
    private int position;
    private VideoView videoView;
    MediaController mediaController;
    private FloatingActionButton send;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        bundle = getIntent().getExtras();
        paths.add("/storage/sdcard0/dcim/IMG_20170406_164430.jpg");
        paths.add("/storage/sdcard0/dcim/IMG_20170426_132831.jpg");

        addPicture = (ImageView) findViewById(R.id.imageView5);
        send = (FloatingActionButton) findViewById(R.id.fab);

        typeBucket = getIntent().getStringExtra(EXTRA_TYPE_BUCKET);
        typeFile = getIntent().getStringExtra(EXTRA_TYPE_FILE);
        videoView = (VideoView) findViewById(R.id.videoViewx);
        ClipData clipData = getIntent().getClipData();

            if (getIntent().hasExtra(EXTRA_PHOTO_PATH)) {
                localPath = bundle.getString(EXTRA_PHOTO_PATH);
                mImagePaths.add(localPath);
                orientation = bundle.getInt(EXTRA_PHOTO_ORIENTATION);
            } else if (paths != null) {
                mImagePaths = paths;
            } else if (getIntent().hasExtra(EXTRA_MEDIA_PATHS_VIDEO)) {
                iconRemove = true;
                addPicture.setVisibility(View.INVISIBLE);
                mImagePaths = bundle.getStringArrayList(EXTRA_MEDIA_PATHS_VIDEO);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < mImagePaths.size(); i++) {
                    stringBuilder.append(mImagePaths.get(i));
                }
                newPath = stringBuilder.toString();
                Bitmap bmThumbnail = ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(newPath,
                        MediaStore.Video.Thumbnails.MINI_KIND), 200, 200);
                createImageViewNew();
                imageView.setImageBitmap(bmThumbnail);

            } else if (paths != null) {
                mImagePaths = paths;
            }


        if (savedInstanceState != null) {
            localPath = savedInstanceState.getString(EXTRA_PHOTO_PATH);
            putDescription = savedInstanceState.getBoolean(EXTRA_PHOTO_EDIT);
            orientation = savedInstanceState.getInt(EXTRA_PHOTO_ORIENTATION);
        }


        adapter = new PhotoViewerAdapter(this, mImagePaths);
        imgDisplay = (ImageView) findViewById(R.id.imgDisplay);
        photoDescription = (EditText) findViewById(R.id.photoDescription);

        disableEditText(photoDescription);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mImagePaths.size(); i++) {
            stringBuilder.append(mImagePaths.get(i));
        }
        newPath = stringBuilder.toString();

        if (newPath.contains(".jpg") || (newPath.contains(".png")) || (newPath.contains(".jpeg"))) {
            createViewPager();
        }

        if (putDescription && mImagePaths.size() == 1) {
            photoDescription.setVisibility(View.VISIBLE);
        }

//        Button cancel = (Button) findViewById(R.id.button);
        send = (FloatingActionButton) findViewById(R.id.fab);

//         cancel.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 cancelSend();
//             }
//         });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPhoto();

            }
        });

        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPicture();
            }
        });

    }


    public void createImageViewNew() {
        FrameLayout frameLayoutV = (FrameLayout) findViewById(R.id.fragmentContainer);
        imageView = new ImageView(this);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT);
        imageView.setId(R.id.viewImageViewNew);
        imageView.setLayoutParams(params);
        frameLayoutV.addView(imageView);

    }

    private void methodVisible() {

        if (send.getVisibility() == View.VISIBLE) {
            send.setVisibility(View.INVISIBLE);
        } else {
            send.setVisibility(View.VISIBLE);
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void sendPhoto() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("photoDescription", photoDescription.getText().toString());
        returnIntent.putStringArrayListExtra("paths", mImagePaths);
        returnIntent.putExtra("orientation", orientation);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void addPicture() {
        Log.i(TAG, "addPicture: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_PHOTO_EDIT, putDescription);
        outState.putInt(EXTRA_PHOTO_ORIENTATION, orientation);
        outState.putString(EXTRA_PHOTO_PATH, localPath);
        outState.putInt(EXTRA_PHOTO_ORIENTATION, orientation);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK && mImagePaths.size() != 0) {
//
//                try {
//                    viewPager.setAdapter(null);
//                    Uri resultUri = result.getUri();
//                    String newPath = resultUri.getPath();
//                    mImagePaths.remove(position);
//                    mImagePaths.add(newPath);
//                    adapter = new PhotoViewerAdapter(this, mImagePaths);
//                    createViewPager();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Logger.e("" + result.getError());
//            }
//        }
//        if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
//            viewPager.setAdapter(null);
//            paths = data.getExtras().getStringArrayList(EXTRA_RESULT_SELECTED_MEDIA);
//            if (paths != null) {
//                mImagePaths = paths;
//
//                StringBuilder stringBuilder = new StringBuilder();
//                for (int i = 0; i < mImagePaths.size(); i++) {
//                    stringBuilder.append(mImagePaths.get(i));
//                }
//                String newString = stringBuilder.toString();
//                if (newString.contains(".jpg") || (newString.contains(".png")) || (newString.contains(".jpeg"))) {
//                    adapter = new PhotoViewerAdapter(this, paths);
//                    createViewPager();
//                }
//            }
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (!iconRemove) {
            menu.clear();
            MenuItem item = menu.add(Menu.NONE, R.id.crop_image_menu_crop, Menu.NONE, R.string.crop_image_menu_crop);
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            item.setIcon(R.drawable.crop);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.crop_image_menu_crop:
                Log.i(TAG, "onOptionsItemSelected: ");
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void cancelSend() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void createVideoViewNew() {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragmentContainer);
        videoView = new VideoView(this);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT);
        params.anchorGravity = Gravity.BOTTOM;
        params.gravity = Gravity.BOTTOM;
        videoView.setId(R.id.videoFile);
        videoView.setLayoutParams(params);
        frameLayout.addView(videoView);
    }

    public void createViewPager() {
        FrameLayout frameLayoutI = (FrameLayout) findViewById(R.id.fragmentContainer);
        viewPager = new ViewPager(this);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT);
        viewPager.setId(R.id.viewPagerNew);
        viewPager.setLayoutParams(params);
        frameLayoutI.addView(viewPager);
        viewPager.setAdapter(adapter);
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setTextColor(Color.TRANSPARENT);
        editText.setHintTextColor(Color.TRANSPARENT);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentPage = position;

    }

    @Override
    public void onPageSelected(int position) {
        currentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

