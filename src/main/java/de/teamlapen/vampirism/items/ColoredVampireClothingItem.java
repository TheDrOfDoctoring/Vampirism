package de.teamlapen.vampirism.items;

import de.teamlapen.vampirism.client.extensions.ItemExtensions;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;


public class ColoredVampireClothingItem extends VampireClothingItem {
    private final String baseName;
    private final EnumClothingColor color;
    private final EnumModel model;

    public ColoredVampireClothingItem(@NotNull ArmorItem.Type type, EnumModel model, String baseRegName, EnumClothingColor color) {
        super(type);
        this.baseName = baseRegName;
        this.color = color;
        this.model = model;
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(ItemExtensions.VAMPIRE_CLOAK);
    }

    public enum EnumModel {
        CLOAK
    }

    public enum EnumClothingColor implements StringRepresentable {
        REDBLACK("red_black"), BLACKRED("black_red"), BLACKWHITE("black_white"), WHITEBLACK(
                "white_black"), BLACKBLUE("black_blue");


        private final String name;

        EnumClothingColor(String nameIn) {
            this.name = nameIn;
        }

        public @NotNull String getName() {
            return getSerializedName();
        }

        @NotNull
        @Override
        public String getSerializedName() {
            return this.name;
        }


    }
}
