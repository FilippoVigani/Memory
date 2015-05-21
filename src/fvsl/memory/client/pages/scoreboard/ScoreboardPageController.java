package fvsl.memory.client.pages.scoreboard;

import fvsl.memory.client.pages.PageListeners;

public class ScoreboardPageController extends PageListeners {
	public void backToMainPage() {
		fireGoToMainPageEvent();
	}
}