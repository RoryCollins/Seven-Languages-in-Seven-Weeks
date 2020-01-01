import org.scalatest.FunSuite

class TheOneWhereNobodyWins extends FunSuite {
  test("No Winner"){
    val ticTacToe = new TicTacToe()
    ticTacToe.play(0)
    ticTacToe.play(4)
    ticTacToe.play(2)
    ticTacToe.play(1)
    ticTacToe.play(7)
    ticTacToe.play(3)
    ticTacToe.play(6)
    ticTacToe.play(8)
    ticTacToe.play(5)
    assert(ticTacToe.getWinner()==="No Winner")
  }
}
