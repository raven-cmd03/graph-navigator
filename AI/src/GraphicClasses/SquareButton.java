package GraphicClasses;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class SquareButton 
{
    private Point point;
    private Color color;
    private boolean selected;
    private boolean hover;
    
    public SquareButton(Point point, Color color)
    {
        this.point = point;
        this.color = color;
        selected = false;
    }

    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillRect(point.getX(), point.getY(), 30, 30);
    }

    public void draw(Graphics g, String name)
    {
        Graphics2D g2 = (Graphics2D) g;

        // Set the stroke width
        g2.setStroke(new BasicStroke(5));

        // Draw the rectangle with stroke
        g2.setColor(color);
        g2.fillRect(point.getX(), point.getY(), 50, 50);
        g2.setColor(Color.BLACK); // Assuming the stroke color is black
        g2.drawRect(point.getX(), point.getY(), 50, 50);

        // Set bold font
        Font originalFont = g2.getFont();
        Font boldFont = originalFont.deriveFont(Font.BOLD);
        g2.setFont(boldFont);

        // Draw the string in the center of the button
        FontMetrics fm = g2.getFontMetrics();
        int stringWidth = fm.stringWidth(name);
        int stringHeight = fm.getAscent();

        int x = point.getX() + (50 - stringWidth) / 2;
        int y = point.getY() + (50 - stringHeight) / 2 + fm.getAscent();

        g2.setColor(Color.WHITE); // Assuming the text color is white
        g2.drawString(name, x, y);

        // Restore the original font
        g2.setFont(originalFont);
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    private boolean cursorOverlap(Point point)
    {
        return (point.getX() > this.point.getX() && point.getX() < this.point.getX() + 50 && point.getY() > this.point.getY() && point.getY() < this.point.getY() + 50);
    }

    public Point getPoint()
    {
        return point;
    }

    public void isHover(int x, int y)
    {
        if (cursorOverlap(new Point(x, y)))
        {
            hover = true;
        }
        else
        {
            hover = false;
        }
    }

    public void clicked(int x, int y)
    {
        if (cursorOverlap(new Point(x, y)))
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
