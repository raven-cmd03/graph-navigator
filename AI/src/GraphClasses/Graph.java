package GraphClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private ArrayList<ArrayList<Edge>> adjacencyList;
    private Map<Integer, int[]> vertexCoordinates;

    public Graph() {
        adjacencyList = new ArrayList<>();
        vertexCoordinates = new HashMap<>();
    }

    public void addVertice(int x, int y) {
        ArrayList<Edge> vertex = new ArrayList<>();
        int index = adjacencyList.size();
        adjacencyList.add(vertex);
        vertexCoordinates.put(index, new int[]{x, y});
    }

    private boolean checkEdge(Edge edge) {
        return edge.getHome() >= 0 && edge.getDestination() >= 0 && edge.getHome() != edge.getDestination();
    }

    private boolean checkArray(Edge edge) {
        return edge.getDestination() < adjacencyList.size() && edge.getHome() < adjacencyList.size();
    }

    public boolean addEdge(Edge edge) {
        if (checkEdge(edge) && checkArray(edge)) {
            adjacencyList.get(edge.getHome()).add(edge);
            adjacencyList.get(edge.getDestination()).add(edge); // Add edge to both vertices' lists
            return true;
        }
        return false;
    }

    public int[][] getWeights() {
        int size = adjacencyList.size();
        int[][] weights = new int[size][size];

        for (int i = 0; i < size; i++) {
            ArrayList<Edge> edges = adjacencyList.get(i);
            for (Edge edge : edges) {
                weights[edge.getHome()][edge.getDestination()] = edge.getWeight();
                weights[edge.getDestination()][edge.getHome()] = edge.getWeight();
            }
        }
        return weights;
    }

    public ArrayList<ArrayList<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    public ArrayList<Integer> getVertices() {
        ArrayList<Integer> vertices = new ArrayList<>();
        for (int i = 0; i < adjacencyList.size(); i++) {
            vertices.add(i);
        }
        return vertices;
    }

    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> edges = new ArrayList<>();
        for (ArrayList<Edge> edgeList : adjacencyList) {
            edges.addAll(edgeList);
        }
        return edges;
    }

    public int[] getVertexCoordinates(int vertex) {
        return vertexCoordinates.get(vertex);
    }

    public double getDistance(int vertex1, int vertex2) {
        int[] coordinates1 = vertexCoordinates.get(vertex1);
        int[] coordinates2 = vertexCoordinates.get(vertex2);

        if (coordinates1 == null || coordinates2 == null) {
            throw new IllegalArgumentException("Invalid vertex index.");
        }

        int x1 = coordinates1[0];
        int y1 = coordinates1[1];
        int x2 = coordinates2[0];
        int y2 = coordinates2[1];

        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public boolean isConnected(int vertex) {
        return !adjacencyList.get(vertex).isEmpty();
    }
}
