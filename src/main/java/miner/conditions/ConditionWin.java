package miner.conditions;

import miner.logic.Game;
/**
 * используется для создания условия победы
 */
public interface ConditionWin {
    boolean isWin(Game game);
}
