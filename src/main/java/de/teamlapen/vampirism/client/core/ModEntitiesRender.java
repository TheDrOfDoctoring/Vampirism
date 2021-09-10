package de.teamlapen.vampirism.client.core;

import de.teamlapen.lib.lib.client.render.RenderAreaParticleCloud;
import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.client.model.*;
import de.teamlapen.vampirism.client.model.armor.*;
import de.teamlapen.vampirism.client.render.entities.*;
import de.teamlapen.vampirism.client.render.layers.VampireEntityLayer;
import de.teamlapen.vampirism.client.render.layers.VampirePlayerHeadLayer;
import de.teamlapen.vampirism.client.render.layers.WingsLayer;
import de.teamlapen.vampirism.core.ModEntities;
import de.teamlapen.vampirism.player.vampire.VampirePlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Handles entity render registration
 */
@OnlyIn(Dist.CLIENT)
public class ModEntitiesRender {
    private final static Logger LOGGER = LogManager.getLogger();

    public static ModelLayerLocation HUNTER = new ModelLayerLocation(new ResourceLocation("vampirism:hunter"), "main");
    public static ModelLayerLocation HUNTER_SLIM = new ModelLayerLocation(new ResourceLocation("vampirism:slim_hunter"), "main");
    public static ModelLayerLocation COFFIN = new ModelLayerLocation(new ResourceLocation("vampirism:coffin"), "main");
    public static ModelLayerLocation WING = new ModelLayerLocation(new ResourceLocation("vampirism:wing"), "main");
    public static ModelLayerLocation BARON = new ModelLayerLocation(new ResourceLocation("vampirism:baron"),"main");
    public static ModelLayerLocation BARONESS = new ModelLayerLocation(new ResourceLocation("vampirism:baroness"),"main");
    public static ModelLayerLocation BARON_ATTIRE = new ModelLayerLocation(new ResourceLocation("vampirism:baron"), "attire");
    public static ModelLayerLocation BARONESS_ATTIRE = new ModelLayerLocation(new ResourceLocation("vampirism:baroness"), "attire");
    public static ModelLayerLocation CLOAK = new ModelLayerLocation(new ResourceLocation("vampirism:cloak"), "main");
    public static ModelLayerLocation CLOTHING_BOOTS = new ModelLayerLocation(new ResourceLocation("vampirism:clothing"),"boots");
    public static ModelLayerLocation CLOTHING_CROWN = new ModelLayerLocation(new ResourceLocation("vampirism:clothing"),"crown");
    public static ModelLayerLocation CLOTHING_PANTS = new ModelLayerLocation(new ResourceLocation("vampirism:clothing"),"pants");
    public static ModelLayerLocation CLOTHING_HAT = new ModelLayerLocation(new ResourceLocation("vampirism:clothing"),"hat");
    public static ModelLayerLocation HUNTER_HAT0 = new ModelLayerLocation(new ResourceLocation("vampirism:hunter_hat0"),"main");
    public static ModelLayerLocation HUNTER_HAT1 = new ModelLayerLocation(new ResourceLocation("vampirism:hunter_hat1"),"main");
    public static ModelLayerLocation HUNTER_EQUIPMENT = new ModelLayerLocation(new ResourceLocation("vampirism:hunter_equipment"),"main");
    public static ModelLayerLocation VILLAGER_WITH_ARMS = new ModelLayerLocation(new ResourceLocation("vampirism:villager_with_arms"),"main");
    public static ModelLayerLocation GENERIC_BIPED = new ModelLayerLocation(new ResourceLocation("vampirism:generic_biped"),"main");
    public static ModelLayerLocation GENERIC_BIPED_SLIM = new ModelLayerLocation(new ResourceLocation("vampirism:generic_biped"),"main");
    public static ModelLayerLocation GENERIC_BIPED_ARMOR_OUTER = new ModelLayerLocation(new ResourceLocation("vampirism:generic_biped"),"outer_armor");
    public static ModelLayerLocation GENERIC_BIPED_ARMOR_INNER = new ModelLayerLocation(new ResourceLocation("vampirism:generic_biped"),"inner_armor");
    public static ModelLayerLocation TASK_MASTER = new ModelLayerLocation(new ResourceLocation("vampirism:task_master"),"main");



    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ModEntities.blinding_bat, BatRenderer::new);
        event.registerEntityRenderer(ModEntities.converted_creature_imob,  ConvertedCreatureRenderer::new);
        event.registerEntityRenderer(ModEntities.converted_creature,  (ConvertedCreatureRenderer::new));
        event.registerEntityRenderer(ModEntities.converted_horse, renderingManager -> {
            HorseRenderer renderer = new HorseRenderer(renderingManager);
            renderer.addLayer(new VampireEntityLayer<>(renderer, new ResourceLocation(REFERENCE.MODID, "textures/entity/vanilla/horse_overlay.png"), false));
            return renderer;
        });
        event.registerEntityRenderer(ModEntities.converted_donkey,  (context)->new ConvertedChestedHorseRenderer<>(context, ModelLayers.DONKEY));
        event.registerEntityRenderer(ModEntities.converted_mule,  (context -> new ConvertedChestedHorseRenderer<>(context, ModelLayers.MULE)));
        event.registerEntityRenderer(ModEntities.converted_sheep,  (ConvertedCreatureRenderer::new));
        event.registerEntityRenderer(ModEntities.converted_cow,  (ConvertedCreatureRenderer::new));
        event.registerEntityRenderer(ModEntities.hunter,  (BasicHunterRenderer::new));
        event.registerEntityRenderer(ModEntities.hunter_imob,  (BasicHunterRenderer::new));
        event.registerEntityRenderer(ModEntities.vampire,  (BasicVampireRenderer::new));
        event.registerEntityRenderer(ModEntities.vampire_imob,  (BasicVampireRenderer::new));
        event.registerEntityRenderer(ModEntities.hunter_trainer, e -> new HunterTrainerRenderer(e, true));
        event.registerEntityRenderer(ModEntities.vampire_baron,  (VampireBaronRenderer::new));
        event.registerEntityRenderer(ModEntities.advanced_hunter,  (AdvancedHunterRenderer::new));
        event.registerEntityRenderer(ModEntities.advanced_hunter_imob,  (AdvancedHunterRenderer::new));
        event.registerEntityRenderer(ModEntities.advanced_vampire,  (AdvancedVampireRenderer::new));
        event.registerEntityRenderer(ModEntities.advanced_vampire_imob,  (AdvancedVampireRenderer::new));
        event.registerEntityRenderer(ModEntities.villager_converted,  (ConvertedVillagerRenderer::new));
        event.registerEntityRenderer(ModEntities.villager_angry, HunterVillagerRenderer::new);
        event.registerEntityRenderer(ModEntities.crossbow_arrow,  (CrossbowArrowRenderer::new));
        event.registerEntityRenderer(ModEntities.particle_cloud,  (RenderAreaParticleCloud::new));
        event.registerEntityRenderer(ModEntities.throwable_item, ThrowableItemRenderer::new);
        event.registerEntityRenderer(ModEntities.dark_blood_projectile,  (DarkBloodProjectileRenderer::new));
        event.registerEntityRenderer(ModEntities.soul_orb, SoulOrbRenderer::new);
        event.registerEntityRenderer(ModEntities.hunter_trainer_dummy, e -> new HunterTrainerRenderer(e, false));
        event.registerEntityRenderer(ModEntities.dummy_creature,  (DummyRenderer::new));
        event.registerEntityRenderer(ModEntities.vampire_minion,  (VampireMinionRenderer::new));
        event.registerEntityRenderer(ModEntities.hunter_minion,  (HunterMinionRenderer::new));
        event.registerEntityRenderer(ModEntities.task_master_vampire,  (VampireTaskMasterRenderer::new));
        event.registerEntityRenderer(ModEntities.task_master_hunter,  (HunterTaskMasterRenderer::new));
    }

    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(HUNTER, BasicHunterModel::createBodyLayer);
        event.registerLayerDefinition(HUNTER_SLIM, BasicHunterModel::createSlimBodyLayer);
        event.registerLayerDefinition(COFFIN, CoffinModel::createLayer);
        event.registerLayerDefinition(WING, WingModel::createLayer);
        event.registerLayerDefinition(BARON, BaronModel::createLayer);
        event.registerLayerDefinition(BARONESS, BaronessModel::createLayer);
        event.registerLayerDefinition(BARON_ATTIRE, BaronAttireModel::createLayer);
        event.registerLayerDefinition(BARONESS_ATTIRE, BaronessAttireModel::createLayer);
        event.registerLayerDefinition(CLOAK, CloakModel::createLayer);
        event.registerLayerDefinition(CLOTHING_BOOTS, ClothingBootsModel::createLayer);
        event.registerLayerDefinition(CLOTHING_CROWN, ClothingCrownModel::createLayer);
        event.registerLayerDefinition(CLOTHING_PANTS, ClothingPantsModel::createLayer);
        event.registerLayerDefinition(CLOTHING_HAT, VampireHatModel::createLayer);
        event.registerLayerDefinition(HUNTER_HAT0, ()->HunterHatModel.createLayer(0,0));
        event.registerLayerDefinition(HUNTER_HAT1, ()->HunterHatModel.createLayer(0,1));
        event.registerLayerDefinition(HUNTER_EQUIPMENT, HunterEquipmentModel::createLayer);
        event.registerLayerDefinition(VILLAGER_WITH_ARMS, () -> VillagerWithArmsModel.createLayer(0));
        event.registerLayerDefinition(GENERIC_BIPED, () -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,false),64,64));
        event.registerLayerDefinition(GENERIC_BIPED_SLIM, () -> LayerDefinition.create(PlayerModel.createMesh(CubeDeformation.NONE,true),64,64));
        event.registerLayerDefinition(GENERIC_BIPED_ARMOR_INNER, () -> LayerDefinition.create(HumanoidModel.createMesh(LayerDefinitions.INNER_ARMOR_DEFORMATION, 0.0F), 64, 32));
        event.registerLayerDefinition(GENERIC_BIPED_ARMOR_OUTER, () -> LayerDefinition.create(HumanoidModel.createMesh(LayerDefinitions.OUTER_ARMOR_DEFORMATION, 0.0F), 64, 32));
        event.registerLayerDefinition(TASK_MASTER, ()-> LayerDefinition.create(VillagerModel.createBodyModel(), 64, 64));
    }

    public static void onAddLayers(EntityRenderersEvent.AddLayers event){

        for (String s : event.getSkins()) {
            LivingEntityRenderer<? extends Player, ? extends EntityModel<? extends Player>> renderPlayer = event.getSkin(s);
            if(renderPlayer != null && renderPlayer.getModel() instanceof HumanoidModel){
                (renderPlayer).addLayer(new VampirePlayerHeadLayer(renderPlayer));
                renderPlayer.addLayer(new WingsLayer(renderPlayer, Minecraft.getInstance().getEntityModels(), player -> VampirePlayer.getOpt((Player) player).map(VampirePlayer::getWingCounter).filter(i -> i > 0).isPresent(), (e, m) -> ((HumanoidModel)m).body));

            }
        }
        for (Map.Entry<EntityType<? extends PathfinderMob>, ResourceLocation> entry : VampirismAPI.entityRegistry().getConvertibleOverlay().entrySet()) {
            EntityType<? extends PathfinderMob> type = entry.getKey();
            LivingEntityRenderer<?,?> render = event.getRenderer(type);
            if (render == null) {
                LOGGER.error("Did not find renderer for {}", type);
                continue;
            }
            render.addLayer(new VampireEntityLayer(render, entry.getValue(), true));
        }
    }

}
