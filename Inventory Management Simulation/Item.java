public class Item{
    //Item attributes.
    protected  String ItemName;
    protected  String Seller;
    protected  int ItemCost;
    protected  String ItemDiscount;
    protected  String ItemCategory;
    protected  String ItemDescription;
    protected  int ItemKey;
    protected  int ItemCount;
    
    //Item constructor.
    public Item( int ItemKey, String ItemName, String ItemCategory, String ItemDescription, String Seller, int ItemCost, String ItemDiscount, int ItemCount){
        this.ItemName = ItemName;
        this.ItemCost = ItemCost;
        this.ItemCategory = ItemCategory;
        this.ItemDescription = ItemDescription;
        this.ItemKey = ItemKey;
        this.ItemCount = ItemCount;
        this.ItemDiscount = ItemDiscount; 
        this.Seller = Seller;

    }
    //Mutator methods to update the data of the item.
    public void Update_Item_Name(String ItemName){
        this.ItemName = ItemName;
    }
    public void Update_Item_Category(String ItemCategory){
        this.ItemCategory = ItemCategory;
    }
    public void Update_Item_Description(String ItemDescription){
        this.ItemDescription = ItemDescription;
    }
    public void Update_Item_Discount(String ItemDiscount){
        this.ItemDiscount = ItemDiscount;
    }
    public void Update_Item_Cost(int ItemCost){
        this.ItemCost = ItemCost;
    }
    public void Update_Item_Count(int ItemCount){
        this.ItemCount = ItemCount;
    }
    public void Update_Item_Seller(String Seller){
        this.Seller = Seller;
    }

    public int get_Item_Key(){
        return this.ItemKey;
    }

    //Method to print the data of the item.
    public String printData(){
        return("" + this.ItemKey + " | " + this.ItemName + " | " + this.ItemCategory + " | " + this.ItemDescription + " | " + this.Seller + " | " + this.ItemCost + " | " + this.ItemDiscount + " | " + this.ItemCount + "\n");
    }
}