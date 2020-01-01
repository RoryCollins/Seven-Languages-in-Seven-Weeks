package Board

class BoardForPlayerO(board: Array[Char]) extends Board(board){
  override val player: Char = 'O'

  override def getNextPlayerBoard(): Board = new BoardForPlayerX(this.toString().toCharArray)
}
