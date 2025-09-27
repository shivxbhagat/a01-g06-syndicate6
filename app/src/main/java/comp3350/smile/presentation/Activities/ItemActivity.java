package comp3350.smile.presentation.Activities;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import comp3350.smile.R;
import comp3350.smile.logic.ItemService;
import comp3350.smile.objects.Item;
import comp3350.smile.objects.User;
import comp3350.smile.objects.CategoryType;
import comp3350.smile.presentation.Adapters.RelatedItemsAdapter;
import comp3350.smile.presentation.PresentationConfig;

public class ItemActivity extends AppCompatActivity {

    private TextView productName, productPrice, categoryLabel, productDescription, productCondtion, productPaymentMode, productSellerName, productSellerEmail, productSellerContact;
    private ImageView productImage, btnClose, bookmarkIcon, categoryImg;
    private RecyclerView relatedItemsRecyclerView;
    private RelatedItemsAdapter relatedItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_layout);

        // Initialize the Close button
        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> finish());

        // Initialize the Bookmark icon (ensure your layout includes an ImageView with id "bookmarkIcon")
        bookmarkIcon = findViewById(R.id.btnBookmark);

        // Initialize UI elements
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        categoryLabel = findViewById(R.id.categoryLabel);
        productDescription = findViewById(R.id.productDescription);
        productCondtion = findViewById(R.id.productCondition);
        productImage = findViewById(R.id.item_photo);
        productPaymentMode = findViewById(R.id.paymentModeValue);
        productSellerName = findViewById(R.id.sellerName);
        productSellerContact = findViewById(R.id.sellerContact);
        productSellerEmail = findViewById(R.id.sellerEmail);
        categoryImg = findViewById(R.id.categoryImage);

        relatedItemsRecyclerView = findViewById(R.id.relatedItemsRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);  // 2 columns
        relatedItemsRecyclerView.setLayoutManager(gridLayoutManager);

        // Get the Item ID from the Intent
        int parsedItemId = getIntent().getIntExtra("ITEM_ID", -1);
        Log.d("ItemActivity", "Item ID: " + parsedItemId);

        if (parsedItemId != -1) {
            ItemService service = new ItemService();
            Item item = service.getItemByID(parsedItemId);
            if (item != null) {
                User productSeller = item.getSeller();

                // Populate the UI with the item details
                productName.setText(item.getName());
                productPrice.setText(String.format("$%.2f", item.getPrice()));
                categoryLabel.setText("Category: " + item.getCategory());

                productDescription.setText(item.getDescription());
                productCondtion.setText("Condition: " + item.getCondition());
                productPaymentMode.setText(item.getPaymentModes());
                productSellerName.setText(productSeller.getFullName());
                productSellerEmail.setText("Email: " + productSeller.getEmail());
                productSellerContact.setText("Contact: " + productSeller.getPhone());


                // Load image from resource identifier based on image path
                String imgPath = item.getImgPath();

                if (imgPath != null && imgPath.startsWith(getString(R.string.image_directory))) {
                    // Handle camera/gallery images
                    File imageFile = new File(getFilesDir(), imgPath);
                    if (imageFile.exists()) {
                        productImage.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
                    } else {
                        productImage.setImageResource(R.drawable.default_image);
                    }
                } else {
                    // Handle drawable resources
                    int resID = getResources().getIdentifier(imgPath, "drawable", getPackageName());
                    productImage.setImageResource(resID != 0 ? resID : R.drawable.default_image);
                }

                int categoryResID =CategoryType.getIconFromString(item.getCategory());
                categoryImg.setImageResource(categoryResID);

                // Check if the item is saved and update the bookmark icon accordingly
                boolean isSaved = !service.isItemSaved(item);
                int bookmarkRes = isSaved ? R.drawable.bookmark : R.drawable.bookmark_filled;
                bookmarkIcon.setImageResource(bookmarkRes);

                item.setSaved(!isSaved); // if not saved, set to false, else set to true



                // Toggle saved state when bookmarkIcon is clicked
                bookmarkIcon.setOnClickListener(v -> {
                    if (service.isItemSaved(item)) {
                        service.removeItemFromSaved(item);
                        bookmarkIcon.setImageResource(R.drawable.bookmark);
                    } else {
                        try {
                            service.addItemToSaved(item);
                            bookmarkIcon.setImageResource(R.drawable.bookmark_filled);
                        }catch(IllegalArgumentException e){
                            Toast.makeText(ItemActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }catch(IllegalStateException s){
                            Toast.makeText(ItemActivity.this,s.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // Fetch and display related items
                List<Item> relatedItems = service.getItemsByCategory(item.getCategory(), item.getItemId());
                if (relatedItems != null && !relatedItems.isEmpty()) {
                    relatedItemAdapter = new RelatedItemsAdapter(this, relatedItems, R.layout.item_card);
                    relatedItemsRecyclerView.setAdapter(relatedItemAdapter);
                }
            } else {
                Log.e(PresentationConfig.LOG_TAG_ITEM_ACTIVITY, PresentationConfig.ERROR_ITEM_NOT_FOUND + parsedItemId);
            }
        } else {
            Log.e(PresentationConfig.LOG_TAG_ITEM_ACTIVITY, PresentationConfig.ERROR_INVALID_ITEM_ID);

        }
    }

}
