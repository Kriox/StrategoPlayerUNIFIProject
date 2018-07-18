package gj.stratego.player.fontani;

import java.util.Arrays;

import gj.stratego.player.Player;

public class FontaniPlayer implements Player {
	Inizialization ini = new Inizialization();
	Posizione p = new Posizione();
	Fighting f = new Fighting();
	Strategia s = new Strategia();
	String[] pezziMain = { "FL", "FB", "SB", "MA", "GE", "FM", "SM", "FS", "SS", "SP" };
	int[][] pezziPrimo = { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 2, 2 }, { 0, 2 }, { 2, 0 }, { 1, 1 }, { 2, 1 }, { 1, 3 },
			{ 1, 2 } };
	int[][] pezziSecondo = { { 9, 9 }, { 8, 9 }, { 9, 8 }, { 7, 8 }, { 7, 7 }, { 7, 9 }, { 8, 8 }, { 9, 7 }, { 8, 6 },
			{ 8, 7 } };
	// ultima posizione dei pezzi
	public int[][][] lastPos = { { { 1 }, { 10, 10 }, { 10, 10 }, { 10, 10 } },
			{ { 1 }, { 10, 10 }, { 10, 10 }, { 10, 10 } }, { { 1 }, { 10, 10 }, { 10, 10 }, { 10, 10 } },
			{ { 1 }, { 10, 10 }, { 10, 10 }, { 10, 10 } }, { { 1 }, { 10, 10 }, { 10, 10 }, { 10, 10 } },
			{ { 1 }, { 10, 10 }, { 10, 10 }, { 10, 10 } }, { { 1 }, { 10, 10 }, { 10, 10 }, { 10, 10 } } };
	// campo da gioco che indica se i pezzi sono miei o dell'avversario
	public int[][] campo = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 3, 3, 0, 0, 3, 3, 0, 0 },
			{ 0, 0, 3, 3, 0, 0, 3, 3, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
	// campo da gioco che indica quali pezzi ho in una certa posizione
	public int[][] campoId = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 11, 11, 0, 0, 11, 11, 0, 0 },
			{ 0, 0, 11, 11, 0, 0, 11, 11, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
	// posizione di possibili FL
	int[][] hpFlag = { { 10, 10 }, { 10, 10 }, { 10, 10 }, { 10, 10 }, { 10, 10 }, { 10, 10 }, { 10, 10 }, { 10, 10 },
			{ 10, 10 }, { 10, 10 } };
	public boolean first;
	public boolean attaccoIo;
	public String pezzoAvv;
	public int[] movPlayer = new int[4];
	public int[] movAv = new int[4];
	public boolean maAvvDead = false;

	public void viewPositions(int[][] position) {
		p.salvaCampoInizio(position, campo, campoId, first, hpFlag);
	}

	public void fight(String piece) {
		pezzoAvv = piece;
		p.aggCampi(attaccoIo, movPlayer, movAv, first, campo, campoId, pezzoAvv, hpFlag);
		if (!maAvvDead) {
			maAvvDead = s.maAvvIsDead(campo, campoId, piece);
		}
	}

	public int[] move() {
		attaccoIo = true;
		movPlayer = s.chancePieces(campo, campoId, lastPos, first, hpFlag, maAvvDead);
		if (movPlayer == null) {
			return null;
		}
		p.aggMossaPl(movPlayer, first, campo, campoId, lastPos);
		int[] r = Arrays.copyOf(movPlayer, movPlayer.length);
		return r;
	}

	public int[] position(String piece) {
		int[] pos = new int[2];
		if (first) { // posizionamento automatico a seconda se e' il primo o il
						// secondo giocatore
			pos = ini.posAuto(piece, pezziMain, pezziPrimo, lastPos);
		} else {
			pos = ini.posAuto(piece, pezziMain, pezziSecondo, lastPos);
		}
		campo[pos[0]][pos[1]] = 1;// aggiorna campo
		campoId[pos[0]][pos[1]] = p.valorePezzoPos(piece);
		return pos;
	}

	public void start(boolean startFirst) {
		first = startFirst;
		ini.inizialField(campo, campoId);
		ini.inizialLastPos(lastPos);
		ini.inizialHpFlag(hpFlag);
		maAvvDead = false;
	}

	public void tellMove(int[] movimento) {
		movAv = movimento;
		attaccoIo = false;
		s.possibFlag(hpFlag, movimento);
		p.aggMossaAvv(movimento, first, campo, campoId);
	}

}
