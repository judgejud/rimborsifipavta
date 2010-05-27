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
    //private ArrayList<Float> arrayF;
    private Object[] array;

    public MyFrameEvent(Object source, String _name, ArrayList<Object[]> _array){
        super(source);
        nameDest = _name;
        arraylist = _array;
    }

    public MyFrameEvent(Object source, String _name, Object[] _array){
        super(source);
        nameDest = _name;
        array = _array;
    }
/*
    public MyFrameEvent(Object source, ArrayList<Float> _array, String _name){
        super(source);
        nameDest = _name;
        arrayF = _array;
    }
    public ArrayList<Float> getArrayF() {
        return arrayF;
    }
*/
    public ArrayList<Object[]> getArrayList() {
        return arraylist;
    }    

    public Object[] getArrayO(){
        return array;
    }

    public String getNameDest() {
        return nameDest;
    }
}