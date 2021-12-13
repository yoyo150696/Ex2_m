import api.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;


public class MyFrame extends JFrame implements ActionListener {
    DirectedWeightedGraphAlgorithms graph;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu add;
    JMenu remove;
    JMenu algo;
    JMenuItem load;
    JMenuItem save;
    JMenuItem node_a;
    JMenuItem edge_a;
    JMenuItem node_r;
    JMenuItem edge_r;
    JMenuItem isConnected;
    JMenuItem shortestPathDist;
    JMenuItem shortestPath;
    JMenuItem tsp;
    JMenuItem center;
    DrawingCanvas dc;

    MyFrame(DirectedWeightedGraphAlgorithms g)
    {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000);
        graph = new DWGA();
        graph = g;

        menuBar = new JMenuBar();
        fileMenu = new JMenu("file");
        load = new JMenuItem("load");
        save  = new JMenuItem("save");
        fileMenu.add(load);
        fileMenu.add(save);
        menuBar.add(fileMenu);
        save.addActionListener(this);
        load.addActionListener(this);

        add = new JMenu("add");
        node_a = new JMenuItem("node");
        edge_a = new JMenuItem("edge");
        add.add(node_a);
        add.add(edge_a);
        menuBar.add(add);
        node_a.addActionListener(this);
        edge_a.addActionListener(this);


        remove = new JMenu("remove");
        node_r = new JMenuItem("node");
        edge_r = new JMenuItem("edge");
        remove.add(node_r);
        remove.add(edge_r);
        menuBar.add(remove);
        node_r.addActionListener(this);
        edge_r.addActionListener(this);

        algo = new JMenu("algo");
        isConnected = new JMenuItem("isConnected");
        shortestPathDist = new JMenuItem("shortestPathDist");
        shortestPath = new JMenuItem("shortestPath");
        tsp = new JMenuItem("tsp");
        center = new JMenuItem("center");
        algo.add(isConnected);
        algo.add(shortestPathDist);
        algo.add(shortestPath);
        algo.add(tsp);
        algo.add(center);
        menuBar.add(algo);
        isConnected.addActionListener(this);
        shortestPath.addActionListener(this);
        shortestPathDist.addActionListener(this);
        tsp.addActionListener(this);
        center.addActionListener(this);

        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == load) {
            if (dc != null)
                this.remove(dc);
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {

                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                graph.load(file.toString());
                dc = new DrawingCanvas(1000, 1000, graph);
                this.add(dc);
                this.setVisible(true);
            }

        }
        if (e.getSource() == save) {
            String name = JOptionPane.showInputDialog("file name");
            String loc = JOptionPane.showInputDialog("location");
            graph.save(loc + "\\" + name + ".json");
        }
        if (e.getSource() == node_a) {
            String id = JOptionPane.showInputDialog("id");
            String pos_x = JOptionPane.showInputDialog("enter x");
            String pos_y = JOptionPane.showInputDialog("enter y");
            String pos_z = String.valueOf(0.0);

            NodeData n = new ND(Integer.parseInt(id), pos_x+","+pos_y+","+pos_z);
            graph.getGraph().addNode(n);
            dc = new DrawingCanvas(1000, 1000, graph);
            this.add(dc);
            this.setVisible(true);

        }
        if (e.getSource() == edge_a) {
            String src = JOptionPane.showInputDialog("src");
            String dest = JOptionPane.showInputDialog("dest");
            String w = JOptionPane.showInputDialog("weight");
            graph.getGraph().connect(Integer.parseInt(src), Integer.parseInt(dest), Double.parseDouble(w));
            dc = new DrawingCanvas(1000, 1000, graph);
            this.add(dc);
            this.setVisible(true);
        }
        if (e.getSource() == node_r) {
            String id = JOptionPane.showInputDialog("id");
            graph.getGraph().removeNode(Integer.parseInt(id));
            dc = new DrawingCanvas(1000, 1000, graph);
            this.add(dc);
            this.setVisible(true);
        }
        if (e.getSource() == edge_r) {

            String src = JOptionPane.showInputDialog("src");
            String dest = JOptionPane.showInputDialog("dest");
            graph.getGraph().removeEdge(Integer.parseInt(src), Integer.parseInt(dest));
            dc = new DrawingCanvas(1000, 1000, graph);
            this.add(dc);
            this.setVisible(true);
        }
        if (e.getSource() == center) {
            int cen = graph.center().getKey();
            JOptionPane.showMessageDialog(this, "the center is " + cen, "center", JOptionPane.INFORMATION_MESSAGE);
        }

        if (e.getSource() == isConnected) {
            Boolean con = graph.isConnected();
            JOptionPane.showMessageDialog(this,con,"is strongly Connected??",JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource() == shortestPath) {
            String src = JOptionPane.showInputDialog("src");
            String dest = JOptionPane.showInputDialog("dest");
            List<NodeData> ls= graph.shortestPath(Integer.parseInt(src),Integer.parseInt(dest));
            String s = "";
            for (int i = 0;i<ls.size();i++){
                s += (ls.get(i).getKey())+", ";
            }
            JOptionPane.showMessageDialog(this,s,"shortestPath",JOptionPane.INFORMATION_MESSAGE);


        }
        if (e.getSource() == shortestPathDist) {
            String src = JOptionPane.showInputDialog("src");
            String dest = JOptionPane.showInputDialog("dest");
            double dist = graph.shortestPathDist(Integer.parseInt(src),Integer.parseInt(dest));
            JOptionPane.showMessageDialog(this,dist,"shortestPathDist",JOptionPane.INFORMATION_MESSAGE);
        }
        if(e.getSource()==tsp){
            System.exit(0);
        }
    }
}
