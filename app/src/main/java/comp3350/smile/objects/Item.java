    package comp3350.smile.objects;

    import java.util.Date;

    public class Item {
        final private int itemId;
        private String name;
        private String description;
        private String category; // e.g., Electronics, Books, etc.
        private String condition; // e.g., New, Used
        private double price;
        private String paymentModes; // e.g., Cash, Digital Payment

        final private User seller; // Reference to the seller
        final private Date listedDate;
        private boolean saved;



        private String imgPath;

    // Constructor
    public Item(int itemId, String name, String description, String category, String condition, double price, User seller, String imgPath, String paymentModes) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.condition = condition;
        this.price = price;
        this.seller = seller;
        this.listedDate = new Date();
        this.imgPath = imgPath;
        this.paymentModes = paymentModes;
        this.saved = false;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {this.price = price; }

    public String getPaymentModes() {
        return paymentModes;
    }

    public void setPaymentModes(String mode){
        this.paymentModes = mode;
    }


    public User getSeller() {
        return seller;
    }


    public String getImgPath() {
        return imgPath;
    }


    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }


}