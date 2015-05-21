package fvsl.memory.common.util;

// TODO: Auto-generated Javadoc
/**
 * The Enum StringResources.
 */
public enum StringResources {
	// ClientRunnable
	/** The created is. */
	createdIS("Created output stream serverside"),
	/** The created os. */
	createdOS("Created input stream serverside"),
	/** The req ask. */
	reqAsk("Request ask "),
	/** The created lo. */
	createdLo("Lobby creata con id "),
	/** The destroyed lo. */
	destroyedLo("Owner of lobby left, removing lobby."),
	/** The lobbies. */
	lobbies("Lobbies"),
	/** The req ff. */
	reqFf("Request fulfilled..."),
	/** The req rec. */
	reqRec("Request received..."),
	/** The null run. */
	nullRun("runnable null"),
	/** The socket err. */
	socketErr("Socket error, closing"),
	/** The game perf. */
	gamePerf("game performing: "),
	/** The content taken. */
	contentTaken("content taken"),
	/** The set up game. */
	setUpGame("Setting up game..."),
	/** The start game. */
	startGame("Starting game"),

	// ClientUpdateRunnable
	/** The created up os. */
	createdUpOS("Created output stream for updater serverside"),
	/** The created up is. */
	createdUpIS("Created input stream for updater serverside"),
	/** The poll new up req. */
	pollNewUpReq("Polling new update request - "),
	// Server
	/** The s console. */
	sConsole("Server console"),
	/** The multy ts. */
	multyTS("Multithreaded Server"),
	/** The s stop. */
	sStop("Server Stopped."),
	/** The error acpt cc. */
	errorAcptCC("Error accepting client connection"),
	/** The error close s. */
	errorCloseS("Error closing server"),
	/** The cant opne port. */
	cantOpnePort("Cannot open port "),
	/** The up serv. */
	upServ("Updater Server"),
	/** The null c sock. */
	nullCSock("Client socket is null"),
	/** The c up add. */
	cUpAdd("Client updater added."),
	/** The new c. */
	newC("New client on port "),
	/** The server started on port. */
	serverStartedOnPort("Server started on port "),
	// DBHelper
	/** The con to db. */
	conToDB("****Connect to Database****"),
	/** The con db to. */
	conDBTo("DataBase connect to: "),
	/** The closed db con. */
	closedDBCon("Database Connection Closed"),
	/** The url. */
	url("URL:"),
	/** The con exc. */
	conExc("Exception in getLocalConeection() "),
	// GUIUpdaterRunnable
	/** The created up run. */
	createdUpRun("Created gui updater runnable"),
	/** The w8 up req. */
	w8UpReq("Waiting for update request..."),
	/** The recived up req. */
	recivedUpReq("Received update request "),
	/** The game req. */
	gameReq("Game request: "),
	// ServerManager
	/** The address not found. */
	addressNotFound("Non trovo indirizzo calcolatore ospite!"),
	/** The con to. */
	conTo("Connected to "),
	/** The created oscs. */
	createdOSCS("Created output stream clientside"),
	/** The created iscs. */
	createdISCS("Created input stream clientside"),
	/** The IO exc. */
	IOExc("IOException:"),
	/** The Something rec. */
	SomethingRec("Something has been received: "),
	/** The req sent. */
	reqSent("Request sent"),
	/** The window title. */
	windowTitle("Memory game"),
	// CreateLobbyPage
	/** The un join lo. */
	unJoinLo("Unable to join lobby: "),
	/** The new lo. */
	newLo("New lobby"),
	/** The lo name. */
	loName("Lobby Name"),
	/** The psw. */
	psw("Password"),
	/** The player num. */
	playerNum("Number of players"),
	/** The couple num. */
	coupleNum("Number of pairs"),
	/** The timer. */
	timer("Timer"),
	/** The create lo. */
	createLo("Create lobby"),
	/** The back. */
	back("Back"),
	// gamePage
	/** The folded card. */
	foldedCard("/card_folded.jpg"),
	/** The scoreboard. */
	scoreboard("Scoreboard"),
	/** The score. */
	score("Score"),
	/** The turn. */
	turn("Turn"),
	/** The text style. */
	textStyle("Arial"),
	/** The timer start. */
	timerStart("00:00"),
	// LobbyPage
	/** The player. */
	player("Player"),
	/** The rdy. */
	rdy("Ready"),
	/** The name. */
	name("Name"),
	/** The info lo. */
	infoLo("Lobby info"),
	/** The connected pl. */
	connectedPl("Connected Players"),
	/** The free slot. */
	freeSlot("~Free slot~"),
	/** The sec. */
	sec(" seconds"),
	/** The pairs. */
	pairs(" pairs"),
	// MainPage
	/** The try join lo. */
	tryJoinLo(" tries to join lobby "),
	/** The join lo. */
	joinLo("Join Lobby"),
	/** The anonymous pl. */
	anonymousPl("Anonymous player"),
	/** The update lo. */
	updateLo("Updating lobbies"),
	/** The change sel lo. */
	changeSelLo("Lobby selection changed "),
	/** The succjoin lo. */
	succjoinLo("successfully joined the lobby "),
	/** The name pl. */
	namePl("Player Name"),
	// ScoreBoard
	/** The cont. */
	cont("Continue"),
	// mapper
	/** The res fig. */
	resFig("res/figures"),
	/** The fig. */
	fig("/figures/"),
	/** The jpg. */
	jpg(".jpg"),
	/** The png. */
	png(".png"),
	/** The jpeg. */
	jpeg(".jpeg"),
	/** The id letter. */
	idLetter("c");

	private final String text;

	/**
	 * Instantiates a new string resources.
	 * 
	 * @param text
	 *            the text
	 */
	StringResources(String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}

}
