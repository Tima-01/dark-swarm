package ds.item.custom;

public enum ArmorEnhancement {
    FIRE("fire_enhanced"),
    ICE("ice_enhanced");

    private final String id;

    ArmorEnhancement(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}