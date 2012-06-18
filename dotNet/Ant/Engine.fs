namespace Ant

type Direction = 
    | Up
    | Down
    | Left
    | Right

type Coord(x: int,y: int) =
    member this.x = x
    member this.y = y
    new() = Coord(0, 0)

type World(ant: Coord, path: List<Coord>, dir: Direction) = 
    new() = World( Coord(), List<Coord>.Empty, Direction.Up )
    member this.direction = dir
    member this.ant = ant
    member this.path = path
    member this.Step() = World( Coord(this.ant.x+1, this.ant.y+1), this.path, this.direction)


