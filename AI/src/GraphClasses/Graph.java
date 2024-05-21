package GraphClasses;

import java.util.ArrayList;

public class Graph 
{
     private ArrayList<ArrayList<Edge>> adjacencyList;

     public Graph()
     {
        adjacencyList = new ArrayList<>();
     }

     public void addVertice()
     {
        ArrayList<Edge> vertex = new ArrayList<>();
        adjacencyList.add(vertex);
     }

     private boolean checkEdge(Edge edge)
     {
        if (edge.getHome() >= 0 && edge.getDestination() >= 0 && edge.getHome() != edge.getDestination())
        {
            return true;
        }
        return false;
     }

     private boolean checkArray(Edge edge)
     {
        if(edge.getDestination() < adjacencyList.size() && edge.getHome() < adjacencyList.size())
        {
            return true;
        }
        return false;
     }

     public boolean addEdge(Edge edge)
     {
        if(checkEdge(edge) && checkArray(edge))
        {
            ArrayList<Edge> e = adjacencyList.get(edge.getHome());
            if(e == null)
            {
               e = new ArrayList<>();
            }
            e.add(edge);
            return true;
        }
        return false;
     }

     public int[][] getWeights()
     {
         int[][] weights = new int[adjacencyList.size()][adjacencyList.size()];
         for(int i = 0 ; i < adjacencyList.size() ; i++)
         {
            ArrayList<Edge> edges = adjacencyList.get(i);
            for(int x = 0 ; x < edges.size() ; x++)
            {
               weights[edges.get(x).getDestination()][i] = edges.get(x).getWeight();
            }
         }
         return weights;
     }
}
