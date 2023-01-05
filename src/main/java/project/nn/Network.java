package project.nn;

import org.apache.mahout.math.Vector;

import java.util.List;

/** this class serves as the input layer of the network */
public class Network {
    private final double eta;
    private final Layer firstLayer;

    public Network(double eta, Layer firstLayer) {
        this.eta = eta;
        this.firstLayer = firstLayer;
    }

    public List<Runnable> propagate(Vector input, Vector desired) {
        firstLayer.propagate(input, desired);
        return firstLayer.adjustWeights(eta, input);
    }

    public void updateWeights(List<Runnable> changes) { changes.forEach(Runnable::run); }

    public double squaredError(Vector desired) { return firstLayer.getOutput().minus(desired).getLengthSquared(); }

    public Vector getOutput() {
        return firstLayer.getOutput();
    }

    public Vector evaluate(Vector input) {
        return firstLayer.evaluate(input);
    }
}