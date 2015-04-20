package fvsl.memory.client.ui;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainPage extends Page {

	public MainPage(JFrame container){
		super(container);
	}
	
	@Override
	protected void loadComponents() {
		add(new JLabel("Main page"));
	}

}
