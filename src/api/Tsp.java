package api;

import java.util.ArrayList;
import java.util.List;

public class Tsp {
    private ArrayList<NodeData> path;
    private double minAns;
    public int s = 0;
    public Tsp(){
        path=new ArrayList<NodeData>();
        minAns=Double.MIN_VALUE;
    }
    public ArrayList<NodeData> getTsp(double[][] graph, boolean[] v, int currPos, int n, int count, double cost ,ArrayList<NodeData> currPath,List<NodeData> cities){
        if (count == n)
        {
            System.out.println(66);
            if (minAns>cost){
                minAns=cost;
                path=currPath;
            }
            return this.path;
        }
        for (int i = 0; i < n; i++)
        {
            if (v[i] == false && graph[currPos][i] > 0)
            {

                v[i] = true;
                ArrayList<NodeData> a=new ArrayList<NodeData>(currPath);
                a.add(cities.get(i));
                this.path =getTsp(graph, v, i, n, count + 1, cost + graph[currPos][i], a,cities);

                // Mark ith node as unvisited
                v[i] = false;
            }
        }
        return this.path;
    }
}