package com.example.alumnihub.utils;

import android.Manifest;
import android.app.Activity;
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

public class ImagePickerUtil {

    // Constant for permission request
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 100;

    // Method to request gallery access permission and open gallery if permission is granted
    public static void requestImageFromGallery(Activity activity, ActivityResultLauncher<Intent> launcher) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_STORAGE_PERMISSION);
        } else {
            // Permission granted, open the gallery
            openGallery(launcher);
        }
    }

    // Method to open the gallery
    private static void openGallery(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        launcher.launch(intent); // Launch the gallery activity
    }

    // Method to handle permission result, you need to call this in your activity's `onRequestPermissionsResult` method
    public static void handlePermissionsResult(int requestCode, int[] grantResults, Activity activity, ActivityResultLauncher<Intent> launcher) {
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open the gallery
                openGallery(launcher);
            } else {
                // Permission denied, you can notify the user here
                // For example, you can use a Snackbar or Toast to notify the user that the permission was denied
            }
        }
    }

    // Method to load image into ImageView using Picasso
    public static void loadImageIntoView(Activity activity, Uri imageUri, ImageView imageView) {
        Picasso.get()
                .load(imageUri)
                .placeholder(R.drawable.placeholder_image) // Optional placeholder image
                .into(imageView);
    }

    // Method to get the uploaded image URI
    public static Uri getUploadedImageUri(Intent data) {
        return data.getData();
    }

    // Method to get the file name from a Uri
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