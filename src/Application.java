import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Application extends JPanel {
	private static final long serialVersionUID = 1L;

	// Display size
	private static final int wid = 1024, hei = 1024;

	private final int size;
	private List<Triomino> triominos;

	public Application(int size, int x, int y) {
		this.size = size;

		// Triomino border looks ugly on larger resolutions
		if (size > 256)
			Triomino.drawBorder = false;

		triominos = genTriominos(x, y);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				initWindow();
			}
		});
	}

	private List<Triomino> genTriominos(int x, int y) {
		List<Triomino> generated = new ArrayList<Triomino>();

		// Add empty square at location
		generated.add(new Triomino(Triomino.EMPTY, x, y));

		int loff = 1, roff = 1, uoff = 1, doff = 1;
		// Recursively fill triomino shapes building off of existing block
		for (int i = 1; i < size; i *= 2) {
			final boolean left = (x & i) == 0;
			final boolean up = (y & i) == 0;
			if (left) {
				if (up) {
					fillTriomino(generated, Triomino.UL, x + loff, y + uoff, i);
					loff += i;
					uoff += i;
				} else {
					fillTriomino(generated, Triomino.DL, x + loff, y - doff, i);
					loff += i;
					doff += i;
				}
			} else {
				if (up) {
					fillTriomino(generated, Triomino.UR, x - roff, y + uoff, i);
					roff += i;
					uoff += i;
				} else {
					fillTriomino(generated, Triomino.DR, x - roff, y - doff, i);
					roff += i;
					doff += i;
				}

			}
		}

		return generated;
	}

	private void fillTriomino(List<Triomino> triominos, int ID, int x, int y, int scale) {
		// Break recursion at scale = 1.
		if (scale == 1) {
			triominos.add(new Triomino(ID, x, y));
			return;
		}

		final int halfOff = scale / 2, fullOff = scale / 2 + 1;
		// A size 2n triomino is composed of 4 size n triominos.
		// One at the inside corner, and 3 touching each edge.
		// Depending on shape, call recursive function to fill.
		switch (ID) {
		case Triomino.UL: // _| shape
			// Fill core triomino
			fillTriomino(triominos, Triomino.UL, x, y, scale / 2);
			// Fill top right triomino
			fillTriomino(triominos, Triomino.DL, x + halfOff, y - fullOff, scale / 2);
			// Fill bottom right triomino
			fillTriomino(triominos, Triomino.UL, x + halfOff, y + halfOff, scale / 2);
			// Fill bottom left triomino
			fillTriomino(triominos, Triomino.UR, x - fullOff, y + halfOff, scale / 2);
			break;

		case Triomino.UR: // |_ shape
			// Fill core triomino
			fillTriomino(triominos, Triomino.UR, x, y, scale / 2);
			// Fill top left triomino
			fillTriomino(triominos, Triomino.DR, x - halfOff, y - fullOff, scale / 2);
			// Fill bottom left triomino
			fillTriomino(triominos, Triomino.UR, x - halfOff, y + halfOff, scale / 2);
			// Fill bottom right triomino
			fillTriomino(triominos, Triomino.UL, x + fullOff, y + halfOff, scale / 2);
			break;

		case Triomino.DL: // --| shape
			// Fill core triomino
			fillTriomino(triominos, Triomino.DL, x, y, scale / 2);
			// Fill top right triomino
			fillTriomino(triominos, Triomino.UL, x + halfOff, y + fullOff, scale / 2);
			// Fill bottom right triomino
			fillTriomino(triominos, Triomino.DL, x + halfOff, y - halfOff, scale / 2);
			// Fill bottom left triomino
			fillTriomino(triominos, Triomino.DR, x - fullOff, y - halfOff, scale / 2);
			break;
		case Triomino.DR: // |-- shape
			// Fill core triomino
			fillTriomino(triominos, Triomino.DR, x, y, scale / 2);
			// Fill top right triomino
			fillTriomino(triominos, Triomino.UR, x - halfOff, y + fullOff, scale / 2);
			// Fill bottom right triomino
			fillTriomino(triominos, Triomino.DR, x - halfOff, y - halfOff, scale / 2);
			// Fill bottom left triomino
			fillTriomino(triominos, Triomino.DL, x + fullOff, y - halfOff, scale / 2);
			break;

		}
	}

	private void initWindow() {
		setPreferredSize(new Dimension(wid, hei));
		// Improves rendering
		setDoubleBuffered(true);
		setBackground(Color.BLACK);

		JFrame window = new JFrame();
		window.add(this);
		window.setResizable(false);
		window.setTitle("Triomino Board");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		// Graphics2D for increased access to functions
		Graphics2D g = (Graphics2D) graphics;
		// Scale triominos to window size
		g.scale((float) wid / (Triomino.size * (size)), (float) hei / (Triomino.size * (size)));

		for (Triomino t : triominos)
			t.draw(g);

	}
}
