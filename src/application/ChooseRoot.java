package application;

import java.io.File;

/**
 * Classe Permettant la selection du rossier root du serveur
 * @author Clement Stoliaroff
 *
 */
public class ChooseRoot implements ControleurLocal {

	/**
	 * Ouverture de la selection du dossier root
	 */
	@Override
	public void exec(ControleurPrincipal controller)
	{
    	// Si un dossier a ete selectionne, 
        File dir = controller.getDirectoryChooser().showDialog(controller.getParcourir().getScene().getWindow());
        
        // On ecrit son chemin dans le TextField
        if (dir != null)
        {
            controller.getRoot().setText(dir.toString());
        }
	}

}
