package ds.entity.custom;

import java.util.UUID;

public record SummonData(
        UUID entityId,
        float healthCost
){}
