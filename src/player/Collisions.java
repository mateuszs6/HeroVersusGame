package player;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import game.BattleArena;
import world.HealthRefill;
import world.Platform;

public class Collisions implements CollisionListener {
    private BattleArena world;
    private Player player;

    public Collisions(BattleArena world, Player player) {
        this.world = world;
        this.player = player;
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Platform) if (!player.isAttacking()) {
            player.removeAllImages();
            player.addImage(player.getCharacter().getIdleImage());
        }

        if (e.getOtherBody() instanceof Player) if (player.isAttacking()) {
            ((Player) e.getOtherBody()).setHealth(((Player) e.getOtherBody()).getHealth() - player.getCharacter().getAttackDamage());
            System.out.println("Attacked player health: " + ((Player) e.getOtherBody()).getHealth());
            player.respawn();
        }

        if (e.getOtherBody() instanceof HealthRefill) if (player.getHealth() < player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
            e.getOtherBody().destroy();
        }
    }
}