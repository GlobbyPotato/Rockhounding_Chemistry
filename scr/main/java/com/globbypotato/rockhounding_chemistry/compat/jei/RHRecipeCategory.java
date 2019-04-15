package com.globbypotato.rockhounding_chemistry.compat.jei;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_core.utils.Translator;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

public abstract class RHRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {
	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;

	public RHRecipeCategory(@Nonnull IDrawable background, String unlocalizedName) {
		this.background = background;
		this.localizedName = Translator.translateToLocal(unlocalizedName);
	}

	@Nonnull
	@Override
	public String getTitle() {
		return this.localizedName;
	}

	@Nonnull
	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public String getModName() {
		return Reference.NAME;
	}

}