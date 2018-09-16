package my.news.news.client;


import com.vaadin.client.ui.VLabel;


public class MyWidget extends VLabel  {
    public static final String CLASSNAME = "mywidget";

    public MyWidget() {
        setStyleName(CLASSNAME);
        setText("This my new is MyWidget");
    }
}