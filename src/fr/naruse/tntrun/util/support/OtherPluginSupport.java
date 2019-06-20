package fr.naruse.tntrun.util.support;

public class OtherPluginSupport {
    private WorldEditPluginSupport worldEditPlugin;
    private VaultPlugin vaultPlugin;
    public OtherPluginSupport(){
        this.vaultPlugin = new VaultPlugin();
        this.worldEditPlugin = new WorldEditPluginSupport();
    }

    public VaultPlugin getVaultPlugin() {
        return vaultPlugin;
    }

    public WorldEditPluginSupport getWorldEditPlugin() {
        return worldEditPlugin;
    }
}

