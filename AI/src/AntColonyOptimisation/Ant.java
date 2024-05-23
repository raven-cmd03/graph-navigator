package AntColonyOptimisation;

import java.util.ArrayList;
import java.util.Random;

public class Ant 
{
    private ArrayList<Integer> pathList;
    private float beta;
    private float alpha;
    private int[][] graph;
    private float[][] pheromoneLevel;
    private int current;
    private int destination;
    private boolean deadEnd;

    public Ant(float alpha , float beta , int[][] graph , float[][] pheromoneLevel , int current , int destination)
    {
        this.alpha = alpha;
        this.beta = beta;
        this.graph =  graph;
        this.pheromoneLevel = pheromoneLevel;
        pathList = new ArrayList<>();
        this.current = current;
        this.destination = destination;
        deadEnd = false;
    }
    
    /**
     * method to chose the next vertice to be travelled to by the ant.
     * @return position of vertice in graph.
     */
    private int chooseNode()
    {
        deadEnd = true;
        float total = 0.0f;
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i = 0 ; i < graph[current].length ; i++)
        {
            if(graph[i][current] != 0) // check if a path to this vertice exists.
            {
                total += sum(graph[i][current], pheromoneLevel[i][current]); // caclulate to total of the evaluation function.
                indexes.add(i); // add vertice to a list as possible path of travel
                deadEnd = false;
            }
        }
        if(!deadEnd)
        {
            Random r = new Random();
            float target = r.nextFloat(0,total); // generate random value between total and 0 to make a roullete wheel.
            for(int i = 0 ; i < indexes.size() ; i++)
            {
                target = target - sum(graph[indexes.get(i)][current], pheromoneLevel[indexes.get(i)][current]); // roullete wheel implementation.
                if (target <= 0) 
                {
                    return indexes.get(i);   
                }
            }
        }
        return -1;
    }

    public float sum(int weight , float pheromoneLevel)
    {
        float f = (float)(Math.pow(1.0f/weight, beta) * Math.pow(pheromoneLevel, alpha)); // (inverse of weight)^beta * (pheromone level on edge)^alpha
        return f;
    }

    public void move() // move ant until it reaches a dead end or the goal.
    {
        if(current != destination && !deadEnd)
        {
            pathList.add(current);
            current = chooseNode();
        }
        if(current == destination)
        {
            pathList.add(current);
        }
    }

    public ArrayList<Integer> getPathList()
    {
        return pathList;
    }

    public int getCurent()
    {
        return current;
    }

    public boolean getDeadEnd()
    {
        return deadEnd;
    }
}
