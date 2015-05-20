package fvsl.memory.common.util;

public enum StringResources {
	// ClientRunnable
	createdIS("Created output stream serverside"), createdOS("Created input stream serverside"), reqAsk("Request ask "), createdLo("Lobby creata con id "), destroyedLo(
			"Owner of lobby left, removing lobby."), lobbies("lobbies"), reqFf("Request fulfilled..."), reqRec("Request received..."), nullRun("runnable null"),
	// ClientUpdateRunnable
	createdUpOS("Created output stream for updater serverside"), createdUpIS("Created input stream for updater serverside"), newUpReq("New update request found! - "),
	// Server
	sStop("Server Stopped."), nullCSock("Client socket is null"), cUpAdd("Client updater added."), newC("New client on port "), serverStartedOnPort("Server started on port "),
	// DBHelper
	conToDB("****Connect to Database****"), conDBTo("DataBase connect to: "), closedDBCon("Database Connection Closed"),
	// GUIUpdaterRunnable
	createdUpRun("Created gui updater runnable"), w8UpReq("Waiting for update request..."), recivedUpReq("Received update request "),
	// ServerManager
	addressNotFound("Non trovo indirizzo calcolatore ospite!"), conTo("Connected to "), createdOSCS("Created output stream clientside"), createdISCS("Created input stream clientside"), IOExc(
			"IOException:"), SomethingRec("Something has been received: "), reqSent("Request sent"), windowTitle("Memory game");

	private final String text;

	StringResources(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
