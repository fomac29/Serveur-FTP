package serveur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe representant la commande GET
 * @author Clement Stoliaroff
 */
public class CommandeGET extends Commande implements Runnable {
	
	/**
	 * Constructeur de la commande GET
	 * @param client Le client qui a envoye la commande
	 * @param tabCommande La commande et ses arguments
	 */
	public CommandeGET(ClientFTP client, String [] tabCommande) {
		super(client, tabCommande);
	}

	/**
	 * Cree un thread afin de transferer un fichier vers le client
	 */
	public void execute()
	{
		new Thread(this).start();
	}

	/**
	 * Execution de la commande GET (envoi du fichier au client)
	 */
	@Override
	public void run()
	{
		try
		{
			// On n'execute la commande que si unfichier est passe en parametre
			if(this.commandeArgs.length > 0)
			{
				// On recupere le nom du fichier a transferer
				String fileName = this.commandeArgs[0];
				
				// On reconstitue le nom si celui-ci contient des espaces
				for(int i = 1; i < this.commandeArgs.length; i++)
				{
					fileName += " " + this.commandeArgs[i];
				}
				
				// On concatene le nom du fichier avec le dossier courant du client
				File file = new File(client.getCurrentDirectory() + "\\" + fileName);
				
				// Si le fichier existe
				if(file.exists() == true)
				{
					// On créé une nouvelle socket pour ne pas tous faire sur la même
					ServerSocket server = new ServerSocket(0);
			        
					//On envoie le port au client
			        ps.println("1 Commande GET");
			        ps.println("1 Port " + server.getLocalPort());
			        
			        // On attend la connexion du client
			        Socket client = server.accept();
			        
			        // Flot de lecture du fichier
			        FileInputStream fis = new FileInputStream(file);
			        
			        // On reçoit le flot d'ecriture
			        OutputStream os = client.getOutputStream();
			        
			        int lus;
			        
			        // On instancie un buffer pour une lecture plus rapide
			        byte [] buf = new byte[1024];
			        
			        // On remplis le buffer avec les octets du fichier
			        while((lus = fis.read(buf)) != -1)
			        {
			        	// et on envoie les octets lus dans le flot du client
			        	os.write(buf, 0, lus);
			        }
			        
			        // On envoie un message indiquant que le fichier est bien transferer
			        ps.println("0 Le fichier " + fileName + " a été téléchargé");
			        
			        //On ferme les flots et la socket
			        fis.close();
			        os.close();
			        server.close();
				}
				
				// Sinon on envoi une erreur
				else
				{
					ps.println("2 Aucun fichier passé en paramètre");
				}
			}
			
		}
		
		// On envoie une erreur si le fichier n'existe pas dans le dossier courant
		catch (FileNotFoundException e)
		{
			ps.println("2 Fichier inexistant");
		}
		
		// On envoie une erreur si une erreur se produit durant le transfert du fichier
		catch (IOException e)
		{
			ps.println("2 Erreur lors du téléchargement du fichier");
		}
	}

}
