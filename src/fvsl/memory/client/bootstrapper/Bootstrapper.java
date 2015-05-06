package fvsl.memory.client.bootstrapper;
import fvsl.memory.client.ui.WindowShell;

/**
 * @author Filippo Vigani
 *
 */
public class Bootstrapper {
	public static void main(String[] args){
		System.out.println("Hello world!");
		WindowShell mainWindow = new WindowShell();
		mainWindow.setVisible(true);
	}
}
