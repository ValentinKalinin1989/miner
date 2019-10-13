package miner.conditions;

import miner.logic.Game;

public class OpenThreeBombToLose implements ConditionLose {
    @Override
    public boolean isLose(Game game) {
        boolean result = false;
        if (game.getBombField().getTotalBombed() == 3) {
            result = true;
        }
        return result;
    }
}
