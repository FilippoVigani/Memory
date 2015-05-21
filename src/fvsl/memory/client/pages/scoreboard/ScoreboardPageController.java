package fvsl.memory.client.pages.scoreboard;
/**
 * @author Stefano Leggio
 *
 */
import fvsl.memory.client.pages.PageListeners;

// TODO: Auto-generated Javadoc
/**
 * The Class ScoreboardPageController.
 */
public class ScoreboardPageController extends PageListeners {

	/**
	 * Back to main page.
	 */
	public void backToMainPage() {
		fireGoToMainPageEvent();
	}
}