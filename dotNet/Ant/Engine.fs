namespace Ant
module Engine =

    let isBlack (world: World, coord: Coord) =
        List.exists (fun (x: Coord) -> x.x = coord.x && x.y = coord.y) world.Path

    let turnLeft direction =
        match direction with
            | Direction.Up -> Direction.Left
            | Direction.Left -> Direction.Down
            | Direction.Down -> Direction.Right
            | Direction.Right -> Direction.Up

    let turnRight direction = 
        match direction with
            | Direction.Up -> Direction.Right
            | Direction.Right -> Direction.Down
            | Direction.Down -> Direction.Left
            | Direction.Left -> Direction.Up

    let turnAnt world =
        let newDirection =
            if isBlack(world, world.Ant) then
                turnLeft world.Direction
            else
                turnRight world.Direction
        World(world.Ant, world.Path, newDirection)

    let moveAntForward (world: World) =
        let newX =
            match world.Direction with
                | Direction.Left -> world.Ant.x - 1
                | Direction.Right -> world.Ant.x + 1
                | _ -> world.Ant.x
        let newY =
            match world.Direction with
                | Direction.Up -> world.Ant.y - 1
                | Direction.Down -> world.Ant.y + 1
                | _ -> world.Ant.y
        World(Coord(newX, newY), world.Path, world.Direction)

    let toggleAntSquare world = 
        let newPath = 
            if isBlack(world, world.Ant) then 
                List.filter (fun (x: Coord) -> x.x <> world.Ant.x || x.y <> world.Ant.y) world.Path
            else
                world.Ant :: world.Path
        World(world.Ant, newPath, world.Direction)

    let Step (world: World) =
        world
            |> turnAnt
            |> toggleAntSquare
            |> moveAntForward
