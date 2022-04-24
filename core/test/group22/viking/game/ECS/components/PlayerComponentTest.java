package group22.viking.game.ECS.components;

import junit.framework.TestCase;

import org.junit.Assert;

import group22.viking.game.models.ECS.components.PlayerComponent;

public class PlayerComponentTest extends TestCase {

    public void testAddToHealth() {
        PlayerComponent playerComponent = new PlayerComponent();
        Assert.assertEquals(playerComponent.getHealth(),1000);

        playerComponent.addToHealth(-50);
        assertEquals(playerComponent.getHealth(),950);

        playerComponent.addToHealth(20);
        assertEquals(playerComponent.getHealth(), 970);

        playerComponent.addToHealth(-1000);
        assertEquals(playerComponent.getHealth(),0);
    }
}