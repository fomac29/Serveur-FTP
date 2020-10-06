package serveur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe representant la commande STOR
 * @author Clement Stoliaroff
 */
public class CommandeSTOR extends Commande implements Runnable
{
	
	/**
	 * Constructeur de la commande STOR
	 * @param client Le client qui a envoye la commande
	 * @param tabCommande La commande et ses arguments
	 */
	public CommandeSTOR(ClientFTP client, String [] tabCommande) {
		super(client, tabCommande);
	}

	/**
	 * Cree un thread afin de transferer un fichier vers le serveur
	 */
	public void execute()
	{
		// On lance un thread afin de pouvoir continuer à lancer des commandes pendant le transfert de fichier
		new Thread(this).start();
	}

	/**
	 * Execute la commande STOR (receptionne le fichier sur le serveur)
	 */
	@Override
	public void run()
	{		
		try
		{
			// On créé une nouvelle socket pour ne pas tous faire sur la même
			ServerSocket server = new ServerSocket(0);
	        
			//On envoie le port au client
	        ps.println("1 Commande STOR");
	        ps.println("1 Port " + server.getLocalPort());
	        
	        // On attend la connexion du client
	        Socket socket = server.accept();
	        
	        // On reçoit les flots
	        PrintStream ps2 = new PrintStream(socket.getOutputStream());
	        
	        // On recupere le chemin distant du fichier
	        String pathFile = this.commandeArgs[0];
	        
	        // Si le chemin distant contient des espaces, on reconstitue son nom
	        for(int i = 1; i < this.commandeArgs.length; i++)
			{
				pathFile += " " + this.commandeArgs[i];
			}
	        
	        // On souhaite recupere le nom du fichier sans le chemin
	        String [] tabPathFile = pathFile.split("\\\\");
	        
	        // On concatene le chemin courant avec le dossier courant (ce nom se trouve au dernier indice du tableau
	        String fileName = (client.getCurrentDirectory() + "\\" + tabPathFile[tabPathFile.length - 1]);
			
			File file = new File(fileName);
			
			// Si le fichier contient une extension
			if(fileName.contains("."))
			{
				file = new File(fileName);
				
				// Recuperation de l'extension
				String extension = fileName.substring(fileName.lastIndexOf('.'));
				
				// Recuperation du nom du fichier (a l'exception de l'extension
				String name = fileName.substring(0, fileName.lastIndexOf('.'));
				
				int i = 2;
				
				// On ajoute un identifiant au fichier, et on change celui-ci tant que le fichier existe
				while(file.exists() == true)
				{
					file = new File(name + " (" + i + ")" + extension);
					i++;
				}
			}
			
			// Si le fichier ne contient pas d'extension
			else
			{
				file = new File(fileName);
				
				int i = 2;
				
				// On ajoute un identifiant au fichier, et on change celui-ci tant que le fichier existe
				while(file.exists() == true)
				{
					file = new File(fileName + " (" + i + ")");
					i++;
				}
			}
			
			// On recupere le flot de lecture du client
			InputStream is = socket.getInputStream();
			
			// On cree le fichier
			file.createNewFile();
			
			// On cree le flot d'ecriture dans le fichier
			FileOutputStream fos = new FileOutputStream(file);
			
			// On instancie un buffer d'octet pour une lecture plus rapide
			byte [] buf = new byte[1024];
			int lus;
			
			// On lis le flot du client avec le buffer
			while((lus = is.read(buf)) != -1)
			{
				// On écrit les octets lus dans le fichier
				fos.write(buf, 0, lus);
			}
			
			// On envoie un message au client pour indiquer la fin du transfert
	        ps.println("0 Le fichier " + fileName + " a été transféré");
	        
	        // On ferme les flots
	        fos.close();
	        ps2.close();
	        server.close();
		}
		
		// ca n'arrivera pas : on a cree le fichier juste avant
		// J'ai juste traite cette erreur car le compilateur le demande
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			ps.println("2 Le fichier à transférer n'existe pas");
		}
		
		// En cas d'erreur de transfert du fichier, on envoi un message d'erreur
		catch (IOException e)
		{
			e.printStackTrace();
			ps.println("2 Erreur lors du transfert du fichier");			
		}
	}
}
