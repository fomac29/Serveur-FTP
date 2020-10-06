package application;

/**
 * Interface decrivant les controlleurs de l'IHM
 * @author Clement Stoliaroff
 */
public interface ControleurLocal
{
	/**
	 * Methode destinee a etre heritee par les controlleurs 
	 * @param controller le controlleur principal de l'application
	 */
	public abstract void exec(ControleurPrincipal controller);
}
