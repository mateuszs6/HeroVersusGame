package player;

import city.cs.engine.SensorEvent;
import city.cs.engine.SensorListener;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.BattleArena;

public class HitboxDetection implements SensorListener, StepListener {
    private BattleArena world;
    private Player player;

    public HitboxDetection(BattleArena world, Player player) {
        this.world = world;
        this.player = player;
    }

    @Override
    public void beginContact(SensorEvent e) {
        if (e.getContactBody() instanceof Player) if (player.isAttacking()) {
            System.out.println("hit");
        }
    }

    @Override
    public void endContact(SensorEvent e) {
        System.out.println("unhit");
    }

    @Override
    public void preStep(StepEvent stepEvent) {

    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }
}
