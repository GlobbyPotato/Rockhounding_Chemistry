package com.globbypotato.rockhounding_chemistry.utils;

import java.util.IllegalFormatException;

import net.minecraft.util.text.translation.I18n;
//From JEI, MIT license https://github.com/mezz/JustEnoughItems/blob/47afa1a9f57e85060c363db447eca023ed378717/src/main/java/mezz/jei/util/Translator.java
public class Translator {
	private Translator() {

	}

	public static String translateToLocal(String key) {
		if (I18n.canTranslate(key)) {
			return I18n.translateToLocal(key);
		} else {
			return I18n.translateToFallback(key);
		}
	}

	public static String translateToLocalFormatted(String key, Object... format) {
		String s = translateToLocal(key);
		try {
			return String.format(s, format);
		} catch (IllegalFormatException e) {
			String errorMessage = "Format error: " + s;
			return errorMessage;
		}
	}
}