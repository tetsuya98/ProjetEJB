package hw;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

@Stateful
@LocalBean
public class HelloStateful implements HelloStatefulRemote {
	private static int nbobj=0;
	private int oid=0;
	
    public HelloStateful() {
    	oid=++nbobj;
    	System.out.println("************************************************************************************************");
        System.out.println("Je suis l'instance n° "+oid+" de la classe HelloStateful. Le serveur vient de me créer. "
        		+ "Je suis sur le serveur et j'attends que MON CLIENT appelle mes fonctions");
    }
    
	@Override
	public String sayHello(String prenom) {
	  	System.out.println("************************************************************************************************");		
		System.out.println("Je suis la méthode sayHello de l'instance de HelloStateful numéro : "+oid);
		String phrase = "Bonjour à toi "+prenom +"!";
		System.out.println("Je m'exécute sur le serveur et je vais renvoyer la phrase "+ phrase + " au client");
		return phrase;		
	}

	@Override
	public int getOid() {
		return oid;
	}
}