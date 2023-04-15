package world;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.BattleArena;
import player.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FallToDeath implements CollisionListener, ActionListener {
    private BattleArena world;

    public FallToDeath(BattleArena world) {
        this.world = world;
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player) {
            e.getReportingBody().removeAllCollisionListeners();
            ((Player) e.getOtherBody()).setHealth(0);
            world.respawnPlayer((Player) e.getOtherBody());

            Timer timer = new Timer(500, this);
            timer.setRepeats(false);
            timer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        world.getDeathBarrier().addCollisionListener(this);
    }
}
