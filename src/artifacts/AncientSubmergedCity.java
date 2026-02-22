package artifacts;

public class AncientSubmergedCity extends Artifact {
    public AncientSubmergedCity(String name, double value, double bmv, boolean fragile) {
        super(name, value, bmv, fragile);
    }

    @Override
	public void displayInfo() {
        System.out.println("Submerged City: " + name + ", Value: $" + estimatedValue + ", BMV: $" + blackMarketValue + ", Fragile: " + isFragile);
    }
}