package com.softdesign.devintensive.ui.activities;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.manager.DataManager;
import com.softdesign.devintensive.utils.ConstantManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = ConstantManager.TAG_PREFIX + "Main Activity";
    private int mCurrentEditMode = 0;
    protected EditText mEditText;
    protected Button mRedBtn, mGreenBtn;
    protected int mColorMode;
    private ImageView mCallImg;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private FloatingActionButton mFab;
    private EditText mUserPhone, mUserMail, mUserVk, mUserGit, mUserBio;
    private List<EditText> mUserInfo;
    private DataManager mDataManager;
    private RelativeLayout mProfilePlaceholder;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout.LayoutParams mAppbarParams=null;
    private AppBarLayout mAppBarLayout;
    private File photoFile=null;
    private Uri mSelectedImage=null;
    private ImageView mProfileImage;
    private ImageView mUserPhoneCall;
    private ImageView mSendMail;
    private ImageView mVkProfile;
    private ImageView mGitProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate");

        mDataManager = DataManager.getInstance();
        mCallImg = (ImageView) findViewById(R.id.call_img);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container );
        mCallImg.setOnClickListener(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mFab = (FloatingActionButton) findViewById(R.id.float_action_button);
        mUserPhone = (EditText) findViewById(R.id.phone_number);
        mUserMail = (EditText) findViewById(R.id.email);
        mUserVk = (EditText) findViewById(R.id.vk_profile);
        mUserGit = (EditText) findViewById(R.id.git_profile);
        mUserBio = (EditText) findViewById(R.id.about_me);
        mProfilePlaceholder = (RelativeLayout) findViewById(R.id.profile_placeholder_rl);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collpsing_toolbar);
        mAppBarLayout = (AppBarLayout)findViewById(R.id.appBar_layout);
        mProfileImage = (ImageView) findViewById(R.id.user_foto_img);
        mUserPhoneCall = (ImageView) findViewById(R.id.user_phone_img);
        mSendMail = (ImageView) findViewById(R.id.mail_send_img);
        mVkProfile = (ImageView) findViewById(R.id.vk_profile_img);
        mGitProfile = (ImageView) findViewById(R.id.git_profile_img);




        mUserInfo = new ArrayList();
        mUserInfo.add(mUserPhone);
        mUserInfo.add(mUserMail);
        mUserInfo.add(mUserVk);
        mUserInfo.add(mUserGit);
        mUserInfo.add(mUserBio);

        mFab.setOnClickListener(this);
        mProfilePlaceholder.setOnClickListener(this);
        mUserPhoneCall.setOnClickListener(this);
        mSendMail.setOnClickListener(this);
        mVkProfile.setOnClickListener(this);
        mGitProfile.setOnClickListener(this);

        setupToolbar();
        setupDrawer();
        //saveUserInfoValue();
        loadUserInfoValue();
        Picasso.with(this).load(mDataManager.getPreferenceManager().loadUserPhoto()).placeholder(R.drawable.layout_image).into(mProfileImage);



        if (savedInstanceState==null){
            //активити было запущено впервые
            loadUserInfoValue();
            showSnackBar("активити запущена впервые");
        }else{
            mCurrentEditMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(mCurrentEditMode);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            mNavigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"onRestart");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.float_action_button:
                if(mCurrentEditMode==0){
                    changeEditMode(1);
                    mCurrentEditMode =1;
                }else {
                    changeEditMode(0);
                    mCurrentEditMode = 0;
                }
                break;
            case R.id.profile_placeholder_rl:
                showDialog(ConstantManager.LOAD_PROFILE_PHOTO);
                break;
            case R.id.user_phone_img:
                Intent userPhoneCallIntene = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mUserPhone.getText()));
                startActivity(userPhoneCallIntene);
                break;
            case R.id.mail_send_img:
                Intent mailIntent = new Intent(Intent.ACTION_SEND);
                mailIntent.setType("text/plain");
                mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {String.valueOf(mUserMail.getText())});
                startActivity(Intent.createChooser(mailIntent, "Sharing something."));
                break;
            case R.id.vk_profile_img:
                Intent vkProfileIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(mUserVk.getText())));
                startActivity(vkProfileIntent);
            case R.id.git_profile_img:
                Intent gitProfileIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + String.valueOf(mUserGit.getText())));
                startActivity(gitProfileIntent);


        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);
    }

    private void runWithDelay(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();
            }
        }, 5000);

    }

    private void showSnackBar(String message){
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    /*
    *Устанавилвает тулбар
     */
    public void setupToolbar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        mAppbarParams = (AppBarLayout.LayoutParams) mCollapsingToolbarLayout.getLayoutParams();
        if (actionBar!=null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setupDrawer(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackBar(item.getTitle().toString());
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    /**
     * Получение результата из другой активити(фото из камеры или галереи)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ConstantManager.REQUEST_GALLERY_PICTURE:
                if(resultCode == RESULT_OK && data!=null){
                    mSelectedImage = data.getData();
                    insertProfileImage(mSelectedImage);
                }
                break;
            case ConstantManager.REQUEST_CAMERA_PICTURE:
                if(resultCode == RESULT_OK && photoFile!=null){
                    mSelectedImage = Uri.fromFile(photoFile);
                    insertProfileImage(mSelectedImage);
                }
                break;
        }

    }

    /**
     * Переключение режима редактирования
     * @param mode если 1 то режим редактирования если 0 о режим просмотра
     */
    private void changeEditMode(int mode){
        if(mode == 1) {
            mFab.setImageResource(R.drawable.ic_done_black_24dp);
            for (EditText userValue : mUserInfo){
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
                showProfilePlaceholder();
                lockToolbar();
                mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
            }
        }else {
            mFab.setImageResource(R.drawable.ic_create_black_24dp);
            for (EditText userValue : mUserInfo){
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);
                saveUserInfoValue();
                hideProfilePlaceholder();
                unlockToolbar();
                mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        }
    }
    }

    private void loadUserInfoValue(){
        List<String> userData = mDataManager.getPreferenceManager().loadUserProfileData();
        for (int i = 0; i<userData.size(); ++i){
            mUserInfo.get(i).setText(userData.get(i));
        }
    }

    private void saveUserInfoValue(){
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView  : mUserInfo) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferenceManager().saveUserProfileData(userData);
    }

    private void loadFotoFromGellery(){
        Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeGalleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(takeGalleryIntent, getString(R.string.user_profile_photo_choose_messanger)),ConstantManager.REQUEST_GALLERY_PICTURE);
    }

    private void loadFotoFromCamera() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            Intent takeCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                // TODO: 08.07.2016 Обработать ошибку
            }

            if(photoFile!=null){
                // TODO: 08.07.2016 Передать фотофайл в интент
                takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takeCaptureIntent, ConstantManager.REQUEST_CAMERA_PICTURE);
            }
        }else {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    ConstantManager.CAMERA_REQUEST_PERMISSION_CODE);
            Snackbar.make(mCoordinatorLayout, "Для корректной работы неоходимо дать разрешения", Snackbar.LENGTH_LONG)
                    .setAction("Разрешить", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openApplicationsSettings();
                        }
                    }).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==ConstantManager.CAMERA_REQUEST_PERMISSION_CODE && grantResults.length==2){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // TODO: 08.07.2016 обработка разрешения 
            }
        }
        
        if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
            // TODO: 08.07.2016
        }
    }

    private void hideProfilePlaceholder(){
        mProfilePlaceholder.setVisibility(View.GONE);
    }

    private void showProfilePlaceholder(){
        mProfilePlaceholder.setVisibility(View.VISIBLE);
    }

    private void lockToolbar(){
        mAppBarLayout.setExpanded(true, true);
        mAppbarParams.setScrollFlags(0);
        mCollapsingToolbarLayout.setLayoutParams(mAppbarParams);
    }

    private void unlockToolbar(){
        mAppbarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL| AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        mCollapsingToolbarLayout.setLayoutParams(mAppbarParams);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case ConstantManager.LOAD_PROFILE_PHOTO:
                String[] selectItem = {getString(R.string.user_profile_dialog_gallery), getString(R.string.user_profile_dialog_photo), getString(R.string.user_profiile_dialog_cancel)};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.user_profile_dialog_title));
                builder.setItems(selectItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choiseItem) {
                        switch (choiseItem){
                            case 0:
                                // TODO Загрузить из галереи
                                loadFotoFromGellery();
                                showSnackBar("Загрузить из галереи");
                                break;

                            case 1:
                                // TODO Загрузить из фотокамеры
                                loadFotoFromCamera();
                               // showSnackBar("Загрузить из фотокамеры");
                                break;

                            case 2:
                                // TODO ОТмена загузки
                                dialog.cancel();
                                showSnackBar("ОТмена загузки");
                                break;

                        }
                    }
                });
                return builder.create();

            default:
                return null;
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File stroageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg",stroageDir);

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        values.put(MediaStore.MediaColumns.DATA, image.getAbsolutePath());

        this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        return image;
    }

    private void insertProfileImage(Uri mSelectedImage) {
        Picasso.with(this).load(mSelectedImage).into(mProfileImage);

        mDataManager.getPreferenceManager().saveUserPhoto(mSelectedImage);
    }

    private void openApplicationsSettings(){
        Intent appSettingIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingIntent, ConstantManager.PERMISSION_REQUEST_SETTINGS_CODE);
    }


}
