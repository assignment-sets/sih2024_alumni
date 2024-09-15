package com.example.alumnihub.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.alumnihub.R;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class ImagePickerUtil {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 100;

    /**
     * Requests permission to access the gallery and opens the gallery if permission is granted.
     *
     * @param activity The activity from which the request is made.
     * @param launcher The launcher to start the gallery intent.
     */
    public static void requestImageFromGallery(Activity activity, ActivityResultLauncher<Intent> launcher) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_STORAGE_PERMISSION);
        } else {
            openGallery(launcher);
        }
    }

    /**
     * Opens the gallery to select an image.
     *
     * @param launcher The launcher to start the gallery intent.
     */
    private static void openGallery(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        launcher.launch(intent);
    }

    /**
     * Handles the result of the permission request.
     *
     * @param requestCode  The request code of the permission request.
     * @param grantResults The results of the permission request.
     * @param activity     The activity from which the request is made.
     * @param launcher     The launcher to start the gallery intent.
     */
    public static void handlePermissionsResult(int requestCode, int[] grantResults, Activity activity, ActivityResultLauncher<Intent> launcher) {
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery(launcher);
            } else {
                // Notify the user that the permission was denied
            }
        }
    }

    /**
     * Loads an image into an ImageView from a Uri.
     *
     * @param activity  The activity context.
     * @param imageUri  The Uri of the image to load.
     * @param imageView The ImageView into which the image will be loaded.
     */
    public static void loadImageIntoView(Activity activity, Uri imageUri, ImageView imageView) {
        if (imageUri == null) {
            Picasso.get()
                    .load(R.drawable.placeholder_image)
                    .into(imageView);
        } else {
            Picasso.get()
                    .load(imageUri)
                    .placeholder(R.drawable.img_placeholder_2)
                    .into(imageView);
        }
    }

    /**
     * Loads an image into an ImageView from a URL.
     *
     * @param context   The context.
     * @param imageUrl  The URL of the image to load.
     * @param imageView The ImageView into which the image will be loaded.
     */
    public static void loadImageIntoView(Context context, String imageUrl, ImageView imageView) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            Picasso.get()
                    .load(R.drawable.placeholder_image)
                    .into(imageView);
        } else {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.img_placeholder_2)
                    .into(imageView);
        }
    }

/**
 * Loads an image into a CircleImageView from a URL with a default user profile placeholder.
 *
 * @param context   The context.
 * @param imageUrl  The URL of the image to load.
 * @param imageView The CircleImageView into which the image will be loaded.
 */
public static void loadImageIntoUserProfile(Context context, String imageUrl, CircleImageView imageView) {
    if (imageUrl == null || imageUrl.isEmpty()) {
        Picasso.get()
                .load(R.drawable.ic_default_user_profile_pic)
                .into(imageView);
    } else {
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_default_user_profile_pic)
                .into(imageView);
    }
}

    /**
     * Retrieves the Uri of the uploaded image from the intent data.
     *
     * @param data The intent data.
     * @return The Uri of the uploaded image.
     */
    public static Uri getUploadedImageUri(Intent data) {
        return data.getData();
    }

    /**
     * Retrieves the file name of the image from its Uri.
     *
     * @param activity The activity context.
     * @param uri      The Uri of the image.
     * @return The file name of the image.
     */
    public static String getFileName(Activity activity, Uri uri) {
        String fileName = null;
        if (uri != null) {
            Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                try {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1 && cursor.moveToFirst()) {
                        fileName = cursor.getString(nameIndex);
                    }
                } finally {
                    cursor.close();
                }
            }
        }
        return fileName;
    }
}