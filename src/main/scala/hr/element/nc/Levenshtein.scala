package hr.element.nc

import scala.Math

object LevenshteinDistance{
  def main(args: Array[String]): Unit = {
    println(computeDistance("Ivana", "Ana"))
  }
  def computeDistance(name1: String, name2: String) = {
    def minimum(i1: Int, i2: Int, i3: Int) = Math.min(Math.min(i1, i2), i3)
    val dist = new Array[Array[Int]](name1.length + 1, name2.length + 1)
    for (i <- 0 to name1.length) dist(i)(0) = i
    for (j <- 0 to name2.length) dist(0)(j) = j

    for (i <- 1 to name1.length; j <- 1 to name2.length)
    dist(i)(j) = minimum (
    dist(i-1)(j) + 1,
    dist(i  )(j-1) + 1,
    dist(i-1)(j-1) + (if (name1(i-1) == name2(j-1)) 0 else 1)
    )

   dist(name1.length)(name2.length)
  }
}
