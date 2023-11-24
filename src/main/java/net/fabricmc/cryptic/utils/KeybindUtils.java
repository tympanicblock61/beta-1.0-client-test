package net.fabricmc.cryptic.utils;

import java.util.HashSet;
import java.util.Set;

public class KeybindUtils {
    public static final Set<Key> pressed = new HashSet<>();
    public enum Key {
        None(0x00),
        Escape(0x01),
        Minus(0x0C),
        Equal(0x0D),
        Backspace(0x0E),
        LeftBracket(0x1A),
        RightBracket(0x1B),
        Enter(0x1C),
        LeftControl(0x1D),
        RightControl(0x9D),
        Semicolon(0x27),
        Apostrophe(0x28),
        AccentGrave(0x29),
        LeftShift(0x2A),
        RightShift(0x36),
        BackSlash(0x2B),
        ForwardSlash(0x35),
        Comma(0x33),
        Period(0x34),
        Asterisk(0x37),
        LeftAlt(0x38),
        RightAlt(0xB8),
        Capital(0x3A),
        Space(0x39),
        One(0x02),
        Two(0x03),
        Three(0x04),
        Four(0x05),
        Five(0x06),
        Six(0x07),
        Seven(0x08),
        Eight(0x09),
        Nine(0x0A),
        Zero(0x0B),
        TAB(0x0F),
        A(0x1E),
        B(0x30),
        C(0x2E),
        D(0x20),
        E(0x12),
        F(0x21),
        G(0x22),
        H(0x23),
        I(0x17),
        J(0x24),
        K(0x25),
        L(0x26),
        M(0x32),
        N(0x31),
        O(0x18),
        P(0x19),
        Q(0x10),
        R(0x13),
        S(0x1F),
        T(0x14),
        U(0x16),
        V(0x2F),
        W(0x11),
        X(0x2D),
        Y(0x15),
        Z(0x2C),
        F1(0x3B),
        F2(0x3C),
        F3(0x3D),
        F4(0x3E),
        F5(0x3F),
        F6(0x40),
        F7(0x41),
        F8(0x42),
        F9(0x43),
        F10(0x44),
        F11(0x57),
        F12(0x58),
        F13(0x64),
        F14(0x65),
        F15(0x66),
        F16(0x67),
        F17(0x68),
        F18(0x69),
        F19(0x71),
        Numpad0(0x52),
        Numpad1(0x4F),
        Numpad2(0x50),
        Numpad3(0x51),
        Numpad4(0x4B),
        Numpad5(0x4C),
        Numpad6(0x4D),
        Numpad7(0x47),
        Numpad8(0x48),
        Numpad9(0x49),
        Subtract(0x4A),
        Add(0x4E),
        Decimal(0x53),
        NumLock(0x45),
        ScrollLock(0x46),
        Kana(0x70),
        Convert(0x79),
        NoConvert(0x7B),
        Yen(0x7D),
        NumpadEquals(0x8D),
        CircumFlex(0x90),
        At(0x91),
        Colon(0x92),
        Underline(0x93),
        Kanji(0x94),
        Stop(0x95),
        Ax(0x96),
        UnLabeled(0x97),
        NumpadDenter(0x9C),
        Section(0xA7),
        NumpadComma(0xB3),
        Divide(0xB5),

        SystemRequest(0xB7),
        Function( 0xC4),
        Pause(0xC5),
        keypadHome(0xC7),
        UpArrow(0xC8),
        LeftArrow(0xCB),
        RightArrow(0xCD),
        DownArrow(0xD0),
        PageUp(0xC9),
        End(0xCF),
        PageDown(0xD1),
        Insert(0xD2),
        Delete(0xD3),
        Clear(0xDA),
        LeftWindowsOption(0xDB),
        RightWindowsOption(0xDC),
        AppMenu(0xDD),
        Power(0xDE),
        Sleep(0xDF);

        public final String name;
        public final int keyCode;

        Key(int keyCode) {
            this.name = this.name();
            this.keyCode = keyCode;
        }

        public static Key getByName(String name) {
            Key[] values = Key.values();
            for (Key key : values) {
                if (key.name.equals(name)) return key;
            }
            return Key.None;
        }

        public static Key getByCode(int keyCode) {
            Key[] values = Key.values();
            for (Key key : values) {
                if (key.keyCode == keyCode) return key;
            }
            return Key.None;
        }
    }
}
