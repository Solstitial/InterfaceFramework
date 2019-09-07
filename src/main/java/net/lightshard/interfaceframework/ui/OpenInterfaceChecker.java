package net.lightshard.interfaceframework.ui;

import net.lightshard.interfaceframework.session.Session;
import org.bukkit.entity.Player;

public interface OpenInterfaceChecker
{

    boolean isOpen(Player player, Session session);

}
