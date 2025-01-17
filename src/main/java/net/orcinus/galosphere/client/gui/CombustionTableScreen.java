package net.orcinus.galosphere.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.orcinus.galosphere.Galosphere;

@Environment(EnvType.CLIENT)
public class CombustionTableScreen extends AbstractContainerScreen<CombustionTableMenu> {
    private static final ResourceLocation TEXTURE = Galosphere.id("textures/gui/container/combustion_table.png");

    public CombustionTableScreen(CombustionTableMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 201;
    }

    @Override
    public void render(GuiGraphics source, int mouseX, int mouseY, float delta) {
        this.renderBackground(source);
        super.render(source, mouseX, mouseY, delta);
        this.renderTooltip(source, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
}