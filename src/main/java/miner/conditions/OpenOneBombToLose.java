package miner.conditions;

import miner.logic.Game;

public class OpenOneBombToLose implements ConditionLose {
    @Override
    public boolean isLose(Game game) {
        boolean result = false;
        if (game.getBombField().getTotalBombed() == 1) {
            result = true;
        }
        return result;
    }
}
