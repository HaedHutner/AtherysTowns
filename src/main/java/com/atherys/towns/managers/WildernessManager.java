package com.atherys.towns.managers;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;

public interface WildernessManager {

    void regenerate ( long timestamp );

    void saveOne ( Transaction<BlockSnapshot> transaction );

}
