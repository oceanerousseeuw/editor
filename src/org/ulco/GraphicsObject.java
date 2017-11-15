package org.ulco;

abstract public class GraphicsObject {
    //constructeur de GraphicsObject, qui a un ID unique, et qui va utiliser la constructeur des classes filles
    public GraphicsObject() {
        m_ID = ++ID.ID;
    }

    //créer un GraphicsObject par copie, utilisera la meme fonction dans la classe fille correspondante
    abstract public GraphicsObject copy();

    public int getID() {
        return m_ID;
    }

    //retourne si la forme est près d'une point, utilise la fonction fille correspondante
    abstract boolean isClosed(Point pt, double distance);

    //deplace la forme a une nouvelle coordonnée, utilise la fonction fille correspondante
    abstract void move(Point delta);

    abstract public String toJson();

    abstract public String toString();

    private int m_ID;
}
