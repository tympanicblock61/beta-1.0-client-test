package net.fabricmc.cryptic.utils;

import net.fabricmc.cryptic.Cryptic;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.Language;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageTranslations {

    private static final Language lang = Language.getInstance();
    private static final Map<String, Map<String, String>> langs = new HashMap<>();
    private static final Pattern transPattern = Pattern.compile("\\S+::\\S+");


    public LanguageTranslations() {
        String[] languages = readLocalFile("assets/languages/langs.cr").split("\n");
        for (String lang : languages) {
            Map<String, String> translatables = new HashMap<>();
            String[] lines = readLocalFile("assets/languages/"+lang.split("->")[1]).split("\n");
            for (String line : lines) {
                String[] parts = line.split("->");
                translatables.put(parts[0], parts[1]);
            }
            langs.put(lang.split("->")[0], translatables);
        }
    }

    public String translate(String pointer) {
        Map<String, String> translatable = langs.getOrDefault(lang.getCode(), langs.get("en_US"));
        String translation = translatable.getOrDefault(pointer, "Unknown");

        Matcher matcher = transPattern.matcher(translation);
        while (matcher.find()) {
            String found = matcher.group();
            translation = translation.replace(found, translate(found));
        }

        return translation;
    }


    private static @NotNull String readLocalFile(String file) {
        ClassLoader classloader = Cryptic.class.getClassLoader();
        InputStream stream = classloader.getResourceAsStream(file);
        StringBuilder builder = new StringBuilder();
        if (stream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            try {
                String string = bufferedReader.readLine();
                while (string != null) {
                    builder.append(string).append("\n");
                    string = bufferedReader.readLine();
                }
                builder.deleteCharAt(builder.length()-1);
                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }
}
