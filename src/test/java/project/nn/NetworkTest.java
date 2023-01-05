package project.nn;

import org.apache.mahout.math.DenseMatrix;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.Vector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NetworkTest {
    /** run a single update on the example nn from the slides and ensure the same weights are calculated */
    @Test public void networkTest() {
        double bias = 0.0;
        double eta = 1.0;
        Vector input = new DenseVector(new double[]{1.0, 2.0});
        Vector desired = new DenseVector(new double[]{0.7});

        Layer outputLayer = new Layer("L2", bias, new DenseMatrix(new double[][]{{0.8,0.8}}), null);
        Layer firstLayer = new Layer("L1", bias, new DenseMatrix(new double[][]{{0.3,0.3},{0.3,0.3}}), outputLayer);

        Network nn = new Network(eta, firstLayer);
        nn.updateWeights(nn.propagate(input, desired));

        Matrix w1 = firstLayer.getWeights();
        assertEquals(0.298270542, w1.get(0,0), 1e-8);
        assertEquals(0.296541084, w1.get(0, 1), 1e-8);
        assertEquals(0.298270542, w1.get(1,0), 1e-8);
        assertEquals(0.296541084, w1.get(1,1), 1e-8);

        Matrix w2 = outputLayer.getWeights();
        assertEquals(0.79252095, w2.get(0,0), 1e-8);
        assertEquals(0.79252095, w2.get(0,1), 1e-8);
    }
}