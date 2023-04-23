package player;

import characters.Character;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player extends Walker implements ActionListener {
    private final int ID;
    private final Character character;
    private KeyBindings keyBindings;
    private final Vec2 startPos;
    private SolidFixture hitbox;
    private int
            health,
            respawns;
    private static final int MAX_HEALTH = 100;
    private static final int MAX_RESPAWNS = 3;
    private boolean
            isFacingRight,
            isMidAir,
            isAttacking;

    /** Initialise a player. */
    public Player(World w,
                  int number,
                  Character character,
                  Vec2 position) {
        super(w);

        ID = number;
        this.character = character;
        this.character.setPlayer(this);
        startPos = position;

        hitbox = new SolidFixture(this, character.getDefaultShape());
        setAlwaysOutline(false);
        setGravityScale(2);

        health = MAX_HEALTH;
        respawns = MAX_RESPAWNS;

        isFacingRight = ID == 1;
        addImage(character.getIdleImage());
        isMidAir = false;
        isAttacking = false;

        setPosition(startPos);
        addCollisionListener(new Collisions(this));
    }

    public Character getCharacter() {
        return character;
    }

    public KeyBindings getKeyBindings() {
        return keyBindings;
    }

    public void setKeyBindings(KeyBindings keybindings) {
        this.keyBindings = keybindings;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    public int getRespawns() {
        return respawns;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public boolean isMidAir() {
        return isMidAir;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void idle() {
        removeAllImages();
        if (isMidAir) addImage(character.getJumpImage());
        else addImage(character.getIdleImage());

        stopWalking();
    }

    public void jump() {
        isMidAir = true;
        removeAllImages();
        addImage(character.getJumpImage());

        jump(character.getJumpSpeed());
    }

    public void land() {
        isMidAir = false;
        removeAllImages();
        addImage(character.getIdleImage());
    }

    public void run(int direction) {
        if (direction == 1) isFacingRight = true;
        else if (direction == -1) isFacingRight = false;

        removeAllImages();
        if (isMidAir) addImage(character.getJumpImage());
        else addImage(character.getRunImage());

        startWalking(character.getRunSpeed() * direction);
    }

    public void attack() {
        if (!isMidAir && !isAttacking) {
            isAttacking = true;

            Timer timer = new Timer(character.getAttackDuration(), this);
            timer.setRepeats(false);
            timer.start();

            updateHitbox();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        isAttacking = false;
        updateHitbox();
    }

    public void updateHitbox() {
        hitbox.destroy();
        removeAllImages();
        if (isAttacking) {
            hitbox = new SolidFixture(this, character.getAttackShape());
            addImage(character.getAttackImage());
        } else {
            hitbox = new SolidFixture(this, character.getDefaultShape());
            addImage(character.getIdleImage());
        }
    }

    public void respawn() {
        if (respawns < 2 && health < 1) {
            respawns = 0;
            destroy();
            System.out.println("Player " + ID + " is defeated.");

        } else if (health < 1) {
            isFacingRight = ID == 1;
            setPosition(startPos);

            health = MAX_HEALTH;
            respawns--;
        }
    }

    public void drawHUD(Graphics2D g, int w, int h, int x, int y) {
        // Save colour and font
        Color savedColour = g.getColor();
        Font savedFont = g.getFont();

        Color panel = new Color(255, 255, 255, 65);

        // Back panel
        g.setColor(panel);
        g.fillRoundRect(x, y, w, h, 10, 10);
//        g.setColor(panel);
//        g.fillRect(x + 2, y + 2, w - 4, h - 4);

        // Name
        g.setFont(new Font("Bahnschrift", Font.BOLD, 15));
        g.setColor(Color.BLACK);
        g.drawString("Player " + ID, x + 7, y + 20);

        // Health bar
        int xPos = x + 7;
        int yPos = y + 25;
        int width = w - 14;
        int height = 15;
        g.setColor(Color.RED);
        g.fillRect(xPos, yPos, width, height);
        float factor = (float) health / 100;
        g.setColor(Color.GREEN);
        g.fillRect(xPos, yPos, (int) (width * factor), height);

        // Lives
        g.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
        g.setColor(Color.BLACK);
        g.drawString("Respawns: " + respawns, x + 7, y + 55);

        // Reset to saved colour and font
        g.setColor(savedColour);
        g.setFont(savedFont);
    }
}