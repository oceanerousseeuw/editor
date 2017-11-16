package org.ulco;

public class ID {
    //servira a definir des ID DIFFERENTS pour chaque forme créées
    private int ID = 1;

    private ID(){}

    private static ID id = null;

    public static synchronized ID getInstance(){
        if(id == null){
            id = new ID();
        }
        return id;
    }

    public int getId(){
        return ID++;
    }
}