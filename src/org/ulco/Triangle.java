package org.ulco;

public class Triangle extends GraphicsObject{
    public Triangle(Point corner, double height, double width) {
        this.m_origin = corner;
        this.m_height = height;
        this.m_width = width;
    }

    public Triangle(String json) {
        String str = JSON.remplace(json);
        int cornerIndex = str.indexOf("corner");
        int heightIndex = str.indexOf("height");
        int widthIndex = str.indexOf("width");
        int endIndex = str.lastIndexOf("}");

        m_origin = new Point(str.substring(cornerIndex + 7, heightIndex - 1));
        m_height = Double.parseDouble(str.substring(heightIndex + 7, widthIndex - 1));
        m_width = Double.parseDouble(str.substring(widthIndex + 6, endIndex));
    }

    public GraphicsObject copy() {
        return new Triangle(m_origin.copy(), m_height, m_width);
    }

    public Point getOrigin() { return m_origin; }

    public boolean isClosed(Point pt, double distance) {
        Point center = new Point(m_origin.getX() + m_width / 2, m_origin.getY() + m_height / 2);
        return Utility.isClosed(center, pt, distance);
    }

    void move(Point delta) { m_origin.move(delta); }

    public String toJson() {
        return JSON.toJsonTrian(m_origin, m_height, m_width);
    }

    public String toString() {
        return "triangle[" + m_origin.toString() + "," + m_height + "," + m_width + "]";
    }

    private final Point m_origin;
    private final double m_height;
    private final double m_width;
}
