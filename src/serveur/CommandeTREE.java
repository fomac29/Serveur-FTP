package serveur;

/**
 * Classe envoyant l'arborescence du serveur au client (non implemente)
 * @author Clement Stoliaroff
 *
 */
public class CommandeTREE extends Commande {

	public CommandeTREE(ClientFTP client, String[] tabCommande)
	{
		super(client, tabCommande);
	}

	@Override
	public void execute()
	{
		
	}

}
