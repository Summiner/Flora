package rs.jamie.flora.modules;

import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screen.ingame.LecternScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import rs.jamie.flora.FloraClient;

public class LecternCrash extends Module {

    public LecternCrash() {
        super(FloraClient.MODULES, "lectern-crash", "Lectern click crash");
        this.chatFeedback = false;
    }

    @EventHandler
    private void openScreen(OpenScreenEvent event) {
        if(event.screen instanceof LecternScreen) {
            mc.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(mc.player.currentScreenHandler.syncId, mc.player.currentScreenHandler.getRevision(), 0, 0, SlotActionType.QUICK_MOVE, new ItemStack(Items.AIR, -1), Int2ObjectMaps.emptyMap()));
            this.toggle();
        }
    }

    @EventHandler
    private void onLeave(GameLeftEvent event) {
        this.toggle();
    }

    @EventHandler
    private void getPackets(PacketEvent.Receive event) {
        if(event.packet instanceof TeleportConfirmC2SPacket) {
            this.toggle();
        }
    }
}