package utils;

import artifacts.*;

public class PurchasedItem {
	public Artifact artifact;
	public double price;

	public PurchasedItem(Artifact artifact, double price) {
        this.artifact = artifact;
        this.price = price;
    }
}