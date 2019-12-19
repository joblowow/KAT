package com.sample;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.views.MainView;

public class Model {
	public static final int YESNO = 0; //ynXxxXxx
    public static final int MC = 1; // mcXxxXxx
    public static final int NUMB = 2; // nmXxxXxx
    public static final int OPEN = 3; // opXxxXxx
    
    public static final boolean ASK = true;
    public static final boolean DONTASK = false;
    
    public boolean allQuestionsAsked;
    
    public Fact facts[] = new Fact [100];
    
    public MainView frame;  
    
    public Model() {
    	this.allQuestionsAsked = false;
    } 
    
    public void createKnowledgeBase(Model model) {
	    try {
			// Load up the knowledge base
			KnowledgeBase kbase = readKnowledgeBase();
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			createQuestions(ksession, model);
		} catch (Throwable t) {
			t.printStackTrace();
	  	}	
    }
    
    public void createQuestions(StatefulKnowledgeSession ksession, Model model) {
    	ksession.setGlobal("gvalues", new Values());

        facts[0] = new Fact("mcHobProf", MC, ksession, "Do you want do farming as a (0) hobby or (1) professionally?", ASK);
        facts[1] = new Fact("ynShed", YESNO, ksession, "Do you have a shed? No (0) Yes (1)", ASK);    
        
        setWindow(model);
        facts[2] = new Fact("nmShedSize", NUMB, ksession, "How big is your shed (in meters squared)?");
         /* Fire all rules directly after nmShedSize question is in the fact base, so it fires instantly */
        ksession.fireAllRules();
        facts[3] = new Fact("ynLand", YESNO, ksession, "Do you have land (including the land you lease)? No (0) Yes (1)", ASK);
        facts[4] = new Fact("nmLandSize", NUMB, ksession, "How big is your land (in acres)?");
        ksession.fireAllRules();
        /* We can make a land size + possible lease size function */
        facts[5] = new Fact("ynNeigbourLease", YESNO, ksession, "Do your neighbours have a land that you can lease? No (0) Yes (1)", ASK);    
        facts[6] = new Fact("ynTractor", YESNO, ksession, "Do you have have a tractor? No (0) Yes (1)", ASK);
        facts[7] = new Fact("nmSheep", NUMB, ksession, "How many sheep would you like to have?", ASK);
        facts[8] = new Fact("mcBirth", MC, ksession, "Do you want do birthing (0) yourself or (1) let someone else do it?", ASK);
        /* Probably should add a class that saves all the needed money, so we can subtract this from the capitol */       
        facts[9] = new Fact("nmCapitol", NUMB, ksession, "How much capitol do you have to spend on the sheep business?", ASK);
        facts[10] = new Fact("ynShaveYourself", YESNO, ksession, "Do you want to shave yourself? No (0) Yes (1)", ASK);     
        /* How much time does it cost to shave yourself? How much money do you gain from selling wool per sheep */
        facts[11] = new Fact("ynShaveWool", YESNO, ksession, "Do you want to sell wool? No (0) Yes (1)", ASK);
        /* Simple rules like if you don't have one of these two "get them" could be added */
        facts[12] = new Fact("ynRegisteredUBN", YESNO, ksession, "Does your farm already have a unique business number (UBN)? No (0) Yes (1)", ASK);
        facts[13] = new Fact("ynRegisteredKvK", MC, ksession, "Is your farm already registered at the Kamer van Koophandel (KvK)? No (0) Yes (1)", ASK);
                 
        ksession.fireAllRules();
        System.out.println("The Result is ");
        ((Values) ksession.getGlobal("gvalues")).test();
        Fact.scanner.close();
    }

	private static void setWindow(Model model) {
		try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Throwable e) {
				e.printStackTrace();
			}
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MainView frame = new MainView(model);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("Rules.drl"), ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
	  
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}
}
	