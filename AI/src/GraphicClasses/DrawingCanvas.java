package GraphicClasses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import javax.swing.JPanel;

import A_Star.Astar;
import A_Star.Node;
import AntColonyOptimisation.AntColony;
import EvolutionaryAlgorithm.EvolutionaryAlgorithm;
import EvolutionaryAlgorithm.Individual;
import GraphClasses.Edge;
import GraphClasses.Graph;

public class DrawingCanvas extends JPanel implements MouseListener, MouseMotionListener {
    private ArrayList<Button> buttonList;
    private ArrayList<Edge> edges;
    private Graph graph;
    private int[] edgePair;
    private int count;
    private boolean select;
    private Button searchACO;
    private Button searchAStar;
    private Button searchEA; // New button for Evolutionary Algorithm
    private boolean searched;

    public DrawingCanvas(Graph graph) {
        this.graph = graph;
        setSize(1000, 1000);
        setBackground(Color.gray);
        setVisible(true);
        buttonList = new ArrayList<>();
        edges = new ArrayList<>();
        edgePair = new int[1];
        count = -1;
        select = false;
        searchACO = new Button(new Point(900, 100), Color.green);
        searchAStar = new Button(new Point(900, 200), Color.pink);
        searchEA = new Button(new Point(900, 300), Color.yellow); // Initialize the new button
        searched = false;
        initialiseListeners();
        readCoordinatesFromFile("C:\\Users\\Aaraiz Masood\\OneDrive\\Desktop\\graph-navigator\\AI\\src\\GraphicClasses\\coordinates");
    }

    public void initialiseListeners() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);

        for (Edge e : edges) {
            e.drawEdge(g, buttonList.get(e.getHome()), buttonList.get(e.getDestination()));
        }
        for (Button b : buttonList) {
            if (!searched) {
                if (b.getHover()) {
                    b.setColor(Color.RED);
                } else if (b.getSelected()) {
                    b.setColor(Color.red);
                } else {
                    b.setColor(Color.blue);
                }
            }
            b.draw(g);
        }
        if (searchACO.getHover()) {
            searchACO.setColor(Color.red);
        } else if (searchACO.getSelected()) {
            searchACO.setColor(Color.black);
        } else {
            searchACO.setColor(Color.green);
        }
        if (searchAStar.getHover()) {
            searchAStar.setColor(Color.red);
        } else if (searchAStar.getSelected()) {
            searchAStar.setColor(Color.black);
        } else {
            searchAStar.setColor(Color.pink);
        }
        if (searchEA.getHover()) {
            searchEA.setColor(Color.red);
        } else if (searchEA.getSelected()) {
            searchEA.setColor(Color.black);
        } else {
            searchEA.setColor(Color.yellow);
        }
        searchAStar.draw(g);
        searchACO.draw(g);
        searchEA.draw(g); // Draw the new button
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!searched) {
            searchAStar.clicked(e.getX(), e.getY());
            searchACO.clicked(e.getX(), e.getY());
            searchEA.clicked(e.getX(), e.getY());
            if (searchAStar.getSelected()) {
                for (Button b : buttonList) {
                    b.clicked(e.getX(), e.getY());
                    if (count == -1 && b.getSelected()) {
                        count++;
                        edgePair[count] = buttonList.indexOf(b);
                        b.setSelected(true);
                        break;
                    } else if (count != -1 && b.getSelected() && edgePair[0] != buttonList.indexOf(b)) {
                        Astar astar = new Astar(graph, this, edgePair[0], buttonList.indexOf(b));
                        astar.run();
                        ArrayList<Node> searchPath = astar.getClosed();
                        for (Node n : searchPath) {
                            buttonList.get(n.getSelf()).setColor(Color.green);
                        }
                        searched = true;
                        clear();
                        count = -1;
                        searchAStar.setSelected(false);
                        break;
                    }
                }
            } else if (searchACO.getSelected()) {
                for (Button b : buttonList) {
                    b.clicked(e.getX(), e.getY());
                    if (count == -1 && b.getSelected()) {
                        count++;
                        edgePair[count] = buttonList.indexOf(b);
                        b.setSelected(true);
                        break;
                    } else if (count != -1 && b.getSelected() && edgePair[0] != buttonList.indexOf(b)) {
                        AntColony aco = new AntColony(graph.getWeights(), edgePair[0], buttonList.indexOf(b));
                        aco.run();
                        ArrayList<Integer> searchPath = aco.getBestPath();
                        for (Integer i : searchPath) {
                            buttonList.get(i).setColor(Color.green);
                        }
                        searched = true;
                        clear();
                        count = -1;
                        searchACO.setSelected(false);
                        break;
                    }
                }
            } else if (searchEA.getSelected()) {
                for (Button b : buttonList) {
                    b.clicked(e.getX(), e.getY());
                    if (count == -1 && b.getSelected()) {
                        count++;
                        edgePair[count] = buttonList.indexOf(b);
                        b.setSelected(true);
                        break;
                    } else if (count != -1 && b.getSelected() && edgePair[0] != buttonList.indexOf(b)) {
                        EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(graph, this, edgePair[0], buttonList.indexOf(b), 50, 100, 0.01);
                        ea.run();
                        ArrayList<Individual> population = ea.getPopulation();
                        Individual bestIndividual = population.get(0);
                        for (Integer i : bestIndividual.getGenes()) {
                            buttonList.get(i).setColor(Color.green);
                        }
                        searched = true;
                        clear();
                        count = -1;
                        searchEA.setSelected(false);
                        break;
                    }
                }
            } else {
                boolean edgeMade = false;
                for (Button b : buttonList) {
                    b.clicked(e.getX(), e.getY());
                    if (count == -1 && b.getSelected()) {
                        count++;
                        edgePair[count] = buttonList.indexOf(b);
                        select = true;
                        b.setSelected(select);
                        break;
                    } else if (count != -1 && b.getSelected()) {
                        Edge edge = new Edge(edgePair[0], buttonList.indexOf(b), getDistance(edgePair[0], buttonList.indexOf(b)));
                        if (graph.addEdge(edge)) {
                            edges.add(edge);
                            clear();
                            count = -1;
                            select = false;
                            edgeMade = true;
                            break;
                        }
                    }
                }
                if (!select && !edgeMade) {
                    graph.addVertice(e.getX(), e.getY());
                    buttonList.add(new Button(new Point(e.getX(), e.getY()), Color.blue));
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        searched = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!searched) {
            searchACO.isHover(e.getX(), e.getY());
            searchAStar.isHover(e.getX(), e.getY());
            searchEA.isHover(e.getX(), e.getY());
            if (!buttonList.isEmpty()) {
                for (Button b : buttonList) {
                    b.isHover(e.getX(), e.getY());
                }
            }
        }
    }

    private void clear() {
        for (Button b : buttonList) {
            if (b.getSelected()) {
                b.setSelected(false);
            }
        }
    }

    public int getDistance(int home, int destination) {
        int x = (int) Math.pow(buttonList.get(home).getPoint().getX() - buttonList.get(destination).getPoint().getX(), 2);
        int y = (int) Math.pow(buttonList.get(home).getPoint().getY() - buttonList.get(destination).getPoint().getY(), 2);
        return (int) (Math.sqrt(x + y));
    }

    private void readCoordinatesFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());
                if (x < 850) { // Ensure coordinates don't collide with the algorithm buttons
                    graph.addVertice(x, y);
                    buttonList.add(new Button(new Point(x, y), Color.blue));
                }
            }
            // Add edges between each vertex and randomly selected five other vertices
            for (int i = 0; i < buttonList.size(); i++) {
                ArrayList<Integer> selectedIndices = new ArrayList<>();
                for (int j = 0; j < 5; j++) {
                    int randomIndex;
                    do {
                        randomIndex = (int) (Math.random() * buttonList.size());
                    } while (randomIndex == i || selectedIndices.contains(randomIndex));
                    selectedIndices.add(randomIndex);
                    Edge edge = new Edge(i, randomIndex, getDistance(i, randomIndex));
                    if (graph.addEdge(edge)) {
                        edges.add(edge);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
