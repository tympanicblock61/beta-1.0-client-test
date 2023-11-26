package net.fabricmc.cryptic.gui.modules;

import net.fabricmc.cryptic.Cryptic;
import net.fabricmc.cryptic.gui.Module;

public class KillAura extends Module {
    public static KillAura INSTANCE = new KillAura();

    public KillAura() {
        super(Cryptic.translator.translate("module::killaura::name"), Cryptic.translator.translate("module::killaura::desc"));
    }

    @Override
    public void onActivate() {
        if (active) {
            System.out.println(Cryptic.translator.translate("module::killaura::name")+" activated");
        }
    }
}
