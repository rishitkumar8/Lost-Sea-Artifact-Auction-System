package auction;
import artifacts.*;import bidders.*;
import java.util.*;



public class Auction {
	public List<Bidder> bidders = new ArrayList<>();
	public Artifact artifact;
	public double currentBid; // Remove default initialization
	public Bidder highestBidder = null;
	public boolean bmvClosed = false; // Track if BMV is closed

    public Auction(Artifact artifact) {
        this.artifact = artifact;
        this.currentBid = artifact.estimatedValue; // Start the auction at the estimated value
    }

    public void addBidder(Bidder bidder) {
        if (bidder.canBid(artifact)) {
            bidders.add(bidder);
        }
    }

    public void startAuction() {
        Scanner scanner = new Scanner(System.in);
        while (bidders.size() > 0) { // Continue until no bidders are left
            for (Bidder bidder : new ArrayList<>(bidders)) { // Create a copy to avoid ConcurrentModificationException
                if (bidder.hasLeft) continue; // Skip if the bidder has left

                System.out.println("Current Bid: $" + currentBid);
                System.out.print(bidder.name + ", enter your bid (or type 'exit' to drop out, or enter BMV to purchase at black market value): ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    bidder.leaveAuction();
                    System.out.println(bidder.name + " has dropped out.");
                    continue; // Skip to the next bidder
                }

                // Check if the user wants to purchase at BMV
                if (input.equalsIgnoreCase("BMV")) {
                    if (bmvClosed) {
                        System.out.println("The black market store has closed. You cannot purchase at BMV.");
                        continue; // Skip to the next bidder
                    }
                    if (bidder.placeBid(artifact.blackMarketValue)) {
                        bidder.addPurchasedItem(artifact, artifact.blackMarketValue); // Add purchased item
                        artifact.sold = true; // Mark artifact as sold
                        System.out.println(bidder.name + " has purchased the artifact at black market value of $" + artifact.blackMarketValue);
                        return; // End auction as the artifact is sold
                    } else {
                        System.out.println("You do not have enough budget to purchase at black market value.");
                        continue; // Skip to the next bidder
                    }
                }

                try {
                    double bid = Double.parseDouble(input);
                    if (bid > currentBid && bidder.placeBid(bid)) {
                        currentBid = bid;
                        highestBidder = bidder;
                        System.out.println("New highest bid: $" + currentBid + " by " + highestBidder.name);
                    } else {
                        System.out.println("Bid not accepted. You must bid higher than the current bid or you do not have enough budget.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number for your bid.");
                }

                // Check if the bidder can continue bidding
                if (bidder.budget < currentBid) {
                    System.out.println(bidder.name + " cannot afford to bid anymore and will drop out.");
                    bidder.leaveAuction();
                }

                // Check if the current bid has reached 50% of the BMV
                if (currentBid >= artifact.blackMarketValue * 0.5) {
                    bmvClosed = true; // Close the BMV option
                    System.out.println("The black market store has closed. No further purchases at BMV are allowed.");
                }
            }

            // Check if there are any bidders left
            bidders.removeIf(bidder -> bidder.hasLeft); // Remove bidders who have left
        }

        // Declare the highest bidder as the winner
        if (highestBidder != null) {
            System.out.println("Auction ended. Winner: " + highestBidder.name + " with a bid of $" + currentBid);
            highestBidder.addPurchasedItem(artifact, currentBid); // Add purchased item to the winner
            artifact.sold = true; // Mark artifact as sold
        } else {
            System.out.println("Auction ended with no winner.");
        }
    }
}