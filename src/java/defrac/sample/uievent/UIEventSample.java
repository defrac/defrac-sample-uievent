package defrac.sample.uievent;

import defrac.display.BlendMode;
import defrac.display.DisplayObject;
import defrac.display.DisplayObjectContainer;
import defrac.ui.*;

import javax.annotation.Nonnull;

public final class UIEventSample extends Screen {
  // We are going to draw a bunch of rectangles on the screen.
  // Let's define their size and how many of them we want.
  public static final int ELEMENTS_HORIZONTAL = 40;
  public static final int ELEMENTS_VERTICAL = 30;
  public static final float ELEMENT_WIDTH = 32.0f;
  public static final float ELEMENT_HEIGHT = 24.0f;
  public static final int ELEMENTS_TOTAL = ELEMENTS_HORIZONTAL * ELEMENTS_VERTICAL;

  private DisplayList displayList;

  @Nonnull
  private final DisplayObject[] displayObjects =
      new DisplayObject[ELEMENTS_TOTAL];

  @Override
  protected void onCreate() {
    super.onCreate();

    LinearLayout layout =
        LinearLayout.
            horizontal().
            gravity(Gravity.CENTER);

    LinearLayout.LayoutConstraints layoutConstraints =
        new LinearLayout.LayoutConstraints();

    layoutConstraints.width((int)(ELEMENTS_HORIZONTAL * ELEMENT_WIDTH + 0.5f), PixelUnits.PX);
    layoutConstraints.height((int)(ELEMENTS_VERTICAL * ELEMENT_HEIGHT + 0.5f), PixelUnits.PX);

    layoutConstraints.gravity = Gravity.CENTER;

    displayList = new DisplayList();
    displayList.layoutConstraints(layoutConstraints);

    layout.addView(displayList);
    rootView(layout);

    displayList.onStageReady(stage -> {
      // This method is called once when our app has been created.
      // Let's fill the stage with some display objects
      createDisplayObjects(stage);

      // We want to animate those display objects. Let's attach a listener
      // to the onEnterFrame event
      stage.globalEvents().onEnterFrame.add(new Animator(displayObjects, 0.09f));
    });
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
        final DisplayObject quad =
            new InteractiveQuad(ELEMENT_WIDTH - 2.0f, ELEMENT_HEIGHT - 2.0f, color).
            centerRegistrationPoint().
            moveTo(halfWidth + x * ELEMENT_WIDTH, halfHeight + y * ELEMENT_HEIGHT).
            blendMode(BlendMode.ADD);

        displayObjects[displayObjectIndex++] = quad;

        container.addChild(quad);
      }
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    displayList.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    displayList.onResume();
  }
}
