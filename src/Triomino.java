import java.awt.*;
import java.awt.geom.Area;

public class Triomino {
	public static boolean drawBorder = true;

	public static final int size = 32;

	public static final int EMPTY = -1, UL = 0, UR = 1, DL = 2, DR = 3;

//	private final int x, y;
	private final Area shape;
	private final Color drawCol;

	public Triomino(int ID, int x, int y) {
//		this.x = x;
//		this.y = y;

		// Generate draw shape by Triomino type
		shape = new Area();
		addShapeLoc(x, y);
		// Set remaining shape and color based off type
		switch (ID) {
		default:
		case EMPTY:
			drawCol = Color.white;
			break;
		case UL:
			addShapeLoc(x - 1, y);
			addShapeLoc(x, y - 1);
			drawCol = Color.red;
			break;
		case UR:
			addShapeLoc(x + 1, y);
			addShapeLoc(x, y - 1);
			drawCol = Color.green;
			break;
		case DL:
			addShapeLoc(x - 1, y);
			addShapeLoc(x, y + 1);
			drawCol = Color.blue;
			break;
		case DR:
			addShapeLoc(x + 1, y);
			addShapeLoc(x, y + 1);
			drawCol = Color.yellow;
			break;
		}
	}

	private void addShapeLoc(int x, int y) {
		shape.add(new Area(new Rectangle(x * size, y * size, size, size)));

	}

	public void draw(Graphics2D g) {
		g.setColor(drawCol);
		g.fill(shape);

		if (Triomino.drawBorder) {
			g.setColor(Color.black);
			g.draw(shape);
		}
	}
}
