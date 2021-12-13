package api;

import java.util.HashMap;


public class ND implements NodeData{

    private GeoLocation pos;
    private int id;
    HashMap<String,EdgeData> edges;

    public ND(int id,String pos){
        this.id=id;
        String[] listPos=pos.split(",");
        GeoLocation p=new Gl(Double.parseDouble(listPos[0]),Double.parseDouble(listPos[1]),Double.parseDouble(listPos[2]));
        this.pos=p;
        this.edges=new HashMap<>();
    }
    public ND(ND nd){
        this.pos=new Gl(nd.pos.x(),nd.pos.y(),nd.pos.z());
        this.id=nd.id;
        this.edges=new HashMap<String, EdgeData>(nd.edges);
    }
    public EdgeData getEdge(String name) {
        return this.edges.get(name);
    }
    public HashMap<String,EdgeData> getEdges(){
        return edges;
    }
    @Override
    public int getKey() {
        return id;
    }

    @Override
    public GeoLocation getLocation() {
        return pos;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.pos=p;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }

}
