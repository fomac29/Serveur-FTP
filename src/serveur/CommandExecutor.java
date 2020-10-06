package serveur;

/**
 * Classe destinee a executer les commandes du client
 * @author Clement Stoliaroff
 */
public class CommandExecutor {
	
	/**
	 * Determine la commande a executer
	 * @param commande La commande envoyee par le client
	 * @param client Le client qui a envoye la commande
	 */
	public static void executeCommande(String commande, ClientFTP client) {
		
		String [] tabCommande = commande.split(" ");
		
		// Si le client est connecte
		if(client.isPwOk() && client.isPwOk())
		{
			// Changer de repertoire. Un (..) permet de revenir au repertoire superieur
			if(tabCommande[0].equalsIgnoreCase("cd")) (new CommandeCD(client, tabCommande)).execute();
	
			// Telecharger un fichier
			else if(tabCommande[0].equalsIgnoreCase("get")) (new CommandeGET(client, tabCommande)).execute();
			
			// Afficher la liste des fichiers et des dossiers du repertoire courant
			else if(tabCommande[0].equalsIgnoreCase("ls")) (new CommandeLS(client, tabCommande)).execute();
		
			// Afficher le repertoire courant
			else if(tabCommande[0].equalsIgnoreCase("pwd")) (new CommandePWD(client, tabCommande)).execute();
			
			// Envoyer (uploader) un fichier
			else if(tabCommande[0].equalsIgnoreCase("stor")) (new CommandeSTOR(client, tabCommande)).execute();
			
			// Envoi d'une erreur en cas de commande invalide
			else
			{
				client.getPs().println("2 Commande non reconnue");
			}
		}
		
		// S'il ne l'est pas
		else
		{
			// Le login pour l'authentification
			if(tabCommande[0].equalsIgnoreCase("user") && client.isUserOk() == false) (new CommandeUSER(client, tabCommande)).execute();
			
			// Le mot de passe pour l'authentification
			else if(tabCommande[0].equalsIgnoreCase("pass") && client.isUserOk() == true && client.isPwOk() == false) (new CommandePASS(client, tabCommande)).execute();
			
			// Envoi d'une erreur en cas d'une autre commande
			else
			{
				client.getPs().println("2 Vous n'êtes pas connecté !");
			}
		}
	}

}
