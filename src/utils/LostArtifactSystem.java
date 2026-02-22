package utils;
import artifacts.*;import bidders.*;import auction.*;
import java.util.*;


public class LostArtifactSystem {
    private static List<Artifact> availableArtifacts = new ArrayList<>(); // List to hold available artifacts

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Bidder> bidders = new ArrayList<>(); // List to hold bidders

        System.out.println("Welcome to the Lost Artifact System!");
        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Search for an Artifact");
            System.out.println("2. Add Bidders");
            System.out.println("3. Start Auction");
            System.out.println("4. Start Reverse Black Market Auction");
            System.out.println("5. Display Bidders and Their Purchased Items");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    ArtifactSearch artSearch = new ArtifactSearch();
                    Artifact foundArtifact = artSearch.searchForArtifact(); // Search for an artifact
                    if (foundArtifact != null) {
                        availableArtifacts.add(foundArtifact); // Add found artifact to the available list
                        System.out.println("Artifact found: " + foundArtifact.name);
                        foundArtifact.displayInfo(); // Display artifact details including BMV and fragility
                    } else {
                        System.out.println("No artifact found.");
                    }
                    break;
                case 2:
                    if (!availableArtifacts.isEmpty()) {
                        addBidders(bidders); // Add bidders only if artifacts are found
                    } else {
                        System.out.println("You must find an artifact before adding bidders.");
                    }
                    break;
                case 3:
                    if (!availableArtifacts.isEmpty() && !bidders.isEmpty()) {
                        Artifact selectedArtifact = selectArtifactForAuction();
                        if (selectedArtifact != null) {
                            Auction auction = new Auction(selectedArtifact);
                            for (Bidder bidder : bidders) {
                                auction.addBidder(bidder);
                            }
                            auction.startAuction();
                            if (selectedArtifact.sold) {
                                availableArtifacts.remove(selectedArtifact); // Remove sold artifact from available list
                            }
                        }
                    } else {
                        System.out.println("You must find an artifact and add bidders before starting an auction.");
                    }
                    break;
                case 4:
                    if (!availableArtifacts.isEmpty() && !bidders.isEmpty()) {
                        Artifact selectedArtifact = selectArtifactForAuction();
                        if (selectedArtifact != null) {
                            ReverseBlackMarketAuction rbAuction = new ReverseBlackMarketAuction(selectedArtifact);
                            for (Bidder bidder : bidders) {
                                rbAuction.addBidder(bidder);
                            }
                            rbAuction.startReverseAuction();
                            if (selectedArtifact.sold) {
                                availableArtifacts.remove(selectedArtifact); // Remove sold artifact from available list
                            }
                        }
                    } else {
                        System.out.println("You must find an artifact and add bidders before starting a reverse auction.");
                    }
                    break;
                case 5:
                    for (Bidder bidder : bidders) {
                        bidder.displayPurchasedItems(); // Display each bidder's purchased items
                    }
                    break;
                case 6:
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 6);
        scanner.close();
    }

    private static void addBidders(List<Bidder> bidders) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of bidders to add: ");
        int numBidders = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < numBidders; i++) {
            System.out.print("Enter name for bidder " + (i + 1) + ": ");
            String name = scanner.nextLine();
            System.out.print("Enter budget for bidder " + (i + 1) + ": ");
            double budget = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            // Add a PrivateCollector for simplicity
            bidders.add(new PrivateCollector(name, budget));
        }
        System.out.println(numBidders + " bidders added.");
    }

    private static Artifact selectArtifactForAuction() {
        Scanner scanner = new Scanner(System.in);
        if (availableArtifacts.isEmpty()) {
            System.out.println("No available artifacts for auction.");
            return null;
        }

        System.out.println("Available artifacts for auction:");
        for (int i = 0; i < availableArtifacts.size(); i++) {
            System.out.println((i + 1) + ". " + availableArtifacts.get(i).name);
        }

        System.out.print("Select an artifact by number: ");
        int selection = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (selection > 0 && selection <= availableArtifacts.size()) {
            return availableArtifacts.get(selection - 1); // Return the selected artifact
        } else {
            System.out.println("Invalid selection.");
            return null; // Invalid selection
        }
     }
}