package chillverse.plasma.scene;

import java.util.Iterator;

import org.apache.pivot.collections.Sequence;

import com.sun.jna.Pointer;

public class Container extends Actor implements Sequence<Actor>, Iterable<Actor> {

  public Container() {
    super();
  }
  
  protected Container(Pointer ptr) {
    super(ptr);
  }

  @Override
  public Iterator<Actor> iterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int add(Actor item) {
    super.addChild(item);
    return super.indexOf(item);
  }

  @Override
  public void insert(Actor item, int index) {
    super.insertChild(item, index);
  }

  @Override
  public Actor update(int index, Actor item) {
    final Actor oldItem = super.getChildAtIndex(index);
    super.replaceChild(oldItem, item);
    return oldItem;
  }

  @Override
  public int remove(Actor item) {
    final int index = this.indexOf(item);
    super.removeChild(item);
    return index;
  }

  @Override
  public Sequence<Actor> remove(int index, int count) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Actor get(int index) {
    return super.getChildAtIndex(index);
  }

  @Override
  public int getLength() {
    return super.getChildrenNum();
  }

}
