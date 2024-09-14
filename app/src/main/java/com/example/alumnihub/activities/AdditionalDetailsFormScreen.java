package com.example.alumnihub.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.alumnihub.R;
import com.example.alumnihub.backend_services.firebase_auth.AuthServices;
import com.example.alumnihub.backend_services.firebase_storage.StorageServices;
import com.example.alumnihub.backend_services.firestore_db.ApplicationServicesDB;
import com.example.alumnihub.backend_services.firestore_db.UserServicesDB;
import com.example.alumnihub.data_models.Application;
import com.example.alumnihub.utils.ImagePickerUtil;
import com.example.alumnihub.utils.ValidationUtils;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdditionalDetailsFormScreen extends AppCompatActivity {

    private Spinner yearOfStudySpinner, graduationYearSpinner;
    private RadioGroup typeRadioGroup;
    private TextView profilePicName, idProofName;
    private EditText fullName, enrollmentNum, contactNum, currentLocation, domain, bio, workplace;
    private ImageButton uploadPfpButton, uploadIdProofButton;
    private TextView yearOfStudyLabel, graduationYearLabel;
    private Button submitButton;
    private FirebaseUser currentUser;

    // Global variables
    private String type = null;
    private Uri pfpPicUri = null;
    private Uri idProofUri = null;

    // Variables to store URLs
    private String pfpPicUrl = null;
    private String idProofUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_details_form_screen);

        // Change the status bar color
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.bgSplashScreen));
        }

        // Set proper window view padding
        View rootView = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemInsets.left, systemInsets.top, systemInsets.right, systemInsets.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        AuthServices authService = new AuthServices();
        currentUser = authService.getCurrentUser();

        // Initialize all fields
        yearOfStudySpinner = findViewById(R.id.year_of_study_spinner);
        graduationYearSpinner = findViewById(R.id.graduation_year_spinner);
        typeRadioGroup = findViewById(R.id.type_radio_group);
        profilePicName = findViewById(R.id.profile_pic_name);
        idProofName = findViewById(R.id.id_proof_name);
        fullName = findViewById(R.id.full_name);
        enrollmentNum = findViewById(R.id.enrollment_num);
        contactNum = findViewById(R.id.contact_num);
        currentLocation = findViewById(R.id.current_location);
        domain = findViewById(R.id.domain);
        bio = findViewById(R.id.bio);
        workplace = findViewById(R.id.workplace);
        uploadPfpButton = findViewById(R.id.upload_profile_pic_btn);
        uploadIdProofButton = findViewById(R.id.upload_id_proof_btn);
        yearOfStudyLabel = findViewById(R.id.year_of_study_label);
        graduationYearLabel = findViewById(R.id.graduation_year_label);
        submitButton = findViewById(R.id.submit_btn);

        // Populate spinners
        populateYearOfStudySpinner();
        populateGraduationYearSpinner();

        // Set RadioGroup listener
        setRadioGroupListener();

        // Set click listeners for upload buttons
        setUploadButtonListeners();

        // Set click listener for submit button
        submitButton.setOnClickListener(v -> handleSubmit());
    }

    private void populateYearOfStudySpinner() {
        List<String> yearOfStudyOptions = new ArrayList<>();
        yearOfStudyOptions.add("N/A"); // Default item
        for (int i = 1; i <= 5; i++) {
            yearOfStudyOptions.add("Year " + i);
        }
        ArrayAdapter<String> yearOfStudyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearOfStudyOptions);
        yearOfStudyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearOfStudySpinner.setAdapter(yearOfStudyAdapter);
        yearOfStudySpinner.setSelection(0); // Set default selection
    }

    private void populateGraduationYearSpinner() {
        List<String> graduationYearOptions = new ArrayList<>();
        graduationYearOptions.add("N/A"); // Default item
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= currentYear; i++) {
            graduationYearOptions.add(String.valueOf(i));
        }
        ArrayAdapter<String> graduationYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, graduationYearOptions);
        graduationYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        graduationYearSpinner.setAdapter(graduationYearAdapter);
        graduationYearSpinner.setSelection(0); // Set default selection
    }

    private void setRadioGroupListener() {
        typeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Log.d("RadioGroup", "Checked ID: " + checkedId);
            if (checkedId == R.id.radio_student) {
                type = "student";
                yearOfStudySpinner.setVisibility(View.VISIBLE);
                yearOfStudyLabel.setVisibility(View.VISIBLE);
                graduationYearSpinner.setVisibility(View.GONE);
                graduationYearLabel.setVisibility(View.GONE);
                workplace.setVisibility(View.GONE);
            } else if (checkedId == R.id.radio_alumni) {
                type = "alumni";
                yearOfStudySpinner.setVisibility(View.GONE);
                yearOfStudyLabel.setVisibility(View.GONE);
                graduationYearSpinner.setVisibility(View.VISIBLE);
                graduationYearLabel.setVisibility(View.VISIBLE);
                workplace.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setUploadButtonListeners() {
        ActivityResultLauncher<Intent> pfpLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        pfpPicUri = ImagePickerUtil.getUploadedImageUri(result.getData());
                        String fileName = ImagePickerUtil.getFileName(this, pfpPicUri);
                        profilePicName.setText(fileName);
                    }
                });

        ActivityResultLauncher<Intent> idProofLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        idProofUri = ImagePickerUtil.getUploadedImageUri(result.getData());
                        String fileName = ImagePickerUtil.getFileName(this, idProofUri);
                        idProofName.setText(fileName);
                    }
                });

        uploadPfpButton.setOnClickListener(v -> ImagePickerUtil.requestImageFromGallery(this, pfpLauncher));
        uploadIdProofButton.setOnClickListener(v -> ImagePickerUtil.requestImageFromGallery(this, idProofLauncher));
    }

    private void handleSubmit() {
        if (type == null) {
            showToast("Please select a type (student or alumni).");
            return;
        }

        if (idProofUri == null) {
            showToast("Please upload an ID proof.");
            return;
        }

        String fullNameValue = fullName.getText().toString().trim();
        String enrollmentNumValue = enrollmentNum.getText().toString().trim();
        String contactNumValue = contactNum.getText().toString().trim();
        String currentLocationValue = currentLocation.getText().toString().trim();
        String domainValue = domain.getText().toString().trim();
        String bioValue = bio.getText().toString().trim();
        String workplaceValue = workplace.getText().toString().trim();
        String yearOfStudyValue = yearOfStudySpinner.getSelectedItem().toString();
        String graduationYearValue = graduationYearSpinner.getSelectedItem().toString();

        StorageServices storageServices = new StorageServices();
        String pfpPicPath = "/profile_pics/" + currentUser.getUid();
        String idProofPath = "/id_proofs/" + currentUser.getUid();

        // Upload profile picture if available
        Task<Uri> pfpUploadTask = Tasks.forResult(null);
        if (pfpPicUri != null) {
            pfpUploadTask = storageServices.uploadFile(pfpPicUri, pfpPicPath)
                    .continueWithTask(task -> {
                        if (task.isSuccessful()) {
                            return storageServices.getDownloadUrl(pfpPicPath);
                        } else {
                            throw task.getException();
                        }
                    });
        }

        // Upload ID proof
        Task<Uri> idProofUploadTask = storageServices.uploadFile(idProofUri, idProofPath)
                .continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        return storageServices.getDownloadUrl(idProofPath);
                    } else {
                        throw task.getException();
                    }
                });

        // Wait for both uploads to complete
        Tasks.whenAllSuccess(pfpUploadTask, idProofUploadTask).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Object> results = task.getResult();
                pfpPicUrl = results.get(0) != null ? results.get(0).toString() : null;
                idProofUrl = results.get(1).toString();

                // Validate the inputs
                if (!validateInputs(fullNameValue, enrollmentNumValue, contactNumValue, domainValue, bioValue,
                        workplaceValue, yearOfStudyValue, graduationYearValue, idProofUrl, type)) {
                    // If validation fails, return early
                    return;
                }

                // Update user and create application entry
                UserServicesDB userServicesDB = new UserServicesDB();
                userServicesDB.getUser(currentUser.getUid())
                        .addOnSuccessListener(user -> {
                            if (user != null) {
                                // Set the fields
                                user.setType(type);
                                user.setFullName(fullNameValue);
                                user.setEnrollmentNum(enrollmentNumValue);
                                user.setContactNum(contactNumValue);
                                user.setCurrentLocation(currentLocationValue);
                                user.setDomain(domainValue);
                                user.setBio(bioValue);
                                user.setWorkplace(workplaceValue);
                                user.setYearOfStudy(yearOfStudyValue);
                                user.setGraduationYear(graduationYearValue);
                                user.setPfPicUrl(pfpPicUrl);
                                user.setIdProofUrl(idProofUrl);
                                user.setIsComplete(true);

                                // Update the user in Firestore
                                userServicesDB.updateUser(currentUser.getUid(), user)
                                        .addOnSuccessListener(aVoid -> {
                                            // Create a new application entry
                                            newApplicationEntry(enrollmentNumValue, idProofUrl)
                                                    .addOnSuccessListener(aVoid1 -> {
                                                        // Navigate to MainActivity on success
                                                        startActivity(new Intent(AdditionalDetailsFormScreen.this, MainActivity.class));
                                                        finish();
                                                        Log.d("UserUpdate", "User and application updated successfully.");
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Log.e("ApplicationUpdateError", "Error adding application", e);
                                                    });
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e("UserUpdateError", "Error updating user", e);
                                        });
                            } else {
                                Log.e("UserFetchError", "User not found.");
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.e("UserFetchError", "Error fetching user", e);
                        });
            } else {
                Log.e("Storage", "Failed to upload files", task.getException());
            }
        });
    }

    // Method to validate all inputs
    private boolean validateInputs(String fullNameValue, String enrollmentNumValue, String contactNumValue,
                                   String domainValue, String bioValue, String workplaceValue,
                                   String yearOfStudyValue, String graduationYearValue,
                                   String idProofUrl, String type) {

        // Validate full name
        if (!ValidationUtils.isValidFullName(fullNameValue)) {
            showToast("Please enter a valid full name.");
            return false;
        }

        // Validate enrollment number
        if (!ValidationUtils.isValidEnrollmentNum(enrollmentNumValue)) {
            showToast("Please enter a valid enrollment number.");
            return false;
        }

        // Validate contact number
        if (!ValidationUtils.isValidContactNum(contactNumValue)) {
            showToast("Please enter a valid contact number.");
            return false;
        }

        // Validate domain
        if (!ValidationUtils.isValidDomain(domainValue)) {
            showToast("Please enter a valid domain.");
            return false;
        }

        // Validate bio
        if (!ValidationUtils.isValidBio(bioValue)) {
            showToast("Please enter a valid bio.");
            return false;
        }

        // Alumni-specific validations
        if (type.equals("alumni")) {
            // Validate workplace for alumni
            if (!ValidationUtils.isValidWorkplace(workplaceValue)) {
                showToast("Please enter a valid workplace.");
                return false;
            }

            // Validate graduation year for alumni
            if (!ValidationUtils.isValidGraduationYear(graduationYearValue)) {
                showToast("Please select a valid graduation year.");
                return false;
            }
        }

        // Student-specific validations
        if (type.equals("student")) {
            // Validate year of study for students
            if (!ValidationUtils.isValidYearOfStudy(yearOfStudyValue)) {
                showToast("Please select a valid year of study.");
                return false;
            }
        }

        // Validate ID proof URL
        if (!ValidationUtils.isValidIdProofUrl(idProofUrl)) {
            showToast("Please enter a valid ID proof URL.");
            return false;
        }

        // If all validations pass, return true
        return true;
    }


    // Method to create a new application entry
    private Task<Void> newApplicationEntry(String enrollmentNumValue, String idProofUrl) {
        ApplicationServicesDB applicationServicesDB = new ApplicationServicesDB();

        // Create a new Application object with a unique ID
        Application application = new Application(
                currentUser.getUid(),
                enrollmentNumValue,
                idProofUrl,
                false,
                currentUser.getUid()
        );

        // Add the application to Firestore
        return applicationServicesDB.addApplication(application);
    }

    // Method to show a toast message
    private void showToast(String message) {
        Toast.makeText(AdditionalDetailsFormScreen.this, message, Toast.LENGTH_SHORT).show();
    }
}