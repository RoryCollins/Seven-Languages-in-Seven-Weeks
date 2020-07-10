-module(word_count).
-export([count/1]).

count([], Count)        -> Count;
count([32|Tail], Count) -> count(Tail, Count + 1);
count([_|Tail], Count)  -> count(Tail, Count).

count(X) -> count(X, 1).
