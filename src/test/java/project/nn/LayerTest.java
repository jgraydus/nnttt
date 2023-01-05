package project.nn;

import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LayerTest {
    @Test
    public void errorTest() {
        Vector desired  = new DenseVector(new double[]{ 1.0,  2.0, 7.0});
        Vector output   = new DenseVector(new double[]{ 9.0,  8.0, 3.0});
        Vector expected = new DenseVector(new double[]{-8.0, -6.0, 4.0});
        Layer layer = new Layer("", 0.0, null, null);
        assertEquals(expected, layer.error(desired, output));
    }

    @Test
    public void dATest() {
        Vector output   = new DenseVector(new double[]{ 2.0, -2.0, 1.0, 0.5 });
        Vector expected = new DenseVector(new double[]{-2.0, -6.0, 0.0, 0.25});
        Layer layer = new Layer("", 0.0, null, null);
        assertEquals(expected, layer.dA(output));
    }

    @Test
    public void deltaTest01() {
        Vector desired  = new DenseVector(new double[]{1.0,   2.0, 0.5, 1.0  });
        Vector output   = new DenseVector(new double[]{2.0,  -2.0, 1.0, 0.5  });
        Vector expected = new DenseVector(new double[]{2.0, -24.0, 0.0, 0.125});
        Layer layer = new Layer("", 0.0, null, null);
        Vector result = layer.delta(desired, output);
        for (int i=0; i<expected.size(); i++) {
            assertEquals(expected.get(i), result.get(i), 1e-10);
        }
    }
}
