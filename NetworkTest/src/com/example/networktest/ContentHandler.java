package com.example.networktest;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ContentHandler extends DefaultHandler {
	
	private String nodeName;
	
	private StringBuilder id;
	private StringBuilder name;
	private StringBuilder version;
	
	private StringBuilder result;

	public String getResult() {
		return result.toString();
	}

	@Override
	public void startDocument() throws SAXException {
		
		id = new StringBuilder();
		name = new StringBuilder();
		version = new StringBuilder();
		
		result = new StringBuilder();

		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		nodeName = localName;
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if ("app".equals(localName)) {

			result.append("id is " + id.toString().trim() + "\n");
			result.append("name is " + name.toString().trim() + "\n");
			result.append("version is " + version.toString().trim() + "\n\n");

			id.setLength(0);
			name.setLength(0);
			version.setLength(0);
		}
		super.endElement(uri, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if ("id".equals(nodeName)) {
			id.append(ch, start, length);
		} else if ("name".equals(nodeName)) {
			name.append(ch, start, length);
		} else if ("version".equals(nodeName)) {
			version.append(ch, start, length);
		}
		super.characters(ch, start, length);
	}

}
