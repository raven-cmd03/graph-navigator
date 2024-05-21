package GraphicClasses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import AntColonyOptimisation.AntColony;
import GraphClasses.*;

public class DrawingCanvas extends JPanel implements MouseListener , MouseMotionListener
{
    private ArrayList<Button> buttonList;
    private ArrayList<Edge> edges;
    private Graph graph;
    private int[] edgePair;
    private int count;
    private boolean select;
    private Button search;
    private boolean searched;

    public DrawingCanvas(Graph graph)
    {
        this.graph = graph;
        setSize(1000, 1000);
        setBackground(Color.gray);
        setVisible(true);
        buttonList = new ArrayList<>();
        edges = new ArrayList<>();
        edgePair = new int[1];
        count = -1;
        select = false;
        search = new Button(new Point(100, 100), Color.green);
        searched = false;
        initialiseListeners();
    }

    public void initialiseListeners()
    {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paint(Graphics g)
    {
        super.paintComponent(g);

        for(Edge e : edges)
        {
            e.drawEdge(g, buttonList.get(e.getHome()), buttonList.get(e.getDestination()));
        }
        for(Button b : buttonList)
        {
            if(!searched)
            {
                if(b.getHover())
                {
                    b.setColor(Color.RED);
                }
                else if(b.getSelected())
                {
                    b.setColor(Color.red);
                }
                else
                {
                    b.setColor(Color.blue);
                }
            }
            b.draw(g);
        }
        if(search.getHover())
        {
            search.setColor(Color.red);
        }
        else if(search.getSelected())
        {
            search.setColor(Color.black);
        }
        else
        {
            search.setColor(Color.green);
        }
        search.draw(g);
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if(!searched)
        {
            search.clicked(e.getX(), e.getY());
            if(search.getSelected())
            {
                for(Button b : buttonList)
                {
                    b.clicked(e.getX(), e.getY());
                    if(count == -1 && b.getSelected())
                    {
                        count++;
                        edgePair[count] = buttonList.indexOf(b);
                        b.setSelected(true);
                        break;
                    }
                    else if (count != -1 && b.getSelected() && edgePair[0] != buttonList.indexOf(b))
                    {
                        AntColony aco = new AntColony(graph.getWeights(), edgePair[0], buttonList.indexOf(b));
                        aco.run();
                        ArrayList<Integer> searchPath = aco.getBestPath();
                        for(Integer i : searchPath)
                        {
                            buttonList.get(i).setColor(Color.green);
                            System.out.println(i);
                        }
                        searched = true;
                        clear();
                        count = -1;
                        search.setSelected(false);
                        break;
                    }
                }
            }
            else
            {
                boolean edgeMade = false;
                for(Button b : buttonList)
                {
                    b.clicked(e.getX(), e.getY());
                    if(count == -1 && b.getSelected())
                    {
                        count++;
                        edgePair[count] = buttonList.indexOf(b);
                        select = true;
                        b.setSelected(select);
                        break;
                    }
                    else if (count != -1 && b.getSelected())
                    {
                        Edge edge = new Edge(edgePair[0], buttonList.indexOf(b), getDistance(edgePair[0], buttonList.indexOf(b)));
                        if(graph.addEdge(edge))
                        {
                            edges.add(edge);
                            clear();
                            count = -1;
                            select = false;
                            edgeMade = true;
                            break;
                        }
                    }
                }
                if(!select && !edgeMade)
                {
                    graph.addVertice();
                    buttonList.add(new Button(new Point(e.getX(), e.getY()), Color.blue));
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {

    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        searched = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {

    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        if(!searched)
        {
            search.isHover(e.getX(), e.getY());
            if(!buttonList.isEmpty())
            {
                for(Button b : buttonList)
                {
                    b.isHover(e.getX(), e.getY());
                }
            }
        }
    }

    private void clear()
    {
        for(Button b : buttonList)
        {
            if(b.getSelected())
            {
                b.setSelected(false);
            }
        }
    }

    public int getDistance(int home , int destination)
    {
        int x = (int)Math.pow(buttonList.get(home).getPoint().getX() - buttonList.get(destination).getPoint().getX(), 2);
        int y = (int)Math.pow(buttonList.get(home).getPoint().getY() - buttonList.get(destination).getPoint().getY(), 2);
        return (int)(Math.sqrt(x+y));
    }
}
