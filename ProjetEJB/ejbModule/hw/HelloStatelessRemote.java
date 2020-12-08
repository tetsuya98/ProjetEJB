package hw;

import javax.ejb.Remote;

@Remote
public interface HelloStatelessRemote {
	public String sayHello(String prenom);
	public int getOid();
}
