package net.fabricmc.cryptic.mixin;

import net.fabricmc.cryptic.events.types.KeyEvent;
import net.fabricmc.cryptic.events.types.MouseEvent;
import net.fabricmc.cryptic.events.types.TickEvent;
import net.fabricmc.cryptic.utils.KeybindUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2d;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.fabricmc.cryptic.Cryptic.EventBus;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow private static Minecraft instance;

    @Inject(method = "tick()V",at = @At("HEAD"))
    public void preTick(CallbackInfo ci) {
        EventBus.post(new TickEvent.Pre());
    }

    @Inject(method = "tick()V", at = @At("TAIL"))
    public void postTick(CallbackInfo ci) {
        EventBus.post(new TickEvent.Post());
    }

    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/i;method_839(IZ)V", ordinal = 0, shift = At.Shift.BEFORE))
    public void mouse(CallbackInfo ci) {
        int mouseX;
        int mouseY;
        if (instance.currentScreen != null) {
            mouseX = Mouse.getEventX() * instance.currentScreen.width / instance.width;
            mouseY = instance.currentScreen.height - Mouse.getEventY() * instance.currentScreen.height / instance.height - 1;
        } else {
            mouseX = Mouse.getEventX();
            mouseY = Mouse.getEventY() - 1;
        }
        if (Mouse.getEventButtonState()) EventBus.post(MouseEvent.Click.get(mouseX, mouseY, Mouse.getEventButton()));
        else EventBus.post(MouseEvent.Release.get(mouseX, mouseY, Mouse.getEventButton()));
    }

    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/i;method_839(IZ)V", ordinal = 1, shift = At.Shift.BEFORE))
    public void keyboard(CallbackInfo ci) {
        if (Keyboard.isKeyDown(Keyboard.getEventKey()) && !KeybindUtils.pressed.contains(Keyboard.getEventKey())) {
            System.out.println(Keyboard.getKeyName(Keyboard.getEventKey()));
            KeybindUtils.pressed.add(Keyboard.getEventKey());
            EventBus.post(KeyEvent.Press.get(Keyboard.getEventCharacter(), Keyboard.getEventKey()));
        } else {
            KeybindUtils.pressed.remove(Keyboard.getEventKey());
            EventBus.post(KeyEvent.Release.get(Keyboard.getEventCharacter(), Keyboard.getEventKey()));
        }
    }

    @Inject(method = "tick()V", at = @At(value = "TAIL"))
    public void dataTypes(CallbackInfo ci) {
        Vec2i.threadPool().tick();
        Vec2d.threadPool().tick();
    }
}
