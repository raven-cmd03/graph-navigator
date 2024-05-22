package A_Star;
import java.util.ArrayList;

import GraphClasses.Edge;
import GraphClasses.Graph;
import GraphicClasses.DrawingCanvas;

public class Astar
{
    private ArrayList<Node> open;
    private ArrayList<Node> closed;
    private Graph graph;
    private DrawingCanvas canvas;
    private int destination;

    public Astar(Graph graph , DrawingCanvas canvas , int start , int destination)
    {
        this.graph = graph;
        this.canvas = canvas;
        this.destination = destination;
        open = new ArrayList<>();
        closed = new ArrayList<>();
        ArrayList<Edge> list = graph.getAdjacencyList().get(start);
        for(Edge e : list)
        {
            open.add(new Node(graph, canvas, e.getHome(), e.getDestination(), destination));
        }
    }

    public void run()
    {
        while (!open.isEmpty())
        {
            boolean found = false;
            int max = Integer.MAX_VALUE;
            Node q = null;
            for(Node n : open)
            {
                if(n.getF() < max)
                {
                    max = n.getF();
                    q = n;
                }
            }
            open.remove(q);
            if(q.getSelf() == this.destination)
            {
                closed.add(q);
                break;
            }
            ArrayList<Edge> tempEdge = graph.getAdjacencyList().get(q.getSelf());
            ArrayList<Node> tempNode = new ArrayList<>();
            for(Edge e : tempEdge)
            {
                tempNode.add(new Node(graph, canvas, e.getHome(), e.getDestination(), destination));
            }
            for(Node n : tempNode)
            {
                if(n.getSelf() == destination)
                {
                    closed.add(n);
                    found = true;
                    break;
                }
                else
                {
                    if(betterInOpen(n))
                    {
                        continue;
                    }
                    else if(betterInClosed(n))
                    {
                        continue;
                    }
                    else
                    {
                        open.add(n);
                    }
                }
            }
            if(found)
            {
                closed.add(q);
                break;
            }
            else
            {
                closed.add(q);
            }
        }
    }

    private boolean betterInOpen(Node n)
    {
        for(Node node : open)
        {
            if(node.getSelf() == n.getSelf() && node.getF() < n.getF())
            {
                return true;
            }
        }
        return false;
    }

    private boolean betterInClosed(Node n)
    {
        for(Node node : closed)
        {
            if(node.getSelf() == n.getSelf() && node.getF() < n.getF())
            {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Node> getClosed()
    {
        return closed;
    }

    public void cleanList()
    {

    }
}
