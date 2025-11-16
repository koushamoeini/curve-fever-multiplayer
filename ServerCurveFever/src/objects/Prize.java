package objects;

public class Prize {
    public enum Type{
        Boost,Clear,Confuse,Freeze
    }

    private Type type;
    private Coordinate coordinate;

    public Prize(Type type, Coordinate coordinate) {
        this.type = type;
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
