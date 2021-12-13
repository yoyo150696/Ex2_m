import api.DWGA;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;



/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraphAlgorithms graph = new DWGA();
        graph.load(json_file);
        return graph.getGraph();
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms graph = new DWGA();
        graph.load(json_file);
        return graph;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        MyFrame f = new MyFrame(alg);
        DrawingCanvas dc = new DrawingCanvas(1000, 1000, alg);
        f.add(dc);
        f.setVisible(true);

    }
    public static void main(String[] args){
        getGrapg(args[0]);
        getGrapgAlgo(args[0]);
        runGUI(args[0]);
    }
}