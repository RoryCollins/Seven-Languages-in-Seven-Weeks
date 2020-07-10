-module(either).
-export([print/1]).

print(success) -> "success";
print({error, Message}) -> io:format("error: ~p~n", [Message]).
