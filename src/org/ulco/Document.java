package org.ulco;

import java.util.Iterator;
import java.util.Vector;

public class Document {
    //constructeur de document de base
    public Document() {
        m_layers = new Vector<Layer>();
    }

    //constructeur de document a partir d'une ligne json
    public Document(String json) {
        m_layers = new Vector<Layer>();
        String str = JSON.remplace(json);
        int layersIndex = str.indexOf("layers");
        int endIndex = str.lastIndexOf("}");

        parseLayers(str.substring(layersIndex + 8, endIndex));
    }

    public Document(Point origin, int line, int column, double length) {
        m_layers = new Vector<Layer>();
        Layer layer = createLayer();
        //lancer DocumentGrid
        layer = Utility.DocumentGrid(origin, line, column, length, layer);
    }

    public Document(Point center, int number, double radius, double delta){
        m_layers = new Vector<Layer>();
        Layer layer = createLayer();
        //lancer DocumentCircle
        layer = Utility.DocumentCircle(center, number, radius, delta, layer);
    }


    //créer un calque
    public Layer createLayer() {
        Layer layer = new Layer();

        m_layers.add(layer);
        return layer;
    }

    public int getLayerNumber() {
        return m_layers.size();
    }

    public int getObjectNumber() {
        int size = 0;

        for (int i = 0; i < m_layers.size(); ++i) {
            size += m_layers.elementAt(i).getObjectNumber();
        }
        return size;
    }

    private void parseLayers(String layersStr) {
        while (!layersStr.isEmpty()) {
            int separatorIndex = Utility.searchSeparator(layersStr);
            String layerStr;

            if (separatorIndex == -1) {
                layerStr = layersStr;
            } else {
                layerStr = layersStr.substring(0, separatorIndex);
            }
            m_layers.add(JSON.parseLayer(layerStr));
            if (separatorIndex == -1) {
                layersStr = "";
            } else {
                layersStr = layersStr.substring(separatorIndex + 1);
            }
        }
    }

    //retourner la liste des objets présents depuis un point sur une certaine distance
    public GraphicsObjects select(Point pt, double distance) {
        return Utility.selectInDoc(pt, distance, m_layers);

    }

    //créer la ligne json correspondant a toutes les formes ajoutées au document
    public String toJson() {
        return JSON.toJsonDoc(m_layers);
    }

    private Vector<Layer> m_layers;
}
