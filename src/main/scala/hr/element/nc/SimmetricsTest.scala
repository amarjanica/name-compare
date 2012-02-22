package hr.element.nc

import uk.ac.shef.wit.simmetrics.similaritymetrics._
import scala.collection.JavaConversions._
import scala.util.parsing.combinator._
import scala.util.parsing.combinator.syntactical._
import scala.io.Source
import java.io.File


object Test {
  def main(args: Array[String]): Unit = {
    val names = Seq("ana","Ana", "Bačić", "a na", "Bachich", "bacic")

    val csv    = readFile("../name-compare/imena.csv")
    val parsedList = parseCsv(csv)
    val countNames = parsedList.size
    println(countNames)

    matchNames(new Levenshtein, parsedList.tail.take(50))//ok
//    matchNames(new ChapmanLengthDeviation, parsedList.tail.take(50))//ne valja
//    matchNames(new ChapmanOrderedNameCompoundSimilarity, parsedList.tail.take(50))//ok
//    matchNames(new Jaro, parsedList.tail.take(50))//ne valja
//    matchNames(new NeedlemanWunch, parsedList.tail.take(50))//ok
//    matchNames(new SmithWatermanGotoh, parsedList.tail.take(50))//tak tak
//    matchNames(new SmithWatermanGotohWindowedAffine, parsedList.tail.take(50))//tak tak


  }
  def matchNames(similarityMethod: AbstractStringMetric, names: Seq[String]){
    println("%s similarity check: \n" format similarityMethod.getShortDescriptionString)
    val res = testSimilarity(similarityMethod, names)
    res foreach println
    println("")
  }
  def readFile(fullPath: String) = Source.fromFile(new File(fullPath)).mkString

  def parseCsv(csv: String)      = csv.split("\n").toList

  private def testSimilarity(similarityMethod: AbstractStringMetric, names: Seq[String]) = {
    val simList = names.map( e => (e, names.map( j => (similarityMethod.getSimilarity( e, j) -> j))))
    val simListSorted = simList.map( e => (e._1, e._2.sortWith((i, j) => i._1 > j._1)))
    simListSorted
  }

  //def matchByName
  //def matchByNameAndSurname


}
