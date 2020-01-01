package Board

class Row(a: Char, b: Char, c: Char) {
  def isWon() = a != '_' && a == b && b == c
}
