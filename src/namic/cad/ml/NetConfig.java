package namic.cad.ml;

import org.encog.engine.network.activation.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by apple on 22/06/2014.
 */
public class NetConfig {

    private int[] size;
    private ActivationFunction activation;
    private ActivationFunction output;
    private double[] W;
    private double[] b;


    public NetConfig(int[] size, String activationFunction, String outputFunction, double[] W, double[] b) {
        this.size = size;
        this.activation = setActivationFunction(activationFunction);
        this.output = setActivationFunction(outputFunction);

    }

    public NetConfig(Filepath filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
        }
        while ((sCurrentLine = br.readLine()) != null) {
            System.out.println(sCurrentLine);
        }
    } catch (IOException e){
        e.printStackTrace();
    } finally {

    }
    }

    ;

    public int[] getSize() {
        return this.size;
    }

    ;

    public ActivationFunction getActivation() {
        return this.activation;
    }

    ;

    public ActivationFunction getOutput() {
        return this.output;
    }

    ;

    public double[] getW(){
        return this.W;
    };

    public double[] getB(){
        return this.b;
    };

    public ActivationFunction setActivationFunction(String activationFunction) {

        ActivationFunction af = null;

        switch (activationFunction) {
            case "sigm":
                af = new ActivationSigmoid();
                break;
            case "softmax":
                af = new ActivationSoftMax();
                break;
            default:
                throw new IllegalArgumentException("Invalid activation type: " + activationFunction);

        }
        return af;

    }

    ;





}
