Swipe Uncle Bobs swing bindings and implenting Langton's ant.

Rules (from Wikipedia):

Squares on a plane are colored variously either black or white. We arbitrarily identify one square as the "ant". 
The ant can travel in any of the four cardinal directions at each step it takes. The ant moves according to the 
rules below:

At a white square, turn 90° right, flip the color of the square, move forward one unit
At a black square, turn 90° left, flip the color of the square, move forward one unit

These simple rules lead to surprisingly complex behavior: after an initial period of apparently chaotic behavior, 
that lasts for about 10,000 steps (in the simplest case), the ant starts building a recurrent "highway" pattern of 
104 steps that repeat indefinitely. All finite initial configurations tested eventually converge to the same 
repetitive pattern suggesting that the "highway" is an attractor of Langton's ant, but no one has been able to prove 
that this is true for all such initial configurations. It is only known that the ant's trajectory is always 
unbounded regardless of the initial configuration[3] - this is known as the Cohen-Kung theorem.

Langton's ant can also be described as a cellular automaton, where most of the grid is colored black or white, and 
the "ant" square has one of eight different colors assigned to encode the combination of black/white state and the 
current direction of motion of the ant.

![Ant Screen Shot](/ant-screen.png "")