package vn.loitp.app.a.game.osero.md.ai

import vn.loitp.app.a.game.osero.md.OseroGame
import vn.loitp.app.a.game.osero.md.Place
import vn.loitp.app.a.game.osero.md.Stone

/**
 * AIを使用しない場合
 */
class AINone : OseroAI {

    override fun computeNext(game: OseroGame, color: Stone): Place {
        throw IllegalAccessException()
    }

}