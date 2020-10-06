package application;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;

/**
 * Sample Skeleton for 'IHM_Serveur.fxml' Controller Class
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import serveur.ServeurFTP;

/**
 * Controleur principal de l'IHM
 * @author Clement Stoliaroff
 */
public class ControleurPrincipal {

    @FXML // fx:id="root"
    private TextField root; // Value injected by FXMLLoader

    @FXML // fx:id="parcourir"
    private Button parcourir; // Value injected by FXMLLoader

    @FXML // fx:id="hote"
    private TextField hote; // Value injected by FXMLLoader

    @FXML // fx:id="port"
    private TextField port; // Value injected by FXMLLoader

    @FXML // fx:id="userName"
    private TextField userName; // Value injected by FXMLLoader

    @FXML // fx:id="password"
    private PasswordField password; // Value injected by FXMLLoader

    @FXML // fx:id="newUser"
    private Button newUser; // Value injected by FXMLLoader

    @FXML // fx:id="Connect"
    private Button Connect; // Value injected by FXMLLoader

    @FXML // fx:id="Disconnect"
    private Button Disconnect; // Value injected by FXMLLoader

    @FXML // fx:id="scrollpane"
    private ScrollPane scrollpane; // Value injected by FXMLLoader

    @FXML // fx:id="flow"
    private TextFlow flow; // Value injected by FXMLLoader
    
    private ServeurFTP serveur;
    
    private DirectoryChooser directoryChooser;

	/**
	 * @return the root
	 */
	public TextField getRoot() {
		return root;
	}

	/**
	 * @return the parcourir
	 */
	public Button getParcourir() {
		return parcourir;
	}

	/**
	 * @return the hote
	 */
	public TextField getHote() {
		return hote;
	}

	/**
	 * @return the port
	 */
	public TextField getPort() {
		return port;
	}

	/**
	 * @return the userName
	 */
	public TextField getUserName() {
		return userName;
	}

	/**
	 * @return the password
	 */
	public PasswordField getPassword() {
		return password;
	}

	/**
	 * @return the newUser
	 */
	public Button getNewUser() {
		return newUser;
	}

	/**
	 * @return the connect
	 */
	public Button getConnect() {
		return Connect;
	}

	/**
	 * @return the disconnect
	 */
	public Button getDisconnect() {
		return Disconnect;
	}

	/**
	 * @return the scrollpane
	 */
	public ScrollPane getScrollpane() {
		return scrollpane;
	}

	/**
	 * @return the flow
	 */
	public TextFlow getFlow() {
		return flow;
	}
    
	/**
	 * @return le serveur
	 */
	protected ServeurFTP getServeur() {
		return serveur;
	}

	/**
	 * @param serveur le serveur a modifier
	 */
	protected void setServeur(ServeurFTP serveur) {
		this.serveur = serveur;
	}
	
	/**
	 * @return the directoryChooser
	 */
	protected DirectoryChooser getDirectoryChooser() {
		return directoryChooser;
	}

	/**
	 * @param directoryChooser the directoryChooser to set
	 */
	protected void setDirectoryChooser(DirectoryChooser directoryChooser) {
		this.directoryChooser = directoryChooser;
	}

	/**
	 * Initialise l'IHM du serveur
	 */
    public void initialize()
    {
    	try
    	{
    		// On affiche l'adresse IP de la machine locale
			this.hote.setText(InetAddress.getLocalHost().getHostAddress().toString());
			
			// On désactive le bouton de deconnexion
			this.Disconnect.setDisable(true);
			
			// On initialise le DirectoryChooser
			new InitRootChooser().exec(this);
		}
    	
    	catch (UnknownHostException e)
    	{
			e.printStackTrace();
		}
    }
    
    /**
     * Allume le serveur
     */
    @FXML
    public void connexion()
    {
    	new Connexion().exec(this);
    }
    
    /**
     * Eteint le serveur
     */
    @FXML
    public void deconnexion()
    {
    	new Deconnexion().exec(this);
    }
    
    /**
     * Selectionne le dossier root du serveur
     */
    @FXML
    public void chooseRoot()
    {
    	new ChooseRoot().exec(this);
    }
    
    /**
     * Cree un nouvel utilisateur
     */
    @FXML
    private void createAccount()
    {
    	new CreateAccount().exec(this);
    }
    
    /**
     * Affichage de la boite d'information a propos
     * @param event
     */
     @FXML
     void ActionAbout(ActionEvent event)
     {
         new About().exec(this);
     }
}
