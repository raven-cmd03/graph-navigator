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
    
    private int chooseNode()
    {
        deadEnd = true;
        float total = 0.0f;
        ArrayList<Integer> indexes = new ArrayList<>();
        for(int i = 0 ; i < graph[current].length ; i++)
        {
            if(graph[i][current] != 0)
            {
                total += sum(graph[i][current], pheromoneLevel[i][current]);
                indexes.add(i);
                deadEnd = false;
            }
        }
        if(!deadEnd)
        {
            Random r = new Random();
            float target = r.nextFloat(0,total);
            for(int i = 0 ; i < indexes.size() ; i++)
            {
                target = target - sum(graph[indexes.get(i)][current], pheromoneLevel[indexes.get(i)][current]);
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
        float f = (float)(Math.pow(1.0f/weight, beta) * Math.pow(pheromoneLevel, alpha));
        return f;
    }

    public void move()
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
