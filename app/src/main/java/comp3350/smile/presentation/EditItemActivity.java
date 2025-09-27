package comp3350.smile.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import comp3350.smile.R;
import comp3350.smile.logic.ItemService;
import comp3350.smile.objects.Item;

public class EditItemActivity extends AppCompatActivity {

    private EditText editProductName, editProductPrice, editProductDescription, editProductCondition;
    private TextView categoryLabel, productPaymentMode;
    private ImageView productImage, btnClose, categoryImg;
    private Button btnUpdate, btnDelete;
    private Item currentItem;
    private ItemService itemService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemService = new ItemService();

        // Initialize UI elements
        btnClose = findViewById(R.id.btnClose);
        editProductName = findViewById(R.id.editProductName);
        editProductPrice = findViewById(R.id.editProductPrice);
        editProductDescription = findViewById(R.id.editProductDescription);
        editProductCondition = findViewById(R.id.editProductCondition);
        categoryLabel = findViewById(R.id.categoryLabel);
        productPaymentMode = findViewById(R.id.editPaymentMode);
        productImage = findViewById(R.id.item_photo);
        categoryImg = findViewById(R.id.categoryImage);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        // Close button functionality
        btnClose.setOnClickListener(v -> finish());

        // Get the Item ID from the Intent
        int itemId = getIntent().getIntExtra("ITEM_ID", -1);
        Log.d("EditItemActivity", "Item ID: " + itemId);

        if (itemId != -1) {
            currentItem = itemService.getItemByID(itemId);
            if (currentItem != null) {
                populateItemDetails();
            } else {
                Log.e("EditItemActivity", "Item not found with ID: " + itemId);
                Toast.makeText(this, "Error loading item", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Log.e("EditItemActivity", "Invalid Item ID received.");
            Toast.makeText(this, "Invalid Item", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Update Button Click Listener
        btnUpdate.setOnClickListener(v -> updateItem());

        // Delete Button Click Listener
        btnDelete.setOnClickListener(v -> deleteItem());
    }

    private void populateItemDetails() {
        editProductName.setText(currentItem.getName());
        editProductPrice.setText(String.valueOf(currentItem.getPrice()));
        editProductDescription.setText(currentItem.getDescription());
        editProductCondition.setText(currentItem.getCondition());

        categoryLabel.setText("Category: " + currentItem.getCategory());
        productPaymentMode.setText( currentItem.getPaymentModes());

        // Load image from resource identifier
        String imgPath = currentItem.getImgPath();
        int resID = getResources().getIdentifier(imgPath, "drawable", getPackageName());
        if (resID != 0) {
            productImage.setImageResource(resID);
        } else {
            productImage.setImageResource(R.drawable.default_image);
        }

        int categoryResID = getCategoryImageResource(currentItem.getCategory());
        categoryImg.setImageResource(categoryResID);
    }

    private void updateItem() {
        String updatedName = editProductName.getText().toString().trim();
        String updatedPrice = editProductPrice.getText().toString().trim();
        String updatedDescription = editProductDescription.getText().toString().trim();
        String updatedCondition = editProductCondition.getText().toString().trim();

        if (updatedName.isEmpty() || updatedPrice.isEmpty() || updatedDescription.isEmpty() || updatedCondition.isEmpty()) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        currentItem.setName(updatedName);
        currentItem.setPrice(Double.parseDouble(updatedPrice));
        currentItem.setDescription(updatedDescription);
        currentItem.setCondition(updatedCondition);

        itemService.updateItem(currentItem);
        Toast.makeText(this, "Item updated successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteItem() {
        itemService.deleteItem(currentItem);
        Toast.makeText(this, "Item deleted!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private int getCategoryImageResource(String category) {
        if (category == null) return R.drawable.default_image;

        switch (category.toLowerCase()) {
            case "electronics":
                return R.drawable.electricity_icon;
            case "notes":
                return R.drawable.notes;
            case "free":
                return R.drawable.free;
            case "lost and found":
                return R.drawable.lost_and_found;
            case "books":
                return R.drawable.book_icon;
            case "clothing":
                return R.drawable.hanger_icon;
            case "house":
                return R.drawable.house_icon;
            default:
                return R.drawable.default_image;
        }
    }
}
