package serveur;

import java.io.File;


/**
 * Classe representant la commande CD
 * @author Clement Stoliaroff
 */
public class CommandeCD extends Commande
{
	/**
	 * Constructeur de la commande CD
	 * @param client Le client qui a envoye la commande
	 * @param tabCommande La commande et ses arguments
	 */
	public CommandeCD(ClientFTP client, String [] tabCommande) {
		super(client, tabCommande);
	}

	/**
	 * Execute la commande CD (change de repertoire courant et envoie le nouveau dossier courant au client)
	 */
	public void execute()
	{
		// On envoie un message d'erreur si aucun dossier n'est entre en parametre
		if(this.commandeArgs.length == 0)
		{
			ps.println("2 Aucun dossier sélectionné");
		}
		
		// Sinon, si le nom du dossier ne contiens pas 2 points
		else if(!(this.commandeArgs[0].contains("..")))
		{
			// On recupere le nom du dossier
			String folderName = this.commandeArgs[0];
			
			// On reconstitue le nom du dossier si celui-ci contient des espaces
			for(int i = 1; i < this.commandeArgs.length; i++)
			{
				folderName += " " + this.commandeArgs[i];
			}
			
			// On concatene le dossier courant avec le nom du dossier
			File folder = new File(client.getCurrentDirectory() + "\\" + folderName);
			
			// Si le dossier n'existe pas, on envoie un message d'erreur
			if(folder.exists() == false)
			{
				ps.println("2 Le dossier demandé n'existe pas");
			}
			 // Si le nom existe, mais que ce n'est pas un dossier, on fait de meme
			else if(folder.isDirectory() == false)
			{
				ps.println("2 " + folderName + " n'est pas un dossier");
			}
			
			// Sinon, on ajoute le dossier au dossier courant et on envoie celui-ci au client
			else
			{
				client.setCurrentDirectory(client.getCurrentDirectory() + "\\" + folderName);
				ps.println("0 " + client.getCurrentDirectory());
			}
		}
		
		else
		{
			if(!(client.getCurrentDirectory().equals(client.getRootDirectory())))
			{
				File file = new File(client.getCurrentDirectory());
				client.setCurrentDirectory(file.getParent());
				ps.println("0 " + client.getCurrentDirectory());
			}
			
			else
			{
				ps.println("2 Vous n'avez pas la permission de remonter dans l'arborescence");
			}
		}
	}
}
