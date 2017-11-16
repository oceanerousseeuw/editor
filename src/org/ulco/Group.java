package org.ulco;

import java.util.Vector;

public class Group extends GraphicsObject {

    //constructeur de Group, qui aura un nouvel id unique a sa création
    public Group() {
        m_objectList = new Vector<GraphicsObject>();
        m_ID = ++ID.ID;
        isGroup = true;
    }

    //constructeur de Group a partir d'une ligne json
    public Group(String json) {
        m_objectList = new Vector<GraphicsObject>();
        isGroup = true;
        String str = json.replaceAll("\\s+", "");
        int objectsIndex = str.indexOf("objects");
        int groupsIndex = str.indexOf("groups");
        int endIndex = str.lastIndexOf("}");

        parseObjects(str.substring(objectsIndex + 9, groupsIndex - 2));
        parseGroups(str.substring(groupsIndex + 8, endIndex - 1));
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

    private int searchSeparator(String str) {
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

    private void parseGroups(String groupsStr) {
        while (!groupsStr.isEmpty()) {
            int separatorIndex = searchSeparator(groupsStr);
            String groupStr;

            if (separatorIndex == -1) {
                groupStr = groupsStr;
            } else {
                groupStr = groupsStr.substring(0, separatorIndex);
            }
            m_objectList.add(JSON.parseGroup(groupStr));
            if (separatorIndex == -1) {
                groupsStr = "";
            } else {
                groupsStr = groupsStr.substring(separatorIndex + 1);
            }
        }
    }

    private void parseObjects(String objectsStr) {
        while (!objectsStr.isEmpty()) {
            int separatorIndex = searchSeparator(objectsStr);
            String objectStr;

            if (separatorIndex == -1) {
                objectStr = objectsStr;
            } else {
                objectStr = objectsStr.substring(0, separatorIndex);
            }
            m_objectList.add(JSON.parse(objectStr));
            if (separatorIndex == -1) {
                objectsStr = "";
            } else {
                objectsStr = objectsStr.substring(separatorIndex + 1);
            }
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
}
