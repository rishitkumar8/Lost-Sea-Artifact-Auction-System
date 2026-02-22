package artifacts;

public class SunkenShipwreck extends Artifact {
	public SunkenShipwreck(String name, double value, double bmv, boolean fragile) {
        super(name, value, bmv, fragile);
    }

    @Override
	public void displayInfo() {
        System.out.println("Shipwreck: " + name + ", Value: $" + estimatedValue + ", BMV: $" + blackMarketValue + ", Fragile: " + isFragile);
    }
}