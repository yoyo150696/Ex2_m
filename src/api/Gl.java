package api;

public class Gl implements GeoLocation{
    private double x;
    private double y;
    private double z;
    public Gl(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double distance(GeoLocation g) {
        return Math.sqrt(Math.pow(Math.abs(this.x-g.x()),2)+Math.pow(Math.abs(this.y-g.y()),2));
    }
}
