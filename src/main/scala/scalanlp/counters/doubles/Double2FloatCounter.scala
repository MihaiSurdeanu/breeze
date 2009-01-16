// THIS IS AN AUTO-GENERATED FILE. DO NOT MODIFY.    
// generated by GenCounter on Sun Jan 11 21:34:02 PST 2009
package scalanlp.counters.doubles;

import scala.collection.mutable.Map;
import scala.collection.mutable.HashMap;

/**
 * Count objects of type Double with type Float.
 * This trait is a wraooer around Scala's Map trait
 * and can work with any scala Map. 
 *
 * @author dlwh
 */
@serializable 
trait Double2FloatCounter extends FloatCounter[Double] {


  abstract override def update(k : Double, v : Float) = {

    super.update(k,v);
  }

  // this isn't necessary, except that the jcl MapWrapper overrides put to call Java's put directly.
  override def put(k : Double, v : Float) :Option[Float] = { val old = get(k); update(k,v); old}

  abstract override def -=(key : Double) = {

    super.-=(key);
  }

  /**
   * Increments the count by the given parameter.
   */
   override  def incrementCount(t : Double, v : Float) = {
     update(t,(this(t) + v).asInstanceOf[Float]);
   }


  override def ++=(kv: Iterable[(Double,Float)]) = kv.foreach(+=);

  /**
   * Increments the count associated with Double by Float.
   * Note that this is different from the default Map behavior.
  */
  override def +=(kv: (Double,Float)) = incrementCount(kv._1,kv._2);

  override def default(k : Double) : Float = 0;

  override def apply(k : Double) : Float = super.apply(k);

  // TODO: clone doesn't seem to work. I think this is a JCL bug.
  override def clone(): Double2FloatCounter  = super.clone().asInstanceOf[Double2FloatCounter]

  /**
   * Return the Double with the largest count
   */
  override  def argmax() : Double = (elements reduceLeft ((p1:(Double,Float),p2:(Double,Float)) => if (p1._2 > p2._2) p1 else p2))._1

  /**
   * Return the Double with the smallest count
   */
  override  def argmin() : Double = (elements reduceLeft ((p1:(Double,Float),p2:(Double,Float)) => if (p1._2 < p2._2) p1 else p2))._1

  /**
   * Return the largest count
   */
  override  def max : Float = values reduceLeft ((p1:Float,p2:Float) => if (p1 > p2) p1 else p2)
  /**
   * Return the smallest count
   */
  override  def min : Float = values reduceLeft ((p1:Float,p2:Float) => if (p1 < p2) p1 else p2)

  // TODO: decide is this is the interface we want?
  /**
   * compares two objects by their counts
   */ 
  override  def comparator(a : Double, b :Double) = apply(a) compare apply(b);

  /**
   * Return a new Double2DoubleCounter with each Float divided by the total;
   */
  override  def normalized() : Double2DoubleCounter = {
    val normalized = Double2DoubleCounter();
    val total : Double = this.total
    if(total != 0.0)
      for (pair <- elements) {
        normalized(pair._1) = pair._2 / total;
      }
    normalized
  }

  /**
   * Return the sum of the squares of the values
   */
  override  def l2norm() : Double = {
    var norm = 0.0
    for (val v <- values) {
      norm += (v * v)
    }
    return Math.sqrt(norm)
  }

  /**
   * Return a List the top k elements, along with their counts
   */
  override  def topK(k : Int) = Counters.topK[(Double,Float)](k,(x,y) => if(x._2 < y._2) -1 else if (x._2 == y._2) 0 else 1)(this);

  /**
   * Return \sum_(t) C1(t) * C2(t). 
   */
  def dot(that : Double2FloatCounter) : Double = {
    var total = 0.0
    for (val (k,v) <- that.elements) {
      total += get(k).asInstanceOf[Double] * v
    }
    return total
  }

  def +=(that : Double2FloatCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) + v).asInstanceOf[Float]);
    }
  }

  def -=(that : Double2FloatCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) - v).asInstanceOf[Float]);
    }
  }

  override  def *=(scale : Float) {
    transform { (k,v) => (v * scale).asInstanceOf[Float]}
  }

  override  def /=(scale : Float) {
    transform { (k,v) => (v / scale).asInstanceOf[Float]}
  }
}


object Double2FloatCounter {
  import it.unimi.dsi.fastutil.objects._
  import it.unimi.dsi.fastutil.ints._
  import it.unimi.dsi.fastutil.shorts._
  import it.unimi.dsi.fastutil.longs._
  import it.unimi.dsi.fastutil.floats._
  import it.unimi.dsi.fastutil.doubles._


  import scala.collection.jcl.MapWrapper;
  @serializable
  @SerialVersionUID(1L)
  class FastMapCounter extends MapWrapper[Double,Float] with Double2FloatCounter {
    private val under = new Double2FloatOpenHashMap;
    def underlying() = under.asInstanceOf[java.util.Map[Double,Float]];
    override def apply(x : Double) = under.get(x);
    override def update(x : Double, v : Float) {
      val oldV = this(x);
      updateTotal(v-oldV);
      under.put(x,v);
    }
  }

  def apply() = new FastMapCounter();

  
}

