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

    //constructeur de document avec une grille
    static public Layer DocumentGrid(Point origin, int line, int column, double length, Layer layer) {

        for (int indexX = 0; indexX < column; ++indexX) {
            for (int indexY = 0; indexY < line; ++indexY) {
                layer.add(new Square(new Point(origin.getX() + indexX * length, origin.getY() + indexY * length), length));
            }
        }

        return layer;
    }

    //constructeur de document avec des cercles
    static public Layer DocumentCircle(Point center, int number, double radius, double delta, Layer layer) {

        for (int index = 0; index < number; ++index) {
            layer.add(new Circle(center, radius + index * delta));
        }

        return layer;
    }

}
