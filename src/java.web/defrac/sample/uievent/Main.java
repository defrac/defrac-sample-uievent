package defrac.sample.uievent;

import defrac.ui.FrameBuilder;

import static defrac.web.Toplevel.document;

/**
 *
 */
public final class Main {
  public static void main(String[] args) {
    document().body.style.backgroundColor = "#666";

    FrameBuilder.
        forScreen(new UIEventSample()).
        show();
  }
}
