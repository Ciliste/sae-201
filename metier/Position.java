package metier;

public record Position(int x, int y) {

    public static Position copier(Position position) {
        
        return new Position(position.x, position.y);
    }
}