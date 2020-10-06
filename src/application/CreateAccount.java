package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Classe permettant de creer un compte utilisateur
 * @author Clement Stoliaroff
 */
public class CreateAccount implements ControleurLocal {

	/**
	 * Creer un utilisateur
	 */
	@Override
	public void exec(ControleurPrincipal controller)
	{
		// Si un dossier root a bien ete selectionne
		if(controller.getRoot().getText() != null && !controller.getRoot().getText().equals(""))
		{
			// Si un nom d'utilisateur a bien ete saisi
			if(controller.getUserName().getText() != null && !controller.getUserName().getText().contentEquals(""))
			{
				// Si un un mot de passe a bien ete saisi
				if(controller.getPassword().getText() != null && !controller.getPassword().getText().contentEquals(""))
				{
					// On verifie que le dossier au nom de l'utilisateur n'existe pas deja
					File user = new File(controller.getRoot().getText() + "\\" + controller.getUserName().getText());
					
					if(!user.exists())
					{						
						try
						{
							// On creer le dossier de l'utilisateur
							user.mkdir();
							
							// On cree le fichier de mot de passe de l'utilisateur
							File pass = new File(user + "\\password.txt");
							pass.createNewFile();
							
							// On ecrit le mot de passe dans le fichier
							PrintStream ps = new PrintStream(new FileOutputStream(pass));
							ps.println(controller.getPassword().getText());
							ps.close();
							
							// On cree le dossier root de l'utilisateur
							new File(user + "\\root").mkdir();
							
							// On affiche un message dans le TextFlow
							Text text = new Text("L'utilisateur "+ controller.getUserName().getText() +" a été créé avec succès !\n");
							text.setFill(Color.GREEN);
							controller.getFlow().getChildren().add(text);
			            	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
						}
						
						// En cas d'erreur, on affiche un message et on supprime le dossier du nouvel utilisateur
						catch (IOException e)
						{
							Text text = new Text("Erreur de création de l'utilisateur\n");
							text.setFill(Color.RED);
							controller.getFlow().getChildren().add(text);
			            	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
			            	user.delete();
						}
					}
					
					else
					{
						Text text = new Text("L'utilisateur "+ controller.getUserName().getText() + " existe déjà\n");
						text.setFill(Color.RED);
						controller.getFlow().getChildren().add(text);
		            	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
					}
				}
				
				else
				{
					Text text = new Text("Veuillez saisir un mot de passe\n");
					text.setFill(Color.RED);
					controller.getFlow().getChildren().add(text);
	            	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
				}
			}
			
			else
			{
				Text text = new Text("Veuillez saisir un nom d'utilisateur\n");
				text.setFill(Color.RED);
				controller.getFlow().getChildren().add(text);
            	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
			}
		}
		
		else
		{
			Text text = new Text("Veuillez sélectionner le dossier root du serveur\n");
			text.setFill(Color.RED);
			controller.getFlow().getChildren().add(text);
        	controller.getScrollpane().vvalueProperty().bind(controller.getFlow().heightProperty());
		}
		
		// On vide les TextFields
		controller.getUserName().clear();
		controller.getPassword().clear();
	}

}
