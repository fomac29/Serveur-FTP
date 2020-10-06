package serveur;

import java.io.File;

/**
 * Classe representant la commande USER
 * @author Clement Stoliaroff
 */
public class CommandeUSER extends Commande {
	
	/**
	 * Constructeur de la commande USER
	 * @param client Le client qui a envoye la commande
	 * @param tabCommande La commande et ses arguments
	 */
	public CommandeUSER(ClientFTP client, String [] tabCommande) {
		super(client, tabCommande);
	}

	/**
	 * Execute la commande USER (Verifie l'identifiant du client)
	 */
	public void execute()
	{
		// Si un identifiant a bien ete saisi
		if(this.commandeArgs.length > 0)
		{
			// On recupere le dossier au nom de l'identifiant de l'utilisateur
			File user = new File(this.client.getCurrentDirectory() + "\\" + this.commandeArgs[0]);
		
			// Si le dossier existe
			if(user.exists())
			{
				// On met a jour le booleen indiquant que le client a saisi son identifiant
				client.setUserOk(true);
				
				// On met a jour le dossier root en entrant dans le dossier au nom de l'utilisateur
				client.setRootDirectory(client.getCurrentDirectory() + "\\" + this.commandeArgs[0]);
	
				// On met a jour le dossier courant du client pour qu'il soit definitivement le dossier a son nom
				client.setCurrentDirectory(client.getRootDirectory());
				ps.println("0 Commande user OK");
			}
		
			// On envoi un message d'erreur sinon
			else
			{
				ps.println("2 L'utilisateur " + commandeArgs[0] + " n'existe pas");
			}
		}

		// On envoi un message d'erreur sinon
		else
		{
			ps.println("2 Aucun identifiant n'a été saisi");
		}
	}
}
