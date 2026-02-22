package artifacts;

public abstract class Artifact {
	public String name;
    public double estimatedValue;
    public double blackMarketValue; // BMV
    public boolean isFragile;
    public boolean sold = false; // Track if the artifact has been sold

    public Artifact(String name, double value, double bmv, boolean fragile) {
        this.name = name;
        this.estimatedValue = value;
        this.blackMarketValue = bmv;
        this.isFragile = fragile;
    }

    public abstract void displayInfo();
}

