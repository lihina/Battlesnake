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
        if (canMoveTo(moveParams.you, moveParams.board.snakes, moveParams.board, getLeftOfLoc(moveParams.you.head))) {
            return MoveResponse("left", "Moving left!!")
        }

        if (canMoveTo(moveParams.you, moveParams.board.snakes, moveParams.board, getRightOfLoc(moveParams.you.head))) {
            return MoveResponse("right", "Moving right!!")
        }

        if (canMoveTo(moveParams.you, moveParams.board.snakes, moveParams.board, getDownOfLoc(moveParams.you.head))) {
            return MoveResponse("down", "Moving down!!")
        }

        return MoveResponse("up", "Moving up!!")
    }

    fun canMoveTo(meSnake: BattleSnake, otherSnakes: List<BattleSnake>, board: Board, dest: Coordinate): Boolean {
        return !isOtherSnakeHere(otherSnakes, dest) && !isBorderHere(board, dest) || !isMeHere(meSnake, dest)
    }

    fun isOtherSnakeHere(otherSnakes: List<BattleSnake>, here: Coordinate): Boolean {
        return otherSnakes.asSequence()
                .map { isHere(it, here) }
                .first() != null
    }

    fun isMeHere(battleSnake: BattleSnake, here: Coordinate): Boolean {
        return isHere(battleSnake, here)
    }

    fun isBorderHere(board: Board, here: Coordinate): Boolean {
        return board.height == here.y || board.width == here.x
    }

    fun isHere(battleSnake: BattleSnake, here: Coordinate): Boolean {
        return battleSnake.body.asSequence()
                .map{isSameLoc(it, here)}
                .first() != null

    }

    fun isSameLoc(loc1: Coordinate, loc2: Coordinate): Boolean{
        return loc1.x == loc2.x && loc1.y == loc2.y
    }

    fun getLeftOfLoc(loc: Coordinate): Coordinate {
        return Coordinate(loc.x-1, loc.y)
    }

    fun getRightOfLoc(loc: Coordinate): Coordinate {
        return Coordinate(loc.x+1, loc.y)
    }

    fun getUpOfLoc(loc: Coordinate): Coordinate {
        return Coordinate(loc.x, loc.y + 1)
    }

    fun getDownOfLoc(loc: Coordinate): Coordinate {
        return Coordinate(loc.x, loc.y - 1)
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