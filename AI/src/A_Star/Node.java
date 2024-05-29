package A_Star;

import java.util.ArrayList;

import GraphClasses.Edge;
import GraphClasses.Graph;
import GraphicClasses.DrawingCanvas;

public class Node
{
    private int parent;
    private int self;
    private int destination;
    private int g; // weight between parent and self
    private int h; // euclidean distance between self and destination
    private int f; // h + g

    public Node(Graph graph , DrawingCanvas canvas , int parent , int self , int destination)
    {
        this.parent = parent;
        this.self = self;
        this.destination = destination;
        this.h = canvas.getDistance(self, destination);
        ArrayList<Edge> list = graph.getAdjacencyList().get(parent);
        for(Edge e : list)
        {
            if(e.getDestination() == self)
            {
                g = e.getWeight();
                break;
            }
        }
        this.f = f();
    }

    private int f()
    {
        return h + g;
    }

    public int getParent()
    {
        return parent;
    }

    public int getSelf()
    {
        return self;
    }

    public int getDestination()
    {
        return destination;
    }

    public int getF()
    {
        return f;
    }
    
}
