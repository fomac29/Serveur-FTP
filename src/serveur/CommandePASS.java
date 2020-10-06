package serveur;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe representant la commande PASS
 * @author Clement Stoliaroff
 */
public class CommandePASS extends Commande
{	
	/**
	 * Constructeur de la commande PASS
	 * @param client Le client qui a envoye la commande
	 * @param tabCommande La commande et ses arguments
	 */
	public CommandePASS(ClientFTP client, String [] tabCommande) {
		super(client, tabCommande);
	}

	/**
	 * Execute la commande PASS (Verifie le mot de passe du client)
	 */
	public void execute() {
		
		try
		{
			// Flot permettant de lire le fichier de mot de passe du client
			BufferedReader pass = new BufferedReader(new InputStreamReader(new FileInputStream(this.client.getCurrentDirectory() + "\\password.txt")));
			
			// Si un mot de passe a ete saisi et que celui-ci correspond a celui contenu dans le fichier
			if(commandeArgs.length > 0 && commandeArgs[0].equals(pass.readLine()))
			{
				// On met a jour le booleen indiquant que le client a saisi son mot de passe
				client.setPwOk(true);
				
				// On met a jout le dossier root du client afin qu'il ne puisse pas telecharger le fichier de mot de passe
				client.setRootDirectory((client.getCurrentDirectory() + "\\root"));
				
				// On met a jour le dossier courant du client pour qu'il soit definitivement dans son dossier root
				client.setCurrentDirectory(client.getRootDirectory());
				
				// On envoi un message au client pour lui indiquer qu'il est bien connecte
				ps.println("1 Commande pass OK");
				ps.println("0 Vous êtes bien connecté sur notre serveur");
			}
			
			// Sinon on lui envois un message d'erreur
			else
			{
				ps.println("2 Le mode de passe est faux");
			}
			
			// On ferme le flot de lecture du fichier de mot de passe
			pass.close();
		}
		
		// On envoie un message d'erreur si le fichier de mot de passe n'existe pas
		catch (FileNotFoundException e)
		{
			ps.println("2 Le fichier de mot de passe n'existe pas");
		}
		
		// On envoie un message d'erreur en cas d'erreur de lecture du mot de passe
		catch (IOException e)
		{
			ps.println("2 Erreur de lecture du mot de passe");
		}
		
	}

}
