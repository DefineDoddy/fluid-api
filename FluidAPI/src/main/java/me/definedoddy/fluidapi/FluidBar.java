package me.definedoddy.fluidapi;

import me.definedoddy.fluidapi.tasks.RepeatingTask;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FluidBar {
    private String title;
    private BarColor color = BarColor.WHITE;
    private BarStyle style = BarStyle.SOLID;
    private final List<BarFlag> flags = new ArrayList<>();
    private BossBar bar;
    private NamespacedKey key;
    private RepeatingTask tweenTask;

    public FluidBar(String title) {
        this.title = title;
    }

    public <T> T build(Class<T> returnClass) {
        if (returnClass == BossBar.class) {
            return (T)(bar == null ? bar = createBar() : bar);
        } else if (returnClass == FluidBar.class) {
            bar = createBar();
            return (T)this;
        }
        return null;
    }

    public BossBar build() {
        return createBar();
    }

    private BossBar createBar() {
        key = new NamespacedKey(FluidPlugin.getPlugin(), title.toLowerCase().replace(" ", "_"));
        if (flags.size() > 0) {
            return Bukkit.createBossBar(key, title, color, style, (BarFlag[]) flags.toArray());
        } else {
            return Bukkit.createBossBar(key, title, color, style);
        }
    }

    public FluidBar setColor(BarColor color) {
        this.color = color;
        if (bar != null) {
            bar.setColor(color);
        }
        return this;
    }

    public FluidBar setStyle(BarStyle style) {
        this.style = style;
        if (bar != null) {
            bar.setStyle(style);
        }
        return this;
    }

    public FluidBar addFlags(BarFlag... flags) {
        this.flags.addAll(Arrays.asList(flags));
        if (bar != null) {
            for (BarFlag flag : flags) {
                bar.addFlag(flag);
            }
        }
        return this;
    }

    public FluidBar removeFlags(BarFlag... flags) {
        this.flags.removeAll(Arrays.asList(flags));
        if (bar != null) {
            for (BarFlag flag : flags) {
                bar.removeFlag(flag);
            }
        }
        return this;
    }

    public FluidBar removeFlags() {
        flags.clear();
        if (bar != null) {
            bar.removeFlag(BarFlag.CREATE_FOG);
            bar.removeFlag(BarFlag.DARKEN_SKY);
            bar.removeFlag(BarFlag.PLAY_BOSS_MUSIC);
        }
        return this;
    }

    public FluidBar setTitle(String title) {
        this.title = title;
        if (bar != null) {
            bar.setTitle(title);
        }
        return this;
    }

    public FluidBar display() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(player);
        }
        return this;
    }

    public FluidBar display(Player... players) {
        for (Player player : players) {
            bar.addPlayer(player);
        }
        return this;
    }

    public void remove() {
        bar.removeAll();
        Bukkit.removeBossBar(key);
    }

    public void remove(Player player) {
        bar.removePlayer(player);
    }

    public FluidBar tween(double value, int ticks) {
        if (tweenTask != null) {
            tweenTask.cancel();
        }
        tweenTask = new RepeatingTask(0, 1) {
            final double time = value / ticks;
            double progress = bar.getProgress();
            final double start = bar.getProgress();
            @Override
            public void run() {
                progress = Math.min(progress, 1);
                progress = Math.max(progress, 0);
                bar.setProgress(progress);
                if (value > start) {
                    if (progress < value) {
                        progress = progress + time;
                    } else {
                        cancel();
                        tweenTask = null;
                    }
                } else if (value < start) {
                    if (progress > value) {
                        progress = progress + time;
                    } else {
                        cancel();
                        tweenTask = null;
                    }
                }
            }
        };
        return this;
    }
}
