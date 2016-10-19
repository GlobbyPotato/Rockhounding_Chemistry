package com.globbypotato.rockhounding_chemistry.items;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChemBook extends ItemWrittenBook {

	public ChemBook(String name) {
		super();
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		GameRegistry.register(this);
		setCreativeTab(Reference.RockhoundingChemistry);
        setMaxStackSize(1);
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand){
		NBTTagList bookPages = new NBTTagList();
		itemStackIn = new ItemStack(Items.WRITTEN_BOOK);
			bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
					  	"SUMMARY\n" 
					  + "p 2 - Acid making\n"
					  + "p 10- Salt making\n"
					  + "p 11- Mineral sizer\n"
					  + "p 14- Mineral analizer\n"
					  + "p 16- Chem. extraction\n"
					  + "p 20- Metal alloyer\n"
					  + "p 23- Alloys\n"
					  + "p 25- Applications\n"
					  + "p 28- Mine Crawler\n"
					  + "p 35- Redstone Laser\n"))));

			bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
            			"ACID MAKING\n" 
					  + "Acids are required to manipulate the minerals through the various stages of the process. They are made in the Lab Oven and are composed of a solute and a solvet. While the solute is simply crafted, the solvent is contained inside a tank."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
            			"Acids are also contained in a tank required in the output slot. The tank can hold up to 100 bucket worth of solution. The Lab oven is powered by fuel which provides heat to the reaction and consumes redstone to eventally simulate the electrolysis process."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
            			"To fill a tank with water it's necessary to stand inside the water while holding the tank in hand. It will start slowly to acquire buckets of water. Here is a list of produced acids:"))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"Sulfuric Acid\n"
        			  + "Solute: Sulfur Comp.\n"
        			  + "Solvent: Water Tank\n"
        			  + "\n"
        			  + "The Sulfur-bearing compost can be obtained from any oredicted sulfur dust, from analyzed pyrite shards and in minor quantity from any coal."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"Hydrochloric Acid\n"
        			  + "Solute: Salt\n"
        			  + "Solvent: Sulfuric Acid\n"
        			  + "\n"
        			  + "Salt can be obtained from any oredicted salt or produced by using an Evaporation Tank filled with water."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"Hydrofluoric Acid\n"
        			  + "Solute: Fluorite Comp.\n"
        			  + "Solvent: Sulfuric Acid\n"
        			  + "\n"
        			  + "The Fluorite Compost can be obtained by crushing the raw granite in the Mineral Sizer, or recycling the fluorite shards from Mineral Analyzer or any oredicted Fluorite dust."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"Syngas (fuel)\n"
        			  + "Solute: Carbon Comp.\n"
        			  + "Solvent: Water Tank\n"
        			  + "\n"
        			  + "The Carbon Compost can be obtained by cracking coal blocks and packing them into a concentrated compost."))));

            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"ACID COLOR CODE\n" 
        			  + "The different acids have a color code to identify in which slot each one needs to go.\n"
  					  + "BLUE:\n Water\n"
  					  + "WHITE:\n Sulfuric Acid\n"
  					  + "LIGHT BLUE:\n Hydrochloric Acid\n"
  					  + "YELLOW:\n Hydrofluoric Acid\n"
  					  + "DARK GRAY:\n Syngas\n"))));

            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"SALT MAKING\n" 
        			  + "Salt is made by filling an Evaporation Tank with a bucket of water and waiting it to dry out through the various phases. Evaporation will take some time which depends on the biome type. In case of rain, any content of the tank will melt back to water."))));
            
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"MINERAL SIZING\n" 
        			  + "The first step of the mineral processing consists into crushing the Uninspected Mineral inside the Mineral Sizer. At this stage the raw mineral is broken into smaller pieces eventually revealing it's content. This allows a first classification between 10 categories."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        			    "The machine has a main output for the biggest samples, but also has a secondary output for lower amounts of material and a waste output for smallest amounts. They may occur respectively with a 20% and 5% chance. An hooper under the machine is required for a perpetual work."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
            			"The machine uses a gear as consumable and is damaged each cycle."))));

            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"MINERAL ANALYZING\n" 
        			  + "Once obtained specific minerals they need to be treated to isolate the specimens. This happens inside the Mineral Analyzer where the mineral is separated by the rest of the ore by acid attacks. Three acid types ate used: Sulfuric, Hydrochloric and Hydrofluoric."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        			    "They are obtained in the Lab Oven. Each type of acid is requested in different quantities. The machine uses a test tube as consumable and is damaged each cycle. Acid tanks need to be placed in their proper slot following the color code."))));

            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"MINERAL EXTRACTION\n" 
        			  + "The final step consists in extracting chemical elements from their specimen. This happens in the Chemical Extractor. This machine uses three different methods to gather the elements: heat reaction, electrolysis and chemical reaction."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
            			"The heat reaction is given by using Syngas as fuel to supply additional heat for the separation. Electrolysis is given by consuming redstone for specific elements, while chemical reaction is given by Hydrofluoric Acid attacks."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
            			"Each specimen hosts different elements in different quantity. The extracted quantity is collected in a cabinet indicated by a green bar. When the indicator is full it means a regular dust can be formed. To create the dust it's necessary to replace the test tube with a Graduated Cylinder."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"The machine uses a test Tube as consumable and is damaged each cycle. An hopper under the machine will allow to pull out the dusts as they form."))));

            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"METAL ALLOYER\n" 
        			  + "This machine allows to mix elements to create alloys. It has a selector to choose the alloy and a grid to show the elements to inject and their quantity. Elements must me replicated in the input row to start the alloying. It uses an Ingot Pattern as consumable."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"The output will provide directly the alloyed ingot. An additional slot may produce additional nuggets of the same alloy as a waste material. To obtain the dust form, the ingots need to be crushed in the Mineral Sizer."))));
            bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"Alloys are split into decorative and fuctional types. Both allow to create blocks and bricks while the functional ones allow to create additional items, tools or machinery with specific applications."))));

    		bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
            			"** Decorative Alloys **\n" 
            			+ "Mischmetal, Rose Gold, Green Gold, White Gold, Shibuichi, Tombak, Pewter."))));
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"** Functional Alloys **\n" 
        				+ "CuBe, ScAl, BAM, YAG, Cupronickel, Nimonic, Hastelloy, Nichrome."))));
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"** Applications **\n" 
        				+ " - Mischmetal blocks produce sparks while walking on it wearing boots.\n"
						+ " - CuBe Crossbow allows to shoot arrows at full charge faster than a normal bow.\n"
        				+ " - ScAl Long Range Bow is a long range precision bow.\n"))));
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				  " - ScAl Compound Bat deals +5 damage at +4 speed.\n"
        				+ " - BAM Shears are long lasting shears (500 uses).\n" 
        				+ " - Mine Crawler allows to automatically mine blocks with different modes and functions. It is assembled in its Assembling Table\n"))));
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				" - Redstone Laser allows to transfer the redstone as a laser beam signal up to 30 blocks away"))));
        	
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"MINE CRAWLER.\n"
        			  + "The Mine Crawler is made of several parts defining its functions and a tier level defining the mining grid. These parts are assembled in the Assembling Table where the machine can be also dismantled and reconfigured.\n"))));
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        			    "The working mode is defined by a logic chip. The basic logic chip makes the machine work as a breaker. The advanced logic chip turns it into coring mode which allows to pick directly the block instead of its drop. Tile entities can't be mined."))));
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"-Filler. Caps fluids and falling blocks\n"
        			  + "-Absorb. Grabs the fluid as an item\n"
        			  + "-Tunneler. Covers the path creating a tunnel\n"
        			  + "-Lighter. Places torches when light decreases\n"
        			  + "-Railmaker. Places rails along the path"))));
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
    					"For functions to work the machine needs to be loaded with the specific resources. Filler and Tunneler require cobblestone, Absorb requires glass and Lighter requires torches. They can be added from the Assembler loader slots. They allow to reuse old memory chips."))));
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"The memory chip allows to store the glass and cobblestone loaded in the machine for future uses. The machine can be loaded in the additional slots of the gui. The machine also requires a casing system and 12 arms to be placed in the gui."))));
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
        				"The tier is set by placing mining heads in the tier grid to form the desired path (see wiki). To dismantle the machine it needs to be placed it in the slot where the casing goes and all the parts composing it will be returned."))));
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
    					"The Mine crawler can be removen from the ground by either mining it or using the mod wrench on it. The configuration will be stored to be placed again. Crawlers taken from the creative tab will not work because missing a configuration."))));

        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
    					"The Redstone Laser Bridge is a simple system which allows to transfer the redstone signal as a laser beam up to 30 blocks away from the origin. It consists of an emitter which gets the signal and sends it and a receiver which grabs and shares it."))));
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
    				  	"To properly work the signal must enter from the back of the emitter by a repeater. The receiver changes its state once hit by the beam and shares the signal to the 3 free sides. It needs a repeater again because the signal is very weak."))));
        	bookPages.appendTag(new NBTTagString(ITextComponent.Serializer.componentToJson(new TextComponentString(
    					"The beam can be barred by any block in its path and the updating speed depends on the distance covered by the beam."))));

    	// Add detals
		itemStackIn.setTagInfo("pages", bookPages);
		itemStackIn.setTagInfo("author", new NBTTagString("GlobbyPotato"));
		itemStackIn.setTagInfo("title", new NBTTagString("Chemistry Book"));
		
        playerIn.openBook(itemStackIn, hand);
        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){

    	if (stack.hasTagCompound()){
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            String a = nbttagcompound.getString("author");

            if (!StringUtils.isNullOrEmpty(a)){
                tooltip.add(TextFormatting.GRAY + "by GlobbyPotato");
            }
            tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("book.generation." + nbttagcompound.getInteger("generation")));
        }else{
            stack.setTagCompound(new NBTTagCompound());
            stack.setTagInfo("author", new NBTTagString("GlobbyPotato"));
        }
    }

	@Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){
        return true;
    }

}