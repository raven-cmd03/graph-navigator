package GraphicClasses;

import javax.swing.JFrame;

import GraphClasses.Graph;

public class MyFrame extends JFrame 
{

    public MyFrame()
    {
        add(new DrawingCanvas(new Graph()));
        setName("Graph");
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
}
