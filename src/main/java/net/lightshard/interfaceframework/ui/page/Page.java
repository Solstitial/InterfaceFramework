package net.lightshard.interfaceframework.ui.page;

import net.lightshard.interfaceframework.util.StrongReference;
import org.bukkit.Bukkit;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Collection;

public class Page<T>
{
    private final Collection<T> content;
    private final int pageIndex;
    private final boolean hasPrevious, hasNext;

    public Page(Collection<T> unpagedContent, int pageSize, StrongReference<Integer> pageIndex)
    {
        int pages = (int) Math.ceil((double) unpagedContent.size() / (double) pageSize);

        // Check whether the requested pageIndex has exceeded the number of pages,
        // if so we'll change the reference to the last page
        if(pageIndex.get() > pages - 1)
            pageIndex.set(pages - 1);
        // Check for a 0 index, just in case
        if(pageIndex.get() < 0)
            pageIndex.set(0);

        int startIndex = pageIndex.get() * pageSize;
        boolean lastPage = (pages == pageIndex.get() + 1);

        content = new ArrayList<T>(unpagedContent).subList(startIndex,
                                                           lastPage
                                                                ? unpagedContent.size() - 1
                                                                : startIndex + pageSize);
        this.pageIndex = pageIndex.get();
        this.hasPrevious = startIndex != 0;
        this.hasNext = !lastPage;
    }

    public Collection<T> getContent()
    {
        return content;
    }

    public int getPageIndex()
    {
        return pageIndex;
    }

    public boolean hasPrevious()
    {
        return hasPrevious;
    }

    public boolean hasNext()
    {
        return hasNext;
    }

}
