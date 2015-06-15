package defrac.sample.uievent;

import defrac.ui.FrameBuilder;

/**
 *
 */
public final class Main {
  public static void main(String[] args) {
    FrameBuilder.
        forScreen(new UIEventSample()).
        width(UIEventSample.ELEMENTS_HORIZONTAL * (int) UIEventSample.ELEMENT_WIDTH).
        height(UIEventSample.ELEMENTS_VERTICAL * (int)UIEventSample.ELEMENT_HEIGHT).
        title("UIEventSample").
        backgroundColor(0xff000000).
        show();
  }
}
