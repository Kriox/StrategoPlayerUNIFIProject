package gj.stratego.player.fontani;

import java.util.LinkedList;

public class Strategia {

	public String[] pezziMov = { "MA", "GE", "FM", "SM", "FS", "SS", "SP" };
	public int[] idPezziPos = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	public String[] pezziPos = { "none", "FL", "FB", "SB", "MA", "GE", "FM", "SM", "FS", "SS", "SP", "water", "unk" };
	public int[] power = { 0, 4, 11, 11, 10, 9, 3, 3, 2, 2, 1 };

	//
	// METODI PER SAPERE TUTTE LE MOSSE POSSIBILI
	//
	// 1 inizializza tutte le mosse possibili(eliminando movimenti di bombe e
	// bandiera) richiamata da 2
	public LinkedList<Integer> mosse(int[][] campo, int[][] campoId) {
		LinkedList<Integer> listMove = new LinkedList<Integer>();
		Integer temp = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (campo[i][j] == 1) {
					if (campoId[i][j] != 1 && campoId[i][j] != 2 && campoId[i][j] != 3) {
						for (int e = 1; e < 5; e++) {
							temp = i * 1000 + j * 100 + e * 10 + 1;
							listMove.add(temp);
						}
					}
				}
			}
		}
		return listMove;
	}

	// 2 elimina mosse fuori dal campo, richiamata da 3
	public LinkedList<Integer> outField(int[][] campo, int[][] campoId, boolean first) {
		LinkedList<Integer> listM = mosse(campo, campoId);
		int[] move = new int[4];
		if (first) {
			for (int i = 0; i < listM.size(); i++) {
				if (i == -1) {
					i = 0;
				}
				move = convertionListToAr(listM, i);
				if (move[2] == 1) {
					if (move[0] + move[3] > 9) {
						listM.remove(i);
						i--;
					}
				}
				if (move[2] == 2) {
					if (move[0] - move[3] < 0) {
						listM.remove(i);
						i--;
					}
				}
				if (move[2] == 3) {
					if (move[1] + move[3] > 9) {
						listM.remove(i);
						i--;
					}
				}
				if (move[2] == 4) {
					if (move[1] - move[3] < 0) {
						listM.remove(i);
						i--;
					}
				}
			}
		} else {
			for (int i = 0; i < listM.size(); i++) {
				move = convertionListToAr(listM, i);
				if (move[2] == 1) {
					if (move[0] - move[3] < 0) {
						listM.remove(i);
						i--;
					}
				}
				if (move[2] == 2) {
					if (move[0] + move[3] > 9) {
						listM.remove(i);
						i--;
					}
				}
				if (move[2] == 3) {
					if (move[1] - move[3] < 0) {
						listM.remove(i);
						i--;
					}
				}
				if (move[2] == 4) {
					if (move[1] + move[3] > 9) {
						listM.remove(i);
						i--;
					}
				}
			}
		}
		return listM;
	}

	// 3 elimina mosse che non si possono fare causa acqua o propri pezzi
	// presenti, richiamata in 4
	public LinkedList<Integer> water(int[][] campo, int[][] campoId, boolean first) {
		int[] move = new int[4];
		LinkedList<Integer> listM = outField(campo, campoId, first);
		if (first) {
			for (int i = 0; i < listM.size(); i++) {
				if (i == -1) {
					i = 0;
				}
				move = convertionListToAr(listM, i);
				if (move[2] == 1) {
					if (campo[move[0] + move[3]][move[1]] == 3 || campo[move[0] + move[3]][move[1]] == 1) {
						listM.remove(i);
						i--;
					}
				}
				if (move[2] == 2) {
					if (campo[move[0] - move[3]][move[1]] == 3 || campo[move[0] - move[3]][move[1]] == 1) {
						listM.remove(i);
						i--;
					}
				}
				if (move[2] == 3) {
					if (campo[move[0]][move[1] + move[3]] == 3 || campo[move[0]][move[1] + move[3]] == 1) {
						listM.remove(i);
						i--;
					}
				}
				if (move[2] == 4) {
					if (campo[move[0]][move[1] - move[3]] == 3 || campo[move[0]][move[1] - move[3]] == 1) {
						listM.remove(i);
						i--;
					}
				}
			}
		} else {
			for (int i = 0; i < listM.size(); i++) {
				move = convertionListToAr(listM, i);
				if (move[2] == 1) {
					if (campo[move[0] - move[3]][move[1]] == 3 || campo[move[0] - move[3]][move[1]] == 1) {
						listM.remove(i);
						i--;
					}
				}
				if (move[2] == 2) {
					if (campo[move[0] + move[3]][move[1]] == 3 || campo[move[0] + move[3]][move[1]] == 1) {
						listM.remove(i);
						i--;
					}
				}
				if (move[2] == 3) {
					if (campo[move[0]][move[1] - move[3]] == 3 || campo[move[0]][move[1] - move[3]] == 1) {
						listM.remove(i);
						i--;
					}
				}
				if (move[2] == 4) {
					if (campo[move[0]][move[1] + move[3]] == 3 || campo[move[0]][move[1] + move[3]] == 1) {
						listM.remove(i);
						i--;
					}
				}
			}
		}
		return listM;
	}

	// 4 elimina mosse di celle gia occupate in precedenza, richiamata in 5
	public LinkedList<Integer> lastPosition(int[][] campo, int[][] campoId, int[][][] lastP, boolean first) {
		LinkedList<Integer> listM = water(campo, campoId, first);
		int[] move = new int[4];
		int valPiece = 0;
		int pieceIdLastPos = 0;
		String piece;
		int i = 0;
		boolean flag = false;
		while (listM.size() > i && listM.size() > 0) {
			flag = false;
			move = convertionListToAr(listM, i);
			valPiece = campoId[move[0]][move[1]];
			piece = findPieceName(valPiece, idPezziPos);
			pieceIdLastPos = trovaIdPezzo(pezziMov, piece);
			int j = 1;
			while (j < 4 && !flag) {
				if (lastP[pieceIdLastPos][j][0] != 10) {
					if (first) {
						if (move[2] == 1) {
							if ((move[0] + move[3]) == lastP[pieceIdLastPos][j][0]
									&& move[1] == lastP[pieceIdLastPos][j][1]) {
								listM.remove(i);
								flag = true;

							}
						} else if (move[2] == 2) {
							if ((move[0] - move[3]) == lastP[pieceIdLastPos][j][0]
									&& move[1] == lastP[pieceIdLastPos][j][1]) {
								listM.remove(i);
								flag = true;
							}
						} else if (move[2] == 3) {
							if ((move[0]) == lastP[pieceIdLastPos][j][0]
									&& (move[1] + move[3]) == lastP[pieceIdLastPos][j][1]) {
								listM.remove(i);
								flag = true;
							}
						} else if (move[2] == 4) {
							if ((move[0]) == lastP[pieceIdLastPos][j][0]
									&& (move[1] - move[3]) == lastP[pieceIdLastPos][j][1]) {
								listM.remove(i);
								flag = true;
							}
						}
					} else {
						if (move[2] == 1) {
							if ((move[0] - move[3]) == lastP[pieceIdLastPos][j][0]
									&& move[1] == lastP[pieceIdLastPos][j][1]) {
								listM.remove(i);
								flag = true;
							}
						} else if (move[2] == 2) {
							if ((move[0] + move[3]) == lastP[pieceIdLastPos][j][0]
									&& move[1] == lastP[pieceIdLastPos][j][1]) {
								listM.remove(i);
								flag = true;
							}
						} else if (move[2] == 3) {
							if ((move[0]) == lastP[pieceIdLastPos][j][0]
									&& (move[1] - move[3]) == lastP[pieceIdLastPos][j][1]) {
								listM.remove(i);
								flag = true;
							}
						} else if (move[2] == 4) {
							if ((move[0]) == lastP[pieceIdLastPos][j][0]
									&& (move[1] + move[3]) == lastP[pieceIdLastPos][j][1]) {
								listM.remove(i);
								flag = true;
							}
						}
					}
				}
				j++;
			}
			if (!flag) {
				i++;
			}
		}

		return listM;
	}

	// 5 restituisce la mossa migliore a seconda della situazione nel campo sul
	// momento (richiamata in move nella classe FontaniPlayer)
	int[] chancePieces(int[][] campo, int[][] campoId, int[][][] lastP, boolean first, int[][] hpFlag,
			boolean maAvvDead) {
		int x = 0;
		int[] move = new int[4];
		LinkedList<Integer> listM = lastPosition(campo, campoId, lastP, first);
		if (listM.size() == 0) {
			return null;
		}
		double[] allMove = new double[listM.size()];
		for (int i = 0; i < listM.size(); i++) {
			move = convertionListToAr(listM, i);
			allMove[i] = whichPiece(move, campoId) + attackStrategyIfMa(campo, campoId, move, first)
					+ fightWin(move, campo, campoId, first, maAvvDead) + pezzoVicino(posDopoMossa(first, move), campo)
					+ mossaInCasoDiBomba(move, campo, campoId, first) + attackFlag(move, first, hpFlag, campoId)
					+ defensiveStrategy(campo, campoId, move, first, maAvvDead);
		}
		x = bestMove(allMove);
		return convertionListToAr(listM, x);
	}

	// trasforma un elemento della lista delle mosse, in vettore per il metodo
	// Move della classe FontaniPlayer
	public int[] convertionListToAr(LinkedList<Integer> listM, int x) {
		int value = listM.get(x);
		int[] move = new int[4];
		move[0] = value / 1000;
		value = value - move[0] * 1000;
		move[1] = (value) / 100;
		value = value - move[1] * 100;
		move[2] = value / 10;
		move[3] = (value - move[2] * 10);
		return move;
	}

	// trova l'indice del pezzo in un vettore di stringhe
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

	// sceglie la mossa migliore e la restituisce
	int bestMove(double[] allMove) {
		int i = 1;
		int pos = 0;
		while (i < allMove.length) {
			if (allMove[pos] < allMove[i]) {
				pos = i;
			}
			i++;
		}
		return pos;
	}

	//
	// METODI PER DECIDERE LE MOSSE MIGLIORI
	//

	// metodo che da un valore maggiore ai propri pezzi piu vicini ai pezzi
	// avversari
	double pezzoVicino(int[] move, int[][] campo) {
		double temp = 0;
		double def = 20;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (campo[i][j] == 2) {
					if (i > move[0] && j > move[1]) {
						temp = i - move[0] + j - move[1];
						if (temp < def) {
							def = temp;
						}
					}
					if (i > move[0] && j < move[1]) {
						temp = i - move[0] - j + move[1];
						if (temp < def) {
							def = temp;
						}
					}
					if (i < move[0] && j > move[1]) {
						temp = move[0] - i + j - move[1];
						if (temp < def) {
							def = temp;
						}
					}
					if (i < move[0] && j < move[1]) {
						temp = move[0] - i - j + move[1];
						if (temp < def) {
							def = temp;
						}
					}

				}
			}
		}
		return (18 / def) / 10;
	}

	// controlla se ci puo essere un fight, se c'e da ad ogni mossa una certa
	// priorita altrimenti restituisce 0
	double fightWin(int[] mov, int[][] campo, int[][] campoId, boolean first, boolean maAvvDead) {
		double x = 0;
		double valoreM = 32;
		double valoreMossaSpy = 32;
		double valoreMossaMa = 32;
		double valoreMossaMiner = 32;
		int idPezzo = campoId[mov[0]][mov[1]];
		int pow = power[idPezzo];
		boolean maSeen = false;
		for (int b = 0; b < 10; b++) {
			for (int z = 0; z < 10; z++) {
				if (campo[b][z] == 2 && campoId[b][z] == 4) {
					maSeen = true;
				}
			}
		}
		if (first) {
			if (mov[2] == 1) {
				if (campo[mov[0] + mov[3]][mov[1]] == 2) {
					if (campoId[mov[0] + mov[3]][mov[1]] != 12) {
						int idPezzoAv = campoId[mov[0] + mov[3]][mov[1]];
						if (idPezzoAv != 1 && idPezzoAv != 2 && idPezzoAv != 3) {
							int powAv = power[idPezzoAv];
							if (pow > powAv) {
								x = valoreM;
							} else {
								x = -valoreM;
							}
						}
						if (idPezzoAv == 4 && idPezzo == 10) {
							x = valoreMossaSpy;
						}
						if ((idPezzoAv == 3 || idPezzoAv == 2) && (idPezzo == 6 || idPezzo == 7)) {
							x = valoreMossaMiner;
						}

					} else if (campoId[mov[0] + mov[3]][mov[1]] == 12 && (maAvvDead || maSeen)) {
						if (idPezzo == 4 || idPezzo == 5) {
							x = valoreMossaMa;
						}
					}
				}
			} else if (mov[2] == 2) {
				if (campo[mov[0] - mov[3]][mov[1]] == 2) {
					if (campoId[mov[0] - mov[3]][mov[1]] != 12) {
						int idPezzoAv = campoId[mov[0] - mov[3]][mov[1]];
						if (idPezzoAv != 1 && idPezzoAv != 2 && idPezzoAv != 3) {
							int powAv = power[idPezzoAv];
							if (pow > powAv) {
								x = valoreM;
							} else {
								x = -valoreM;
							}
						}
						if (idPezzoAv == 4 && idPezzo == 10) {
							x = valoreMossaSpy;
						}
						if ((idPezzoAv == 3 || idPezzoAv == 2) && (idPezzo == 6 || idPezzo == 7)) {
							x = valoreMossaMiner;
						}
					} else if (campoId[mov[0] - mov[3]][mov[1]] == 12 && (maAvvDead || maSeen)) {
						if (idPezzo == 4 || idPezzo == 5) {
							x = valoreMossaMa;
						}
					}
				}
			} else if (mov[2] == 3) {
				if (campo[mov[0]][mov[1] + mov[3]] == 2) {
					if (campoId[mov[0]][mov[1] + mov[3]] != 12) {
						int idPezzoAv = campoId[mov[0]][mov[1] + mov[3]];
						if (idPezzoAv != 1 && idPezzoAv != 2 && idPezzoAv != 3) {
							int powAv = power[idPezzoAv];
							if (pow > powAv) {
								x = valoreM;
							} else {
								x = -valoreM;
							}
						}
						if (idPezzoAv == 4 && idPezzo == 10) {
							x = valoreMossaSpy;
						}
						if ((idPezzoAv == 3 || idPezzoAv == 2) && (idPezzo == 6 || idPezzo == 7)) {
							x = valoreMossaMiner;
						}

					} else if (campoId[mov[0]][mov[1] + mov[3]] == 12 && (maAvvDead || maSeen)) {
						if (idPezzo == 4 || idPezzo == 5) {
							x = valoreMossaMa;
						}
					}
				}
			} else if (mov[2] == 4) {
				if (campo[mov[0]][mov[1] - mov[3]] == 2) {
					if (campoId[mov[0]][mov[1] - mov[3]] != 12) {
						int idPezzoAv = campoId[mov[0]][mov[1] - mov[3]];
						if (idPezzoAv != 1 && idPezzoAv != 2 && idPezzoAv != 3) {
							int powAv = power[idPezzoAv];
							if (pow > powAv) {
								x = valoreM;
							} else {
								x = -valoreM;
							}
						}
						if (idPezzoAv == 4 && idPezzo == 10) {
							x = valoreMossaSpy;
						}
						if ((idPezzoAv == 3 || idPezzoAv == 2) && (idPezzo == 6 || idPezzo == 7)) {
							x = valoreMossaMiner;
						}

					} else if (campoId[mov[0]][mov[1] - mov[3]] == 12 && (maAvvDead || maSeen)) {
						if (idPezzo == 4 || idPezzo == 5) {
							x = valoreMossaMa;
						}
					}
				}
			}
		} else {
			if (mov[2] == 1) {
				if (campo[mov[0] - mov[3]][mov[1]] == 2) {
					if (campoId[mov[0] - mov[3]][mov[1]] != 12) {
						int idPezzoAv = campoId[mov[0] - mov[3]][mov[1]];
						if (idPezzoAv != 1 && idPezzoAv != 2 && idPezzoAv != 3) {
							int powAv = power[idPezzoAv];
							if (pow > powAv) {
								x = valoreM;
							} else {
								x = -valoreM;
							}
						}
						if (idPezzoAv == 4 && idPezzo == 10) {
							x = valoreMossaSpy;
						}
						if ((idPezzoAv == 3 || idPezzoAv == 2) && (idPezzo == 6 || idPezzo == 7)) {
							x = valoreMossaMiner;
						}

					} else if (campoId[mov[0] - mov[3]][mov[1]] == 12 && (maAvvDead || maSeen)) {
						if (idPezzo == 4 || idPezzo == 5) {
							x = valoreMossaMa;
						}
					}
				}
			} else if (mov[2] == 2) {
				if (campo[mov[0] + mov[3]][mov[1]] == 2) {
					if (campoId[mov[0] + mov[3]][mov[1]] != 12) {
						int idPezzoAv = campoId[mov[0] + mov[3]][mov[1]];
						if (idPezzoAv != 1 && idPezzoAv != 2 && idPezzoAv != 3) {
							int powAv = power[idPezzoAv];
							if (pow > powAv) {
								x = valoreM;
							} else {
								x = -valoreM;
							}
						}
						if (idPezzoAv == 4 && idPezzo == 10) {
							x = valoreMossaSpy;
						}
						if ((idPezzoAv == 3 || idPezzoAv == 2) && (idPezzo == 6 || idPezzo == 7)) {
							x = valoreMossaMiner;
						}

					} else if (campoId[mov[0] + mov[3]][mov[1]] == 12 && (maAvvDead || maSeen)) {
						if (idPezzo == 4 || idPezzo == 5) {
							x = valoreMossaMa;
						}
					}
				}
			} else if (mov[2] == 3) {
				if (campo[mov[0]][mov[1] - mov[3]] == 2) {
					if (campoId[mov[0]][mov[1] - mov[3]] != 12) {
						int idPezzoAv = campoId[mov[0]][mov[1] - mov[3]];
						if (idPezzoAv != 1 && idPezzoAv != 2 && idPezzoAv != 3) {
							int powAv = power[idPezzoAv];
							if (pow > powAv) {
								x = valoreM;
							} else {
								x = -valoreM;
							}
						}
						if (idPezzoAv == 4 && idPezzo == 10) {
							x = valoreMossaSpy;
						}
						if ((idPezzoAv == 3 || idPezzoAv == 2) && (idPezzo == 6 || idPezzo == 7)) {
							x = valoreM;
						}

					} else if (campoId[mov[0]][mov[1] - mov[3]] == 12 && (maAvvDead || maSeen)) {
						if (idPezzo == 4 || idPezzo == 5) {
							x = valoreMossaMa;
						}
					}
				}
			} else if (mov[2] == 4) {
				if (campo[mov[0]][mov[1] + mov[3]] == 2) {
					if (campoId[mov[0]][mov[1] + mov[3]] != 12) {
						int idPezzoAv = campoId[mov[0]][mov[1] + mov[3]];
						if (idPezzoAv != 1 && idPezzoAv != 2 && idPezzoAv != 3) {
							int powAv = power[idPezzoAv];
							if (pow > powAv) {
								x = valoreM;
							} else {
								x = -valoreM;
							}
						}
						if (idPezzoAv == 4 && idPezzo == 10) {
							x = valoreMossaSpy;
						}
						if ((idPezzoAv == 3 || idPezzoAv == 2) && (idPezzo == 6 || idPezzo == 7)) {
							x = valoreMossaMiner;
						}

					} else if (campoId[mov[0]][mov[1] + mov[3]] == 12 && (maAvvDead || maSeen)) {
						if (idPezzo == 4 || idPezzo == 5) {
							x = valoreMossaMa;
						}
					}
				}
			}
		}
		return x;
	}

	// se la carta a cui corrisponde la mossa e' uno scout o MA da una certa
	// priorita a tale mossa
	double whichPiece(int[] mov, int[][] campoId) {
		int x = campoId[mov[0]][mov[1]];
		double p = 0;
		if (x == 8 || x == 9) {
			p = 26;
		} else if (x == 4) {
			p = 22;
		}
		return p;
	}

	// restituisce dove sono state scoperte bombe
	int[] areThereBomb(int[][] campo, int[][] campoId) {
		boolean flag = false;
		int i = 0;
		int e = 0;
		int[] mov = new int[2];
		while (!flag && e < 10 && i < 10) {
			for (i = 0; i < 10; i++) {
				for (e = 0; e < 10; e++) {
					if (campo[i][e] == 2 && (campoId[i][e] == 2 || campoId[i][e] == 3)) {
						flag = true;
						mov[0] = i;
						mov[1] = e;
					}
				}
			}
		}
		if (!flag) {
			return null;
		}
		return mov;
	}

	// dice da quale parte la bomba e scoperta
	int[] isBombUncovered(int[][] campo, int[][] campoId, boolean first) {
		int[] posBomb = areThereBomb(campo, campoId);
		int[] freePos = new int[2];
		boolean flag = false;
		if (posBomb == null) {
			return null;
		}
		if (first) {
			if (posBomb[0] != 9 && posBomb[1] == 0) {
				if ((campo[posBomb[0] + 1][posBomb[1]] == 0)) {
					flag = true;
					freePos[0] = posBomb[0] + 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0] - 1][posBomb[1]] == 0) {
					flag = true;
					freePos[0] = posBomb[0] - 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0]][posBomb[1] + 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] + 1;
				}
			} else if (posBomb[0] == 9 && posBomb[1] != 0 && posBomb[1] != 9) {
				if ((campo[posBomb[0]][posBomb[1] - 1] == 0)) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] - 1;
				} else if (campo[posBomb[0]][posBomb[1] + 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] + 1;
				} else if (campo[posBomb[0] - 1][posBomb[1]] == 0) {
					flag = true;
					freePos[0] = posBomb[0] - 1;
					freePos[1] = posBomb[1];
				}
			} else if (posBomb[0] != 9 && posBomb[1] == 9) {
				if ((campo[posBomb[0] + 1][posBomb[1]] == 0)) {
					flag = true;
					freePos[0] = posBomb[0] + 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0] - 1][posBomb[1]] == 0) {
					flag = true;
					freePos[0] = posBomb[0] - 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0]][posBomb[1] - 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] - 1;
				}
			} else if (posBomb[0] == 9 && posBomb[1] == 0) {
				if ((campo[posBomb[0] - 1][posBomb[1]] == 0)) {
					flag = true;
					freePos[0] = posBomb[0] - 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0]][posBomb[1] + 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] + 1;
				}
			} else if (posBomb[0] == 9 && posBomb[1] == 9) {
				if ((campo[posBomb[0] - 1][posBomb[1]] == 0)) {
					flag = true;
					freePos[0] = posBomb[0] - 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0]][posBomb[1] - 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] - 1;
				}
			} else if (posBomb[0] != 9 && posBomb[1] != 0 && posBomb[1] != 9) {
				if ((campo[posBomb[0] - 1][posBomb[1]] == 0)) {
					flag = true;
					freePos[0] = posBomb[0] - 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0] + 1][posBomb[1]] == 0) {
					flag = true;
					freePos[0] = posBomb[0] + 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0]][posBomb[1] - 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] - 1;
				} else if (campo[posBomb[0]][posBomb[1] + 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] + 1;
				}
			}
		} else {
			if (posBomb[0] != 0 && posBomb[1] == 0) {
				if ((campo[posBomb[0] + 1][posBomb[1]] == 0)) {
					flag = true;
					freePos[0] = posBomb[0] + 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0] - 1][posBomb[1]] == 0) {
					flag = true;
					freePos[0] = posBomb[0] - 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0]][posBomb[1] + 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] + 1;
				}
			} else if (posBomb[0] == 0 && posBomb[1] != 0 && posBomb[1] != 9) {
				if ((campo[posBomb[0]][posBomb[1] - 1] == 0)) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] - 1;
				} else if (campo[posBomb[0]][posBomb[1] + 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] - 1;
				} else if (campo[posBomb[0] + 1][posBomb[1]] == 0) {
					flag = true;
					freePos[0] = posBomb[0] + 1;
					freePos[1] = posBomb[1];
				}
			} else if (posBomb[0] != 0 && posBomb[1] == 9) {
				if ((campo[posBomb[0] + 1][posBomb[1]] == 0)) {
					flag = true;
					freePos[0] = posBomb[0] + 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0] - 1][posBomb[1]] == 0) {
					flag = true;
					freePos[0] = posBomb[0] - 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0]][posBomb[1] - 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] - 1;
				}
			} else if (posBomb[0] == 0 && posBomb[1] == 0) {
				if ((campo[posBomb[0] + 1][posBomb[1]] == 0)) {
					flag = true;
					freePos[0] = posBomb[0] + 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0]][posBomb[1] + 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] + 1;
				}
			} else if (posBomb[0] == 0 && posBomb[1] == 9) {
				if ((campo[posBomb[0] - 1][posBomb[1]] == 0)) {
					flag = true;
					freePos[0] = posBomb[0] - 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0]][posBomb[1] + 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] + 1;
				}
			} else if (posBomb[0] != 0 && posBomb[1] != 0 && posBomb[1] != 9) {
				if ((campo[posBomb[0] - 1][posBomb[1]] == 0)) {
					flag = true;
					freePos[0] = posBomb[0] - 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0] + 1][posBomb[1]] == 0) {
					flag = true;
					freePos[0] = posBomb[0] + 1;
					freePos[1] = posBomb[1];
				} else if (campo[posBomb[0]][posBomb[1] - 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] - 1;
				} else if (campo[posBomb[0]][posBomb[1] + 1] == 0) {
					flag = true;
					freePos[0] = posBomb[0];
					freePos[1] = posBomb[1] + 1;
				}
			}
		}
		if (!flag) {
			return null;
		}
		return freePos;
	}

	// se c'e una bomba scoperta, da' priorita' alle mosse che avvicinano i
	// miner alla bomba
	double mossaInCasoDiBomba(int[] move, int[][] campo, int[][] campoId, boolean first) {
		int pezzo = campoId[move[0]][move[1]];
		int[] posToReach = isBombUncovered(campo, campoId, first);
		int[] posAftMove = posDopoMossa(first, move);
		double x = 0;
		if (posToReach == null || (pezzo != 6 && pezzo != 7)) {
			return 0;
		} else {
			x = destination(posToReach[0], posToReach[1], move, posAftMove, campo, 24);
		}

		return x;

	}

	// aggiorna possibili flag
	void possibFlag(int[][] hpF, int[] movim) {
		for (int i = 0; i < 10; i++) {
			if (hpF[i][0] == movim[0] && hpF[i][1] == movim[1]) {
				hpF[i][0] = 10;
				hpF[i][1] = 10;
			}
		}
	}

	// se un pezzo e vicino ad una possibile flag, la attacca
	double attackFlag(int[] move, boolean first, int[][] hpF, int[][] campoId) {
		int[] posAftMove = posDopoMossa(first, move);
		int x = 0;
		for (int i = 0; i < 10; i++) {
			if (campoId[posAftMove[0]][posAftMove[1]] == 12) {
				if (hpF[i][0] == posAftMove[0] && hpF[i][1] == posAftMove[1]) {
					x = 100;
				}
			}
		}
		return x;
	}

	// se qualcuno si avvicina alla flag si da priorita alla carta GE che
	// attacchera' i pezzi avversari
	double defensiveStrategy(int[][] campo, int[][] campoId, int[] move, boolean first, boolean maAvvDead) {
		int pezzo = campoId[move[0]][move[1]];
		int[] posAftMove = posDopoMossa(first, move);
		double x = 0;
		boolean maSeen = false;
		for (int b = 0; b < 10; b++) {
			for (int z = 0; z < 10; z++) {
				if (campo[b][z] == 2 && campoId[b][z] == 4) {
					maSeen = true;
				}
			}
		}
		if (first) {
			for (int i = 0; i < 5; i++) {
				for (int e = 0; e < 5; e++) {
					if (campo[i][e] == 2) {
						if (campoId[i][e] != 4 && (maSeen || maAvvDead)) {
							if (pezzo == 5) {
								x = destination(i, e, move, posAftMove, campo, 28);
							}
						}
					}

				}
			}

		} else {
			for (int p = 5; p < 10; p++) {
				for (int k = 5; k < 10; k++) {
					if (campo[p][k] == 2) {
						if (campoId[p][k] != 4 && (maSeen || maAvvDead)) {
							if (pezzo == 5) {
								x = destination(p, k, move, posAftMove, campo, 28);
							}
						}

					}
				}

			}
		}
		return x;
	}

	// se MA avversario viene scoperto, viene data priorita alla mossa che
	// avvicina SP del mio player all'MA avversario
	double attackStrategyIfMa(int[][] campo, int[][] campoId, int[] move, boolean first) {
		int pezzo = campoId[move[0]][move[1]];
		int[] posAftMove = posDopoMossa(first, move);
		double x = 0;
		for (int i = 0; i < 10; i++) {
			for (int e = 0; e < 10; e++) {
				if (campo[i][e] == 2 && campoId[i][e] == 4) {
					if (pezzo == 10) {
						if (!(posAftMove[0] == i && posAftMove[1] == e + 1)
								&& !(posAftMove[0] == i && posAftMove[1] == e - 1)
								&& !(posAftMove[0] == i + 1 && posAftMove[1] == e)
								&& !(posAftMove[0] == i - 1 && posAftMove[1] == e)) {
							x = destination(i, e, move, posAftMove, campo, 30);
						}
					}

				}
			}
		}
		return x;
	}

	// controlla se MA avversario e' stato eliminato dal campo di gioco
	boolean maAvvIsDead(int[][] campo, int[][] campoId, String pezzoAvv) {
		boolean maAvD = true;
		if (pezzoAvv.equals("MA")) {
			for (int i = 0; i < 10; i++) {
				for (int e = 0; e < 10; e++) {
					if (campo[i][e] == 2 && campoId[i][e] == 4) {
						maAvD = false;
					}
				}
			}
		} else {
			maAvD = false;
		}
		return maAvD;
	}

	// metodo che da' piu importanza ai movimenti che avvicinano un pezzo ad una
	// posizione sul campo
	double destination(int i, int e, int[] move, int[] posAftMove, int[][] campo, int valoreMossa) {
		int x = 0;
		if (campo[posAftMove[0]][posAftMove[1]] == 0) {
			if (move[0] < i && move[1] < e) {
				if ((i - posAftMove[0] <= i - move[0]) && e - posAftMove[1] <= e - move[1]) {
					x = valoreMossa;
				}
			} else if (move[0] < i && move[1] > e) {
				if ((i - posAftMove[0] <= i - move[0]) && e - posAftMove[1] >= e - move[1]) {
					x = valoreMossa;
				}
			} else if (move[0] > i && move[1] > e) {
				if ((i - posAftMove[0] >= i - move[0]) && e - posAftMove[1] >= e - move[1]) {
					x = valoreMossa;
				}
			} else if (move[0] > i && move[1] < e) {
				if ((i - posAftMove[0] >= i - move[0]) && e - posAftMove[1] <= e - move[1]) {
					x = valoreMossa;
				}
			} else if (move[0] == i && move[1] > e) {
				if (e - posAftMove[1] > e - move[1]) {
					x = valoreMossa;
				}
			} else if (move[0] == i && move[1] < e) {
				if (e - posAftMove[1] < e - move[1]) {
					x = valoreMossa;
				}
			} else if (move[0] > i && move[1] == e) {
				if (i - posAftMove[0] > i - move[0]) {
					x = valoreMossa;
				}
			} else if (move[0] < i && move[1] == e) {
				if (i - posAftMove[0] < i - move[0]) {
					x = valoreMossa;
				}
			}
		}
		return x;
	}

	// trova la posizione che viene occupata dopo che viene richiamato il metodo
	// Move della classe FontaniPlayer
	int[] posDopoMossa(boolean first, int[] movim) {
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