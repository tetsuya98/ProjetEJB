package hw;

import javax.ejb.Remote;

@Remote
public interface HelloStatefulRemote {
	public String sayHello(String prenom);
	public int getOid();
}
