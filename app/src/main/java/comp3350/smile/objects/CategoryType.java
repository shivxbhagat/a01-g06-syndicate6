package comp3350.smile.objects;


import comp3350.smile.R;

public enum CategoryType {
    ELECTRONICS("Electronics", R.drawable.electricity_icon),
    BOOKS("Books", R.drawable.book_icon),
    CLOTHING("Clothing", R.drawable.hanger_icon),
    HOUSE("House", R.drawable.house_icon),
    LOST_AND_FOUND("Lost and Found", R.drawable.lost_and_found),
    FREE("Free", R.drawable.free),
    DEFAULT("Default", R.drawable.default_image);

    private final String displayName;
    private final int drawableId;

    CategoryType(String displayName, int drawableId) {
        this.displayName = displayName;
        this.drawableId = drawableId;
    }

    public int getDrawableId() {
        return drawableId;
    }
    //get icon based on the string
    public static int getIconFromString(String text) {
        for (CategoryType type : CategoryType.values()) {
            if (type.displayName.equalsIgnoreCase(text)) {
                return type.getDrawableId();
            }
        }
        return DEFAULT.getDrawableId();
    }
}
