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

    static public int searchSeparator(String str) {
        int index = 0;
        int level = 0;
        boolean found = false;

        while (!found && index < str.length()) {
            if (str.charAt(index) == '{') {
                ++level;
                ++index;
            } else if (str.charAt(index) == '}') {
                --level;
                ++index;
            } else if (str.charAt(index) == ',' && level == 0) {
                found = true;
            } else {
                ++index;
            }
        }
        if (found) {
            return index;
        } else {
            return -1;
        }
    }

    static public void parseGroups(String groupsStr, Vector<GraphicsObject> m_list) {
        while (!groupsStr.isEmpty()) {
            int separatorIndex = Utility.searchSeparator(groupsStr);
            String groupStr;

            if (separatorIndex == -1) {
                groupStr = groupsStr;
            } else {
                groupStr = groupsStr.substring(0, separatorIndex);
            }
            m_list.add(JSON.parseGroup(groupStr));
            if (separatorIndex == -1) {
                groupsStr = "";
            } else {
                groupsStr = groupsStr.substring(separatorIndex + 1);
            }
        }
    }

    static public void parseObjects(String objectsStr, Vector<GraphicsObject> m_list) {
        while (!objectsStr.isEmpty()) {
            int separatorIndex = Utility.searchSeparator(objectsStr);
            String objectStr;

            if (separatorIndex == -1) {
                objectStr = objectsStr;
            } else {
                objectStr = objectsStr.substring(0, separatorIndex);
            }
            m_list.add(JSON.parse(objectStr));
            if (separatorIndex == -1) {
                objectsStr = "";
            } else {
                objectsStr = objectsStr.substring(separatorIndex + 1);
            }
        }
    }

}
