package org.ulco;

abstract public class GraphicsObject {

    protected boolean isGroup=false;
    //constructeur de GraphicsObject, qui a un ID unique, et qui va utiliser la constructeur des classes filles
    public GraphicsObject() {
        m_ID = ID.getInstance().getId();
        m_bgColor = Utility.randomColor();
        m_borderColor = Utility.randomColor();
    }

    //créer un GraphicsObject par copie, utilisera la meme fonction dans la classe fille correspondante
    abstract public GraphicsObject copy();

    public int getID() {
        return m_ID;
    }

    public String getM_bgColor(){ return m_bgColor;}

    public String getM_borderColor() { return m_borderColor;}

    //retourne si la forme est près d'une point, utilise la fonction fille correspondante
    abstract boolean isClosed(Point pt, double distance);

    //deplace la forme a une nouvelle coordonnée, utilise la fonction fille correspondante
    abstract void move(Point delta);

    abstract public String toJson();

    abstract public String toString();

    private int m_ID;
    private String m_bgColor;
    private String m_borderColor;

}
