package miner.conditions;

import miner.logic.Game;
/**
 * используется для создания условия проигрыша
 */
public interface ConditionLose {
    boolean isLose (Game game);
}
