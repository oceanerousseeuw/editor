package org.ulco;

import java.lang.reflect.Constructor;
import java.util.Vector;

public class JSON {
    //fonction pour reconnaitre quel type de forme il s'agit pour recréer la ligne json correspondante
    static public GraphicsObject parse(String json) {
        GraphicsObject o = null;
        String str = json.replaceAll("\\s+", "");
        String type = str.substring(str.indexOf("type") + 5, str.indexOf(","));

        try{
            String className = "org.ulco." + type.substring(0,1).toUpperCase() + type.substring(1);
            Constructor c = Class.forName(className).getConstructor(String.class);
            o= (GraphicsObject) c.newInstance(str);
        }catch(Exception e){
            System.out.println("probleme de class json");
        }
        return o;
    }

    static public Group parseGroup(String json) {
        return new Group(json);
    }

    static public Layer parseLayer(String json) {
        return new Layer(json);
    }

    static public Document parseDocument(String json) {
        return new Document(json);
    }

    static public String toJsonDoc(Vector<Layer> m_layers){
        String str = "{ type: document, layers: { ";

        for (int i = 0; i < m_layers.size(); ++i) {
            Layer element = m_layers.elementAt(i);

            str += element.toJson();
            if (i < m_layers.size() - 1) {
                str += ", ";
            }
        }
        return str + " } }";
    }

    static public String toJsonGroup(Vector<GraphicsObject> m_objectList){
        String str = "{ type: group, objects : { ";
        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            if (!element.isGroup) {
                str += element.toJson();
                if (i < m_objectList.size() - 1) {
                    str += ", ";
                }
            }
        }
        str += " }, groups : { ";

        for (int i = 0; i < m_objectList.size(); ++i) {
            GraphicsObject element = m_objectList.elementAt(i);
            if (element.isGroup) {
                str += element.toJson();
            }
        }
        return str + " } }";
    }

    static public String toJsonLayer(Vector<GraphicsObject> m_list){
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

    static public String toJsonPoint(double m_x, double m_y){
        return "{ type: point, x: " + m_x + ", y: " + m_y + " }";
    }

    static public String toJsonRect(Point m_origin, double m_height, double m_width){
        return "{ type: rectangle, center: " + m_origin.toJson() + ", height: " + m_height + ", width: " + m_width + " }";
    }

    static public String toJsonSquare(Point m_origin, double m_length){
        return "{ type: square, center: " + m_origin.toJson() + ", length: " + m_length + " }";
    }

    static public String toJsonCircle(Point m_center, double m_radius){
        return "{ type: circle, center: " + m_center.toJson() + ", radius: " + m_radius + " }";
    }
}
