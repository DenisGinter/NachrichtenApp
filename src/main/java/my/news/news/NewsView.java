package my.news.news;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class NewsView extends NewsViewDesign implements View{

	public NewsView() {	
       
		//nachrichten1.setValue("Nachrichten text Test 1");
		//nachrichten2.setValue("Nachrichten text Test 2");
	
		//newsContainer.addTab(texts, "Nachrichtenprofile 1");
		//newsContainer.addTab(textss ,"Nachrichtenprofile 2");
		
		Layout tab1 = new VerticalLayout(); // Wrap in a layout	
		Accordion newAccordion = new Accordion();
		TextArea textnews1 = new TextArea();
		textnews1.setValue("Gov. Doug Ducey, who made the selection, was facing a difficult balancing act in trying to please Trump supporters while not alienating McCain’s family and loyalists.");
		Layout tabnews1 = new VerticalLayout(); // Wrap in a layout
		tabnews1.addComponent(textnews1);
		textnews1.setReadOnly(true);
		newAccordion.addTab(tabnews1,"Jon Kyl, Former Senator, Will Replace McCain in Arizona");
		TextArea textnews2 = new TextArea();
		textnews2.setValue("Anybody who’s ever published even the slightest of critiques of Tesla’s Elon Musk can probably relate to this response from his supporters: “Don’t try to understand a mindset you will never have.");
		Layout tabnews2 = new VerticalLayout(); // Wrap in a layout
		tabnews2.addComponent(textnews2);
		textnews2.setReadOnly(true);
		newAccordion.addTab(tabnews2,"Poll: Elon Musk fans are the worst");
		
		tab1.addComponent(newAccordion);
		newsArea.addTab(tab1,"Profile 1");	
		
				
		Layout tab2 = new VerticalLayout(); // Wrap in a layout
		Accordion newAccordion2 = new Accordion();
		TextArea textnews3 = new TextArea();
		textnews3.setValue("The Mercedes-Benz EQC will be the brand's first all-electric production model SUV. It will go on sale in the United States in 2020.");
		Layout tabnews3 = new VerticalLayout(); // Wrap in a layout
		tabnews3.addComponent(textnews3);
		textnews3.setReadOnly(true);
		newAccordion2.addTab(tabnews3,"Mercedes-Benz reveals its first all-electric SUV");
		TextArea textnews4 = new TextArea();
		textnews4.setValue("American factory activity in August expanded at the strongest pace in more than 14 years, despite rising tensions with some of the U.S.’s largest trade partners.");
		Layout tabnews4 = new VerticalLayout(); // Wrap in a layout
		tabnews4.addComponent(textnews4);
		textnews4.setReadOnly(true);
		newAccordion2.addTab(tabnews4,"US Factory Sector Clocks Strongest Growth in 14 Years");
		
		tab2.addComponent(newAccordion2);
		newsArea.addTab(tab2,"Profile 2");	
		 

	}
}
