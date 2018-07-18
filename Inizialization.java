package gj.stratego.player.fontani;

import java.util.Scanner;

public class Inizialization {

	Scanner scan = new Scanner(System.in);
	Posizione p = new Posizione();
	public String[] pezziIni = { "FL", "FB", "SB", "MA", "GE", "FM", "SM", "FS", "SS", "SP" };
	public int[] idPezziIni = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	// metodo per trovare l'indice corrispondente ad una stringa in un vettore
	// di stringhe
	public int trovaIdPezzo(String[] pezzi, String pezzo) {
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

	// posizionamento pezzi automatico
	public int[] posAuto(String piece, String[] pezziS, int[][] position, int[][][] lastPos) {
		int i = trovaIdPezzo(pezziS, piece);
		int[] pos = position[i];
		return pos;
	}

	// Inizializzazione di lastposition
	public void inizialLastPos(int[][][] lastP) {
		for (int i = 0; i < 7; i++) {
			for (int j = 1; j < 4; j++) {
				lastP[i][j][0] = 10;
				lastP[i][j][1] = 10;
			}
			lastP[i][0][0] = 1;
		}

	}

	// Inizializzazione possibili flag
	public void inizialHpFlag(int[][] hpF) {
		for (int i = 0; i < 10; i++) {
			hpF[i][0] = 10;
			hpF[i][1] = 10;
		}
	}

	// Inizializzazione campi di gioco
	public void inizialField(int[][] field, int[][] fieldId) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				field[i][j] = 0;
				fieldId[i][j] = 0;
			}
		}
		field[4][2] = 3;
		field[4][3] = 3;
		field[5][2] = 3;
		field[5][3] = 3;
		field[4][6] = 3;
		field[4][7] = 3;
		field[5][6] = 3;
		field[5][7] = 3;
		fieldId[4][2] = 11;
		fieldId[4][3] = 11;
		fieldId[5][2] = 11;
		fieldId[5][3] = 11;
		fieldId[4][6] = 11;
		fieldId[4][7] = 11;
		fieldId[5][6] = 11;
		fieldId[5][7] = 11;
	}
}