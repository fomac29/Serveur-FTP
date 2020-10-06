package application;

import java.io.File;

import javafx.stage.DirectoryChooser;

/**
 * Classe initialisant le DirectoryChooser permettant
 * de selectionner le dossier root
 * @author Clement Stoliaroff
 */
public class InitRootChooser implements ControleurLocal {

	/**
	 * Initialise le DirectoryChooser permettant
	 * de selectionner le dossier root
	 */
	@Override
	public void exec(ControleurPrincipal controller)
	{
		// On instancie le DirectoryChooser
		controller.setDirectoryChooser(new DirectoryChooser());
		
        // On modifie le nom DirectoryChooser
		controller.getDirectoryChooser().setTitle("Sélectionnez le dossier à partager");
 
        // Set Initial Directory
		controller.getDirectoryChooser().setInitialDirectory(new File(System.getProperty("user.home")));
	}

}
