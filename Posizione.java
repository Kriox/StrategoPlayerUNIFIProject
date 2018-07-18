package gj.stratego.player.fontani;

public class Posizione {
	Fighting f = new Fighting();

	public String[] pezziMov = { "MA", "GE", "FM", "SM", "FS", "SS", "SP" };
	public int[] idPezziPos = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	public String[] pezziPos = { "none", "FL", "FB", "SB", "MA", "GE", "FM", "SM", "FS", "SS", "SP", "water", "unk" };
	public int[] idPezziPosF = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	public String[] pezziPosF = { "FL", "FB", "SB", "MA", "GE", "FM", "SM", "FS", "SS", "SP" };

	// metodo che salva il campo di gioco iniziale
	public void salvaCampoInizio(int[][] pos, int[][] campo, int[][] campoId, boolean first, int[][] hpF) {
		int i = 0;
		int j = 0;
		int x = 0;
		if (!first) {
			for (i = 0; i < pos.length; i++) {
				for (j = 0; j < pos[0].length; j++) {
					if (pos[i][j] == 1) {
						campo[i][j] = 2;
						campoId[i][j] = 12;
						hpF[x][0] = i;
						hpF[x][1] = j;
						x++;
					}
				}
			}
		} else {
			for (i = 0; i < pos.length; i++) {
				for (j = 0; j < pos[0].length; j++) {
					if (pos[i][j] == 1) {
						campo[i + 6][j] = 2;
						campoId[i + 6][j] = 12;
						hpF[x][0] = i + 6;
						hpF[x][1] = j;
						x++;
					}
				}
			}
		}
	}

	// metodo per salvare il movimento avversario nel campo
	public void aggMossaAvv(int[] movim, boolean first, int[][] campo, int[][] campoId) {

		int value = campoId[movim[0]][movim[1]];
		if (first) {
			if (movim[2] == 1) {
				if (campo[movim[0] - movim[3]][movim[1]] != 1) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0] - movim[3]][movim[1]] = 2;
					campoId[movim[0] - movim[3]][movim[1]] = value;
				}
			}
			if (movim[2] == 2) {
				if (campo[movim[0] + movim[3]][movim[1]] != 1) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0] + movim[3]][movim[1]] = 2;
					campoId[movim[0] + movim[3]][movim[1]] = value;
				}
			}
			if (movim[2] == 3) {
				if (campo[movim[0]][movim[1] - movim[3]] != 1) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0]][movim[1] - movim[3]] = 2;
					campoId[movim[0]][movim[1] - movim[3]] = value;
				}
			}
			if (movim[2] == 4) {
				if (campo[movim[0]][movim[1] + movim[3]] != 1) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0]][movim[1] + movim[3]] = 2;
					campoId[movim[0]][movim[1] + movim[3]] = value;
				}
			}
		} else {
			if (movim[2] == 1) {
				if (campo[movim[0] + movim[3]][movim[1]] != 1) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0] + movim[3]][movim[1]] = 2;
					campoId[movim[0] + movim[3]][movim[1]] = value;
				}
			}
			if (movim[2] == 2) {
				if (campo[movim[0] - movim[3]][movim[1]] != 1) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0] - movim[3]][movim[1]] = 2;
					campoId[movim[0] - movim[3]][movim[1]] = value;
				}
			}
			if (movim[2] == 3) {
				if (campo[movim[0]][movim[1] + movim[3]] != 1) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0]][movim[1] + movim[3]] = 2;
					campoId[movim[0]][movim[1] + movim[3]] = value;
				}
			}
			if (movim[2] == 4) {
				if (campo[movim[0]][movim[1] - movim[3]] != 1) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0]][movim[1] - movim[3]] = 2;
					campoId[movim[0]][movim[1] - movim[3]] = value;
				}
			}
		}
	}

	// metodo per salvare il movimento del mio player nel campo
	public void aggMossaPl(int[] movim, boolean first, int[][] campo, int[][] campoId, int[][][] lastPos) {
		int value = 0;
		value = campoId[movim[0]][movim[1]];
		String piece = findPieceName(value, idPezziPos);
		aggPosit(piece, movim[0], movim[1], lastPos);
		if (first) {
			if (movim[2] == 1) {
				if (campo[movim[0] + movim[3]][movim[1]] != 2) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0] + movim[3]][movim[1]] = 1;
					campoId[movim[0] + movim[3]][movim[1]] = value;
				}
			}
			if (movim[2] == 2) {
				if (campo[movim[0] - movim[3]][movim[1]] != 2) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0] - movim[3]][movim[1]] = 1;
					campoId[movim[0] - movim[3]][movim[1]] = value;
				}
			}
			if (movim[2] == 3) {
				if (campo[movim[0]][movim[3] + movim[1]] != 2) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0]][movim[1] + movim[3]] = 1;
					campoId[movim[0]][movim[1] + movim[3]] = value;
				}
			}
			if (movim[2] == 4) {
				if (campo[movim[0]][movim[1] - movim[3]] != 2) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0]][movim[1] - movim[3]] = 1;
					campoId[movim[0]][movim[1] - movim[3]] = value;
				}
			}
		} else {
			if (movim[2] == 1) {
				if (campo[movim[0] - movim[3]][movim[1]] != 2) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0] - movim[3]][movim[1]] = 1;
					campoId[movim[0] - movim[3]][movim[1]] = value;
				}
			}
			if (movim[2] == 2) {
				if (campo[movim[0] + movim[3]][movim[1]] != 2) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0] + movim[3]][movim[1]] = 1;
					campoId[movim[0] + movim[3]][movim[1]] = value;
				}
			}
			if (movim[2] == 3) {
				if (campo[movim[0]][movim[1] - movim[3]] != 2) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0]][movim[1] - movim[3]] = 1;
					campoId[movim[0]][movim[1] - movim[3]] = value;
				}
			}
			if (movim[2] == 4) {
				if (campo[movim[0]][movim[1] + movim[3]] != 2) {
					campo[movim[0]][movim[1]] = 0;
					campoId[movim[0]][movim[1]] = 0;
					campo[movim[0]][movim[1] + movim[3]] = 1;
					campoId[movim[0]][movim[1] + movim[3]] = value;
				}
			}
		}
	}

	// metodo che restituisce il valore di quale pezzo si chiama "stringa"
	// (esempio: il valore di MA e 4)
	public int valorePezzoPos(String pezzo) {
		int i = 0;
		boolean check = false;
		while (!check) {
			if (pezzo.equals(pezziPos[i])) {
				check = true;
			} else {
				i = i + 1;
			}
		}
		return idPezziPos[i];
	}

	// metodo per aggiorare il campo e il campoId in caso di fight
	public void aggCampi(boolean attack, int[] movPl, int[] movAv, boolean first, int[][] campo, int[][] campoId,
			String pezzoAvv, int[][] hpFlag) {
		String pAttaccato = f.pezzoAttaccato(campoId, movAv, movPl, attack, first);
		int winner = f.battleWon(pezzoAvv, pAttaccato, attack);
		int[] posFight = new int[2];
		if (attack) { // cerca posizione del combattimento
			posFight = pos(movPl, first);
		} else {
			posFight = f.posizPezzoAttaccato(movAv, first);
		}
		if (attack) {
			for (int z = 0; z < 10; z++) {
				if (hpFlag[z][0] == posFight[0] && hpFlag[z][1] == posFight[1]) {
					hpFlag[z][0] = 10;
					hpFlag[z][1] = 10;
				}
			}

		}
		// cancello posizioni precedenti
		if (attack) {
			campo[movPl[0]][movPl[1]] = 0;
			campoId[movPl[0]][movPl[1]] = 0;
		} else {
			campo[movAv[0]][movAv[1]] = 0;
			campoId[movAv[0]][movAv[1]] = 0;
		}
		// aggiorna i campi a seconda del vincitore
		if (winner == 1) {
			campo[posFight[0]][posFight[1]] = 1;
			campoId[posFight[0]][posFight[1]] = valorePezzoPos(pAttaccato);
		}
		if (winner == 2) {
			campo[posFight[0]][posFight[1]] = 2;
			campoId[posFight[0]][posFight[1]] = valorePezzoPos(pezzoAvv);
		}
		if (winner == 0) {
			campo[posFight[0]][posFight[1]] = 0;
			campoId[posFight[0]][posFight[1]] = 0;
		}
	}

	// metodo per aggiornare le ultime tre posizioni di ogni pezzo
	public void aggPosit(String piece, int row, int column, int[][][] lastPos) {
		int i = trovaIdPezzo(pezziMov, piece);
		int j = lastPos[i][0][0];
		lastPos[i][j][0] = row;
		lastPos[i][j][1] = column;
		if (j == 3) {
			lastPos[i][0][0] = 1;
		} else {
			lastPos[i][0][0] = j + 1;
		}
	}

	// metodo che trova a quale indice corrisponde una stringa, in un vettore di
	// stringhe
	int trovaIdPezzo(String[] pezzi, String pezzo) {
		boolean flag = false;
		int i = 0;
		while (!flag) {
			if (pezzi[i].equals(pezzo)) {
				flag = true;
			} else {
				i = i + 1;
			}
		}
		return i;
	}

	// dato un valore trova il nome del pezzo corrispondente
	String findPieceName(int val, int[] pezzi) {
		boolean flag = false;
		int i = 0;
		while (!flag) {
			if (pezzi[i] == val) {
				flag = true;
			} else {
				i = i + 1;
			}
		}
		return pezziPos[i];
	}

	// trovare posizione pezzo del mio player quando attacca(ovvero dove va dopo
	// il move)
	int[] pos(int[] movim, boolean first) {
		int[] mov = new int[2];
		if (first) {
			if (movim[2] == 1) {
				mov[0] = movim[0] + movim[3];
				mov[1] = movim[1];
			}
			if (movim[2] == 2) {
				mov[0] = movim[0] - movim[3];
				mov[1] = movim[1];
			}
			if (movim[2] == 3) {
				mov[0] = movim[0];
				mov[1] = movim[1] + movim[3];
			}
			if (movim[2] == 4) {
				mov[0] = movim[0];
				mov[1] = movim[1] - movim[3];
			}
		} else {
			if (movim[2] == 1) {
				mov[0] = movim[0] - movim[3];
				mov[1] = movim[1];
			}
			if (movim[2] == 2) {
				mov[0] = movim[0] + movim[3];
				mov[1] = movim[1];
			}
			if (movim[2] == 3) {
				mov[0] = movim[0];
				mov[1] = movim[1] - movim[3];
			}
			if (movim[2] == 4) {
				mov[0] = movim[0];
				mov[1] = movim[1] + movim[3];
			}
		}
		return mov;
	}

}