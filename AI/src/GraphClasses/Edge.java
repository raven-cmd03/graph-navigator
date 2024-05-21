package GraphClasses;

import java.awt.Color;
import java.awt.Graphics;

import GraphicClasses.Button;

public class Edge 
{
    private int home;
    private int destination;
    private int weight;

    public Edge(int home , int destination , int weight)
    {
        this.home = home;
        this.destination = destination;
        this.weight = weight;
    }

    public int getHome()
    {
        return home;
    }

    public int getDestination()
    {
        return destination;
    }

    public int getWeight()
    {
        return weight;
    }

    public void drawEdge(Graphics g , Button b1 , Button b2)
    {
        g.setColor(Color.BLACK);
        g.drawLine(b1.getPoint().getX(), b1.getPoint().getY(), b2.getPoint().getX(), b2.getPoint().getY());
    }
}
