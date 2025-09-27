package comp3350.smile.presentation;

import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import comp3350.smile.R;
import comp3350.smile.logic.ItemService;
import comp3350.smile.objects.Item;

public class SellItemActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 101;
    private static final int REQUEST_IMAGE_CAPTURE = 102;

    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private static final int REQUEST_STORAGE_PERMISSION = 202;

    // Views
    private ImageButton btnClose;
    private ConstraintLayout flImagePicker;
    private TextView tvAddImage;
    private ImageView ivPlaceholder;  // Added ImageView
    private EditText editTitle, editPrice, editDescription, paymentMode;
    private Spinner spinnerCondition, spinnerCategory;
    private Button btnCancel, btnSubmit;

    // Data
    private Uri selectedImageUri = null;
    private Uri imageUri;
    private ItemService itemService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_item);

        itemService = new ItemService();

        // Initialize views
        btnClose = findViewById(R.id.btnClose);
        flImagePicker = findViewById(R.id.flImagePicker);
        tvAddImage = findViewById(R.id.tvAddImage);
        ivPlaceholder = findViewById(R.id.ivPlaceholder);  // Initialize ImageView
        editTitle = findViewById(R.id.editTitle);
        editPrice = findViewById(R.id.editPrice);
        editDescription = findViewById(R.id.editDescription);
        spinnerCondition = findViewById(R.id.spinnerCondition);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        paymentMode = findViewById(R.id.editPaymentMode);
        btnCancel = findViewById(R.id.btnCancel);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Set click listeners for cancel actions
        btnClose.setOnClickListener(v -> finish());
        btnCancel.setOnClickListener(v -> finish());

        // Image Picker: Show chooser dialog when FrameLayout is clicked
        flImagePicker.setOnClickListener(v -> showImageChooserDialog());

        // Set up spinner adapters for condition and category
        String[] conditions = {"New", "Old", "N/A"};
        ArrayAdapter<String> conditionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, conditions);
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondition.setAdapter(conditionAdapter);

        String[] categories = {"Electronics", "Books", "Clothing", "Free", "Lost and Found", "Misc"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        // Submit button: Validate and add item
        btnSubmit.setOnClickListener(v -> {
            if (validateInputs()) {
                String title = editTitle.getText().toString().trim();
                double price = Double.parseDouble(editPrice.getText().toString().trim());
                String condition = spinnerCondition.getSelectedItem().toString();
                String description = editDescription.getText().toString().trim();
                String category = spinnerCategory.getSelectedItem().toString();
                String paymentModeText = paymentMode.getText().toString().trim();
                // Instead of using the URI string directly, we copy the image to our internal "drawable" folder.
                String imagePath = "";
                if (selectedImageUri != null) {
                    imagePath = copyImageToInternalDrawable(selectedImageUri);
                }

                // Create new Item. Adjust the constructor as per your Item class.
                Item newItem = new Item(0, title, description, category, condition, price, null, imagePath, paymentModeText);
                // For seller, you might need to get the current user.

                itemService.addItem(newItem);
                Toast.makeText(this, "Item added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private boolean validateInputs() {
        // test without this as there is no gallery and camera app here !
//        if (selectedImageUri == null) {
//            Toast.makeText(this, "Please add an image", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if (TextUtils.isEmpty(editTitle.getText().toString().trim())) {
            editTitle.setError("Title is required");
            return false;
        }
        if (TextUtils.isEmpty(editPrice.getText().toString().trim())) {
            editPrice.setError("Price is required");
            return false;
        }
        if (spinnerCondition.getSelectedItem() == null) {
            Toast.makeText(this, "Please select a condition", Toast.LENGTH_SHORT).show();
            return false;
        }
        String desc = editDescription.getText().toString().trim();
        if (TextUtils.isEmpty(desc)) {
            editDescription.setError("Description is required");
            return false;
        }
        if (desc.length() > 255) {
            editDescription.setError("Description must be 255 characters or less");
            return false;
        }
        if (spinnerCategory.getSelectedItem() == null) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(paymentMode.getText().toString().trim())) {
            paymentMode.setError("Payment mode is required");
            return false;
        }
        return true;
    }

    // New method: Show an AlertDialog to choose between Camera and Gallery
    private void showImageChooserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image Source");
        String[] options = {"Camera", "Gallery"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    openCamera();
                } else if (which == 1) {
                    openGallery();
                }
            }
        });
        builder.show();
    }

    // Opens the camera to capture an image
    private void openCamera() {
        // Check camera permission first
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            startCameraIntent();
        }
    }

    private void startCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            try {
                File photoFile = createImageFile();
                imageUri = FileProvider.getUriForFile(this,
                        "comp3350.smile.fileprovider",
                        photoFile);

                // Grant temporary read permission
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException | IllegalArgumentException e) {
                Toast.makeText(this, "Error creating file: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "No camera app found!", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle permission results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraIntent();
            } else {
                Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Opens the gallery to pick an image
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                selectedImageUri = data.getData();
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                selectedImageUri = imageUri;
            }

            if (selectedImageUri != null) {
                // Display the selected image in ImageView
                ivPlaceholder.setImageURI(selectedImageUri);
                tvAddImage.setVisibility(View.GONE);

                // Clear any existing background
                flImagePicker.setBackground(null);
            }
        }
    }

    /**
     * Copies the image from the given URI to an internal "drawable" folder.
     * This simulates storing the image like a resource.
     * Returns the absolute file path of the copied image.
     */
    // Add this constant at the top

    // Modify copyImageToInternalDrawable()
    private String copyImageToInternalDrawable(Uri sourceUri) {
        String destinationPath = "";
        String IMAGE_DIRECTORY = getString(R.string.image_directory);
        try {
            // Create app-specific directory
            File directory = new File(getFilesDir(), IMAGE_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Create unique filename
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String fileName = "item_" + timeStamp + ".jpg";
            File destFile = new File(directory, fileName);

            // Copy the file
            InputStream in = getContentResolver().openInputStream(sourceUri);
            OutputStream out = new FileOutputStream(destFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();

            // Return relative path
            destinationPath = IMAGE_DIRECTORY + File.separator + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
        }
        return destinationPath;
    }
}
