package hr.element.nc

import uk.ac.shef.wit.simmetrics.similaritymetrics._
import scala.collection.JavaConversions._
import scala.util.parsing.combinator._
import scala.util.parsing.combinator.syntactical._
import scala.io.Source
import java.io.File
import java.text.Normalizer
import java.util.regex.Pattern


object Test {
  def main(args: Array[String]): Unit = {
    val csv    = readFile("../name-compare/imena.csv")
    val parsedList  = parseCsv(csv)
    val countNames  = parsedList.size
    val optParsList = optimizedList(parsedList.tail.take(300))


//    matchNames(new Levenshtein, optParsList)//ok
//    getMatchedNames(new Levenshtein, optParsList, 0.7f) foreach println
////    matchNames(new ChapmanOrderedNameCompoundSimilarity, optParsList)//ok
//    getMatchedNames(new ChapmanOrderedNameCompoundSimilarity, optParsList, 0.7f) foreach println
////    matchNames(new Jaro, optParsList)//bzvz
//    getMatchedNames(new Jaro, optParsList, 0.7f) foreach println
////    matchNames(new NeedlemanWunch, optParsList)//ok
//    getMatchedNames(new NeedlemanWunch, optParsList, 0.7f) foreach println
////    matchNames(new SmithWatermanGotoh, optParsList)//ok je
//    getMatchedNames(new SmithWatermanGotoh, optParsList, 0.7f) foreach println
////    matchNames(new SmithWatermanGotohWindowedAffine, optParsList)//ok je
//    getMatchedNames(new SmithWatermanGotohWindowedAffine, optParsList, 0.7f) foreach println


  }
  def getMatchedNames(similarityMethod: AbstractStringMetric, names: Seq[String], correlationMin: Float) = {
    val res = testSimilarity(similarityMethod, names)
    res.map( e => (e._1, e._2.filter(_._1 > correlationMin)))
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

  //def matchByNameAndSurname
  def optimizedList(list: Seq[String]) = {
    list.map( name => removeDiacritics(name.trim().toLowerCase.replace("\"","").replace(","," ")))
  }

  //Jedino đ ne miče i valjda slična slova koja se ne sastoje od 2 dijela
  private def removeDiacritics(str: String) = {
    val nfd = Normalizer.normalize(str, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    pattern.matcher(nfd).replaceAll("")
    }


}
