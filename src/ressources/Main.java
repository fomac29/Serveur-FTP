package ressources;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try
		{
			// charge le fichier FXML
			Parent root = FXMLLoader.load(getClass().getResource("IHM_Serveur.fxml"));
			
			// Modifie le nom de l'application
			primaryStage.setTitle("Mon serveur FTP");
			
			// Specifie la scene a utiliser
			primaryStage.setScene(new Scene(root));
			
			// Change l'icone de l'application
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("forbidden.png")));
			
			// Affiche l'IHM
			primaryStage.show();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
