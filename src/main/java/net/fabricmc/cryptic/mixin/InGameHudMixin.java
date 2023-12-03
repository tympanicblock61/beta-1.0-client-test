package net.fabricmc.cryptic.mixin;

import net.fabricmc.cryptic.gui.screens.ClickGui;
import net.fabricmc.cryptic.gui.screens.HudEditor;
import net.fabricmc.cryptic.gui.Element;
import net.fabricmc.cryptic.gui.screens.SettingsScreen;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow
    @Final
    private Minecraft field_1166;

    @Inject(method = "method_979", at = @At("TAIL"))
    public void method_979(float f2, boolean bl, int i2, int j2, CallbackInfo ci) {
        float tickDelta = f2;
        boolean hasScreenOpen = bl;
        int mouseX = i2;
        int mouseY = j2;
        if (!(field_1166.currentScreen == ClickGui.INSTANCE) && !(field_1166.currentScreen == SettingsScreen.INSTANCE)) {
            for (Element element : HudEditor.INSTANCE.elements) {
                element.init(HudEditor.INSTANCE.render);
                if (HudEditor.INSTANCE.drawBackground) HudEditor.INSTANCE.render.drawBackground(element.getPos(), element.getSize(), Vec2i.create(0x000000, 0x634b4b), HudEditor.INSTANCE.borderSize);
                element.render(HudEditor.INSTANCE.render);
            }
        }
    }
    //Minecraft.grabMouse();

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        for (Element element : HudEditor.INSTANCE.elements) element.tick(HudEditor.INSTANCE.render);
    }
}
