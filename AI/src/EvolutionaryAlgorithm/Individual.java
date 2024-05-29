package EvolutionaryAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import GraphClasses.Graph;

public class Individual {
    private ArrayList<Integer> genes;
    private double fitness;

    public Individual(int numberOfGenes) {
        genes = new ArrayList<>(numberOfGenes);
        for (int i = 0; i < numberOfGenes; i++) {
            genes.add(i);
        }
        Collections.shuffle(genes, new Random());
        fitness = 0.0;
    }

    public Individual(ArrayList<Integer> genes) {
        this.genes = new ArrayList<>(genes);
        fitness = 0.0;
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public double getFitness() {
        return fitness;
    }

    public void calculateFitness(Graph graph, int start, int end) {
        fitness = 0.0;
        for (int i = 0; i < genes.size() - 1; i++) {
            fitness += graph.getDistance(genes.get(i), genes.get(i + 1));
        }
        fitness += graph.getDistance(start, genes.get(0));
        fitness += graph.getDistance(genes.get(genes.size() - 1), end);
    }
}
