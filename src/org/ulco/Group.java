package org.ulco;

import java.util.Vector;

public class Group extends GraphicsObject {

    //constructeur de Group, qui aura un nouvel id unique a sa création
    public Group() {
        m_objectList = new Vector<GraphicsObject>();
        m_ID = ID.getInstance().getId();
        isGroup = true;
    }

    //constructeur de Group a partir d'une ligne json
    public Group(String json) {
        m_objectList = new Vector<GraphicsObject>();
        isGroup = true;
        String str = JSON.remplace(json);
        int objectsIndex = str.indexOf("objects");
        int groupsIndex = str.indexOf("groups");
        int endIndex = str.lastIndexOf("}");

        if(str.contains("groups")) {
            Utility.parse(str.substring(groupsIndex + 8, endIndex - 1), m_objectList, isGroup);
        }
        Utility.parse(str.substring(objectsIndex + 9, groupsIndex - 2), m_objectList, false);
    }

    //fonction qui va ajouter un GraphicObject au Group
    public void add(GraphicsObject object) {
        m_objectList.add(object);
    }

    //faire une copie du Group ne contenant que des GraphicsObject
    public Group copy() {
        Group g = new Group();

        for (Object o : m_objectList) {
            GraphicsObject element = (GraphicsObject) (o);

            g.add(element.copy());
        }
        return g;
    }

    public int getID() {
        return m_ID;
    }

    public boolean isClosed(Point pt, double distance) {
        return true;
    }

    //on va bouger le group élément par élément grace a la fonction fille correspondante
    public void move(Point delta) {
        Group g = new Group();

        for (Object o : m_objectList) {
            GraphicsObject element = (GraphicsObject) (o);
            element.move(delta);
        }
    }

    //connaitre le nombre d'objets, si c'est un group on regarde sa taille (sans le compter lui meme)
    public int size() {

        int size = 0;
        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            if (!element.isGroup) {
                size++;
            } else {
                size += m_objectList.size();
            }
        }
        return size;
    }

    public String toJson() {
        return JSON.toJsonGroupAndLayer(m_objectList, type);
    }

    public String toString() {
        String str = "group[[";
        boolean first = true;

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            if (!element.isGroup) {
                if (!first) {
                    str += ", ";
                    str += element.toString();
                } else {
                    str += element.toString();
                    first = false;
                }
            }


        }
        str += "],[";

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            if (element.isGroup) {
                str += element.toString();
            }
        }
        return str + "]]";
    }

    private Vector<GraphicsObject> m_objectList;
    private int m_ID;
    private String type = "group";
}
