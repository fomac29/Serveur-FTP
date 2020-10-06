package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import application.ControleurPrincipal;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ServeurFTP implements Runnable
{
	private static volatile ServerSocket serveurFTP;
	private static volatile boolean on = false;
	private ControleurPrincipal controleur;
	private String serverRoot;
	
	public ServeurFTP(ControleurPrincipal controleur) throws IOException, NumberFormatException
	{
		int port = Integer.parseInt(controleur.getPort().getText());
		this.controleur = controleur;
		serveurFTP = new ServerSocket(port);
		on = false;
		this.serverRoot = controleur.getRoot().getText();
	}

	public void start()
	{
		// Affichage du demarrage dans le TextFlow
		Text demarrage = new Text("Démarage du serveur...\n");
		demarrage.setFill(Color.GREEN);
		controleur.getFlow().getChildren().add(demarrage);
		
		// On baisse la scrollbar
		controleur.getScrollpane().vvalueProperty().bind(controleur.getFlow().heightProperty());
		
		// On demarre le serveur
		on = true;
		
		// On cree le Thread
		Thread t = new Thread(this);
		
		/* On passe le Thread en demon afin qu'il se ferme lors de la fermeture de
		 l'application */
		t.setDaemon(true);
		
		// On demarre le Thread
		t.start();
	}
	
	public void stop() throws IOException
	{
		// On affiche l'extinction du serveur dans le TextFlow
		Platform.runLater(new Runnable()
		{
            @Override public void run()
            {
        		Text extinction = new Text("Extinction du serveur...\n");
        		extinction.setFill(Color.RED);
        		controleur.getFlow().getChildren().add(extinction);
        		controleur.getScrollpane().vvalueProperty().bind(controleur.getFlow().heightProperty());
            }
		});
		
		// On eteint le serveur
		on = false;
		serveurFTP.close();
		
		// On eteint les clients
		ClientFTP.setOn(false);
	}
	
	@Override
	public void run()
	{
		// On boucle tant que le serveur est allume
		while(on)
		{
			try
			{
				// On attend un client
				Socket socket = serveurFTP.accept();
				
				// Lorsqu'un client arrive, on lance un thread pour traiter ses commandes
				new ClientFTP(socket, this.serverRoot, controleur).start();
			}
			
			// En cas d'erreur de creation d'un client, on affiche une erreur
			// Puis on se remet en attente
			catch(SocketException e)
			{
				System.err.println("Le serveur est maintenant déconnecté");
			}
			
			// En cas d'erreur de creation d'un client, on affiche une erreur
			// Puis on se remet en attente
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
