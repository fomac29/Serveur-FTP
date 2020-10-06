package application;

import java.io.IOException;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Eteint le serveur
 * @author Clement Stoliaroff
 */
public class Deconnexion implements ControleurLocal
{
	/**
	 * Ferme la socket du serveur
	 */
	@Override
	public void exec(ControleurPrincipal controller)
	{
		try
		{
			// Extinction du serveur
			controller.getServeur().stop();
			
			// Activation du bouton de connexion
			controller.getConnect().setDisable(false);
			
			// Desactivation du bouton de deconnexion
			controller.getDisconnect().setDisable(true);
			
			// Activation du DirectoryChooser
			controller.getParcourir().setDisable(false);
			
			// Activation de la modification du port
			controller.getPort().setEditable(true);
		}
		
		catch (IOException e)
		{
			Text text = new Text("Erreur d'extinction du serveur\n");
			text.setFill(Color.RED);
			controller.getFlow().getChildren().add(text);
        	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
		}
	}

}
