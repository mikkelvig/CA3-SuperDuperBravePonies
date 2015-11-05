/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currency;

/**
 *
 * @author steffen
 */
class CurrencyRunner implements Runnable {

    public XmlReaderDemo reader;
    
    public CurrencyRunner() {
           
        reader = new XmlReaderDemo();
        
    }

    @Override
    public void run() {
        
        reader.updateCurrency();
        
       
    }
    
}
