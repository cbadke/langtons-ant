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

    let Step world =
        let newDirection =
            if isBlack(world, world.Ant) then
                turnLeft world.Direction
            else
                turnRight world.Direction
        let newX =
            match newDirection with
                | Direction.Left -> world.Ant.x - 1
                | Direction.Right -> world.Ant.x + 1
                | _ -> world.Ant.x
        let newY =
            match newDirection with
                | Direction.Up -> world.Ant.y - 1
                | Direction.Down -> world.Ant.y + 1
                | _ -> world.Ant.y
        let newAnt = Coord(newX, newY)
        let newPath = 
            if isBlack(world, newAnt) then 
                List.filter (fun (x: Coord) -> x.x <> newAnt.x || x.y <> newAnt.y) world.Path
            else
                newAnt :: world.Path
        World(newAnt, newPath, newDirection)
