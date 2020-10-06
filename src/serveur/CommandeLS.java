package serveur;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;

/**
 * Classe representant la commande LS
 * @author Clement Stoliaroff
 */
public class CommandeLS extends Commande {
	
	/**
	 * Constructeur de la commande LS
	 * @param client Le client qui a envoye la commande
	 * @param tabCommande La commande et ses arguments
	 */
	public CommandeLS(ClientFTP client, String [] tabCommande) {
		super(client, tabCommande);
	}

	/**
	 * execute la commande LS (envoi de la liste des fichiers du dossier courant)
	 */
	public void execute()
	{
		// On instancie un objet SimpleDateFormat, permettant de mettre en forme la date de modification du fichier
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		File folder = new File(client.getCurrentDirectory());
		
		// On recupere les fichiers du dossier courant
		File [] listOfFile = folder.listFiles();
		
		int nbFichiers = 0, nbDossiers = 0;
		long tailleTotale = 0;
		
		//Si on n'est pas dans le dossier root, on envoie le dossier ".." pour revenir en arriere
		if(!client.getCurrentDirectory().equals(client.getRootDirectory()))
		{
			ps.println("1,..,0,Dossier de fichier, ,rwx, ");
		}
		
		// Si le dossier contient des fichiers ou dossiers, on envoie le detail au client
		String reponse;
		
		// Pour chaque fichier
		for(int i = 0; i < listOfFile.length; i++)
		{
			try
			{
				// On prepare l'etat de la reponse
				reponse = "1,";
				
				// On ajoute le nom du fichier
				reponse += listOfFile[i].getName() + ",";
				
				// On ajoute la taille du fichier
				reponse += listOfFile[i].length() + ",";
				
				// On ajoute le statut du fichier (fichier ou dossier)
				reponse += (listOfFile[i].isDirectory() ? "Dossier de fichier," : "Fichier,");
				
				// On ajoute la date de derniere modification du fichier
				reponse += sdf.format(listOfFile[i].lastModified()) + ",";
				
				// On ajoute la permission de lecture du fichier
				reponse += (listOfFile[i].canRead() ? "r" : "-");

				// On ajoute la permission d'ecriture du fichier
				reponse += (listOfFile[i].canWrite() ? "w" : "-");
				
				// On ajoute la permission d'execution du fichier
				reponse += (listOfFile[i].canExecute() ? "x," : "-,");
				
				// On ajoute Le proprietaire du fichier
				reponse += Files.getOwner(listOfFile[i].toPath());
				
				if(listOfFile[i].isDirectory())
				{
					nbDossiers++;
				}
				
				else
				{
					nbFichiers++;
				}
				
				tailleTotale += listOfFile[i].length();
				
				// On envoie la reponse au client
				ps.println(reponse);
			}
			
			
			// On affiche une erreur s'il y a un probleme lors du transfert d'un fichier
			catch(IOException e)
			{
				System.err.println("Erreur LS");
				ps.println("2 Erreur LS");
			}
		}
		
		ps.println("0 " + nbFichiers + " " + nbDossiers + " " + tailleTotale);
	}

}
