import api.*;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

import static java.lang.Math.abs;

public class DrawingCanvas extends JComponent {
    private int width;
    private int height;
    private DirectedWeightedGraphAlgorithms graph;
    public DrawingCanvas(int w,int h,DirectedWeightedGraphAlgorithms g){
        width = w;
        height = h;
        graph = g;
    }
    private void drawArrowLine(Graphics g, double x1, double y1, double x2, double y2, int d, int h) {
        Graphics2D g2d = (Graphics2D) g;
        double dx = (x2 - x1), dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {(int)x2, (int) xm, (int) xn};
        int[] ypoints = {(int)y2, (int) ym, (int) yn};

        Line2D line = new Line2D.Double (x1, y1, x2, y2);
        g2d.draw(line);
        g.fillPolygon(xpoints, ypoints, 3);

    }
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        Map<Integer,Double> x_val = new HashMap<>();
        Map<Integer,Double> y_val = new HashMap<>();
        double max_x = graph.getGraph().nodeIter().next().getLocation().x();
        double min_x = graph.getGraph().nodeIter().next().getLocation().x();
        double max_y = graph.getGraph().nodeIter().next().getLocation().y();
        double min_y = graph.getGraph().nodeIter().next().getLocation().y();
        Iterator<NodeData> node_scale = graph.getGraph().nodeIter();
        while (node_scale.hasNext()){
            NodeData node = node_scale.next();
            double x = node.getLocation().x();
            double y = node.getLocation().y();
            if(max_x<x)
                max_x = x;
            if(min_x>x)
                min_x = x;
            if(max_y<y)
                max_y = y;
            if(min_y>y)
                min_y = y;
        }
        double abs_x = abs(max_x-min_x);
        double abs_y = abs(max_y - min_y);
        double scale_x = width*0.6/abs_x;
        double scale_y = height*0.6/abs_y;
        Iterator<NodeData> node_I = graph.getGraph().nodeIter();
        while (node_I.hasNext()){
            NodeData node = node_I.next();
            int i = node.getKey();
            double dis_x = (node.getLocation().x()+0.001-min_x)*scale_x;
            double dis_y = (node.getLocation().y()+0.0001-min_y)*scale_y;
            x_val.put(i,dis_x);
            y_val.put(i,dis_y);
        }
        Iterator<NodeData> node_e = graph.getGraph().nodeIter();
        while (node_e.hasNext()){

            NodeData node = node_e.next();
            int i = node.getKey();
            Rectangle2D rect = new Rectangle2D.Double(x_val.get(i)-5, y_val.get(i)-5, 10, 10);
            float x = (float) (x_val.get(i)+5);
            float y = (float) (y_val.get(i)+5);
            String s = String.valueOf(i);
            g2d.setColor(Color.red);
            g2d.drawString(s,x+3,y);
            g2d.setColor(Color.black);
            g2d.draw(rect);
            Iterator<EdgeData> edge_I = graph.getGraph().edgeIter(node.getKey());
            while (edge_I.hasNext()) {
                EdgeData edge = edge_I.next();
                Line2D line = new Line2D.Double(x_val.get(i),y_val.get(i),x_val.get(edge.getDest()),y_val.get(edge.getDest()));
                g2d.draw(line);
                drawArrowLine(g,x_val.get(i),y_val.get(i),x_val.get(edge.getDest()),y_val.get(edge.getDest()),6,6);
            }

        }
    }

}

