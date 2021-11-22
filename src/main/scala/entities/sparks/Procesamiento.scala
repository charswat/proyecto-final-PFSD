package eci.edu.co
package entities.sparks
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, desc, split}
object Procesamiento extends SparkSessionWrapper {

  def main(args: Array[String]): Unit = {
    val tweetsds:DataFrame =
      spark
        .read
        .json("TweetsBackup.json")

    userRetweetsFollowers(tweetsds)
   // usertweetsLanguage(tweetsds)
   // usertweetsCountry(tweetsds)
   // usertweetsFollowers(tweetsds)
    //usertweetsDispositive(tweetsds)
    //userRetweetsYear(tweetsds)
  }

  case class UserRetweetsFollowers(Usuarios: String,Seguidores:String,Num_retweets: String){

  }

  // 1.los 50 Usuarios que mas retweets escribieron y la cantidad de seguidores  ordenados de mayor a menor .
  def userRetweetsFollowers( ds:DataFrame): Unit = {
  val data =ds.select("id_str","retweeted_status")
    .withColumn("usuarios",col("retweeted_status.user.screen_name"))
   .withColumn("Seguidores",col("retweeted_status.user.followers_count"))
    .groupBy("usuarios","Seguidores").count().orderBy(desc("count"))
    .withColumn("Num_retweets",col("count"))
    .drop("count")
   .where(col("usuarios").isNotNull and  col("Seguidores")
   .isNotNull).limit(50)//as[UserRetweetsFollowers].collect()
   // SaveUserRetweetsFollowersCommand(PostgresRepositoryTweets,data.toList).execute()
    data.write.csv(path="hdfs://localhost:9001/proyectoFinal")
      }
  case class UserTweetsLanguage(Usuarios: String,idioma:String,cantidad: Int)
//2.Muestra la cantidad de veces que escribieron tweets por cada idioma.
  def usertweetsLanguage( ds:DataFrame): Unit = {
   val language=  ds.select("id_str","lang")
      //.withColumn("usuarios",col("retweeted_status.user.screen_name"))
      //.withColumn("Seguidores",col("retweeted_status.user.followers_count"))
      .groupBy(col("lang").alias("idioma")).count().orderBy(desc("count"))
      .where(col("lang").isNotNull).limit(100)
    language.write.csv(path="hdfs://localhost:9001/proyectoFinal1")
    //.as[UserTweetsLanguage].collect()
    //SaveUserTweetsLanguageCommand(PostgresRepositoryTweets,language.toList).execute()

  }

  //3.Cantidad de amigos que tienen los usuarios que mas tweets realizaron.
  def usertweetsFollowers( ds:DataFrame): Unit = {
  val followers = ds.select("user")
      .groupBy(col("user.screen_name").alias("Usuario"),col("user.followers_count").alias("Seguidores")).count().orderBy(desc("count"))
      .limit(100)
      .na.fill("Desconocido",Seq("Seguidores"))
    followers.write.csv(path="hdfs://localhost:9001/proyectoFinal3")
  }
//4.Usuario y los Dispositivos que utilizaron para generar sus tweets.
  def usertweetsDispositive( ds:DataFrame): Unit = {
   val dispositive = ds.select("user","source")
      .withColumn("Dispositivo",split(col("source"),">").getItem(1))
     .withColumn("Dispositivos",split(col("Dispositivo"),"<").getItem(0))
     .groupBy(col("user.screen_name").alias("Usuario"),col("Dispositivos"))
     .count().orderBy(desc("count"))
      .limit(100)
    dispositive.write.csv(path="hdfs://localhost:9001/proyectoFinal4")

  }
  //5.muestre el nombre de los Usuarios que mas tweets realizaron y el pais de origen
  def usertweetsCountry( ds:DataFrame): Unit = {
    val  Country=   ds.select("user")
      .withColumn("pais",col("user.location"))
      //.withColumn("Seguidores",col("retweeted_status.user.followers_count"))
      .groupBy(col("user.screen_name").alias("Nombre"),col("pais")).count().orderBy(desc("count"))
      .limit(100)
      .na.fill("Desconocido",Seq("pais"))
    Country.write.csv(path="hdfs://localhost:9001/proyectoFinal2")
  }
  //6.Año en el que mas retweets realizaron los usuarios, ordenado de manera descendente
  def userRetweetsYear( ds:DataFrame): Unit = {
    val year = ds.select("retweeted_status.user","retweeted_status")
      .withColumn("Año",split(col("retweeted_status.created_at"),"0000").getItem(1))
     // .withColumn("Dispositivos",split(col("Dispositivo"),"<").getItem(0))
      .groupBy(col("user.screen_name").alias("Usuario"),col("Año"))
      .count().orderBy(desc("count"))
      .limit(100)
      .where(col("Usuario").isNotNull)
      year.write.csv(path="hdfs://localhost:9001/proyectoFinal5")

  }
}


