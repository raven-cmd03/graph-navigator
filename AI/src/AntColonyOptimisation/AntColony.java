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
        alpha = 0.5f;
        beta = 0.5f;
        generatePheromones();
        generateAnts();
    }

    /*
     * method to generate pheromone table. 
     */
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

    /*
     * method to generate ants for each iteration cycle.
     */
    public void generateAnts()
    {
        ants = new ArrayList<>();
        for(int i = 0 ; i < maxAnts ; i++)
        {
            ants.add(new Ant(alpha, beta, graph, pheromoneLevel, current, destination));
        }
    }

    /*
     * method that runs the ant colony optimisation.
     */
    public void run()
    {
        double start = System.currentTimeMillis();
        // for loop to run iterations of the optimisation.
        for(int i = 0 ; i < maxIterations ; i++)
        {
            ArrayList<Ant> toRemove = new ArrayList<>(); // array list to store ants to be removed that have reached a dead end.
            generateAnts();
            for(Ant a : ants)
            {
                while (a.getCurent() != destination && !a.getDeadEnd())
                {
                    a.move(); // move ants while they have not reached their deestination.
                }
                if(!a.getDeadEnd() && a.getCurent() == destination && a.getPathList().size() < pathLength) // if we want path with least weight
                {
                    // if this ant has the best path yet assign the bestpath and pathlength accordingly.
                    pathLength = a.getPathList().size(); // change this to pathlength = a.getPathLength
                    bestpath = a.getPathList();
                }
                if(a.getDeadEnd() && a.getCurent() != destination)
                {
                    // add ants to be removed.
                    toRemove.add(a);
                }
            }
            for(Ant a : toRemove)
            {
                ants.remove(a);
            }
            evaporate(); // evaporate pheromone on every index of pheromone table.
            depositPheromones(); // deposit pheromones where ants have travelled and found the goal.
        }
        double end = System.currentTimeMillis();
        System.out.println("Time taken is : " + (end - start) + " Alpha is : " + alpha + " Beta is : " + beta);
    }

    /**
     * method to deposit pheromones where ants have travelled.
     */
    public void depositPheromones()
    {
        for(Ant a : ants)
        {
            ArrayList<Integer> path = a.getPathList();
            for(int i = 0 ; i < path.size() - 1 ; i++)
            {
                pheromoneLevel[path.get(i)][path.get(i+1)] += 1.0f/path.size();
            }
        }
    }

    public ArrayList<Integer> getBestPath()
    {
        return bestpath;
    }

    /**
     * method to evaporate pheromones on every position.
     */
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
