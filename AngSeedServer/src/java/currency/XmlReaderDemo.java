/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currency;

import entity.Currency;
import facades.CurrencyFacade;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author steffen
 */
public class XmlReaderDemo extends DefaultHandler {
    
    private String id;
    List<Currency> currencyList = new ArrayList();
    
    CurrencyFacade c = new CurrencyFacade();
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public void startDocument() throws SAXException {
//        System.out.println("Start Document (Sax-event)");
    }
    
    @Override
    public void endDocument() throws SAXException {
//        System.out.println("End Document (Sax-event)");
        try {
            c.saveCurrency(currencyList);
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        Currency cu = new Currency();
        for (int i = 0; i < attributes.getLength(); i++) {
            
            if (attributes.getValue(i) == null) {
                break;
            }
            
            String attribute = attributes.getLocalName(i);
            if (attribute.equals("id")) {
                setId(attributes.getValue(i));
            }
            if (attribute.equals("code")) {
                cu.setCode(attributes.getValue(i));
            }
            if (attribute.equals("desc")) {
                cu.setDescription(attributes.getValue(i));
            }
            if (attribute.equals("rate")) {
                if (attributes.getValue(i).equals("-")) {
                    cu.setRate(0);
                } else {
                    cu.setRate(Double.parseDouble(attributes.getValue(i)));
                }
            }
            cu.setDag(id);
        }

        currencyList.add(cu);
        
    }
    
    public void updateCurrency() {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            xr.setContentHandler(new XmlReaderDemo());
            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            xr.parse(new InputSource(url.openStream()));
            
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
