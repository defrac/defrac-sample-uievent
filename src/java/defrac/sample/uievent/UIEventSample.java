package defrac.sample.uievent;

import defrac.app.App;
import defrac.app.Bootstrap;
import defrac.display.BlendMode;
import defrac.display.DisplayObject;
import defrac.display.DisplayObjectContainer;
import defrac.event.Events;

import javax.annotation.Nonnull;

public final class UIEventSample extends App {
  public static void main(final String[] arguments) {
    // Every defrac application follows usually two rules:
    //  - It extends defrac.app.App
    //  - The main method is just a call to Bootstrap.run(new MyApp());
    //
    // Bootstrap is our platform-agnostic skeleton that will create an
    // app with a rendering context. Once your app has been created,
    // it will be notified via the onCreate, onStart, ..., onDestroy
    // methods about its current state and life-cycle.
    Bootstrap.run(new UIEventSample());
  }

  // We are going to draw a bunch of rectangles on the screen.
  // Let's define their size and how many of them we want.
  private static final int ELEMENTS_HORIZONTAL = 40;
  private static final int ELEMENTS_VERTICAL = 30;
  private static final float ELEMENT_WIDTH = 32.0f;
  private static final float ELEMENT_HEIGHT = 24.0f;
  private static final int ELEMENTS_TOTAL = ELEMENTS_HORIZONTAL * ELEMENTS_VERTICAL;

  @Nonnull
  private final DisplayObject[] displayObjects =
      new DisplayObject[ELEMENTS_TOTAL];

  @Override
  protected void onCreate() {
    // This method is called once when our app has been created.
    // Let's fill the stage with some display objects
    createDisplayObjects(stage());

    // We want to animate those display objects. Let's attach a listener
    // to the onEnterFrame event
    Events.onEnterFrame.attach(new Animator(displayObjects, 0.09f));
  }

  private void createDisplayObjects(@Nonnull final DisplayObjectContainer container) {
    final float halfWidth = ELEMENT_WIDTH * 0.5f;
    final float halfHeight = ELEMENT_HEIGHT * 0.5f;
    final float colorStepX = (float)0x80 / (float)ELEMENTS_HORIZONTAL;
    final float colorStepY = (float)0x80 / (float)ELEMENTS_VERTICAL;

    int displayObjectIndex = 0;

    for(int x = 0; x < ELEMENTS_HORIZONTAL; ++x) {
      for(int y = 0; y < ELEMENTS_VERTICAL; ++y) {
        // Give each display object a unique color.
        // Please note that we have to specify the alpha value for the color as well.
        int color = 0xff000000;
        color |=  (0x80 + (int)(x * colorStepX)) & 0xff;
        color |= ((0x80 + (int)(y * colorStepY)) & 0xff) << 0x08;
        color |=   0x800000;

        // This is where most stuff happens:
        //  - Create our InteractiveQuad
        //  - Center its registration point
        //  - Move it to a position on screen
        //  - Change the blend mode to ADD
        //  - Finally, we add it as a child to the container
        //
        // Hint: The return type of container.addChild would be InteractiveQuad
        //       in this context
        displayObjects[displayObjectIndex++] =
          container.addChild(
            new InteractiveQuad(ELEMENT_WIDTH - 2.0f, ELEMENT_HEIGHT - 2.0f, color).
                centerRegistrationPoint().
                moveTo(halfWidth + x * ELEMENT_WIDTH, halfHeight + y * ELEMENT_HEIGHT)).
                blendMode(BlendMode.ADD);
      }
    }
  }
}
