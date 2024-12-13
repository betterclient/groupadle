package io.github.betterclient.groupadle.render;

public record RenderHolder(Renderer mainRenderer,
                           DesktopRenderer desktopRenderer,
                           TaskbarRenderer taskbarRenderer,
                           TopBarRenderer topBarRenderer,
                           ApplicationRenderer appRenderer) { }