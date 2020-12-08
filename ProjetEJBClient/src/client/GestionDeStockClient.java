package client;

import hw.GestionDeStock;
import hw.GestionDeStockRemote;
import hw.Produit;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class GestionDeStockClient {
   public static void main(String[] args) {
	try {
         Context context = new InitialContext();
         //GestionDeStock stock = (GestionDeStock) context.lookup("ejb:/ProjetEJB/GestionDeStock!hw.GestionDeStockRemote");
         GestionDeStockRemote stock= lookupRemoteStatelessGestionDeStock();
         Produit p = stock.ajouter(new Produit("Tomate", 100));
         // Petit affichage pour noter que l'objet récupéré à l'issue de l'ajout dispose bien de sa clé primaire auto générée
         System.out.println(p.getId()+" - "+p.getLibelle());
         p=stock.ajouter(new Produit("Pomme de terre", 5680));
         p=stock.ajouter(new Produit("Orange", 23));
         p=stock.ajouter(new Produit("Carotte", 115));
         p=stock.ajouter(new Produit("Muscadet", 48));
         List<Produit> produits = stock.listerTousLesProduits();
         for (Iterator iter = produits.iterator(); iter.hasNext();) {
            Produit eachProduit = (Produit) iter.next();
            System.out.println(eachProduit);
         }     

         p = stock.rechercherProduit(1);
         System.out.println("Produit recherché : ");
         System.out.println(p.getId()+" - "+p.getLibelle());
      } catch (NamingException e) {e.printStackTrace();}
   }
   
   // Connexion au serveur et lookup du bean
   private static GestionDeStockRemote lookupRemoteStatelessGestionDeStock() throws NamingException {
    GestionDeStockRemote remote=null;
     try {
         Properties jndiProps = new Properties();
         jndiProps.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
         InitialContext ctx = new InitialContext(jndiProps); 
         remote = (GestionDeStockRemote) ctx.lookup("ejb:/ProjetEJB/GestionDeStock!hw.GestionDeStockRemote");
          } catch (Exception e) {
         	 e.printStackTrace();
           }      
     return remote;
 }
}