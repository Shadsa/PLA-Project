package XML;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import roles.Automate;
import roles.Cardinaux;
import roles.action.*;
import roles.conditions.*;

public class XML_Reader {
	
	@SuppressWarnings("null")
	public static ArrayList<Automate> readXML(File f) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, DOMException{
		ArrayList<Automate> liste = null;
		Automate automate = null;
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setIgnoringComments(true);	
	    factory.setIgnoringElementContentWhitespace(true);
	    try {
	       factory.setValidating(true);
	       DocumentBuilder builder = factory.newDocumentBuilder();
	       ErrorHandler errHandler = new DefaultHandler();
	       builder.setErrorHandler(errHandler);
	       File fileXML = f;
	       Document xml;
	       try {
	           xml = builder.parse(fileXML);
	           Element n = xml.getDocumentElement();
	   			if(n instanceof Element){
	   				if(n.getNodeName()=="nombre" && n.getAttributes() != null && n.getAttributes().getLength() == 1){
	   					int nbChild = n.getChildNodes().getLength();
	   					NodeList list = n.getChildNodes();
	   					for(int i = 0; i < nbChild; i++)
	   						automate = ListeCreate(list.item(i));
	   					liste.add(automate);
	   				}
	   			}
	       } catch (SAXParseException e) {}    
	    }catch (ParserConfigurationException e) {
	        e.printStackTrace();
	     } catch (SAXException e1) {
	        e1.printStackTrace();
	     } catch (IOException e2) {
	        e2.printStackTrace();
	     }
	    return liste;
	}	

	public static Automate ListeCreate(Node n) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, DOMException{
		Automate auto = null;
		if(n instanceof Element){
			if(n.getNodeName()=="nombre" && n.getAttributes() != null && n.getAttributes().getLength() == 1){
			   	int nbChild = n.getChildNodes().getLength();
				NodeList list = n.getChildNodes();		
				if(n.getNodeName()=="automate" && n.getAttributes() != null && n.getAttributes().getLength() > 0){
					NamedNodeMap att = n.getAttributes();
					if(att.getLength()==1 && att.item(0).getNodeName()=="name")
						auto = new Automate(Integer.parseInt(att.item(0).getNodeValue()));
					else throw new RuntimeException("Bad format XML.");
				}
				else throw new RuntimeException("Bad format XML.");
			    nbChild = n.getChildNodes().getLength();
				list = n.getChildNodes();
				
				for(int i = 0; i < nbChild; i++){
					Node n2 = list.item(i);
					if (n2 instanceof Element){
						if(n2.getNodeName()=="transition")
						AutomateCreate(auto, n2);
					}
				}
			}
		}
		else throw new RuntimeException("Bad Argument.");

		return auto;
	}
	
	
	public static void AutomateCreate(Automate auto, Node n) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, DOMException{
		@SuppressWarnings("null")
		int etat=(Integer) null, suiv=(Integer) null;
		Action action=null;
		Condition cond=null;
		
		if(n.getAttributes() != null && n.getAttributes().getLength() == 1){
				
			final NodeList trans = ((Document) n).getElementsByTagName("transition");
			final int nbElt = trans.getLength();
			
			if(nbElt == 4){
				Node node1 = trans.item(0);
				if(node1 instanceof Element && node1.getAttributes() == null){
					final Element e1 = (Element) ((NamedNodeMap) n).item(0);
					etat = Integer.parseInt(e1.getTextContent());
				}
				//etat = Integer.parseInt(node1.getAttributes().item(0).getNodeValue());
				Node node2 = trans.item(0);
				if(node2 instanceof Element && node2.getAttributes() != null && node2.getAttributes().getLength() == 1){
					final Element e2 = (Element) ((NamedNodeMap) n).item(0);
					String s1 = e2.getTextContent();
					cond = (Condition) Class.forName(s1).getDeclaredConstructor(Cardinaux.class).newInstance(node2.getAttributes().item(0).getNodeValue());
				}
				Node node3 = trans.item(0);
				if(node3 instanceof Element && node3.getAttributes() != null && node3.getAttributes().getLength() == 1){
					final Element e3 = (Element) ((NamedNodeMap) n).item(0);
					String s2 = e3.getTextContent();
					action = (Action) Class.forName(s2).getDeclaredConstructor(Cardinaux.class).newInstance(node3.getAttributes().item(0).getNodeValue());
				}				
				Node node4 = trans.item(0);
				if(node4 instanceof Element && node4.getAttributes() == null){
					final Element e4 = (Element) ((NamedNodeMap) n).item(0);
					suiv = Integer.parseInt(e4.getTextContent());
				}
			}
			auto.ajoute_transition(etat,action,cond,suiv);
		}
	}
}
