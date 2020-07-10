-module(count).
-export([count_to/1]).

count(X, X) -> io:format("~p~n", [X]);
count(X, Y) -> io:format("~p~n", [X]), 
	       count(X+1, Y).

count_to(X) -> count(1, X).



