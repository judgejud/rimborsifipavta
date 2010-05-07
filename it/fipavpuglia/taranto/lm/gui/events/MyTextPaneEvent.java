package it.fipavpuglia.taranto.lm.gui.events;

import java.util.EventObject;

/**
 *
 * @author luca
 */
@SuppressWarnings("serial")
public class MyTextPaneEvent extends EventObject{
    private String msg;
    private String type;

    public static final String ERROR = "ERROR";
    public static final String OK = "OK";
    public static final String ALERT = "ALERT";

    public MyTextPaneEvent(Object source, String _msg, String _type) {
        super(source);
        msg = _msg;
        type = _type;
    }

    public String getMsg() {
        return msg;
    }

    public String getType() {
        return type;
    }
}