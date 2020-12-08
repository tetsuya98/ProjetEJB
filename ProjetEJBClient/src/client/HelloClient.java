package client;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import hw.HelloStateless;
import hw.HelloStatelessRemote;
public class HelloClient {
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
		System.out.println("***************** 2. Récupération de l'ejb *************************\n"
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
		System.out.println("***************** 1. Connexion au serveur d'applications *************************\n"
				+ "1.1 Récupération des informations de connexion au serveur dans le fichier jboss-ejb_client.properties");
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		
		System.out.println("1.2 Connexion au serveur");
		Context context=null;
		try {
			context = new InitialContext(jndiProperties);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return context;
	}
	
	public static void main(String[] args) {
		// Connexion au serveur d'application contenu dans Wildfly
		// Etape 1 connexion au serveur
		Context context=HelloClient.connexionServeurWildfly();
		
		// Etape 2 Récupération d'une référence vers l'ejb sur le serveur
		HelloStatelessRemote ejb= HelloClient.getEjbStateless(context);
		
		// Etape 3 Exécution de la méthode distante sayHello()
		System.out.println("***************** 3. Exécution d'une méthode sur le serveur via RMI *************************"
				+ "Demande d'éxécution de la méthode sayHello(\"Anne\") sur le serveur. Ceci est un appel distant."
				+ "Le paramètre de la fonction et sa valeur de retour seront sérialisés et circulent entre le client et le serveur");
		String retour = ejb.sayHello("Anne");
		System.out.println("Cet appel d'EJB renvoie la chaine : "+retour);
	}

}
