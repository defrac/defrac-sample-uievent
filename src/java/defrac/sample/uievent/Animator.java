package defrac.sample.uievent;

import defrac.display.DisplayObject;
import defrac.event.Event;
import defrac.lang.Procedure;

import javax.annotation.Nonnull;

// The Animator is attached to the onEnterFrame event.
// Therefore it must implement Procedure<EnterFrameEvent> or just Procedure<Event>
//
// Each time this Animator is notified about an event, it's apply method is called.
// Have a look at defrac.lang.* to check some of the fundamental types and utilities
// we use in the framework
public final class Animator implements Procedure<Event> {
  @Nonnull
  private final DisplayObject[] displayObjects;
  private final float inertia;

  public Animator(@Nonnull final DisplayObject[] displayObjects,
                  final float inertia) {
    this.displayObjects = displayObjects;
    this.inertia = inertia;
  }

  @Override
  public void apply(@Nonnull final Event event) {
    for(final DisplayObject displayObject : displayObjects) {
      final float alpha = displayObject.alpha();
      final float rotation = displayObject.rotation();
      final float scaleX = displayObject.scaleX();
      final float scaleY = displayObject.scaleY();

      // Perform some kind of animation here ...
      //
      // Hint: Start this sample with "jvm:run" and then type "~jvm:compile"
      //       Then tweak some of those values here and you will instantly
      //       see the result in the running application
      displayObject.
          alpha(interpolate(alpha, 1.0f)).
          rotation(interpolate(rotation, 0.0f)).
          scaleX(interpolate(scaleX, 1.0f)).
          scaleY(interpolate(scaleY, 1.0f));
    }
  }

  private float interpolate(final float src, final float dst) {
    return src + (dst - src) * inertia;
  }
}
