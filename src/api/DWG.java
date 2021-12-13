package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DWG implements DirectedWeightedGraph{
    HashMap<Integer,NodeData> nodes=new HashMap<>();
    private int changed=0;
    @Override
    public NodeData getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        String s = Integer.toString(src);
        String d = Integer.toString(dest);


        ND node = (ND) getNode(src);

        return node.getEdge(s+d);
    }

    @Override
    public void addNode(NodeData n) {
        changed++;
        nodes.put(n.getKey(),n);
    }

    @Override
    public void connect(int src, int dest, double w) {
        String s = Integer.toString(src);
        String d = Integer.toString(dest);
        EdgeData edge = new ED(src,w,dest);
        changed++;
        ND n = (ND) nodes.get(src);
        n.getEdges().put(s+d,edge);

    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return nodes.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        List<EdgeData> list = new ArrayList<>();
        Iterator<NodeData> i=nodeIter();
        while (i.hasNext()){
            Iterator<EdgeData> iE=edgeIter(i.next().getKey());
            while (iE.hasNext()){
                list.add(iE.next());
            }
        }
        return list.iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        ND n = (ND) nodes.get(node_id);
        return n.getEdges().values().iterator();    }

    @Override
    public NodeData removeNode(int key) {
        NodeData node;
        Iterator<EdgeData> edge_I = this.edgeIter();
        while (edge_I.hasNext()){
            EdgeData edge = edge_I.next();
            if(edge.getDest()==key){
                removeEdge(edge.getSrc(),edge.getDest());
            }
        }
        node = nodes.remove(key);
        if (node!=null)
            changed++;
        return node;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData edge;
        String s = Integer.toString(src);
        String d = Integer.toString(dest);
        ND n = (ND) nodes.get(src);
        ND nd = (ND) nodes.get(dest);

        n.getEdges().remove(s+d);
        edge = nd.getEdges().remove(s+d);
        if (edge!=null)
            changed++;
        return edge;
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        int s  = 0;
        Iterator<EdgeData> i =edgeIter();
        while (i.hasNext()){
            i.next();
            s++;
        }
        return s;
    }

    @Override
    public int getMC() {
        return changed;
    }
}
