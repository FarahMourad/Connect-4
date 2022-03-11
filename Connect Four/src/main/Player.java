package main;

public class Player {
    private Role role;
    private int score;
    private Piece piece;
    Player(Role role){
        this.role = role;
        if (role == Role.PLAYER)
            piece = Piece.RED;
        else
            piece = Piece.YELLOW;
        score = 0;
    }
    public int incrementScore(){
        score++;
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }
    public Role getRole() {
        return role;
    }
    public Piece getPiece() {
        return piece;
    }
}
