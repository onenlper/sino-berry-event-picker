package util.ace;

import java.util.ArrayList;

import mention.Entity;
import mention.EntityMention;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ValueXMLReader extends DefaultHandler {
		ArrayList<String> tags = new ArrayList<String>();
		ArrayList<Entity> entities;
		
		Entity entity;
		
		EntityMention entityMention;
		
		public ValueXMLReader(ArrayList<Entity> entities) {
			super();
			this.entities = entities;
		}
		
		public void startElement(String uri, String name, String qName,
				Attributes atts) {
			tags.add(qName);
			if(qName.equalsIgnoreCase("value")) {
				entity = new Entity();
				entities.add(entity);
			}
			if(qName.equalsIgnoreCase("value_mention")) {
				entityMention = new EntityMention();
				entity.mentions.add(entityMention);
				entityMention.entity = entity;
			}
			if(qName.equalsIgnoreCase("charseq")) {
				if(tags.get(tags.size()-2).equalsIgnoreCase("extent") && 
						tags.get(tags.size()-3).equalsIgnoreCase("value_mention") &&
						tags.get(tags.size()-4).equalsIgnoreCase("value")) {
					int start = Integer.valueOf(atts.getValue("START"));
					int end = Integer.valueOf(atts.getValue("END"));
					entityMention.start = start;
					entityMention.end = end;
				}
			}
		}

		public void characters(char ch[], int start, int length)
				throws SAXException {
			if(tags.get(tags.size()-1).equalsIgnoreCase("charseq") &&
					tags.get(tags.size()-2).equalsIgnoreCase("extent") && 
					tags.get(tags.size()-3).equalsIgnoreCase("value_mention") &&
					tags.get(tags.size()-4).equalsIgnoreCase("value")) {
				entityMention.extent += new String(ch, start, length);
			}
		}

		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			tags.remove(tags.size() - 1);
		}
	}