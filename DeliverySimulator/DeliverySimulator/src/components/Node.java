package components;
/**
 * @authors Ziv Abutbul - 205778939
 * 			Shlomo Shnur - 206067811
 * 
 */
public interface Node {
	
	public void collectPackage(Package p);
	public void deliverPackage(Package p);
	public void work();
	public String getName();
	
}
