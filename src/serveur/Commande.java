package serveur;

import java.io.PrintStream;

/**
 * Classe abstraite destinee a etre heritee par des classes executant les commandes FTP
 * @author Clement Stoliaroff
 */
public abstract class Commande {
	
	/**
	 * Le flot servant a envoyer les reponses au client
	 */
	protected PrintStream ps;
	
	/**
	 * Le client FTP qui a execute la commande
	 */
	ClientFTP client;
	
	/**
	 * Nom de la commande
	 */
	protected String commandeNom = "";
	
	/**
	 * Arguments de la commande
	 */
	protected String [] commandeArgs ;
	
	/**
	 * Constructeur des differentes commandes
	 * @param client Le client qui a envoye la commande
	 * @param tabCommande La commande et ses arguments
	 */
	public Commande(ClientFTP client, String [] tabCommande)
	{
		// On obtient le flot d'envoi des reponses
		this.ps = client.getPs();
		
		// On recupere le client 
		this.client = client;
		
		// On recupere le nom de la commande
		commandeNom = tabCommande[0];
		
		// On recupere les arguments de la commande dans un tableau
		commandeArgs = new String[tabCommande.length-1];
		
		for(int i = 0; i < commandeArgs.length; i++)
		{
			commandeArgs[i] = tabCommande[i+1];
		}
	}
	
	/**
	 * Methode commune aux classes filles permettant d'executer les commandes
	 * et d'envoyer les reponses au client
	 */
	public abstract void execute();

}