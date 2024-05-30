package EvolutionaryAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import GraphicClasses.DrawingCanvas;
import GraphClasses.Graph;

public class EvolutionaryAlgorithm {
    private Graph graph;
    private DrawingCanvas canvas;
    private int start;
    private int end;
    private int populationSize;
    private int generations;
    private double mutationRate;
    private ArrayList<Individual> population;

    public EvolutionaryAlgorithm(Graph graph, DrawingCanvas canvas, int start, int end, int populationSize, int generations, double mutationRate) {
        this.graph = graph;
        this.canvas = canvas;
        this.start = start;
        this.end = end;
        this.populationSize = populationSize;
        this.generations = generations;
        this.mutationRate = mutationRate;
        this.population = new ArrayList<>();

        // Create the initial population
        for (int i = 0; i < populationSize; i++) {
            population.add(new Individual(graph.getVertices().size()));
        }
    }

    public void run() {
        for (int generation = 0; generation < generations; generation++) {
            // Evaluate the fitness of each individual
            for (Individual individual : population) {
                individual.calculateFitness(graph, start, end);
            }

            // Sort the population by fitness
            Collections.sort(population, Comparator.comparingDouble(Individual::getFitness));

            // Create the next generation
            ArrayList<Individual> newPopulation = new ArrayList<>();
            for (int i = 0; i < populationSize / 2; i++) {
                // Select two parents
                Individual parent1 = population.get(i);
                Individual parent2 = population.get(i + 1);

                // Perform crossover
                Individual child1 = crossover(parent1, parent2);
                Individual child2 = crossover(parent2, parent1);

                // Perform mutation
                mutate(child1);
                mutate(child2);

                newPopulation.add(child1);
                newPopulation.add(child2);
            }

            population = newPopulation;
        }
    }

    private Individual crossover(Individual parent1, Individual parent2) {
        Random rand = new Random();
        ArrayList<Integer> genes = new ArrayList<>();
        int crossoverPoint = rand.nextInt(parent1.getGenes().size());
        genes.addAll(parent1.getGenes().subList(0, crossoverPoint));
        for (Integer gene : parent2.getGenes()) {
            if (!genes.contains(gene)) {
                genes.add(gene);
            }
        }
        return new Individual(genes);
    }

    private void mutate(Individual individual) {
        Random rand = new Random();
        if (rand.nextDouble() < mutationRate) {
            int index1 = rand.nextInt(individual.getGenes().size());
            int index2 = rand.nextInt(individual.getGenes().size());
            Collections.swap(individual.getGenes(), index1, index2);
        }
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }
}
