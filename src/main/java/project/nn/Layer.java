package project.nn;

import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.function.DoubleDoubleFunction;
import org.apache.mahout.math.function.DoubleFunction;

import java.util.LinkedList;
import java.util.List;

public class Layer {
    private final String id;
    private final double bias;
    private Matrix weights;
    private Vector activity;
    private Vector output;
    private Vector delta;
    private final Layer nextLayer;

    public Layer(String id, double bias, Matrix initialWeights, Layer nextLayer){
        this.id = id;
        this.bias = bias;
        this.weights = initialWeights;
        this.nextLayer = nextLayer;
    }

    /* the sigmoid function */
    private final DoubleFunction activation = new DoubleFunction() {
        @Override public double apply(double x) {
            return 1.0 / (1.0 + Math.exp(-1.0 * (x + bias)));
        }
    };

    /* element-wise multiplication */
    private final DoubleDoubleFunction zipWithMultiplication = new DoubleDoubleFunction() {
        @Override public double apply(double x, double y) { return x * y; }
    };

    /* element-wise x * (1 - x) */
    private final DoubleFunction zipWithTimesOneMinus = new DoubleFunction() {
        @Override public double apply(double x) { return x * (1.0 - x); }
    };

    /* calculate the output of this layer.  if this is not the last layer, use its output as input to the next layer.
     * if this is the last layer, go back down the stack adjusting the delta vector for each layer */
    void propagate(Vector input, Vector desired) {
        activity = weights.times(input);
        output = activity.clone().assign(activation);
        if (nextLayer != null) { // hidden layer
            nextLayer.propagate(output, desired);
            delta = delta(output, nextLayer.delta, nextLayer.weights);
        } else { // output layer
            delta = delta(desired, output);
        }
    }

    Vector evaluate(Vector input) {
        Vector output = weights.times(input).assign(activation);
        if (nextLayer != null) {
            return nextLayer.evaluate(output);
        } else {
            return output;
        }
    }

    /* calculate a change vector for each layers' weights, but don't apply it.  instead, return a Runnable that will
     * apply the change when invoked.  this allows us to defer the weight change until after propagation completes. */
    List<Runnable> adjustWeights(double eta, Vector input) {
        List<Runnable> result = nextLayer != null ? nextLayer.adjustWeights(eta, output) : new LinkedList<>();
        Matrix change = delta.cross(input).times(eta);
        result.add(() -> weights = weights.plus(change));
        return result;
    }

    /* calculate the error vector d - y */
    Vector error(Vector desired, Vector output) { return desired.clone().minus(output); }

    /* calculate y * (1 - y) */
    Vector dA(Vector output) { return output.clone().assign(zipWithTimesOneMinus);}

    /* delta calculation for output layer */
    Vector delta(Vector desired, Vector output) {
        return dA(output).assign(error(desired, output), zipWithMultiplication);
    }

    /* delta calculation for hidden layers */
    Vector delta(Vector output, Vector nextLayerDelta, Matrix nextLayerWeight) {
        Vector d = nextLayerWeight.transpose().times(nextLayerDelta);
        return dA(output).assign(d, zipWithMultiplication);
    }

    Matrix getWeights() { return weights; }

    public Vector getOutput() { return nextLayer == null ? output : nextLayer.getOutput(); }
}