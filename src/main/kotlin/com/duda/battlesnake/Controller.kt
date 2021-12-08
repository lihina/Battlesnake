package com.duda.battlesnake

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
class Controller {

    @GetMapping("/")
    fun getBattleSnakeBasic(): GetBattleSnakeDTO {
        System.out.println("Get Basic!!!!!!!")
        return GetBattleSnakeDTO(head = Coordinate(1, 1), tail = Coordinate(1,2));
    }

    @PostMapping("/start")
    fun startGame(): Unit {
        System.out.println("Starting!!!!!!!")

    }

    @PostMapping("/move")
    fun move(moveParams: MoveParams): MoveResponse {
        System.out.println("moving!!!!!!!")
        return MoveResponse("up", "Moving uppppp")
    }

    data class GetBattleSnakeDTO(val apiversion: String="1", val author: String="lihi", val color: String="#888888", val head: Coordinate, val tail: Coordinate, val version: String="0.0.1-beta")
    data class MoveResponse(val move: String="up", val shout: String="Moving up!")
    data class RuleSet(val name: String, val version: String, val settings: Map<String, Object>)
    data class Coordinate(val x: Int, val y: Int)


    data class MoveParams(val game: Game, val turn: Int, val board: Board, val you: BattleSnake)
    data class Game(val id:String, val ruleSet: RuleSet, val timeout: Int, val source: String)
    data class Board(val height: Int, val width: Int, val foods: List<Coordinate>, val hazards: List<Coordinate>, val snakes: List<BattleSnake>)
    data class BattleSnake(val id: String, val name: String, val health: String, val body: List<Coordinate>, val latency: Int, val head: Coordinate, val length: Int, val shout: String, val squad: Int)


}