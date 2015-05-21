package fvsl.memory.common.util;

public enum StringResources {
	// ClientRunnable
	createdIS("Created output stream serverside"), createdOS("Created input stream serverside"), reqAsk("Request ask "), createdLo("Lobby creata con id "), destroyedLo(
			"Owner of lobby left, removing lobby."), lobbies("lobbies"), reqFf("Request fulfilled..."), reqRec("Request received..."), nullRun("runnable null"), socketErr("Socket error, closing"),
			gamePerf("game performing: "),contentTaken("content taken"),setUpGame("Setting up game..."),startGame("Starting game"),
			
	// ClientUpdateRunnable
	createdUpOS("Created output stream for updater serverside"), createdUpIS("Created input stream for updater serverside"), pollNewUpReq("Polling new update request - "),
	// Server
	sConsole("Server console"),multyTS("Multithreaded Server"),sStop("Server Stopped."),errorAcptCC("Error accepting client connection"),errorCloseS("Error closing server"),cantOpnePort("Cannot open port "),
	upServ("Updater Server"), nullCSock("Client socket is null"), cUpAdd("Client updater added."), newC("New client on port "), serverStartedOnPort("Server started on port "),
	// DBHelper
	conToDB("****Connect to Database****"), conDBTo("DataBase connect to: "), closedDBCon("Database Connection Closed"),url("URL:"),conExc("Exception in getLocalConeection() "),
	// GUIUpdaterRunnable
	createdUpRun("Created gui updater runnable"), w8UpReq("Waiting for update request..."), recivedUpReq("Received update request "),gameReq("Game request: "),
	// ServerManager
	addressNotFound("Non trovo indirizzo calcolatore ospite!"), conTo("Connected to "), createdOSCS("Created output stream clientside"), createdISCS("Created input stream clientside"), IOExc(
			"IOException:"), SomethingRec("Something has been received: "), reqSent("Request sent"), windowTitle("Memory game"),
	//CreateLobbyPage
	unJoinLo("Unable to join lobby: "),newLo("new lobby"),loName("Lobby Name"),psw("Password"),playerNum( "players numbers"),coupleNum("Number of pairs"),timer("timer"),
	createLo("create lobby"),back("back"),
	//gamePage
	foldedCard("/card_folded.jpg"),scoreboard("Scoreboard"),score("Score"),turn("Turn"),textStyle("Arial"),timerStart("00:00"),
	//LobbyPage
	player("player"),rdy("Ready" ),name("Name"),infoLo("Lobby info"),connectedPl("Connected Players"),freeSlot("~Free slot~"),sec(" seconds"),pairs(" pairs"),
	//MainPage
	tryJoinLo(" tries to join lobby "),joinLo("Join Lobby"),anonymousPl("Anonymous player"),updateLo("Updating lobbies"),changeSelLo("Lobby selection changed "),
	succjoinLo("successfully joined the lobby "),namePl("Player Name"),
	//mapper
	resFig("res/figures"),fig("/figures/"),jpg(".jpg"),png(".png"),jpeg(".jpeg"),idLetter("c");
	
	
	private final String text;

	StringResources(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
