package org.ulco;

import java.util.Vector;

public class Utility {


    static public GraphicsObjects selectInDoc(Point pt, double distance, Vector<Layer> m_layers) {
        GraphicsObjects list = new GraphicsObjects();

        for (Layer layer : m_layers) {
            list.addAll(layer.select(pt, distance));
        }
        return list;
    }

    static public GraphicsObjects selectInLayer(Point pt, double distance, Vector<GraphicsObject> m_list) {
        GraphicsObjects list = new GraphicsObjects();

        for (GraphicsObject object : m_list) {
            if (object.isClosed(pt, distance)) {
                list.add(object);
            }
        }
        return list;
    }

}
