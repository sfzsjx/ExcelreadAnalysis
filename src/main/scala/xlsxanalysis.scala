import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

object xlsxanalysis {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("xlsanalysis").setMaster("local[*]")

    val sc = new SparkContext(conf)

    val sqlContext = new SQLContext(sc)

    val peopleSchema = StructType(Array(
      StructField("Name", StringType, nullable = false),
      StructField("Age", DoubleType, nullable = false),
      StructField("Occupation", StringType, nullable = false),
      StructField("Date of birth", StringType, nullable = false)))

    val df = sqlContext.read
      .format("com.crealytics.spark.excel")
      .option("sheetName", "Daily") // Required
      .option("useHeader", "true") // Required
      .option("treatEmptyValuesAsNulls", "false") // Optional, default: true
      .option("inferSchema", "false") // Optional, default: false
      .option("addColorColumns", "true") // Optional, default: false
      .option("startColumn", 0) // Optional, default: 0
      .option("endColumn", 99) // Optional, default: Int.MaxValue
      .option("timestampFormat", "MM-dd-yyyy HH:mm:ss") // Optional, default: yyyy-mm-dd hh:mm:ss[.fffffffff]
      .option("maxRowsInMemory", 20) // Optional, default None. If set, uses a streaming reader which can help with big files
      .option("excerptSize", 10) // Optional, default: 10. If set and if schema inferred, number of rows to infer schema from
      .schema(peopleSchema) // Optional, default: Either inferred schema, or all columns are Strings
      .load("C:\\Users\\hadoop\\Documents\\test\\test4.xlsx")
//    def readExcel(file: String): DataFrame = sqlContext.read
//      .format("com.crealytics.spark.excel")
//      .option("location", file)
//      .option("useHeader", "true")
//      .option("treatEmptyValuesAsNulls", "true")
//      .option("inferSchema", "true")
//      .option("addColorColumns", "False")
//      .load()

 //   val data = readExcel("C:\\Users\\hadoop\\Documents\\test.xls")

    df.show(false)
  }
}