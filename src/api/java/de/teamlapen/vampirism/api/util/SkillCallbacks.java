package de.teamlapen.vampirism.api.util;

import com.mojang.datafixers.util.Either;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.factions.ISkillTree;
import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.api.entity.player.skills.DefaultSkill;
import de.teamlapen.vampirism.api.entity.player.skills.IActionSkill;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.registries.callback.AddCallback;
import net.neoforged.neoforge.registries.callback.ClearCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SkillCallbacks implements AddCallback<ISkill<?>>, ClearCallback<ISkill<?>> {


    private static final Map<IAction<?>, ISkill<?>> ACTION_TO_SKILL_MAP = new HashMap<>();
    private static final Map<IAction<?>, ISkill<?>> ACTION_TO_SKILL_MAP_READ_ONLY = Collections.unmodifiableMap(ACTION_TO_SKILL_MAP);

    @Override
    public void onAdd(@NotNull Registry<ISkill<?>> registry, int id, @NotNull ResourceKey<ISkill<?>> key, @NotNull ISkill<?> value) {
        if (value instanceof IActionSkill<?> actionSkill) {
            ACTION_TO_SKILL_MAP.put(actionSkill.action(), actionSkill);
        } else if (value instanceof DefaultSkill<?> defaultSkill){
            defaultSkill.getActions().forEach(action -> ACTION_TO_SKILL_MAP.put(action, new EmptyActionSkill<>(action)));
        }
    }

    @Override
    public void onClear(@NotNull Registry<ISkill<?>> registry, boolean full) {
        if (full) {
            ACTION_TO_SKILL_MAP.clear();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends IFactionPlayer<T>> Map<IAction<T>, ISkill<T>> getActionSkillMap() {
        return (Map<IAction<T>, ISkill<T>>) (Object) ACTION_TO_SKILL_MAP_READ_ONLY;
    }

    public record EmptyActionSkill<T extends IFactionPlayer<T>>(IAction<T> action) implements IActionSkill<T> {
        private static final TagKey<ISkillTree> key = TagKey.create(VampirismRegistries.Keys.SKILL_TREE, VResourceLocation.mod("empty"));
        @Override
            public @Nullable Component getDescription() {
                return null;
            }

            @Override
            public @NotNull Optional<IPlayableFaction<?>> getFaction() {
                return this.action.getFaction();
            }

            @Override
            public String getTranslationKey() {
                return this.action.getTranslationKey();
            }

            @Override
            public void onDisable(T player) {

            }

            @Override
            public void onEnable(T player) {

            }

            @Override
            public Either<ResourceKey<ISkillTree>, TagKey<ISkillTree>> allowedSkillTrees() {
                return Either.right(key);
            }
        }
}
