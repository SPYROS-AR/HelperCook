package org.helperCook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IngredientParser implements InstructionParser
{

    @Override
    public void parse(String instruction, Step.StepBuilder stepBuilder, Recipe.RecipeBuilder recipeBuilder)
    {
        Matcher ingredientMatcher = Pattern.compile(RegexConstants.INGREDIENT_REGEX).matcher(instruction);
        while (ingredientMatcher.find())
        {
            String ingredientName = ingredientMatcher.group(1);
            double quantity = ingredientMatcher.group(2) != null ? Double.parseDouble(ingredientMatcher.group(2)) : 1.0;
            String unit = ingredientMatcher.group(3) != null ? ingredientMatcher.group(3) : "";

            //System.out.println(quantity + " " + unit + " " + ingredientName);

            Ingredient ingredient = new Ingredient(ingredientName, unit);
            stepBuilder.addIngredient(ingredient, quantity);
            recipeBuilder.addIngredient(ingredient, quantity);
        }
    }
}