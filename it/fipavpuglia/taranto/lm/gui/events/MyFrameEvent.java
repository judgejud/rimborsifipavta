package it.fipavpuglia.taranto.lm.gui.events;

import java.util.ArrayList;
import java.util.EventObject;
/**
 *
 * @author luca
 */
@SuppressWarnings("serial")
public class MyFrameEvent extends EventObject{
    private String nameDest;
    private ArrayList<Object[]> arraylist;
    private String[] arrayString;
    private Float[] arrayFloat;

    public MyFrameEvent(Object source, String _name, ArrayList<Object[]> _array){
        super(source);
        nameDest = _name;
        arraylist = _array;
    }

    public MyFrameEvent(Object source, String _name, String[] array){
        super(source);
        nameDest = _name;
        arrayString = array;
    }

    public MyFrameEvent(Object source, String _name, Float[] array){
        super(source);
        nameDest = _name;
        arrayFloat = array;
    }

    public ArrayList<Object[]> getArrayList() {
        return arraylist;
    }    

    public String[] getArrayString(){
        return arrayString;
    }

    public Float[] getArrayFloat(){
        return arrayFloat;
    }

    public String getNameDest() {
        return nameDest;
    }
}