package net.fabricmc.cryptic.gui.font;

import net.minecraft.client.TextureManager;
import net.minecraft.client.ab;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class FontRenderer extends TextRenderer {

    Font font = new Font("Arial", Font.PLAIN, 12);
    public double scale = 1;
    DrawableHelper drawableHelper = new DrawableHelper();

    public FontRenderer(ab arg, String string, TextureManager textureManager, boolean bl) {
        super(arg, string, textureManager, bl);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    public void renderText(@NotNull String text, int posX, int posY, Color color, Color bgColor) {
        AffineTransform af = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(af, true, false);
        Rectangle2D stringBounds = font.getStringBounds(text, frc);
        int width = (int) ((Math.ceil(stringBounds.getWidth())*2)*scale);
        int height = (int) ((Math.ceil(stringBounds.getHeight())*2)*scale);
        BufferedImage textImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = textImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        if (scale > 1) g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(color);
        g2d.scale(scale, scale);
        g2d.drawString(text, 0, (int) -stringBounds.getY());
        g2d.dispose();

        for (int x = 0; x<textImage.getWidth(); x++ )
            for (int y = 0; y<textImage.getHeight(); y++)
               DrawableHelper.fill(posX+x, posY+y, posX+x+1, posY+y+1, textImage.getRGB(x, y));
        //TextureManager textureManager = Minecraft.getMinecraft().textureManager;
        //int texture = textureManager.method_1417(textImage);
        //GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        //textureManager.bindTexture(texture);
        //drawableHelper.drawTexture(posX, posY, 0, 0, width, height);

        /*int id = glGenTextures();
        ByteBuffer buffer = genByteBuffer(textImage);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, textImage.getWidth(), textImage.getHeight(), 0, GL_BGRA, GL_UNSIGNED_INT_8_8_8_8_REV, buffer);
        */
    }

    public ByteBuffer genByteBuffer(@NotNull BufferedImage image) {
        int[] pixels = new int[image.getHeight() * image.getWidth()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        buffer.asIntBuffer().put(pixels).flip();
        return buffer;
    }
}


