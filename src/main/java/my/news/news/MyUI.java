package my.news.news;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	Label title = new Label("Menü");
    	title.addStyleName(ValoTheme.MENU_TITLE);
    	
    	Button addProfileBtn = new Button("Interessenprofile hinzufügen", e -> getNavigator().navigateTo("addProfileNav"));
    	addProfileBtn.addStyleNames(ValoTheme.BUTTON_LINK,ValoTheme.MENU_ITEM);
    	Button adminProfileBtn = new Button("Interessenprofile verwalten", e -> getNavigator().navigateTo("adminProfileNav"));
    	adminProfileBtn.addStyleNames(ValoTheme.BUTTON_LINK,ValoTheme.MENU_ITEM);
    	
    	CssLayout menu = new CssLayout(title,addProfileBtn,adminProfileBtn);
    	menu.addStyleName(ValoTheme.MENU_ROOT);
    	
    	CssLayout viewContainer = new CssLayout();
    	
    	HorizontalLayout mainLayout = new HorizontalLayout(menu,viewContainer);
    	mainLayout.setSizeFull();
    	
        setContent(mainLayout);
        
        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addView("", Default.class);
        navigator.addView("addProfileNav", AddProfile.class);
        navigator.addView("adminProfileNav", AdminProfile.class);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
