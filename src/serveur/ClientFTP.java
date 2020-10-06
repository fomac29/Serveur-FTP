package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;

import application.ControleurPrincipal;
import javafx.application.Platform;
import javafx.scene.text.Text;

/**
 * Classe representant un client FTP
 * @author Clement Stoliaroff
 */
public class ClientFTP implements Runnable
{
	/**
	 * Socket sur laquelle communique le client
	 */
	private Socket socket;
	
	/**
	 * Prochain identifiant a attribuer a un client
	 */
	private static int nextID = 1;
	
	/**
	 * Identifiant du client actuel
	 */
	private volatile int id;
	
	/**
	 * Flot permettant de lire les commandes du client
	 */
	private BufferedReader br;
	
	/**
	 * Flot permettant d'envoyer des reponses au  client
	 */
	private PrintStream ps;
	
	/**
	 * Booleen indiquant si le client a saisi son identifiant
	 */
	private boolean userOk = false ;
	
	/**
	 * Booleen indiquant si le client a saisi son mot de passe
	 */
	private boolean pwOk = false ;
	
	/**
	 * Dossier root du client
	 */
	private String rootDirectory;
	
	/**
	 * Dossier courant du client
	 */
	private String currentDirectory;
	
	/**
	 * Le controlleur de l'IHM
	 */
	private volatile ControleurPrincipal controller;
	
	/**
	 * La commande actuelle du client
	 */
	private volatile String commande;
	
	private static volatile boolean on = false;
	
	/**
	 * Constructeur du client
	 * @param socket Socket sur laquelle communique le client
	 * @throws IOException Erreur de recuperation des flots
	 */
	public ClientFTP(Socket socket, String serverRoot, ControleurPrincipal controller) throws IOException
	{
		this.socket = socket;
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.ps = new PrintStream(socket.getOutputStream());
		this.id = nextID++;
		this.rootDirectory = serverRoot;
		this.currentDirectory = this.rootDirectory;
		this.controller = controller;
		on = true;
	}
	
	/**
	 * @param on the on to set
	 */
	protected static void setOn(boolean on) {
		ClientFTP.on = on;
	}



	/**
	 * @return Le booleen indiquant si le client a saisi son identifiant 
	 */
	public boolean isUserOk()
	{
		return userOk;
	}

	/**
	 * Met a jour le booleen indiquant si le client a saisi son identifiant 
	 * @param userOk La nouvelle valeur du booleen
	 */
	public void setUserOk(boolean userOk) {
		this.userOk = userOk;
	}

	/**
	 * @return Le booleen indiquant si le client a saisi son mot de passe
	 */
	public boolean isPwOk() {
		return pwOk;
	}

	/**
	 * Met a jour le booleen indiquant si le client a saisi son mot de passe
	 * @param pwOk La nouvelle valeur du booleen
	 */
	public void setPwOk(boolean pwOk) {
		this.pwOk = pwOk;
	}

	/**
	 * @return Le dossier root du client
	 */
	public String getRootDirectory() {
		return rootDirectory;
	}

	/**
	 * Met a jour le dossier root du client
	 * @param rootDirectory Le nouveau dossier root du client
	 */
	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	/**
	 * @return Le dossier courant du client
	 */
	public String getCurrentDirectory() {
		return currentDirectory;
	}

	/**
	 * Met a jour le dossier courant du client
	 * @param currentDirectory Le nouveau dossier courant du client
	 */
	public void setCurrentDirectory(String currentDirectory) {
		this.currentDirectory = currentDirectory;
	}

	/**
	 * @return Le flot permettant de lire les commandes du client
	 */
	public BufferedReader getBr() {
		return br;
	}

	/**
	 * @return Flot permettant d'envoyer les commandes du client
	 */
	public PrintStream getPs() {
		return ps;
	}

	/**
	 * Demarre un nouveau thread pour traiter les demandes du client
	 */
	public void start()
	{
		// On instancie le Thread
		Thread t = new Thread(this);
		
		/* On passe le Thread en demon afin qu'il se ferme lors de la fermeture de
		 l'application */
		t.setDaemon(true);
		
		// On demarre le Thread
		t.start();
	}

	/**
	 * Routine du thhread traitant les demandes du client
	 */
	@Override
	public void run()
	{
		try
		{			
			Platform.runLater(new Runnable()
			{
	            @Override public void run()
	            {
					// On affiche la reponse
	            	controller.getFlow().getChildren().add(new Text(">> Un nouveau client est arrivé\n"));
	            	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
	            }
			});
			
			// On envoie des messages de bienvenues au client
			ps.println("1 Bienvenue !");
			ps.println("1 Serveur FTP Personnel.");
			ps.println("0 Authentification : ");
			
			commande = "";
			
			// Attente de reception de commandes et leur execution
			while(!(commande=br.readLine()).equalsIgnoreCase("QUIT") && on == true)
			{
				Platform.runLater(new Runnable()
				{
		            @Override public void run()
		            {
						// On affiche la reponse
		            	controller.getFlow().getChildren().add(new Text(">> client n°" + id + " : " + commande + "\n"));
		            	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
		            }
				});

				if(on)
				{
					CommandExecutor.executeCommande(commande, this);
				}
			}
			
			// Si le client ou le serveur se deconnecte, on ferme la socket
			socket.close();
			
			// Si c'est le client qui s'est deconnecte, on l'affiche dans le TextFlow
			Platform.runLater(new Runnable()
			{
	            @Override public void run()
	            {
	            	if(on == true)
	            	{
						// On affiche la reponse
		            	controller.getFlow().getChildren().add(new Text(">> client n°" + id + " : " + commande + "\n"));
		            	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
	            	}
	            }
			});
		}
		
		// On affiche une erreur si le client se deconnecte brutalement
		catch(SocketException e)
		{
			Platform.runLater(new Runnable()
			{
	            @Override public void run()
	            {
					// On affiche la reponse
	            	controller.getFlow().getChildren().add(new Text("Le client n°" + id + " s'est déconnecté\\n"));
	            	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
	            }
			});
		}
		
		// On affiche une erreur en cas de probleme de lecture ou d'ecriture
		catch(IOException e)
		{
			Platform.runLater(new Runnable()
			{
	            @Override public void run()
	            {
					// On affiche la reponse
	            	controller.getFlow().getChildren().add(new Text("Client n°" + id + " : Erreur de lecture ou d'écriture\n"));
	            	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
	            }
			});
		}
	}

}
