package api;

import java.beans.JavaBean;
import java.io.FileNotFoundException;
import java.util.*;

import com.google.gson.*;
import java.io.FileReader;


import java.io.FileWriter;
import java.io.IOException;

public class DWGA implements DirectedWeightedGraphAlgorithms{
    private DirectedWeightedGraph graph;
    @Override
    public void init(DirectedWeightedGraph g) {
       graph = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraph g=new DWG();
        Iterator<NodeData> in=graph.nodeIter();
        while (in.hasNext()){
            NodeData n=new ND((ND) in.next());
            g.addNode(n);
        }
        return g;
    }

    @Override
    public boolean isConnected() {
        int nodeCount=0,edgesCount=0;
        Iterator<NodeData> nI=graph.nodeIter();
        Iterator<EdgeData> eI= graph.edgeIter();
        while (nI.hasNext()){
            nodeCount++;
            nI.next();
        }
        while (eI.hasNext()){
            edgesCount++;
            eI.next();
        }
        return nodeCount*(nodeCount-1)==edgesCount;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        List<NodeData> shortestpath = shortestPath(src,dest);
        double sum = 0;
        for(int i = 0;i<shortestpath.size()-1;i++) {
            sum += graph.getEdge(shortestpath.get(i).getKey(), shortestpath.get(i + 1).getKey()).getWeight();
        }
        return sum;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        Iterator<NodeData> in=graph.nodeIter();
        HashMap<Integer,ArrayList<NodeData>> paths=new HashMap<>();
        HashMap<Integer,Double> dist=new HashMap<>();
        Queue<NodeData> pq = new LinkedList<>();
        ArrayList<NodeData> settled=new ArrayList<NodeData>();
        pq.add(graph.getNode(src));
        while (in.hasNext()){
            NodeData n=in.next();

            paths.put(n.getKey(),new ArrayList<NodeData>());
            if (n.getKey()==src){
                paths.get(n.getKey()).add(graph.getNode(src));
                dist.put(n.getKey(),0.0);
            }else {
                dist.put(n.getKey(), Double.MAX_VALUE);
            }
        }

        while (settled.size()!=graph.nodeSize()){
            if (pq.size()==0){
                return paths.get(dest);
            }
            NodeData n=pq.remove();
            if (settled.contains(n)){
                continue;
            }
            settled.add(n);
            double edgeDistance = -1;
            double newDistance = -1;
            Iterator<EdgeData> ie=graph.edgeIter(n.getKey());
            while (ie.hasNext()){
                EdgeData e=ie.next();
                NodeData cur=graph.getNode(e.getDest());
                if (!settled.contains(cur)){
                    edgeDistance=e.getWeight();
                    newDistance=dist.get(n.getKey())+edgeDistance;
                    if(newDistance<dist.get(cur.getKey())){
                        dist.put(cur.getKey(),newDistance);
                        ArrayList<NodeData> newPath=new ArrayList<NodeData>(paths.get(n.getKey()));
                        newPath.add(cur);
                        paths.put(cur.getKey(),newPath);
                    }
                    pq.add(cur);
                }
            }
        }
        return paths.get(dest);
    }

    @Override
    public NodeData center() {
        Iterator<NodeData> iN=graph.nodeIter();

        NodeData curNode= iN.next();
        NodeData bestNode=curNode;
        double min=999999999.0;
        while (iN.hasNext()){
            Iterator<NodeData> iNC=graph.nodeIter();
            NodeData curNodeCheck= iNC.next();

            if(curNodeCheck==curNode){
                curNodeCheck=iNC.next();
            }
            double max=-1;
            while (iNC.hasNext()){
                if(curNode!=curNodeCheck) {
                    double curDis = shortestPathDist(curNode.getKey(), curNodeCheck.getKey());

                    if (curDis != -1&&max < curDis) {
                        max = curDis;

                    }
                }
                curNodeCheck=iNC.next();
            }

            if (max<min&&max!=-1){
                min=max;
                bestNode=curNode;
            }
            curNode=iN.next();

        }
        return bestNode;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        Tsp tsp=new Tsp();
        int n=cities.size();
        boolean[] v=new boolean[n];
        v[0]=true;
        double[][] w=new double[n][n];
        Iterator<NodeData> in=cities.iterator();
        while (in.hasNext()) {
            NodeData curN=in.next();
            Iterator<NodeData> curI = cities.iterator();
            while (curI.hasNext()) {
                NodeData curNI=curI.next();
                if (graph.getEdge(curN.getKey(),curNI.getKey())!=null){
                    w[cities.indexOf(curN)][cities.indexOf(curNI)]=graph.getEdge(curN.getKey(),curNI.getKey()).getWeight();
                }
            }
        }
        return tsp.getTsp(w,v,0,n,1,0,new ArrayList<NodeData>(),cities);
    }
    @Override
    public boolean save(String file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter f = new FileWriter(file);

            List <EdgeData> edges= new ArrayList<>();
            List <Nodes> nodes= new ArrayList<>();
            Map<String,List> json = new HashMap<>();

            Iterator<EdgeData> edge_I = graph.edgeIter();
            while (edge_I.hasNext()){
                EdgeData edge = edge_I.next();
                edges.add(edge);
            }
            Iterator<NodeData> node_I = graph.nodeIter();
            while (node_I.hasNext()){

                NodeData node = node_I.next();
                double pos_x = node.getLocation().x();
                double pos_y = node.getLocation().y();
                double pos_z = node.getLocation().z();
                String pos = pos_x+","+pos_y+","+pos_z;
                Nodes n = new Nodes(pos,node.getKey());
                nodes.add(n);
            }
            json.put("Edges",edges);
            json.put("Nodes",nodes);

            String json_w = gson.toJson(json);

            f.write(json_w);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public boolean load(String file) {
        graph = new DWG();
        Gson gson = new Gson();
        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return false;
        }
        JsonObject obj = gson.fromJson(reader, JsonObject.class);
        JsonArray nodes = obj.getAsJsonArray("Nodes");
        JsonArray edges = obj.getAsJsonArray("Edges");


        for (int i = 0; i < nodes.size(); i++) {
            String s = nodes.get(i).toString();
            String sr = s.replaceAll("\"|}", "");
            String[] new_s = sr.split(",|:");
            String pos = new_s[1]+","+new_s[2]+","+new_s[3];
            int id = Integer.parseInt(new_s[5]);
            ND node = new ND(id, pos);
            graph.addNode(node);
        }
        for (int i = 0; i < edges.size(); i++) {

            String se = edges.get(i).toString();
            String ser = se.replaceAll("\"|}", "");
            String[] see = ser.split(",|:");

            int src = Integer.parseInt(see[1]);
            double weight = Double.parseDouble(see[3]);
            int dest = Integer.parseInt(see[5]);
            graph.connect(src, dest, weight);

        }
        return true;
    }

}
