package Board

class GameCompleteBoard(board: Array[Char], currentPlayer: Char) extends Board(board){
  override val player: Char = currentPlayer;

  override def getNextPlayerBoard(): Board = this

  override def play(location: Int): Board = this
}
