package eyeq.nameddrop.event;

import eyeq.nameddrop.NamedDrop;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NamedDropEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingDropsFirst(LivingDropsEvent event) {
        event.getEntityLiving().captureDrops = true;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onLivingDropsLast(LivingDropsEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if(!entity.hasCustomName()) {
            return;
        }
        String adjective = I18n.translateToLocal(NamedDrop.I18n_ADJ_FRONT) + entity.getCustomNameTag() + I18n.translateToLocal(NamedDrop.I18n_ADJ_BACK);
        for(EntityItem capturedDrop : entity.capturedDrops) {
            ItemStack itemStack = capturedDrop.getEntityItem();
            if(NamedDrop.isFront) {
                itemStack.setStackDisplayName(adjective + itemStack.getDisplayName());
            } else {
                itemStack.setStackDisplayName(itemStack.getDisplayName() + adjective);
            }
        }
        event.getEntityLiving().captureDrops = false;
    }
}
