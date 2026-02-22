package utils;
import java.util.*;
import artifacts.*;


class ArtifactSearch {
    public Artifact searchForArtifact() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        System.out.println("Welcome to the Artifact Search System!");
        System.out.println("Press Enter to search for an artifact...");
        scanner.nextLine();
        System.out.print("Enter the estimated depth (in meters): ");
        int depth = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Choose your search method:");
        System.out.println("1. Diver");
        System.out.println("2. Robot");
        System.out.print("Enter your choice (1/2): ");
        int methodChoice = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Choose the type of search:");
        System.out.println("1. Sunken Shipwrecks (valuable items like maps and coins)");
        System.out.println("2. Ancient Submerged Cities (treasures, idols, and artifacts)");
        System.out.println("3. Deep-Sea Natural Formations (pearls and rare sea objects)");
        System.out.print("Enter your choice (1/2/3): ");
        int searchType = scanner.nextInt();
        scanner.nextLine();
        double probability = calculateProbability(depth);
        System.out.println("Estimated probability of finding an item: " + (probability * 100) + "%");
        if (random.nextDouble() < probability) {
            return getFoundArtifact(searchType); // Return the found artifact
        } else {
            System.out.println("Unfortunately, no artifact was found this time.");
            return null; // No artifact found
        }
    }

    public static double calculateProbability(int depth) {
        if (depth <= 50) return 0.9; // Shallow waters, high probability
        else if (depth <= 200) return 0.6; // Medium depth, moderate probability
        else if (depth <= 1000) return 0.3; // Deep-sea, lower probability
        else return 0.1; // Very deep-sea, rare finds
    }

    public static Artifact getFoundArtifact(int searchType) {
        Random random = new Random();
        switch (searchType) {
            case 1:
                String[] shipwreckItems = {"Old Map", "Ancient Coin", "Gold Necklace", "Rusty Compass"};
                String shipwreckItem = shipwreckItems[random.nextInt(shipwreckItems.length)];
                return new SunkenShipwreck(shipwreckItem, 10000 + random.nextInt(50000), 50000 + random.nextInt(50000), true);
            case 2:
                String[] cityItems = {"Golden Idol", "Ancient Scroll", "Gem-Encrusted Dagger", "Ceramic Pottery"};
                String cityItem = cityItems[random.nextInt(cityItems.length)];
                return new AncientSubmergedCity(cityItem, 20000 + random.nextInt(50000), 50000 + random.nextInt(50000), false);
            case 3:
                // For deep-sea natural formations, we can create a generic artifact
                return new Artifact("Deep-Sea Treasure", 15000 + random.nextInt(50000), 50000 + random.nextInt(50000), false) {
                    @Override
                    public void displayInfo() {
                        System.out.println("Deep-Sea Treasure: " + name + ", Value: $" + estimatedValue + ", BMV: $" + blackMarketValue + ", Fragile: " + isFragile);
                    }
                };
            default:
                return null; // Unknown item
        }
    }
}