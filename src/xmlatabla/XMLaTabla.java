/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlatabla;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.XMLConstants;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
/**
 *
 * @author DobleL09
 */
public class XMLaTabla {

    /**
     * @param args the command line arguments
     */
     private static final String FILENAME = "staff.xml";
    public static void main(String[] args) {
        
        
        // TODO code application logic here
      // TODO code application logic here
     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

      try {

          // optional, but recommended
          // process XML securely, avoid attacks like XML External Entities (XXE)
          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

          // parse XML file
          DocumentBuilder db = dbf.newDocumentBuilder();

          Document doc = db.parse(new File(FILENAME));

          // optional, but recommended
       
          doc.getDocumentElement().normalize();

          System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
          System.out.println("------");

          // get <staff>
          NodeList list = doc.getElementsByTagName("staff");

          for (int temp = 0; temp < list.getLength(); temp++) {

              Node node = list.item(temp);

              if (node.getNodeType() == Node.ELEMENT_NODE) {

                  Element element = (Element) node;

                  // get staff's attribute
                  String id = element.getAttribute("id");

                  // get text
                  String firstname = element.getElementsByTagName("firstname").item(0).getTextContent();
                  String lastname = element.getElementsByTagName("lastname").item(0).getTextContent();
                  String nickname = element.getElementsByTagName("nickname").item(0).getTextContent();

                  NodeList salaryNodeList = element.getElementsByTagName("salary");
                  String salary = salaryNodeList.item(0).getTextContent();

                  // get salary's attribute
                  String currency = salaryNodeList.item(0).getAttributes().getNamedItem("currency").getTextContent();

                  
//-----------------------------------------INICIO

 

        EntityManager em;

        EntityManagerFactory emf;

        emf = javax.persistence.Persistence.createEntityManagerFactory("XMLaTablaPU");

        em = emf.createEntityManager();

        em.getTransaction().begin();

Staff c = new Staff();

c.setStaffid(element.getAttribute("id"));

c.setFisrtname(element.getElementsByTagName("firstname").item(0).getTextContent());   

c.setLastname(element.getElementsByTagName("lastname").item(0).getTextContent());   

c.setNickname(element.getElementsByTagName("nickname").item(0).getTextContent());   

c.setSalary(element.getElementsByTagName("salary").item(0).getTextContent());   

em.persist(c);

em.getTransaction().commit();                        

 

//-----------------------------------------FIN
                  
                  
                  System.out.println("Current Element :" + node.getNodeName());
                  System.out.println("Staff Id : " + id);
                  System.out.println("First Name : " + firstname);
                  System.out.println("Last Name : " + lastname);
                  System.out.println("Nick Name : " + nickname);
                  System.out.printf("Salary [Currency] : %,.2f [%s]%n%n", Float.parseFloat(salary), currency);

              }
          }

      } catch (ParserConfigurationException | SAXException | IOException e) {
          e.printStackTrace();
      }
    
    }
    
}
