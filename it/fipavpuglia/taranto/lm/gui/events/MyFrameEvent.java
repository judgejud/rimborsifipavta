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
    private ArrayList<String[]> arrayS;
    private ArrayList<Float> arrayF;
    private Object[] arrayO;

    public MyFrameEvent(Object source, String _name, ArrayList<String[]> _array){
        super(source);
        nameDest = _name;
        arrayS = _array;
    }

    public MyFrameEvent(Object source, String _name, Object[] _array){
        super(source);
        nameDest = _name;
        arrayO = _array;
    }

    public MyFrameEvent(Object source, ArrayList<Float> _array, String _name){
        super(source);
        nameDest = _name;
        arrayF = _array;
    }

    public ArrayList<String[]> getArrayS() {
        return arrayS;
    }

    public ArrayList<Float> getArrayF() {
        return arrayF;
    }

    public Object[] getArrayO(){
        return arrayO;
    }

    public String getNameDest() {
        return nameDest;
    }
}