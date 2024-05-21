package AntColonyOptimisation;

import java.util.ArrayList;

public class AntColony
{
    private ArrayList<Ant> ants;
    private ArrayList<Integer> bestpath;
    private int pathLength;
    private int[][] graph;
    private float[][] pheromoneLevel;
    private float alpha;
    private float beta;
    private int maxAnts;
    private int maxIterations;
    private int current;
    private int destination;
    
    public AntColony(int[][] graph , int current , int destination)
    {
        this.graph = graph;
        this.current = current;
        this.destination = destination;
        maxAnts = 20;
        maxIterations = 10;
        bestpath = new ArrayList<>();
        pathLength = Integer.MAX_VALUE;
        generatePheromones();
        generateAnts();
    }

    private void generatePheromones()
    {
        pheromoneLevel = new float[graph.length][graph.length];
        for(int i = 0 ; i < pheromoneLevel.length ; i++)
        {
            for(int x = 0 ; x < pheromoneLevel[i].length ; x++)
            {
                pheromoneLevel[i][x] = 0.75f;
            }
        }
    }

    public void generateAnts()
    {
        ants = new ArrayList<>();
        for(int i = 0 ; i < maxAnts ; i++)
        {
            ants.add(new Ant(alpha, beta, graph, pheromoneLevel, current, destination));
        }
    }

    public void run()
    {
        for(int i = 0 ; i < maxIterations ; i++)
        {
            ArrayList<Ant> toRemove = new ArrayList<>();
            generateAnts();
            for(Ant a : ants)
            {
                while (a.getCurent() != destination && !a.getDeadEnd())
                {
                    a.move();
                }
                if(!a.getDeadEnd() && a.getCurent() == destination && a.getPathSize() < pathLength)
                {
                    pathLength = a.getPathSize();
                    bestpath = a.getPathList();
                }
                if(a.getDeadEnd() && a.getCurent() != destination)
                {
                    toRemove.add(a);
                }
            }
            for(Ant a : toRemove)
            {
                ants.remove(a);
            }
            evaporate();
            depositPheromones();
        }
    }

    public void depositPheromones()
    {
        for(Ant a : ants)
        {
            ArrayList<Integer> path = a.getPathList();
            for(int i = 0 ; i < path.size() - 1 ; i++)
            {
                pheromoneLevel[path.get(i)][path.get(i+1)] += 1.0f/pathLength;
            }
        }
    }

    public ArrayList<Integer> getBestPath()
    {
        return bestpath;
    }

    public void evaporate()
    {
        for(int i = 0 ; i < pheromoneLevel.length ; i++)
        {
            for(int x = 0 ; x < pheromoneLevel[i].length ; x++)
            {
                pheromoneLevel[i][x] *= (1.0f - 0.4f);
            }
        }
    }
}
