package auction;
import artifacts.*;import bidders.*;
import java.util.Scanner;

public class ReverseBlackMarketAuction extends Auction {
    public ReverseBlackMarketAuction(Artifact artifact) {
        super(artifact);
    }

    public void startReverseAuction() {
        double price = artifact.blackMarketValue; // Start at the black market value
        Scanner scanner = new Scanner(System.in);
        System.out.println("Reverse Auction started at $" + price);

        // Check if the artifact is sold
        if (artifact.sold) {
            System.out.println("This artifact has already been sold and cannot be auctioned.");
            return; // Exit if the artifact is sold
        }

        if (bidders.isEmpty()) {
            System.out.println("No bidders available for the reverse auction.");
            return; // Exit if no bidders are available
        }

        while (price > artifact.blackMarketValue * 0.1 && bidders.size() > 0) {
            System.out.println("Current Price: $" + price);
            System.out.print("Do you want to freeze the price? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                System.out.print("Enter your name to confirm purchase: ");
                String bidderName = scanner.nextLine();
                Bidder bidder = findBidderByName(bidderName);

                if (bidder != null) {
                    if (bidder.placeBid(price)) {
                        bidder.addPurchasedItem(artifact, price); // Add purchased item
                        artifact.sold = true; // Mark artifact as sold
                        System.out.println(bidder.name + " has purchased the artifact at the frozen price of $" + price);
                        return; // End auction as the artifact is sold
                    } else {
                        System.out.println("You do not have enough budget to purchase at this price.");
                    }
                } else {
                    System.out.println("Bidder not found. Please enter a valid name.");
                }
            }
            price *= 0.95; // Decrease price every iteration
        }
        System.out.println("Reverse auction ended without any purchases.");
    }

    private Bidder findBidderByName(String name) {
        for (Bidder bidder : bidders) {
            if (bidder.name.equalsIgnoreCase(name)) {
                return bidder;
            }
        }
        return null; // Return null if no matching bidder is found
    }
}