import javax.swing.JLabel;


public class CreateLobbyPage extends Page {

	public CreateLobbyPage(Page page){
		super(page);
	}
	
	@Override
	protected void loadComponents() {
		add(new JLabel("createLobbyPage"));

	}

}
