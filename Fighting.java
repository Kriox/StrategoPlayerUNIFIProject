package gj.stratego.player.fontani;

public class Fighting {
	String[] pezziPos = { "FL", "FB", "SB", "MA", "GE", "FM", "SM", "FS", "SS", "SP" };
	int[] power = { 4, 11, 11, 10, 9, 3, 3, 2, 2, 1 };
	int[] idPezziPosF = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	String[] pezziPosF = { "FL", "FB", "SB", "MA", "GE", "FM", "SM", "FS", "SS", "SP" };

	//
	//
	// INIZIO DEI METODI PER AGGIORNARE I CAMPI IN CASO DI FIGHT
	//
	//
	// metodo che restituisce la stringa di quale pezzo del Player si trova in
	// una certa posizione
	public String pezzoInPos(int[] pos, int[][] campoId) {
		int id = campoId[pos[0]][pos[1]];
		int i = 0;
		boolean check = false;
		while (!check) {
			if (id == idPezziPosF[i]) {
				check = true;
			} else {
				i = i + 1;
			}
		}
		return pezziPosF[i];
	}

	// metodo che restituisce quale pezzo del Player si sta scontrando
	String pezzoAttaccato(int[][] campoId, int[] movAv, int[] movPl, boolean attack, boolean first) {
		String piece;
		if (attack) {
			piece = pezzoInPos(movPl, campoId); // restituisce quale pezzo del
												// Player si
												// sta scontrando se attacca il
												// mio player
		} else {
			piece = pezzoInPos(posizPezzoAttaccato(movAv, first), campoId);// restituisce
			// quale pezzo del Player si sta scontrando se attacca l'avversario
		}
		return piece;
	}

	// metodo che restituisce 0 in caso di pareggio, 1 se vince il player, 2 se
	// vince avversario
	public int battleWon(String pieceAvv, String piecePl, boolean attack) {
		int vincitore = 0;
		int puntAvv = 0;
		int puntPl = 0;
		int i = 0;
		int j = 0;
		boolean checkA = false;
		boolean checkP = false;
		int firstExe = minerBomb(pieceAvv, piecePl);
		int secondExe = marSpy(pieceAvv, piecePl, attack);
		if (firstExe == 1 || secondExe == 1) {
			vincitore = 1;
		} else if (firstExe == 2 || secondExe == 2) {
			vincitore = 2;
		} else {
			while (!checkA) { // cerca punteggio pezzi
				if (pieceAvv.equals(pezziPos[i])) {
					checkA = true;
				} else {
					i = i + 1;
				}
			}
			while (!checkP) {
				if (piecePl.equals(pezziPos[j])) {
					checkP = true;
				} else {
					j = j + 1;
				}
			}
			puntAvv = power[i];
			puntPl = power[j];
			if (puntAvv > puntPl) {
				vincitore = 2;
			} else if (puntAvv < puntPl) {
				vincitore = 1;
			} else if (puntAvv == puntPl) {
				vincitore = 0;
			}
		}
		return vincitore;
	}

	// restituisce in quale posizione sta attaccando l'avversario, dal quale
	// e' possibile capire quale pezzo e' stato attaccato
	public int[] posizPezzoAttaccato(int[] movAv, boolean first) {
		int[] newPos = new int[2];
		if (first) {
			if (movAv[2] == 1) {
				newPos[0] = movAv[0] - movAv[3];
				newPos[1] = movAv[1];
			}
			if (movAv[2] == 2) {
				newPos[0] = movAv[0] + movAv[3];
				newPos[1] = movAv[1];
			}
			if (movAv[2] == 3) {
				newPos[0] = movAv[0];
				newPos[1] = movAv[1] - movAv[3];
			}
			if (movAv[2] == 4) {
				newPos[0] = movAv[0];
				newPos[1] = movAv[1] + movAv[3];
			}
		} else {
			if (movAv[2] == 1) {
				newPos[0] = movAv[0] + movAv[3];
				newPos[1] = movAv[1];
			}
			if (movAv[2] == 2) {
				newPos[0] = movAv[0] - movAv[3];
				newPos[1] = movAv[1];
			}
			if (movAv[2] == 3) {
				newPos[0] = movAv[0];
				newPos[1] = movAv[1] + movAv[3];
			}
			if (movAv[2] == 4) {
				newPos[0] = movAv[0];
				newPos[1] = movAv[1] - movAv[3];
			}
		}
		return newPos;
	}

	// metodo per vedere se si scontrano FM SM e FB SB
	int minerBomb(String pieceAvv, String piecePl) {
		int check = 0;
		if ((pieceAvv.equals(pezziPos[5]) || pieceAvv.equals(pezziPos[6]))
				&& (piecePl.equals(pezziPos[1]) || piecePl.equals(pezziPos[2]))) {
			check = 2;
		}
		if ((piecePl.equals(pezziPos[5]) || piecePl.equals(pezziPos[6]))
				&& (pieceAvv.equals(pezziPos[1]) || pieceAvv.equals(pezziPos[2]))) {
			check = 1;
		}
		if ((!pieceAvv.equals(pezziPos[5]) && !pieceAvv.equals(pezziPos[6]))
				&& (piecePl.equals(pezziPos[1]) || piecePl.equals(pezziPos[2]))) {
			check = 1;
		}
		if ((!piecePl.equals(pezziPos[5]) && !piecePl.equals(pezziPos[6]))
				&& (pieceAvv.equals(pezziPos[1]) || pieceAvv.equals(pezziPos[2]))) {
			check = 2;
		}
		return check;
	}

	// metodo per vedere se si scontrano MA e SP
	int marSpy(String pieceAvv, String piecePl, boolean attack) {
		int win = 0;
		if (!attack) {
			if (pieceAvv.equals(pezziPos[9]) && piecePl.equals(pezziPos[3])) {
				win = 2;
			}
		} else {
			if ((piecePl.equals(pezziPos[9]) && pieceAvv.equals(pezziPos[3]))) {
				win = 1;
			}
		}
		return win;
	}

	//
	// FINE METODI PER AGGIORNARE I CAMPI
	//

}
