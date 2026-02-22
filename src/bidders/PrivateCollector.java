package bidders;
import artifacts.Artifact;

 public class PrivateCollector extends Bidder {
    public PrivateCollector(String name, double budget) {
        super(name, budget);
    }

    @Override
    public boolean canBid(Artifact artifact) {
        return budget >= artifact.estimatedValue && !hasLeft && !artifact.sold; // Check if artifact is not sold
    }
}
