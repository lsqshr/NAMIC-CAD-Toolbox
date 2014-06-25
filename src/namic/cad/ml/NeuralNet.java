package namic.cad.ml;

import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.structure.NetworkCODEC;

/**
 * Created by apple on 24/06/2014.
 */
public class NeuralNet extends BasicNetwork{
    public NeuralNet(){
        super();
    };

    public NeuralNet(NetConfig netConfig){

        BasicNetwork network = new BasicNetwork();

        for (int i = 1; i < netConfig.getSize().length; i++){
            if (i == 0){
                network.addLayer(new BasicLayer(null, false, netConfig.getSize()[i]));
            }
            else if (i == netConfig.getSize().length - 1){
                network.addLayer(new BasicLayer(netConfig.getOutput(), true, netConfig.getSize()[i]));
            }
            else {
                network.addLayer(new BasicLayer(netConfig.getActivation(), true, netConfig.getSize()[i]));
            }

        }
        network.getStructure().finalizeStructure();

        NetworkCODEC.arrayToNetwork(netConfig.getW(), network);

    };



}
