package net.fabricmc.cryptic.mixin;

import net.fabricmc.cryptic.Cryptic;
import net.fabricmc.cryptic.gui.screens.ClickGui;
import net.fabricmc.cryptic.gui.screens.ElementPicker;
import net.fabricmc.cryptic.gui.screens.HudEditor;
import net.fabricmc.cryptic.gui.Element;
import net.fabricmc.cryptic.gui.screens.SettingsScreen;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.InGameHud;
import org.lwjgl.input.Mouse;
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
        if (!(field_1166.currentScreen instanceof ClickGui || field_1166.currentScreen instanceof SettingsScreen || field_1166.currentScreen instanceof ElementPicker) && Cryptic.loaded) {
            for (Element element : HudEditor.INSTANCE.elements) {
                element.init();
                if (HudEditor.INSTANCE.drawBackground) element.drawBackground(Vec2i.create(0x000000, 0x634b4b));
                element.render();
            }
        }
    }
    //Minecraft.grabMouse();

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        HudEditor.INSTANCE.elements.forEach(Element::tick);
    }
}
