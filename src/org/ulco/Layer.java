package org.ulco;

import java.util.Vector;

public class Layer {
    //constructeur de calque
    public Layer() {
        m_list = new Vector<GraphicsObject>();
        m_ID = ID.getInstance().getId();
    }
    //constructeur de calque avec une ligne json
    public Layer(String json) {
        m_list = new Vector<GraphicsObject>();
        String str = json.replaceAll("\\s+", "");
        int objectsIndex = str.indexOf("objects");
        int endIndex = str.lastIndexOf("}");

        Utility.parse(str.substring(objectsIndex + 9, endIndex - 1), m_list, false);
        if(str.contains("groups")) {
            int groupsIndex = str.indexOf("groups");
            Utility.parse(str.substring(groupsIndex + 8, endIndex - 1), m_list, true);
        }
    }

    //ajouter une forme à la liste des formes présentes sur le calque
    public void add(GraphicsObject o) {
        m_list.add(o);
    }

    public GraphicsObject get(int index) {
        return m_list.elementAt(index);
    }

    public int getObjectNumber() {
        return m_list.size();
    }

    public int getID() {
        return m_ID;
    }

    //créer un GraphicsObjects qui contient tous les objets présents sur le calque
    public GraphicsObjects select(Point pt, double distance) {
        return Utility.selectInLayer(pt, distance, m_list);
    }

    //créer la ligne json permettant de créer un calque
    public String toJson() {
        String str = "{ type: layer, objects : { ";

        for (int i = 0; i < m_list.size(); ++i) {
            GraphicsObject element = m_list.elementAt(i);
            if (!element.isGroup) {
                str += element.toJson();
                if (i < m_list.size() - 1) {
                    str += ", ";
                }
            }
        }

        for (int i = 0; i < m_list.size(); ++i) {
            GraphicsObject element = m_list.elementAt(i);
            if (element.isGroup) {
                str += element.toJson();
            }
        }
        return str + " } }";
    }

    private Vector<GraphicsObject> m_list;
    private int m_ID;
}
