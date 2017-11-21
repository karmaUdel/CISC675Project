/**
 * 
 */
package gui_learning;

/**
 * @author Aditya
 * Write the Fxml file used as GUI design element file
 */



import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FXMLWriter {
    public static final String HEAD ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
    		"\r\n" + 
    		"<?import java.net.*?>\r\n" + 
    		"<?import javafx.geometry.*?>\r\n" + 
    		"<?import javafx.scene.control.*?>\r\n" + 
    		"<?import javafx.scene.layout.*?>\r\n" + 
    		"<?import javafx.scene.text.*?>"; 
	public void builder(int numberOffloors, int numberOfElevators) {

	  try {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		
		Element rootElement = doc.createElement("AnchorPane");
		doc.appendChild(rootElement);

		// staff elements

		// set attribute to staff element

		// shorten way
		rootElement.setAttribute("fx:controller", "ClassName");
		rootElement.setAttribute("xmlns:fx", "http://javafx.com/fxml");
		rootElement.setAttribute("alignment", "center");
		rootElement.setAttribute("prefHeight", "1080");
		rootElement.setAttribute("prefWidth", "1920");
		
		Element leftAnchorPane = doc.createElement("Pane");
		leftAnchorPane.setAttribute("prefHeight", "1080");
		System.out.println((1920/(numberOfElevators+1)));
		leftAnchorPane.setAttribute("prefWidth", ""+(1920/(numberOfElevators+1)));
		rootElement.appendChild(leftAnchorPane);
		int pos =0;
		// firstname elements
		for(int i = 0; i < numberOffloors; i++) {
			Element buttonUp = doc.createElement("button");
			Element buttonDown = doc.createElement("button");
				buttonUp.setAttribute("text", "Up");
			 	pos = i *20;
			 	buttonUp.setAttribute("AnchorPane.topAnchor", ""+pos);
			 	buttonUp.setAttribute("AnchorPane.leftAnchor", ""+0);
			 	buttonUp.setAttribute("AnchorPane.rightAnchor", ""+0);
			 	buttonDown.setAttribute("text", "Down");
			 	pos = pos+10;
			 	buttonDown.setAttribute("AnchorPane.topAnchor", ""+pos);
			 	buttonDown.setAttribute("AnchorPane.leftAnchor", ""+0);
			 	buttonDown.setAttribute("AnchorPane.rightAnchor", ""+0);
			Element label = doc.createElement("Label");
			label.setAttribute("text", "Floor "+(i+1));
			 	leftAnchorPane.appendChild(label);
			 	leftAnchorPane.appendChild(buttonUp);
			 	leftAnchorPane.appendChild(buttonDown);
		}// creating all buttons

		// lastname elements
		for(int i = 0;i<numberOfElevators;i++) {
			Element elevatorPane = doc.createElement("Pane");		
				elevatorPane.setAttribute("prefHeight", "1080");
				elevatorPane.setAttribute("prefWidth", ""+(1920/(numberOfElevators+1)));
			Element elevator = doc.createElement("Rectangle");
				elevator.setAttribute("height", "20");
				elevator.setAttribute("width", "20");				
				elevator.setAttribute("fill", "#000000");
			elevatorPane.appendChild(elevator);
			//create Buttons
			int maxRow = numberOffloors/2;
			pos = 1000;
			//TODO : lookup the locations
			for(int j = 0; j < maxRow; j++) {
				Element buttonLeft = doc.createElement("button");
				Element buttonRight = doc.createElement("button");
				buttonLeft.setAttribute("text", ""+((j*2)+1));
				 	pos =1000+ j *20;
				 	buttonLeft.setAttribute("Pane.topAnchor", ""+pos);
				 	buttonLeft.setAttribute("Pane.leftAnchor", ""+0);
				 	buttonLeft.setAttribute("Pane.rightAnchor", ""+0);
				 	buttonRight.setAttribute("text", ""+((j*2)+2));
				 	pos = pos+10;
				 	buttonRight.setAttribute("Pane.topAnchor", ""+pos);
				 	buttonRight.setAttribute("Pane.leftAnchor", ""+0);
				 	buttonRight.setAttribute("Pane.rightAnchor", ""+0);
				 	elevatorPane.appendChild(buttonLeft);
				 	elevatorPane.appendChild(buttonRight);
			}// creating all buttons

			rootElement.appendChild(elevatorPane);
		}
		//staff.appendChild(lastname);

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		String finalop = HEAD + source.toString();
		File dir = new File(System.getProperty("user.dir")+"/config");
		File file = new File(System.getProperty("user.dir")+"/config/uiConfig.fxml");
		if(!dir.exists()) {
			dir.mkdir();
			// mostly files won't be created if we don't have directory
			// so create one
		
		}
		if(!file.exists()) {
			try {
				file.createNewFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		StreamResult result = new StreamResult(file);

		// Output to console for testing
		//StreamResult result1 = new StreamResult(System.out);
		//source  = finalop.
		transformer.transform(source, result);
		//transformer.transform(source, result1);
		//StreamSource src = new StreamSource(HEAD+result1.toString());
		//transformer.transform(src, result1);
		
		System.out.println("File saved!");

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
	
	public static void main(String args[]) {
		FXMLWriter write = new FXMLWriter();
		write.builder(10, 5);
	}
}