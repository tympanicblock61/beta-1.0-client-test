package net.fabricmc.cryptic.mixin;

import net.fabricmc.cryptic.Cryptic;
import net.fabricmc.cryptic.events.types.KeyEventListener;
import net.fabricmc.cryptic.events.types.MouseEventListener;
import net.fabricmc.cryptic.events.types.TickEventListener;
import net.fabricmc.cryptic.gui.screens.ClickGui;
import net.fabricmc.cryptic.gui.screens.ElementPicker;
import net.fabricmc.cryptic.gui.screens.HudEditor;
import net.fabricmc.cryptic.gui.screens.SettingsScreen;
import net.fabricmc.cryptic.utils.KeybindUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2d;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static net.fabricmc.cryptic.Cryptic.EventBus;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow private static Minecraft instance;
    @Unique
    Vec2i[] prevPos = new Vec2i[]{Vec2i.create(0,0), Vec2i.create(0,0), Vec2i.create(0,0)};

    @Shadow
    protected MinecraftApplet applet;

    @Inject(method = "tick()V",at = @At("HEAD"))
    public void preTick(CallbackInfo ci) {
        EventBus.post(TickEventListener.Event.ID, new TickEventListener.Event(TickEventListener.Type.Pre));
    }

    @Inject(method = "tick()V", at = @At("TAIL"))
    public void postTick(CallbackInfo ci) {
        EventBus.post(TickEventListener.Event.ID, new TickEventListener.Event(TickEventListener.Type.Post));
    }

    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/i;method_839(IZ)V", ordinal = 0, shift = At.Shift.BEFORE))
    public void mouse(CallbackInfo ci) {
        int mouseX, mouseY, button = Mouse.getEventButton();
        if (button == -1) return;
        boolean newState = Mouse.isButtonDown(button);
        if (instance.currentScreen != null) {
            mouseX = Mouse.getEventX() * instance.currentScreen.width / instance.width;
            mouseY = instance.currentScreen.height - Mouse.getEventY() * instance.currentScreen.height / instance.height - 1;
        } else {
            mouseX = Mouse.getEventX();
            mouseY = Mouse.getEventY() - 1;
        }

        if (newState) {
            prevPos[button] = Vec2i.create(mouseX, mouseY);
            EventBus.post(MouseEventListener.Event.ID, new MouseEventListener.Event(Vec2i.create(mouseX, mouseY), Vec2i.create(0,0), button, MouseEventListener.Action.Click));
        } else {
            EventBus.post(MouseEventListener.Event.ID, new MouseEventListener.Event(Vec2i.create(mouseX, mouseY), Vec2i.create(0,0), button, MouseEventListener.Action.Release));
            Vec2i pos = prevPos[button];
            if (!pos.equals(Vec2i.create(mouseX, mouseY))) EventBus.post(MouseEventListener.Event.ID, new MouseEventListener.Event(Vec2i.create(pos.x, pos.y), Vec2i.create(mouseX,mouseY), button, MouseEventListener.Action.Drag));
        }
    }

    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/i;method_839(IZ)V", ordinal = 1, shift = At.Shift.BEFORE))
    public void keyboard(CallbackInfo ci) {
        if (Keyboard.isKeyDown(Keyboard.getEventKey()) && !KeybindUtils.pressed.contains(KeybindUtils.Key.getByCode(Keyboard.getEventKey()))) {
            KeybindUtils.pressed.add(KeybindUtils.Key.getByCode(Keyboard.getEventKey()));
            EventBus.post(KeyEventListener.Event.ID, new KeyEventListener.Event(Keyboard.getEventCharacter(), Keyboard.getEventKey(), KeyEventListener.Action.Press));
        } else {
            KeybindUtils.pressed.remove(KeybindUtils.Key.getByCode(Keyboard.getEventKey()));
            EventBus.post(KeyEventListener.Event.ID, new KeyEventListener.Event(Keyboard.getEventCharacter(), Keyboard.getEventKey(), KeyEventListener.Action.Release));
        }
    }

    @Inject(method = "tick()V", at = @At(value = "TAIL"))
    public void dataTypes(CallbackInfo ci) {
        Vec2i.threadPool().tick();
        Vec2d.threadPool().tick();
    }
}
