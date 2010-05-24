package it.fipavpuglia.taranto.lm.gui.events;

import java.util.ArrayList;
import java.util.EventObject;
/**
 *
 * @author luca
 */
@SuppressWarnings("serial")
public class MyFrameEvent extends EventObject{
    private String nametable;
    private ArrayList<String[]> array;
    private ArrayList<Float> arrayF;

    public MyFrameEvent(Object source, String _name, ArrayList<String[]> _array){
        super(source);
        nametable = _name;
        array = _array;
    }

    public MyFrameEvent(Object source, ArrayList<Float> _array, String _name){
        super(source);
        nametable = _name;
        arrayF = _array;
    }

    public ArrayList<String[]> getArray() {
        return array;
    }

    public ArrayList<Float> getArrayF() {
        return arrayF;
    }

    public String getNametable() {
        return nametable;
    }
}