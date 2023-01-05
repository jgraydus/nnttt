# Description

This is a project I did for a neural networks class in the fall of 2015. The
project implements a configurable neural network and applies it to learning
how to play tic tac toe (a.k.a. naughts and crosses).

The following output demonstrates strong evidence that this works.
RandomPlayer, as the name suggests, chooses moves randomly. LearningPlayer
chooses moves by feeding the game state into a neural network which has its
weights updated based on the outcome of each game. Interestingly, it seems
that player 1 has an advantage in tic tac toe. LearningPlayer really benefits
from this advantage in the player 1 position, but also manages to overcome the
advantage frequently in the player 2 position.

```
RandomPlayer (player 1) vs RandomPlayer (player 2)
player 1:    58407
player 2:    28940
draws:       12653
total games: 100000

LearningPlayer (player 1) vs RandomPlayer (player 2)
player 1:    82846
player 2:    9324
draws:       7830
total games: 100000

RandomPlayer (player 1) vs LearningPlayer (player 2)
player 1:    31654
player 2:    59195
draws:       9151
total games: 100000
```

It should be noted that an optimal player would never lose a game of
tic tac toe as either player 1 or player 2.