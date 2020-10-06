package serveur;

import java.io.File;

/**
 * Classe representant la commande PWD
 * @author Clement Stoliaroff
 */
public class CommandePWD extends Commande
{	
	/**
	 * Constructeur de la commande PWD
	 * @param client Le client qui a envoye la commande
	 * @param tabCommande La commande et ses arguments
	 */
	public CommandePWD(ClientFTP client, String [] tabCommande) {
		super(client, tabCommande);
	}

	/**
	 * Execute la commande PWD (retourne le chemin absolu du dossier courant)
	 */
	public void execute()
	{
		// On recupere le chemin absolu du dossier courant
		File file = new File(client.getCurrentDirectory());
		String s = file.getAbsoluteFile().toString();
		
		// On l'envoie au client
		ps.println("0 " + s);
	}

}
