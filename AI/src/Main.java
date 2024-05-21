import java.awt.EventQueue;
import GraphicClasses.MyFrame;

public class Main 
{
    public static void main(String[] args) 
    {
        EventQueue.invokeLater(() ->
        {
            new MyFrame();
        });
    }
    
}
