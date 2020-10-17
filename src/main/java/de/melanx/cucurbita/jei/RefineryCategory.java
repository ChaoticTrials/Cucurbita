package de.melanx.cucurbita.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.api.recipe.IRefinery;
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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class RefineryCategory implements IRecipeCategory<IRefinery> {
    public static final ResourceLocation UID = new ResourceLocation(Cucurbita.getInstance().modid, "jei_refinery");
    public static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Cucurbita.getInstance().modid, "textures/gui/refinery.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;

    public RefineryCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(150, 50);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.HOMEMADE_REFINERY));
        this.slot = guiHelper.getSlotDrawable();
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends IRefinery> getRecipeClass() {
        return IRefinery.class;
    }

    @Override
    public String getTitle() {
        return I18n.format("block.cucurbita.homemade_refinery");
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
    public void draw(IRefinery recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        matrixStack.push();
        matrixStack.translate(59, 6, 0);
        matrixStack.scale(2, 2, 0);
        Minecraft.getInstance().getTextureManager().bindTexture(GUI_TEXTURE);
        AbstractGui.blit(matrixStack, 0, 0, 0, 0, 16, 16, 16, 16);
        matrixStack.pop();

        matrixStack.push();
        TranslationTextComponent heatS = new TranslationTextComponent("jei.cucurbita.heatS");
        TranslationTextComponent heatV = new TranslationTextComponent("jei.cucurbita.heatV", recipe.getMinHeat());
        String s = heatS.appendString(" ").append(heatV).getString();
        float xS = fontRenderer.getStringWidth(s) / 2f;
        matrixStack.translate(164 / 2f - xS, 42, 0);
        matrixStack.scale(0.7f, 0.7f, 1);
        fontRenderer.drawString(matrixStack, s, 0, 0, 0x000000);
        matrixStack.pop();

        boolean hasItemOutput = !recipe.getRecipeOutput().isEmpty();
        matrixStack.push();
        matrixStack.translate(22, 17, 0);
        slot.draw(matrixStack);
        matrixStack.pop();

        matrixStack.push();
        matrixStack.translate(hasItemOutput ? 101 : 110, 17, 0);
        slot.draw(matrixStack);
        matrixStack.pop();

        if (hasItemOutput) {
            matrixStack.push();
            matrixStack.translate(120, 17, 0);
            slot.draw(matrixStack);
            matrixStack.pop();
        }
    }

    @Override
    public void setIngredients(IRefinery recipe, IIngredients ii) {
        List<Ingredient> listItemInputs = new ArrayList<>();
        List<ItemStack> listItemOutputs = new ArrayList<>();
        List<FluidStack> listFluidOutputs = new ArrayList<>();
        listItemInputs.add(recipe.getInput());
        if (!recipe.getRecipeOutput().isEmpty()) listItemOutputs.add(recipe.getRecipeOutput());
        listFluidOutputs.add(recipe.getFluidOutput());
        ii.setInputIngredients(listItemInputs);
        ii.setOutputs(VanillaTypes.ITEM, listItemOutputs);
        ii.setOutputs(VanillaTypes.FLUID, listFluidOutputs);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, IRefinery recipe, IIngredients ii) {
        boolean hasItemOutput = !ii.getOutputs(VanillaTypes.ITEM).isEmpty();

        layout.getItemStacks().init(0, true, 22, 17);
        layout.getItemStacks().set(0, ii.getInputs(VanillaTypes.ITEM).get(0));

        layout.getFluidStacks().init(0, true, hasItemOutput ? 102 : 111, 18, 16, 16, recipe.getFluidOutput().getAmount(), true, null);
        layout.getFluidStacks().set(0, ii.getOutputs(VanillaTypes.FLUID).get(0));

        layout.getItemStacks().init(1, false, 120, 17);
        if (hasItemOutput) layout.getItemStacks().set(1, ii.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
