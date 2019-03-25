////需导入spark-xml_2.10-0.4.0.jar
//package com.beagledata.spark
//
//import org.apache.spark.sql.SQLContext
//import org.apache.spark.{SparkConf, SparkContext}
//
///**
//  * xml数据处理（SQLContext）
//  *
//  * Created by drguo on 2017/8/18.
//  * blog.csdn.net/dr_guo
//  */
//
//object testxml {
//
//  val conf = new SparkConf().setAppName("pcsdata-sql")
//    .set("spark.jars.packages", "io.netty:netty-common:4.1.8.Final")
//    .set("spark.jars.exclude", "io.netty:netty-common")
//    .setMaster("local")
//
//  val sc = new SparkContext(conf)
//
//  val sqlContext = new SQLContext(sc)
//
//  def xlstest(args: Array[String]): Unit = {
//
//    val df = sqlContext.read
//      .format("com.databricks.spark.xml")
//      .option("rowTag", "ROW")
//      .load("C:\\Users\\hadoop\\Documents\\test.xlsx")
//    //.load("src/xlstest/resources/pcsTestData.xml")
//    df.show(100)
//    val pcsdf = df.select("ZDJG_V", "BGT", "SXZDDL", "XXZDDL").na.drop()//去掉缺失值，一行中有一个字段缺失整行去掉
//    val pcsrdd = pcsdf//.rdd.filter(_.length==4)
//      .map(x => x(0).toString+";"+x(1).toString.split("\\|")(0).split("\\$")(0)+";"+x(2).toString+";"+x(3).toString )
//    //.map(_.split(";")).filter(_.length>1).map(x => x(0)+";"+x(1)+";"+x(2)+";"+x(3))
//    //pcsrdd.foreach(println)
//    pcsrdd.repartition(40).saveAsTextFile("C:\\Users\\hadoop\\Documents\\result")
//
//  }
//}
