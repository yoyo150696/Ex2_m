there are 5 main classes.
1.DWGA- this class implements DirectedWeightedGraphAlgorithms.
It has one property graph from type DirectedWeightedGraph.
2.DWG- this class implements DirectedWeightedGraph.
It has 2 properties : hashMap that get int that represents the key of the NodeData and returns the NodeData,
and int that represents the number of changes in the graph.
3. ND- this class implements NodeData.
It has 3 properties: int that represents the key of the node,
 pos from the GeoLocation that represents the position of the node in the graph,
 hashMap that represents the edgeData that are connected to the node; the hashMap get string that is combine by "src-id+dest-id" and return the EdgeData.
4.ED- this class implements EdgeData.
It has 3 properties: int that represents the src id, int that represents the dest id, double that represents the weight of the edge.
5.Gl- this class implements EdgeData.
It has 3 properties: 3 doubles each represents x,y and z.
