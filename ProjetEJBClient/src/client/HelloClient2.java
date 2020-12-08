package client;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import hw.HelloStateless;
import hw.HelloStatelessRemote;

public class HelloClient2 {
	public static HelloStatelessRemote getEjbStateless(Context context)
	{
		HelloStatelessRemote ejb=null;
		System.out.println("Je suis getEjbStateless. Je m'exécute dans la JVM qui est sur la machine du client.");
		final String appName = "";
		final String moduleName = "ProjetEJB";
		final String distinctName = "";
		final String beanName = HelloStateless.class.getSimpleName();
		final String viewClassName = HelloStatelessRemote.class.getName();
				String jndi="ejb:/" + moduleName + "/" + beanName + "!" + viewClassName;
		System.out.println("***************** Récupération d'un ejb *************************\n"
				+ "Je récupère un pointeur vers l'objet qui est et reste sur le serveur en le cherchant via\n"
				+ "son jndi : "+jndi);
		try {
			ejb = (HelloStatelessRemote) context.lookup(jndi);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ejb;
	}

	public static Context connexionServeurWildfly()
	{
		// Connexion au serveur et récupération du pointeur distant
		System.out.println("***************** Connexion au serveur d'applications *************************\n"
				+ "Récupération des informations de connexion au serveur dans le fichier jboss-ejb_client.properties");
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		
		System.out.println("Connexion au serveur");
		Context context=null;
		try {
			context = new InitialContext(jndiProperties);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return context;
	}
	
	public static void testEJBStateless(Context context)
	{
		HelloStatelessRemote ejb=null;
		//*******************************************************************
		// Appel de plusieurs EJB
		// Vous rematrquerez que les instances de l'EJB sont partagées entre les clients
		String[] prenoms = {"Josselin", "Marcel", "Clémentine", "Jean", "Hippolyte"}; 
		// Liste des ejb sur lesquels on pointe
		ArrayList<HelloStatelessRemote> listeEjbUtilises = new ArrayList<HelloStatelessRemote>();
		for (String prenom : prenoms) {
			// Récupération de l'ejb
			ejb= HelloClient2.getEjbStateless(context);
			// Ajout de la référence d'ejb dans la liste
			listeEjbUtilises.add(ejb);
			System.out.println("***************** Exécution d'une méthode sur le serveur via RMI *************************");
			System.out.println("Demande d'éxécution de la méthode sur le serveur. Ceci est un appel distant");
			System.out.println("Je suis côté client, le serveur m'a envoyé : "+ejb.sayHello(prenom));	
		}
		System.out.println("Liste des ejb utilisés pendant l'exécution du programme");
		for (HelloStatelessRemote ejbUtilise : listeEjbUtilises)
		{
			System.out.println("Ejb numéro "+ ejbUtilise.getOid());
		}		
	}
	
	public static void main(String[] args) {
		// Connexion au serveur d'application contenu dans Wildfly
		Context context=HelloClient2.connexionServeurWildfly();
		testEJBStateless(context);
	}
}