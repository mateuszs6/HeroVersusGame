package game;

import city.cs.engine.BoxShape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class GameWorld extends World {
    private final Player player1;
    private final Player player2;

    /** Initialise the game world. */
    public GameWorld() {
        // Stage
        BoxShape stageShape = new BoxShape(7, 0.5f);
        StaticBody stage = new StaticBody(this, stageShape);
        stage.setPosition(new Vec2(0, -12));
        stage.setFillColor(new Color(0, true));
        stage.setLineColor(new Color(0, true));

        // Suspended platform
        BoxShape platformShape = new BoxShape(2.5f, 0.5f);
        StaticBody platform = new StaticBody(this, platformShape);
        platform.setPosition(new Vec2(-7.5f, -5.5f));

        // Player 1
        player1 = new Player(this, new Knight());
        player1.setPosition(new Vec2(-4, -9));

        // Player 2
        player2 = new Player(this, new Skeleton());
        player2.setPosition(new Vec2(4, -9));
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}