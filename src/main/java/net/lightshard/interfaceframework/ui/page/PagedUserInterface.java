package net.lightshard.interfaceframework.ui.page;

import net.lightshard.interfaceframework.session.SessionMeta;
import net.lightshard.interfaceframework.session.SessionMetaObject;
import net.lightshard.interfaceframework.ui.InterfaceManager;
import net.lightshard.interfaceframework.ui.UserInterface;
import net.lightshard.interfaceframework.ui.content.Button;
import net.lightshard.interfaceframework.ui.content.ButtonListener;
import net.lightshard.interfaceframework.ui.content.ContentItem;
import net.lightshard.interfaceframework.util.StrongReference;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class PagedUserInterface<T> extends UserInterface
{
    private static final String PAGE_META_KEY_PREFIX = "INTERNALS_";
    private static final String PAGE_META_KEY_SUFFIX = "_PAGEINDEX";

    private final int startingSlot, elementsPerPage;
    private final int previousButtonSlot, nextButtonSlot;

    public PagedUserInterface(InterfaceManager interfaceManager, int startingSlot, int elementsPerPage,
                              int previousButtonSlot, int nextButtonSlot)
    {
        super(interfaceManager);

        this.startingSlot = startingSlot;
        this.elementsPerPage = elementsPerPage;
        this.previousButtonSlot = previousButtonSlot;
        this.nextButtonSlot = nextButtonSlot;
    }

    public abstract Collection<T> getUnpagedContent(Player player, SessionMeta meta);

    public abstract PagedButton getButtonForElement(T element);

    public abstract ItemStack getPreviousButtonItem(Player player, SessionMeta meta, Page page);

    public abstract ItemStack getNextButtonItem(Player player, SessionMeta meta, Page page);

    public abstract Collection<ContentItem> getAdditionalContent(Player player, SessionMeta meta, Page page);

    @Override
    public Collection<ContentItem> getContent(Player player, SessionMeta meta)
    {
        List<ContentItem> items = new ArrayList<ContentItem>();

        Collection<T> uContent = getUnpagedContent(player, meta);
        Page<T> page = new Page(uContent, elementsPerPage, getPageIndexReference(player));

        int offset = 0;
        for (T element : page.getContent())
        {
            PagedButton button = getButtonForElement(element);
            // Now we have to inject the slot where it'll appear
            button.injectSlot(startingSlot + offset++);
            items.add(button);
        }

        if (page.hasPrevious())
        {
            Button backButton = new Button(previousButtonSlot, getPreviousButtonItem(player, meta, page));
            backButton.addListener(newButtonListener(player, -1));
            items.add(backButton);
        }
        if (page.hasNext())
        {
            Button nextButton = new Button(nextButtonSlot, getNextButtonItem(player, meta, page));
            nextButton.addListener(newButtonListener(player, 1));
            items.add(nextButton);
        }

        items.addAll(getAdditionalContent(player, meta, page));
        return items;
    }

    public void setPageIndex(Player player, int pageIndex)
    {
        SessionMeta meta = getMeta(player);
        String metaKey = getMetaKey();
        if (meta.exists(metaKey))
            ((SessionMetaObject<StrongReference<Integer>>) meta.get(metaKey)).asType().set(pageIndex);
        else
            meta.set(metaKey, new SessionMetaObject(new StrongReference<Integer>(pageIndex)));
    }

    public StrongReference<Integer> getPageIndexReference(Player player)
    {
        SessionMeta meta = getMeta(player);
        String metaKey = getMetaKey();

        if (meta.exists(metaKey))
            return ((SessionMetaObject<StrongReference<Integer>>) meta.get(metaKey)).asType();
        else
        {
            StrongReference<Integer> ref = new StrongReference<Integer>(0);
            meta.set(metaKey, new SessionMetaObject(ref));
            return ref;
        }
    }

    public int getPageIndex(Player player)
    {
        return getPageIndexReference(player).get();
    }

    private SessionMeta getMeta(Player player)
    {
        return getDelegate().getVersionHandler().getSessionManager().getSession(player).getMeta();
    }

    private String getMetaKey()
    {
        return PAGE_META_KEY_PREFIX + toString() + PAGE_META_KEY_SUFFIX;
    }

    private ButtonListener newButtonListener(final Player player, final int increment)
    {
        return new ButtonListener()
        {
            @Override
            public void onClicked(Cancellable bukkitEvent)
            {
                int oldPage = getPageIndex(player);

                StrongReference<Integer> ref = getPageIndexReference(player);
                ref.set(ref.get() + increment);
                refresh(player);
            }

        };
    }

}
