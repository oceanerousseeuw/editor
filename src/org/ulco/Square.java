package org.ulco;

public class Square extends GraphicsObject {
    //constructeur de carré avec un centre et une taille
    public Square(Point center, double length) {
        this.m_origin = center;
        this.m_length = length;
    }

    //constructeur de carré a partir d'une ligne json
    public Square(String json) {
        String str = JSON.remplace(json);
        int centerIndex = str.indexOf("center");
        int lengthIndex = str.indexOf("length");
        int endIndex = str.lastIndexOf("}");

        m_origin = new Point(str.substring(centerIndex + 7, lengthIndex - 1));
        m_length = Double.parseDouble(str.substring(lengthIndex + 7, endIndex));
    }

    //création carré par copie, retrourne un GraphicsObject car carré est fille de GraphicsObject
    public GraphicsObject copy() {
        return new Square(m_origin.copy(), m_length);
    }

    public Point getOrigin() { return m_origin; }

    //retourne vrai si la taille du segment séparant le centre et le point est <= a la distance demandée
    public boolean isClosed(Point pt, double distance) {
        Point center = new Point(m_origin.getX() + m_length / 2, m_origin.getY() + m_length / 2);
        return Utility.isClosed(center, pt, distance);
    }

    //déplace le centre du carré a une nouvelle coordonnée
    void move(Point delta) { m_origin.move(delta); }

    public String toJson() {
        return JSON.toJsonSquareAndCircle(m_origin, m_length, type);
    }

    public String toString() {
        return "square[" + m_origin.toString() + "," + m_length + "]";
    }

    private final Point m_origin;
    private final double m_length;
    private String type = "square";
}
