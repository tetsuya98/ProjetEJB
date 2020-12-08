package hw;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class HelloStateless
 */
@Stateless(mappedName = "HelloStateless")
@LocalBean
public class HelloStateless implements HelloStatelessRemote {
	private static int nbobj=0;
	private int oid=0;

    public HelloStateless() {
        oid=++nbobj;
        System.out.println("******************************************************************************************");
        System.out.println("Je suis l'instance n°"+ oid +" de la class HelloStateless. Le serveur vient de me créer. "
        		+ "Je suis sur le serveur et j'attend qu'on appelles mes fonctions");
    }
    
    @Override
    public String sayHello(String prenom) {
        System.out.println("******************************************************************************************");
        System.out.println("Je suis la méthode sayHello de l'instance de HelloStateless numéro : "+ oid);
        String phrase = "Bonjour à toi " + prenom + " !";
        System.out.println("Je m'execute sur le serveur et je vais renvoyer la phrase "+ phrase +" au client");
		return phrase;
    }
    
    @Override
    public int getOid() {
    	return oid;
    }

}
