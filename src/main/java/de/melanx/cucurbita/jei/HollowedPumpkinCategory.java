package de.melanx.cucurbita.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.api.recipe.IHollowedPumpkin;
import de.melanx.cucurbita.core.registration.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HollowedPumpkinCategory implements IRecipeCategory<IHollowedPumpkin> {

    public static final ResourceLocation UID = new ResourceLocation(Cucurbita.getInstance().modid, "jei_hollowed_pumpkin");
    public static final ResourceLocation PUMPKIN_TEXTURE = new ResourceLocation("minecraft", "textures/block/pumpkin_top.png");
    public static final ResourceLocation PUMPKIN_INNER_TEXTURE = new ResourceLocation(Cucurbita.getInstance().modid, "textures/block/hollowed_pumpkin_inner.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;

    public HollowedPumpkinCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(144, 96);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.HOLLOWED_PUMPKIN));
        this.slot = guiHelper.getSlotDrawable();
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends IHollowedPumpkin> getRecipeClass() {
        return IHollowedPumpkin.class;
    }

    @Override
    public String getTitle() {
        return I18n.format("block.cucurbita.hollowed_pumpkin");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(IHollowedPumpkin recipe, IIngredients ii) {
        ii.setOutputLists(VanillaTypes.ITEM, recipe.getOutputs().stream().map(Pair::getLeft).map(Collections::singletonList).collect(Collectors.toList()));
        ii.setInputLists(VanillaTypes.ITEM, recipe.getIngredients().stream().map(Ingredient::getMatchingStacks).map(Arrays::asList).collect(Collectors.toList()));
        ii.setInput(VanillaTypes.FLUID, recipe.getFluidInput());
    }

    @Override
    public void draw(IHollowedPumpkin recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        matrixStack.push();
        matrixStack.scale(6, 6, 0);
        Minecraft.getInstance().getTextureManager().bindTexture(PUMPKIN_TEXTURE);
        AbstractGui.blit(matrixStack, 0, 0, 0, 0, 16, 16, 16, 16);
        Minecraft.getInstance().getTextureManager().bindTexture(PUMPKIN_INNER_TEXTURE);
        AbstractGui.blit(matrixStack, 1, 1, 1, 1, 14, 14, 16, 16);
        matrixStack.pop();

        matrixStack.push();
        matrixStack.translate(39, 30, 0);
        slot.draw(matrixStack);
        matrixStack.pop();
        matrixStack.push();
        String heatS = new TranslationTextComponent("jei.cucurbita.heatS").getString();
        String heatV = new TranslationTextComponent("jei.cucurbita.heatV", recipe.getMinHeat()).getString();
        float xS = fontRenderer.getStringWidth(heatS) / 2f;
        float xV = fontRenderer.getStringWidth(heatV) / 2f;
        matrixStack.translate(104 / 2f - xS, 50, 0);
        matrixStack.scale(0.7f, 0.7f, 1);
        fontRenderer.drawString(matrixStack, heatS, 0, 0, 0x000000);
        matrixStack.scale(1 / 0.7f, 1 / 0.7f, 1);
        matrixStack.translate(xS - xV, 7, 0);
        matrixStack.scale(0.7f, 0.7f, 1);
        fontRenderer.drawString(matrixStack, heatV, 0, 0, 0x000000);
        matrixStack.pop();

        int size = recipe.getOutputs().size();
        for (int i = 0; i < size; i++) {
            matrixStack.push();
            int x = 102 + i % 2 * 18;
            int y = 4 + (i / 2 * 26);
            matrixStack.translate(x, y, 0);
            matrixStack.translate(0, 19, 0);
            matrixStack.scale(0.7f, 0.7f, 1);
            String s = BigDecimal.valueOf(recipe.getOutputs().get(i).getRight()).multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).toPlainString() + "%";
            int center = fontRenderer.getStringWidth(s) / 2;
            fontRenderer.drawString(matrixStack, s, 14 - center, 0, 0x000000);
            matrixStack.pop();
        }
    }

    @Override
    public void setRecipe(IRecipeLayout layout, IHollowedPumpkin recipe, IIngredients ii) {
        List<List<FluidStack>> fluidInput = ii.getInputs(VanillaTypes.FLUID);
        layout.getFluidStacks().init(0, true, 40, 31, 16, 16, recipe.getFluidInput().getAmount(), true, null);
        layout.getFluidStacks().set(0, fluidInput.get(0));

        List<List<ItemStack>> inputs = ii.getInputs(VanillaTypes.ITEM);
        int index = 1;
        double angleBetweenEach = 360.0 / inputs.size();
        Vector2f point = new Vector2f(40, 7);
        Vector2f center = new Vector2f(40, 38);
        for (List<ItemStack> stack : inputs) {
            layout.getItemStacks().init(index, true, (int) point.x, (int) point.y);
            layout.getItemStacks().set(index, stack);
            index += 1;
            point = rotatePointAbout(point, center, angleBetweenEach);
        }

        List<List<ItemStack>> outputs = ii.getOutputs(VanillaTypes.ITEM);
        for (int i = 0; i < outputs.size(); i++) {
            int x = 102 + i % 2 * 18;
            int y = 4 + (i / 2 * 26);
            layout.getItemStacks().init(index, true, x, y);
            layout.getItemStacks().set(index, outputs.get(i));
            index += 1;
        }
    }

    public static Vector2f rotatePointAbout(Vector2f in, Vector2f about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vector2f((float) newX, (float) newY);
    }
}