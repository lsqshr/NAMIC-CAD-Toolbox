package namic.cad.ml;

import com.sun.xml.internal.bind.v2.TODO;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.networks.structure.NeuralStructure;

/**
 * Construct a neural network by loading the pre-trained parameters to predict the diagnosis
 * probability of an input vector.
 */
public class Learner {

    NeuralNet neuralNet;

    public Learner(){
        NeuralNet neuralNet = null;
    };

    public void setNeuralNet(NetConfig nc){
        NeuralNet nn = new NeuralNet(nc);
        this.neuralNet = nn;
    };

    public NeuralNet getNeuralNet(){
        return this.neuralNet;
    };




}
