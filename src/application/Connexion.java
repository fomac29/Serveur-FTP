package application;

import java.io.IOException;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import serveur.ServeurFTP;

/**
 * Classe reprentant la connexion du serveur
 * @author Clement Stoliaroff
 */
public class Connexion implements ControleurLocal {

	/**
	 * Demarre le serveur
	 */
	@Override
	public void exec(ControleurPrincipal controller)
	{
		try
		{
			if(controller.getRoot().getText() != null && !controller.getRoot().getText().equals(""))
			{
				// Instanciation du serveur
				controller.setServeur(new ServeurFTP(controller));
				
				// Demarage du serveur
				controller.getServeur().start();
				
				// Desactivation du bouton de connexion
				controller.getConnect().setDisable(true);
				
				// Activation du bouton de deconnexion
				controller.getDisconnect().setDisable(false);
				
				// Desactivation du DirectoryChooser
				controller.getParcourir().setDisable(true);
				
				// Desactivation de la modification du port
				controller.getPort().setEditable(false);
			}
			
			else
			{
				Text text = new Text("Veuillez sélectionner un dossier de serveur\n");
				text.setFill(Color.RED);
				controller.getFlow().getChildren().add(text);
            	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
			}
		}
		
		catch(NumberFormatException e)
		{
			Text text = new Text("Le port saisi soit être un nombre\n");
			text.setFill(Color.RED);
			controller.getFlow().getChildren().add(text);
        	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
		}
		
		catch(IOException e)
		{
			Text text = new Text("Erreur de démarage du serveur\n");
			text.setFill(Color.RED);
			controller.getFlow().getChildren().add(text);
		}
	}

}
