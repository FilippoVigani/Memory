package fvsl.memory.client.entities;

	public enum ClientRunResources{

		createdIS ("Created output stream serverside"),
		createdOS ("Created input stream serverside"),
		reqAsk ("Request ask "),
		createdLo("Lobby creata con id "),
		destroyedLo("Owner of lobby left, removing lobby."),
		lo("lobbies"),
		reqFf("Request fulfilled..."),
		reqRec("Request received..."),
		nullRun("runnable null");
		private final String argoument;
		ClientRunResources( String argoument ){
			
			this.argoument=argoument;
		}
	
		public String getArgoument() {
			return argoument;
		}
		
}
	