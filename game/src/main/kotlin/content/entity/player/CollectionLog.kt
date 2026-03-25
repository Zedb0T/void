package content.entity.player

import content.social.friend.friend
import world.gregs.voidps.engine.Script
import world.gregs.voidps.engine.client.message
import world.gregs.voidps.engine.entity.character.player.Player
import world.gregs.voidps.engine.entity.character.player.Players
import world.gregs.voidps.engine.entity.character.player.name

class CollectionLog : Script {

    init {
        itemAdded(inventory = "inventory") {
            onItemObtained(this, it.item.id)
        }

        itemAdded(inventory = "bank") {
            onItemObtained(this, it.item.id)
        }

        itemAdded(inventory = "worn_equipment") {
            onItemObtained(this, it.item.id)
        }
    }

    companion object {
        fun onItemObtained(player: Player, itemId: String) {
            if (itemId.isBlank()) return
            val obtained: MutableSet<String> = player.getOrPut("collection_log") { mutableSetOf() }
            if (!obtained.add(itemId)) return
            val playerName = player.name
            for (other in Players) {
                if (other.friend(player)) {
                    other.message("$playerName has obtained a new item: $itemId")
                }
            }
        }
    }
}
