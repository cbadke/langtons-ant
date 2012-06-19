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
    member this.Direction = dir
    member this.Ant = ant
    member this.Path = path
    new() = World( Coord(), List<Coord>.Empty, Direction.Up )

