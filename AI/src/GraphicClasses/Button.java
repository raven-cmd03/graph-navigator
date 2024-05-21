package GraphicClasses;
import java.awt.*;

public class Button 
{
    private Point point;
    private Color color;
    private boolean selected;
    private boolean hover;
    
    public Button(Point point , Color color)
    {
        this.point = point;
        this.color = color;
        selected = false;
    }

    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillOval(point.getX() - 30/2, point.getY() - 30/2, 30, 30);
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    private boolean cursorOverlap(Point point)
    {
        return ((Math.pow((this.point.getX() - point.getX()) , 2) + Math.pow(this.point.getY() - point.getY() , 2)) <= Math.pow(30 , 2));
    }

    public Point getPoint()
    {
        return point;
    }

    public void isHover(int x , int y)
    {
        if(cursorOverlap(new Point(x, y)))
        {
            hover = true;
        }
        else
        {
            hover = false;
        }
    }

    public void clicked(int x , int y)
    {
        if(cursorOverlap(new Point(x, y)))
        {
            selected = true;
        }
    }

    public boolean getHover()
    {
        return hover;
    }

    public boolean getSelected()
    {
        return selected;
    }

    public void setSelected(boolean b)
    {
        selected = b;
    }
}
