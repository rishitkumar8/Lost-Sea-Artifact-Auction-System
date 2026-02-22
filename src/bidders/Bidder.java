package bidders;
import artifacts.*; import utils.PurchasedItem;
import java.util.*;


public abstract class Bidder {
	public String name;
    public double budget;
    public boolean hasLeft = false; // Track if the bidder has left
    public List<PurchasedItem> purchasedItems = new ArrayList<>(); // List of purchased items

    Bidder(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public abstract boolean canBid(Artifact artifact);

    public boolean placeBid(double amount) {
        if (amount <= budget) {
            budget -= amount;
            return true;
        }
        return false; 
    }

    public void leaveAuction() {
        hasLeft = true;
    }

    public void addPurchasedItem(Artifact artifact, double price) {
        purchasedItems.add(new PurchasedItem(artifact, price));
    }

    public void displayPurchasedItems() {
        System.out.println("Bidder: " + name);
        for (PurchasedItem item : purchasedItems) {
            System.out.println(" - Purchased: " + item.artifact.name + " for $" + item.price);
        }
    }
}