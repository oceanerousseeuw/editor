package org.ulco;

public class Circle extends GraphicsObject {
    //constructeur de cercle avec un point de centre et un rayon
    public Circle(Point center, double radius) {
        this.m_center = center;
        this.m_radius = radius;
    }

    //constructeur de cercle en fromat json
    public Circle(String json) {
        String str = json.replaceAll("\\s+", "");
        int centerIndex = str.indexOf("center");
        int radiusIndex = str.indexOf("radius");
        int endIndex = str.lastIndexOf("}");

        m_center = new Point(str.substring(centerIndex + 7, radiusIndex - 1));
        m_radius = Double.parseDouble(str.substring(radiusIndex + 7, endIndex));
    }

    //fonction qui crée un nouveau cercle par recopie
    public GraphicsObject copy() {
        return new Circle(m_center.copy(), m_radius);
    }

    public Point getCenter() { return m_center; }

    //retourne vrai si la taille du segment séparant le centre et le point est <= a la distance demandée
    public boolean isClosed(Point pt, double distance) {
        return Utility.isClosed(m_center, pt, distance);
    }

    void move(Point delta) { m_center.move(delta); }

    //crée une ligne json en utilisant le toJson du point
    public String toJson() {
        return JSON.toJsonCircle(m_center, m_radius);
    }

    //utilise le toString du point
    public String toString() {
        return "circle[" + m_center.toString() + "," + m_radius + "]";
    }

    private final Point m_center;
    private final double m_radius;
}
