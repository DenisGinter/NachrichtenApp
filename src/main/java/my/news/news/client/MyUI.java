package my.news.news.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.io.IOUtils;
import org.vaadin.leif.headertags.HeaderTagHandler;
import org.vaadin.leif.headertags.Link;
import org.vaadin.leif.headertags.Meta;
import org.vaadin.leif.headertags.MetaTags;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import my.news.news.AddProfile;
import my.news.news.AdminProfile;
import my.news.news.Default;
import my.news.news.NewsView;

import com.vaadin.client.ui.VLabel;
import com.vaadin.client.ui.VUI;
import com.vaadin.client.ui.VVerticalLayout;
import com.vaadin.client.ui.VButton;
import com.vaadin.client.ui.VCssLayout;
import com.vaadin.client.ui.VHorizontalLayout;
import com.vaadin.client.ui.VMenuBar;
/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@JavaScript("vaadin://js/app.js")
@MetaTags({
	@Meta(name="viewport", content="width=device-width, initial-scale=1"),
	@Meta(name="theme-color", content="#404549")
})
@Link(rel="manifest", href="VAADIN/manifest.json")
public class MyUI extends VHorizontalLayout {
	 public static final String CLASSNAME = "myui";
	public MyUI (){
		
		
		VCssLayout viewContainer = new VCssLayout();
		VCssLayout menu = new VCssLayout();
		
		
		VLabel title1 = new VLabel("Menü");
		title1.addStyleName(ValoTheme.MENU_TITLE);
		viewContainer.add(title1);
		
		VLabel title = new VLabel("Menü");
		title.addStyleName(ValoTheme.MENU_TITLE);
				 
		VButton addProfileBtn = new VButton();
		addProfileBtn.setText("Interessenprofile hinzufügen");
		 //addProfileBtn.addClickHandler(e -> getNavigator().navigateTo("addProfileNav"));
	    addProfileBtn.addStyleName(ValoTheme.BUTTON_LINK);
	    
	    	
	    
	    VButton adminProfileBtn = new VButton();
	    adminProfileBtn.setText("Interessenprofile verwalten");
		 //addProfileBtn.addClickHandler(e -> getNavigator().navigateTo("addProfileNav"));
	   
	    adminProfileBtn.addStyleName(ValoTheme.MENU_ITEM);
	    
    	VButton newsViewBtn = new VButton();
    	newsViewBtn.setText("Nachrichten");
		 //addProfileBtn.addClickHandler(e -> getNavigator().navigateTo("addProfileNav"));
    	newsViewBtn.addStyleName(ValoTheme.BUTTON_LINK);
    	newsViewBtn.addStyleName(ValoTheme.MENU_ITEM);
	    
		menu.add(title);
		menu.add(addProfileBtn);
		menu.add(adminProfileBtn);
		menu.add(newsViewBtn);
		add(menu);
		add(viewContainer);
		
		
        
        
	}
        
        /*Navigator navigator = new Navigator(this, viewContainer);
        navigator.addView("", Default.class);
        navigator.addView("addProfileNav", AddProfile.class);
        navigator.addView("adminProfileNav", AdminProfile.class);
        navigator.addView("newsViewNav", NewsView.class);
        
	}
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	/*
	@Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	Label title = new Label("Menü");
    	title.addStyleName(ValoTheme.MENU_TITLE);
    	
    	Button addProfileBtn = new Button("Interessenprofile hinzufügen", e -> getNavigator().navigateTo("addProfileNav"));
    	addProfileBtn.addStyleNames(ValoTheme.BUTTON_LINK,ValoTheme.MENU_ITEM);
    	Button adminProfileBtn = new Button("Interessenprofile verwalten", e -> getNavigator().navigateTo("adminProfileNav"));
    	adminProfileBtn.addStyleNames(ValoTheme.BUTTON_LINK,ValoTheme.MENU_ITEM);
    	Button newsViewBtn = new Button("Nachrichten", e -> getNavigator().navigateTo("newsViewNav"));
    	newsViewBtn.addStyleNames(ValoTheme.BUTTON_LINK,ValoTheme.MENU_ITEM);
    	
    	
    	CssLayout menu = new CssLayout(title,addProfileBtn,adminProfileBtn, newsViewBtn);
    	menu.addStyleName(ValoTheme.MENU_ROOT);
    	
    	CssLayout viewContainer = new CssLayout();
    	
    	HorizontalLayout mainLayout = new HorizontalLayout(menu,viewContainer);
    	mainLayout.setSizeFull();
    	
        setContent(mainLayout);
        
        /*Navigator navigator = new Navigator(this, viewContainer);
        navigator.addView("", Default.class);
        navigator.addView("addProfileNav", AddProfile.class);
        navigator.addView("adminProfileNav", AdminProfile.class);
        navigator.addView("newsViewNav", NewsView.class);
    	
    }
    
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = true)
    public static class MyUIServlet extends VaadinServlet {
    	
		/**
		 * 
		 
		private static final long serialVersionUID = 1L;

		@Override
		protected void servletInitialized() throws ServletException {
			super.servletInitialized();

            HeaderTagHandler.init(getService());
			
			getService().addSessionInitListener(new SessionInitListener() {

				/**
				 * 
				 
				private static final long serialVersionUID = 1L;

				@Override
				public void sessionInit(SessionInitEvent event) throws ServiceException {
					event.getSession().addRequestHandler(new RequestHandler() {

						/**
						 * 
						 
						private static final long serialVersionUID = 1L;

						@Override
						public boolean handleRequest(VaadinSession session, VaadinRequest request,
								VaadinResponse response) throws IOException {

							String pathInfo = request.getPathInfo();
							InputStream in = null;

							if (pathInfo.endsWith("sw.js")) {
								response.setContentType("application/javascript");
								in = getClass().getResourceAsStream("/sw.js");
							}

							if (in != null) {
								OutputStream out = response.getOutputStream();
								IOUtils.copy(in, out);
								in.close();
								out.close();

								return true;
							} else {

								return false;
							}
						}
					});
				}
			});
		}
    }*/
}