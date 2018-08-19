package my.news.news;


import java.util.ArrayList;
import java.util.Iterator;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;



public class AddProfile extends AddProfileDesign  implements View{

	private Profile profile = new Profile();
	
	public AddProfile() {
		
				
		save.setClickShortcut(KeyCode.ENTER);
		save.addClickListener(e -> save());
		stop.addClickListener(e -> stop());
		add.addClickListener(e -> add());
		
		
	}
	
	private void save() {
		
		if(topic.getValue() == null || name.getValue().isEmpty() ) {
			Notification message = new Notification("Interessensprofile muss Name und Themenbereich besitzen",Notification.TYPE_WARNING_MESSAGE);
			message.show(Page.getCurrent());
			
		}else {
						
			this.profile.setName(name.getValue());
			this.profile.setTopic(topic.getValue());
			
			ArrayList<String> arraywords = new ArrayList<String>();
			Iterator<Component> iterate = keywords.iterator();
			while(iterate.hasNext()) {
				String nextword = ((AbstractTextField) iterate.next()).getValue();
				if(!nextword.isEmpty()) {
				arraywords.add(nextword);
				}
			}
			
			this.profile.setWords(arraywords);
	
			VaadinService.getCurrentRequest().getWrappedSession().setAttribute("Profile", this.profile);
		      		    
			Notification message = new Notification("Interessensprofile gespeichert",Notification.TYPE_HUMANIZED_MESSAGE);
			message.setDelayMsec(1500);
			message.show(Page.getCurrent());	
		}

	}
	
	private void add() {
		/*Profile p1 = (Profile) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("Profile");
		Notification message = new Notification("Profile "+p1.getName(),Notification.TYPE_WARNING_MESSAGE);
		message.show(Page.getCurrent());*/
		
		TextField textField = new TextField();
		
		keywords.addComponent(textField);
	}
	
	//reloaded
	private void stop() {
		Page.getCurrent().reload();
	}
}
