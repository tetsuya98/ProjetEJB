package client;

import java.util.ArrayList;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import hw.HelloStateful;
import hw.HelloStatefulRemote;

public class HelloClient3 {
	
	public static HelloStatefulRemote getEjbStateful(Context context)
	{
		HelloStatefulRemote ejb=null;
		System.out.println("Je suis getEjbStateful. Je m'exécute dans la JVM qui est sur la machine du client.");
		final String appName = "";
		final String moduleName = "ProjetEJB";
		final String distinctName = "";
		final String beanName = HelloStateful.class.getSimpleName();
		final String viewClassName = HelloStatefulRemote.class.getName();
		System.out.println("***************** 2. Récupération de l'ejb *************************");
		System.out.println("Récupération d'un pointeur vers l'objet qui est et reste sur le serveur");
		System.out.println("Looking EJB via JNDI ");
		String jndi="ejb:/" + moduleName + "/" + beanName + "!" + viewClassName+"?stateful";
		System.out.println(jndi);
		try {
			ejb = (HelloStatefulRemote) context.lookup(jndi);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		System.out.println("je renvoie une référence vers l'interface distante HelloStatefulRemote");		
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
	
	public static void testEJBStateful(Context context)
	{
		//****************************************************************
		// Premier test d'un EJB
		// Récupération d'un pointeur vers l'objet distant (via RMI)
		HelloStatefulRemote ejb= HelloClient3.getEjbStateful(context);
		System.out.println("***************** 3. Exécution d'une méthode sur le serveur via RMI *************************");
		System.out.println("Demande d'éxécution de la méthode sayHello(\"Josselin\") sur le serveur. Ceci est un appel distant.");
		System.out.println("Le paramètre de la fonction et sa valeur de retour seront sérialisés et circulent entre le client et le serveur");
		String phraseEnvoyeeParServeur = ejb.sayHello("Josselin");		
		//*******************************************************************
		// Appel de plusieurs EJB
		// Vous rematrquerez que les instances de l'EJB sont partagées entre les clients
		String[] prenoms = {"Josselin", "Marcel", "Clémentine", "Jean", "Hippolyte"}; 
		// Liste des ejb sur lesquels on pointe
		ArrayList<HelloStatefulRemote> listeEjbUtilises = new ArrayList<HelloStatefulRemote>();
		for (String prenom : prenoms) {
			ejb= HelloClient3.getEjbStateful(context);
			listeEjbUtilises.add(ejb);
			System.out.println("***************** 3. Exécution d'une méthode sur le serveur via RMI ************************");
			System.out.println("Demande d'éxécution de la méthode sur le serveur. Ceci est un appel distant");
			phraseEnvoyeeParServeur = ejb.sayHello(prenom);
			System.out.println("Je suis côté client, le serveur m'a envoyé : "+phraseEnvoyeeParServeur);	
		}
		System.out.println("Liste des ejb utilisés pendant l'exécution du programme");
		for (HelloStatefulRemote ejbUtilise : listeEjbUtilises)
		{
			System.out.println("Ejb numéro "+ ejbUtilise.getOid());
		}
	}
	
	public static void main(String[] args) {
		// Connexion au serveur d'application contenu dans Wildfly
		Context context=HelloClient3.connexionServeurWildfly();
		testEJBStateful(context);
	}


}
