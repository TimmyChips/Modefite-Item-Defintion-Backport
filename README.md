## Modefite - Item Definition Backport
***

Modefite is a mod that backports the new Items Model Definition system (also called the "Items" system) that was introduced in 1.21.4 to older versions of Minecraft.

With Modefite, you can use your favorite resource packs designed for newer versions for their extra customizability.
<br>

### FAQ
***
* Main missing features not implemented yet include the: `special`, `bundle/selected_item`, and the `model tint source` types.
* You can see the full list of differences from the official 1.21.4+ system [here](https://github.com/TimmyChips/Modefite-Item-Defintion-Backport/wiki#differences-from-1214).
* Some resource packs use core shaders that may be incompatible with your version of the game! You may need to delete the core shaders folder for a pack if you get the error, "*Resource reload failed*" when enabling.

##### For Resource Pack Makers

* Modefite adds some new properties, fields, and a new resource folder that can be used. More info available [here](https://github.com/TimmyChips/Modefite-Item-Defintion-Backport/wiki#custom-additions).
* Use any custom properties/fields in `assets/<minecraft_or_modid>/modefite_items_override` in your resource pack as vanilla Minecraft 1.21.4+ will render a missing model on any unknown properties/fields.
* If you haven't seen it already, the documentation on the items model definition system can be seen on the [Minecraft wiki](https://minecraft.wiki/w/Items_model_definition).
* Any unknown properties or fields will be ignored and won't cause any missing models with Modefite.
* See the `latest.log` file for any potential errors you run into!
  <br><br><br>

* Modefite is not a one-to-one version of the vanilla items model definition system and hopefully there aren't any breaking changes!
* Please report any issues, bugs, or feature requests on the [GitHub issue tracker!](https://github.com/TimmyChips/Modefite-Item-Defintion-Backport/issues)
  <br><br>

### Why Modefite?
***
Before 1.21.4 released, I created [Pommel](https://modrinth.com/mod/pommel-held-item-models) as a way to have my resource pack have separate held models in the hand compared to the inventory. 1.21.4 and 1.21.5 introduced the new items system that gave resource pack creators more freedom and customizability, which essentially made the main function of the mod, obsolete. Though it does have a couple of features still missing in the latest release of Minecraft.

To my surprise, Pommel ended up being used by many resource pack creators afterward to backport their resource pack to 1.21.1 and below. Together with mods like, [CIT Resewn](https://modrinth.com/mod/cit-resewn), [Eating Animation](https://modrinth.com/mod/eating-animation), and [EMF](https://modrinth.com/mod/entity-model-features)/[ETF](https://modrinth.com/mod/entitytexturefeatures), several of the features could be refactored to work in these versions. Though it was cumbersome and difficult, and many features were missing. I updated Pommel to add some of these missing features from 1.21.4, but a random question in a conversation with someone was, "What if you took a look at the 'items' system [and made that a mod] for resource packs?"

And thus, Modefite was born.
<br><br>

### Future Plans
***
The main plan is to port the mod to 1.20.1. And possibly as well as for 1.21.2 and 1.21.3. Other main plans are bug fixing and making sure items model definition resource packs work with Modefite.

Another goal is to add the `special` type to Modefite; although the priority is lower since I don't believe many resource pack makers use this feature much. The types, `oversized_in_gui` and `swap_animation_scale` are in similar positions, as well as `model tint source` types.

Type, `budle/selected_item` will most likely be added to the 1.21.2/1.21.3 version of Modefite, as the ability to do this with bundles was introduced in those versions.

Eventually I'd like to perhaps add some more custom properties for resource pack creators, and port Modefite and these properties to newer versions so creators can use them.