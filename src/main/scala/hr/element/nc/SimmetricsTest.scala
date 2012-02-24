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
    val nameList  = getNameList("../name-compare/imena.csv")
    val cleanedNameList = cleanNameList(nameList.tail)

    val similarityMethod = new Levenshtein
    val start = System.currentTimeMillis()
    val res = getFullMatchNames(new Levenshtein, cleanedNameList, 0.75f, 0.75f)

    res foreach println
    println("Elapsed milliseconds: %s" format System.currentTimeMillis()- start)
  }

  def getFullMatchNames(similarityMethod: AbstractStringMetric, list: List[List[String]], minNameCorr: Float, minSurnameCorr: Float) = {
    val nameList = getPartialMatchNames(new Levenshtein, list, minNameCorr)
    val plainNameList = nameList.map(e => e._1 -> e._2.map(_._2))
    val matchSurnames = plainNameList.map(e => (List(e._1) -> e._2.map(x => (similarityMethod.getSimilarity(e._1._2, x._2), x))))
    val fullMatchList = matchSurnames.map( e => (e._1, e._2.sortWith((i, j) => i._1 > j._1).filter(_._1 > minSurnameCorr)))
    fullMatchList.filter(_._2.length > 2)//we filter only the names which have similar ones in the list
  }

  def getNameList(fullPath: String) = {
    parseCsv(readCsv(fullPath))
  }

  private def getPartialMatchNames(similarityMethod: AbstractStringMetric, names: List[List[String]], minCorrelation: Float) = {
    val simList = names.map( e => ((e.head, e.last), names.map( j => (similarityMethod.getSimilarity( e.head, j.head) -> (j.head, j.last)))))
    simList.map(e => (e._1, e._2.sortWith((x, y) => x._1 > y._1).filter(_._1 > minCorrelation)))
  }

  private def readCsv(fullPath: String) = Source.fromFile(new File(fullPath)).mkString

  private def parseCsv(csv: String) = csv.split("\n").toList.map(name => name.split(",").toList)

  private def cleanNameList(list: List[List[String]]) = {
    list.map( fullname => fullname.map(name => removeDiacritics(name.trim().toLowerCase)))//replaceAll("\\W", "")
  }

  //Jedino đ ne miče i valjda slična slova koja se ne sastoje od 2 dijela
  private def removeDiacritics(str: String) = {
    val nfd = Normalizer.normalize(str, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    pattern.matcher(nfd).replaceAll("")
    }


}
