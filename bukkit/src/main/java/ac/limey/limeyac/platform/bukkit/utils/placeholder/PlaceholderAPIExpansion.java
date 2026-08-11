package ac.limey.limeyac.platform.bukkit.utils.placeholder;

import ac.limey.limeyac.LimeyAPI;
import ac.grim.grimac.api.GrimUser;
import ac.limey.limeyac.player.LimeyPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "limey";
    }

    public @NotNull String getAuthor() {
        return String.join(", ", LimeyAPI.INSTANCE.getLimeyPlugin().getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return LimeyAPI.INSTANCE.getExternalAPI().getGrimVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull List<String> getPlaceholders() {
        Set<String> staticReplacements = LimeyAPI.INSTANCE.getExternalAPI().getStaticReplacements().keySet();
        Set<String> variableReplacements = LimeyAPI.INSTANCE.getExternalAPI().getVariableReplacements().keySet();
        ArrayList<String> placeholders = new ArrayList<>(staticReplacements.size() + variableReplacements.size());
        for (String s : staticReplacements) {
            placeholders.add(s.equals("%limey_version%") ? s : "%limey_" + s.replace("%", "") + "%");
        }
        for (String s : variableReplacements) {
            placeholders.add(s.equals("%player%") ? "%limey_player%" : "%limey_player_" + s.replace("%", "") + "%");
        }
        return placeholders;
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
        for (Map.Entry<String, String> entry : LimeyAPI.INSTANCE.getExternalAPI().getStaticReplacements().entrySet()) {
            String key = entry.getKey().equals("%limey_version%")
                    ? "version"
                    : entry.getKey().replace("%", "");
            if (params.equalsIgnoreCase(key)) {
                return entry.getValue();
            }
        }

        if (offlinePlayer instanceof Player player) {
            LimeyPlayer limeyPlayer = LimeyAPI.INSTANCE.getPlayerDataManager().getPlayer(player.getUniqueId());
            if (limeyPlayer == null) return null;

            for (Map.Entry<String, Function<GrimUser, String>> entry : LimeyAPI.INSTANCE.getExternalAPI().getVariableReplacements().entrySet()) {
                String key = entry.getKey().equals("%player%")
                        ? "player"
                        : "player_" + entry.getKey().replace("%", "");
                if (params.equalsIgnoreCase(key)) {
                    return entry.getValue().apply(limeyPlayer);
                }
            }
        }

        return null;
    }
}
