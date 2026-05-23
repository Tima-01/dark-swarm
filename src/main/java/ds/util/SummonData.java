package ds.util;

import java.util.UUID;

public record SummonData(
        UUID entityId,
        float healthCost
){}
