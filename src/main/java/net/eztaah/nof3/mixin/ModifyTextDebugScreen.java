package net.eztaah.nof3.mixin;

import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.List;



@Mixin(DebugHud.class)
public class ModifyTextDebugScreen {
    
    // Handle left side of the debug screen
    @Inject(method = "getLeftText", at = @At("RETURN"), cancellable = true)
    public void modifyTextLeftDebugScreen(CallbackInfoReturnable<List<String>> cir) {
        List<String> lines = cir.getReturnValue();

        lines.removeIf(line -> line.startsWith("minecraft:"));
        lines.removeIf(line -> line.startsWith("XYZ:"));
        lines.removeIf(line -> line.startsWith("Block:"));
        lines.removeIf(line -> line.startsWith("Chunk:"));
        lines.removeIf(line -> line.startsWith("Facing:"));
        lines.removeIf(line -> line.startsWith("Client Light:"));   
        lines.removeIf(line -> line.startsWith("Biome:"));
        lines.removeIf(line -> line.startsWith("Local Difficulty:"));

        cir.setReturnValue(lines);
    }

    // Handle right side of the debug screen
    @Inject(method = "getRightText", at = @At("RETURN"), cancellable = true)
    public void modifyTextRightDebugScreen(CallbackInfoReturnable<List<String>> cir) {

        List<String> lines = cir.getReturnValue();
        int targetedBlockIndex = -1;

        // Search for "Targeted Block"
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("§nTargeted Block:")) {
                targetedBlockIndex = i;
                break;
            }
        }

        // If the line is found
        if (targetedBlockIndex != -1) {
            lines.subList(targetedBlockIndex, lines.size()).clear();
        }

        cir.setReturnValue(lines);
    }
}